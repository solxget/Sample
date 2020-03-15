package org.enduser.service.model;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.stereotype.Component;

@Entity
@DynamicUpdate
@Component
public class Tour {

    // DELETE NULLABLE FALSE FIELDS
    @Id
    @Column(name = "TOUR_ID", nullable = false)
    private String tourId;
    @Column(name = "TOUR_ROUTE")
    private String tourRoute;
    @Column(name = "TOUR_DURATION")
    private String tourDuration;
    @Column(name = "TOUR_MAX_SIZE")
    private String tourMaxSize;
    @Column(name = "TOUR_MIN_SIZE")
    private String tourMinSize;
    @Column(name = "TOUR_TYPE", nullable = false)
    private String tourType;
    @Column(name = "TOUR_START_PLACE")
    private String tourStartPlace;
    @Column(name = "TOUR_START_TIME")
    private String tourStartTime;
    @Column(name = "TOUR_START_DATE")
    private Date tourStartDate; // probably not necessary for year round tours
    @Column(name = "TOUR_END_DATE")
    private Date tourEndDate;

    @Column(name = "OPERATOR_ID")
    private String operatorId;
    @Column(name = "REGISTRATION_DATE")
    private Date registrationDate;
    @Column(name = "COUNTRY")
    private String country;

    @Column(name = "SERVCIE_LEVEL")
    private String serviceLevel;
    @Column(name = "MIN_AGE")
    private int minAge;
    @Column(name = "TRANSPORTATION")
    private String[] transportation;
    @Column(name = "PHYSCIAL_DEMAND")
    private String physicalDemand;
    @Column(name = "ENDING_PLACE")
    private String endingPlace;
    @Column(name = "WITH_GUIDE")
    private boolean withGuide;

    @Transient
    private Double tourPrice;

    @Transient
    private int groupSize;

    @Transient
    private List<ExchangeRate> exchangeRate;

    @Transient
    private TourOperator tourOperator;

    @Transient
    private Group1Price group1Price;
    @Transient
    private Group2Price group2Price;
    @Transient
    private Group3Price group3Price;
    @Transient
    private Group4Price group4Price;
    @Transient
    private Group5Price group5Price;
    @Transient
    private TourRoute tourRouteObject;

    @Transient
    private Group1Price exc_Group1Price;
    @Transient
    private Group2Price exc_Group2Price;
    @Transient
    private Group3Price exc_Group3Price;
    @Transient
    private Group4Price exc_Group4Price;
    @Transient
    private Group5Price exc_Group5Price;

    /*
     * @OneToMany on list property here denotes that one University can have multiple students.With
     * students property defined in University class, we can now navigate from University to
     * students. mappedBy says that it�s the inverse side of relationship. Also note the cascade
     * attribute, which means the dependent object(Student) will be persisted/updated/deleted
     * automatically on subsequent persist/update/delete on University object.No need to perform
     * operation separately on Student.
     * 
     * @JoinColumn says that Student table will contain a separate column UNIVERSITY_ID which will
     * eventually act as a foreign key reference to primary key of University table. @ManyToOne says
     * that multiple Student tuples can refer to same University Tuples(Multiple students can
     * register in same university).Additionally , with optional=false we make sure that no Student
     * tuple can exist without a University tuple.
     */

    /*
     * @ManyToOne(optional = false)
     * 
     * @JoinColumn(name="OPERATOR_ID") private TourOperator tourOperator;
     */

    public Tour() {}

    public Tour(String tourId, String tourRoute, String tourDuration, String tourMaxSize, String tourMinSize, String tourType,
            String tourStartPlace, String tourStartTime, Date tourStartDate, Date tourEndDate, Double tourPrice,
            String operatorId, Date registrationDate, String country, String serviceLevel, int minAge, String[] transportation,
            String physicalDemand, String endingPlace, boolean withGuide) {

        this.tourId = tourId;
        this.tourRoute = tourRoute;
        this.tourDuration = tourDuration;
        this.tourMaxSize = tourMaxSize;
        this.tourMinSize = tourMinSize;
        this.tourType = tourType;
        this.tourStartPlace = tourStartPlace;
        this.tourStartTime = tourStartTime;
        this.tourStartDate = tourStartDate;
        this.tourEndDate = tourEndDate;
        this.tourPrice = tourPrice;
        // this.tourInclusivePrice = tourInclusivePrice;

        this.operatorId = operatorId;
        this.registrationDate = registrationDate;
        this.country = country;

        this.serviceLevel = serviceLevel;
        this.minAge = minAge;
        this.transportation = transportation;
        this.physicalDemand = physicalDemand;
        this.endingPlace = endingPlace;
        this.withGuide = withGuide;
    }

    public String getTourId() {
        return tourId;
    }

    public void setTourId(String tourId) {
        this.tourId = tourId;
    }

    public String getTourRoute() {
        return tourRoute;
    }

    public void setTourRoute(String tourRoute) {
        this.tourRoute = tourRoute;
    }

    public String getTourDuration() {
        return tourDuration;
    }

    public void setTourDuration(String tourDuration) {
        this.tourDuration = tourDuration;
    }

    public String getTourMaxSize() {
        return tourMaxSize;
    }

    public void setTourMaxSize(String tourMaxSize) {
        this.tourMaxSize = tourMaxSize;
    }

    public String getTourMinSize() {
        return tourMinSize;
    }

    public void setTourMinSize(String tourMinSize) {
        this.tourMinSize = tourMinSize;
    }

    public String getTourType() {
        return tourType;
    }

    public void setTourType(String tourType) {
        this.tourType = tourType;
    }

    public String getTourStartPlace() {
        return tourStartPlace;
    }

    public void setTourStartPlace(String tourStartPlace) {
        this.tourStartPlace = tourStartPlace;
    }

    public String getTourStartTime() {
        return tourStartTime;
    }

    public void setTourStartTime(String tourStartTime) {
        this.tourStartTime = tourStartTime;
    }

    public Date getTourStartDate() {
        return tourStartDate;
    }

    public void setTourStartDate(Date tourStartDate) {
        this.tourStartDate = tourStartDate;
    }

    public Date getTourEndDate() {
        return tourEndDate;
    }

    public void setTourEndDate(Date tourEndDate) {
        this.tourEndDate = tourEndDate;
    }

    public Double getTourPrice() {
        return tourPrice;
    }

    public void setTourPrice(Double tourPrice) {
        this.tourPrice = tourPrice;
    }

    /*
     * public TourOperator getTourOperator() { return tourOperator; } public void
     * setTourOperator(TourOperator tourOperator) { this.tourOperator = tourOperator; }
     */

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    /*
     * public Double getTourInclusivePrice() { return tourInclusivePrice; }
     * 
     * public void setTourInclusivePrice(Double tourInclusivePrice) { this.tourInclusivePrice =
     * tourInclusivePrice; }
     */


    public TourOperator getTourOperator() {
        return tourOperator;
    }

    public void setTourOperator(TourOperator tourOperator) {
        this.tourOperator = tourOperator;
    }


    public Group1Price getGroup1Price() {
        return group1Price;
    }

    public void setGroup1Price(Group1Price group1Price) {
        this.group1Price = group1Price;
    }

    public Group2Price getGroup2Price() {
        return group2Price;
    }

    public void setGroup2Price(Group2Price group2Price) {
        this.group2Price = group2Price;
    }

    public Group3Price getGroup3Price() {
        return group3Price;
    }

    public void setGroup3Price(Group3Price group3Price) {
        this.group3Price = group3Price;
    }

    public Group4Price getGroup4Price() {
        return group4Price;
    }

    public void setGroup4Price(Group4Price group4Price) {
        this.group4Price = group4Price;
    }

    public Group5Price getGroup5Price() {
        return group5Price;
    }

    public void setGroup5Price(Group5Price group5Price) {
        this.group5Price = group5Price;
    }

    public List<ExchangeRate> getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(List<ExchangeRate> exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public TourRoute getTourRouteObject() {
        return tourRouteObject;
    }

    public void setTourRouteObject(TourRoute tourRouteObject) {
        this.tourRouteObject = tourRouteObject;
    }

    public Group1Price getExc_Group1Price() {
        return exc_Group1Price;
    }

    public void setExc_Group1Price(Group1Price exc_Group1Price) {
        this.exc_Group1Price = exc_Group1Price;
    }

    public Group2Price getExc_Group2Price() {
        return exc_Group2Price;
    }

    public void setExc_Group2Price(Group2Price exc_Group2Price) {
        this.exc_Group2Price = exc_Group2Price;
    }

    public Group3Price getExc_Group3Price() {
        return exc_Group3Price;
    }

    public void setExc_Group3Price(Group3Price exc_Group3Price) {
        this.exc_Group3Price = exc_Group3Price;
    }

    public Group4Price getExc_Group4Price() {
        return exc_Group4Price;
    }

    public void setExc_Group4Price(Group4Price exc_Group4Price) {
        this.exc_Group4Price = exc_Group4Price;
    }

    public Group5Price getExc_Group5Price() {
        return exc_Group5Price;
    }

    public void setExc_Group5Price(Group5Price exc_Group5Price) {
        this.exc_Group5Price = exc_Group5Price;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public int getGroupSize() {
        return groupSize;
    }

    public void setGroupSize(int groupSize) {
        this.groupSize = groupSize;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getServiceLevel() {
        return serviceLevel;
    }

    public void setServiceLevel(String serviceLevel) {
        this.serviceLevel = serviceLevel;
    }

    public int getMinAge() {
        return minAge;
    }

    public void setMinAge(int minAge) {
        this.minAge = minAge;
    }

    public String[] getTransportation() {
        return transportation;
    }

    public void setTransportation(String[] transportation) {
        this.transportation = transportation;
    }

    public String getPhysicalDemand() {
        return physicalDemand;
    }

    public void setPhysicalDemand(String physicalDemand) {
        this.physicalDemand = physicalDemand;
    }

    public String getEndingPlace() {
        return endingPlace;
    }

    public void setEndingPlace(String endingPlace) {
        this.endingPlace = endingPlace;
    }

    public boolean isWithGuide() {
        return withGuide;
    }

    public void setWithGuide(boolean withGuide) {
        this.withGuide = withGuide;
    }

    @Override
    public String toString() {
        return "Tour [tourId=" + tourId + ", tourRoute=" + tourRoute + ", tourDuration=" + tourDuration + ", tourMaxSize="
                + tourMaxSize + ", tourMinSize=" + tourMinSize + ", tourType=" + tourType + ", tourStartPlace=" + tourStartPlace
                + ", tourStartTime=" + tourStartTime + ", tourStartDate=" + tourStartDate + ", tourEndDate=" + tourEndDate
                + ", operatorId=" + operatorId + ", registrationDate=" + registrationDate + ", country=" + country
                + ", serviceLevel=" + serviceLevel + ", minAge=" + minAge + ", transportation=" + Arrays.toString(transportation)
                + ", physicalDemand=" + physicalDemand + ", endingPlace=" + endingPlace + ", tourPrice=" + tourPrice
                + ", groupSize=" + groupSize + ", withGuide=" + withGuide + "]";
    }


}
