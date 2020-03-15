import NanoFlux = require('../bundle/nanoflux')

import * as i18next from 'i18next';
import {dispatcher} from '../stores/Dispatcher'
import {Message, MessageType} from '../models/Messaging'


export class MessageStoreClass {
    
    private currentMessage : Message
    
    getCurrentMessage = function(): Message {
        return this.currentMessage;
    }
    
    getMessage = function(message: Message | string) {
        var msg : Message;
        if (typeof(message) == "string") {
            msg = { content: <string>message }; 
        }
        else msg = <Message>message;
        return msg;
    }
    
    onShowMessage = function(message: Message | string) {
        var msg : Message = this.getMessage(message);
        if (msg.type == undefined) msg.type = MessageType.INFO;
        this.currentMessage = msg;
        this.notify();
    }
    
    onShowError = function(message: Message | string) {
        var msg : Message = this.getMessage(message);
        msg.type = MessageType.ERROR;
        this.onShowMessage(msg);
    }
    
    onShowSuccess = function(message: Message | string) {
        var msg : Message = this.getMessage(message);
        msg.type = MessageType.SUCCESS;
        this.onShowMessage(msg);
    }
    
    onShowWarning = function(message: Message | string) {
        var msg : Message = this.getMessage(message);
        msg.type = MessageType.WARNING;
        this.onShowMessage(msg);
    }
    
    notify(args?: any) {}
    subscribe(subscriber?: any, callback?: any) {}
    
}
    
NanoFlux.createStore('messageStore', new MessageStoreClass())
export var messageStore : MessageStoreClass = NanoFlux.getStore('messageStore');

dispatcher.connectTo(messageStore);