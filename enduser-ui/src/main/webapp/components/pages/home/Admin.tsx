import React = require('react');
import i18next = require('i18next');

import {NavBar} from '../../common/NavBar';
import {NavBottom} from '../../common/NavBottom';
import {Page} from '../Page';

export class AdminHome extends React.Component<{}, {}> {
    render() {
        return (
            <Page title="Admin">
                <div className="text-center">
                    <h1><i className="fa fa-check-circle-o green-text"></i> Welcome, Admin</h1>
                </div>
            </Page>
        );
    }
}