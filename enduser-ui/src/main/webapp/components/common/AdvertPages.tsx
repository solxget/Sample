import * as React from 'react';
import * as ReactDOM from 'react-dom';
import * as i18next from 'i18next';
import * as moment from 'moment'

import {quickFinderStore, QuickFinderSection, EventData, EventType, TourModel, GroupModel, CarModel} from '../../stores/QuickFinderStore'
import {toursStore} from '../../stores/ToursStore'
import {checkOutStore} from '../../stores/checkOutStore'
import {localeStore} from '../../stores/LocaleStore'
import {currencyStore} from '../../stores/CurrencyStore'
import {imageUploadStore} from '../../stores/ImageUploadStore'
import {securityStore} from '../../stores/SecurityStore'
import {TourSummary} from './TourSummary'
import {CarSummary} from './CarSummary'
import {ConfirmPayment} from './ConfirmPayment'
import {CarCheckOut} from './CarCheckOut'

import {Page} from '../Pages/Page';
import {TourItemList} from './TourItemList'

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
        imageUploadStore.ViewPhotos(localeStore.getVisitedCountry(), 'Background_img', 'background', true);
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

class TourImageItem extends React.Component<{}, {}> {

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

class RouteDetailDialog extends React.Component<{}, {}> {
    
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

class TransportationItem extends React.Component<TransportationItemProps, {}> {

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

export class AdvertToursResult extends React.Component<{}, {}> {
    
    render() {

        return (    		
            <p>Solomon </p>
        );
    }
    
}