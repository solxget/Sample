package org.enduser.service.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.enduser.service.exception.EndUserException;
import org.enduser.service.model.GroupMember;
import org.enduser.service.model.PasswordReset;
import org.enduser.service.model.Tourist;
import org.enduser.service.model.TouristGroup;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Component;

@Component
public class TouristDao {

    private static final Logger logger = Logger.getLogger(TouristDao.class);
    private SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

/*    public void Sisay() {
       System.out.println("Log: entered TouristDao saveTourist method ");

        SessionFactory sessionFactory1 = new Configuration().configure().buildSessionFactory();
        Session session = null;
        try {
            session = sessionFactory1.openSession();
            session.beginTransaction();
            System.out.println(" in the try block");
            System.out.println("Try block Session object is Open "+ session.isOpen());
         //   session.save(tourist);
        //    session.getTransaction().commit();
            int x = 90/0;
            session.close();
        } catch (Exception e) {
            System.out.println("Exception throwen from TouristDao saveTourist method");
            throw new EndUserException(e);
        } finally {
            System.out.println(" just entereed fainally block");
            System.out.println("Finally block Session object is Open "+ session.isOpen());
            if (session.isOpen()){
                session.close();
                System.out.println("Finally block Session object is Open "+ session.isOpen());
                System.out.println(" finally block");
            }
        }

    }
    */
    public Tourist saveTourist(Tourist tourist) {
        logger.info("Log: entered TouristDao saveTourist method ");

        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();

            session.save(tourist);
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            logger.error("Exception throwen from TouristDao saveTourist method", e);
            throw new EndUserException(e);
        } finally {
            if (session.isOpen())
                session.close();
        }

        return tourist;
    }

    @SuppressWarnings("unchecked")
    public List<Tourist> getTourist(String emailAddress) {
        logger.info("entered TouristDao getTourist method ");

        List<Tourist> tourist = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();

            Query q = session.createQuery("from Tourist where email_address = :emailAddress");
            q.setString("emailAddress", emailAddress);
            tourist = (List<Tourist>) q.list();

            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            logger.error("Exception throwen when trying to get a trourist/tourists ", e);
            throw new EndUserException(e);
        } finally {
            if (session.isOpen())
                session.close();
        }

        return tourist;
    }

    public void updateTourist(String key, String value, String emailAddress) {
        logger.info("entered TouristDao updateTourist method ");

        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();

            String sql = "update Tourist set " + key + " = :value where email_address = '" + emailAddress + "' ";
            SQLQuery query = session.createSQLQuery(sql);
            query.setString("value", value);
            query.addEntity(Tourist.class);
            query.executeUpdate();

            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            logger.error("Exception throwen from TouristDao updateTurist method" + e);
            throw new EndUserException(e);
        } finally {
            if (session.isOpen())
                session.close();
        }
    }

    public void deleteTouristPermanently(Tourist tourist) {
        logger.info("entered TouristDao deleteTouristPermanently method ");

        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();

            session.delete(tourist);
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            logger.error("Exception throwen from TouristDao deleteTouristPermanently method" + e);
            throw new EndUserException(e);
        } finally {
            if (session.isOpen())
                session.close();
        }
    }

    public GroupMember addToGroup(GroupMember groupMember) {
        logger.info("Log: entered TouristDao addToGroup method ");
        
        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();

            session.save(groupMember);

            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            logger.error("Exception throwen from TouristDao addToGroup method" + e);
            throw new EndUserException(e);
        } finally {
            if (session.isOpen())
                session.close();
        }

        return groupMember;
    }

    public void deleteFromGroup(GroupMember groupMember) {
        logger.info("Log: entered TouristDao addToGroup method ");
        
        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.delete(groupMember);

            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            logger.error("Exception throwen from TouristDao deleteFromGroup method" + e);
            throw new EndUserException(e);
        } finally {
            if (session.isOpen())
                session.close();
        }
    }

    @SuppressWarnings("unchecked")
    public List<TouristGroup> getMyGroups(String emailAddress) {
        logger.info("entered TouristDao getMygroups method ");

        List<TouristGroup> touristGroup = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();

            String sql =
                    "select * from TouristGroup where group_id in (select group_id from GroupMember where " + "email_address = '"
                            + emailAddress + "' )";

            SQLQuery query = session.createSQLQuery(sql);
            query.addEntity(TouristGroup.class);

            touristGroup = (List<TouristGroup>) query.list();

            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            logger.error("Exception throwen from TouristDao getMygroups method ", e);
            throw new EndUserException(e);
        } finally {
            if (session.isOpen())
                session.close();
        }

        return touristGroup;
    }

    public void deleteResetData(PasswordReset passwordReset) {
        logger.info("entered TouristDao deleteResetData method ");

        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();

            session.delete(passwordReset);
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            logger.error("Exception throwen from TouristDao deleteResetData method ", e);
            throw new EndUserException(e);
        } finally {
            if (session.isOpen())
                session.close();
        }
    }


}
