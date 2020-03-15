package org.enduser.service.model.util;

import java.io.Serializable;

import org.springframework.stereotype.Component;


@Component
public class EnrollementCredentials implements Serializable {

    private static final long serialVersionUID = 1L;

    private String emailAddress;
    private String password;
    private String country;
    private String companyName;
    
    
    public String getEmailAddress() {
        return emailAddress;
    }
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public String getCompanyName() {
        return companyName;
    }
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

}
