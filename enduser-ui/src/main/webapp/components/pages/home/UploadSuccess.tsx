import React = require('react');
import i18next = require('i18next');

import {NavBar} from '../../common/NavBar';
import {NavBottom} from '../../common/NavBottom';

export class UploadSuccess extends React.Component<{}, {}> {
    
    onBack(e){
        e.preventDefault();
        window.location.href = '#/documents';
    }

    render() {
        
        return (
            <div className="container">
                <NavBar />
                <div className="vertical-space"></div>
                <div className="text-center">
                <br /><br /><br />
                    <h5> {i18next.t('MESSAGE_SENT_1')}</h5> 
                    <h5> {i18next.t('MESSAGE_SENT_2')}</h5> <br />
                    <h5> {i18next.t('MESSAGE_SENT_3')}</h5> <br />
                    <br />
                    <br />
                    <br />
                    <br />
                    <button onClick={this.onBack.bind(this)} type="submit" className="btn btn-md btn-default">{i18next.t('BACK')}</button>
                    <input hidden={true} id="submit-login" type="submit"/>
                </div>
                <NavBottom />
            </div>
        );
    }
}