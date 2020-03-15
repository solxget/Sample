package org.enduser.service.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.enduser.service.exception.EndUserException;
import org.enduser.service.model.CustomedTour;
import org.enduser.service.model.LandingTour;
import org.enduser.service.model.RefundTourist;
import org.enduser.service.model.SimplifyPayment;
import org.enduser.service.model.Tour;
import org.enduser.service.model.TourRoute;
import org.enduser.service.model.TourTourOperator_C;
import org.enduser.service.model.TourTransaction;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Component;

@Component
public class TourDao {

    private static final Logger logger = Logger.getLogger(TourDao.class);
    private SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();


    @SuppressWarnings("unchecked")
    public List<Tour> getTourId(String key, String value) {
        logger.info("entered TourDao getTourId method ");

        List<Tour> tour = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();

            Query q = session.createQuery("from Tour where " + key + " = :value");
            q.setString("value", value);
            tour = (List<Tour>) q.list();

            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            logger.error("Exception throwen from TourDao getTourId", e);
            throw new EndUserException(e);
        } finally {
            if (session.isOpen())
                session.close();
        }

        return tour;
    }

    @SuppressWarnings("unchecked")
    public List<LandingTour> getLandingTourId(String key, String value) {
        logger.info("entered TourDao getLandingTourId method ");

        List<LandingTour> tour = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();

            Query q = session.createQuery("from LandingTour where " + key + " = :value");
            q.setString("value", value);
            tour = (List<LandingTour>) q.list();

            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            logger.error("Exception throwen from TourDao getLandingTourId", e);
            throw new EndUserException(e);
        } finally {
            if (session.isOpen())
                session.close();
        }

        return tour;
    }

    @SuppressWarnings("unchecked")
    public List<Tour> getTour(String key, String value, String[] plannedTripDate, int[] tourDuration, String tripDate, String country) {
        logger.info("entered TourDao getTour method ");

        List<Tour> tour = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();

//            String sql =
//                    "select * from Tour where country = :country and tour_duration between " + tourDuration[0] + " and " + tourDuration[1]
//                            + " and (tour_start_date between '" + plannedTripDate[0] + "' and '" + plannedTripDate[1]
//                            + "' or tour_start_date is null) and " + key
//                            + " = :value order by ABS (DATEDIFF (tour_start_date, :tripDate)) asc";
 //           String sql = "select * from Tour where country = :country";
            
          String sql = "select * from Tour where tour_id in ( select tour_id from TourTypeLookup where catagory_id in ( select catagory_id from TourCatagory where " + key + " = :value and country = :country ) )" ; 

            SQLQuery query = session.createSQLQuery(sql);
//            query.setString("value", value);
//            query.setString("tripDate", tripDate);
            query.setString("country", country);
            query.setString("value", value);
            query.addEntity(Tour.class);
            tour = (List<Tour>) query.list();

            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            logger.error("Exception throwen from TourDao getTour", e);
            throw new EndUserException(e);
        } finally {
            if (session.isOpen())
                session.close();
        }

        return tour;
    }

    @SuppressWarnings("unchecked")
    public List<Tour> getTourById(String tourId) {
        logger.info("entered TourDao getTourById method ");
        List<Tour> tour = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();

            Query query = session.createQuery("from Tour where tour_id = :tourId");
            query.setString("tourId", tourId);
            tour = (List<Tour>) query.list();

            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            logger.error("Exception throwen from TourDao getTour", e);
            throw new EndUserException(e);
        } finally {
            if (session.isOpen())
                session.close();
        }
        return tour;
    }

    @SuppressWarnings("unchecked")
    public List<LandingTour> getTourForEdit(String tourId) {
        logger.info("entered TourDao getTourForEdit method ");
        List<LandingTour> tour = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();

            Query query = session.createQuery("from LandingTour where tour_id = :tourId");
            query.setString("tourId", tourId);
            tour = (List<LandingTour>) query.list();

            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            logger.error("Exception throwen from TourDao getTourForEdit", e);
            throw new EndUserException(e);
        } finally {
            if (session.isOpen())
                session.close();
        }
        return tour;
    }

    @SuppressWarnings("unchecked")
    public List<Tour> getTourByOperatorId(String operatorId) {
        logger.info("entered TourDao getTourById method ");
        List<Tour> tour = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();

            Query query = session.createQuery("from Tour where operator_id = :operatorId");
            query.setString("operatorId", operatorId);
            tour = (List<Tour>) query.list();

            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            logger.error("Exception throwen from TourDao getTourByOperatorId", e);
            throw new EndUserException(e);
        } finally {
            if (session.isOpen())
                session.close();
        }
        return tour;
    }
    
    @SuppressWarnings("unchecked")
    public List<Tour> getTourByCountry(String country) {
        logger.info("entered TourDao getTourByCountry method ");
        List<Tour> tour = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();

            Query query = session.createQuery("from Tour where country = :country");
            query.setString("country", country);
            tour = (List<Tour>) query.list();

            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            logger.error("Exception throwen from TourDao getTourByCountry", e);
            throw new EndUserException(e);
        } finally {
            if (session.isOpen())
                session.close();
        }
        return tour;
    }

    @SuppressWarnings("unchecked")
    public List<LandingTour> getMyTours(String operatorId) {
        logger.info("entered TourDao getMyTours method ");
        List<LandingTour> tour = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();

            Query query = session.createQuery("from LandingTour where operator_id = :operatorId");
            query.setString("operatorId", operatorId);
            tour = (List<LandingTour>) query.list();

            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            logger.error("Exception throwen from TourDao getMyTours", e);
            throw new EndUserException(e);
        } finally {
            if (session.isOpen())
                session.close();
        }
        return tour;
    }

    public Tour saveTour(Tour tour) {
        logger.info("Log: entered TourDao saveTour method ");

        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.save(tour);

            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            logger.error("Exception throwen from TourDao saveTour method", e);
            throw new EndUserException(e);
        } finally {
            if (session.isOpen())
                session.close();
        }
        return tour;
    }

    public LandingTour saveLandingTour(LandingTour landingTour, boolean editFlag) {
        logger.info("Log: entered TourDao saveLandingTour method ");

        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            
            if(editFlag){
                session.update(landingTour);
//                this.tourId = tourId;
//                this.tourRoute = tourRoute;
//                this.tourDuration = tourDuration;
//                this.tourMaxSize = tourMaxSize;
//                this.tourMinSize = tourMinSize;
//                this.tourType = tourType;
//                this.tourStartPlace = tourStartPlace;
//                this.tourStartTime = tourStartTime;
//                this.tourStartDate = tourStartDate;
//                this.tourEndDate = tourEndDate;
//                this.operatorId = operatorId;
//                this.registrationDate = registrationDate;
//                this.country = country;
//                
//                this.serviceLevel = serviceLevel;
//                this.minAge = minAge;
//                this.transportation = transportation;
//                this.physicalDemand = physicalDemand;
//                this.endingPlace = endingPlace;
//                this.withGuide = withGuide;
//                String sql = "update LandingTour set tourId = :tourId, tourRoute = :tourRoute, tourDuration = :tourDuration, tourMaxSize = :tourMaxSize, "
//                        + "tourMinSize = :tourMinSize, tourType = :tourType, tourStartPlace = :tourStartPlace, tourStartTime = :tourStartTime, tourStartDate = :tourStartDate,"
//                        + " tourEndDate = :tourEndDate, operatorId = :operatorId, registrationDate = :registrationDate, country = :country, serviceLevel = :serviceLevel,"
//                        + " minAge = :minAge, transportation = :transportation, physicalDemand = :physicalDemand, endingPlace = :endingPlace, withGuide = :withGuide"
//                        + " where tour_id = :tourId";
//                SQLQuery query = session.createSQLQuery(sql);
//                query.setString("value", value);
//                query.setString("tourId", tourId);
//                query.addEntity(LandingTour.class);
//                query.executeUpdate();
            }
            else{
                session.save(landingTour);
            }

            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            logger.error("Exception throwen from TourDao saveLandingTour method", e);
            throw new EndUserException(e);
        } finally {
            if (session.isOpen())
                session.close();
        }
        return landingTour;
    }

    @SuppressWarnings("unchecked")
    public List<Tour> getBookedTour(String emailAddress) {
        logger.info("entered TourDao getBookedTour method ");

        List<Tour> bookedTour = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();

            String sql =
                    "select * from Tour where tour_id in (select tour_id from tourtransaction where email_address = :emailAddress)";
            SQLQuery query = session.createSQLQuery(sql);
            query.setString("emailAddress", emailAddress);
            query.addEntity(Tour.class);
            bookedTour = (List<Tour>) query.list();

            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            logger.error("Exception throwen from TourDao getBookedTour", e);
            throw new EndUserException(e);
        } finally {
            if (session.isOpen())
                session.close();
        }
        return bookedTour;
    }

    public TourTransaction bookTour(TourTransaction transaction) {
        logger.info("Log: entered TourDao bookTour method ");

        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.save(transaction);

            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            logger.error("Exception throwen from TourDao bookTour method", e);
            throw new EndUserException(e);
        } finally {
            if (session.isOpen())
                session.close();
        }
        return transaction;
    }

    public SimplifyPayment savePayment(SimplifyPayment simplifyPayment) {
        logger.info("Log: entered TourDao savePayment method ");

        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.save(simplifyPayment);

            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            logger.error("Exception throwen from TourDao savePayment method", e);
            throw new EndUserException(e);
        } finally {
            if (session.isOpen())
                session.close();
        }
        return simplifyPayment;
    }

    public RefundTourist saveRefund(RefundTourist refundTourist) {
        logger.info("Log: entered TourDao saveRefund method ");

        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.save(refundTourist);

            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            logger.error("Exception throwen from TourDao saveRefund method", e);
            throw new EndUserException(e);
        } finally {
            if (session.isOpen())
                session.close();
        }
        return refundTourist;
    }

    public int getTourCount(String operatorId) {
        logger.info("Log: entered TourDao getTourCount method ");

        BigInteger count;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            String sql = "select count(*) from LandingTour where operator_id = :operatorId";

            SQLQuery query = session.createSQLQuery(sql);
            query.setString("operatorId", operatorId);
            count = (BigInteger) query.uniqueResult();

            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            logger.error("Exception throwen from TourDao getTourCount" + e);
            throw new EndUserException(e);
        } finally {
            if (session.isOpen())
                session.close();
        }

        return count.intValue();
    }

    public void updateTour(String key, String value, String tourId) {
        logger.info("Log: entered TourDao updateTour method ");

        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();

            String sql = "update Tour set " + key + " = :value where tour_id = :tourId ";
            SQLQuery query = session.createSQLQuery(sql);
            query.setString("value", value);
            query.setString("tourId", tourId);
            query.addEntity(Tour.class);
            query.executeUpdate();

            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            logger.error("Exception throwen from TourDao updateTour" + e);
            throw new EndUserException(e);
        } finally {
            if (session.isOpen())
                session.close();
        }
    }

    public void deleteTour(Tour tour) {
        logger.info("Log: entered TourDao deleteTour method ");

        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.delete(tour);

            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            logger.error("Exception throwen from TourDao deleteTour method" + e);
            throw new EndUserException(e);
        } finally {
            if (session.isOpen())
                session.close();
        }
    }

    public void deleteTour(LandingTour tour) {
        logger.info("Log: entered TourDao deleteTour method : LandingTour");

        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.delete(tour);

            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            logger.error("Exception throwen from TourDao deleteTour method : LandingTour" + e);
            throw new EndUserException(e);
        } finally {
            if (session.isOpen())
                session.close();
        }
    }

    public void deleteCustomedTour(String emailAddress) {
        logger.info("Log: entered TourDao deleteCustomedTour method ");

        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();

            String sql = "delete from CustomedTour where email_Address = :emailAddress and tour_price = '0'";
            SQLQuery query = session.createSQLQuery(sql);
            query.setString("emailAddress", emailAddress);
            query.addEntity(Tour.class);
            query.executeUpdate();

            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            logger.error("Exception throwen from TourDao deleteCustomedTour method" + e);
            throw new EndUserException(e);
        } finally {
            if (session.isOpen())
                session.close();
        }
    }

    @SuppressWarnings("unchecked")
    public List<TourTourOperator_C> getToursByType(String tourType) {
        logger.info("entered TourDao getToursByType method ");

        List<TourTourOperator_C> tourTourOperatorC = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            String sql =
                    "select tour.tour_id as tTO_C_sequence_num, tour.tour_duration, tour.TOUR_PRICE, tour.TOUR_INCLUSIVE_PRICE,"
                            + " tour.tour_route, touroperator.OPERATOR_NAME, touroperator.rating from tour inner join touroperator on "
                            + "tour.operator_id = touroperator.operator_id where tour.tour_type = :tourType";
            SQLQuery query = session.createSQLQuery(sql);
            query.setString("tourType", tourType);
            query.addEntity(TourTourOperator_C.class);
            tourTourOperatorC = (List<TourTourOperator_C>) query.list();

            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            logger.error("Exception throwen from TourDao getToursByType", e);
            throw new EndUserException(e);
        } finally {
            if (session.isOpen())
                session.close();
        }
        return tourTourOperatorC;
    }

    @SuppressWarnings("unchecked")
    public List<String> getTourType(String country) {
        logger.info("entered TourDao getTourType method ");

        List<String> tourTypeList = new ArrayList<String>();
        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();

            // get it from TourCatagory table;
            SQLQuery query = session.createSQLQuery("select distinct tour_type from TourCatagory where country = :country ORDER BY tour_Type ASC");
       //     SQLQuery query = session.createSQLQuery("select distinct tour_type from Tour where country = :country ORDER BY tour_Type ASC");
            query.setString("country", country);
            tourTypeList = (List<String>) query.list();
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            logger.error("Exception throwen from TourDao getTourType", e);
            throw new EndUserException(e);
        } finally {
            if (session.isOpen())
                session.close();
        }

        return tourTypeList;
    }

    @SuppressWarnings("unchecked")
    public List<LandingTour> getLandingTourType(String country) {
        logger.info("entered TourDao getLandingTourType method ");

        List<LandingTour> tourType = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();

            String sql = "select * from LandingTour where country = :country ORDER BY tour_Type ASC";
            SQLQuery query = session.createSQLQuery(sql);
        //    query.setString("country", country);             // Uncomment this after we have enough data for Tour Type drop down 
            query.setString("country", "Test");
            query.addEntity(LandingTour.class);
            tourType = (List<LandingTour>) query.list();
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            logger.error("Exception throwen from TourDao getLandingTourType", e);
            throw new EndUserException(e);
        } finally {
            if (session.isOpen())
                session.close();
        }

        return tourType;
    }

    public void updateRefundedSimplifyPayment(String paymentId) {
        logger.info("Log: entered TourDao updateRefundedSimplifyPayment method ");

        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();

            String sql = "update SimplifyPayment set refunded = '1' where id = :paymentId";
            SQLQuery query = session.createSQLQuery(sql);
            query.setString("paymentId", paymentId);
            query.addEntity(SimplifyPayment.class);
            query.executeUpdate();

            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            logger.error("Exception throwen from TourDao updateRefundedSimplifyPayment" + e);
            throw new EndUserException(e);
        } finally {
            if (session.isOpen())
                session.close();
        }
    }

    public void updateTourTransaction(String paymentId, String refundDate) {
        logger.info("Log: entered TourDao updateTourTransaction method ");

        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            // update TourTransaction set refund_flag = '1', refund_Date = TO_DATE('2015/02/14
            // 10:30:00', 'yyyy/MM/dd HH:mi:ss') where payment_Id = 'ypMB6n6M'
            // Sun Feb 14 17:17:48 EST 2016
            // DY MON DD HH:mi:ss YYY
            // 2016/02/14 18:00:16
            String sql = "update TourTransaction set refund_flag = '1' where payment_Id = :paymentId";
            // String sql = "update TourTransaction set refund_flag = '1', refund_Date = '" +
            // java.sql.Timestamp.valueOf(refundDate) +"' where payment_Id = '" + paymentId + "'";
            SQLQuery query = session.createSQLQuery(sql);
            query.setString("paymentId", paymentId);
            query.addEntity(TourTransaction.class);
            query.executeUpdate();

            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            logger.error("Exception throwen from TourDao updateTourTransaction" + e);
            throw new EndUserException(e);
        } finally {
            if (session.isOpen())
                session.close();
        }
    }

    @SuppressWarnings("unchecked")
    public List<TourTransaction> getTourTransactions(String key, String value) throws EndUserException {
        logger.info("entered TourDao getTourTransactions method ");

        List<TourTransaction> tourTransaction = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();

            Query q = session.createQuery("from TourTransaction where " + key + " = :value");
            q.setString("value", value);
            tourTransaction = (List<TourTransaction>) q.list();

            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            logger.error("Exception throwen from TourDao getTourTransactions", e);
            throw new EndUserException(e);
        } finally {
            if (session.isOpen())
                session.close();
        }

        return tourTransaction;
    }

    public CustomedTour saveCustomedTour(CustomedTour customedTour) {
        logger.info("entered TourDao saveCustomedTour method ");

        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.save(customedTour);

            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            logger.error("Exception throwen from TourDao saveCustomedTour", e);
            throw new EndUserException(e);
        } finally {
            if (session.isOpen())
                session.close();
        }

        return customedTour;
    }

    public CustomedTour updateCustomedTour(CustomedTour customedTour) {
        logger.info("entered TourDao updateCustomedTour method ");

        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.save(customedTour);

            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            logger.error("Exception throwen from TourDao updateCustomedTour", e);
            throw new EndUserException(e);
        } finally {
            if (session.isOpen())
                session.close();
        }

        return customedTour;
    }

    @SuppressWarnings("unchecked")
    public List<CustomedTour> getCustomedTour() {
        logger.info("entered TourDao getCustomedTour method ");

        List<CustomedTour> customedTour = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();

            Query q = session.createQuery("from CustomedTour where tour_price = '0'");
            customedTour = (List<CustomedTour>) q.list();

            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            logger.error("Exception throwen from TourDao getCustomedTour", e);
            throw new EndUserException(e);
        } finally {
            if (session.isOpen())
                session.close();
        }

        return customedTour;
    }

    @SuppressWarnings("unchecked")
    public List<TourRoute> getTourRoute(String tourId) {
        logger.info("entered TourDao getTourRoute method ");

        List<TourRoute> tourRoute = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();

            Query q = session.createQuery("from TourRoute where tour_id = :tourId");
            q.setString("tourId", tourId);
            tourRoute = (List<TourRoute>) q.list();

            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            logger.error("Exception throwen from TourDao getTourRoute", e);
            throw new EndUserException(e);
        } finally {
            if (session.isOpen())
                session.close();
        }

        return tourRoute;
    }

    public TourRoute saveTourRoute(TourRoute tourRoute, boolean editObject) {
        logger.info("Log: entered TourDao saveTourRoute method ");

        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            if(editObject)
                session.update(tourRoute);
            else
                session.save(tourRoute);

            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            logger.error("Exception throwen from TourDao saveTourRoute method", e);
            throw new EndUserException(e);
        } finally {
            if (session.isOpen())
                session.close();
        }

        return tourRoute;
    }

    public void deleteTourRoute(String tourId) {
        logger.info("Log: entered TourDao deleteTourRoute method ");

        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();

            String sql = "delete from TourRoute where tour_id = :tourId";
            SQLQuery query = session.createSQLQuery(sql);
            query.setString("tourId", tourId);
            query.addEntity(TourRoute.class);
            query.executeUpdate();

            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            logger.error("Exception throwen from TourDao deleteTourRoute method" + e);
            throw new EndUserException(e);
        } finally {
            if (session.isOpen())
                session.close();
        }
    }
}
