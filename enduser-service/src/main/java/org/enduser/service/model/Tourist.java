package org.enduser.service.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.stereotype.Component;

@Entity
@DynamicUpdate
@Component
public class Tourist {

    @Id
    @Column(name = "EMAIL_ADDRESS", nullable = false)
    private String emailAddress;
    @Column(name = "FIRST_NAME", nullable = false)
    private String firstName;
    @Column(name = "MIDDLE_NAME")
    private String middleName;
    @Column(name = "LAST_NAME", nullable = false)
    private String lastName;
    @Column(name = "PASSWORD")
    private String password;
    @Column(name = "PHONE_NUMBER", nullable = false)
    private String phoneNumber;
    @Column(name = "HOME_ADDRESS")
    private String homeAddress;
    @Column(name = "ZIP_CODE")
    private String zipCode;
    @Column(name = "STATE")
    private String state;
    @Column(name = "CITY")
    private String city;
    @Column(name = "COUNTRY_OF_RESIDENCE", nullable = false)
    private String countryOfResidence;
    @Column(name = "DATE_OF_BIRTH", nullable = false)
    private Date dateOfBirth;
    @Column(name = "SEX", nullable = false)
    private String sex;

    @Column(name = "REGISTRATION_DATE")
    private Date registrationDate;

    @Transient
    private AuthenticationData authenticationData;


    public Tourist() {}

    public Tourist(String emailAddress, String firstName, String middleName, String lastName, String phoneNumber,
            String homeAddress, String zipCode, String state, String city, String countryOfResidence, Date dateOfBirth,
            String sex, String password, Date registrationDate) {
        super();
        this.emailAddress = emailAddress;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.homeAddress = homeAddress;
        this.zipCode = zipCode;
        this.state = state;
        this.city = city;
        this.countryOfResidence = countryOfResidence;
        this.dateOfBirth = dateOfBirth;
        this.sex = sex;
        this.password = password;
        this.registrationDate = registrationDate;
    }


    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountryOfResidence() {
        return countryOfResidence;
    }

    public void setCountryOfResidence(String countryOfResidence) {
        this.countryOfResidence = countryOfResidence;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AuthenticationData getAuthenticationData() {
        return authenticationData;
    }

    public void setAuthenticationData(AuthenticationData authenticationData) {
        this.authenticationData = authenticationData;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    @Override
    public String toString() {
        return "Tourist [emailAddress=" + emailAddress + ", firstName=" + firstName + ", middleName=" + middleName
                + ", lastName=" + lastName + ", password=" + password + ", phoneNumber=" + phoneNumber + ", homeAddress="
                + homeAddress + ", zipCode=" + zipCode + ", state=" + state + ", city=" + city + ", countryOfResidence="
                + countryOfResidence + ", dateOfBirth=" + dateOfBirth + ", sex=" + sex + "registrationDate=" + registrationDate
                + "]";
    }

}
