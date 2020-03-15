
import NanoFlux = require('../bundle/nanoflux')

import * as i18next from 'i18next';
import {dispatcher} from '../stores/Dispatcher'


export class CurrencyStoreClass {
	protected currencyEnum = {
		US: "$",
		CA: "$",
		GB: "$",
		IT: "$",
		DE: "$",
		FR: "$",
		ES: "$",
	};
	
	getCurrency = function(countryCode: string): string { 
		return "$";
		// var currencySymbol;
		// switch(countryCode){
		// 	case "US": 
		// 		currencySymbol = this.currencyEnum.US;   //why is others don't take this ???????
		// 		break;
		// 	case "CA": 
		// 		currencySymbol = currencyEnum.CA;
		// 		break;
		// 	case "GB": 
		// 		currencySymbol = currencyEnum.GB;
		// 		break;
		// 	case "IT": 
		// 		currencySymbol = currencyEnum.IT;
		// 		break;
		// 	case "DE": 
		// 		currencySymbol = currencyEnum.DE;
		// 		break;
		// 	case "FR": 
		// 		currencySymbol = currencyEnum.FR;
		// 		break;
		// 	case "ES": 
		// 		currencySymbol = currencyEnum.ES;
		// 		break;
		// 	default:
		// 		currencySymbol = currencyEnum.US;
		// }		

		// return currencySymbol; 
	}
	
    getTourPrice = function(tourPrice: number, exchangeRate: any, countryCode: string): number{
    	return tourPrice;
  //   	var newPrice;
  //   	switch(countryCode){
  //   	case "USA":
  //   		for(var i=0; i < exchangeRate.length; i++){
  //   			if (exchangeRate[i].currency == "USA"){
  //   				newPrice = exchangeRate[i].exchangeRate * tourPrice;
  //   				break;
  //   			}
  //   		}
  //   		break;
		// case "CA": 
		// 	for(var i=0; i < exchangeRate.length; i++){
  //   			if (exchangeRate[i].currency == "Canada"){
  //   				newPrice = exchangeRate[i].exchangeRate * tourPrice;
  //   				break;
  //   			}
  //   		}
		// 	break;
		// case "GB": 
		// 	for(var i=0; i < exchangeRate.length; i++){
  //   			if (exchangeRate[i].currency == "England"){
  //   				newPrice = exchangeRate[i].exchangeRate * tourPrice;
  //   				break;
  //   			}
  //   		}
		// 	break;
		// case "IT": 
		// 	for(var i=0; i < exchangeRate.length; i++){
  //   			if (exchangeRate[i].currency == "Europe"){
  //   				newPrice = exchangeRate[i].exchangeRate * tourPrice;
  //   				break;
  //   			}
  //   		}
		// 	break;
		// case "DE": 
		// 	for(var i=0; i < exchangeRate.length; i++){
  //   			if (exchangeRate[i].currency == "Europe"){
  //   				newPrice = exchangeRate[i].exchangeRate * tourPrice;
  //   				break;
  //   			}
  //   		}
		// 	break;
		// case "FR": 
		// 	for(var i=0; i < exchangeRate.length; i++){
  //   			if (exchangeRate[i].currency == "Europe"){
  //   				newPrice = exchangeRate[i].exchangeRate * tourPrice;
  //   				break;
  //   			}
  //   		}
		// 	break;
		// case "ES": 
		// 	for(var i=0; i < exchangeRate.length; i++){
  //   			if (exchangeRate[i].currency == "Europe"){
  //   				newPrice = exchangeRate[i].exchangeRate * tourPrice;
  //   				break;
  //   			}
  //   		}
		// 	break;
		// default:
		// 	for(var i=0; i < exchangeRate.length; i++){
  //   			if (exchangeRate[i].currency == "USA"){                   // Why Europe
  //   				newPrice = exchangeRate[i].exchangeRate * tourPrice;
  //   				break;
  //   			}
  //   		}
  //   	}
    	
  //   	return newPrice;
    }
	
    notify(args?: any) {}
    subscribe(subscriber?: any, callback?: any) {}

}

    
NanoFlux.createStore('currencyStore', new CurrencyStoreClass())
export var currencyStore : CurrencyStoreClass = NanoFlux.getStore('currencyStore');

dispatcher.connectTo(currencyStore);