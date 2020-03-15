package org.enduser.service.util;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.log4j.Logger;
import org.enduser.service.delegates.TourDelegate;
import org.enduser.service.delegates.TourPriceDelegate;
import org.enduser.service.exception.EndUserException;
import org.enduser.service.model.CustomedTour;
import org.enduser.service.model.Group1Price;
import org.enduser.service.model.Group2Price;
import org.enduser.service.model.Group3Price;
import org.enduser.service.model.Group4Price;
import org.enduser.service.model.Group5Price;
import org.enduser.service.model.GroupSeasonCast;
import org.enduser.service.model.LandingTour;
import org.enduser.service.model.Tour;
import org.enduser.service.model.TourRoute;
import org.enduser.service.model.util.TourRegistration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TourServiceUtil {
    private static final Logger logger = Logger.getLogger(TourServiceUtil.class);

    private TourPriceDelegate tourPriceDelegate;
    private TourDelegate tourDelegate;
    private TouristServiceUtil touristServiceUtil;
    private SendMailUtil sendMailUtil;

    @Autowired
    public void setTourPriceDelegate(TourPriceDelegate tourPriceDelegate) {
        this.tourPriceDelegate = tourPriceDelegate;
    }

    @Autowired
    public void setTourDelegate(TourDelegate tourDelegate) {
        this.tourDelegate = tourDelegate;
    }

    @Autowired
    public void setTouristServiceUtil(TouristServiceUtil touristServiceUtil) {
        this.touristServiceUtil = touristServiceUtil;
    }

    @Autowired
    public void setSendMailUtil(SendMailUtil sendMailUtil) {
        this.sendMailUtil = sendMailUtil;
    }
    

    public int getGroupSize(GroupSeasonCast groupSeasonCast, Group1Price tourPrice, Date tripDate) {
        if (tripDate.after(groupSeasonCast.getSeason1StartDate()) && tripDate.before(groupSeasonCast.getSeason2StartDate())) {
            return 1;
        } else if (tripDate.after(groupSeasonCast.getSeason2StartDate())
                && tripDate.before(groupSeasonCast.getSeason3StartDate())) {
            return 2;
        } else if (tripDate.after(groupSeasonCast.getSeason3StartDate())
                && tripDate.before(groupSeasonCast.getSeason4StartDate())) {
            return 3;
        } else if (tripDate.after(groupSeasonCast.getSeason4StartDate())
                && tripDate.before(groupSeasonCast.getSeason5StartDate())) {
            return 4;
        } else if (tripDate.after(groupSeasonCast.getSeason5StartDate()) && tripDate.before(groupSeasonCast.getSeason5EndDate())) {
            return 5;
        } else
            return 0;
    }

    public double getPrice(GroupSeasonCast groupSeasonCast, Group1Price tourPrice, Date tripDate) {
        switch (getGroupSize(groupSeasonCast, tourPrice, tripDate)) {
            case 1:
                return tourPrice.getG1S1();
            case 2:
                return tourPrice.getG1S2();
            case 3:
                return tourPrice.getG1S3();
            case 4:
                return tourPrice.getG1S4();
            case 5:
                return tourPrice.getG1S5();
            default:
                throw new EndUserException();
        }
    }

    public double getThisTourPrice(String operatorId, String tourId, boolean inclusiveFlag, Date tripDate, int groupSize) {
        logger.info("Entered TourServiceUtil getThisTourPrice method");
        GroupSeasonCast groupSeasonCast = new GroupSeasonCast();
        groupSeasonCast = tourPriceDelegate.getGroupSeason(operatorId);

        if (groupSeasonCast != null) {
            if (groupSeasonCast.getG1MinSize() > groupSize) {
                try {
                    List<Group1Price> tourPrice = tourPriceDelegate.getGroup1Price(tourId, inclusiveFlag);
                    if (tourPrice != null && tourPrice.size() == 1) {
                        return getPrice(groupSeasonCast, tourPrice.get(0), tripDate);
                    }
                    return 0.00;
                } catch (Exception e) {
                    logger.error("TourServiceUtil getThisTourPrice method throw exception " + e );
                    throw new EndUserException();
                }
            } 
            else if (groupSeasonCast.getG1MinSize() <= groupSize && groupSeasonCast.getG2MinSize() > groupSize) {
                try {
                    List<Group1Price> tourPrice = tourPriceDelegate.getGroup1Price(tourId, inclusiveFlag);
                    if (tourPrice != null && tourPrice.size() == 1) {
                        return getPrice(groupSeasonCast, tourPrice.get(0), tripDate);
                    }
                    return 0.00;
                } catch (Exception e) {
                    logger.error("TourServiceUtil getThisTourPrice method throw exception " + e );
                    throw new EndUserException();
                }
            } else if (groupSeasonCast.getG2MinSize() <= groupSize && groupSeasonCast.getG3MinSize() > groupSize) {
                try {
                    List<Group2Price> tourPrice = tourPriceDelegate.getGroup2Price(tourId, inclusiveFlag);
                    if (tourPrice != null && tourPrice.size() == 1) {
                        Group1Price tourPriceObject = getGroup2Price(tourPrice.get(0));
                        return getPrice(groupSeasonCast, tourPriceObject, tripDate);
                    }
                    return 0.00;
                } catch (Exception e) {
                    logger.error("TourServiceUtil getThisTourPrice method throw exception " + e );
                    throw new EndUserException();
                }
            } else if (groupSeasonCast.getG3MinSize() <= groupSize && groupSeasonCast.getG4MinSize() > groupSize) {
                try {
                    List<Group3Price> tourPrice = tourPriceDelegate.getGroup3Price(tourId, inclusiveFlag);
                    if (tourPrice != null && tourPrice.size() == 1) {
                        Group1Price tourPriceObject = getGroup3Price(tourPrice.get(0));
                        return getPrice(groupSeasonCast, tourPriceObject, tripDate);
                    }
                    return 0.00;
                } catch (Exception e) {
                    logger.error("TourServiceUtil getThisTourPrice method throw exception " + e );
                    throw new EndUserException();
                }

            } else if (groupSeasonCast.getG4MinSize() <= groupSize && groupSeasonCast.getG5MinSize() > groupSize) {
                try {
                    List<Group4Price> tourPrice = tourPriceDelegate.getGroup4Price(tourId, inclusiveFlag);
                    if (tourPrice != null && tourPrice.size() == 1) {
                        Group1Price tourPriceObject = getGroup4Price(tourPrice.get(0));
                        return getPrice(groupSeasonCast, tourPriceObject, tripDate);
                    }
                    return 0.00;
                } catch (Exception e) {
                    logger.error("TourServiceUtil getThisTourPrice method throw exception " + e );
                    throw new EndUserException();
                }
            } else if (groupSeasonCast.getG5MinSize() <= groupSize) {
                try {
                    List<Group5Price> tourPrice = tourPriceDelegate.getGroup5Price(tourId, inclusiveFlag);
                    if (tourPrice != null && tourPrice.size() == 1) {
                        Group1Price tourPriceObject = getGroup5Price(tourPrice.get(0));
                        return getPrice(groupSeasonCast, tourPriceObject, tripDate);
                    }
                    return 0.00;
                } catch (Exception e) {
                    logger.error("TourServiceUtil getThisTourPrice method throw exception " + e );
                    throw new EndUserException();
                }
            } else {
                return 0.00;
            }
        }
        return 0.00;
    }

    public Group1Price getTourPrice(String operatorId, String tourId, Date tripDate, boolean inclusiveFlag) {
        GroupSeasonCast groupSeasonCast = new GroupSeasonCast();
        groupSeasonCast = tourPriceDelegate.getGroupSeason(operatorId);

        if (groupSeasonCast != null) {
            if (tripDate.after(groupSeasonCast.getSeason1StartDate()) && tripDate.before(groupSeasonCast.getSeason2StartDate())) {
                try {
                    List<Group1Price> tourPrice = tourPriceDelegate.getGroup1Price(tourId, inclusiveFlag);
                    if (tourPrice != null && tourPrice.size() == 1) {
                        return tourPrice.get(0);
                    }
                } catch (Exception e) {
                    throw new EndUserException();
                }
                return null;
            } else if (tripDate.after(groupSeasonCast.getSeason2StartDate())
                    && tripDate.before(groupSeasonCast.getSeason3StartDate())) {
                try {
                    List<Group2Price> tourPrice = tourPriceDelegate.getGroup2Price(tourId, inclusiveFlag);
                    if (tourPrice != null && tourPrice.size() == 1) {
                        return getGroup2Price(tourPrice.get(0));
                    }
                } catch (Exception e) {
                    throw new EndUserException();
                }
                return null;
            } else if (tripDate.after(groupSeasonCast.getSeason3StartDate())
                    && tripDate.before(groupSeasonCast.getSeason4StartDate())) {
                try {
                    List<Group3Price> tourPrice = tourPriceDelegate.getGroup3Price(tourId, inclusiveFlag);
                    if (tourPrice != null && tourPrice.size() == 1) {
                        return getGroup3Price(tourPrice.get(0));
                    }
                } catch (Exception e) {
                    throw new EndUserException();
                }
                return null;

            } else if (tripDate.after(groupSeasonCast.getSeason4StartDate())
                    && tripDate.before(groupSeasonCast.getSeason5StartDate())) {
                try {
                    List<Group4Price> tourPrice = tourPriceDelegate.getGroup4Price(tourId, inclusiveFlag);
                    if (tourPrice != null && tourPrice.size() == 1) {
                        return getGroup4Price(tourPrice.get(0));
                    }
                } catch (Exception e) {
                    throw new EndUserException();
                }
                return null;
            } else if (tripDate.after(groupSeasonCast.getSeason5StartDate())
                    && tripDate.before(groupSeasonCast.getSeason5EndDate())) {
                try {
                    List<Group5Price> tourPrice = tourPriceDelegate.getGroup5Price(tourId, inclusiveFlag);
                    if (tourPrice != null && tourPrice.size() == 1) {
                        return getGroup5Price(tourPrice.get(0));
                    }
                } catch (Exception e) {
                    throw new EndUserException();
                }
                return null;
            } else {
                return null;
            }
        }
        return null;
    }

    public void deleteTourPrice(String operatorId, String tourId, Date tripDate, boolean inclusiveFlag) {
        GroupSeasonCast groupSeasonCast = new GroupSeasonCast();
        groupSeasonCast = tourPriceDelegate.getGroupSeason(operatorId);

        if (groupSeasonCast != null) {
            if (tripDate.after(groupSeasonCast.getSeason1StartDate()) && tripDate.before(groupSeasonCast.getSeason2StartDate())) {
                try {
                    List<Group1Price> tourPrice = tourPriceDelegate.getGroup1Price(tourId, inclusiveFlag);
                    if (tourPrice != null && tourPrice.size() == 1) {
                        tourPriceDelegate.deleteGroup1Price(operatorId, inclusiveFlag);
                    } else
                        throw new EndUserException();
                } catch (Exception e) {
                    throw new EndUserException();
                }
            } else if (tripDate.after(groupSeasonCast.getSeason2StartDate())
                    && tripDate.before(groupSeasonCast.getSeason3StartDate())) {
                try {
                    List<Group2Price> tourPrice = tourPriceDelegate.getGroup2Price(tourId, inclusiveFlag);
                    if (tourPrice != null && tourPrice.size() == 1) {
                        tourPriceDelegate.deleteGroup2Price(operatorId, inclusiveFlag);
                    } else
                        throw new EndUserException();
                } catch (Exception e) {
                    throw new EndUserException();
                }
            } else if (tripDate.after(groupSeasonCast.getSeason3StartDate())
                    && tripDate.before(groupSeasonCast.getSeason4StartDate())) {
                try {
                    List<Group3Price> tourPrice = tourPriceDelegate.getGroup3Price(tourId, inclusiveFlag);
                    if (tourPrice != null && tourPrice.size() == 1) {
                        tourPriceDelegate.deleteGroup3Price(operatorId, inclusiveFlag);
                    } else
                        throw new EndUserException();
                } catch (Exception e) {
                    throw new EndUserException();
                }

            } else if (tripDate.after(groupSeasonCast.getSeason4StartDate())
                    && tripDate.before(groupSeasonCast.getSeason5StartDate())) {
                try {
                    List<Group4Price> tourPrice = tourPriceDelegate.getGroup4Price(tourId, inclusiveFlag);
                    if (tourPrice != null && tourPrice.size() == 1) {
                        tourPriceDelegate.deleteGroup4Price(operatorId, inclusiveFlag);
                    } else
                        throw new EndUserException();
                } catch (Exception e) {
                    throw new EndUserException();
                }
            } else if (tripDate.after(groupSeasonCast.getSeason5StartDate())
                    && tripDate.before(groupSeasonCast.getSeason5EndDate())) {
                try {
                    List<Group5Price> tourPrice = tourPriceDelegate.getGroup5Price(tourId, inclusiveFlag);
                    if (tourPrice != null && tourPrice.size() == 1) {
                        tourPriceDelegate.deleteGroup5Price(operatorId, inclusiveFlag);
                    } else
                        throw new EndUserException();
                } catch (Exception e) {
                    throw new EndUserException();
                }
            } else {
                throw new EndUserException();
            }
        }
    }

    public void saveTourObject(TourRegistration tourRegistration, boolean editFlag) {
        LandingTour landingTour = new LandingTour();
        TourRoute tourRoute = new TourRoute();
        Group1Price group1Price = new Group1Price();
        Group2Price group2Price = new Group2Price();
        Group3Price group3Price = new Group3Price();
        Group4Price group4Price = new Group4Price();
        Group5Price group5Price = new Group5Price();

        Group1Price group1Price_1 = new Group1Price();
        Group2Price group2Price_1 = new Group2Price();
        Group3Price group3Price_1 = new Group3Price();
        Group4Price group4Price_1 = new Group4Price();
        Group5Price group5Price_1 = new Group5Price();

        try {
            landingTour = setLandingTour(tourRegistration, landingTour);
            landingTour.setRegistrationDate(touristServiceUtil.getDateTime());
            tourDelegate.saveLandingTour(landingTour, editFlag);
            tourRoute = setTourRoute(tourRegistration, tourRoute);
            tourDelegate.saveTourRoute(tourRoute, editFlag);

            group1Price = setInclusiveGroup1Price(tourRegistration, group1Price);
            tourPriceDelegate.saveGroup1Price(group1Price, editFlag);
            group2Price = setInclusiveGroup2Price(tourRegistration, group2Price);
            tourPriceDelegate.saveGroup2Price(group2Price, editFlag);
            group3Price = setInclusiveGroup3Price(tourRegistration, group3Price);
            tourPriceDelegate.saveGroup3Price(group3Price, editFlag);
            group4Price = setInclusiveGroup4Price(tourRegistration, group4Price);
            tourPriceDelegate.saveGroup4Price(group4Price, editFlag);
            group5Price = setInclusiveGroup5Price(tourRegistration, group5Price);
            tourPriceDelegate.saveGroup5Price(group5Price, editFlag);
            
            if(editFlag){
                sendMailUtil.TourEditEmail(landingTour.getCountry(), landingTour.getOperatorId(), landingTour.getTourId());
            }

//            group1Price_1 = setExclusiveGroup1Price(tourRegistration, group1Price);
//            tourPriceDelegate.saveGroup1Price(group1Price_1, editFlag);
//            group2Price_1 = setExclusiveGroup2Price(tourRegistration, group2Price);
//            tourPriceDelegate.saveGroup2Price(group2Price_1, editFlag);
//            group3Price_1 = setExclusiveGroup3Price(tourRegistration, group3Price);
//            tourPriceDelegate.saveGroup3Price(group3Price_1);
//            group4Price_1 = setExclusiveGroup4Price(tourRegistration, group4Price);
//            tourPriceDelegate.saveGroup4Price(group4Price_1);
//            group5Price_1 = setExclusiveGroup5Price(tourRegistration, group5Price);
//            tourPriceDelegate.saveGroup5Price(group5Price_1);
            
        } catch (Exception e) {
            logger.error("TourServiceUtil saveTourObject method throw exception ", e);
            boolean activeTourFlag = false;
            if(!editFlag){
                if (landingTour.getTourId() != null) {
                    deleteTourObject(landingTour, activeTourFlag);
                }
            }
            throw new EndUserException(e);
        }
    }

    public void deleteTourObject(LandingTour tour, boolean activeTourFlag) {
        tourDelegate.deleteTour(tour);
        tourDelegate.deleteTourRoute(tour.getTourId());
        tourPriceDelegate.deleteTourPrice(tour.getTourId(), "Group1Price");
        tourPriceDelegate.deleteTourPrice(tour.getTourId(), "Group2Price");
        tourPriceDelegate.deleteTourPrice(tour.getTourId(), "Group3Price");
        tourPriceDelegate.deleteTourPrice(tour.getTourId(), "Group4Price");
        tourPriceDelegate.deleteTourPrice(tour.getTourId(), "Group5Price");
        if(activeTourFlag){
            sendMailUtil.deleteTourEmail(tour.getCountry(), tour.getOperatorId(), tour.getTourId(), "Landing Tour");
        }
    }
    
    public void deleteTourObject(Tour tour, boolean activeTourFlag) {
        tourDelegate.deleteTour(tour);
        tourDelegate.deleteTourRoute(tour.getTourId());
        tourPriceDelegate.deleteTourPrice(tour.getTourId(), "Group1Price");
        tourPriceDelegate.deleteTourPrice(tour.getTourId(), "Group2Price");
        tourPriceDelegate.deleteTourPrice(tour.getTourId(), "Group3Price");
        tourPriceDelegate.deleteTourPrice(tour.getTourId(), "Group4Price");
        tourPriceDelegate.deleteTourPrice(tour.getTourId(), "Group5Price");
        if(activeTourFlag){
            sendMailUtil.deleteTourEmail(tour.getCountry(), tour.getOperatorId(), tour.getTourId(), "Tour");
        }
    }

    public Group1Price getGroup2Price(Group2Price group2Price) {
        Group1Price group1Price = new Group1Price();

        group1Price.setG1S1(group2Price.getG2S1());
        group1Price.setG1S2(group2Price.getG2S2());
        group1Price.setG1S3(group2Price.getG2S3());
        group1Price.setG1S4(group2Price.getG2S4());
        group1Price.setG1S5(group2Price.getG2S5());
        group1Price.setTourId(group2Price.getTourId());
        group1Price.setOperatorId(group2Price.getOperatorId());
        group1Price.setInclusiveFlag(group2Price.isInclusiveFlag());

        return group1Price;
    }

    public Group1Price getGroup3Price(Group3Price group3Price) {
        Group1Price group1Price = new Group1Price();

        group1Price.setG1S1(group3Price.getG3S1());
        group1Price.setG1S2(group3Price.getG3S2());
        group1Price.setG1S3(group3Price.getG3S3());
        group1Price.setG1S4(group3Price.getG3S4());
        group1Price.setG1S5(group3Price.getG3S5());
        group1Price.setTourId(group3Price.getTourId());
        group1Price.setOperatorId(group3Price.getOperatorId());
        group1Price.setInclusiveFlag(group3Price.isInclusiveFlag());

        return group1Price;
    }

    public Group1Price getGroup4Price(Group4Price group4Price) {
        Group1Price group1Price = new Group1Price();

        group1Price.setG1S1(group4Price.getG4S1());
        group1Price.setG1S2(group4Price.getG4S2());
        group1Price.setG1S3(group4Price.getG4S3());
        group1Price.setG1S4(group4Price.getG4S4());
        group1Price.setG1S5(group4Price.getG4S5());
        group1Price.setTourId(group4Price.getTourId());
        group1Price.setOperatorId(group4Price.getOperatorId());
        group1Price.setInclusiveFlag(group4Price.isInclusiveFlag());

        return group1Price;
    }

    public Group1Price getGroup5Price(Group5Price group5Price) {
        Group1Price group1Price = new Group1Price();

        group1Price.setG1S1(group5Price.getG5S1());
        group1Price.setG1S2(group5Price.getG5S2());
        group1Price.setG1S3(group5Price.getG5S3());
        group1Price.setG1S4(group5Price.getG5S4());
        group1Price.setG1S5(group5Price.getG5S5());
        group1Price.setTourId(group5Price.getTourId());
        group1Price.setOperatorId(group5Price.getOperatorId());
        group1Price.setInclusiveFlag(group5Price.isInclusiveFlag());

        return group1Price;
    }

    public TourRoute setTourRoute(CustomedTour customedTour) {
        TourRoute tourRoute = new TourRoute();
        tourRoute.setTourId("Cust_" + RandomStringUtils.randomAlphanumeric(10));
        tourRoute.setTourRoute("Customed");
        tourRoute.setTourDescription(customedTour.getTourDescription());
        return tourRoute;
    }

    public LandingTour setLandingTour(TourRegistration tourRegistration, LandingTour tour) {

        tour.setOperatorId(tourRegistration.getOperatorId());
        tour.setTourDuration(tourRegistration.getTourDuration());
        tour.setTourEndDate(tourRegistration.getTourEndDate());
        tour.setTourId(tourRegistration.getTourId());
        tour.setTourMaxSize(tourRegistration.getTourMaxSize());
        tour.setTourMinSize(tourRegistration.getTourMinSize());
        tour.setTourStartDate(tourRegistration.getTourStartDate());
        tour.setTourStartPlace(tourRegistration.getTourStartPlace());
        tour.setTourStartTime(tourRegistration.getTourStartTime());
        tour.setTourType(tourRegistration.getTourType());
        tour.setTourRoute(tourRegistration.getTourRoute());
        tour.setCountry(tourRegistration.getCountry());
        
        tour.setServiceLevel(tourRegistration.getServiceLevel());
        tour.setMinAge(tourRegistration.getMinAge());
        tour.setEndingPlace(tourRegistration.getEndingPlace());
        tour.setTransportation(tourRegistration.getTransportation());
        tour.setPhysicalDemand(tourRegistration.getPhysicalDemand());
        tour.setWithGuide(tourRegistration.isWithGuide());
        
        return tour;
    }

    public TourRoute setTourRoute(TourRegistration tourRegistration, TourRoute tourRoute) {

        tourRoute.setTourDescription(tourRegistration.getTourDescription());
        tourRoute.setTourId(tourRegistration.getTourId());
        tourRoute.setTourRoute(tourRegistration.getTourRoute());
        return tourRoute;

    }

    // save exclusive price
    public Group1Price setInclusiveGroup1Price(TourRegistration tourRegistration, Group1Price group1Price) {

        group1Price.setG1S1(tourRegistration.getInclusive_g1S1());
        group1Price.setG1S2(tourRegistration.getInclusive_g1S2());
        group1Price.setG1S3(tourRegistration.getInclusive_g1S3());
        group1Price.setG1S4(tourRegistration.getInclusive_g1S4());
        group1Price.setG1S5(tourRegistration.getInclusive_g1S5());
        group1Price.setInclusiveFlag(true);
        group1Price.setOperatorId(tourRegistration.getOperatorId());
        group1Price.setTourId(tourRegistration.getTourId());

        return group1Price;
    }

    public Group2Price setInclusiveGroup2Price(TourRegistration tourRegistration, Group2Price group2Price) {

        group2Price.setG2S1(tourRegistration.getInclusive_g2S1());
        group2Price.setG2S2(tourRegistration.getInclusive_g2S2());
        group2Price.setG2S3(tourRegistration.getInclusive_g2S3());
        group2Price.setG2S4(tourRegistration.getInclusive_g2S4());
        group2Price.setG2S5(tourRegistration.getInclusive_g2S5());
        group2Price.setInclusiveFlag(true);
        group2Price.setOperatorId(tourRegistration.getOperatorId());
        group2Price.setTourId(tourRegistration.getTourId());

        return group2Price;
    }

    public Group3Price setInclusiveGroup3Price(TourRegistration tourRegistration, Group3Price group3Price) {

        group3Price.setG3S1(tourRegistration.getInclusive_g3S1());
        group3Price.setG3S2(tourRegistration.getInclusive_g3S2());
        group3Price.setG3S3(tourRegistration.getInclusive_g3S3());
        group3Price.setG3S4(tourRegistration.getInclusive_g3S4());
        group3Price.setG3S5(tourRegistration.getInclusive_g3S5());
        group3Price.setInclusiveFlag(true);
        group3Price.setOperatorId(tourRegistration.getOperatorId());
        group3Price.setTourId(tourRegistration.getTourId());

        return group3Price;
    }

    public Group4Price setInclusiveGroup4Price(TourRegistration tourRegistration, Group4Price group4Price) {

        group4Price.setG4S1(tourRegistration.getInclusive_g4S1());
        group4Price.setG4S2(tourRegistration.getInclusive_g4S2());
        group4Price.setG4S3(tourRegistration.getInclusive_g4S3());
        group4Price.setG4S4(tourRegistration.getInclusive_g4S4());
        group4Price.setG4S5(tourRegistration.getInclusive_g4S5());
        group4Price.setInclusiveFlag(true);
        group4Price.setOperatorId(tourRegistration.getOperatorId());
        group4Price.setTourId(tourRegistration.getTourId());

        return group4Price;
    }

    public Group5Price setInclusiveGroup5Price(TourRegistration tourRegistration, Group5Price group5Price) {

        group5Price.setG5S1(tourRegistration.getInclusive_g5S1());
        group5Price.setG5S2(tourRegistration.getInclusive_g5S2());
        group5Price.setG5S3(tourRegistration.getInclusive_g5S3());
        group5Price.setG5S4(tourRegistration.getInclusive_g5S4());
        group5Price.setG5S5(tourRegistration.getInclusive_g5S5());
        group5Price.setInclusiveFlag(true);
        group5Price.setOperatorId(tourRegistration.getOperatorId());
        group5Price.setTourId(tourRegistration.getTourId());

        return group5Price;
    }


    // save exclusive price
    public Group1Price setExclusiveGroup1Price(TourRegistration tourRegistration, Group1Price group1Price) {
        group1Price.setG1S1(tourRegistration.getExclusive_g1S1());
        group1Price.setG1S2(tourRegistration.getExclusive_g1S2());
        group1Price.setG1S3(tourRegistration.getExclusive_g1S3());
        group1Price.setG1S4(tourRegistration.getExclusive_g1S4());
        group1Price.setG1S5(tourRegistration.getExclusive_g1S5());
        group1Price.setInclusiveFlag(false);
        group1Price.setOperatorId(tourRegistration.getOperatorId());
        group1Price.setTourId(tourRegistration.getTourId());

        return group1Price;
    }

    public Group2Price setExclusiveGroup2Price(TourRegistration tourRegistration, Group2Price group2Price) {
        group2Price.setG2S1(tourRegistration.getExclusive_g2S1());
        group2Price.setG2S2(tourRegistration.getExclusive_g2S2());
        group2Price.setG2S3(tourRegistration.getExclusive_g2S3());
        group2Price.setG2S4(tourRegistration.getExclusive_g2S4());
        group2Price.setG2S5(tourRegistration.getExclusive_g2S5());
        group2Price.setInclusiveFlag(false);
        group2Price.setOperatorId(tourRegistration.getOperatorId());
        group2Price.setTourId(tourRegistration.getTourId());

        return group2Price;
    }

    public Group3Price setExclusiveGroup3Price(TourRegistration tourRegistration, Group3Price group3Price) {
        group3Price.setG3S1(tourRegistration.getExclusive_g3S1());
        group3Price.setG3S2(tourRegistration.getExclusive_g3S2());
        group3Price.setG3S3(tourRegistration.getExclusive_g3S3());
        group3Price.setG3S4(tourRegistration.getExclusive_g3S4());
        group3Price.setG3S5(tourRegistration.getExclusive_g3S5());
        group3Price.setInclusiveFlag(false);
        group3Price.setOperatorId(tourRegistration.getOperatorId());
        group3Price.setTourId(tourRegistration.getTourId());

        return group3Price;
    }

    public Group4Price setExclusiveGroup4Price(TourRegistration tourRegistration, Group4Price group4Price) {
        group4Price.setG4S1(tourRegistration.getExclusive_g4S1());
        group4Price.setG4S2(tourRegistration.getExclusive_g4S2());
        group4Price.setG4S3(tourRegistration.getExclusive_g4S3());
        group4Price.setG4S4(tourRegistration.getExclusive_g4S4());
        group4Price.setG4S5(tourRegistration.getExclusive_g4S5());
        group4Price.setInclusiveFlag(false);
        group4Price.setOperatorId(tourRegistration.getOperatorId());
        group4Price.setTourId(tourRegistration.getTourId());

        return group4Price;
    }

    public Group5Price setExclusiveGroup5Price(TourRegistration tourRegistration, Group5Price group5Price) {
        group5Price.setG5S1(tourRegistration.getExclusive_g5S1());
        group5Price.setG5S2(tourRegistration.getExclusive_g5S2());
        group5Price.setG5S3(tourRegistration.getExclusive_g5S3());
        group5Price.setG5S4(tourRegistration.getExclusive_g5S4());
        group5Price.setG5S5(tourRegistration.getExclusive_g5S5());
        group5Price.setInclusiveFlag(false);
        group5Price.setOperatorId(tourRegistration.getOperatorId());
        group5Price.setTourId(tourRegistration.getTourId());

        return group5Price;
    }

}
