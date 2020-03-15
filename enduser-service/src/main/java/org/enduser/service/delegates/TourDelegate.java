package org.enduser.service.delegates;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.apache.log4j.Logger;
import org.enduser.service.dao.AdminDao;
import org.enduser.service.dao.TourDao;
import org.enduser.service.exception.EndUserException;
import org.enduser.service.model.CustomedTour;
import org.enduser.service.model.Group1Price;
import org.enduser.service.model.Group2Price;
import org.enduser.service.model.Group3Price;
import org.enduser.service.model.Group4Price;
import org.enduser.service.model.Group5Price;
import org.enduser.service.model.LandingTour;
import org.enduser.service.model.RefundTourist;
import org.enduser.service.model.SimplifyPayment;
import org.enduser.service.model.Tour;
import org.enduser.service.model.TourOperator;
import org.enduser.service.model.TourRoute;
import org.enduser.service.model.TourTourOperator_C;
import org.enduser.service.model.TourTransaction;
import org.enduser.service.model.TourType;
import org.enduser.service.util.DateConstants;
import org.enduser.service.util.SendMailUtil;
import org.enduser.service.util.TourServiceUtil;
import org.enduser.service.util.TouristGroupServiceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TourDelegate {
    private static final Logger logger = Logger.getLogger(TourDelegate.class);

    private TourDao tourDao;
    private TourOperatorDelegate tourOperatorDelegate;
    private DateConstants dateConstants;
    private TourPriceDelegate tourPriceDelegate;
    private CustomedTour customedTour;
    private TourServiceUtil tourServiceUtil;
    private TouristGroupServiceUtil touristGroupServiceUtil;
    private SendMailUtil sendMailUtil;
    private AdminDao adminDao;

    @Autowired
    public void setTourDao(TourDao tourDao) {
        this.tourDao = tourDao;
    }

    @Autowired
    public void setTourOperatorDelegate(TourOperatorDelegate tourOperatorDelegate) {
        this.tourOperatorDelegate = tourOperatorDelegate;
    }

    @Autowired
    public void setDateConstants(DateConstants dateConstants) {
        this.dateConstants = dateConstants;
    }

    @Autowired
    public void setTourPriceDelegate(TourPriceDelegate tourPriceDelegate) {
        this.tourPriceDelegate = tourPriceDelegate;
    }

    @Autowired
    public void setCustomedTour(CustomedTour customedTour) {
        this.customedTour = customedTour;
    }

    @Autowired
    public void setTourServiceUtil(TourServiceUtil tourServiceUtil) {
        this.tourServiceUtil = tourServiceUtil;
    }

    @Autowired
    public void setTouristGroupServiceUtil(TouristGroupServiceUtil touristGroupServiceUtil) {
        this.touristGroupServiceUtil = touristGroupServiceUtil;
    }

    @Autowired
    public void setSendMailUtil(SendMailUtil sendMailUtil) {
        this.sendMailUtil = sendMailUtil;
    }

    @Autowired
    public void setAdminDao(AdminDao adminDao) {
        this.adminDao = adminDao;
    }

    public Tour saveTour(Tour tour) {
        logger.info("Entered TourDelegate saveTour method");
        Tour tourObject = null;

        try {
            tourObject = tourDao.saveTour(tour);
            if (tourObject != null) {
                List<TourOperator> tourOperator =
                        tourOperatorDelegate.getOperatorPublicData("operator_id", tourObject.getOperatorId());
                if (tourOperator.size() != 0)
                    sendMailUtil.tourRegistrationEmail(tourObject, tourOperator.get(0).getEmailAddress());
            }

        } catch (Exception e) {
            throw new EndUserException();
        }
        return tourObject;
    }

    public LandingTour saveLandingTour(LandingTour landingTour, boolean editFlag) {
        logger.info("Entered TourDelegate LandingTour method");
        LandingTour tourObject = null;

        try {
            tourObject = tourDao.saveLandingTour(landingTour, editFlag);
            if (tourObject != null) {
                List<TourOperator> tourOperator =
                        tourOperatorDelegate.getOperatorPublicData("operator_id", tourObject.getOperatorId());

                if (tourOperator.size() != 0 && !editFlag){
                    sendMailUtil.tourRegistrationEmail(tourObject, tourOperator.get(0).getEmailAddress());
                    sendMailUtil.tourRegistrationNotification(tourObject.getOperatorId(),tourOperator.get(0).getOperatorName(), landingTour.getTourId());
                }
                    //   else if(tourOperator.size() != 0 && editFlag == true)
                //    sendMailUtil.tourUpdateEmail(tourObject, tourOperator.get(0).getEmailAddress());
            }

        } catch (Exception e) {
            throw new EndUserException();
        }
        return tourObject;
    }

    public List<Tour> getTourId(String key, String value) {
        logger.info("Entered TourDelegate getTourId method");
        List<Tour> tourList = null;

        try {
            tourList = tourDao.getTourId(key, value);
        } catch (Exception e) {
            throw new EndUserException();
        }
        return tourList;
    }

    public List<LandingTour> getLandingTourId(String key, String value) {
        logger.info("Entered TourDelegate getLandingTourId method");
        List<LandingTour> tourList = null;

        try {
            tourList = tourDao.getLandingTourId(key, value);
        } catch (Exception e) {
            throw new EndUserException();
        }
        return tourList;
    }

    public List<Tour> getTour(String key, String value, boolean inclusiveFlag, String tripDate, int tourDuration, int groupSize,
            String country) {
        logger.info("Entered TourDelegate getTour method");
        List<Tour> tours = null;
        int[] minMaxTripDuration = new int[2];
        String[] minMaxPlannedTripDate = new String[2];

        try {
            minMaxTripDuration = touristGroupServiceUtil.getMinMaxTripDuration(tourDuration);
            minMaxPlannedTripDate = touristGroupServiceUtil.getMinMaxPlannedTripDate(tripDate);

            tours = tourDao.getTour(key, value, minMaxPlannedTripDate, minMaxTripDuration, tripDate, country);

            for (Tour tour : tours) {
                String operatorId = tour.getOperatorId();
                String tourId = tour.getTourId();
                tour.setGroupSize(groupSize);
                tour.setExchangeRate(adminDao.getExchangeRate());
                if (tour.getTourStartDate() == null)
                    tour.setTourStartDate(dateConstants.parseDate(tripDate));

                if (operatorId != null) {
                    List<TourOperator> tourOperator = tourOperatorDelegate.getTourOperator("operator_id", operatorId);
                    if (tourOperator != null && tourOperator.size() == 1) {
                        tour.setTourOperator(tourOperator.get(0));
                    }
                }

                tour.setTourPrice(tourServiceUtil.getThisTourPrice(operatorId, tourId, inclusiveFlag,
                        dateConstants.parseDate(tripDate), groupSize));
                tour.setTourRouteObject(getTourRoute(tourId));

                List<Group1Price> group1Price = tourPriceDelegate.getGroup1Price(tourId, inclusiveFlag);
                tour.setGroup1Price((group1Price.size() == 1) ? group1Price.get(0) : null);

                List<Group2Price> group2Price = tourPriceDelegate.getGroup2Price(tourId, inclusiveFlag);
                tour.setGroup2Price((group2Price.size() == 1) ? group2Price.get(0) : null);

                List<Group3Price> group3Price = tourPriceDelegate.getGroup3Price(tourId, inclusiveFlag);
                tour.setGroup3Price((group3Price.size() == 1) ? group3Price.get(0) : null);

                List<Group4Price> group4Price = tourPriceDelegate.getGroup4Price(tourId, inclusiveFlag);
                tour.setGroup4Price((group4Price.size() == 1) ? group4Price.get(0) : null);

                List<Group5Price> group5Price = tourPriceDelegate.getGroup5Price(tourId, inclusiveFlag);
                tour.setGroup5Price((group5Price.size() == 1) ? group5Price.get(0) : null);

            }

            if (tours.size() > 0)
                (tours.get(0)).setExchangeRate(adminDao.getExchangeRate());

        } catch (Exception e) {
            throw new EndUserException();
        }

        return tours;
    }

    public List<Tour> getTourForAdvert(String country) {
        logger.info("Entered getTourForAdvert getTour method");
   
        boolean inclusiveFlag = true;
        Date tripDate = new Date();
        int groupSize = 1;
        List<Tour> tours = null;

        try {

            tours = tourDao.getTourByCountry(country);

            for (Tour tour : tours) {
                String operatorId = tour.getOperatorId();
                String tourId = tour.getTourId();
                tour.setGroupSize(1);
                tour.setExchangeRate(adminDao.getExchangeRate());
                if (tour.getTourStartDate() == null)
                    tour.setTourStartDate(tripDate);

                if (operatorId != null) {
                    List<TourOperator> tourOperator = tourOperatorDelegate.getTourOperator("operator_id", operatorId);
                    if (tourOperator != null && tourOperator.size() == 1) {
                        tour.setTourOperator(tourOperator.get(0));
                    }
                }

                tour.setTourPrice(tourServiceUtil.getThisTourPrice(operatorId, tourId, inclusiveFlag, tripDate, groupSize));
                tour.setTourRouteObject(getTourRoute(tourId));

                List<Group1Price> group1Price = tourPriceDelegate.getGroup1Price(tourId, inclusiveFlag);
                tour.setGroup1Price((group1Price.size() == 1) ? group1Price.get(0) : null);

                List<Group2Price> group2Price = tourPriceDelegate.getGroup2Price(tourId, inclusiveFlag);
                tour.setGroup2Price((group2Price.size() == 1) ? group2Price.get(0) : null);

                List<Group3Price> group3Price = tourPriceDelegate.getGroup3Price(tourId, inclusiveFlag);
                tour.setGroup3Price((group3Price.size() == 1) ? group3Price.get(0) : null);

                List<Group4Price> group4Price = tourPriceDelegate.getGroup4Price(tourId, inclusiveFlag);
                tour.setGroup4Price((group4Price.size() == 1) ? group4Price.get(0) : null);

                List<Group5Price> group5Price = tourPriceDelegate.getGroup5Price(tourId, inclusiveFlag);
                tour.setGroup5Price((group5Price.size() == 1) ? group5Price.get(0) : null);

            }

            if (tours.size() > 0)
                (tours.get(0)).setExchangeRate(adminDao.getExchangeRate());

        } catch (Exception e) {
            throw new EndUserException();
        }

        return tours;
    }

    public Tour getTourById(String tourId) {
        logger.info("Entered TourDelegate getTourById method");
        List<Tour> tourList = new ArrayList<Tour>();
        Tour tour = null;

        try {
            tourList = tourDao.getTourById(tourId);
            if (tourList.size() == 1) {
                tour = tourList.get(0);
                String operatorId = tour.getOperatorId();
                if (operatorId != null) {
                    List<TourOperator> tourOperator = tourOperatorDelegate.getTourOperator("operator_id", operatorId);
                    if (tourOperator.size() == 1) {
                        tour.setTourOperator(tourOperator.get(0));
                    }
                }
            }
            return tour;
        } catch (Exception e) {
            throw new EndUserException();
        }
    }

    public LandingTour getTourForEdit(String tourId) {
        logger.info("Entered TourDelegate getTourForEdit method");
        List<LandingTour> tourList = new ArrayList<LandingTour>();
        LandingTour tour = null;
        boolean inclusiveFlag = true;
        
        try {
            tourList = tourDao.getTourForEdit(tourId);
            if (tourList.size() == 1) {
                tour = tourList.get(0);
                
                tour.setTourRouteObject(getTourRoute(tourId));

                List<Group1Price> group1Price = tourPriceDelegate.getGroup1Price(tourId, inclusiveFlag);
                tour.setGroup1Price((group1Price.size() == 1) ? group1Price.get(0) : null);

                List<Group2Price> group2Price = tourPriceDelegate.getGroup2Price(tourId, inclusiveFlag);
                tour.setGroup2Price((group2Price.size() == 1) ? group2Price.get(0) : null);

                List<Group3Price> group3Price = tourPriceDelegate.getGroup3Price(tourId, inclusiveFlag);
                tour.setGroup3Price((group3Price.size() == 1) ? group3Price.get(0) : null);

                List<Group4Price> group4Price = tourPriceDelegate.getGroup4Price(tourId, inclusiveFlag);
                tour.setGroup4Price((group4Price.size() == 1) ? group4Price.get(0) : null);

                List<Group5Price> group5Price = tourPriceDelegate.getGroup5Price(tourId, inclusiveFlag);
                tour.setGroup5Price((group5Price.size() == 1) ? group5Price.get(0) : null);
            }
            return tour;
        } catch (Exception e) {
            throw new EndUserException();
        }
    }
    
    public List<Tour> getTourByOperatorId(String operatorId) { // NO need to loop inside
        logger.info("Entered TourDelegate getTourByOperatorId method");
        List<Tour> tourList = null;

        try {
            tourList = tourDao.getTourByOperatorId(operatorId);
            if (tourList.size() != 0) {
                List<TourOperator> tourOperator =
                        tourOperatorDelegate.getTourOperator("operator_id", tourList.get(0).getOperatorId());
                for (Tour tour : tourList) {
                    tour.setTourOperator(tourOperator.get(0));
                }
            }
            return tourList;
        } catch (Exception e) {
            throw new EndUserException();
        }
    }
    
    public List<Tour> getTourByCountry(String country) { // NO need to loop inside
        logger.info("Entered TourDelegate getTourByCountry method");
        List<Tour> tourList = null;

        try {
            tourList = tourDao.getTourByCountry(country);
            if (tourList.size() != 0) {
                List<TourOperator> tourOperator =
                        tourOperatorDelegate.getTourOperator("operator_id", tourList.get(0).getOperatorId());
                for (Tour tour : tourList) {
                    tour.setTourOperator(tourOperator.get(0));
                }
            }
            return tourList;
        } catch (Exception e) {
            throw new EndUserException();
        }
    }

    public List<LandingTour> getMyTours(String operatorId) {
        logger.info("Entered TourDelegate getMyTours method");
        List<LandingTour> tourList = new ArrayList<LandingTour>();
        try {
            tourList = tourDao.getMyTours(operatorId);

            for (LandingTour tour : tourList) {
                String tourId = tour.getTourId();

                tour.setTourRouteObject(getTourRoute(tourId));

                // Inclusive price
                List<Group1Price> group1Price = tourPriceDelegate.getGroup1Price(tourId, true);
                tour.setGroup1Price((group1Price.size() == 1) ? group1Price.get(0) : null);

                List<Group2Price> group2Price = tourPriceDelegate.getGroup2Price(tourId, true);
                tour.setGroup2Price((group2Price.size() == 1) ? group2Price.get(0) : null);

                List<Group3Price> group3Price = tourPriceDelegate.getGroup3Price(tourId, true);
                tour.setGroup3Price((group3Price.size() == 1) ? group3Price.get(0) : null);

                List<Group4Price> group4Price = tourPriceDelegate.getGroup4Price(tourId, true);
                tour.setGroup4Price((group4Price.size() == 1) ? group4Price.get(0) : null);

                List<Group5Price> group5Price = tourPriceDelegate.getGroup5Price(tourId, true);
                tour.setGroup5Price((group5Price.size() == 1) ? group5Price.get(0) : null);

                // Exclusive price
                List<Group1Price> exc_Group1Price = tourPriceDelegate.getGroup1Price(tourId, false);
                tour.setExc_Group1Price((exc_Group1Price.size() == 1) ? exc_Group1Price.get(0) : null);

                List<Group2Price> exc_Group2Price = tourPriceDelegate.getGroup2Price(tourId, false);
                tour.setExc_Group2Price((exc_Group2Price.size() == 1) ? exc_Group2Price.get(0) : null);

                List<Group3Price> exc_Group3Price = tourPriceDelegate.getGroup3Price(tourId, false);
                tour.setExc_Group3Price((exc_Group3Price.size() == 1) ? exc_Group3Price.get(0) : null);

                List<Group4Price> exc_Group4Price = tourPriceDelegate.getGroup4Price(tourId, false);
                tour.setExc_Group4Price((exc_Group4Price.size() == 1) ? exc_Group4Price.get(0) : null);

                List<Group5Price> exc_Group5Price = tourPriceDelegate.getGroup5Price(tourId, false);
                tour.setExc_Group5Price((exc_Group5Price.size() == 1) ? exc_Group5Price.get(0) : null);
            }

            return tourList;
        } catch (Exception e) {
            throw new EndUserException();
        }
    }

    public String getTourId(String operatorId) {
        logger.info("Entered TourDelegate getTourId method");

        try {
            return (operatorId.concat("_T")).concat(Integer.toString((tourDao.getTourCount(operatorId)) + 1));
        } catch (Exception e) {
            throw new EndUserException();
        }
    }

    public String getTourId(String operatorId, int counter) {
        logger.info("Entered TourDelegate getTourId with counter method");

        try {
            return (operatorId.concat("_T")).concat(Integer.toString((tourDao.getTourCount(operatorId)) + counter));
        } catch (Exception e) {
            throw new EndUserException();
        }
    }

    public void updateTour(String key, String value, String tourId) {
        logger.info("Entered TourDelegate updateTour method");

        try {
            tourDao.updateTour(key, value, tourId);
        } catch (Exception e) {
            throw new EndUserException();
        }
    }

    public void deleteTour(Tour tour) {
        logger.info("Entered TourDelegate deleteTour method");

        try {
            tourDao.deleteTour(tour);
        } catch (Exception e) {
            throw new EndUserException();
        }
    }

    public void deleteTour(LandingTour tour) {
        logger.info("Entered TourDelegate deleteTour method : LandingTour");

        try {
            tourDao.deleteTour(tour);
        } catch (Exception e) {
            throw new EndUserException();
        }
    }

    public void deleteCustomedTour(String emailAddress) {
        logger.info("Entered TourDelegate deleteCustomedTour method");

        try {
            tourDao.deleteCustomedTour(emailAddress);
        } catch (Exception e) {
            throw new EndUserException();
        }
    }

    public List<TourTourOperator_C> getToursByType(String tourType) {
        logger.info("Entered TourDelegate getToursByType method");
        List<TourTourOperator_C> tourOperator_C = null;

        try {
            tourOperator_C = tourDao.getToursByType(tourType);
        } catch (Exception e) {
            throw new EndUserException();
        }
        return tourOperator_C;
    }

    public List<TourType> getTourType(String country) {
        logger.info("Entered TourDelegate getTourType method");

        List<TourType> tourTypeList = new ArrayList<TourType>();
        List<String> tourTypeSet = tourDao.getTourType(country);
        int i = 0;
        for (String set : tourTypeSet) {
            TourType tourTypeObject = new TourType(i, set);
            tourTypeList.add(tourTypeObject);
            i++;
        }
        return tourTypeList;
    }

    public List<TourType> getLandingTourType(String country) {
        logger.info("Entered TourDelegate getLandingTourType method");

        List<LandingTour> tourList = null;
        try {
            tourList = tourDao.getLandingTourType(country);
        } catch (Exception e) {
            throw new EndUserException();
        }

        HashSet<String> tourTypeSet = new HashSet<String>();
        for (LandingTour tourType : tourList) {
            tourTypeSet.add(tourType.getTourType());
        }

        List<TourType> tourTypeList = new ArrayList<TourType>();
        int i = 0;
        for (String set : tourTypeSet) {
            TourType tourTypeObject = new TourType(i, set);
            tourTypeList.add(tourTypeObject);
            i++;
        }
        return tourTypeList;
    }

    public List<Tour> getBookedTour(String emailAddress) {
        logger.info("Entered TourDelegate getBookedTour method");

        List<Tour> tours = null;
        try {
            tours = tourDao.getBookedTour(emailAddress);
        } catch (Exception e) {
            throw new EndUserException();
        }

        for (Tour tour : tours) {
            String operatorId = tour.getOperatorId();
            if (operatorId != null) {
                List<TourOperator> tourOperator = tourOperatorDelegate.getTourOperator("operator_id", operatorId);
                if (tourOperator.size() == 1) {
                    tour.setTourOperator(tourOperator.get(0));
                }
            }
        }
        return tours;
    }

    public TourTransaction bookTour(TourTransaction transaction) {
        logger.info("Entered TourDelegate bookTour method");
        TourTransaction tourTransactionObject = null;

        try {
            tourTransactionObject = tourDao.bookTour(transaction);
        } catch (Exception e) {
            throw new EndUserException();
        }
        return tourTransactionObject;
    }

    public SimplifyPayment savePayment(SimplifyPayment simplifyPayment) {
        logger.info("Entered TourDelegate savePayment method");
        SimplifyPayment simplifyPaymentObject = null;

        try {
            simplifyPaymentObject = tourDao.savePayment(simplifyPayment);
        } catch (Exception e) {
            throw new EndUserException();
        }
        return simplifyPaymentObject;
    }

    public RefundTourist saveRefund(RefundTourist refundTourist) {
        logger.info("Entered TourDelegate saveRefund method");
        RefundTourist refundTouristObject = null;

        try {
            refundTouristObject = tourDao.saveRefund(refundTourist);
        } catch (Exception e) {
            throw new EndUserException();
        }
        return refundTouristObject;

    }

    public void updateRefundedSimplifyPaymentEntry(String paymentId, String refundDate) {
        logger.info("Entered TourDelegate updateRefundedSimplifyPaymentEntry method");

        try {
            tourDao.updateRefundedSimplifyPayment(paymentId);
            tourDao.updateTourTransaction(paymentId, refundDate);
        } catch (Exception e) {
            throw new EndUserException();
        }
    }

    public List<TourTransaction> getTourTransactions(String key, String value) {
        logger.info("Entered TourDelegate getTourTransactions method");

        List<TourTransaction> tourTransaction = null;

        try {
            tourTransaction = tourDao.getTourTransactions(key, value);
        } catch (EndUserException e) {
            throw new EndUserException();
        }
        return tourTransaction;
    }

    public CustomedTour saveCustomedTour(CustomedTour customedTour) {
        logger.info("Entered TourDelegate saveCustomedTour method");
        CustomedTour customedTourObject = null;
        try {
            customedTourObject = tourDao.saveCustomedTour(customedTour);
            if(customedTourObject != null)
                sendMailUtil.customedTourRegistered(customedTour);
        } catch (Exception e) {
            throw new EndUserException();
        }
        return customedTourObject;
    }

    public CustomedTour updateCustomedTour(CustomedTour customedTour) {
        logger.info("Entered TourDelegate updateCustomedTour method");
        CustomedTour customedTourObject = null;

        try {
            customedTourObject = tourDao.updateCustomedTour(customedTour);
        } catch (Exception e) {
            throw new EndUserException();
        }
        return customedTourObject;
    }

    public List<CustomedTour> getCustomedTour() {
        logger.info("Entered TourDelegate getCustomedTour method");
        List<CustomedTour> customedTour = null;

        try {
            customedTour = tourDao.getCustomedTour();
        } catch (Exception e) {
            throw new EndUserException();
        }
        return customedTour;
    }

    public TourRoute getTourRoute(String tourId) {
        logger.info("Entered TourDelegate getTourRoute method");
        List<TourRoute> tourRouteList = null;

        try {
            tourRouteList = tourDao.getTourRoute(tourId);
        } catch (Exception e) {
            throw new EndUserException();
        }
        if (tourRouteList != null && tourRouteList.size() == 1) {
            return tourRouteList.get(0);
        } else
            return null;
    }

    public TourRoute saveTourRoute(TourRoute tourRoute, boolean editFlag) {
        logger.info("Entered TourDelegate saveTourRoute method");

        TourRoute tourRouteObject = null;
        try {
            if(editFlag){
                List<TourRoute> tourRouteVar = tourDao.getTourRoute(tourRoute.getTourId());
                if(tourRouteVar.size() == 1){
                    tourRoute.setTourRoute_sequence_num(tourRouteVar.get(0).getTourRoute_sequence_num());
                    tourRouteObject = tourDao.saveTourRoute(tourRoute, editFlag);
                }else{
                    throw new EndUserException();
                }   
            }
            else{
                tourRouteObject = tourDao.saveTourRoute(tourRoute, editFlag);
            }
        } catch (Exception e) {
            logger.error("Exception throwen from TourDelegate saveTourRoute method" + e);
            throw new EndUserException();
        }
        return tourRouteObject;
    }

    public void deleteTourRoute(String tourId) {
        logger.info("Entered TourDelegate deleteTourRoute method");

        try {
            tourDao.deleteTourRoute(tourId);
        } catch (Exception e) {
            throw new EndUserException();
        }
    }
}
