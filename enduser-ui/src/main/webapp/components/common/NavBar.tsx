import * as React from 'react';
import * as i18next from 'i18next';

import {LangChoice} from './LangChoice'
import {VisitedCountryChoice} from './VisitedCountryChoice'
import {Link} from 'react-router'
import {QuickFinderSection, quickFinderStore} from '../../stores/QuickFinderStore'
import {securityStore} from '../../stores/SecurityStore'
import * as Roles from '../../models/User'

export class NavBar extends React.Component<{}, {}> {
    
    onSignOut(e) {
        securityStore.onLogout();
    }
    
    onVisitTours(e) {
        e.preventDefault();
        window.location.href = '#/';
        quickFinderStore.onSetSection(QuickFinderSection.TOURS);
    }
    
    onVisitGroups(e) {
        e.preventDefault();
        window.location.href = '#/';
        quickFinderStore.onSetSection(QuickFinderSection.GROUPS);
    }
    
    render() {
        return <nav className="navbar navbar-default navbar-fixed-top">
            <div className="container-fluid">
                <div className="navbar-header">
                    <button type="button" className="navbar-toggle collapsed" data-toggle="collapse"
                        data-target="#main-navbar" aria-expanded="false">
                        <span className="sr-only">Toggle Navigation</span>
                        <span className="icon-bar"></span>
                        <span className="icon-bar"></span>
                        <span className="icon-bar"></span>
                    </button>
                    <Link to="/" className="navbar-brand">
                        <span style={{fontWeight: 'bold'}}>tour
                            <span className="label label-default"
                                style={{backgroundColor: '#DDD', fontSize: 'large', paddingLeft: 2, paddingRight: 2, paddingBottom: 2}}>
                            <span className="green-text">a</span>
                            <span className="red-text">n</span>
                            <span className="blue-text">b</span>
                            </span>
                        </span>
                        <span style={{marginLeft: 10, color: '#999'}} className="x-small-text">tour africa & beyond</span>
                    </Link>
                </div>
                <div className="collapse navbar-collapse" id="main-navbar">
                	
                    <ul className="nav navbar-nav">                        
                        {
                            !securityStore.isUserLoggedIn()
                            ?
                            [
                             	<VisitedCountryChoice />                               
/*								<li key={1}><a href="#" onClick={this.onVisitTours}>{i18next.t('TOURS')}</a></li>
								<li key={2}><a href="#" onClick={this.onVisitGroups} hidden={securityStore.getCurrentUserProfile().role == Roles.ROLE_OPERATOR}>
								{i18next.t('GROUPS')} </a></li>*/    

                             ] : 
                            	(
                                    !(securityStore.getCurrentUserProfile().role == Roles.ROLE_OPERATOR)                      
                                    ?                            	  
                                    [
  
                                    ] :
                            			(
                                            (securityStore.getCurrentUserProfile().status == 'Unconfirmed') ?
                                                ''
                                                :
                                                (
                                                    !(securityStore.getCurrentUserProfile().status == 'Active')
                                                    ? 
                                                		[
                                                            <li key={1}><a href="#/company">{i18next.t('COMPANY_REGISTRATION')}</a></li>
                                                            <li key={2}><a href="https://s3.amazonaws.com/touranbemailbucket/Files%2FTour+Operator+User+Manual.pdf" target="_blank" className="fileLink"> {i18next.t('USER_MANUAL')} </a></li>
                                                		]:
                                                    	(
                                                    		!securityStore.getCurrentUserProfile().groupSeasonFlag
                                                    		&& !securityStore.getCurrentUserProfile().carRentalOnly
                                                    		?
                                                                [
                                                				    <li key={1}><a href="#/season">{i18next.t('GROUP_SEASON')}</a></li>
                                                                    <li key={2}><a href="https://s3.amazonaws.com/touranbemailbucket/Files%2FTour+Operator+User+Manual.pdf" target="_blank" className="fileLink"> {i18next.t('USER_MANUAL')} </a></li>
                                                				]:
                                            					(
                	                                            	securityStore.getCurrentUserProfile().carRentalOnly
                	                                                ?		                                                  
                	                                				[
                	                                                     <li key={1}><a href="#/my-cars">{i18next.t('MY_CARS')}</a></li>,
                	                                                     <li key={2}><a href="#/cars">{i18next.t('CAR_REGISTRY')}</a></li>
                                                                         <li key={3}><a href="https://s3.amazonaws.com/touranbemailbucket/Files%2FTour+Operator+User+Manual.pdf" target="_blank" className="fileLink"> {i18next.t('USER_MANUAL')} </a></li>
                	                                                 ]:	
                	                                                	[
                    	                                                	<li key={1}><a href="#/season">{'Group Season'}</a></li>  
                    	                                                	<li key={2}><a href="#/my-tours">{'My Tours'}</a></li>
                    	                                                    <li key={3}><a href="#/my-cars">{i18next.t('MY_CARS')}</a></li>		                                                                                                                
                    	                                                    <li key={4}><a href="#/packages">{'Package Registry'}</a></li>
                    	                                                    <li key={5}><a href="#/cars">{i18next.t('CAR_REGISTRY')}</a></li>
                                                                            <li key={6}><a href="https://s3.amazonaws.com/touranbemailbucket/Files%2FTour+Operator+User+Manual.pdf" target="_blank" className="fileLink"> {i18next.t('USER_MANUAL')} </a></li>
                	                     								]
                                                                )
                                                    	) 
                                                )                                              			
                            			)
                             )
                        }               	 
                    </ul>
                        
                        <ul className="nav navbar-nav navbar-right">
                            <li key={1}><a className="cyan-text horizontal-space-4x" >{i18next.t('SUPPORT_EMAIL')}</a></li>
                            <LangChoice />
                            <li className="dropdown">
                                <a href="#" className="dropdown-toggle" data-toggle="dropdown" role="button"
                                    aria-haspopup="true" aria-expanded="false">
                                    <i className="fa fa-user"></i> { securityStore.isUserLoggedIn() ? securityStore.getCurrentUser() : i18next.t('SIGN_IN')} 
                                    <span className="caret"></span>
                                </a>
                                {
                                    !securityStore.isUserLoggedIn() ?                                    
                                        <ul className="dropdown-menu">
                                            <li><Link to="/Login">{i18next.t('SIGN_IN')}</Link></li>
                                          {/*  <li><Link to="/Login">{i18next.t('REGISTER')}</Link></li>*/}
                                            <li role="separator" className="divider"></li>
                                            <li><Link to="/Help">{i18next.t('HELP')}</Link></li>
                                        </ul> :
                                        <ul className="dropdown-menu">                                        
                                            <li><Link to="/Profile">Profile</Link></li>                                        
                                            <li><Link to="/Home">Home</Link></li>
                                            <li><Link to="/ChangePassword">Change Password</Link></li>
                                            <li role="separator" className="divider"></li>
                                            <li><a onClick={this.onSignOut}>Sign Out</a></li>
                                        </ul>
                                }
                            </li>
                        </ul>
                    
                </div>
            </div>
        </nav>
    }
}