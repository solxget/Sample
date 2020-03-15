import NanoFlux = require('../bundle/nanoflux')

import * as i18next from 'i18next';
import {dispatcher} from '../stores/Dispatcher'


export class ApplicationStoreClass {
    
    private isBusy = false;
    private isBlocked = false;
    
    onSetBusyStatus = function(status: boolean, isBlocked: boolean = false) {
        this.isBusy = status;
        this.isBlocked = isBlocked;
        this.notify(this.isBusy);
        if (!this.isBlocked && status)
            window['NProgress'].start();
        else if (!status && !isBlocked)
            window['NProgress'].done();
    }
    
    getBusyStatus = function(): boolean {
        return this.isBusy;
    }
    getIsBlocked = function() : boolean {
        return this.isBlocked;
    }
    notify(args?: any) {}
    subscribe(subscriber?: any, callback?: any) {}
    
}
    
NanoFlux.createStore('applicationStore', new ApplicationStoreClass());
export var applicationStore : ApplicationStoreClass = NanoFlux.getStore('applicationStore');

dispatcher.connectTo(applicationStore);