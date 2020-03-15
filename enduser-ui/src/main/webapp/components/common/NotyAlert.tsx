import * as React from 'react';
import * as i18next from 'i18next';

import {Message, MessageType} from '../../models/Messaging'
import {messageStore} from '../../stores/MessageStore'


export class NotyAlert extends React.Component<{}, {}> {

    _subscription = null
    onValChanged(val) {
        var msg = messageStore.getCurrentMessage();
        if (msg == null) return;
        this.showMessage(msg);
    }
    componentDidMount() {
        this._subscription = messageStore.subscribe(this, this.onValChanged);
    }
    componentWillUnmount() {
        this._subscription.unsubscribe(this, this.onValChanged)
    }

    showMessage(message: Message) {
        var type = 'alert';
        if (message.type == MessageType.ERROR) type = 'error';
        else if (message.type == MessageType.SUCCESS) type = 'success';
        else if (message.type == MessageType.WARNING) type = 'warning';
        noty({
            text: `${message.title ? message.title : ''} ${message.content}`,
            type: type,
            layout: 'topRight',
            timeout: 7000,
            theme: 'relax',
             animation: {
                open: 'animated bounceInRight',
                close: 'animated bounceOutRight',
             },
        });
    }
    
    render() {         
        return (
            <div></div>
        );
    }
}