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
public class TourTourOperator_C {

    @Id
    @GeneratedValue
    private int tTO_C_sequence_num;

    @Column(name = "tour_duration")
    private String tour_duration;
    @Column(name = "tour_route")
    private String tour_route;
    @Column(name = "tour_price")
    private double tour_price;
    @Column(name = "tour_inclusive_price")
    private double tour_inclusive_price;

    @Column(name = "operator_name")
    private String operator_name;
    @Column(name = "rating")
    private String rating;

    public TourTourOperator_C() {}

    public TourTourOperator_C(String tour_duration, String tour_route, double tour_price, double tour_inclusive_price,
            String operator_name, String rating) {
        this.tour_duration = tour_duration;
        this.tour_route = tour_route;
        this.tour_price = tour_price;
        this.tour_inclusive_price = tour_inclusive_price;
        this.operator_name = operator_name;
        this.rating = rating;
    }

    public int gettTO_C_sequence_num() {
        return tTO_C_sequence_num;
    }

    public void settTO_C_sequence_num(int tTO_C_sequence_num) {
        this.tTO_C_sequence_num = tTO_C_sequence_num;
    }

    public String getTour_duration() {
        return tour_duration;
    }

    public void setTour_duration(String tour_duration) {
        this.tour_duration = tour_duration;
    }

    public String getTour_route() {
        return tour_route;
    }

    public void setTour_route(String tour_route) {
        this.tour_route = tour_route;
    }

    public double getTour_price() {
        return tour_price;
    }

    public void setTour_price(double tour_price) {
        this.tour_price = tour_price;
    }

    public String getOperator_name() {
        return operator_name;
    }

    public void setOperator_name(String operator_name) {
        this.operator_name = operator_name;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public double getTour_inclusive_price() {
        return tour_inclusive_price;
    }

    public void setTour_inclusive_price(double tour_inclusive_price) {
        this.tour_inclusive_price = tour_inclusive_price;
    }

    @Override
    public String toString() {
        return "TourTourOperator_C [tTO_C_sequence_num=" + tTO_C_sequence_num + ", tour_duration=" + tour_duration
                + ", tour_route=" + tour_route + ", tour_price=" + tour_price + ", tourInclusivePrice=" + tour_inclusive_price
                + ", operator_name=" + operator_name + ", rating=" + rating + "]";
    }

}
