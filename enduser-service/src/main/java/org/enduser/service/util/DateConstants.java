package org.enduser.service.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.enduser.service.delegates.TourDelegate;
import org.enduser.service.exception.EndUserException;
import org.springframework.stereotype.Component;

@Component
public class DateConstants {
    private static final Logger logger = Logger.getLogger(TourDelegate.class);

    public Date parseDate(String date) {
        try {
            logger.info(" Entered DateConstants parseDate method");
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:SS");
            return formatter.parse(date);
        } catch (ParseException e) {
            logger.error("Date Parsing Exception throwen while parsign SeasonXStartDate  ", e);
            throw new EndUserException(e.getMessage());
        }
    }

}
