import * as React from 'react';
import * as ReactDOM from 'react-dom';
import * as i18next from 'i18next';

import {TourItemProps, toursStore} from '../../stores/ToursStore'

export class TourItem extends React.Component<TourItemProps, {}> {
    
    componentDidMount() {
        
        // Expand or collapse this panel
        var node = $(ReactDOM.findDOMNode(this));
        node.find('.accordion-toggle').on('click', function() {
            
            node.find('.accordion-content').slideToggle('fast');

            // Toggle this icon.
            var thisIcon = node.find('.accordion-toggle-icon');
            if (thisIcon.hasClass('fa-chevron-up')) {
                thisIcon.removeClass('fa-chevron-up').addClass('fa-chevron-down');
            }
            else {
                thisIcon.removeClass('fa-chevron-down').addClass('fa-chevron-up');
            }
            
        });

    }
    
    onDetailsClick() {
        toursStore.onOpenDetails(this.props)
    }
    
    render() {
        var imgURL = `./bundle/${this.props.imageUrl ? this.props.imageUrl : 'traveling2.jpg'}`;
        return (
            <li>
                <div className="thumbnail">
                    <div style={{overflow: 'hidden', height: 200}}>
                        <img src={imgURL} style={{height: 250}} />
                    </div>
                    <div className="caption text-left">
                        <h4>
                            {this.props.title}
                        </h4>
                        <p>
                            {this.props.description}
                            <span className="pull-right btn btn-xs btn-info" onClick={this.onDetailsClick.bind(this)}>
                                {i18next.t('MORE')} ...
                            </span>
                        </p>
                    </div>
                </div>
            </li>
        );
    }
}