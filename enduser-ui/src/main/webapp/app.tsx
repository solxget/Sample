// import * as $ from 'jquery';
import NanoFlux = require('./bundle/nanoflux');
import * as React from 'react';
import * as ReactDOM from 'react-dom';
import * as Bootstrap from 'bootstrap';
import * as i18next from 'i18next';
import { Router, Route, Link, IndexRoute, hashHistory, browserHistory } from 'react-router';

import { Login } from './components/pages/Login';
import { Tours } from './components/pages/Tours';
import { Welcome } from './components/pages/Welcome';
import { MyTours } from './components/pages/MyTours';
import { MyCars } from './components/pages/MyCars';
import { GoogleMap } from './components/pages/GoogleMap';
import { GroupSeasonRegistration } from './components/pages/home/GroupSeasonReg';
import { OperatorTourRegistration } from './components/pages/home/OperatorTourReg';
import { OperatorTourEdit } from './components/pages/home/OperatorTourEdit';
import { TourOperatorHome } from './components/pages/home/TourOperator';
import { PageNotFound } from './components/pages/PageNotFound';
import { localeStore } from './stores/LocaleStore';
import { Profile } from './components/pages/home/Profile';
import { ChangePassword } from './components/pages/home/ChangePassword';
import { Help } from './components/pages/home/Help';
import { Home } from './components/pages/home/Home';
import { OperatorCarRegistration } from './components/pages/home/OperatorCarReg';
import { OperatorCarEdit } from './components/pages/home/OperatorCarEdit';
import { PrivacyPolicy } from './components/pages/PrivacyPolicy';
import { TermsOfService } from './components/pages/TermsOfService';
import { AboutTouranb } from './components/pages/AboutTouranb';
import { ImageUpload } from './components/common/ImageUpload';
import { SuccessPage } from './components/pages/home/SuccessPage';
import { ErrorPage } from './components/pages/home/ErrorPage';
import { UploadSuccess } from './components/pages/home/UploadSuccess';
import { CreateOperatorAccount } from './components/pages/home/CreateOperatorAccount';
import { DocumentUpload } from './components/pages/home/DocumentUpload';
import { AttractionsPage } from './components/pages/AttractionsPage';
import {TravelTips} from './components/pages/TravelTips';


export class App extends React.Component<{}, {}>{
    _subscription = null
    onValChanged(val) { this.forceUpdate() }
    componentDidMount() {
        this._subscription = localeStore.subscribe(this, this.onValChanged)
    }
    componentWillUnmount() {
        this._subscription.unsubscribe(this, this.onValChanged)
    }
    
    render() {
        return (
            <Router history={hashHistory}>
                <Route path="/" component={Welcome}/>
                <Route path="/welcome" component={Welcome}/>
                <Route path="/login" component={Login}/>
                <Route path="/tours" component={Tours}/>
                <Route path="/profile" component={Profile} />
                <Route path="/ChangePassword" component={ChangePassword} />
                <Route path="/help" component={Help} />
                <Route path="/home" component={Home} />
                <Route path="/cars" component={OperatorCarRegistration} />
                <Route path="/editcar" component={OperatorCarEdit} />     
                <Route path="/my-tours" component={MyTours} />
                <Route path="/my-cars" component={MyCars} /> 
                <Route path="/packages" component={OperatorTourRegistration} /> 
                <Route path="/edit" component={OperatorTourEdit} />
                <Route path="/season" component={GroupSeasonRegistration} />		
                <Route path="/company" component={TourOperatorHome} />
                <Route path="/privacy-policy" component={PrivacyPolicy} />	
                <Route path="/terms-of-service" component={TermsOfService} />	
                <Route path="/about-touranb" component={AboutTouranb} />
                <Route path="/service" component={ImageUpload} />
                <Route path="/operator-account" component={CreateOperatorAccount} />
                <Route path="/documents" component={DocumentUpload} />
                <Route path="/confirmation" component={UploadSuccess} />
                <Route path="/attractions" component={AttractionsPage} />
                <Route path="/tips" component={TravelTips} />

                
                <Route path="/success" component={SuccessPage} />
                <Route path="/error" component={ErrorPage} />
                <Route path="*" component={PageNotFound} />
            </Router>
        );
    }
};

window['i18next'] = i18next;
window['ReactDOM'] = ReactDOM;
window['React'] = React;
window['App'] = App;
window['NanoFlux'] = NanoFlux;