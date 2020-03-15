package org.enduser.service.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.stereotype.Component;

@Entity
@DynamicUpdate
@Component
public class TourCatagory {

    @Id
    @Column(name = "CATAGORY_ID", nullable = false)
    private int catagoryId;
    @Column(name = "TOUR_TYPE")
    private String tourType;
    @Column(name = "COUNTRY")
    private String country;
    
    public TourCatagory(){}
    public TourCatagory(int catagoryId, String tourType, String country) {
        super();
        this.catagoryId = catagoryId;
        this.tourType = tourType;
        this.country = country;
    }
    
    
    public int getCatagoryId() {
        return catagoryId;
    }
    public void setCatagoryId(int catagoryId) {
        this.catagoryId = catagoryId;
    }
    public String getTourType() {
        return tourType;
    }
    public void setTourType(String tourType) {
        this.tourType = tourType;
    }
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    
    @Override
    public String toString() {
        return "TourCatagory [catagoryId=" + catagoryId + ", tourType=" + tourType + ", country=" + country + "]";
    }
      
}
