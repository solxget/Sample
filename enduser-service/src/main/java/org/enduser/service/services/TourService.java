package org.enduser.service.services;

import java.util.List;

import org.enduser.service.model.CustomedTour;
import org.enduser.service.model.Group1Price;
import org.enduser.service.model.Group2Price;
import org.enduser.service.model.Group3Price;
import org.enduser.service.model.Group4Price;
import org.enduser.service.model.Group5Price;
import org.enduser.service.model.GroupSeasonCast;
import org.enduser.service.model.LandingTour;
import org.enduser.service.model.SimplifyPayment;
import org.enduser.service.model.Tour;
import org.enduser.service.model.TourRoute;
import org.enduser.service.model.TourTourOperator_C;
import org.enduser.service.model.TourTransaction;
import org.enduser.service.model.TourType;
import org.enduser.service.model.util.ImageObject;
import org.enduser.service.model.util.RefundObject;
import org.enduser.service.model.util.TourRegistration;

import com.simplify.payments.domain.Refund;

/**
 * @author solxget
 *
 */
public interface TourService {

	public List<Tour> getTour(String key, String value, Boolean inclusiveFlag, String tripDate, int tripDuration, int groupSize, String country);
	public Tour getTourById(String tourId);
	public LandingTour getTourForEdit(String tourId);
	public List<Tour> getTourByOperatorId(String operatorId);
	public List<Tour> getTourByCountry(String country);
	public List<Tour> getTourForAdvert(String country);  
	public CustomedTour saveCustomedTour(CustomedTour customedTour);
	
	public String getTourId(String operatorId);
	public List<LandingTour> getMyTours();
	public Tour createTour(Tour tour);
	public TourRegistration saveTour(TourRegistration tourRegistration);
	public TourRegistration updateTour(TourRegistration tourRegistration);
    public void saveTourPackageImage(ImageObject imageObject);
	public Tour updateTour(String key, String value, String tourId);
	public void deleteTour(String tourId);	
	public void deleteMyTour(String tourId);
	public void deleteCustomedTour(String emailAddress);
	public CustomedTour updateCustomedTour(CustomedTour customedTour);
	public List<CustomedTour> getCustomedTour();
	public List<TourTourOperator_C> getToursByType(String tourType);
	public List<TourType> getTourType(String country);
	public List<TourType> getLandingTourType(String country);
	public List<Tour> getBookedTour(String emailAddress);

	public TourTransaction bookTour(TourTransaction transaction);	
	public List<SimplifyPayment> getTransactionsByDate(String date);
	public int getTransactionCountByDate(String date);
	public SimplifyPayment findPayment(String paymentId);
	
	public void refundCustomer(RefundObject refundObject);
	public Refund findRefund(String refundId);
	
	public List<TourTransaction> getTourTransactions(String key, String value);
	
	public TourRoute getTourRoute(String tourId);
	public TourRoute saveTourRoute(TourRoute tourRoute);
	public void deleteTourRoute(String tourId);
	
	public GroupSeasonCast saveGroupSeasonCast(GroupSeasonCast groupSeasonCast);
	public GroupSeasonCast getGroupSeasonCast(String operatorId);
	public void deleteGroupSeasonCast(String operatorId);	
	
	public Group1Price getTourPrice(String operatorId, String tourId, String tripDate, Boolean inclusiveFlag);
	
	public Group1Price saveGroup1Price(Group1Price group1Price);
	public Group2Price saveGroup2Price(Group2Price group2Price);
	public Group3Price saveGroup3Price(Group3Price group3Price);
	public Group4Price saveGroup4Price(Group4Price group4Price);
	public Group5Price saveGroup5Price(Group5Price group5Price);
	
	public void deleteTourPrice(String operatorId, String tourId, String tripDate, Boolean inclusiveFlag);
	
}
