package org.enduser.service.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.log4j.Logger;
import org.enduser.service.dao.TourDao;
import org.enduser.service.delegates.TouristGroupDelegate;
import org.enduser.service.exception.EndUserException;
import org.enduser.service.model.Tour;
import org.enduser.service.model.TouristGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TouristGroupServiceUtil {

    private static final Logger logger = Logger.getLogger(TouristGroupServiceUtil.class);

    private TouristServiceUtil touristServiceUtil;
    private TouristGroupDelegate touristGroupDelegate;
    private TourDao tourDao;

    @Autowired
    public void setTouristServiceUtil(TouristServiceUtil touristServiceUtil) {
        this.touristServiceUtil = touristServiceUtil;
    }

    @Autowired
    public void setTouristGroupDelegate(TouristGroupDelegate touristGroupDelegate) {
        this.touristGroupDelegate = touristGroupDelegate;
    }

    @Autowired
    public void setTourDao(TourDao tourDao) {
        this.tourDao = tourDao;
    }

    public void setFields(TouristGroup touristGroup) {
        touristGroup.setRegistrationDate(touristServiceUtil.getDateTime());
        touristGroup.setStatusChangeDate(touristServiceUtil.getDateTime());
        touristGroup.setActiveInactiveFlag("Active");
        touristGroup.setGroupStatus("New TouristGroup");
        touristGroup.setGroupSize(1);
        touristGroup.setGroupId(touristGroup.getEmailAddress() + RandomStringUtils.randomAlphanumeric(10));
    }

    /*
     * public String getStatusChangeDate(){
     * logger.debug("Entered TouristGroupServiceUtil getStatusChangeDate method ");
     * 
     * DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy"); Calendar calendar =
     * Calendar.getInstance(); Date date = new Date(); String dateObject = null; try {
     * calendar.setTime(dateFormat.parse(dateFormat.format(date))); dateObject =
     * dateFormat.format(new Date(calendar.getTimeInMillis())); } catch (ParseException e1) {
     * logger.error("Date format internal Exception throwen while registering user  ", e1); throw
     * new EndUserException("Date format internal Exception throwen while registering user  " + e1);
     * } return dateObject; }
     */

    public List<TouristGroup> showGroupForTourist(String plannedTripDate, int tripDuration, String country) {
        int[] minMaxTripDuration = new int[2];
        String[] minMaxPlannedTripDate = new String[2];
        try {
            minMaxTripDuration = getMinMaxTripDuration(tripDuration);
            minMaxPlannedTripDate = getMinMaxPlannedTripDate(plannedTripDate);
        } catch (Exception e) {
            logger.error("TouristGroupServiceUtil showGroupForTourist throws exception " + e);
            throw new EndUserException(e);
        }
        List<TouristGroup> touristGroupList = null;
        try {
            touristGroupList = touristGroupDelegate.showGroupForTourist(minMaxPlannedTripDate, minMaxTripDuration, plannedTripDate, country);
        } catch (Exception e) {
            throw new EndUserException(e);
        }
        for (TouristGroup tourstgroup : touristGroupList) {
            String tourId = tourstgroup.getTourId();
            List<Tour> tour = tourDao.getTourId("tour_Id", tourId);
            if (tour.size() == 1) {
                tourstgroup.setTour((tourDao.getTourId("tour_Id", tourId)).get(0));
            }
        }

        return touristGroupList;
    }

    public List<TouristGroup> showGroupForTouristGeneric(String key, String value) {
        List<TouristGroup> touristGroupList = null;
        try {
            touristGroupList = touristGroupDelegate.showGroupForTouristGeneric(key, value);
            for (TouristGroup tourstgroup : touristGroupList) { // too much database call, optimize
                String tourId = tourstgroup.getTourId();
                List<Tour> tour = tourDao.getTourId("tour_Id", tourId);
                if (tour.size() == 1) {
                    tourstgroup.setTour(tour.get(0));
                }
            }
        } catch (Exception e) {
            logger.error("TouristGroupServiceUtil showGroupForTouristGeneric throws exception " + e);
            throw new EndUserException(e);
        }

        return touristGroupList;
    }

    public String[] getMinMaxPlannedTripDate(String plannedTripDate) {
        String minMaxTripDate[] = new String[2];
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Calendar calendar = Calendar.getInstance();      //SOURCE OF PROBLEM WHEN TIMEZONE COMES TO PICTURE

        try {
            Date inputDate = inputFormat.parse(plannedTripDate);
            calendar.setTime(dateFormat.parse(dateFormat.format(inputDate)));

            calendar.add(Calendar.DATE, -360);       // calendar.add(Calendar.DATE, -30);
            Date today = touristServiceUtil.getDateTime();
            Date lowerBound = new Date(calendar.getTimeInMillis());
            Date minDate = today.before(lowerBound) ? lowerBound : today;
            minMaxTripDate[0] = dateFormat.format(minDate);
            calendar.add(Calendar.DATE, 720);     //calendar.add(Calendar.DATE, 60);
            minMaxTripDate[1] = dateFormat.format(new Date(calendar.getTimeInMillis()));
        } catch (ParseException e) {
            logger.error("TouristGroupServiceUtil getMinMaxPlannedTripDate throws exception " + e);
            throw new EndUserException(e);
        }

        return minMaxTripDate;
    }

    public int[] getMinMaxTripDuration(int tripDuration) {
        int[] minMaxTripDuration = new int[2];

/*        if (tripDuration <= 5) {
            minMaxTripDuration[0] = 1;
            minMaxTripDuration[1] = 10;
        } else if (tripDuration <= 10 && tripDuration > 5) {
            minMaxTripDuration[0] = 3;
            minMaxTripDuration[1] = 15;
        } else if (tripDuration <= 15 && tripDuration > 10) {
            minMaxTripDuration[0] = 5;
            minMaxTripDuration[1] = 25;
        } else {
            minMaxTripDuration[0] = 10;
            minMaxTripDuration[1] = 40;
        }
        return minMaxTripDuration;*/
        
        if (tripDuration <= 5) {
            minMaxTripDuration[0] = 1;
            minMaxTripDuration[1] = 30;
        } else if (tripDuration <= 10 && tripDuration > 5) {
            minMaxTripDuration[0] = 1;
            minMaxTripDuration[1] = 30;
        } else if (tripDuration <= 15 && tripDuration > 10) {
            minMaxTripDuration[0] = 1;
            minMaxTripDuration[1] = 30;
        } else {
            minMaxTripDuration[0] = 1;
            minMaxTripDuration[1] = 30;
        }
        return minMaxTripDuration;
    }

}
