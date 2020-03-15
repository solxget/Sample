import React = require('react');
import i18next = require('i18next');

import {NavBar} from '../../common/NavBar';
import {NavBottom} from '../../common/NavBottom';
import {Page} from '../Page'
import {LogedinTourist} from '../../common/LogedinTourist'
import {LogedinTouristCars} from '../../common/LogedinTouristCars'

export class TouristHome extends React.Component<{}, {}> {
    render() {
        return (
            <Page title="User">
	            <div className="row">
		            <div className="col-md-2"></div>
		            <div className="col-md-8">
		                {<LogedinTourist / }
		                
		            </div>
		            <div className="col-md-2"></div>
		        </div>
            </Page>            
        );
    }
}