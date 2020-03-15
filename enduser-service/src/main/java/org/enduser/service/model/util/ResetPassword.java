package org.enduser.service.model.util;

import org.springframework.stereotype.Component;

@Component
public class ResetPassword {
    private String emailAddress;
    private String currentPassword;
    private String newPassword;
    
    public ResetPassword(){
        super();
    }
    public ResetPassword(String emailAddress, String currentPassword, String newPassword) {
        super();
        this.emailAddress = emailAddress;
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
    }
    
    
    public String getEmailAddress() {
        return emailAddress;
    }
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
    public String getCurrentPassword() {
        return currentPassword;
    }
    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }
    public String getNewPassword() {
        return newPassword;
    }
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
    
    @Override
    public String toString() {
        return "ResetPassword [emailAddress=" + emailAddress + ", currentPassword=" + currentPassword + ", newPassword="
                + newPassword + "]";
    }      

}
