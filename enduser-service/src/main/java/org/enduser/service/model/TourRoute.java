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
public class TourRoute {

    @Id
    @GeneratedValue
    private int tourRoute_sequence_num;

    @Column(name = "TOUR_ID", nullable = false, unique = true)
    private String tourId;
    @Column(name = "TOUR_ROUTE", nullable = false)
    private String tourRoute;
    @Column(name = "TOUR_DESCRIPTION", columnDefinition = "TEXT")
    private String tourDescription;

    public TourRoute() {}

    public TourRoute(String tourId, String tourRoute, String tourDescription, int tourRoute_sequence_num) {
        super();
        this.tourRoute_sequence_num = tourRoute_sequence_num;
        this.tourId = tourId;
        this.tourRoute = tourRoute;
        this.tourDescription = tourDescription;
    }

    
    public int getTourRoute_sequence_num() {
        return tourRoute_sequence_num;
    }

    public void setTourRoute_sequence_num(int tourRoute_sequence_num) {
        this.tourRoute_sequence_num = tourRoute_sequence_num;
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

    public String getTourDescription() {
        return tourDescription;
    }

    public void setTourDescription(String tourDescription) {
        this.tourDescription = tourDescription;
    }

    @Override
    public String toString() {
        return "TourRoute [tourId=" + tourId + ", tourRoute=" + tourRoute + ", tourDescription=" + tourDescription + "]";
    }

}
