import * as React from 'react';
import * as i18next from 'i18next';

import {LangChoice} from './LangChoice'
import {QuickFinderSection, QuickFinderSubSection, quickFinderStore, EventData, EventType} from '../../stores/QuickFinderStore'
import {QuickFinderSubSectionToolbar} from './quick-finder/QuickFinderSubSectionToolbar'
import {QuickFinderTours, QuickFinderCustomTour, QuickFinderGroups, QuickFinderNewGroup, QuickFinderCars} from '../common/quick-finder/QuickFinderPages'
import * as Pikaday from 'pikaday'

export class QuickFinder extends React.Component<{}, {}> {
    
    _subscription = null
    onValChanged(val: EventData) {
        if (val.eventType == EventType.SECTION_CHANGED || val.eventType == EventType.SUB_SECTION_CHANGED) {
            this.forceUpdate(() =>             	
                $('.pikaday1').each((index, item) => new Pikaday({ field: $(item)[0], minDate: new Date(), maxDate: new Date('2018-12-31')}))                
            );
        }
    }
    componentDidMount() {
        this._subscription = quickFinderStore.subscribe(this, this.onValChanged);
        $('.pikaday1').each((index, item) => new Pikaday({ field: $(item)[0], minDate: new Date(), maxDate: new Date('2018-12-31') })   
    }
    
    componentWillUnmount() {
        this._subscription.unsubscribe(this, this.onValChanged);
    }
    
    getButtonClass(section: QuickFinderSection) {
        if (quickFinderStore.getCurrentSection() == section)
            return 'btn btn-primary';
        else return 'btn btn-default';
    }
    
    onSelectSection(e) {
        var section = e.currentTarget.value as QuickFinderSection;
        quickFinderStore.onSetSection(section);
    }
        
    getPage(section: QuickFinderSection) {
        if (section == QuickFinderSection.TOURS)
            return <QuickFinderTours />
        else if (section == QuickFinderSection.GROUPS)
            return <QuickFinderGroups />
        else if (section == QuickFinderSection.CUSTOM_TOUR)
            return <QuickFinderCustomTour />
        else if (section == QuickFinderSection.NEW_GROUP)
            return <QuickFinderNewGroup />
        else if (section == QuickFinderSection.CARS)
            return <QuickFinderCars />
    }
    
    render() {

        return <div className="text-center flipInY animated">
            <div className="btn-group btn-group-lg">
                <button className={this.getButtonClass(QuickFinderSection.TOURS)} 
                    onClick={this.onSelectSection} value={QuickFinderSection.TOURS}>
                    <i className="fa fa-plane"></i><br /> <span className="hidden-xs">{i18next.t('TOURS')}</span>
                </button>
                <button className={this.getButtonClass(QuickFinderSection.CARS)} 
                    onClick={this.onSelectSection} value={QuickFinderSection.CARS}>
                    <i className="fa fa-car"></i><br /> <span className="hidden-xs">{i18next.t('CARS')}</span>
                </button>
                <button className={this.getButtonClass(QuickFinderSection.GROUPS)}
                    onClick={this.onSelectSection} value={QuickFinderSection.GROUPS}>
                    <i className="fa fa-group"></i><br /> <span className="hidden-xs">{i18next.t('GROUPS')}</span>
                </button>
                <button className={this.getButtonClass(QuickFinderSection.CUSTOM_TOUR)}
                    onClick={this.onSelectSection} value={QuickFinderSection.CUSTOM_TOUR}>
                    <i className="fa fa-archive"></i><br /> <span className="hidden-xs">{i18next.t('CUSTOM_TOUR')}</span>
                </button>
                <button className={this.getButtonClass(QuickFinderSection.NEW_GROUP)}
                    onClick={this.onSelectSection} value={QuickFinderSection.NEW_GROUP}>
                    <i className="fa fa-plus"></i><br /> <span className="hidden-xs">{i18next.t('NEW_GROUP')}</span>
                </button>
            </div>
            <label className="control-label visible-xs">{i18next.t(QuickFinderSection[quickFinderStore.getCurrentSection()])}</label>
            <div className="vertical-space-2x"></div>
            <div className="row">
                <div className="col-md-2"></div>
                <div className="col-md-8">
                    { this.getPage(quickFinderStore.getCurrentSection()) }
                </div>                
                <div className="col-md-2"></div>
            </div>
            <div className="vertical-space-2x"></div>
        </div>
    }
}