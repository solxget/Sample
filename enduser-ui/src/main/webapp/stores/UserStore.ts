import NanoFlux = require('../bundle/nanoflux')

import * as i18next from 'i18next';
import {dispatcher} from '../stores/Dispatcher'
import {securityStore, UserProfile} from './SecurityStore'
import {Comm} from './CommunicationStore'


export interface UserProfile2 {
    activationCode: string,
    city: string,
    countryOfResidence: string,
    dateOfBirth: string,
    emailAddress: string,
    firstName: string,
    homeAddress: string,
    lastName: string,
    middleName: string,
    phoneNumber: string,
    registrationDate: string,
    sex: string,
    state: string,
    status: string,
    statusChangeReason: string,
    zipCode: string
}

export class UserStoreClass {
    
    getCurrentUserProfile = function(callback: (promise: Promise<UserProfile>) => void) {
        Comm.get({ url: 'user/getusertype?token=' + securityStore.getCurrentToken(), reqMethod: 'get' })
            .then(res => {
                if (res.ok) {
                    callback(res.json<UserProfile>())
                }
            })
    }
    
    notify(args?: any) {}
    subscribe(subscriber?: any, callback?: any) {}
    
}
    
NanoFlux.createStore('userStore', new UserStoreClass())
export var userStore : UserStoreClass = NanoFlux.getStore('userStore');

dispatcher.connectTo(userStore);