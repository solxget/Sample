import * as React from 'react';
import * as i18next from 'i18next';

import {applicationStore} from '../../stores/ApplicationStore'
import {appActions} from '../../actions/SystemActions'

export class BusyIndicator extends React.Component<{}, {}> {
    _subscription = null
    onValChanged(val) {
        if (applicationStore.getBusyStatus() && applicationStore.getIsBlocked())
            $('#default-busy-indicator').show();
        else $('#default-busy-indicator').hide();
    }
    componentDidMount() {
        this._subscription = applicationStore.subscribe(this, this.onValChanged)
    }
    componentWillUnmount() {
        this._subscription.unsubscribe(this, this.onValChanged)
    }
    render() {
        return (
            <div className="loading" id="default-busy-indicator" style={{display: 'none'}}>Loading&#8230;</div>
        );
    }
}

