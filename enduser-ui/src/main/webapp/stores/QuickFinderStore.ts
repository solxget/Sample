import NanoFlux = require('../bundle/nanoflux')

import * as i18next from 'i18next';
import {dispatcher} from '../stores/Dispatcher'
import {Comm} from './CommunicationStore'
import {messageStore} from './MessageStore'
import {securityStore} from './SecurityStore'
import * as moment from 'moment';

export class QFSearchResult {
    resultType: QuickFinderSection
    results: Array<any>
    
    constructor(type: QuickFinderSection, results: Array<any>) {
        this.results = results;
        this.resultType = type;
    }
}

export interface GroupMember {
    emailAddress: string,
    touristStatus: string,
}

export interface TourOperator {
    operatorId: string,
    operatorName: string,
    rating: string,
    phoneNumber: string,
    airportPickUp: boolean,
    houseNumber: string,
    buildingName: string,
    subCity: string,
    city: string,
    county: string,
    kebela: string,
}

export interface TourModel {
    tourId: string,
    tourRoute: string,
    tourDuration: string,
    tourStartDate: string,
    tourEndDate: string,
    tourStartPlace: string,
    tourStartTime: string,
    tourMaxSize: string,
    tourMinSize: string,
    tourType: string,
    tourPrice: number,
    rating: string,
    tourOperator: TourOperator,
    tourRouteObject: any
}

export interface GroupModel {
    country: string,
    groupId?: string,
    groupName: string,
    groupSize?: string,
    plannedTripDate: string,
    registrationDate?: string,
    tripDuration: string,
    emailAddress: string,
    groupMembers?: GroupMember[],
}

export interface CustomTourModel {
    tourStartDate: string,
    tourEndDate: string,
    tourDescription: string,
    email: string,
    travellersCount: number,
}

export interface TourFilterParams {
    tourType: number
    tripStartDate: string
    tripEndDate: string
    tripDuration?: number
    travellers: number
    allInclusive: boolean
    country: string
}

export interface CarFilterParams {
    pickUpAddress: string,
    returnAddress: string,
    pickUpDate: string,
    returnDate: string,
    rentalDuration?: number,
    pickUpTime: string,
    dropOffTime: string,
    country: string,
}

export interface CarModel {
    carId: string,
    operatorId: string,
    operatorName: string,
    carType: string,
    carModel: string,
    passangerSize: number,
    doorCount?: number,
    withDriver: boolean,
    dailyPrice: number,
    houseNumber?: number,
    buildingName?: string,
    kebela?: string,
    county?: string,
    subCity?: string,
    city?: string,
    returnAddress: string,
    carCount?: number,
    automaticTransmission?: boolean,
	tourOperator: TourOperator,
}

export interface GroupFilterParams {
    tripDate: string,
    tripDuration: number,
    country: string
}

export interface GroupSeasonModel {
	 season1StartDate : string,
	 season2StartDate : string,
	 season3StartDate : string,
	 season4StartDate : string,
	 season5StartDate : string,
	 season5EndDate : string,
	 g1MinSize : number,
	 g2MinSize : number,
	 g3MinSize : number,
	 g4MinSize : number,
	 g5MinSize : number, 
}

export enum EventType {
    SEARCH_INVOKED, SEARCH_CLOSED, SECTION_CHANGED, SUB_SECTION_CHANGED, TOUR_SELECTED, CAR_SELECTED, TOUR_CHECKOUT, 
    CAR_CHECKOUT, COUNTRY_CHANGED, ADVERT_SEARCH
}

export interface EventData {
    eventType: EventType
    data?: any
}

export enum QuickFinderSection {
    TOURS, CARS, GROUPS, CUSTOM_TOUR, NEW_GROUP
}

export class QuickFinderStoreClass {
    
    private currentSection = QuickFinderSection.TOURS;
    private searchResult: QFSearchResult;
    private advertTourSearchResult: QFSearchResult;
    private advertCarSearchResult: QFSearchResult;
    private selectedTour: Arrays<TourModel>;
    private selectedCar: Arrays<CarModel>;
    private tourCheckOutFlag: boolean;
    private carCheckOutFlag: boolean;
    
	saveSelectedTour = function(tourObject: TourModel) {
    	this.tourCheckOutFlag = false;
    	this.selectedCar = null;
    	this.searchResult = null;
		this.selectedTour = [tourObject];
		this.notify({eventType: EventType.TOUR_SELECTED})
	}
    
	saveSelectedCar = function(carObject: CarModel) {
    	this.carCheckOutFlag = false;
    	this.selectedTour = null;
    	this.searchResult = null;
		this.selectedCar = [carObject];
		this.notify({eventType: EventType.CAR_SELECTED})
	}

	getSelectedTour = function() : Array<TourModel> {
		return this.selectedTour;
	}	
	
	getSelectedCar = function() : Array<CarModel> {
		return this.selectedCar;
	}	
	
	showTourCheckOut = function() {
		this.tourCheckOutFlag = true;
		this.notify({eventType: EventType.TOUR_CHECKOUT})
	}
	
	showCarCheckOut = function() {
		this.carCheckOutFlag = true;
		this.notify({eventType: EventType.CAR_CHECKOUT})
	}	
	
	getTourCheckOutFlag = function() : boolean{
		return this.tourCheckOutFlag;		
	}	
    
	getCarCheckOutFlag = function() : boolean{
		return this.carCheckOutFlag;		
	}	
	
    onCountryChanged = function(section : QuickFinderSection) { 
        if (section == null)
            section = QuickFinderSection.TOURS;
        this.selectedTour = null;
        this.selectedCar = null;
        this.searchResult = null;
        this.advertTourSearchResult = null;
        this.advertCarSearchResult = null;
        this.currentSection = section;
        this.notify({ eventType: EventType.COUNTRY_CHANGED });
    }
	
    onSetSection = function(section : QuickFinderSection) { 
        if (section == null)
            section = QuickFinderSection.TOURS;
        this.selectedTour = null;
        this.selectedCar = null;
        if(!(this.currentSection == section)){
        	this.currentSection = section;
        	this.notify({ eventType: EventType.SECTION_CHANGED });
        }
    }
    
    getCurrentSection = function() : QuickFinderSection { return this.currentSection; }
    
    getSearchResult = function() : QFSearchResult { return this.searchResult; }
    setSearchResult = function (result: QFSearchResult) {
    	this.selectedTour = null;
    	this.selectedCar = null;
        this.searchResult = result;
        this.notify({eventType: EventType.SEARCH_INVOKED})
    }

    getAdvertTourSearchResult = function() : QFSearchResult { return this.advertTourSearchResult; }
    setadvertTourSearchResult = function (result: QFSearchResult) {this.advertTourSearchResult = result; }

    getadvertCarSearchResult = function() : QFSearchResult { return this.advertCarSearchResult; }
    setadvertCarSearchResult = function(result: QFSearchResult){ this.advertCarSearchResult = result; }

    findTours = function(filter: TourFilterParams) {
        if (window['demo']) {
            this.setSearchResult({
                resultType: QuickFinderSection.TOURS,
                results: [
                    {
                        tourId: '2', tourRoute: 'Churchil Road',
                        tourDuration: '9', tourType: 'Ethiopian Festival',
                        tourPrice: '3250.98', operatorId: '1',
                        rating: '5',
                    },
                    {
                        tourId: '9', tourRoute: 'Route Details',
                        tourDuration: '15', tourType: 'Ethiopian Festival',
                        tourPrice: '652.00', operatorId: '1',
                        rating: '5',
                    },
                    {
                        tourId: '4', tourRoute: 'A to B',
                        tourDuration: '19', tourType: 'Sky Dive',
                        tourPrice: '15230.23', operatorId: '4',
                        rating: '5',
                    },
                ]
            });
        }
        else {
            var duration = moment(filter.tripEndDate).diff(moment(filter.tripStartDate), 'days');
            Comm.get({url: `tour/get?key=tour_type&value=${filter.tourType}&inclusiveFlag=${filter.allInclusive.toString()}&groupSize=${filter.travellers}&tripDate=${filter.tripStartDate}&tripEndDate=${filter.tripEndDate}&tripDuration=${duration}&country=${filter.country}`, reqMethod: 'get'})
                .then(res => {
                    if (res.ok) {
                        res.json().then(j => {
                            this.setSearchResult({ results: j, resultType: QuickFinderSection.TOURS });
                            this.notify({eventType: EventType.SEARCH_INVOKED});
                        });
                    }
                });
        }
    }
    
    findGroups = function(filter: GroupFilterParams) {
        if (window['demo']) {
            this.setSearchResult({
                resultType: QuickFinderSection.GROUPS,
                results: [
                    { groupSize: '8', groupName: 'The Force', plannedTripDate: '2016-09-12', tripDuration: '5', emailAddress: 'tostao@gmail.com' },
                    { groupSize: '8', groupName: 'The Titans', plannedTripDate: '2017-07-11', tripDuration: '15', emailAddress: 'gerviniho@yahoo.co.uk' },
                    { groupSize: '8', groupName: 'D\'Acheivers', plannedTripDate: '2018-01-29', tripDuration: '9', emailAddress: 'zico@hotmail.com' },
                ]
            });
        }
        else {
            Comm.get({url: `group/showgroups?plannedTripDate=${filter.tripDate}&tripDuration=${filter.tripDuration}&country=${filter.country}`, reqMethod: 'get'})
                .then(res => {
                    if (res.ok) {
                        res.json().then(j => {
                            this.setSearchResult({ results: j, resultType: QuickFinderSection.GROUPS });
                            this.notify({eventType: EventType.SEARCH_INVOKED});
                        });
                    }
                })
        }
    }

    createCustomTour = function(customTour: CustomTourModel) {
        Comm.get({url: 'tour/save', body: customTour, reqMethod: 'post'})
            .then(res => {
                if (res.ok) {
                    res.text().then(j => {
                        messageStore.onShowSuccess('Customed tour request successfully submitted');
                    });
                }
            })
            .catch(err => messageStore.onShowError('Sorry we couldn\'t create the custom tour. Something went wrong.'));
    }

    createNewGroup = function(group: GroupModel) {
        Comm.get({url: 'group/register', body: group, reqMethod: 'post'})
            .then(res => {
                if (res.ok) {
                    res.text().then(j => {
                        messageStore.onShowSuccess('Group successfully created.');
                    });
                }
            })
            .catch(err => { 
                messageStore.onShowError('Sorry we couldn\'t create the group you provided. Something went wrong.');
            })
    }

    addMe = function(groupId: string) {

        Comm.get({url: 'tourist/addtogroup', body: { emailAddress: securityStore.getCurrentUserEmail(), groupId: groupId }, reqMethod: 'post'})
            .then(res => {
                if (res.ok) {
                    res.text().then(j => {
                        messageStore.onShowSuccess('You are now in the group.');
                    });
                }
            })
            .catch(err => { 
                messageStore.onShowError('Sorry we couldn\'t add you to the group. Something went wrong.');
            })

    }
    
    findCars = function(filter: CarFilterParams) {
        
        if (window['demo']) {
            this.setSearchResult({ results: [
                { carId: '01', carType: 'Standard', carModel: 'Chrysler 20 Suaj', passangerSize: 5, doorCount: 2, operatorName: 'Alfacatoure Inc.', dailyPrice: 520 },
                { carId: '04', carType: 'SUV', carModel: 'Toyota Carina 5x', passangerSize: 5, doorCount: 4, operatorName: 'Tour Ops LLC.', dailyPrice: 350.25 },
            ], resultType: QuickFinderSection.CARS });
        }
        else {
        	var duration = moment(filter.returnDate).diff(moment(filter.pickUpDate), 'days');
        	var duration2 = moment(filter.dropOffTime, "HH:mm a").diff(moment(filter.pickUpTime, "HH:mm a"));
        	duration = duration2>0 ? duration+1 : duration; 
            Comm.get({url: `car/get?pickUpAddress=${filter.pickUpAddress}&pickUpDate=${filter.pickUpDate}&returnAddress=${filter.returnAddress}&returnDate=${filter.returnDate}&rentalDuration=${duration}&country=${filter.country}&pickUpTime=${filter.pickUpTime}&dropOffTime=${filter.dropOffTime}`, reqMethod: 'get'})
                .then(res => {
                    if (res.ok) {
                        res.json().then(j => {
                            this.setSearchResult({ results: j, resultType: QuickFinderSection.CARS });
                            this.notify({eventType: EventType.SEARCH_INVOKED});
                        });
                    }
                    /*else if (res.status >= 550 && res.status < 600)
                        res.text().then(msg => messageStore.onShowError(msg));
                    else
                        messageStore.onShowError('Sorry, we couldn\'t find cars. Something went wrong.');*/
                });
        }
    }
    
    getPickupAddress = function(countryObject: string): Promise<Response> {
        return Comm.get({url: `car/pickupaddress?country=${countryObject}`, reqMethod: 'get'});
    }
    
    getTourTypes = function(countryObject: string): Promise<Response> {
        return Comm.get({url: `tour/tourtype?country=${countryObject}`, reqMethod: 'get'});
    }
    
    getAdvertTours = function(countryObject: string): Promise<Response> {
        Comm.get({url: `tour/advert?country=${countryObject}`, reqMethod: 'get'})
           .then(res => {
                if (res.ok) {
                    res.json().then(j => {
                        this.setadvertTourSearchResult({ results: j, resultType: QuickFinderSection.TOURS });
                        this.notify({eventType: EventType.ADVERT_SEARCH});
                    });
                }
            });
    }

    getAdvertCars = function(countryObject: string): Promise<Response> {
        return Comm.get({url: `car/country?country=${countryObject}`, reqMethod: 'get'});
    }

    getLandingTourTypes = function(): Promise<Response> {
        return Comm.get({url: `tour/landingtourtype?country=${securityStore.getCurrentUserCountry()}`, reqMethod: 'get'});
    }
    
    notify(args: EventData) {}
    subscribe(subscriber?: any, callback?: any) {}
    
}
    
NanoFlux.createStore('quickFinderStore', new QuickFinderStoreClass())
export var quickFinderStore : QuickFinderStoreClass = NanoFlux.getStore('quickFinderStore');

dispatcher.connectTo(quickFinderStore);