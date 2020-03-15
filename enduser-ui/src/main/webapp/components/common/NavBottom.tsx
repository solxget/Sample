import * as React from 'react';
import * as i18next from 'i18next';


import {LangChoice} from './LangChoice'
import {VisitedCountryChoice} from './VisitedCountryChoice'
import {Link} from 'react-router'
import {QuickFinderSection, quickFinderStore} from '../../stores/QuickFinderStore'
import {securityStore} from '../../stores/SecurityStore'
import * as Roles from '../../models/User'

export class NavBottom extends React.Component<{}, {}> {

    componentDidMount() {

    }
    
    render() {
       
        return (
            <nav className="navbar navbar-default footer-style">
                <div className="container-fluid " >
                    <div className="navbar-header">
                        <button type="button" className="navbar-toggle collapsed" data-toggle="collapse"
                            data-target="#bottom-navbar" aria-expanded="false">
                            <span className="sr-only">Toggle Navigation</span>
                            <span className="icon-bar"></span>
                            <span className="icon-bar"></span>
                            <span className="icon-bar"></span>
                        </button>
                    </div>
                    <div className="collapse navbar-collapse" id="bottom-navbar">                        
                        <div className="row text-center footer-blocks">
                            <div className="col-md-3">
                                <h5>About</h5>
                                <a href="#/about-touranb">{i18next.t('ABOUT_TOURANB')}</a> <br />
                                <a href="#/privacy-policy">{i18next.t('PRIVACY_POLICY')}</a> <br />
                                <a href="#/terms-of-service">{i18next.t('TERMSOF_SERVICE')}</a> <br />
                            { /*   <a href="#/maps">Google Map</a> <br />   */}
                            </div>
                            <div className="col-md-3 social-media">
                                <h5>Contact Us</h5>
                                <span>Email : <a href="mailto:connection@touranb.com"> connection@touranb.com </a> </span>  <br />
                                <span>Phone : <a href="tel:+17349265495">+1 (734) 926-5495</a> </span> <br />
                                <span> 301 E Liberty S., Ann Arbor </span> <br />
                                <span>USA, 48104 </span> <br />
                            </div>
                            <div className="col-md-3">
                                <h5>Links </h5>
                                <a href="#/tips">Travel Tips </a>  <br />
                                <a href="#/attractions">Attractions </a> <br />
                                <a href="#/operator-account">{i18next.t('CREATE_OPERTOR_ACCOUNT')}</a> <br />
                            {/*    <a href="#/careers">Careers </a>  <br />  */}
                            </div>
                            <div className="col-md-3 social-media">
                                <h5>Connect With Us</h5>

                                <a href="https://twitter.com/Touranb1" target="_blank" className="fa fa-twitter-square" data-show-count="false"></a>
                                <span className="horizontal-space-2x"></span>

                                <a href="https://www.facebook.com/Touranb-Tour-Africa-and-Beyond-1840355586179143/" target="_blank" className="fa fa-facebook-official"></a>
                                <span className="horizontal-space-2x"></span>

                                <a href="https://plus.google.com/u/4/114015957313667823032" target="_blank" className="fa fa-google-plus-square" >  </a>
                                <span className="horizontal-space-2x"></span>

                                <br /> 
                                <a href="https://www.instagram.com/tourafricaandbeyond/?hl=en" target="_blank" className="fa fa-instagram">  </a>
                                <span className="horizontal-space-2x"></span>

                                <a href="https://www.linkedin.com/organization/11034315/admin/updates" target="_blank" className="fa fa-linkedin-square">  </a>
                                <span className="horizontal-space-2x"></span>


                                <a href="https://www.pinterest.com/touranb9571/" target="_blank" className="fa fa-pinterest">  </a>
                                <span className="horizontal-space-2x"></span>

                                <br />
                                <a href="https://www.youtube.com/channel/UCXw1blxwOpL4PgtYRTqEKwQ" target="_blank" className="fa fa-youtube">  </a>
                                <span className="horizontal-space-2x"></span>
                                
                            </div>
                        </div>
                        <div className="row text-center">
                             <br />
                             <span style={{fontSize: '0.9em'}}>
                                {i18next.t('COPYRIGHT')} 2016-17 &copy; Touranb llc.
                            </span>
                        </div>
                    </div>                     
                </div>
            </nav>
        );
    }
}
