import NanoFlux = require('../bundle/nanoflux')

import * as i18next from 'i18next';
import {dispatcher} from '../stores/Dispatcher'

export enum Country {
    ETHIOPIA, KENYA, SOUTH_AFRICA, TANZANIA, UGANDA
}

export interface ClientAddress {
	ip: string,
	country_code: string,
	country_name: string,
	region_code: string,
	region_name: string,
	city: string,
	zip_code: string,
	time_zone: string,
	latitude: number,
	longitude: number,
	metro_code: number
}

export class LocaleStoreClass {
    
    private visitedCountry: Country;
    private clientAddress: string;
	private visitorCountry: CountryCode
    
    onSetLanguage = function(lang) {
        i18next.changeLanguage(lang)
        this.notify()
    }
    
    getVisitedCountry = function() : Country { return this.visitedCountry; }
    
    setVisitedCountry = function(country: Country) {
        this.visitedCountry = country;       
    }
    
    setCountryCode = function(countryCode: string){
    	this.clientAddress = countryCode;
    }
    
    getCountryCode = function(): string {
    	return this.clientAddress;
	}

    
    notify(args?: any) {}
  //  notify(args: EventData) {}
    subscribe(subscriber?: any, callback?: any) {}
    
}
    
NanoFlux.createStore('localeStore', new LocaleStoreClass())
export var localeStore : LocaleStoreClass = NanoFlux.getStore('localeStore');

dispatcher.connectTo(localeStore);