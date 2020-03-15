package org.enduser.service.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.stereotype.Component;


@Entity
@DynamicUpdate
@Component
public class VisitorInfo {
    
    @Id
    @GeneratedValue
    private int visitor_sequence_num;
    
    @Column(name = "CITY")
    private String city;
    @Column(name = "COUNTRY_CODE")
    private String country_code;
    @Column(name = "COUNTRY_NAME")
    private String country_name;
    @Column(name = "IP")
    private String ip;
    @Column(name = "LATITUDE")
    private double latitude;
    @Column(name = "LOGITUDE")
    private double longitude;
    @Column(name = "METRO_CODE")
    private int metro_code;
    @Column(name = "REGION_CODE")
    private String region_code;
    @Column(name = "REGION_NAME")
    private String region_name;
    @Column(name = "TIME_ZONE")
    private String time_zone;
    @Column(name = "ZIP_CODE")
    private String zip_code;
    
    @Column(name = "REGISTRATION_DATE")
    private Date registrationDate;
    
    
    public VisitorInfo(){}
    public VisitorInfo(String city, String country_code, String country_name, String ip, double latitude, double longitude,
            int metro_code, String region_code, String region_name, String time_zone, String zip_code, Date registrationDate) {
        super();
        this.city = city;
        this.country_code = country_code;
        this.country_name = country_name;
        this.ip = ip;
        this.latitude = latitude;
        this.longitude = longitude;
        this.metro_code = metro_code;
        this.region_code = region_code;
        this.region_name = region_name;
        this.time_zone = time_zone;
        this.zip_code = zip_code;
        this.registrationDate = registrationDate;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }

    public String getCountry_name() {
        return country_name;
    }

    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getMetro_code() {
        return metro_code;
    }

    public void setMetro_code(int metro_code) {
        this.metro_code = metro_code;
    }

    public String getRegion_code() {
        return region_code;
    }

    public void setRegion_code(String region_code) {
        this.region_code = region_code;
    }

    public String getRegion_name() {
        return region_name;
    }

    public void setRegion_name(String region_name) {
        this.region_name = region_name;
    }

    public String getTime_zone() {
        return time_zone;
    }

    public void setTime_zone(String time_zone) {
        this.time_zone = time_zone;
    }

    public String getZip_code() {
        return zip_code;
    }

    public void setZip_code(String zip_code) {
        this.zip_code = zip_code;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }
    
    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }
    
    
    @Override
    public String toString() {
        return "VisitorInfo [city=" + city + ", country_code=" + country_code + ", country_name=" + country_name + ", ip=" + ip
                + ", latitude=" + latitude + ", longitude=" + longitude + ", metro_code=" + metro_code + ", region_code="
                + region_code + ", region_name=" + region_name + ", registrationDate =" + registrationDate + "time_zone=" + time_zone + ", zip_code=" + zip_code + "]";
    }


}
