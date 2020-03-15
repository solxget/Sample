package org.enduser.service.services.impl;

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
import org.enduser.service.delegates.GroupMembersDelegate;
import org.enduser.service.delegates.TouristDelegate;
import org.enduser.service.delegates.TouristGroupDelegate;
import org.enduser.service.delegates.UserGenericServiceDelegate;
import org.enduser.service.exception.EndUserException;
import org.enduser.service.model.AuthenticationData;
import org.enduser.service.model.GroupMember;
import org.enduser.service.model.PasswordReset;
import org.enduser.service.model.Tourist;
import org.enduser.service.model.TouristGroup;
import org.enduser.service.model.util.AddToGroupObject;
import org.enduser.service.services.TouristService;
import org.enduser.service.services.impl.util.Role;
import org.enduser.service.services.impl.util.Secured;
import org.enduser.service.util.EndUserConstants;
import org.enduser.service.util.SendMailUtil;
import org.enduser.service.util.TouristServiceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Path("/tourist")
public class TouristServiceImpl implements TouristService {
    private static final Logger logger = Logger.getLogger(TouristServiceImpl.class);
    @Context
    SecurityContext securityContext;
    
    private TouristDelegate touristDelegate;
    private TouristServiceUtil touristServiceUtil;
    private TouristGroupDelegate touristGroupDelegate;
    private GroupMembersDelegate groupMembersDelegate;
    private SendMailUtil sendMailUtil;
    private UserGenericServiceDelegate userGenericServiceDelegate;

    private TouristGroupServiceImpl touristGroupServiceImpl; // autowiring the impl? problem >???

    @Autowired
    public void setTouristDelegate(TouristDelegate touristDelegate) {
        this.touristDelegate = touristDelegate;
    }

    @Autowired
    public void setTouristServiceUtil(TouristServiceUtil touristServiceUtil) {
        this.touristServiceUtil = touristServiceUtil;
    }

    @Autowired
    public void setSendMailUtil(SendMailUtil sendMailUtil) {
        this.sendMailUtil = sendMailUtil;
    }

    @Autowired
    public void setTouristGroupDelegate(TouristGroupDelegate touristGroupDelegate) {
        this.touristGroupDelegate = touristGroupDelegate;
    }

    @Autowired
    public void setGroupMembersDelegate(GroupMembersDelegate groupMembersDelegate) {
        this.groupMembersDelegate = groupMembersDelegate;
    }

    @Autowired
    public void setTouristGroupServiceImpl(TouristGroupServiceImpl touristGroupServiceImpl) {
        this.touristGroupServiceImpl = touristGroupServiceImpl;
    }

    @Autowired
    public void setUserGenericServiceDelegate(UserGenericServiceDelegate userGenericServiceDelegate) {
        this.userGenericServiceDelegate = userGenericServiceDelegate;
    }


    @GET
    @Secured({Role.SuperAdmin, Role.Admin, Role.User})
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/getuser")
    public List<Tourist> getTourist(@QueryParam("emailAddress") String emailAddress) throws EndUserException {
        logger.info("Entering TouristServiceImpl getTourist method");

        // Principal principal = securityContext.getUserPrincipal();
        // String username = principal.getName();
        // System.out.println("  fuckkkkkkkkkkkkkkkkkkkkkkkk   username  "+username);

        try {
            return touristDelegate.getTourist(emailAddress);
        } catch (Exception e) {
            logger.error("retriving tourist record failed", e);
            throw new EndUserException(EndUserConstants.HttpErrorCode_450, e.getMessage());
        }
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @Path("/register")
    public Tourist createTourist(Tourist tourist) throws EndUserException { // carefully RE-Check this logic
        logger.info("Entering TouristServiceImpl createTourist method");
        Tourist touristObject = null;
        AuthenticationData authenticationData = null;

        if (getTourist(tourist.getEmailAddress()).size() != 0) {
            throw new EndUserException(EndUserConstants.HttpErrorCode_552, "Error!");
        } else {
            try {
                if (touristServiceUtil.validateEmail(tourist.getEmailAddress())) {
                    authenticationData =
                            userGenericServiceDelegate.saveAuthenticationData(touristServiceUtil.setAuthenticationData(tourist));
                    tourist.setPassword(null);
                    tourist.setRegistrationDate(touristServiceUtil.getDateTime());
                    touristObject = touristDelegate.saveTourist(tourist);
                    userGenericServiceDelegate.saveResetData(new PasswordReset(tourist.getEmailAddress(), null, null));
                    if (touristObject != null) {
                        logger.info("TouristServiceImpl createTourist Trying to send email");
                        sendMailUtil.regestrationSuccessMail(tourist, authenticationData.getActivationCode());
                    }
                } else {
                    logger.error("TouristServiceImpl createTourist Email Validation failed");
                    throw new EndUserException(EndUserConstants.HttpErrorCode_551, "Error!");
                }
                return touristObject;

            } catch (Exception e) {
                logger.error("registering new tourist failed", e);
                if (touristObject != null) {
                    logger.error("registering new tourist: sending registration email failed", e);
                    boolean exceptionFlag = false;
                    try {
                        touristDelegate.deleteTouristPermanently(tourist);
                        userGenericServiceDelegate.deleteAuthenticationData(authenticationData);
                        userGenericServiceDelegate.deleteResetData(new PasswordReset(tourist.getEmailAddress(), null, null));
                        exceptionFlag = true;
                        throw new EndUserException();
                    } catch (Exception ex) {
                        if (exceptionFlag)
                            throw new EndUserException(EndUserConstants.HttpErrorCode_569, "Error!");
                        throw new EndUserException(EndUserConstants.HttpErrorCode_554, "Error!");
                    }
                }
                throw new EndUserException(EndUserConstants.HttpErrorCode_450, e.getMessage());
            }
        }
    }

    @PUT
    @Secured({Role.SuperAdmin, Role.Admin, Role.User})
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @Path("/update")
    public Tourist updateTourist(@QueryParam("key") String key, @QueryParam("value") String value,
            @QueryParam("emailAddress") String emailAddress) throws EndUserException {
        logger.info("Entering TouristServiceImpl updateTourist method Email Address: " + emailAddress);
        boolean exceptionFlag = false;
        boolean suspeciousFlag = false;

        try {
            if (StringUtils.equalsIgnoreCase(key, value)) {
                suspeciousFlag = true;
                throw new EndUserException();
            }
            List<Tourist> touristObject = touristDelegate.getTourist(emailAddress);
            if (touristObject.size() == 1 && StringUtils.equals(emailAddress, touristObject.get(0).getEmailAddress())) {
                touristDelegate.updateTourist(key, value, emailAddress);
                return touristDelegate.getTourist(emailAddress).get(0);
            } else {
                exceptionFlag = true;
                throw new EndUserException();
            }

        } catch (Exception e) {
            logger.error("TouristServiceImpl updateTourist method throw exception ", e);
            if (exceptionFlag)
                throw new EndUserException(EndUserConstants.HttpErrorCode_553, "Error!");
            else if (suspeciousFlag)
                throw new EndUserException(EndUserConstants.HttpErrorCode_573, "Error!");
            throw new EndUserException(EndUserConstants.HttpErrorCode_450, e.getMessage());
        }
    }

    @DELETE
    @Secured({Role.SuperAdmin, Role.Admin, Role.User})
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @Path("/delete")
    public void deleteTourist(@QueryParam("emailAddress") String emailAddress, // may be delete the
                                                                               // user permanently.
                                                                               // update will do
                                                                               // this job
            @QueryParam("statusChangeReason") String statusChangeReason) throws EndUserException {
        logger.info("Entering TouristServiceImpl deleteTourist method");
        boolean exceptionFlag = false;

        try {
            List<Tourist> touristObject = touristDelegate.getTourist(emailAddress);
            if (touristObject.size() == 1 && StringUtils.equals(emailAddress, touristObject.get(0).getEmailAddress())) {
                userGenericServiceDelegate.deleteTourist(touristServiceUtil.setFields(touristObject.get(0), statusChangeReason));
            } else {
                exceptionFlag = true;
                throw new EndUserException();
            }
        } catch (Exception e) {
            logger.error("TouristServiceImpl deleteTourist method throw exception ", e);
            if (exceptionFlag)
                throw new EndUserException(EndUserConstants.HttpErrorCode_554, "Error!");
            throw new EndUserException(EndUserConstants.HttpErrorCode_450, e.getMessage());
        }
    }

    @POST
    // @Secured({Role.SuperAdmin, Role.Admin, Role.User})
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @Path("/addtogroup")
    public GroupMember addToGroup(AddToGroupObject addToGroupObject) {
        logger.info("Entering TouristServiceImpl addToGroup method ");
        boolean accountExistFlag = false;
        boolean groupExistFlag = true;
        GroupMember newGroupMember = null;
        try {
            List<GroupMember> groupMemberList =
                    touristDelegate.getGroupMember(addToGroupObject.getEmailAddress(), addToGroupObject.getGroupId());
            List<TouristGroup> touristGroup = touristGroupDelegate.getGroup(addToGroupObject.getGroupId());
            if (groupMemberList.size() == 0 && touristGroup.size() == 1) {
                GroupMember groupMember =
                        touristServiceUtil.setGroupMemberParameters(addToGroupObject.getGroupId(),
                                addToGroupObject.getEmailAddress());
                newGroupMember = touristDelegate.addToGroup(groupMember);
                touristServiceUtil.informGroupMembers(addToGroupObject.getEmailAddress(), touristGroup.get(0).getGroupId());
                return newGroupMember;
            } else if (touristGroup.size() == 0) {
                groupExistFlag = false;
                throw new EndUserException();
            } else {
                accountExistFlag = true;
                throw new EndUserException();
            }
        } catch (Exception e) {
            logger.error("TouristServiceImpl addToGroup method throw exception ", e);
            if (accountExistFlag)
                throw new EndUserException(EndUserConstants.HttpErrorCode_576, "Error!");
            else if (!groupExistFlag)
                throw new EndUserException(EndUserConstants.HttpErrorCode_561, "Error");
            throw new EndUserException(EndUserConstants.HttpErrorCode_450, e.getMessage());
        }
    }

    @DELETE
    @Secured({Role.SuperAdmin, Role.Admin, Role.User})
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @Path("/deletefromgroup")
    public void deleteFromGroup(GroupMember groupMember) {
        logger.info("Entering deleteFromGroup addToGroup method ");
        List<TouristGroup> touristGroupObject = null;
        boolean touristExistFlag = false;

        try {
            touristGroupObject = touristGroupDelegate.getGroup(groupMember.getGroupId());
            if (touristGroupObject.size() == 1
                    && StringUtils.equals(groupMember.getGroupId(), touristGroupObject.get(0).getGroupId())) {
                touristDelegate.deleteFromGroup(groupMember);
                // DELETing THE ENTIRE GROUP IF THE GROUP HAS NO MORE MEMBERS
                if (groupMembersDelegate.getGroupMembersById(groupMember.getGroupId()).size() == 0) {
                    touristGroupServiceImpl.deleteGroup(groupMember.getGroupId());
                }
            } else {
                touristExistFlag = true;
                throw new EndUserException();
            }
        } catch (Exception e) {
            logger.error("TouristServiceImpl deleteFromGroup method throw exception ", e);
            if (touristExistFlag)
                throw new EndUserException(EndUserConstants.HttpErrorCode_562, "Error!");
            throw new EndUserException(EndUserConstants.HttpErrorCode_450, e.getMessage());
        }
    }

    @GET
    @Secured({Role.SuperAdmin, Role.Admin, Role.User})
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @Path("/showmygroup")
    public List<TouristGroup> getMyGroups(@QueryParam("emailAddress") String emailAddress) {
        logger.info("Entering TouristServiceImpl getMygroups method");
        try {
            return touristDelegate.getMyGroups(emailAddress);
        } catch (Exception e) {
            logger.error("TouristServiceImpl getMyGroups method throw exception ", e);
            throw new EndUserException(EndUserConstants.HttpErrorCode_450, e.getMessage());
        }
    }

}
