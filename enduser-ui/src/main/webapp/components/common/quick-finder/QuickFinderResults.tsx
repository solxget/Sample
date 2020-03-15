import * as React from 'react';
import * as ReactDOM from 'react-dom';
import * as i18next from 'i18next';
import * as moment from 'moment'

import {quickFinderStore, QuickFinderSection, EventData, EventType, TourModel, GroupModel, CarModel} from '../../../stores/QuickFinderStore'
import {toursStore} from '../../../stores/ToursStore'
import {checkOutStore} from '../../../stores/checkOutStore'
import {localeStore} from '../../../stores/LocaleStore'
import {currencyStore} from '../../../stores/CurrencyStore'
import {imageUploadStore} from '../../../stores/ImageUploadStore'
import {securityStore} from '../../../stores/SecurityStore'
import {TourSummary} from '../TourSummary'
import {CarSummary} from '../CarSummary'
import {ConfirmPayment} from '../ConfirmPayment'
import {CarCheckOut} from '../CarCheckOut'

interface TransportationItemProps {
    transportationIcons: string[]   
}

export function datef(str): string {
    var val = moment(str);
    var result = moment(str).format('MMM D')
    if (moment().year() != val.year())
        result += `, ${val.year()}`;
    return result;
}

export function getTourPrice(tourPrice, exchangeRate, countryCode): number{
	return currencyStore.getTourPrice(tourPrice, exchangeRate, countryCode);
}

export class QuickFinderResult extends React.Component<{}, {}> {
	
    _subscription = null
    onValChanged(val: EventData) {
    	
        var searchResults = quickFinderStore.getSearchResult();

        if (val.eventType == EventType.SEARCH_INVOKED)
        {
            this.forceUpdate(() => {
            	$('#quickFinderblock').show();
                $('#searchResults').hide();
                $('#toursPanel').hide();
                $('#searchResults').slideDown('fast');
            });
        }
        else if (val.eventType == EventType.SECTION_CHANGED && searchResults != null) {
            if (searchResults.resultType == quickFinderStore.getCurrentSection()) {
                this.forceUpdate(() => {
                	$('#quickFinderblock').show();
                    $('#searchResults').hide();
                    $('#toursPanel').hide();
                    $('#searchResults').slideDown('fast');
                });
            }
            else {
                this.onClose();
            }
        }
        else if(val.eventType == EventType.TOUR_SELECTED || val.eventType == EventType.TOUR_CHECKOUT || 
        		val.eventType == EventType.CAR_SELECTED || val.eventType == EventType.CAR_CHECKOUT){
        	  this.forceUpdate(() => {
                  $('#searchResults').hide();
                  $('#toursPanel').hide();  
                  $('#quickFinderblock').hide();                   
                  $('#searchResults').slideDown('fast');
              });
        } 
        else if(val.eventType == EventType.COUNTRY_CHANGED){
        	$('#quickFinderblock').show();
            $('#searchResults').slideUp('fast');
            $('#toursPanel').show();
        }
        else{
        	 this.doNothing();
        }
    }
    
    componentDidMount() {
        this._subscription = quickFinderStore.subscribe(this, this.onValChanged)
    }
    componentWillUnmount() {
        this._subscription.unsubscribe(this, this.onValChanged)
    }
    onClose() {
        imageUploadStore.ViewPhotos(localeStore.getVisitedCountry(), 'Background_img', 'background');
    	$('#quickFinderblock').show();
        $('#searchResults').slideUp('fast');
        $('#searchResults').hide();
        $('#toursPanel').show();
    }
    
    doNothing(){

    }

    render() {
    	var selectedTour = quickFinderStore.getSelectedTour();
    	var selectedCar = quickFinderStore.getSelectedCar();
        var result = quickFinderStore.getSearchResult();
        var tourCheckOutFlag = quickFinderStore.getTourCheckOutFlag();
        var carCheckOutFlag = quickFinderStore.getCarCheckOutFlag();
        if (selectedTour != null){
        	if(!tourCheckOutFlag){
        		var ui = <TourSummary />
        	}
        	else if(tourCheckOutFlag){
        		var ui = <ConfirmPayment />
        	}
        }
        else if(selectedCar != null){
        	if(!carCheckOutFlag){
        		var ui = <CarSummary />        		
        	}
        	else if(carCheckOutFlag){
        		var ui = <CarCheckOut />
        	}
        }
        else if (result != null) {
            if (result.resultType == QuickFinderSection.TOURS)
                var ui = <QuickFinderToursResult />
            else if (result.resultType == QuickFinderSection.GROUPS)
                var ui = <QuickFinderGroupsResult />
            else if (result.resultType == QuickFinderSection.CARS)
                var ui = <QuickFinderCarsResult />
        }
        return (
            <div className="row panel-search-result" id="searchResults" hidden={true}>            
                <button title={i18next.t('CLOSE')} style={{margin: 10, fontSize: '2em'}} onClick={this.onClose} type="button" className="close" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <div className="container">
	                <div className="col-md-2"></div>
	                {ui == null ? null : ui}
	                 <div className="col-md-2"></div>
                </div>
            </div>
        );
    }
    
}

 export class TourImageItem extends React.Component<{}, {}> {

    _subscription = null;

    onValChanged(val) {  
        this.forceUpdate(() => {
            var urls = imageUploadStore.getImageUrls().url;
            if(urls[0]){
                $('#itImage1')[0].src = urls[0];
                $('#liBlock1')[0].style.display= 'block';
                $('#liBlock1')[0].setAttribute('data-thumb', urls[0]);
            }else{
                $('#itImage1')[0].src = '';
                $('#liBlock1')[0].setAttribute('data-thumb', '');
            }

            if(urls[1]){
                $('#itImage2')[0].src = urls[1];
                $('#liBlock2')[0].style.display= 'block';
                $("#liBlock2")[0].setAttribute('data-thumb', urls[1]);
            }else{
                $('#itImage2')[0].src = '';
                $('#liBlock2')[0].setAttribute('data-thumb', '');
            }

            if(urls[2]){ 
                $('#itImage3')[0].src = urls[2];
                $('#liBlock3')[0].style.display= 'block';
                $("#liBlock3")[0].setAttribute('data-thumb', urls[2]);
            }else{
                $('#itImage3')[0].src = '';
                $('#liBlock3')[0].setAttribute('data-thumb', '');
            }

            if(urls[3]){
                $('#itImage4')[0].src = urls[3];
                $('#liBlock4')[0].style.display= 'block';
                $("#liBlock4")[0].setAttribute('data-thumb', urls[3]);
            }else{
                $('#itImage4')[0].src = '';
                $('#liBlock4')[0].setAttribute('data-thumb', '');
            }

            if(urls[4]) {
                $('#itImage5')[0].src = urls[4];
                $('#liBlock5')[0].style.display= 'block';
                $('#liBlock5')[0].setAttribute('data-thumb', urls[4]);
            }else{
                $('#itImage5')[0].src = '';
                $('#liBlock5')[0].setAttribute('data-thumb', '');
            }

            if(urls[5]){ 
                $('#itImage6')[0].src = urls[5];
                $('#liBlock6')[0].style.display= 'block';
                $('#liBlock6')[0].setAttribute('data-thumb', urls[5]);
            }else{
                $('#itImage6')[0].src = '';
                $('#liBlock6')[0].setAttribute('data-thumb', '');
            }

            this.refresh();  
        }); 
    }

    refresh() {
        if(imageUploadStore.getSlider() != null){
            imageUploadStore.getSlider().destroy();
        }

        var node = ($('#sliderLight') as any);
        var sliderInit = node.lightSlider({
            gallery: true;
            item: 1,
            loop: true,
            slideMargin: 0,
            thumbItem: 6
        });
        imageUploadStore.saveSlider(sliderInit);
    }
    
    componentDidMount() {        
        this._subscription = imageUploadStore.subscribe(this, this.onValChanged);
        
    }
    
    componentWillUnmount() {
        this._subscription.unsubscribe(this, this.onValChanged)
    }

    render() { 
        var smallScreenImage = {
            width: (screen.width < 750 ? '97%' : '')
        }
        return (
            ( imageUploadStore.getImageUrls() != null && (imageUploadStore.getImageUrls().url).length > 0) ?
                (   
                    <div className="iternary-images" id="iternaryImages">
                    <br /><hr /> 
                        <ul id="sliderLight" className="slider-light">
                            <li id="liBlock1" className="li-block" data-thumb="xx">
                                <img className="iternary-image" src="xx" alt="" id="itImage1" ></img>
                            </li>
                            <li id="liBlock2" className="li-block" data-thumb="xx">
                                <img className="iternary-image" src="xx" alt="" id="itImage2" ></img>
                            </li>
                            <li id="liBlock3" className="li-block" data-thumb="xx">
                                <img className="iternary-image" src="xx" alt="" id="itImage3" ></img>
                            </li>
                            <li id="liBlock4" className="li-block" data-thumb="xx">
                                <img className="iternary-image" src="xx" alt="" id="itImage4" ></img>
                            </li>
                            <li id="liBlock5" className="li-block" data-thumb="xx">
                                <img className="iternary-image" src="xx" alt="" id="itImage5" ></img>
                            </li>
                            <li id="liBlock6" className="li-block" data-thumb="xx">
                                <img className="iternary-image" src="xx" alt="" id="itImage6" ></img>
                            </li>
                        </ul>
                    </div>
                )
            :  <br />
        );
    }
}

export class RouteDetailDialog extends React.Component<{}, {}> {
    
    _subscription = null
    onValChanged(val) {        
        this.forceUpdate(() => {
            if (toursStore.isDetailMode) {
                $('#tourItemDetailDialog').modal('show');
            }
        }); 
    }
    
    componentDidMount() {        
        this._subscription = toursStore.subscribe(this, this.onValChanged);
    }
    
    componentWillUnmount() {
        this._subscription.unsubscribe(this, this.onValChanged)
    }
    
    render() {
        var item = toursStore.getCurrentItem();
        var visitedCountry = localeStore.getVisitedCountry();
    //    var imgURL = `./bundle/ItineraryImages/${visitedCountry}/${item && item.description ? item.description : ''}/${item && item.imageUrl ? item.imageUrl : ''}`;
        var smallScreenImage = {
            width: (screen.width < 400 ? '97%' : '')
        }
        return <div id="tourItemDetailDialog" className="modal fade" tabIndex={-1} role="dialog" ariaLabelledBy="">
                    <div className="modal-dialog modal-lg">
                        <div className="modal-content">
                            <div className="modal-header">
                                <h4 className="modal-title">
                                    <button title={i18next.t('BACK')} className="btn btn-primary" data-dismiss="modal">
                                        <i className="fa fa-arrow-left"></i>
                                    </button> {item == null ? '' : item.title} <small> for {item == null ? '' : item.tourDuration} </small>
                                </h4>
                            </div>
                            <div className="modal-body">
                                <div>
                                    <p>Tour Starts from <strong>{item == null ? '' : item.tourStartPlace} </strong> and ends in <strong> {item == null ? '' : item.endingPlace} </strong></p>
                                    <div className="row">
                                        <div className="col-md-6">
                                            <div className="row">
                                                <label className="control-label col-sm-4"> Service Level: </label>
                                                <div className="col-sm-8">
                                                    <span>{item == null ? '-' : <span>{item.serviceLevel ? item.serviceLevel : '' }</span>}</span>
                                                </div>
                                            </div>
                                           <div className="row">
                                                <label className="control-label col-sm-4"> Minimum Age: </label>
                                                <div className="col-sm-8">
                                                    <span>{item == null ? '-' : <span>{item.minAge ? item.minAge : '' }</span>}</span>
                                                </div>
                                            </div>
                                        </div>
                                        <div className="col-md-6">
                                            <div className="row">
                                                <label className="control-label col-sm-4"> Physical rating: </label>
                                                <div className="col-sm-8">
                                                    <span>{item == null ? '-' : <span> {item.physicalDemand ? <span> {item.physicalDemand} {i18next.t('OUT_OF')} </span> : ''}</span> } </span>
                                                </div>
                                            </div>

                                        </div>
                                    </div>
                                </div>
                                
                                <TourImageItem />
                                
                                <br /><hr />
                                    {item == null ? '' : item.content}

                            </div>
                            <div className="modal-footer">
                                <button className="btn btn-primary" data-dismiss="modal">{i18next.t('BACK')}</button>
                            </div>
                        </div>
                    </div>
                </div>
    }
    
}

export class TransportationItem extends React.Component<TransportationItemProps, {}> {

    render() {        
        var icons = [];
        var transportList = [];
        var transportList = this.props.transportationIcons;
        if(this.props.transportationIcons != null && this.props.transportationIcons.length > 0){
            for (var i = 0; i < this.props.transportationIcons.length; i++) {
                if(transportList[i] == "car")
                    icons.push(<span title="Involves surface drive" className="fa fa-car grey-text horizontal-space-3x"> </span> );
                else if(transportList[i] == "plane")
                    icons.push(<span title="Involves local flight" className="fa fa-plane grey-text horizontal-space-3x"> </span> );
                else if(transportList[i] == "train")
                    icons.push(<span title="Involves train ride" className="fa fa-train grey-text horizontal-space-3x"> </span> );
                else if(transportList[i] == "subway")
                    icons.push(<span title="Involves subway ride" className="fa fa-subway grey-text horizontal-space-3x"> </span> );
                else if(transportList[i] == "ship")
                    icons.push(<span title="Involves transport by ship" className="fa fa-ship grey-text horizontal-space-3x"> </span> );
                else if(transportList[i] == "foot")
                    icons.push(<span title="Involves walking" className="fa fa-blind grey-text horizontal-space-3x"> </span> );
                else if(transportList[i] == "bus")
                    icons.push(<span title="Involves bus ride" className="fa fa-bus grey-text horizontal-space-3x"> </span> );
                else if(transportList[i] == "safari van")
                    icons.push(<span title="Involves safari van ride" className="fa fa-bus grey-text horizontal-space-3x"> </span> );
                else if(transportList[i] == "safari land cruiser")
                    icons.push(<span title="Involves safari land cruiser ride" className="fa fa-automobile grey-text horizontal-space-3x"> </span> );
                else if(transportList[i] == "bicycle")
                    icons.push(<span title="Involves surface ride" className="fa fa-bicycle grey-text horizontal-space-3x"> </span> );
                else if(transportList[i] == "motorcycle")
                    icons.push(<span title="Involves motorcycle ride" className="fa fa-motorcycle grey-text horizontal-space-3x"> </span> );
            }
        }
        return (
            <div className="row transporation-icons vertical-space">
                {icons}
            </div>
        );
    }
}

export class QuickFinderToursResult extends React.Component<{}, {}> {

    componentDidMount() {
        $('[data-toggle="popover"]').popover({trigger: 'hover'});
    }
        
    onDetailsClick(tourId, operatorId, tourType, content, serviceLevel, tourStartPlace, endingPlace, minAge, physicalDemand,
        tourMinSize, tourMaxSize, tourDuration) {
    	var modifiedItinerary = content.split("\n").map(function(item) {
    		return (
    				<span> {item} <br/> </span>    		
    				)
    	})

        toursStore.onOpenDetails({ title: tourType, description: operatorId, content: <div> {modifiedItinerary} </div>,
            serviceLevel: serviceLevel, tourStartPlace: tourStartPlace, endingPlace: endingPlace, minAge: minAge, 
            physicalDemand: physicalDemand, tourMinSize: tourMinSize, tourMaxSize: tourMaxSize, tourDuration: tourDuration});

        var visitedCountry = localeStore.getVisitedCountry();
        var currentUser = securityStore.getCurrentUser();

        imageUploadStore.ViewPhotos(visitedCountry, operatorId, tourId);
    }
    
    onSelectClick(tourObject){
    	quickFinderStore.saveSelectedTour(tourObject);    	   	
    }
    
    render() {
        var counter = 0;
    	var countryCode = localeStore.getCountryCode();
    	var currency = currencyStore.getCurrency(countryCode);
        var items = quickFinderStore.getSearchResult().results as Array<TourModel>
        var summary = <span className="lightgrey-text small-text"><i className="fa fa-frown-o"></i> {i18next.t('FOUND_NOTHING')}</span>
        if (items.length > 0)
            summary = <span className="lightgrey-text small-text">{items.length} {i18next.t('MATCHES')}</span>
        return (    		
            <div className="col-md-8 col-sm-12">
            	<RouteDetailDialog />
                <h3><i className="fa fa-search lightgrey-text"></i> {i18next.t('TOUR_RESULTS')} <i className="horizontal-space-2x"></i> {summary} </h3>
                <div className="list-group">
                {items.sort((a,b) => a.tourPrice - b.tourPrice).map(i =>
                	<div className="panel panel-default">
						<div className="panel-body">
							<div className="row">
								<div className="col-md-3 text-center">
									<i className="fa fa-3x grey-text"></i><br />
									<h5><span>{i.tourOperator.operatorName}</span> </h5>
									<p> {i.tourOperator.airportPickUp ? <span><i className="fa fa-check-circle green-text"></i>{i18next.t('AIRPORT_PICKUP')} </span> : '' } </p>  
								</div>
								
								<div className="col-md-7 grey-text">
									<h5><span className="badge">{i.tourType}</span><b> {i.tourRoute}</b> - {i.tourDuration}</h5>
									<p className="list-group-item-text grey-text">
										{i.tourEndDate ? <span><b>{datef(i.tourStartDate)}</b> to <b>{datef(i.tourEndDate)}</b> </span> : <span>{i18next.t('ANY_TIME_OF')}</span>}<br/>
										
									</p>
									<i className="vertical-space-1x"></i>
									<span> {i.withGuide ? <span><i className="fa fa-check-circle green-text"></i> {i18next.t('INCLUDING_GUIDE')}</span> : '' } </span> 
									
									
									<button className="btn btn-default btn-xs" onClick={() => this.onDetailsClick(i.tourId, i.tourOperator.operatorId, i.tourType, 
                                        !i.tourRouteObject ? 'No itinerary to show' : i.tourRouteObject.tourDescription,
                                        i.serviceLevel, i.tourStartPlace, i.endingPlace, i.minAge, i.physicalDemand, i.tourMinSize, i.tourMaxSize, i.tourDuration)}>{i18next.t('DETAILS')}
                                    </button>							
									<br />
                                                
                                    {!i.serviceLevel ? '' :
                                        ( i.serviceLevel == 'Basic' ? <span className="label label-warning" 
                                            title="Simple and clean hotels and hostels; affordable public and private transport; lots of optional activities"> Basic service</span> : 
                                            (
                                                i.serviceLevel == 'Standard' ? <span className="label label-primary" 
                                                    title="Comfortable tourist-class accommodations with character; mix of public and private transport"> Standard service</span> :
                                                (
                                                    i.serviceLevel == 'Luxury' ? <span className="label label-success" 
                                                        title="Quality accommodations and more inclusions than Standard tours, like meals, private transport, and activities"> Luxury service</span> : ''
                                                )
                                            
                                            )
                                        )
                                    }

                                    <div className="row">
                                        {i.transportation ? <TransportationItem transportationIcons={i.transportation}/> : ''}
                                    </div>							
								</div>
								
								<div className="col-md-2 text-center"><br/>									
									<p className="list-group-item-text">															
										<span className="horizontal-space-3x"></span>
										<span className="green-text">{currency}{getTourPrice(i.tourPrice, i.exchangeRate, countryCode).toFixed(2)} </span><br/>											
									</p>
									<button className="btn btn-xs btn-default pull-right" onClick={() => this.onSelectClick(i)} ><i className="fa fa-check-circle green-text"></i> {i18next.t('SELECT')}</button>
								</div>
							</div>
						</div>
					</div>
                )}
                </div>
            </div>
        );
    }
    
}

export class QuickFinderGroupsResult extends React.Component<{}, {}> {
        
    componentDidMount() {
        $('[data-toggle="popover"]').popover({trigger: 'click', html: true});
    }    
        
    onAddMe(e) {
        var groupId = e.target.value as string;
        quickFinderStore.addMe(groupId);
    }

    render() {        
        var items = quickFinderStore.getSearchResult().results as Array<GroupModel>
        var summary = <span className="lightgrey-text small-text"><i className="fa fa-frown-o"></i> {i18next.t('FOUND_NOTHING')}</span>
        if (items.length > 0)
            summary = <span className="lightgrey-text small-text">{items.length} {i18next.t('MATCHES')}</span>
        return (
            <div className="col-md-8 col-sm-12">
                <h3><i className="fa fa-search lightgrey-text"></i> {i18next.t('GROUP_RESULTS')} <i className="horizontal-space-2x"></i> {summary} </h3>
                <div className="list-group">
                {items.sort((a,b) => a.groupName - b.groupName).map(i =>
                    <li className="list-group-item">
                        <h3><span className="">{i.groupName}</span> <span className="badge">{i.tripDuration} {i18next.t('DAYS')}</span></h3>
                        <p className="list-group-item-text grey-text">
                            Registration started on <b>{datef(i.registrationDate)}</b><i className="horizontal-space"></i>
                            and {i18next.t('TRIP_STARTS_AT')} <b>{datef(i.plannedTripDate)}</b><br />
                            <span>
                                {i.groupSize} {i18next.t('PEOPLE_IN_GROUP')}
                                <span className="horizontal-space"></span>
                                <button style={{marginBottom: 5, backgroundColor: 'transparent'}} className="btn btn-default btn-xs" data-toggle="popover" title="People in this group" data-content={
                                    i.groupMembers.map(gm => `<a href="mailto:${gm.emailAddress}">${gm.emailAddress} <br /></a>`).join()}>
                                    <i className="fa fa-group"></i> Who is in...</button>
                            </span>
                        </p>
                        <button title="please login to join group" value={i.groupId} onClick={this.onAddMe} className="btn btn-xs btn-default pull-right" disabled = {true}><i className="fa fa-plus-circle green-text"></i> {i18next.t('ADD_ME')}</button>
                        <p className="list-group-item-text">
                            <a href={`mailto:${i.emailAddress}`}>{i.emailAddress} <i className="fa fa-external-link"></i></a>
                        </p>
                    </li>
                )}
                </div>
            </div>
        );
    }
    
}

export class QuickFinderCarsResult extends React.Component<{}, {}> {

    onReserve(carObject){
    	quickFinderStore.saveSelectedCar(carObject);    	   	
    }

    render() {     
    	var countryCode = localeStore.getCountryCode();
    	var visitedCountry = localeStore.getVisitedCountry();
    	var currency = currencyStore.getCurrency(countryCode);
        var items = quickFinderStore.getSearchResult().results as Array<CarModel>
        var summary = <span className="lightgrey-text small-text"><i className="fa fa-frown-o"></i> {i18next.t('FOUND_NOTHING')}</span>
        if (items.length > 0)
            summary = <span className="lightgrey-text small-text">{items.length} {i18next.t('MATCHES')}</span>
        return (
            <div className="col-md-8 col-sm-12">
                <h3><i className="fa fa-search lightgrey-text"></i> Car Results <i className="horizontal-space-2x"></i> {summary} </h3>
                <div className="list-group">
                    {items.sort((a,b) => a.dailyPrice - b.dailyPrice).map(i =>                        
                        <div className="panel panel-default">
                            <div className="panel-body">
                                <div className="row">
                                    <div className="col-md-2 text-center">
                                        <i className="fa fa-car fa-3x blue-text"></i><br />
                                        {i.operatorName}
                                    </div>
                                    <div className="col-md-8 grey-text">
                                        <h4>
                                            <span className="black-text">{i.carModel}</span> <span className="badge">{i.carType}</span> <i className="fa fa-pin"></i>
                                        </h4> 
                                       Unlimited Millage <span title="Passenger Size"><i className="fa fa-user"></i> {i.passangerSize}</span>
                                        <span title="Doors"><i className="fa fa-stop horizontal-space-2x"></i> {i.doorCount}</span>
                                        <br />
                                        { i.withDriver ? <span><i className="fa fa-hand-grab-o green-text"></i> With Driver, </span> : ""} {i.automaticTransmission ? <span><i className="fa fa-check-circle green-text"></i> Automatic Transmission </span> : 'Manual Transmission' }
                                        <div className="row">
                                            <div className="col-md-6">
                                                <h5 className="black-text">Pick-up</h5>
                                                <p>{i.streetAddress}, {i.city} {i.state}, {i.zipCode} {visitedCountry} </p>
                                            </div>
                                            <div className="col-md-6">
                                                <h5 className="black-text">Drop-off</h5>
                                                { i.returnAddress?
		                                        	<p>{i.streetAddress},{i.city} {i.state}, {i.zipCode} {visitedCountry} </p>
		                                        	: <p>{i.return_streetAddress} {i.return_city} {i.return_state}, {i.return_zipCode} {visitedCountry}</p>
		                                        }
                                            </div>
                                        </div>
                                    </div>
                                    <div className="col-md-2">
                                        <span className="pull-right green-text">{currency}{getTourPrice(i.dailyPrice, i.exchangeRate, countryCode).toFixed(2)}</span>
                                        <br /><span className="pull-right grey-text">Per Day</span><br />
                                        <span className="pull-right">{currency}{getTourPrice(i.totalPrice, i.exchangeRate, countryCode).toFixed(2)} Total</span><br />
                                        <button value={i.carId}  onClick={() => this.onReserve(i)} className="btn btn-warning pull-right">
                                            <i className="fa fa-check-circle green-text"></i> Reserve
                                        </button>
                                    </div>
                                </div>
                                
                            </div>
                        </div>                        
                    )}
                </div>
            </div>
        );
    }
    
}
