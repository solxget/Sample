package org.enduser.service.services.impl;

import java.security.Principal;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.enduser.service.delegates.TourDelegate;
import org.enduser.service.delegates.TourPriceDelegate;
import org.enduser.service.delegates.UserGenericServiceDelegate;
import org.enduser.service.exception.EndUserException;
import org.enduser.service.model.CustomedTour;
import org.enduser.service.model.Group1Price;
import org.enduser.service.model.Group2Price;
import org.enduser.service.model.Group3Price;
import org.enduser.service.model.Group4Price;
import org.enduser.service.model.Group5Price;
import org.enduser.service.model.GroupSeasonCast;
import org.enduser.service.model.LandingTour;
import org.enduser.service.model.SimplifyPayment;
import org.enduser.service.model.Tour;
import org.enduser.service.model.TourRoute;
import org.enduser.service.model.TourTourOperator_C;
import org.enduser.service.model.TourTransaction;
import org.enduser.service.model.TourType;
import org.enduser.service.model.util.ImageObject;
import org.enduser.service.model.util.RefundObject;
import org.enduser.service.model.util.TourRegistration;
import org.enduser.service.services.TourService;
import org.enduser.service.services.impl.util.Role;
import org.enduser.service.services.impl.util.Secured;
import org.enduser.service.util.DateConstants;
import org.enduser.service.util.EndUserConstants;
import org.enduser.service.util.ImageUtil;
import org.enduser.service.util.SendMailUtil;
import org.enduser.service.util.TourServiceUtil;
import org.enduser.service.util.TourTransactionUtil;
import org.enduser.service.util.TouristServiceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.simplify.payments.domain.Payment;
import com.simplify.payments.domain.Refund;

@Component
@Path("/tour")
public class TourServiceImpl implements TourService {
    private static final Logger logger = Logger.getLogger(TourServiceImpl.class);

    @Context
    SecurityContext securityContext;

    private TourDelegate tourDelegate;
    private TouristServiceUtil touristServiceUtil;
    private TourTransactionUtil tourTransactionUtil;
    private SendMailUtil sendMailUtil;
    private DateConstants dateConstants;
    private TourServiceUtil tourServiceUtil;
    private TourPriceDelegate tourPriceDelegate;
    private UserGenericServiceDelegate userGenericServiceDelegate;
    private ImageUtil imageUtil;

    @Autowired
    public void setTourDelegate(TourDelegate tourDelegate) {
        this.tourDelegate = tourDelegate;
    }

    @Autowired
    public void setTouristServiceUtil(TouristServiceUtil touristServiceUtil) {
        this.touristServiceUtil = touristServiceUtil;
    }

    @Autowired
    public void setTourTransactionUtil(TourTransactionUtil tourTransactionUtil) {
        this.tourTransactionUtil = tourTransactionUtil;
    }

    @Autowired
    public void setSendMailUtil(SendMailUtil sendMailUtil) {
        this.sendMailUtil = sendMailUtil;
    }

    @Autowired
    public void setDateConstants(DateConstants dateConstants) {
        this.dateConstants = dateConstants;
    }

    @Autowired
    public void setTourServiceUtil(TourServiceUtil tourServiceUtil) {
        this.tourServiceUtil = tourServiceUtil;
    }

    @Autowired
    public void setTourPriceDelegate(TourPriceDelegate tourPriceDelegate) {
        this.tourPriceDelegate = tourPriceDelegate;
    }

    @Autowired
    public void setUserGenericServiceDelegate(UserGenericServiceDelegate userGenericServiceDelegate) {
        this.userGenericServiceDelegate = userGenericServiceDelegate;
    }
    
    @Autowired
    public void setImageUtil(ImageUtil imageUtil) {
        this.imageUtil = imageUtil;
    }
    

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/get")
    public List<Tour> getTour(@QueryParam("key") String key, @QueryParam("value") String value,
            @QueryParam("inclusiveFlag") Boolean inclusiveFlag, @QueryParam("tripDate") String tripDate,
            @QueryParam("tripDuration") int tripDuration, @QueryParam("groupSize") int groupSize,
            @QueryParam("country") String country) throws EndUserException {
        logger.info("Entering TourServiceImpl getTour method");

        try {
            List<Tour> tourList = tourDelegate.getTour(key, value, inclusiveFlag, tripDate, tripDuration, groupSize, country);
            return tourList;
        } catch (Exception e) {
            logger.error("Exception throwen from TourServiceImpl getTour", e);
            throw new EndUserException(EndUserConstants.HttpErrorCode_450, e.getMessage());
        }
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/operatorid")
    public List<Tour> getTourByOperatorId(@QueryParam("operatorId") String operatorId) throws EndUserException {
        logger.info("Entering TourServiceImpl getTourByOperatorId method");

        try {
            return tourDelegate.getTourByOperatorId(operatorId);
        } catch (Exception e) {
            logger.error("Exception throwen from TourServiceImpl getTourByOperatorId", e);
            throw new EndUserException(EndUserConstants.HttpErrorCode_450, e.getMessage());
        }
    }
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/country")
    public List<Tour> getTourByCountry(@QueryParam("country") String country) throws EndUserException {
        logger.info("Entering TourServiceImpl getTourByCountry method");

        try {
            return tourDelegate.getTourByCountry(country);
        } catch (Exception e) {
            logger.error("Exception throwen from TourServiceImpl getTourByCountry", e);
            throw new EndUserException(EndUserConstants.HttpErrorCode_450, e.getMessage());
        }
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/advert")
    public List<Tour> getTourForAdvert(@QueryParam("country") String country) throws EndUserException {
        logger.info("Entering TourServiceImpl getTourByForAdvert method");

        try {
            return tourDelegate.getTourForAdvert(country);
        } catch (Exception e) {
            logger.error("Exception throwen from TourServiceImpl getTourByForAdvert", e);
            throw new EndUserException(EndUserConstants.HttpErrorCode_450, e.getMessage());
        }
    }
    
    @GET
    @Secured({Role.SuperAdmin, Role.Admin, Role.Operator})
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/mytours")
    public List<LandingTour> getMyTours() throws EndUserException {
        logger.info("Entering TourServiceImpl getMyTours method");
        Principal principal = securityContext.getUserPrincipal();

        if (principal != null) {
            try {
                return tourDelegate.getMyTours(principal.getName());
            } catch (Exception e) {
                logger.error("Exception throwen from TourServiceImpl getMyTours", e);
                throw new EndUserException(EndUserConstants.HttpErrorCode_450, e.getMessage());
            }
        } else
            return null;
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/getbyid")
    public Tour getTourById(@QueryParam("tourId") String tourId) throws EndUserException { // ReturningLisInsteadOfArray
        logger.info("Entering TourServiceImpl getTourById method");

        try {
            Tour tour = tourDelegate.getTourById(tourId);
            return tour;
        } catch (Exception e) {
            logger.error("Exception throwen from TourServiceImpl getTourById", e);
            throw new EndUserException(EndUserConstants.HttpErrorCode_450, e.getMessage());
        }
    }

    @GET
    @Secured({Role.SuperAdmin, Role.Admin, Role.Operator})
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/gettouredit")
    public LandingTour getTourForEdit(@QueryParam("tourId") String tourId) throws EndUserException { // ReturningLisInsteadOfArray
        logger.info("Entering TourServiceImpl getTourForEdit method");

        try {
            LandingTour tour = tourDelegate.getTourForEdit(tourId);
            return tour;
        } catch (Exception e) {
            logger.error("Exception throwen from getTourForEdit getTourById", e);
            throw new EndUserException(EndUserConstants.HttpErrorCode_450, e.getMessage());
        }
    }
    
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @Path("/save")
    public CustomedTour saveCustomedTour(CustomedTour customedTour) throws EndUserException {
        logger.info("Entering TourServiceImpl saveCustomedTour method");
        boolean editFlag = false;
        
        try {
            customedTour.setRegistrationDate(touristServiceUtil.getDateTime());
            tourDelegate.saveTourRoute(tourServiceUtil.setTourRoute(customedTour), editFlag);
            // customedTour.setTourDescription(null); // WHY am i setting this null
            return tourDelegate.saveCustomedTour(customedTour);
        } catch (Exception e) {
            logger.error("Exception throwen from TourServiceImpl saveCustomedTour method", e);
            throw new EndUserException(EndUserConstants.HttpErrorCode_450, e.getMessage());
        }
    }

    @POST
    @Secured({Role.SuperAdmin, Role.Admin, Role.Operator})
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @Path("/register")
    public Tour createTour(Tour tour) throws EndUserException {
        logger.info("Entering TourServiceImpl createTour method");
        boolean tourExist = false;

        try {
            if (tourDelegate.getTourById(tour.getTourId()) != null) {
                tourExist = true;
                throw new EndUserException();
            }
            tour.setRegistrationDate(touristServiceUtil.getDateTime());
            return tourDelegate.saveTour(tour);
        } catch (Exception e) {
            logger.error("Exception throwen from TourServiceImpl createTour", e);
            if (tourExist)
                throw new EndUserException(EndUserConstants.HttpErrorCode_575, "Error!");
            throw new EndUserException(EndUserConstants.HttpErrorCode_450, e.getMessage());
        }
    }

    @POST
    @Secured({Role.SuperAdmin, Role.Admin, Role.Operator})
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @Path("/savetour")
    public TourRegistration saveTour(TourRegistration tourRegistration) throws EndUserException {
        logger.info("Entering TourServiceImpl saveTour method");
        boolean tourExist = false;
        boolean editFlag = false;
        Principal principal = securityContext.getUserPrincipal();

        try {
            if (tourDelegate.getLandingTourId("tour_id", tourRegistration.getTourId()).size() != 0) {
                tourExist = true;
                throw new EndUserException();
            }
            if (principal != null)
                tourRegistration.setOperatorId(principal.getName());
            tourServiceUtil.saveTourObject(tourRegistration, editFlag);
        } catch (Exception e) {
            logger.error("Exception throwen from TourServiceImpl saveTour", e);
            if (tourExist)
                throw new EndUserException(EndUserConstants.HttpErrorCode_575, "Error!");
            throw new EndUserException(EndUserConstants.HttpErrorCode_450, e.getMessage());
        }
        return tourRegistration;
    }
    
    @POST
    @Secured({Role.SuperAdmin, Role.Admin, Role.Operator})
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @Path("/updatetour")
    public TourRegistration updateTour(TourRegistration tourRegistration) throws EndUserException {
        logger.info("Entering TourServiceImpl updateTour method");
        boolean tourDontExist = false;
        boolean editFlag = true;
        Principal principal = securityContext.getUserPrincipal();

        try {
            if (! (tourDelegate.getLandingTourId("tour_id", tourRegistration.getTourId()).size() != 0)) {
                tourDontExist = true;
                throw new EndUserException();
            }
            if (principal != null){
                tourRegistration.setOperatorId(principal.getName());
            }
            tourServiceUtil.saveTourObject(tourRegistration, editFlag);
        } catch (Exception e) {
            logger.error("Exception throwen from TourServiceImpl updateTour", e);
            if (tourDontExist)
                throw new EndUserException(EndUserConstants.HttpErrorCode_589, "Error!");
            throw new EndUserException(EndUserConstants.HttpErrorCode_450, e.getMessage());
        }
        return tourRegistration;
    }
    
    @POST
    @Secured({Role.SuperAdmin, Role.Admin, Role.Operator})
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @Path("/savetourimage")
    public void saveTourPackageImage(ImageObject imageObject) throws EndUserException {
        logger.info("Entering TourServiceImpl saveTourPackageImage method");

        try {
            imageUtil.saveImageToS3(imageObject);
        } catch (Exception e) {
            logger.error("Exception throwen from TourServiceImpl saveTourPackageImage ", e);
            throw new EndUserException(EndUserConstants.HttpErrorCode_450, e.getMessage());
        }
    }
    
    @GET
    @Secured({Role.SuperAdmin, Role.Admin, Role.Operator})
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/tourid")
    public String getTourId(@QueryParam("operatorId") String operatorId) throws EndUserException {
        logger.info("Entering TourServiceImpl getTourId method");
        String tourId = null;
        int counter = 6;

        try {
            tourId = tourDelegate.getTourId(operatorId);
            while (tourDelegate.getLandingTourId("tour_id", tourId).size() != 0) {
                tourId = tourDelegate.getTourId(operatorId, counter);
                counter += 5;
            }
            return tourId;
        } catch (Exception e) {
            logger.error("TourServiceImpl getTourId method throw exception ", e);
            throw new EndUserException(EndUserConstants.HttpErrorCode_450, e.getMessage());
        }
    }

    @PUT
    @Secured({Role.SuperAdmin, Role.Admin})
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @Path("/update")
    public Tour updateTour(@QueryParam("key") String key, @QueryParam("value") String value, @QueryParam("tourId") String tourId)
            throws EndUserException {
        logger.info("Entering TourServiceImpl updateTour method");
        boolean exceptionFlag = false;

        try {
            List<Tour> tourObject = tourDelegate.getTourId("tour_id", tourId);
            if (tourObject.size() == 1 && StringUtils.equals(tourId, tourObject.get(0).getTourId())) {
                tourDelegate.updateTour(key, value, tourId);
                return tourDelegate.getTourId("tour_id", tourId).get(0);
            } else {
                exceptionFlag = true;
                throw new EndUserException();
            }
        } catch (Exception e) {
            logger.error("TourServiceImpl updateTour method throw exception ", e);
            if (exceptionFlag)
                throw new EndUserException(EndUserConstants.HttpErrorCode_565, "Error!");
            throw new EndUserException(EndUserConstants.HttpErrorCode_450, e.getMessage());
        }
    }

    @DELETE
    @Secured({Role.SuperAdmin, Role.Admin, Role.Operator})
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @Path("/delete")
    public void deleteTour(@QueryParam("tourId") String tourId) throws EndUserException {
        logger.info("Entering TourServiceImpl deleteTour method");
        boolean exceptionFlag = false;
        boolean activeTourFlag = true;

        try {
            List<Tour> tourObject = tourDelegate.getTourId("tour_id", tourId);
            if (tourObject.size() == 1 && StringUtils.equals(tourId, tourObject.get(0).getTourId())) {
                // tourDelegate.deleteTour(tourObject.get(0));
                tourServiceUtil.deleteTourObject(tourObject.get(0), activeTourFlag);
            } else {
                exceptionFlag = true;
                throw new EndUserException();
            }

        } catch (Exception e) {
            logger.error("TourServiceImpl deleteTour method throw exception ", e);
            if (exceptionFlag)
                throw new EndUserException(EndUserConstants.HttpErrorCode_566, "Error!");
            throw new EndUserException(EndUserConstants.HttpErrorCode_450, e.getMessage());
        }
    }

    @DELETE
    @Secured({Role.SuperAdmin, Role.Admin, Role.Operator})
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @Path("/deletetour")
    public void deleteMyTour(@QueryParam("tourId") String tourId) throws EndUserException {
        logger.info("Entering TourServiceImpl deleteMyTour method");
        boolean exceptionFlag = false;
        boolean activeTourFlag = true;
        
        try {
            List<LandingTour> tourObject = tourDelegate.getLandingTourId("tour_id", tourId);
            if (tourObject.size() == 1 && StringUtils.equals(tourId, tourObject.get(0).getTourId())) {
                // tourDelegate.deleteTour(tourObject.get(0));
                tourServiceUtil.deleteTourObject(tourObject.get(0), activeTourFlag);
            } else {
                exceptionFlag = true;
                throw new EndUserException();
            }

        } catch (Exception e) {
            logger.error("TourServiceImpl deleteMyTour method throw exception ", e);
            if (exceptionFlag)
                throw new EndUserException(EndUserConstants.HttpErrorCode_566, "Error!");
            throw new EndUserException(EndUserConstants.HttpErrorCode_450, e.getMessage());
        }
    }

    @DELETE
    @Secured({Role.SuperAdmin, Role.Admin})
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @Path("/deletecustomed")
    public void deleteCustomedTour(@QueryParam("emailAddress") String emailAddress) {
        logger.info("Entering TourServiceImpl deleteCustomedTour method");
        boolean exceptionFlag = false;

        try {
            List<CustomedTour> tourObject = tourDelegate.getCustomedTour();
            if (tourObject.size() == 1 && StringUtils.equals(emailAddress, tourObject.get(0).getEmailAddress())) {
                tourDelegate.deleteCustomedTour(emailAddress);
            } else {
                exceptionFlag = true;
                throw new EndUserException();
            }
        } catch (Exception e) {
            logger.error("TourServiceImpl deleteCustomedTour method throw exception ", e);
            if (exceptionFlag)
                throw new EndUserException(EndUserConstants.HttpErrorCode_566, "Error!");
            throw new EndUserException(EndUserConstants.HttpErrorCode_450, e.getMessage());
        }
    }

    @POST
    @Secured({Role.SuperAdmin, Role.Admin})
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @Path("/updatecustomed")
    // WHY DO I NEED THIS IF I HAVE CREATE C.TOUR
    public CustomedTour updateCustomedTour(CustomedTour customedTour) {
        logger.info("Entering TourServiceImpl updateCustomedTour method");
        // check if it exist before updating
        try {
            customedTour.setRegistrationDate(touristServiceUtil.getDateTime());
            return tourDelegate.updateCustomedTour(customedTour); // createing instead of updating
                                                                  // at the dao layer
        } catch (Exception e) {
            logger.error("Exception throwen from TourServiceImpl updateCustomedTour method", e);
            throw new EndUserException(EndUserConstants.HttpErrorCode_450, e.getMessage());
        }
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @Path("/getcustomed")
    public List<CustomedTour> getCustomedTour() {
        logger.info("Entering TourServiceImpl getCustomedTour method");

        try {
            return tourDelegate.getCustomedTour();
        } catch (Exception e) {
            logger.error("Exception throwen from TourServiceImpl getCustomedTour", e);
            throw new EndUserException(EndUserConstants.HttpErrorCode_450, e.getMessage());
        }
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @Path("/tourdetails")
    public List<TourTourOperator_C> getToursByType(@QueryParam("tourType") String tourType) throws EndUserException {
        logger.info("Entering TourServiceImpl getToursByType method");

        try {
            return tourDelegate.getToursByType(tourType);
        } catch (Exception e) {
            logger.error("Exception throwen from TourServiceImpl getToursByType", e);
            throw new EndUserException(EndUserConstants.HttpErrorCode_450, e.getMessage());
        }
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @Path("/tourtype")
    public List<TourType> getTourType(@QueryParam("country") String country) throws EndUserException {
        logger.info("Entering TourServiceImpl getTourType method");

        try {
            return tourDelegate.getTourType(country);
        } catch (Exception e) {
            logger.error("TourServiceImpl getTourType method throw exception ", e);
            throw new EndUserException(EndUserConstants.HttpErrorCode_450, e.getMessage());
        }
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @Path("/landingtourtype")
    public List<TourType> getLandingTourType(@QueryParam("country") String country) throws EndUserException {
        logger.info("Entering TourServiceImpl getLandingTourType method");

        try {
            return tourDelegate.getLandingTourType(country);
        } catch (Exception e) {
            logger.error("TourServiceImpl getLandingTourType method throw exception ", e);
            throw new EndUserException(EndUserConstants.HttpErrorCode_450, e.getMessage());
        }
    }


    @GET
    @Secured({Role.SuperAdmin, Role.Admin, Role.User})
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/bookedtour")
    public List<Tour> getBookedTour(@QueryParam("emailAddress") String emailAddress) throws EndUserException {
        logger.info("Entering TourServiceImpl getBookedTour method");

        try {
            return tourDelegate.getBookedTour(emailAddress);
        } catch (Exception e) {
            logger.error("TourServiceImpl getBookedTour method throw exception ", e);
            throw new EndUserException(EndUserConstants.HttpErrorCode_450, e.getMessage());
        }
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @Path("/booktour")
    public TourTransaction bookTour(TourTransaction transaction) throws EndUserException {
        logger.info("Entering TourServiceImpl bookTour method");
        Principal principal = securityContext.getUserPrincipal();
        Payment payment = null;
        boolean emailExceptionFlag = false;
        TourTransaction transactionObject = null;

        try {
            payment = tourTransactionUtil.processTransaction(transaction);
        } catch (Exception e) {
            logger.error("TourServiceImpl bookTour method throw exception ", e);
            throw new EndUserException(EndUserConstants.HttpErrorCode_450, e.getMessage());
        }
        if (payment != null) {
            try {
                SimplifyPayment simplifyPayment = tourTransactionUtil.savePayment(payment);
                if (principal != null)
                    transaction.setEmailAddress(principal.getName()); // make sure this will not mix
                                                                      // for different users when
                                                                      // multi threading
                tourTransactionUtil.setParametrs(transaction, simplifyPayment);
                transactionObject = tourDelegate.bookTour(transaction);
                emailExceptionFlag = true;
                SendMailUtil sendMailUtil = new SendMailUtil();
                sendMailUtil.bookTourEmail(transaction);
                emailExceptionFlag = false;
            } catch (Exception e) {
                logger.error("TourServiceImpl bookTour method throw exception ", e);
                // Tour is booked and payment is processed. but local database update
                // (simplifyPayment or transaction) failed
                if (emailExceptionFlag) {
                    logger.error("Tour successfully booked but unable to send confirmation email.");
                    throw new EndUserException(EndUserConstants.HttpErrorCode_574, "Error!");
                } else {
                    // Tour is booked and payment is processed. but sending email failed WRONG
                    // TRUPMS STYLE
                    logger.error("Tour is booked and payment is processed. but sending email failed");
                    throw new EndUserException(EndUserConstants.HttpErrorCode_571, "Error!");
                }
            }
        }
        return transactionObject;
    }

    @GET
    @Secured({Role.SuperAdmin, Role.Admin})
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/gettransactions")
    // is not really getting it by GIVEN DATE
    public List<SimplifyPayment> getTransactionsByDate(@QueryParam("date") String date) throws EndUserException {
        logger.info("Entering TourServiceImpl getTransactionsByDate method");

        try {
            return tourTransactionUtil.getTransactionsByDate(tourTransactionUtil.getDateFromISO8601(date));
        } catch (Exception e) {
            logger.error("TourServiceImpl getTransactionsByDate method throw exception ", e);
            throw new EndUserException(EndUserConstants.HttpErrorCode_450, "Simplify Payment API throw exception");
        }
    }

    @GET
    @Secured({Role.SuperAdmin, Role.Admin})
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/gettransactioncount")
    public int getTransactionCountByDate(@QueryParam("date") String date) throws EndUserException {
        logger.info("Entering TourServiceImpl getTransactionCountByDate method");

        try {
            return tourTransactionUtil.getTransactionCountByDate(tourTransactionUtil.getDateFromISO8601(date));
        } catch (Exception e) {
            logger.error("TourServiceImpl getTransactionCountByDate method throw exception ", e);
            throw new EndUserException(EndUserConstants.HttpErrorCode_450, "Simplify Payment API throw exception");
        }
    }

    @GET
    @Secured({Role.SuperAdmin, Role.Admin})
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @Path("/findpaymnet")
    public SimplifyPayment findPayment(@QueryParam("paymentId") String paymentId) throws EndUserException {
        logger.info("Entering TourServiceImpl findPayment method");

        try {
            return tourTransactionUtil.findPayment(paymentId);
        } catch (Exception e) {
            logger.error("TourServiceImpl findPayment method throw exception ", e);
            throw new EndUserException(EndUserConstants.HttpErrorCode_450, "Simplify Payment API throw exception");
        }
    }

    @POST
    @Secured({Role.SuperAdmin, Role.Admin})
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @Path("/refund")
    // THE SAME THING ROLL BACK NEEDED????
    public void refundCustomer(RefundObject refundObject) throws EndUserException {
        logger.info("Entering TourServiceImpl refundCustomer method");
        Refund refund = null;
        boolean exceptionFlag = false;

        try {
            refund = tourTransactionUtil.refundCustomer(refundObject);
            if (refund == null) {
                exceptionFlag = true;
                throw new EndUserException();
            } else {
                tourTransactionUtil.saveRefund(refund);
                tourDelegate.updateRefundedSimplifyPaymentEntry(refundObject.getPaymentId(), touristServiceUtil.getSimpleDate());
                sendMailUtil.refundTouristEmail(refundObject);
            }
        } catch (Exception e) {
            logger.error("TourServiceImpl refundCustomer method throw exception ", e);
            if (exceptionFlag)
                throw new EndUserException(EndUserConstants.HttpErrorCode_572, "Error!");
            throw new EndUserException(EndUserConstants.HttpErrorCode_450, "Simplify Payment API throw exception");
        }
    }

    @GET
    @Secured({Role.SuperAdmin, Role.Admin})
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @Path("/findrefund")
    public Refund findRefund(@QueryParam("refundId") String refundId) throws EndUserException {
        logger.info("Entering TourServiceImpl findRefund method");

        try {
            return tourTransactionUtil.findRefund(refundId);
        } catch (Exception e) {
            logger.error("TourServiceImpl findRefund method throw exception ", e);
            throw new EndUserException(EndUserConstants.HttpErrorCode_450, e.getMessage());
        }
    }

    @GET
    @Secured({Role.SuperAdmin, Role.Admin})
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @Path("/gettourtransactions")
    public List<TourTransaction> getTourTransactions(@QueryParam("key") String key, @QueryParam("value") String value)
            throws EndUserException {
        logger.info("Entering TourServiceImpl getTourTransactions method");
        boolean exceptionFlag = false;

        if (StringUtils.equalsIgnoreCase(key, value)) {
            exceptionFlag = true;
            throw new EndUserException();
        }
        try {
            return tourDelegate.getTourTransactions(key, value);
        } catch (Exception e) {
            logger.error("TourServiceImpl getTourTransactions method throw exception ", e);
            if (exceptionFlag)
                throw new EndUserException(EndUserConstants.HttpErrorCode_573, "Error!");
            throw new EndUserException(EndUserConstants.HttpErrorCode_450, e.getMessage());
        }
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/gettourroute")
    public TourRoute getTourRoute(@QueryParam("tourId") String tourId) {
        logger.info("Entering TourServiceImpl method");

        try {
            return tourDelegate.getTourRoute(tourId);
        } catch (Exception e) {
            logger.error("TourServiceImpl getTourRoute method throw exception ", e);
            throw new EndUserException(EndUserConstants.HttpErrorCode_450, e.getMessage());
        }
    }

    @POST
    @Secured({Role.SuperAdmin, Role.Admin})
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @Path("/savetourroute")
    public TourRoute saveTourRoute(TourRoute tourRoute) {
        logger.info("Entering saveTourRoute method");
        boolean editFlag = false;
        try {
            return tourDelegate.saveTourRoute(tourRoute, editFlag);
        } catch (Exception e) {
            logger.error("TourServiceImpl saveTourRoute method throw exception ", e);
            throw new EndUserException(EndUserConstants.HttpErrorCode_450, e.getMessage());
        }
    }

    @DELETE
    @Secured({Role.SuperAdmin, Role.Admin})
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @Path("/deletetourroute")
    public void deleteTourRoute(@QueryParam("tourId") String tourId) {
        logger.info("Entering TourServiceImpl deleteTourRoute method");
        boolean exceptionFlag = false;

        try {
            TourRoute tourRouteObject = tourDelegate.getTourRoute(tourId);
            if (tourRouteObject != null) {
                tourDelegate.deleteTourRoute(tourId);
            } else {
                exceptionFlag = true;
                throw new EndUserException();
            }
        } catch (Exception e) {
            logger.error("TourServiceImpl deleteTourRoute method throw exception ", e);
            if (exceptionFlag)
                throw new EndUserException(EndUserConstants.HttpErrorCode_567, "Error!");
            throw new EndUserException(EndUserConstants.HttpErrorCode_450, e.getMessage());
        }
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/getprice")
    public Group1Price getTourPrice(@QueryParam("operatorId") String operatorId, @QueryParam("tourId") String tourId,
            @QueryParam("tripDate") String tripDate, @QueryParam("inclusiveFlag") Boolean inclusiveFlag) {
        logger.info("Entering TourServiceImpl method");

        try {
            return tourServiceUtil.getTourPrice(operatorId, tourId, dateConstants.parseDate(tripDate), inclusiveFlag);
        } catch (Exception e) {
            logger.error("TourServiceImpl getTourPrice method throw exception ", e);
            throw new EndUserException(EndUserConstants.HttpErrorCode_450, e.getMessage());
        }
    }

    @POST
    @Secured({Role.SuperAdmin, Role.Admin, Role.Operator})
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @Path("/saveg1price")
    public Group1Price saveGroup1Price(Group1Price group1Price) {
        logger.info("Entering saveGroup1Price method");
        boolean editFlag = false;
        
        try {
            return tourPriceDelegate.saveGroup1Price(group1Price, editFlag);
        } catch (Exception e) {
            logger.error("TourServiceImpl saveGroup1Price method throw exception ", e);
            if ("could not execute statement".equalsIgnoreCase(e.getMessage()))
                throw new EndUserException(EndUserConstants.HttpErrorCode_580, "Error!");
            throw new EndUserException(EndUserConstants.HttpErrorCode_450, e.getMessage());
        }
    }

    @POST
    @Secured({Role.SuperAdmin, Role.Admin, Role.Operator})
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @Path("/saveg2price")
    public Group2Price saveGroup2Price(Group2Price group2Price) {
        logger.info("Entering saveGroup2Price method");
        boolean editFlag = false;

        try {
            return tourPriceDelegate.saveGroup2Price(group2Price, editFlag);
        } catch (Exception e) {
            logger.error("TourServiceImpl saveGroup2Price method throw exception ", e);
            if ("could not execute statement".equalsIgnoreCase(e.getMessage()))
                throw new EndUserException(EndUserConstants.HttpErrorCode_580, "Error!");
            throw new EndUserException(EndUserConstants.HttpErrorCode_450, e.getMessage());
        }
    }

    @POST
    @Secured({Role.SuperAdmin, Role.Admin, Role.Operator})
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @Path("/saveg3price")
    public Group3Price saveGroup3Price(Group3Price group3Price) {
        logger.info("Entering saveGroup3Price method");
        boolean editFlag = false;
        
        try {
            return tourPriceDelegate.saveGroup3Price(group3Price, editFlag);
        } catch (Exception e) {
            logger.error("TourServiceImpl saveGroup3Price method throw exception ", e);
            if ("could not execute statement".equalsIgnoreCase(e.getMessage()))
                throw new EndUserException(EndUserConstants.HttpErrorCode_580, "Error!");
            throw new EndUserException(EndUserConstants.HttpErrorCode_450, e.getMessage());
        }
    }

    @POST
    @Secured({Role.SuperAdmin, Role.Admin, Role.Operator})
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @Path("/saveg4price")
    public Group4Price saveGroup4Price(Group4Price group4Price) {
        logger.info("Entering saveGroup4Price method");
        boolean editFlag = false;

        try {
            return tourPriceDelegate.saveGroup4Price(group4Price, editFlag);
        } catch (Exception e) {
            logger.error("TourServiceImpl saveGroup4Price method throw exception ", e);
            if ("could not execute statement".equalsIgnoreCase(e.getMessage()))
                throw new EndUserException(EndUserConstants.HttpErrorCode_580, "Error!");
            throw new EndUserException(EndUserConstants.HttpErrorCode_450, e.getMessage());
        }
    }

    @POST
    @Secured({Role.SuperAdmin, Role.Admin, Role.Operator})
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @Path("/saveg5price")
    public Group5Price saveGroup5Price(Group5Price group5Price) {
        logger.info("Entering saveGroup5Price method");
        boolean editFlag = false;

        try {
            return tourPriceDelegate.saveGroup5Price(group5Price, editFlag);
        } catch (Exception e) {
            logger.error("TourServiceImpl saveGroup5Price method throw exception ", e);
            if ("could not execute statement".equalsIgnoreCase(e.getMessage()))
                throw new EndUserException(EndUserConstants.HttpErrorCode_580, "Error!");
            throw new EndUserException(EndUserConstants.HttpErrorCode_450, e.getMessage());
        }
    }

    /*
     * @PUT
     * 
     * @Secured({Role.SuperAdmin, Role.Admin})
     * 
     * @Produces({MediaType.APPLICATION_JSON})
     * 
     * @Consumes({MediaType.APPLICATION_JSON})
     * 
     * @Path("/updatetourprice") public PriceInclSeason1 updateTourPrice(PriceInclSeason1
     * priceInclSeason1) { // delete and create instead return null; }
     */

    @DELETE
    @Secured({Role.SuperAdmin, Role.Admin, Role.Operator})
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @Path("/deletetourprice")
    public void deleteTourPrice(@QueryParam("operatorId") String operatorId, @QueryParam("tourId") String tourId,
            @QueryParam("tripDate") String tripDate, @QueryParam("inclusiveFlag") Boolean inclusiveFlag) {
        logger.info("Entering TourServiceImpl deleteTourPrice method");

        try {
            tourServiceUtil.deleteTourPrice(operatorId, tourId, dateConstants.parseDate(tripDate), inclusiveFlag);
        } catch (Exception e) {
            logger.error("TourServiceImpl TourServiceImpl deleteTourRoute method throw exception ", e);
            throw new EndUserException(EndUserConstants.HttpErrorCode_450, e.getMessage());
        }
    }

    @POST
    @Secured({Role.SuperAdmin, Role.Admin, Role.Operator})
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @Path("/savegroupseason")
    public GroupSeasonCast saveGroupSeasonCast(GroupSeasonCast groupSeasonCast) {
        logger.info("Entering saveGroupSeasonCast method");
        boolean groupSeasonFlag = false;

        try {
            userGenericServiceDelegate.setGroupSeaosnFlagStatus(groupSeasonCast.getOperatorId());
            groupSeasonFlag = true;
            return tourPriceDelegate.saveGroupSeasonCast(groupSeasonCast);
        } catch (Exception e) {
            logger.error("TourServiceImpl saveGroupSeasonCast method throw exception ", e);
            if (groupSeasonFlag)
                userGenericServiceDelegate.resetGroupSeaosnFlagStatus(groupSeasonCast.getOperatorId());
            throw new EndUserException(EndUserConstants.HttpErrorCode_450, e.getMessage());
        }
    }

    @GET
    @Secured({Role.SuperAdmin, Role.Admin, Role.Operator})
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @Path("/getgroupseason")
    public GroupSeasonCast getGroupSeasonCast(@QueryParam("operatorId") String operatorId) {
        logger.info("Entering getGroupSeasonCast method");
        Principal principal = securityContext.getUserPrincipal();

        try {
            return tourPriceDelegate.getGroupSeason(principal.getName()); // principal.getName() can
                                                                          // replace operatorId
        } catch (Exception e) {
            logger.error("TourServiceImpl getGroupSeasonCast method throw exception ", e);
            throw new EndUserException(EndUserConstants.HttpErrorCode_450, e.getMessage());
        }
    }

    @DELETE
    @Secured({Role.SuperAdmin, Role.Admin, Role.Operator})
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @Path("/deletegroupseason")
    public void deleteGroupSeasonCast(@QueryParam("operatorId") String operatorId) {
        logger.info("Entering deleteGroupSeasonCast method");

        try {
            userGenericServiceDelegate.resetGroupSeaosnFlagStatus(operatorId);
            tourPriceDelegate.deleteGroupSeasonCast(operatorId);
        } catch (Exception e) {
            logger.error("TourServiceImpl deleteGroupSeasonCast method throw exception ", e);
            throw new EndUserException(EndUserConstants.HttpErrorCode_450, e.getMessage());
        }
    }


}
