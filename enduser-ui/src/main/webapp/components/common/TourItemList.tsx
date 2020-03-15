import * as React from 'react';
import * as i18next from 'i18next';

import {TourItem} from './TourItem'
import {TourItemProps} from '../../stores/ToursStore'

export interface TourItemListProps {
    items: TourItemProps[]
}

export class TourItemList extends React.Component<TourItemListProps, {}> {    
    render() {
        return (
            <ul className="slider-light">
                {this.props.items.map(i => 
                    <TourItem title={i.title} content={i.content} imageUrl={i.imageUrl} description={i.description} key={i.title}/>
                )}
            </ul>
        );
    }
}