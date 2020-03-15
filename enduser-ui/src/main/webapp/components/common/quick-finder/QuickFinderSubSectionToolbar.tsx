import * as React from 'react';
import * as i18next from 'i18next';

import {LangChoice} from '../LangChoice'
import {QuickFinderSection, QuickFinderSubSection, quickFinderStore} from '../../../stores/QuickFinderStore'


export interface QuickFinderSubSectionToolbarProps {
    
    subSections: QuickFinderSubSection[]
    
}

export class QuickFinderSubSectionToolbar extends React.Component<QuickFinderSubSectionToolbarProps, {}> {        
    
    getButtonClass(subSection: QuickFinderSubSection) {
        if (quickFinderStore.getCurrentSubSection() == subSection)
            return 'btn btn-default';
        else return 'btn btn-primary';
    }
    
    onSelectSubSection(e) {
        var section = e.target.value as QuickFinderSubSection;
        quickFinderStore.onSetSubSection(section);
    }
    
    render() {
        return <div className="vertical-space">
            <div className="btn-group btn-group">
            {
                this.props.subSections.map(s =>
                    <button className={this.getButtonClass(s)} 
                        onClick={this.onSelectSubSection} value={s} key={s}>
                        {i18next.t(QuickFinderSubSection[s])}
                    </button>
                )
            }
            </div>
        </div>
    }
}