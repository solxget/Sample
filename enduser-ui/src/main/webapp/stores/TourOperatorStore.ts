import NanoFlux = require('../bundle/nanoflux')

import * as i18next from 'i18next';
import {dispatcher} from '../stores/Dispatcher'

import {TourPackagePrice, Tour, TourRoute, Car, GroupSeasonCast} from '../models/Tour'
import {Comm} from './CommunicationStore'
import {applicationStore} from './ApplicationStore'
import {securityStore} from './SecurityStore'
import {messageStore} from './MessageStore'
import {getErrorMsg} from '../models/ErrorCodes'
//import {CarRegistration} from '../components/common/CarReg'


export class TourOperatorStoreClass {
    
    saveGroupPrice = function(data: TourPackagePrice) {
        // TODO: Save the prices here.
        // tour/saveg1price, tour/saveg2price, tour/saveg3price, ...
    }
    
    saveTourRoute = function(data: TourRoute) {
        // TODO: Save tour route
        // tour/savetourroute
    }
    
    getGroupSeasonCast = function(): any {
        // TODO: fetch group season cast
        // tour/getgroupseason
    }
    
    saveGroupSeasonCast = function(data: GroupSeasonCast) {
        // TODO: Update group season cast
        // tour/savegroupseason
    }
    
    registerTour = function(data: Tour) {
        // TODO: Create a tour
        // tour/register
    }
    
    updateTour = function(data: Tour) {
        // TODO: update a tour
        // tour/update
    }
    
    deleteTour = function(tourId: string) {
        // tour/delete
    }
    
    registerCar = function(car: Car) {
        applicationStore.onSetBusyStatus(true);
        Comm.get({ url: 'car/save', body: car, reqMethod: 'post' })
            .then(res => {
                applicationStore.onSetBusyStatus(false);
                if (res.ok) {
                    window.location.href = '#/Home';
                    messageStore.onShowSuccess('Car registered succesfully.');
                    populateTourId()
                }
                else if (res.status > 550 && res.status < 600) {
                    messageStore.onShowError(getErrorMsg(res.status));
                }
                else {
                    messageStore.onShowError('Sorry, we could not register the car. Something went wrong.');
                }
            })
            .catch(err => {
                applicationStore.onSetBusyStatus(false);
                messageStore.onShowError('Sorry, we could not register the car. Something went wrong.');
            });
            
    }
    
    notify(args?: any) {}
    subscribe(subscriber?: any, callback?: any) {}
    
}
    
NanoFlux.createStore('tourOperatorStore', new TourOperatorStoreClass())
export var tourOperatorStore : TourOperatorStoreClass = NanoFlux.getStore('tourOperatorStore');

dispatcher.connectTo(tourOperatorStore);