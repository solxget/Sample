import * as React from 'react';
import * as i18next from 'i18next';

import {Message, MessageType} from '../../models/Messaging'
import {messageStore} from '../../stores/MessageStore'

    
export enum Icons {		
    PLUS, MINUS, MUSIC, VIDEO, HEART, STAR, PERSON, RIGHT, X, ZOOM_IN, ZOOM_OUT, COG, TRASH, HOME, FILE, FILES,
    FOLDER, FOLDERS, FOLDER_OPEN, LOCK, INFO, QUESTION, BLOCK, BELL, WRENCH, WARNING, SEARCH,		
}
    
var faIcons = {
    [Icons.PLUS]: 'plus', [Icons.WARNING]: 'exclamation', [Icons.RIGHT]: 'check', [Icons.X]: 'times', 
    [Icons.QUESTION]: 'question', [Icons.INFO]: 'info'
}

export enum IconBackground {
    NONE, SOLID_CIRCLE, HOLLOW_CIRCLE, SOLID_TRIANGLE, HOLLOW_TRIANGLE
}

export interface MessageBannerState {
    message: Message
}

export interface IconProps {
    customAttributes?: any
    classes?: string
    scale?: number
    color?: string
    background?: IconBackground
    img: Icons
}

export class Icon extends React.Component<IconProps, {}> {
    render() {
        if (this.props.img == null) {
            console.error('You need to provide img property for the Icon component.')
            return null
        }
        var iconKey = faIcons[this.props.img]
        if (iconKey == null) console.warn('The given icon is not supported by font-awesome.')
        if (this.props.background == IconBackground.HOLLOW_CIRCLE)
            iconKey += '-sign'
        else if (this.props.background == IconBackground.SOLID_CIRCLE)
            iconKey += '-circle'
        var className = `fa fa-${iconKey} ${this.props.classes ? this.props.classes : ''} ${this.props.scale > 1 ? `fa-${this.props.scale}x` : ''}`
        var style = this.props.color == null ? {} : { color: this.props.color }
        return (<i className={className} style={style}></i>)
    }
}
    
export function getDefaultTitle(messageType: MessageType): string {
    return { 
        [MessageType.INFO]: i18next.t('INFORMATION'), 
        [MessageType.WARNING]: i18next.t('WARNING'), 
        [MessageType.ERROR]: i18next.t('FAILED'), 
        [MessageType.SUCCESS]: i18next.t('SUCCESS') 
    }[messageType]
}
export function getStdIcon(messageType: MessageType): number {
    return { [MessageType.INFO]: Icons.INFO, [MessageType.WARNING]: Icons.WARNING, 
        [MessageType.ERROR]: Icons.X, [MessageType.SUCCESS]: Icons.RIGHT }[messageType]
}

export class MessageBanner extends React.Component<{}, MessageBannerState> {
    
    constructor() {
        super();
        this.state = { message: null };
    }
    _subscription = null
    onValChanged(val) {
        var msg = messageStore.getCurrentMessage();
        if (msg == null) return;
        $('.alert').hide();
        this.setState({ message: messageStore.getCurrentMessage() });
        $('.alert').slideDown('fast');
    }
    componentDidMount() {
        this._subscription = messageStore.subscribe(this, this.onValChanged);
        $('.alert').hide();
    }
    componentWillUnmount() {
        this._subscription.unsubscribe(this, this.onValChanged)
    }
    
    render() {
        
        if (this.state.message == null) return <div className="alert"></div>
        var type = this.state.message.type != null ? this.state.message.type : MessageType.INFO
        var title = this.state.message.title ? this.state.message.title : getDefaultTitle(type)
        var message = this.state.message.content ? this.state.message.content : 'This is an information banner.'
        var iconImg = getStdIcon(type)
        var alertClass = 'info';
        if (type == MessageType.ERROR) alertClass = 'danger';
        else if (type == MessageType.SUCCESS) alertClass = 'success';
        else if (type == MessageType.WARNING) alertClass = 'warn';
         
        return (
            <div className={`alert alert-${alertClass}`}>
                <button type="button" className="close" data-dismiss="alert" hidden aria-label="close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <span style={{fontSize: '2em'}}>
                    <span id="banner-icon"><Icon img={iconImg} background={IconBackground.SOLID_CIRCLE}/></span> <span id="banner-title">{title}</span>
                </span> <span id="banner-message" style={{marginLeft: 5}}>{message}</span>
            </div>
        );
    }
}