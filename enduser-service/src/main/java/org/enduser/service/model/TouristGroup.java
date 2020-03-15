package org.enduser.service.model;


import java.util.Date;
import java.util.List;

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
public class TouristGroup {

    @Id
    @GeneratedValue
    private int group_sequence_num;

    @Column(name = "GROUP_ID", nullable = false, unique = true)
    private String groupId;
    @Column(name = "GROUP_NAME", nullable = false)
    private String groupName;
    @Column(name = "GROUP_SIZE", nullable = false)
    private int groupSize;
    @Column(name = "GROUP_STATUS", nullable = false)
    private String groupStatus;
    @Column(name = "ACTIVE_INACTIVE_FLAG", nullable = false)
    private String activeInactiveFlag;
    @Column(name = "REGISTRATION_DATE", nullable = false)
    private Date registrationDate;
    @Column(name = "STATUS_CHANGE_DATE")
    private Date statusChangeDate;
    @Column(name = "EMAIL_ADDRESS", nullable = false)
    private String emailAddress;
    @Column(name = "PLANNED_TRIP_DATE")
    private Date plannedTripDate;
    @Column(name = "TRIP_END_DATE")
    private Date tripEndDate;
    @Column(name = "TRIP_DURATION")
    private int tripDuration;
    @Column(name = "TOUR_ID")
    private String tourId;
    @Column(name = "COUNTRY")
    private String country;

    @Transient
    private Tour tour;
    @Transient
    private List<GroupMember> groupMembers;

    public TouristGroup() {}

    public TouristGroup(String groupId, String groupName, int groupSize, String groupStatus, String activeInactiveFlag,
            Date registrationDate, Date statusChangeDate, String emailAddress, Date plannedTripDate, int tripDuration,
            String tourId, Date tripEndDate, String country) {

        this.groupId = groupId;
        this.groupName = groupName;
        this.groupSize = groupSize;
        this.groupStatus = groupStatus;
        this.activeInactiveFlag = activeInactiveFlag;
        this.registrationDate = registrationDate;
        this.statusChangeDate = statusChangeDate;
        this.emailAddress = emailAddress;
        this.plannedTripDate = plannedTripDate;
        this.tripDuration = tripDuration;
        this.tourId = tourId;
        this.tripEndDate = tripEndDate;
        this.country = country;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public int getGroupSize() {
        return groupSize;
    }

    public void setGroupSize(int groupSize) {
        this.groupSize = groupSize;
    }

    public String getGroupStatus() {
        return groupStatus;
    }

    public void setGroupStatus(String groupStatus) {
        this.groupStatus = groupStatus;
    }

    public String getActiveInactiveFlag() {
        return activeInactiveFlag;
    }

    public void setActiveInactiveFlag(String activeInactiveFlag) {
        this.activeInactiveFlag = activeInactiveFlag;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Date getStatusChangeDate() {
        return statusChangeDate;
    }

    public void setStatusChangeDate(Date statusChangeDate) {
        this.statusChangeDate = statusChangeDate;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public Date getPlannedTripDate() {
        return plannedTripDate;
    }

    public void setPlannedTripDate(Date plannedTripDate) {
        this.plannedTripDate = plannedTripDate;
    }

    public int getTripDuration() {
        return tripDuration;
    }

    public void setTripDuration(int tripDuration) {
        this.tripDuration = tripDuration;
    }

    public String getTourId() {
        return tourId;
    }

    public void setTourId(String tourId) {
        this.tourId = tourId;
    }


    /*
     * This for TouristGroupServiceImpl.showGroupForTourist() method to retrieve the Tour which is
     * added to a group
     */
    public Tour getTour() {
        return tour;
    }

    public void setTour(Tour tour) {
        this.tour = tour;
    }

    public List<GroupMember> getGroupMembers() {
        return groupMembers;
    }

    public void setGroupMembers(List<GroupMember> groupMembers) {
        this.groupMembers = groupMembers;
    }
    
    public Date getTripEndDate() {
        return tripEndDate;
    }

    public void setTripEndDate(Date tripEndDate) {
        this.tripEndDate = tripEndDate;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "TouristGroup [group_sequence_num=" + group_sequence_num + ", groupId=" + groupId + ", groupName=" + groupName
                + ", groupSize=" + groupSize + ", groupStatus=" + groupStatus + ", activeInactiveFlag=" + activeInactiveFlag
                + ", registrationDate=" + registrationDate + ", statusChangeDate=" + statusChangeDate + ", emailAddress="
                + emailAddress + ", plannedTripDate=" + plannedTripDate + ", tripDuration=" + tripDuration + ", tourId=" + tourId
                + ", tripEndDate=" + tripEndDate + ", country=" + country + "]";
    }


}
