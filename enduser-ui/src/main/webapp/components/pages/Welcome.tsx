
import * as React from 'react';
import * as i18next from 'i18next';

import {Page} from './Page';
import {GoogleMap} from './GoogleMap'
import {QuickFinder} from '../common/QuickFinder';
import {Tours} from './Tours';
import {quickFinderStore, QuickFinderSection} from '../../stores/QuickFinderStore'
import {localeStore, ClientAddress} from '../../stores/LocaleStore'
import {MessageBanner} from '../common/MessageBanner';
import {QuickFinderResult} from '../common/quick-finder/QuickFinderResults'
import {Comm} from '../../stores/CommunicationStore'
import {applicationStore} from '../../stores/ApplicationStore'


export interface WelcomeProps {
    section: QuickFinderSection
}

export class Welcome extends React.Component<WelcomeProps, {}> {
    
    componentWillMount() {
        quickFinderStore.onSetSection(this.props.section);
    }   
    
    detectBrwoser(){

    	// Opera 8.0+
		var isOpera = (!!window.opr && !!opr.addons) || !!window.opera || navigator.userAgent.indexOf(' OPR/') >= 0;
        if(isOpera) {
        }

		// Firefox 1.0+
		var isFirefox = typeof InstallTrigger !== 'undefined';
        if(isFirefox){
        }

		// Safari 3.0+ "[object HTMLElementConstructor]" 
		var isSafari = Object.prototype.toString.call(window.HTMLElement).indexOf('Constructor') > 0 || (function (p) { return p.toString() === "[object SafariRemoteNotification]"; })(!window['safari'] || safari.pushNotification);
		if(isSafari){
        }

		// Internet Explorer 6-11
		var isIE = /*@cc_on!@*/false || !!document.documentMode;
        if(isIE){
            if($.browser.msie && parseFloat($.browser.version)&lt;8){
                //    alert("The browser version you are using is old. Please update to Internet Explorer 11 or use Google Chrome or Firefox");
            
                //Other way of detecting browser version
                var ua = window.navigator.userAgent;
                var msie = ua.indexOf('MSIE ');
                if (msie > 0) {
                    // IE 10 or older => return version number
                   // return parseInt(ua.substring(msie + 5, ua.indexOf('.', msie)), 10);
                   alert("The browser version you are using is old. Please update to Internet Explorer 11 or use Google Chrome or Firefox");
                }

                var trident = ua.indexOf('Trident/');
                if (trident > 0) {
                    // IE 11 => return version number
                 //   var rv = ua.indexOf('rv:');
                  //  return parseInt(ua.substring(rv + 3, ua.indexOf('.', rv)), 10);
                }

            }

        }

		// Edge 20+
		var isEdge = !isIE && !!window.StyleMedia;
        if(isEdge){
        }

		// Chrome 1+
		var isChrome = !!window.chrome && !!window.chrome.webstore;
        if(isChrome){

        }

		// Blink engine detection
	//	var isBlink = (isChrome || isOpera) && !!window.CSS;
      //  if(isBlink){
     //       console.log("We are unable to detect the browser you are using. Please use Firefox or Google chrome for better experiance ");
      //  }
	}  

    componentDidMount() {
    	$.getJSON('//freegeoip.net/json/?callback=?', function(data) { 
    		localeStore.setCountryCode(data.country_code);
    		var VisitorInfo = {
        			city: data.city,
        			country_code: data.country_code,
        			country_name: data.country_name,
        			ip: data.ip,
        			latitude: data.latitude,
        			longitude: data.longitude,
        			metro_code: data.metro_code,
        			region_code: data.region_code,
        			region_name: data.region_name,
        			time_zone: data.time_zone,
        			zip_code: data.zip_code
        		}
        	
        	 Comm.getSilent({ url: 'user/visitor', body: VisitorInfo, reqMethod: 'post' })
             .then(res => {
                 applicationStore.onSetBusyStatus(false);
             })
             .catch(err => {
                 applicationStore.onSetBusyStatus(false);
             });    	
		});	
		this.detectBrwoser();
    }
    
    render() {
        
        var quickFinderPanelStyle = {
            paddingTop: 30, paddingBottom: 30,
            background: "url('bundle/traveling2.jpg')",
            backgroundRepeat: 'no-repeat',
            backgroundSize: (screen.height < screen.width ? '100%' : ''),
            marginBottom: 20,
            marginTop: -20
        }
        return (
            <Page title="Home">
                <div className="row" id="quickFinderblock" style={quickFinderPanelStyle}>
                    <div className="col-md-2">
                    
                    </div>
                    <div className="col-md-8">
                        <div className="panel panel-default panel-quick-finder">
                            <div className="panel-body">                                
                                <QuickFinder />
                            </div>
                        </div>
                    </div>
                    <div className="col-md-2">
                    
                    </div>
                </div>

                <QuickFinderResult />
                <Tours />

            </Page>
        );
    }
