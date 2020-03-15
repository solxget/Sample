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
public class TourOperator {

    @Id
    @Column(name = "OPERATOR_ID", nullable = false)
    private String operatorId;
    @Column(name = "EMAIL_ADDRESS")
    private String emailAddress;
    @Column(name = "OPERATOR_NAME")
    private String operatorName;
    @Column(name = "RATING")
    private String rating;
    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;
    @Column(name = "FAX_NUMBER")
    private String faxNumber;
    @Column(name = "STREET_ADDRESS")
    private String streetAddress;
    @Column(name = "CITY")
    private String city;
    @Column(name = "STATE")
    private String state;
    @Column(name = "ZIP_CODE")
    private String zipCode;
    @Column(name = "AIRPORT_PICKUP")
    private boolean airportPickUp;

    @Column(name = "CAR_RENTAL_ONLY")
    private boolean carRentalOnly;
    @Column(name = "REGISTRATION_DATE")
    private Date registrationDate;
    @Column(name = "URL")
    private String url;
    @Column(name = "SECONDARY_PHONE_NUMBER")
    private String secondaryphoneNumber;

    @Transient
    private AuthenticationData authenticationData;

    // @OneToMany(mappedBy = "tourOperator", cascade = CascadeType.ALL)

    /*
     * @OneToMany(mappedBy = "tourOperator") private List<Tour> tours;
     */

    public TourOperator() {}

    public TourOperator(String operatorId, String emailAddress, String operatorName, String rating, String phoneNumber,
            String faxNumber, String streetAddress, String city, String state, String zipCode, boolean airportPickUp,
            boolean carRentalOnly, Date registrationDate, String url, String secondaryphoneNumber) {
        super();
        this.operatorId = operatorId;
        this.emailAddress = emailAddress;
        this.operatorName = operatorName;
        this.rating = rating;
        this.phoneNumber = phoneNumber;
        this.faxNumber = faxNumber;
        this.streetAddress = streetAddress;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.airportPickUp = airportPickUp;
        this.carRentalOnly = carRentalOnly;
        this.registrationDate = registrationDate;
        this.url = url;
        this.secondaryphoneNumber = secondaryphoneNumber;
    }



    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFaxNumber() {
        return faxNumber;
    }

    public void setFaxNumber(String faxNumber) {
        this.faxNumber = faxNumber;
    }


    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public boolean isAirportPickUp() {
        return airportPickUp;
    }

    public void setAirportPickUp(boolean airportPickUp) {
        this.airportPickUp = airportPickUp;
    }

    public AuthenticationData getAuthenticationData() {
        return authenticationData;
    }

    public void setAuthenticationData(AuthenticationData authenticationData) {
        this.authenticationData = authenticationData;
    }

    public boolean isCarRentalOnly() {
        return carRentalOnly;
    }

    public void setCarRentalOnly(boolean carRentalOnly) {
        this.carRentalOnly = carRentalOnly;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSecondaryphoneNumber() {
        return secondaryphoneNumber;
    }

    public void setSecondaryphoneNumber(String secondaryphoneNumber) {
        this.secondaryphoneNumber = secondaryphoneNumber;
    }

    /*
     * public List<Tour> getTours() { return tours; } public void setTours(List<Tour> tours) {
     * this.tours = tours; }
     */


    @Override
    public String toString() {
        return "TourOperator [operatorId=" + operatorId + ", emailAddress=" + emailAddress + ", operatorName=" + operatorName
                + ", rating=" + rating + ", phoneNumber=" + phoneNumber + ", faxNumber=" + faxNumber + ", streetAddress="
                + streetAddress + ", city=" + city + ", state=" + state + ", zipCode=" + zipCode + ", airportPickUp="
                + airportPickUp + ", carRentalOnly=" + carRentalOnly + ", registrationDate=" + registrationDate + ", url=" + url
                + ", secondaryphoneNumber=" + secondaryphoneNumber + "]";
    }

}
