import NanoFlux = require('../bundle/nanoflux')

import * as i18next from 'i18next';
import * as moment from 'moment';

import {dispatcher} from '../stores/Dispatcher'
import {Comm} from './CommunicationStore'
import {messageStore} from './MessageStore'
import {securityStore} from './SecurityStore'
import {TourOperator} from './QuickFinderStore'

export enum EventType {
    TOUR_SELECTED, CAR_SELECTED
}

export interface EventData {
    eventType: EventType
    data?: any
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

export class CheckOutStoreClass {
	private selectedTour: TourModel 
	
	saveSelectedTour = function(tourObject: TourModel) {
		this.selectedTour = tourObject;
		this.notify({eventType: EventType.TOUR_SELECTED})
	}

	getSelectedTour = function(){
		return this.selectedTour;
	}	
	
	
    notify(args: EventData) {}
    subscribe(subscriber?: any, callback?: any) {}
}



NanoFlux.createStore('checkOutStore', new CheckOutStoreClass())
export var checkOutStore : CheckOutStoreClass = NanoFlux.getStore('checkOutStore');

dispatcher.connectTo(checkOutStore);