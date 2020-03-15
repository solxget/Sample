package org.enduser.service.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.stereotype.Component;

@Entity
@DynamicUpdate
@Component
public class TourTypeLookup {
    
    @Id
    @GeneratedValue
    private int lookup_sequence_num;

    @Column(name = "CATAGORY_ID")
    private int catagory_id;
    @Column(name = "TOUR_ID")
    private String tour_id;
    @Column(name = "COUNTRY")
    private String country;
    
    public TourTypeLookup(){}
    public TourTypeLookup(int lookup_sequence_num, int catagory_id, String tour_id, String country) {
        super();
        this.lookup_sequence_num = lookup_sequence_num;
        this.catagory_id = catagory_id;
        this.tour_id = tour_id;
        this.country = country;
    }
    public int getCatagory_id() {
        return catagory_id;
    }
    public void setCatagory_id(int catagory_id) {
        this.catagory_id = catagory_id;
    }
    public String getTour_id() {
        return tour_id;
    }
    public void setTour_id(String tour_id) {
        this.tour_id = tour_id;
    }
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    
    
    @Override
    public String toString() {
        return "TourTypeLookup [lookup_sequence_num=" + lookup_sequence_num + ", catagory_id=" + catagory_id + ", tour_id="
                + tour_id + ", country=" + country + "]";
    } 
   
}
