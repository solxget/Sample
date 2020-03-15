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
public class VisitorInfo2 {

    @Id
    @GeneratedValue
    private int visitor_sequence_num;
    
    @Column(name = "IP")
    private String ip;
    @Column(name = "HOST_NAME")
    private String hostname;
    @Column(name = "CITY")
    private String city;
    @Column(name = "REGION")
    private String region;
    @Column(name = "COUNTRY")
    private String country;
    @Column(name = "LOC")
    private String loc;
    @Column(name = "ORG")
    private String org;
    @Column(name = "POSTAL")
    private String postal;
    
    @Column(name = "REGISTRATION_DATE")
    private Date registrationDate;

    public VisitorInfo2(){}
    public VisitorInfo2(int visitor_sequence_num, String ip, String hostname, String city, String region, String country,
            String loc, String org, String postal, Date registrationDate) {
        super();
        this.visitor_sequence_num = visitor_sequence_num;
        this.ip = ip;
        this.hostname = hostname;
        this.city = city;
        this.region = region;
        this.country = country;
        this.loc = loc;
        this.org = org;
        this.postal = postal;
        this.registrationDate = registrationDate;
    }
    public String getIp() {
        return ip;
    }
    public void setIp(String ip) {
        this.ip = ip;
    }
    public String getHostname() {
        return hostname;
    }
    public void setHostname(String hostname) {
        this.hostname = hostname;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getRegion() {
        return region;
    }
    public void setRegion(String region) {
        this.region = region;
    }
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public String getLoc() {
        return loc;
    }
    public void setLoc(String loc) {
        this.loc = loc;
    }
    public String getOrg() {
        return org;
    }
    public void setOrg(String org) {
        this.org = org;
    }
    public String getPostal() {
        return postal;
    }
    public void setPostal(String postal) {
        this.postal = postal;
    }
    public Date getRegistrationDate() {
        return registrationDate;
    }
    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }
    
    
    @Override
    public String toString() {
        return "VisitorInfo2 [ip=" + ip + ", hostname=" + hostname + ", city=" + city + ", region=" + region + ", country="
                + country + ", loc=" + loc + ", org=" + org + ", postal=" + postal + ", registrationDate=" + registrationDate
                + "]";
    }    
    
}
