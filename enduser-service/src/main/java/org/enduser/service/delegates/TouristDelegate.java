package org.enduser.service.delegates;

import java.util.List;

import org.apache.log4j.Logger;
import org.enduser.service.dao.GroupMemberDao;
import org.enduser.service.dao.TouristDao;
import org.enduser.service.dao.UserGenericServiceDao;
import org.enduser.service.exception.EndUserException;
import org.enduser.service.model.AuthenticationData;
import org.enduser.service.model.GroupMember;
import org.enduser.service.model.Tourist;
import org.enduser.service.model.TouristGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TouristDelegate {

    private static final Logger logger = Logger.getLogger(TouristDelegate.class);

    private TouristDao touristDao;
    private GroupMemberDao groupMemberDao;
    private UserGenericServiceDao userGenericServiceDao;

    @Autowired
    public void setTouristDao(TouristDao touristDao) {
        this.touristDao = touristDao;
    }

    @Autowired
    public void setGroupMemberDao(GroupMemberDao groupMemberDao) {
        this.groupMemberDao = groupMemberDao;
    }
    
    @Autowired
    public void setUserGenericServiceDao(UserGenericServiceDao userGenericServiceDao) {
        this.userGenericServiceDao = userGenericServiceDao;
    }

    public Tourist saveTourist(Tourist tourist) {
        logger.info("Entered TouristDelegate saveTourist method");
        Tourist touristObject = null;
        
        try {
            touristObject = touristDao.saveTourist(tourist);
        } catch (Exception e) {
            throw new EndUserException();
        }
        return touristObject;
    }

    public List<Tourist> getTourist(String emailAddress) {
        logger.info("Entered TouristDelegate getTourist method");
        List<Tourist> touristList = null;

        try {
            touristList = touristDao.getTourist(emailAddress);
            if(touristList.size() == 1){
                List<AuthenticationData> authenticationData = userGenericServiceDao.getAuthenticationData(emailAddress);
                if(authenticationData.size() == 1)
                    touristList.set(0, populateAuthenticationData(authenticationData.get(0), touristList.get(0)));                
            }
        } catch (Exception e) {
            throw new EndUserException();
        }
        return touristList;
    }

    public void updateTourist(String key, String value, String emailAddress) {
        logger.info("Entered TouristDelegate updateTourist method");

        try {
            touristDao.updateTourist(key, value, emailAddress);
        } catch (Exception e) {
            throw new EndUserException();
        }
    }

    public void deleteTouristPermanently(Tourist tourist) {
        logger.info("Entered TouristDelegate deleteTourist method");
        
        try {
            touristDao.deleteTouristPermanently(tourist);
        } catch (Exception e) {
            throw new EndUserException();
        }
    }

    public GroupMember addToGroup(GroupMember groupMember) {
        logger.info("Entered TouristDelegate addToGroup method");
        GroupMember groupMemberObject = null;
        try {
            groupMemberObject = touristDao.addToGroup(groupMember);
        } catch (Exception e) {
            throw new EndUserException();
        }
        return groupMemberObject;
    }

    public void deleteFromGroup(GroupMember groupMember) {
        logger.info("Entered TouristDelegate deleteFromGroup method");
        TouristDao touristDao = new TouristDao();
        
        try {
            touristDao.deleteFromGroup(groupMember);
        } catch (Exception e) {
            throw new EndUserException();
        }
    }

    public List<TouristGroup> getMyGroups(String emailAddress) {
        logger.info("Entered TouristDelegate getMygroups method");
        List<TouristGroup> touristGroupList = null;
        
        try {
            touristGroupList = touristDao.getMyGroups(emailAddress);
        } catch (Exception e) {
            throw new EndUserException();
        }
        return touristGroupList;
    }

    public List<GroupMember> getGroupMember(String emailAddress, String groupId) {
        logger.info("Entered TouristDelegate getGroupMember method");
        
        try {
            return groupMemberDao.getGroupMember(emailAddress, groupId);
        } catch (Exception e) {
            throw new EndUserException();
        }
    }
    
    private Tourist populateAuthenticationData(AuthenticationData authenticationData, Tourist tourist){
        authenticationData.setPassword(null);
        authenticationData.setToken(null);
        authenticationData.setActivationCode(null);
        tourist.setAuthenticationData(authenticationData);
        
        return tourist;
    }
}
