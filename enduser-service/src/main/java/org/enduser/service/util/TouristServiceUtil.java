package org.enduser.service.util;

import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;
import org.enduser.service.delegates.GroupMembersDelegate;
import org.enduser.service.delegates.UserGenericServiceDelegate;
import org.enduser.service.exception.EndUserException;
import org.enduser.service.model.AdminUser;
import org.enduser.service.model.AuthenticationData;
import org.enduser.service.model.Credentials;
import org.enduser.service.model.GroupMember;
import org.enduser.service.model.OperatorTour;
import org.enduser.service.model.PasswordReset;
import org.enduser.service.model.Tourist;
import org.enduser.service.model.util.EnrollementCredentials;
import org.enduser.service.model.util.ResetPassword;
import org.jasypt.digest.config.SimpleDigesterConfig;
import org.jasypt.util.password.ConfigurablePasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TouristServiceUtil {
    private static final Logger logger = Logger.getLogger(TouristServiceUtil.class);

    private SendMailUtil sendMailUtil;
    private UserGenericServiceDelegate userGenericServiceDelegate;
    private GroupMembersDelegate groupMembersDelegate;

    @Autowired
    public void setSendMailUtil(SendMailUtil sendMailUtil) {
        this.sendMailUtil = sendMailUtil;
    }

    @Autowired
    public void setUserGenericServiceDelegate(UserGenericServiceDelegate userGenericServiceDelegate) {
        this.userGenericServiceDelegate = userGenericServiceDelegate;
    }

    @Autowired
    public void setGroupMembersDelegate(GroupMembersDelegate groupMembersDelegate) {
        this.groupMembersDelegate = groupMembersDelegate;
    }



    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public Date getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        Date dateObject = null;
        try {
            dateObject = dateFormat.parse(dateFormat.format(date));
        } catch (ParseException e1) {
            logger.error("Date format internal Exception throwen from TouristServiceUtil.getDateTime  ", e1);
            throw new EndUserException();
        }
        return dateObject;
    }

    public String getSimpleDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public boolean validateEmail(String emailAddress) {
        logger.debug("Entered validating email address method ");
        Pattern mypattern = Pattern.compile(EMAIL_PATTERN);
        Matcher mymatcher = mypattern.matcher(emailAddress);

        return mymatcher.matches();
    }

    public AuthenticationData setAuthenticationData(Tourist tourist) {
        logger.debug("Entered TouristServiceUtil setAuthenticationData method ");
        AuthenticationData authenticationData = new AuthenticationData();
        authenticationData.setEmailAddress(tourist.getEmailAddress());
        authenticationData.setFirstName(tourist.getFirstName());
        authenticationData.setRole("User");
        authenticationData.setRegistrationDate(getDateTime());
        authenticationData.setActivationCode(RandomStringUtils.randomAlphanumeric(10));
        authenticationData.setStatus("Inactive");
        authenticationData.setPassword(encryptPassword(tourist.getPassword()));

        return authenticationData;
    }

    public AuthenticationData setAuthenticationData(AdminUser adminUser) {
        logger.debug("Entered TouristServiceUtil setAuthenticationData method ");
        AuthenticationData authenticationData = new AuthenticationData();
        authenticationData.setEmailAddress(adminUser.getEmailAddress());
        authenticationData.setFirstName(adminUser.getFirstName());
        authenticationData.setRole("Admin");
        authenticationData.setRegistrationDate(getDateTime());
        authenticationData.setActivationCode(RandomStringUtils.randomAlphanumeric(10));
        authenticationData.setStatus("New User");
        authenticationData.setPassword(encryptPassword(adminUser.getPassword()));

        return authenticationData;
    }

    public AuthenticationData setAuthenticationData(Credentials credentials) {
        logger.debug("Entered TouristServiceUtil setTuristDetail method ");
        AuthenticationData authenticationData = new AuthenticationData();
        authenticationData.setEmailAddress(credentials.getEmailAddress());
        authenticationData.setRole("Operator");
        authenticationData.setRegistrationDate(getDateTime());
        authenticationData.setActivationCode(RandomStringUtils.randomAlphanumeric(10));
        authenticationData.setStatus("New User");
        authenticationData.setGroupSeasonFlag(false);
        authenticationData.setCarRentalOnly(false);
        authenticationData.setPassword(encryptPassword(credentials.getPassword()));
        authenticationData.setCountry(credentials.getCountry());

        return authenticationData;
    }
    
    public AuthenticationData setAuthenticationData(EnrollementCredentials enrollementCredentials) {
        logger.debug("Entered TouristServiceUtil setTuristDetail method ");
        AuthenticationData authenticationData = new AuthenticationData();
        authenticationData.setEmailAddress(enrollementCredentials.getEmailAddress());
        authenticationData.setRole("Operator");
        authenticationData.setRegistrationDate(getDateTime());
        authenticationData.setActivationCode(RandomStringUtils.randomAlphanumeric(10));
        authenticationData.setStatus("New User");
      //  authenticationData.setStatus("Unconfirmed");
        authenticationData.setGroupSeasonFlag(false);
        authenticationData.setCarRentalOnly(false);
        authenticationData.setPassword(encryptPassword(enrollementCredentials.getPassword()));
        authenticationData.setCountry(enrollementCredentials.getCountry());
        authenticationData.setFirstName(enrollementCredentials.getCompanyName());

        return authenticationData;
    }

    /*
     * public AdminUser setAdminUserDetail(AdminUser adminUser) {
     * logger.debug("Entered TouristServiceUtil setAdminUserDetail method ");
     * adminUser.setRegistrationDate(getDateTime());
     * adminUser.setActivationCode(RandomStringUtils.randomAlphanumeric(10));
     * adminUser.setStatus("New User");
     * adminUser.setPassword(encryptPassword(adminUser.getPassword()));
     * 
     * return adminUser; }
     */

    public String encryptPassword(String password) {
        SimpleDigesterConfig digesterConfig = new SimpleDigesterConfig();
        digesterConfig.setIterations(5000);
        digesterConfig.setSaltSizeBytes(16);

        ConfigurablePasswordEncryptor passwordEncryptor = new ConfigurablePasswordEncryptor();
        passwordEncryptor.setConfig(digesterConfig);
        passwordEncryptor.setAlgorithm("MD5");
        passwordEncryptor.setPlainDigest(false);

        return passwordEncryptor.encryptPassword(password);
    }

    public boolean authenticateUser(String password, String encryptedPassword) {
        ConfigurablePasswordEncryptor passwordEncryptor = new ConfigurablePasswordEncryptor();

        return passwordEncryptor.checkPassword(password, encryptedPassword);
    }

    public GroupMember setGroupMemberParameters(String groupId, String emailAddress) {
        GroupMember groupMember = new GroupMember(emailAddress, groupId, "White");
        return groupMember;
    }

    public OperatorTour setOperatorTour(String operatorId, String tourId) {
        OperatorTour operatorTour = new OperatorTour(operatorId, tourId);
        operatorTour.setOperatorId(operatorId);
        operatorTour.setTourId(tourId);
        return operatorTour;
    }

    public AuthenticationData setFields(Tourist tourist, String statusChangeReason) {
        AuthenticationData authenticationData = new AuthenticationData();
        authenticationData.setStatusChangeDate(getDateTime());
        authenticationData.setStatusChangeReason(statusChangeReason);
        authenticationData.setStatus("In-Active");
        authenticationData.setEmailAddress(tourist.getEmailAddress());

        return authenticationData;
    }

    public List<AuthenticationData> getUserNameToken(String emailAddress) {
        return userGenericServiceDelegate.getUserNameToken(emailAddress);
    }

    public void updateUserNameToken(String emailAddress, String token) {
        AuthenticationData authenticationDataObject = new AuthenticationData();
        authenticationDataObject.setEmailAddress(emailAddress);
        authenticationDataObject.setToken(token);

        userGenericServiceDelegate.updateUserNameToken(authenticationDataObject);
    }

    public String generateToken() {
        SecureRandom random = new SecureRandom();
        return new BigInteger(130, random).toString(32);
    }

    public PasswordReset saveResetData(String emailAddress, String userType) {
        PasswordReset passwordResetObj = new PasswordReset();
        String resetToken = generateToken();
        passwordResetObj.setEmailAddress(emailAddress);
        passwordResetObj.setResetToken(resetToken);
        passwordResetObj.setExpirationDate(DateUtils.addHours(getDateTime(), 1));

        return userGenericServiceDelegate.saveResetData(passwordResetObj);

    }

    public void generateTempUrl(PasswordReset passwordResetObject, String emailAddress, String userType) throws EndUserException {
        URL url = null;

        try {
            if (StringUtils.equals("User", userType)) {
                url =
                        new URL("http://localhost:7001/enduser/service/user/resetpswd/uid?" + passwordResetObject.getResetToken()
                                + "expd?" + (passwordResetObject.getExpirationDate().getTime()) / 1000);
            } else if (StringUtils.equals("Admin", userType)) {
                url =
                        new URL("http://localhost:7001/enduser/service/user/resetpswd/uid?" + passwordResetObject.getResetToken()
                                + "expd?" + (passwordResetObject.getExpirationDate().getTime()) / 1000);
            } else if (StringUtils.equals("Operator", userType)) {
                url =
                        new URL("http://localhost:7001/enduser/service/user/resetpswd/uid?" + passwordResetObject.getResetToken()
                                + "expd?" + (passwordResetObject.getExpirationDate().getTime()) / 1000);
            }
        } catch (MalformedURLException e) {
            throw new EndUserException(e);
        } catch (Exception e) {
            throw new EndUserException(e);
        }
        sendMailUtil.sendPasswordResetEmail(url, emailAddress);
    }

    public void informGroupMembers(String newMember, String groupId) {
        List<GroupMember> groupMembersList = groupMembersDelegate.getGroupMembersById(groupId);
        List<String> emailList = new ArrayList<String>();
        if (groupMembersList.size() != 0) {
            for (GroupMember member : groupMembersList) {
                emailList.add(member.getEmailAddress());
            }
            sendMailUtil.newGroupMemberEmail(newMember, emailList);
        }
    }

    public void sendPasswordResetEmail(ResetPassword resetPassword) {
        try {
            sendMailUtil.sendPasswordResetEmail(resetPassword);
        } catch (Exception e) {
            throw new EndUserException(e);
        }
    }

}
