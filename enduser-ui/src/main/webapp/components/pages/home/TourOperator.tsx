import React = require('react');
import i18next = require('i18next');

import {NavBar} from '../../common/NavBar';
import {NavBottom} from '../../common/NavBottom';
import {Page} from '../Page'
import {securityStore} from '../../../stores/SecurityStore'
import {OperatorRegistration} from '../../common/OpRegistration'
import {TourPackageReg} from '../../common/TourPackageReg'
import {CarRegistration} from '../../common/CarReg'
import {SeasonReg} from '../../common/SeasonReg'
import {FileUpload} from '../../common/FileUpload'

export class TourOperatorHome extends React.Component<{}, {}> {
    
    render() {

        return (
            <Page title="Operator">
                <div className="row">
                    <div className="col-md-2"></div>
                    <div className="col-md-8">
                        { 
                            securityStore.getCurrentUserProfile().status == 'Unconfirmed' ? <FileUpload />
                            : (
                                securityStore.getCurrentUserProfile().status == 'New User' ? <OperatorRegistration />
                                : (
                                	securityStore.getCurrentUserProfile().carRentalOnly ?  <CarRegistration />
                                	: (
        	                        	securityStore.getCurrentUserProfile().groupSeasonFlag ?  <TourPackageReg /> 
        	                        	: <SeasonReg /> 
                                	)
                                	
                                ) 
                            )
                        }
                    </div>
                    <div className="col-md-2"></div>
                </div>
            </Page>
        );
    }
}