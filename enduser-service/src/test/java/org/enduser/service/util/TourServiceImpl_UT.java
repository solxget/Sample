package org.enduser.service.util;

import org.enduser.service.util.TouristServiceUtil;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mock;


public class TourServiceImpl_UT {

	@Mock
	private TouristServiceUtil touristServiceUtil;
	@Test
    public void validateEmailTest() {
		TouristServiceUtil touristServiceUtil = new TouristServiceUtil();
		Assert.assertTrue(touristServiceUtil.validateEmail("solxget@gmail.com"));
	}
}
