import NanoFlux = require('../bundle/nanoflux')

import * as i18next from 'i18next';
import {dispatcher} from '../stores/Dispatcher';
import {messageStore} from './MessageStore';
import {applicationStore} from './ApplicationStore';
import {getErrorMsg} from '../models/ErrorCodes';
import * as Config from '../config/Constants';
import * as moment from 'moment';


export class UserProfile {
    emailAddress: string = '';
    firstName: string = 'Guest';
    registrationDate: string = '';
    role: string = 'guest';
    status: string = '';
    token: string = '';
    loginTime: Date = null;
	groupSeasonFlag: boolean = false;
	carRentalOnly: boolean = false;
	country: string = '';
}

export class SecurityStoreClass {       
    
    private currentUser : UserProfile = new UserProfile()
    private baseUrl = `${Config.SERVER_URL}${Config.API_URL}`
    
    onLogin = function(email: string, password: string) {
        
        applicationStore.onSetBusyStatus(true);
        fetch(`${this.baseUrl}user/login`, {
            method: 'POST',
            body: JSON.stringify({ emailAddress: email, password: password }),
            headers: {
                 "Content-Type": "application/json"
            },
        })
            .then((data) => {
                if (!data.ok) {
                    applicationStore.onSetBusyStatus(false);
                    var errorMsg = getErrorMsg(data.status);
                    if (errorMsg != null)
                        //data.text().then(msg => messageStore.onShowError(msg));
                        messageStore.onShowError(errorMsg);
                    else
                        messageStore.onShowError('Sorry, we couldn\'t log you in. something went wrong.');
                    return;
                }
                data.json().then((data: UserProfile) => {
                    this.currentUser = data;
                    this.currentUser.loginTime = new Date();
                    localStorage.setItem('user', JSON.stringify(data));
                    applicationStore.onSetBusyStatus(false);
                    window.location.href = '#/home';                    
                })
                .catch(err => {
                    applicationStore.onSetBusyStatus(false);
                    messageStore.onShowError('Sorry, we couldn\'t log you in. Something went wrong.');
                });
            })
            .catch(err => {
                applicationStore.onSetBusyStatus(false);
                messageStore.onShowError('Sorry, we couldn\'t log you in. Something went wrong.');
            });
            
        this.notify();
    }
    onLogout = function() {
        fetch(`${this.baseUrl}user/logout`, {
            method: 'POST',
            headers: {
                "Content-Type": "application/json",
                 "Authorization": `Bearer ${this.currentUser.token} ${this.currentUser.emailAddress}`
            },
        })
        .then(res => {
            this.currentUser = new UserProfile();
            localStorage.setItem('user', JSON.stringify(this.currentUser));
            window.location.href = '#/';
            this.notify()
        });
    }
    isUserLoggedIn = function(): boolean {
        return this.getCurrentUserProfile().token.length > 0;
    }
    getCurrentUser = function(): string {
        return this.getCurrentUserProfile().firstName;
    }
    getCurrentUserEmail = function(): string {
        return this.getCurrentUserProfile().emailAddress;
    }
    getCurrentUserCountry = function(): string {
        return this.getCurrentUserProfile().country;
    }
    getCurrentToken = function(): string {
        return this.getCurrentUserProfile().token;
    }
    getCurrentUserProfile = function() : UserProfile {
        var result = this.currentUser;
        if (result.token.length <= 0) {
            var profile = JSON.parse(localStorage.getItem('user')) as UserProfile;
            if (profile != null) {
                if (profile.token.length > 0) {
                    var diff = moment().diff(moment(profile.loginTime), 'minutes')
                    if (diff > 300) {
                    //    this.onLogout();
                    }
                }
                return profile;
            }
        }
        return result;
    }
    
    notify(args?: any) {}
    subscribe(subscriber?: any, callback?: any) {}
    
}
    
NanoFlux.createStore('securityStore', new SecurityStoreClass())
export var securityStore : SecurityStoreClass = NanoFlux.getStore('securityStore');

dispatcher.connectTo(securityStore);