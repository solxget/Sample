package org.enduser.service.model.util;

import java.util.Arrays;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class TourRegistration {

    private String country;
    private String tourId;
    private String operatorId;
    private String tourRoute;
    private String tourDuration;
    private String tourMaxSize;
    private String tourMinSize;
    private String tourType;
    private String tourStartPlace;
    private String tourStartTime;
    private Date tourStartDate;
    private Date tourEndDate;
    
    private String serviceLevel;
    private int minAge;
    private String[] transportation;
    private String physicalDemand;
    private String endingPlace;
    private boolean withGuide;

    private String tourDescription;

    private boolean inclusiveFlag;
    private double inclusive_g1S1;
    private double inclusive_g1S2;
    private double inclusive_g1S3;
    private double inclusive_g1S4;
    private double inclusive_g1S5;

    private double inclusive_g2S1;
    private double inclusive_g2S2;
    private double inclusive_g2S3;
    private double inclusive_g2S4;
    private double inclusive_g2S5;

    private double inclusive_g3S1;
    private double inclusive_g3S2;
    private double inclusive_g3S3;
    private double inclusive_g3S4;
    private double inclusive_g3S5;

    private double inclusive_g4S1;
    private double inclusive_g4S2;
    private double inclusive_g4S3;
    private double inclusive_g4S4;
    private double inclusive_g4S5;

    private double inclusive_g5S1;
    private double inclusive_g5S2;
    private double inclusive_g5S3;
    private double inclusive_g5S4;
    private double inclusive_g5S5;
    
// Exclusive goes here    
    private double exclusive_g1S1;
    private double exclusive_g1S2;
    private double exclusive_g1S3;
    private double exclusive_g1S4;
    private double exclusive_g1S5;

    private double exclusive_g2S1;
    private double exclusive_g2S2;
    private double exclusive_g2S3;
    private double exclusive_g2S4;
    private double exclusive_g2S5;

    private double exclusive_g3S1;
    private double exclusive_g3S2;
    private double exclusive_g3S3;
    private double exclusive_g3S4;
    private double exclusive_g3S5;

    private double exclusive_g4S1;
    private double exclusive_g4S2;
    private double exclusive_g4S3;
    private double exclusive_g4S4;
    private double exclusive_g4S5;

    private double exclusive_g5S1;
    private double exclusive_g5S2;
    private double exclusive_g5S3;
    private double exclusive_g5S4;
    private double exclusive_g5S5;

    public TourRegistration() {}

    public TourRegistration(String country, String tourId, String operatorId, String tourRoute, String tourDuration,
            String tourMaxSize, String tourMinSize, String tourType, String tourStartPlace, String tourStartTime,
            Date tourStartDate, Date tourEndDate, String serviceLevel, int minAge, String[] transportation, boolean withGuide,
            String physicalDemand, String endingPlace, String tourDescription, boolean inclusiveFlag, double inclusive_g1S1,
            double inclusive_g1S2, double inclusive_g1S3, double inclusive_g1S4, double inclusive_g1S5, double inclusive_g2S1,
            double inclusive_g2S2, double inclusive_g2S3, double inclusive_g2S4, double inclusive_g2S5, double inclusive_g3S1,
            double inclusive_g3S2, double inclusive_g3S3, double inclusive_g3S4, double inclusive_g3S5, double inclusive_g4S1,
            double inclusive_g4S2, double inclusive_g4S3, double inclusive_g4S4, double inclusive_g4S5, double inclusive_g5S1,
            double inclusive_g5S2, double inclusive_g5S3, double inclusive_g5S4, double inclusive_g5S5, double exclusive_g1S1,
            double exclusive_g1S2, double exclusive_g1S3, double exclusive_g1S4, double exclusive_g1S5, double exclusive_g2S1,
            double exclusive_g2S2, double exclusive_g2S3, double exclusive_g2S4, double exclusive_g2S5, double exclusive_g3S1,
            double exclusive_g3S2, double exclusive_g3S3, double exclusive_g3S4, double exclusive_g3S5, double exclusive_g4S1,
            double exclusive_g4S2, double exclusive_g4S3, double exclusive_g4S4, double exclusive_g4S5, double exclusive_g5S1,
            double exclusive_g5S2, double exclusive_g5S3, double exclusive_g5S4, double exclusive_g5S5) {
        super();
        this.country = country;
        this.tourId = tourId;
        this.operatorId = operatorId;
        this.tourRoute = tourRoute;
        this.tourDuration = tourDuration;
        this.tourMaxSize = tourMaxSize;
        this.tourMinSize = tourMinSize;
        this.tourType = tourType;
        this.tourStartPlace = tourStartPlace;
        this.tourStartTime = tourStartTime;
        this.tourStartDate = tourStartDate;
        this.tourEndDate = tourEndDate;
        this.serviceLevel = serviceLevel;
        this.minAge = minAge;
        this.transportation = transportation;
        this.physicalDemand = physicalDemand;
        this.endingPlace = endingPlace;
        this.withGuide = withGuide;
        this.tourDescription = tourDescription;
        this.inclusiveFlag = inclusiveFlag;
        this.inclusive_g1S1 = inclusive_g1S1;
        this.inclusive_g1S2 = inclusive_g1S2;
        this.inclusive_g1S3 = inclusive_g1S3;
        this.inclusive_g1S4 = inclusive_g1S4;
        this.inclusive_g1S5 = inclusive_g1S5;
        this.inclusive_g2S1 = inclusive_g2S1;
        this.inclusive_g2S2 = inclusive_g2S2;
        this.inclusive_g2S3 = inclusive_g2S3;
        this.inclusive_g2S4 = inclusive_g2S4;
        this.inclusive_g2S5 = inclusive_g2S5;
        this.inclusive_g3S1 = inclusive_g3S1;
        this.inclusive_g3S2 = inclusive_g3S2;
        this.inclusive_g3S3 = inclusive_g3S3;
        this.inclusive_g3S4 = inclusive_g3S4;
        this.inclusive_g3S5 = inclusive_g3S5;
        this.inclusive_g4S1 = inclusive_g4S1;
        this.inclusive_g4S2 = inclusive_g4S2;
        this.inclusive_g4S3 = inclusive_g4S3;
        this.inclusive_g4S4 = inclusive_g4S4;
        this.inclusive_g4S5 = inclusive_g4S5;
        this.inclusive_g5S1 = inclusive_g5S1;
        this.inclusive_g5S2 = inclusive_g5S2;
        this.inclusive_g5S3 = inclusive_g5S3;
        this.inclusive_g5S4 = inclusive_g5S4;
        this.inclusive_g5S5 = inclusive_g5S5;
        this.exclusive_g1S1 = exclusive_g1S1;
        this.exclusive_g1S2 = exclusive_g1S2;
        this.exclusive_g1S3 = exclusive_g1S3;
        this.exclusive_g1S4 = exclusive_g1S4;
        this.exclusive_g1S5 = exclusive_g1S5;
        this.exclusive_g2S1 = exclusive_g2S1;
        this.exclusive_g2S2 = exclusive_g2S2;
        this.exclusive_g2S3 = exclusive_g2S3;
        this.exclusive_g2S4 = exclusive_g2S4;
        this.exclusive_g2S5 = exclusive_g2S5;
        this.exclusive_g3S1 = exclusive_g3S1;
        this.exclusive_g3S2 = exclusive_g3S2;
        this.exclusive_g3S3 = exclusive_g3S3;
        this.exclusive_g3S4 = exclusive_g3S4;
        this.exclusive_g3S5 = exclusive_g3S5;
        this.exclusive_g4S1 = exclusive_g4S1;
        this.exclusive_g4S2 = exclusive_g4S2;
        this.exclusive_g4S3 = exclusive_g4S3;
        this.exclusive_g4S4 = exclusive_g4S4;
        this.exclusive_g4S5 = exclusive_g4S5;
        this.exclusive_g5S1 = exclusive_g5S1;
        this.exclusive_g5S2 = exclusive_g5S2;
        this.exclusive_g5S3 = exclusive_g5S3;
        this.exclusive_g5S4 = exclusive_g5S4;
        this.exclusive_g5S5 = exclusive_g5S5;
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
    public boolean isWithGuide() {
        return withGuide;
    }
    public void setWithGuide(boolean withGuide) {
        this.withGuide = withGuide;
    }
    public String getTourDescription() {
        return tourDescription;
    }
    public void setTourDescription(String tourDescription) {
        this.tourDescription = tourDescription;
    }
    public boolean isInclusiveFlag() {
        return inclusiveFlag;
    }
    public void setInclusiveFlag(boolean inclusiveFlag) {
        this.inclusiveFlag = inclusiveFlag;
    }
    public double getInclusive_g1S1() {
        return inclusive_g1S1;
    }
    public void setInclusive_g1S1(double inclusive_g1S1) {
        this.inclusive_g1S1 = inclusive_g1S1;
    }
    public double getInclusive_g1S2() {
        return inclusive_g1S2;
    }
    public void setInclusive_g1S2(double inclusive_g1S2) {
        this.inclusive_g1S2 = inclusive_g1S2;
    }
    public double getInclusive_g1S3() {
        return inclusive_g1S3;
    }
    public void setInclusive_g1S3(double inclusive_g1S3) {
        this.inclusive_g1S3 = inclusive_g1S3;
    }
    public double getInclusive_g1S4() {
        return inclusive_g1S4;
    }
    public void setInclusive_g1S4(double inclusive_g1S4) {
        this.inclusive_g1S4 = inclusive_g1S4;
    }
    public double getInclusive_g1S5() {
        return inclusive_g1S5;
    }
    public void setInclusive_g1S5(double inclusive_g1S5) {
        this.inclusive_g1S5 = inclusive_g1S5;
    }
    public double getInclusive_g2S1() {
        return inclusive_g2S1;
    }
    public void setInclusive_g2S1(double inclusive_g2S1) {
        this.inclusive_g2S1 = inclusive_g2S1;
    }
    public double getInclusive_g2S2() {
        return inclusive_g2S2;
    }
    public void setInclusive_g2S2(double inclusive_g2S2) {
        this.inclusive_g2S2 = inclusive_g2S2;
    }
    public double getInclusive_g2S3() {
        return inclusive_g2S3;
    }
    public void setInclusive_g2S3(double inclusive_g2S3) {
        this.inclusive_g2S3 = inclusive_g2S3;
    }
    public double getInclusive_g2S4() {
        return inclusive_g2S4;
    }
    public void setInclusive_g2S4(double inclusive_g2S4) {
        this.inclusive_g2S4 = inclusive_g2S4;
    }
    public double getInclusive_g2S5() {
        return inclusive_g2S5;
    }
    public void setInclusive_g2S5(double inclusive_g2S5) {
        this.inclusive_g2S5 = inclusive_g2S5;
    }
    public double getInclusive_g3S1() {
        return inclusive_g3S1;
    }
    public void setInclusive_g3S1(double inclusive_g3S1) {
        this.inclusive_g3S1 = inclusive_g3S1;
    }
    public double getInclusive_g3S2() {
        return inclusive_g3S2;
    }
    public void setInclusive_g3S2(double inclusive_g3S2) {
        this.inclusive_g3S2 = inclusive_g3S2;
    }
    public double getInclusive_g3S3() {
        return inclusive_g3S3;
    }
    public void setInclusive_g3S3(double inclusive_g3S3) {
        this.inclusive_g3S3 = inclusive_g3S3;
    }
    public double getInclusive_g3S4() {
        return inclusive_g3S4;
    }
    public void setInclusive_g3S4(double inclusive_g3S4) {
        this.inclusive_g3S4 = inclusive_g3S4;
    }
    public double getInclusive_g3S5() {
        return inclusive_g3S5;
    }
    public void setInclusive_g3S5(double inclusive_g3S5) {
        this.inclusive_g3S5 = inclusive_g3S5;
    }
    public double getInclusive_g4S1() {
        return inclusive_g4S1;
    }
    public void setInclusive_g4S1(double inclusive_g4S1) {
        this.inclusive_g4S1 = inclusive_g4S1;
    }
    public double getInclusive_g4S2() {
        return inclusive_g4S2;
    }
    public void setInclusive_g4S2(double inclusive_g4S2) {
        this.inclusive_g4S2 = inclusive_g4S2;
    }
    public double getInclusive_g4S3() {
        return inclusive_g4S3;
    }
    public void setInclusive_g4S3(double inclusive_g4S3) {
        this.inclusive_g4S3 = inclusive_g4S3;
    }
    public double getInclusive_g4S4() {
        return inclusive_g4S4;
    }
    public void setInclusive_g4S4(double inclusive_g4S4) {
        this.inclusive_g4S4 = inclusive_g4S4;
    }
    public double getInclusive_g4S5() {
        return inclusive_g4S5;
    }
    public void setInclusive_g4S5(double inclusive_g4S5) {
        this.inclusive_g4S5 = inclusive_g4S5;
    }
    public double getInclusive_g5S1() {
        return inclusive_g5S1;
    }
    public void setInclusive_g5S1(double inclusive_g5S1) {
        this.inclusive_g5S1 = inclusive_g5S1;
    }
    public double getInclusive_g5S2() {
        return inclusive_g5S2;
    }
    public void setInclusive_g5S2(double inclusive_g5S2) {
        this.inclusive_g5S2 = inclusive_g5S2;
    }
    public double getInclusive_g5S3() {
        return inclusive_g5S3;
    }
    public void setInclusive_g5S3(double inclusive_g5S3) {
        this.inclusive_g5S3 = inclusive_g5S3;
    }
    public double getInclusive_g5S4() {
        return inclusive_g5S4;
    }
    public void setInclusive_g5S4(double inclusive_g5S4) {
        this.inclusive_g5S4 = inclusive_g5S4;
    }
    public double getInclusive_g5S5() {
        return inclusive_g5S5;
    }
    public void setInclusive_g5S5(double inclusive_g5S5) {
        this.inclusive_g5S5 = inclusive_g5S5;
    }
    public double getExclusive_g1S1() {
        return exclusive_g1S1;
    }
    public void setExclusive_g1S1(double exclusive_g1S1) {
        this.exclusive_g1S1 = exclusive_g1S1;
    }
    public double getExclusive_g1S2() {
        return exclusive_g1S2;
    }
    public void setExclusive_g1S2(double exclusive_g1S2) {
        this.exclusive_g1S2 = exclusive_g1S2;
    }
    public double getExclusive_g1S3() {
        return exclusive_g1S3;
    }
    public void setExclusive_g1S3(double exclusive_g1S3) {
        this.exclusive_g1S3 = exclusive_g1S3;
    }
    public double getExclusive_g1S4() {
        return exclusive_g1S4;
    }
    public void setExclusive_g1S4(double exclusive_g1S4) {
        this.exclusive_g1S4 = exclusive_g1S4;
    }
    public double getExclusive_g1S5() {
        return exclusive_g1S5;
    }
    public void setExclusive_g1S5(double exclusive_g1S5) {
        this.exclusive_g1S5 = exclusive_g1S5;
    }
    public double getExclusive_g2S1() {
        return exclusive_g2S1;
    }
    public void setExclusive_g2S1(double exclusive_g2S1) {
        this.exclusive_g2S1 = exclusive_g2S1;
    }
    public double getExclusive_g2S2() {
        return exclusive_g2S2;
    }
    public void setExclusive_g2S2(double exclusive_g2S2) {
        this.exclusive_g2S2 = exclusive_g2S2;
    }
    public double getExclusive_g2S3() {
        return exclusive_g2S3;
    }
    public void setExclusive_g2S3(double exclusive_g2S3) {
        this.exclusive_g2S3 = exclusive_g2S3;
    }
    public double getExclusive_g2S4() {
        return exclusive_g2S4;
    }
    public void setExclusive_g2S4(double exclusive_g2S4) {
        this.exclusive_g2S4 = exclusive_g2S4;
    }
    public double getExclusive_g2S5() {
        return exclusive_g2S5;
    }
    public void setExclusive_g2S5(double exclusive_g2S5) {
        this.exclusive_g2S5 = exclusive_g2S5;
    }
    public double getExclusive_g3S1() {
        return exclusive_g3S1;
    }
    public void setExclusive_g3S1(double exclusive_g3S1) {
        this.exclusive_g3S1 = exclusive_g3S1;
    }
    public double getExclusive_g3S2() {
        return exclusive_g3S2;
    }
    public void setExclusive_g3S2(double exclusive_g3S2) {
        this.exclusive_g3S2 = exclusive_g3S2;
    }
    public double getExclusive_g3S3() {
        return exclusive_g3S3;
    }
    public void setExclusive_g3S3(double exclusive_g3S3) {
        this.exclusive_g3S3 = exclusive_g3S3;
    }
    public double getExclusive_g3S4() {
        return exclusive_g3S4;
    }
    public void setExclusive_g3S4(double exclusive_g3S4) {
        this.exclusive_g3S4 = exclusive_g3S4;
    }
    public double getExclusive_g3S5() {
        return exclusive_g3S5;
    }
    public void setExclusive_g3S5(double exclusive_g3S5) {
        this.exclusive_g3S5 = exclusive_g3S5;
    }
    public double getExclusive_g4S1() {
        return exclusive_g4S1;
    }
    public void setExclusive_g4S1(double exclusive_g4S1) {
        this.exclusive_g4S1 = exclusive_g4S1;
    }
    public double getExclusive_g4S2() {
        return exclusive_g4S2;
    }
    public void setExclusive_g4S2(double exclusive_g4S2) {
        this.exclusive_g4S2 = exclusive_g4S2;
    }
    public double getExclusive_g4S3() {
        return exclusive_g4S3;
    }
    public void setExclusive_g4S3(double exclusive_g4S3) {
        this.exclusive_g4S3 = exclusive_g4S3;
    }
    public double getExclusive_g4S4() {
        return exclusive_g4S4;
    }
    public void setExclusive_g4S4(double exclusive_g4S4) {
        this.exclusive_g4S4 = exclusive_g4S4;
    }
    public double getExclusive_g4S5() {
        return exclusive_g4S5;
    }
    public void setExclusive_g4S5(double exclusive_g4S5) {
        this.exclusive_g4S5 = exclusive_g4S5;
    }
    public double getExclusive_g5S1() {
        return exclusive_g5S1;
    }
    public void setExclusive_g5S1(double exclusive_g5S1) {
        this.exclusive_g5S1 = exclusive_g5S1;
    }
    public double getExclusive_g5S2() {
        return exclusive_g5S2;
    }
    public void setExclusive_g5S2(double exclusive_g5S2) {
        this.exclusive_g5S2 = exclusive_g5S2;
    }
    public double getExclusive_g5S3() {
        return exclusive_g5S3;
    }
    public void setExclusive_g5S3(double exclusive_g5S3) {
        this.exclusive_g5S3 = exclusive_g5S3;
    }
    public double getExclusive_g5S4() {
        return exclusive_g5S4;
    }
    public void setExclusive_g5S4(double exclusive_g5S4) {
        this.exclusive_g5S4 = exclusive_g5S4;
    }
    public double getExclusive_g5S5() {
        return exclusive_g5S5;
    }
    public void setExclusive_g5S5(double exclusive_g5S5) {
        this.exclusive_g5S5 = exclusive_g5S5;
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

    @Override
    public String toString() {
        return "TourRegistration [country=" + country + ", tourId=" + tourId + ", operatorId=" + operatorId + ", tourRoute="
                + tourRoute + ", tourDuration=" + tourDuration + ", tourMaxSize=" + tourMaxSize + ", tourMinSize=" + tourMinSize
                + ", tourType=" + tourType + ", tourStartPlace=" + tourStartPlace + ", tourStartTime=" + tourStartTime
                + ", tourStartDate=" + tourStartDate + ", tourEndDate=" + tourEndDate + ", serviceLevel=" + serviceLevel
                + ", minAge=" + minAge + ", transportation=" + Arrays.toString(transportation) + ", physicalDemand="
                + physicalDemand + ", endingPlace=" + endingPlace + ", withGuide=" + withGuide + "]";
    }


}
