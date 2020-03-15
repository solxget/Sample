package org.enduser.service.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.stereotype.Component;

@Entity
@DynamicUpdate
@Component
public class TourTransaction {

    @Id
    @GeneratedValue
    private int bookedTour_sequence_num;

    // ADD TAX, CARD HOLDERS ADDRESS, AND OTHER DETAILS
    // https://www.simplify.com/commerce/docs/apidoc/authorization?language=java#create
    @Column(name = "EMAIL_ADDRESS", nullable = false)
    private String emailAddress;
    @Column(name = "TOUR_ID", nullable = false)
    // Also Car Rental Id
    private String tourId;
    @Column(name = "OPERATOR_ID", nullable = false)
    private String operatorId;
    @Column(name = "PAYMENT_AMOUNT", nullable = false)
    private int paymentAmount;
    @Column(name = "CURRENCY", nullable = false)
    private String currency;
    @Column(name = "DESCRIPTION", nullable = false)
    private String description;
    @Column(name = "PAYMENT_DATE", nullable = false)
    private Date paymentDate;
    @Column(name = "TRIP_DATE")
    private Date tripDate;
    @Column(name = "SUPPLIED_EMAILADDRESS", nullable = false)
    private String suppliedEmailAddress;
    @Column(name = "FIRST_NAME", nullable = false)
    private String firstName;
    @Column(name = "LAST_NAME", nullable = false)
    private String lastName;
    @Column(name = "PHONE_NUMBER", nullable = false)
    private String phoneNumber;
    @Column(name = "PAYMENT_ID", nullable = false)
    private String paymentId;

    // @GeneratedValue
    @Column(name = "CONFIRMATION_NUMBER", nullable = false)
    // an auto increment field
    private String confirmationNumber;
    @Column(name = "REFUND_FLAG", nullable = false)
    // shouldn't be null at the beginning
    private boolean refundFlag;
    @Column(name = "REFUND_Date")
    private Date refundDate;
    @Column(name = "TRIP_DURATION")
    private int tripDuration; // also car rental duration

    @Column(name = "TOUR_PRICE")
    private double tourPrice;
    @Column(name = "TAX")
    private double tax;
    @Column(name = "CARD_HOLDERS_NAME")
    private String cardHoldersname;
    @Column(name = "PASSPORT_COUNTRY")
    private String passportCountry;
    @Column(name = "SEX")
    private String sex;
    @Column(name = "DATE_OF_BIRTH")
    private Date dateOfBirth;
    @Column(name = "COUNTRY")
    private String country;
    @Column(name = "BILLING_ADDRESS1")
    private String billingAddress1;
    @Column(name = "BILLING_ADDRESS2")
    private String billingAddress2;
    @Column(name = "CITY")
    private String city;
    @Column(name = "STATE")
    private String state;
    @Column(name = "ZIP_CODE")
    private String zipCode;
    @Column(name = "TRIP_COUNTRY")
    private String tripCountry;
    @Column(name = "GROUP_SIZE")
    private int groupSize;

    @Transient
    private String token;

    @Transient
    private String operatorName;
    @Transient
    private String operatorPhone;
    @Transient
    private boolean airportPickUp;
    @Transient
    private String tourStartPlace;
    @Transient
    private String endingPlace;
    @Transient
    private String operatorEmailAddress;
    @Transient
    private String tourType;
    @Transient
    private String tourRoute;

    public TourTransaction() {}

    public TourTransaction(int bookedTour_sequence_num, String emailAddress, String tourId, String operatorId,
            int paymentAmount, String currency, String description, Date paymentDate, Date tripDate,
            String suppliedEmailAddress, String firstName, String lastName, String phoneNumber, String paymentId,
            String confirmationNumber, boolean refundFlag, Date refundDate, int tripDuration, double tourPrice, double tax,
            String cardHoldersname, String passportCountry, String sex, Date dateOfBirth, String country, String billingAddress1,
            String billingAddress2, String city, String state, String zipCode, String tripCountry, int groupSize) {
        super();
        this.bookedTour_sequence_num = bookedTour_sequence_num;
        this.emailAddress = emailAddress;
        this.tourId = tourId;
        this.operatorId = operatorId;
        this.paymentAmount = paymentAmount;
        this.currency = currency;
        this.description = description;
        this.paymentDate = paymentDate;
        this.tripDate = tripDate;
        this.suppliedEmailAddress = suppliedEmailAddress;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.paymentId = paymentId;
        this.confirmationNumber = confirmationNumber;
        this.refundFlag = refundFlag;
        this.refundDate = refundDate;
        this.tripDuration = tripDuration;
        this.tourPrice = tourPrice;
        this.tax = tax;
        this.cardHoldersname = cardHoldersname;
        this.passportCountry = passportCountry;
        this.sex = sex;
        this.dateOfBirth = dateOfBirth;
        this.country = country;
        this.billingAddress1 = billingAddress1;
        this.billingAddress2 = billingAddress2;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.tripCountry = tripCountry;
        this.groupSize = groupSize;
    }


    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getTourId() {
        return tourId;
    }

    public void setTourId(String tourId) {
        this.tourId = tourId;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public int getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(int paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Date getTripDate() {
        return tripDate;
    }

    public void setTripDate(Date tripDate) {
        this.tripDate = tripDate;
    }

    public String getSuppliedEmailAddress() {
        return suppliedEmailAddress;
    }

    public void setSuppliedEmailAddress(String suppliedEmailAddress) {
        this.suppliedEmailAddress = suppliedEmailAddress;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
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

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getConfirmationNumber() {
        return confirmationNumber;
    }

    public void setConfirmationNumber(String confirmationNumber) {
        this.confirmationNumber = confirmationNumber;
    }

    public boolean isRefundFlag() {
        return refundFlag;
    }

    public void setRefundFlag(boolean refundFlag) {
        this.refundFlag = refundFlag;
    }

    public Date getRefundDate() {
        return refundDate;
    }

    public void setRefundDate(Date refundDate) {
        this.refundDate = refundDate;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getTripDuration() {
        return tripDuration;
    }

    public void setTripDuration(int tripDuration) {
        this.tripDuration = tripDuration;
    }

    public double getTourPrice() {
        return tourPrice;
    }

    public void setTourPrice(double tourPrice) {
        this.tourPrice = tourPrice;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public String getCardHoldersname() {
        return cardHoldersname;
    }

    public void setCardHoldersname(String cardHoldersname) {
        this.cardHoldersname = cardHoldersname;
    }

    public String getPassportCountry() {
        return passportCountry;
    }

    public void setPassportCountry(String passportCountry) {
        this.passportCountry = passportCountry;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getBillingAddress1() {
        return billingAddress1;
    }

    public void setBillingAddress1(String billingAddress1) {
        this.billingAddress1 = billingAddress1;
    }

    public String getBillingAddress2() {
        return billingAddress2;
    }

    public void setBillingAddress2(String billingAddress2) {
        this.billingAddress2 = billingAddress2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

    public String getTripCountry() {
        return tripCountry;
    }

    public void setTripCountry(String tripCountry) {
        this.tripCountry = tripCountry;
    }

    public int getGroupSize() {
        return groupSize;
    }

    public void setGroupSize(int groupSize) {
        this.groupSize = groupSize;
    }
    
    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getOperatorPhone() {
        return operatorPhone;
    }

    public void setOperatorPhone(String operatorPhone) {
        this.operatorPhone = operatorPhone;
    }

    public String getEndingPlace() {
        return endingPlace;
    }

    public void setEndingPlace(String endingPlace) {
        this.endingPlace = endingPlace;
    }

    public boolean isAirportPickUp() {
        return airportPickUp;
    }

    public void setAirportPickUp(boolean airportPickUp) {
        this.airportPickUp = airportPickUp;
    }

    public String getTourStartPlace() {
        return tourStartPlace;
    }

    public void setTourStartPlace(String tourStartPlace) {
        this.tourStartPlace = tourStartPlace;
    }
    
    public String getOperatorEmailAddress() {
        return operatorEmailAddress;
    }

    public void setOperatorEmailAddress(String operatorEmailAddress) {
        this.operatorEmailAddress = operatorEmailAddress;
    }

    public String getTourType() {
        return tourType;
    }

    public void setTourType(String tourType) {
        this.tourType = tourType;
    }

    public String getTourRoute() {
        return tourRoute;
    }

    public void setTourRoute(String tourRoute) {
        this.tourRoute = tourRoute;
    }

    @Override
    public String toString() {
        return "TourTransaction [bookedTour_sequence_num=" + bookedTour_sequence_num + ", emailAddress=" + emailAddress
                + ", tourId=" + tourId + ", operatorId=" + operatorId + ", paymentAmount=" + paymentAmount + ", currency="
                + currency + ", description=" + description + ", paymentDate=" + paymentDate + ", tripDate=" + tripDate
                + ", suppliedEmailAddress=" + suppliedEmailAddress + ", firstName=" + firstName + ", lastName=" + lastName
                + ", phoneNumber=" + phoneNumber + ", paymentId=" + paymentId + ", confirmationNumber=" + confirmationNumber
                + ", refundFlag=" + refundFlag + ", refundDate=" + refundDate + ", tripDuration=" + tripDuration + ", tourPrice="
                + tourPrice + ", tax=" + tax + ", cardHoldersname=" + cardHoldersname + ", passportCountry=" + passportCountry
                + ", sex=" + sex + ", dateOfBirth=" + dateOfBirth + ", country=" + country + ", billingAddress1="
                + billingAddress1 + ", billingAddress2=" + billingAddress2 + ", city=" + city + ", state=" + state + ", zipCode="
                + zipCode + ", tripCountry=" + tripCountry + "]";
    }

}
