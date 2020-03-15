import NanoFlux = require('../bundle/nanoflux')

import * as i18next from 'i18next';
import {dispatcher} from '../stores/Dispatcher'


export interface TourItemProps {
    title: string
    content: any
    description?: string
    serviceLevel: string
    tourStartPlace: string 
    endingPlace: string
    minAge: number
    physicalDemand: number
    tourMinSize: number
    tourMaxSize: number
    tourDuration: number
}

export class ToursStoreClass {
    
    private _currentItem: TourItemProps
    private _isDetailMode = false
    private tourId: string
    private carId: string

    getCurrentItem = function(): TourItemProps { return this._currentItem; }
    isDetailMode = function(): boolean { return this._isDetailMode; }
    getTourId = function(): string { return this.tourId; }
    getCarId = function(): string { return this.carId; }

    saveTourId = function(tourId: string) {
        this.tourId = tourId;
    }
    
    saveCarId = function(carId: string) {
        this.carId = carId;
    }
    
    onOpenDetails = function(item: TourItemProps) {
        this._currentItem = item;
        this._isDetailMode = true;
        this.notify();
    }
    
    onCloseDetails = function() {
        this._currentItem = null;
        this._isDetailMode = false;
        this.notify();
    }
    
    notify(args?: any) {}
    subscribe(subscriber?: any, callback?: any) {}
    
}
    
NanoFlux.createStore('toursStore', new ToursStoreClass())
export var toursStore : ToursStoreClass = NanoFlux.getStore('toursStore');

dispatcher.connectTo(toursStore);