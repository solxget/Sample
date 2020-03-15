import * as React from 'react';
import * as ReactDOM from 'react-dom';
import * as i18next from 'i18next';
import * as moment from 'moment'

import {NavBar} from '../common/NavBar';
import {NavBottom} from '../common/NavBottom';
import {BusyIndicator} from '../common/BusyIndicator';
import {TourItemProps, toursStore} from '../../stores/ToursStore'
import {imageUploadStore} from '../../stores/ImageUploadStore'
import {quickFinderStore, QuickFinderSection, EventData, EventType, TourModel, GroupModel, CarModel} from '../../stores/QuickFinderStore'
import {localeStore} from '../../stores/LocaleStore'
import {currencyStore} from '../../stores/CurrencyStore'
import {securityStore} from '../../stores/SecurityStore'
import {TourItemList} from '../common/TourItemList'
import {RouteDetailDialog, TourImageItem} from '../common/quick-finder/QuickFinderResults'
import {FestivalItem} from '../common/FestivalItem'
import {GoogleMap} from './GoogleMap'

export interface ToursList {
    tourTypes: Array<any>
}

export function getTourPrice(tourPrice, exchangeRate, countryCode): number{
    return currencyStore.getTourPrice(tourPrice, exchangeRate, countryCode);
}

/*class TourDetailDialog extends React.Component<{}, {}> {
        
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
        var imgURL = `./bundle/${item && item.imageUrl ? item.imageUrl : 'traveling2.jpg'}`;
        var smallScreenImage = {
            width: (screen.width < 400 ? '97%' : '')
        }
        return  <div id="tourItemDetailDialog" className="modal fade" tabIndex={-1} role="dialog" ariaLabelledBy="">
                    <div className="modal-dialog modal-lg">
                        <div className="modal-content">
                            <div className="modal-header">
                                <h4 className="modal-title">
                                    <button title={i18next.t('BACK')} className="btn btn-primary" data-dismiss="modal">
                                        <i className="fa fa-arrow-left"></i>
                                    </button> {item == null ? '' : item.title}
                                </h4>
                            </div>
                            <div className="modal-body">
                                <div>
                                    <img className="pull-right image-format" src={imgURL} alt="" style={smallScreenImage}/>
                                </div>
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
 */
class AdvertImageItem extends React.Component<{}, {}> {

    _subscription = null;

    onValChanged(val) {  
        this.forceUpdate(() => {
            var item = imageUploadStore.getImageUrls();
            var urls = item.url;
            if(item.url[1]){
                $('#itImageH1')[0].src = urls[1];
                $('#liBlockH1')[0].style.display= 'block';
            }else{
                $('#itImageH1')[0].src = '';
            }

            if(item.url[2]){
                $('#itImageH2')[0].src = urls[2];
                $('#liBlockH2')[0].style.display= 'block';
            }else{
                $('#itImageH2')[0].src = '';
            }

            if(item.url[3]){ 
                $('#itImageH3')[0].src = urls[3];
                $('#liBlockH3')[0].style.display= 'block';
            }else{
                $('#itImageH3')[0].src = '';
            }

            if(item.url[4]){
                $('#itImageH4')[0].src = urls[4];
                $('#liBlockH4')[0].style.display= 'block';
            }else{
                $('#itImageH4')[0].src = '';
            }

            if(item.url[5]) {
                $('#itImageH5')[0].src = urls[5];
                $('#liBlockH5')[0].style.display= 'block';
            }else{
                $('#itImageH5')[0].src = '';
            }

            if(item.url[6]){ 
                $('#itImageH6')[0].src = urls[6];
                $('#liBlockH6')[0].style.display= 'block';
            }else{
                $('#itImageH6')[0].src = '';
            }

            this.refresh();  
        }); 
    }

    refresh() {

        if(imageUploadStore.getSliderH() != null){
            imageUploadStore.getSliderH().destroy();
        }

        var node = ($('#sliderLight-H') as any);
        var sliderInit = node.lightSlider({
            gallery: false;
            item: 1,
            loop: true,
            auto: true,
            speed: 300,
            slideMargin: 0,
        });
        imageUploadStore.saveSliderH(sliderInit);
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
            imageUploadStore.getImageUrls() != null ? 
            (
                <div className="iternary-images-H" id="iternaryImagesH">
                    <ul id="sliderLight-H" >
                        <li id="liBlockH1" className="li-block-H" >
                            <img className="iternary-image-H" src="xx" alt="" id="itImageH1" ></img>
                        </li>
                        <li id="liBlockH2" className="li-block-H" >
                            <img className="iternary-image-H" src="xx" alt="" id="itImageH2" ></img>
                        </li>
                        <li id="liBlockH3" className="li-block-H" >
                            <img className="iternary-image-H" src="xx" alt="" id="itImageH3" ></img>
                        </li>
                        <li id="liBlockH4" className="li-block-H" >
                            <img className="iternary-image-H" src="xx" alt="" id="itImageH4" ></img>
                        </li>
                        <li id="liBlockH5" className="li-block-H" >
                            <img className="iternary-image-H" src="xx" alt="" id="itImageH5" ></img>
                        </li>
                        <li id="liBlockH6" className="li-block-H" >
                            <img className="iternary-image-H" src="xx" alt="" id="itImageH6" ></img>
                        </li>
                    </ul>
                </div>
            )
            : <br />
        );
    }
}

export class Tours extends React.Component<{}, {}> {
    
    _subscription = null

    getInitState() : ToursList {
        return { tourTypes: [] }
    }
    constructor() {
        super();
        this.state = this.getInitState();
    }

    onValChanged(val: EventData) {

        if (val.eventType == EventType.COUNTRY_CHANGED) {
            this.forceUpdate(() =>              
               imageUploadStore.ViewPhotos(localeStore.getVisitedCountry(), 'Background_img', 'background');
            );
        }
        else if(val.eventType == EventType.SECTION_CHANGED){
            if(quickFinderStore.getCurrentSection() == QuickFinderSection.TOURS){
            //    console.log("Tours");
            }
            else if(quickFinderStore.getCurrentSection() == QuickFinderSection.CARS){
            //    console.log("Cars");
            }
            else if(quickFinderStore.getCurrentSection() == QuickFinderSection.GROUPS){
            //    console.log("Groups");
            }
            else if(quickFinderStore.getCurrentSection() == QuickFinderSection.CUSTOM_TOUR){
            //    console.log("Custom Tour");
            }
            else if(quickFinderStore.getCurrentSection() == QuickFinderSection.NEW_GROUP){
            //    console.log("New Group");
            }    
        }
        else{
          //  var items1 = quickFinderStore.getAdvertTourSearchResult().results as Array<TourModel>
         //   console.log(" here we go " + JSON.stringify("Here we go " + JSON.stringify(items1));
        }
    }


    googleAnalitcs(){ 
        (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
        (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
        m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
        })(window,document,'script','https://www.google-analytics.com/analytics.js','ga');

        ga('create', 'UA-97028779-1', 'auto');
        ga('send', 'pageview');
    }
    
    componentDidMount() {
        this._subscription = quickFinderStore.subscribe(this, this.onValChanged);
        quickFinderStore.getAdvertTours(localeStore.getVisitedCountry());
        imageUploadStore.ViewPhotos(localeStore.getVisitedCountry(), 'Background_img', 'background');
        this.googleAnalitcs();

    }

    componentWillUnmount() {
        this._subscription.unsubscribe(this, this.onValChanged) 
    }
    
    render() {
        //var items1 = quickFinderStore.getAdvertTourSearchResult().results as Array<TourModel>
        //console.log(" here we go " + JSON.stringify("Here we go " + items1));
        return (
            <div id="toursPanel">
                <div className="row">
                    <div className="col-md-2"></div>
                    <div className="col-md-8">
                        <h3>{i18next.t('TOURS_DESCRIPTION_1')}</h3>
                        <p>{i18next.t('TOURS_DESCRIPTION_2')}</p> 
                        <p>{i18next.t('TOURS_DESCRIPTION_3')}</p>
                    </div>
                    <div className="col-md-2"></div>
                </div>

                <div className="row" id="quickFinderblock" >
                    <AdvertImageItem />
                </div>

                <div className="container" >
        
                    <RouteDetailDialog />  

                    <QuickFinderAdvertResult />
                    <div className="vertical-space-4x"></div>

                </div>
            </div>
        );
    }
}


export class QuickFinderAdvertResult extends React.Component<{}, {}> {
    
    _subscription = null
    onValChanged(val: EventData) {
        
        var advertSearchResults = quickFinderStore.getAdvertTourSearchResult();

        if (val.eventType == EventType.ADVERT_SEARCH && advertSearchResults != null)
        {
            this.forceUpdate(() => {
                $('#advertToursSearchResults').show();
            });
        }
        
        else{
             this.doNothing();
        }
    }

    componentDidMount() {
        this._subscription = quickFinderStore.subscribe(this, this.onValChanged);
    }

    componentWillUnmount() {
        this._subscription.unsubscribe(this, this.onValChanged)
    }
    
    doNothing(){

    }

    render() {
        var result = quickFinderStore.getAdvertTourSearchResult();

        if (result != null) {
            if (result.resultType == QuickFinderSection.TOURS)
                var ui = <QuickFinderAdvertToursResult />
//            else if(result.resultType == QuickFinderSection.TOURS)
//                var ui = <QuickFinderAdvertCarsResult />
        }
        return (
            <div id="advertToursSearchResults" hidden={true} >     
                {ui == null ? null : ui}
            </div>
        );
    }
    
}


export class QuickFinderAdvertToursResult extends React.Component<{}, {}> {

    refresh() {
        
        var node = ($('.slider-light') as any);
        node.lightSlider({ enableDrag: false, responsive: [{breakpoint: 800, settings: {item: 1}}] });
        setTimeout(() => node.css('height', ''), 500);
        
        var thisNode = $(ReactDOM.findDOMNode(this));
        thisNode.find('.accordion-toggle').on('click', function(e) {
            
            var elt = $(e.target);
            elt.find('.accordion-content').slideToggle('fast');

            // Toggle this icon.
            var thisIcon = elt.find('.accordion-toggle-icon');
            if (thisIcon.hasClass('fa-chevron-up')) {
                thisIcon.removeClass('fa-chevron-up').addClass('fa-chevron-down');
            }
            else {
                thisIcon.removeClass('fa-chevron-down').addClass('fa-chevron-up');
            }
            
        }); 
    }

    componentDidMount() {
        $('[data-toggle="popover"]').popover({trigger: 'hover'});
        this.refresh();
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
        var countryCode = localeStore.getCountryCode();
        var currency = currencyStore.getCurrency(countryCode);
        var itemsList = quickFinderStore.getAdvertTourSearchResult().results as Array<TourModel>
    //    var urls = imageUploadStore.getImageUrls().url;
        return (    
                      
                <div style={{background: 'rgba(255,255,255,0.7)'}}>
                    {itemsList.map(i =>
                            <TourItemList items={[
                                {   title: <span>
                                            <h5><span className="badge">{i.tourType}</span><b> {i.tourRoute} </b> - {i.tourDuration}</h5>
                                            
                                            <span className="green-text pull-right">{currency} {getTourPrice(i.tourPrice, i.exchangeRate, countryCode).toFixed(2)} </span><br/>
                                            <button className="btn btn-xs btn-default pull-right" onClick={() => this.onSelectClick(i)} ><i className="fa fa-check-circle green-text"></i> {i18next.t('SELECT')}</button> <br />
                                        
                                            <button className="btn btn-default btn-xs pull-right" onClick={() => this.onDetailsClick(i.tourId, i.tourOperator.operatorId, i.tourType, 
                                                !i.tourRouteObject ? 'No itinerary to show' : i.tourRouteObject.tourDescription,
                                                i.serviceLevel, i.tourStartPlace, i.endingPlace, i.minAge, i.physicalDemand, i.tourMinSize, i.tourMaxSize, i.tourDuration)}>{i18next.t('DETAILS')}
                                            </button>   

                                        </span>, 
                                    imageUrl: 'Table Mountain.jpg', 
                                    description:  !i.serviceLevel ? '' :
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

                                    content: <span> {!i.tourRouteObject ? <p> No itinerary to show</p> : i.tourRouteObject.tourDescription } </span>
                                },

                            ]}/>
                    )}
                 </div>
        );
    }
    
}
