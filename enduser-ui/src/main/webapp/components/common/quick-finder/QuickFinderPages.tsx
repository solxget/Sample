import * as React from 'react';
import * as ReactDOM from 'react-dom';
import * as i18next from 'i18next';
import * as Pikaday from 'pikaday';
import * as moment from 'moment';

import {quickFinderStore, EventType} from '../../../stores/QuickFinderStore'
import {securityStore} from '../../../stores/SecurityStore'
import {getTimePoint} from '../../../models/Time'
import {localeStore} from '../../../stores/LocaleStore'
import {messageStore} from '../../../stores/MessageStore'

function formatDate(date: string) {
    if (date == null || date.trim() == '') return date;
    else return `${date}T00:00:00`;
}

export interface QFTourState {
    tourTypes: Array<any>
}

export interface QFCarState {
    pickUpAddress: Array<any>
}

export class QuickFinderTours extends React.Component<{}, QFTourState> {
  
    _subscription = null
    onValChanged(val: EventData) {
        if (val.eventType == EventType.COUNTRY_CHANGED) {
            this.forceUpdate(() =>              
                quickFinderStore.getTourTypes(localeStore.getVisitedCountry())
                .then(res => {
                    if (res.ok) {
                        res.json().then((j: Array<any>) => {
                            this.setState({ tourTypes: j });
                        });
                    }
                })             
            );
        }
    }
    getInitState() : QFTourState {
        return { tourTypes: [] }
    }
    constructor() {
        super();
        this.state = this.getInitState();
    }
    
    componentDidMount() {
        this._subscription = quickFinderStore.subscribe(this, this.onValChanged);
        
        quickFinderStore.getTourTypes(localeStore.getVisitedCountry())
            .then(res => {
                if (res.ok) {
                    res.json().then((j: Array<any>) => {
                        this.setState({ tourTypes: j });
                    });
                }
            })
    }
    
    componentWillUnmount() {
        this._subscription.unsubscribe(this, this.onValChanged);
    }
    
    onBlur(e){
        var dateFormat = "YYYY-MM-DD"
        var $from = $('#tripStartDate').val();
        if($from) {
            $('#endDate').empty();
            $('#endDate').html('<input required={true} placeholder="yyyy-mm-dd" class="form-control input-sm pikaday2" name="tripEndDate" id="tripEndDate"></input>');
            $('#tripEndDate').each((index, item) => new Pikaday({ field: $(item)[0], minDate: moment($from, dateFormat).toDate(), maxDate: moment('2018-12-31').toDate()})
        }
    }
    
    isValid(){
        if ($('#toursForm #tripStartDate').attr('required') != null && $('#toursForm #tripStartDate').val() =="") {
            messageStore.onShowError('Required fields are empty');
        //  $('#toursForm #tripStartDate').parentElement.classList.add('error'); //add class error
        //  $('#toursForm #tripStartDate').nextSibling.textContent = this.props.messageRequired; // show error message
            return false;
        }
        else {
        //  $('#toursForm #tripStartDate').classList.remove('error'); 
        //  $('#toursForm #tripStartDate').nextSibling.textContent = ""; 
        }
    }
    
    validateFields(e){
        return this.isValid(e.target)
    }
    
    onSearch(e) {
        if((typeof document.createElement('input').checkValidity == 'function')) {
            if (!$('.panel-quick-finder form')[0]['checkValidity']()) {
                $('.panel-quick-finder input[type="submit"]').click();
                return;
            } 
        }else{  
            var elements = $('#toursForm :input');
            for (var i = 0, element; element = elements[i++];) {
                if (element.hasAttribute('required') && element.value =="") {
                    element.style.borderColor="red";
                    e.preventDefault();
                    return false;
                }else{
                    element.style.borderColor="";
                }
            }
        }
        
        e.preventDefault();
        // making it work for 2018 while on 2017 and data price and groupSeason data in db are for 2017
        var tempSD = $('#tripStartDate').val();
        var tempED = $('#tripEndDate').val();

        var tempStartDate = new Date(tempSD);
        var tempEndDate = new Date(tempED);
        if(tempStartDate.getFullYear() == "2018"){
            tempStartDate.setFullYear("2017");
            tempStartDate = tempStartDate.toISOString().slice(0,10);
            tempEndDate.setFullYear("2017");
            tempEndDate = tempEndDate.toISOString().slice(0,10);
        }
        else if(tempEndDate.getFullYear() == "2018" && tempStartDate.getFullYear() == "2017"){
            tempEndDate.setDate("28");
            tempEndDate.setMonth("11");
            tempEndDate.setFullYear("2017");
            tempEndDate = tempEndDate.toISOString().slice(0,10);
            tempStartDate = tempStartDate.toISOString().slice(0,10);
        }else{
            tempEndDate = tempEndDate.toISOString().slice(0,10);
            tempStartDate = tempStartDate.toISOString().slice(0,10);
        }

        quickFinderStore.findTours({
            allInclusive: $('#allInclusive').prop('checked'),
            tourType: $('#tourType').val(),
            travellers: $('#numberOfTravellers').val(),
            tripStartDate: formatDate(tempStartDate),
            tripEndDate: formatDate(tempEndDate),
            country: localeStore.getVisitedCountry()
        });
    }
        
    render() {        
        return (
            <div className="text-left">
            <form className="row form-horizontal" id="toursForm">
                <div className="col-md-6">
                    <div className="form-group">
                        <label for="tourType" className="col-sm-5 control-label">{i18next.t('TOUR_TYPE')}</label>
                        <div className="col-sm-7">
                            <select name="tourType" required={true} id="tourType" className="form-control input-sm" defaultValue="">
                                <option value="" disabled={true}>-- SELECT TYPE --</option>
                                {
                                    this.state.tourTypes
                                        .sort((a,b) => a.name.localeCompare(b.name))
                                        .map(tt => <option key={tt.name} value={tt.name}>{tt.name}</option>)
                                }
                            </select>
                        </div>
                    </div>
                    <div className="form-group">
                        <label for="tripStartDate" className="col-sm-5 control-label">{i18next.t('TRIP_START_DATE')}</label>
                        <div className="col-sm-7">
                            <input required={true} placeholder="yyyy-mm-dd" className="form-control input-sm pikaday1" name="tripStartDate" id="tripStartDate" onBlur={this.onBlur.bind(this)}></input>
                        </div>
                    </div> 
                </div>
                <div className="col-md-6">
                    <div className="form-group">
                        <label for="numberOfTravellers" className="col-sm-5 control-label">{i18next.t('TRAVELLERS')}</label>
                        <div className="col-sm-7">
                            <select name="numberOfTravellers" id="numberOfTravellers" className="form-control input-sm" required={true}>
                                { 
                                    ([1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,
                                        22,23,24,25,26,27,28,29,30,31,32,33,34,35])
                                        .map(n => <option value={n} key={n}>{n}</option>)
                                }
                                <option value=">35">&gt;35</option>
                            </select>
                        </div>
                    </div>                 
                    <div className="form-group">
                        <label for="tripDuration" className="col-sm-5 control-label">{i18next.t('TRIP_END_DATE')}</label>
                        <div className="col-sm-7" id="endDate">
                            <input required={true} placeholder="yyyy-mm-dd" className="form-control input-sm pikaday2" name="tripEndDate" id="tripEndDate"></input>
                        </div>
                    </div>
                    <div className="form-group">
                        <input hidden={true} type="checkbox" name="allInclusive" id="allInclusive" value="1" checked />    
                    </div>
                </div> 
                 <div className="col-md-6">
                    <div className="form-group">
                        <div className="col-sm-offset-5 col-sm-7">
                            <button onClick={this.onSearch} className="btn btn-info" type="button">{i18next.t('SEARCH')}</button>
                            <input type="submit" id="submit" hidden={true}/>
                        </div>
                    </div>
                 </div>
            </form>
            </div>
        );
    }
}


export class QuickFinderGroups extends React.Component<{}, {}> {   
    
    _subscription = null
    onValChanged(val: EventData) {
    }

    componentDidMount() {
        this._subscription = quickFinderStore.subscribe(this, this.onValChanged);
    }
    
    componentWillUnmount() {
        this._subscription.unsubscribe(this, this.onValChanged);
    }
    
    onSearch(e) {
        if((typeof document.createElement('input').checkValidity == 'function')) {
            if (!$('.panel-quick-finder form')[0]['checkValidity']()) {
                $('.panel-quick-finder input[type="submit"]').click();
                return;
            } 
        }else{  
            var elements = $('#groupForms :input');
            for (var i = 0, element; element = elements[i++];) {
                if (element.hasAttribute('required') && element.value =="") {
                    element.style.borderColor="red";
                    e.preventDefault();
                    return false;
                }else{
                    element.style.borderColor="";
                }
            }
        }
        
        quickFinderStore.findGroups({
            tripDate: formatDate($('#tripDate').val()),
            tripDuration: $('#tripDuration').val(),
            country: localeStore.getVisitedCountry()
        });
    }

    render() {        
        return (
            <div className="text-left">
            <form className="row form-horizontal" id="groupForms">
                <div className="col-md-6">                    
                    <div className="form-group">
                        <label for="tripDate" className="col-sm-4 control-label">{i18next.t('TRIP_DATE')}</label>
                        <div className="col-sm-8">
                            <input required={true} placeholder="yyyy-mm-dd" className="form-control input-sm pikaday1" name="tripDate" id="tripDate"></input>
                        </div>
                    </div>
                </div>
                <div className="col-md-6">
                    <div className="form-group">
                        <label for="tripDuration" className="col-sm-5 control-label">{i18next.t('TRIP_DURATION')}</label>
                        <div className="col-sm-7">
                            <select name="tripDuration" id="tripDuration" className="form-control input-sm">
                                { 
                                    ([1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,
                                        22,23,24,25,26,27,28,29,30])
                                        .map(n => <option value={n}>{n} Day{n<2 ? '':'s'}</option>)
                                }
                                <option value=">30">&gt; 30 Days</option>
                            </select>
                        </div>
                    </div>
                </div>
                <div className="col-md-6">
                    <div className="form-group">
                        <div className="col-sm-offset-4 col-sm-8">
                            <button type="button" onClick={this.onSearch} className="btn btn-info">{i18next.t('SEARCH')}</button>
                            <input type="submit" id="submit" hidden={true}/>
                        </div>
                    </div>
                </div>
            </form>
            </div>
        );
    }
}

export class QuickFinderNewGroup extends React.Component<{}, {}> {
        
    _subscription = null
    onValChanged(val: EventData) {
    }

    componentDidMount() {
        this._subscription = quickFinderStore.subscribe(this, this.onValChanged);
    }
    
    componentWillUnmount() {
        this._subscription.unsubscribe(this, this.onValChanged);
    }

    onBlur(e){
        var dateFormat = "YYYY-MM-DD"
        var $from = $('#tripStartDate').val();
        if($from) {
            $('#endDate').empty();
            $('#endDate').html('<input required={true} placeholder="yyyy-mm-dd" class="form-control input-sm pikaday2" name="tripEndDate" id="tripEndDate"></input>');
            $('#tripEndDate').each((index, item) => new Pikaday({ field: $(item)[0], minDate: moment($from, dateFormat).toDate(), maxDate: moment('2018-12-31').toDate()})
        }
    }
    
    onCreate(e) {
        if((typeof document.createElement('input').checkValidity == 'function')) {
            if (!$('.panel-quick-finder form')[0]['checkValidity']()) {
                $('.panel-quick-finder input[type="submit"]').click();
                return;
            } 
        }else{  
            var elements = $('#newGroupForm :input');
            for (var i = 0, element; element = elements[i++];) {
                if (element.hasAttribute('required') && element.value =="") {
                    element.style.borderColor="red";
                    e.preventDefault();
                    return false;
                }else{
                    element.style.borderColor="";
                }
            }
        }
        
        quickFinderStore.createNewGroup({
            groupName: $('#name').val(),
            plannedTripDate: formatDate($('#tripStartDate').val()),
            tripEndDate: formatDate($('#tripEndDate').val()),
            emailAddress: $('#email').val(),//securityStore.getCurrentUserEmail()
            country: localeStore.getVisitedCountry()
            tripDuration: moment($('#tripEndDate').val()).diff(moment($('#tripStartDate').val()), 'days');
        })
    }

    render() {        
        return (
            <form className="text-left" id="newGroupForm">
                <div className="row form-horizontal">
                    <div className="col-md-6">
                        <div className="form-group">
                            <label for="groupName" className="col-sm-5 control-label">{i18next.t('GROUP_NAME')}</label>
                            <div className="col-sm-7">
                                <input required={true} type="text" placeholder="Name" className="form-control input-sm" name="name" id="name"/>
                            </div>
                        </div>

                        <div className="form-group" title={i18next.t('TRIP_START_DATE')}>
                            <label for="tripStartDate" className="col-sm-5 control-label">{i18next.t('TRIP_START_DATE')}</label>
                            <div className="col-sm-7">
                                <input required={true} placeholder="yyyy-mm-dd" className="form-control input-sm pikaday1" id="tripStartDate" name="tripStartDate" onBlur={this.onBlur.bind(this)}></input>
                            </div>
                        </div>
                    </div>
                    <div className="col-md-6">
                        <div className="form-group" hidden={securityStore.isUserLoggedIn()}>
                            <label for="email" className="col-sm-5 control-label">{i18next.t('EMAIL')}</label>
                            <div className="col-sm-7">
                                <input required={true} placeholder="Email" className="form-control input-sm" id="email" name="email"
                                    defaultValue={securityStore.isUserLoggedIn() ? securityStore.getCurrentUserEmail() : ''}></input>
                            </div>
                        </div>
                        <div className="form-group" title={i18next.t('TRIP_END_DATE')}>
                            <label for="tripEndDate" className="col-sm-5 control-label">{i18next.t('TRIP_END_DATE')}</label>
                            <div className="col-sm-7" id="endDate">
                                <input required={true} placeholder="yyyy-mm-dd" className="form-control input-sm pikaday1" id="tripEndDate" name="tripEndDate"></input>
                            </div>
                        </div>
                    </div>
                </div>
                <div className="vertical-space"></div>
                <div className="row">                
                    <div className="col-md-6">
                        <div className="form-group">
                            <div className="col-sm-offset-5 col-sm-7">
                                <button type="button" onClick={this.onCreate}  className="btn btn-info" >{i18next.t('CREATE_GROUP')}</button>
                                <input type="submit" id="submit" hidden={true}/>
                            </div>
                        </div>
                    </div>
                    <div className="col-md-6"></div>
                </div>
            </form>
        );
    }
}

export class QuickFinderCustomTour extends React.Component<{}, {}> {
        
    _subscription = null
    onValChanged(val: EventData) {
    }
    
    componentDidMount() {
        this._subscription = quickFinderStore.subscribe(this, this.onValChanged);
    }
    
    componentWillUnmount() {
        this._subscription.unsubscribe(this, this.onValChanged);
    }
    
    onBlur(e){
        var dateFormat = "YYYY-MM-DD"
        var $from = $('#tripDate').val();
        if($from) {
            $('#endDate').empty();
            $('#endDate').html('<div className="col-sm-8"> <input required={true} placeholder="yyyy-mm-dd" class="form-control input-sm pikaday2" name="tripEndDate" id="tripEndDate"></input> </div>');
            $('#tripEndDate').each((index, item) => new Pikaday({ field: $(item)[0], minDate: moment($from, dateFormat).toDate(), maxDate: moment('2018-12-31').toDate()})
        }
    }
    
    onCreate(e) {
        if((typeof document.createElement('input').checkValidity == 'function')) {
            if (!$('.panel-quick-finder form')[0]['checkValidity']()) {
                $('.panel-quick-finder input[type="submit"]').click();
                return;
            } 
        }else{  
            var elements = $('#customTourForm :input');
            for (var i = 0, element; element = elements[i++];) {
                if (element.hasAttribute('required') && element.value =="") {
                    element.style.borderColor="red";
                    e.preventDefault();
                    return false;
                }else{
                    element.style.borderColor="";
                }
            }
        }
        
        quickFinderStore.createCustomTour({
            tourStartDate: formatDate($('#tripDate').val()),
            tourEndDate: formatDate($('#tripEndDate').val()),
            tourDescription: $('#description').val(),
            emailAddress: $('#email').val(),
            travellersCount: parseInt($('#travellers').val()),
            visiting: localeStore.getVisitedCountry()
        });
    }

    render() {        
        return (
            <form className="text-left" id="customTourForm">
                <div className="row form-horizontal">
                    <div className="col-md-6">
                        <div className="form-group">
                            <label for="tripDate" className="col-sm-4 control-label">{i18next.t('TRIP_START_DATE')}</label>
                            <div className="col-sm-8">
                                <input required={true} placeholder="yyyy-mm-dd" className="form-control input-sm pikaday1" name="tripDate" id="tripDate" onBlur={this.onBlur.bind(this)}></input>
                            </div>
                        </div>
                    </div>
                    <div className="col-md-6">
                        <div className="form-group">
                            <label for="tripEndDate" className="col-sm-4 control-label">{i18next.t('TRIP_END_DATE')}</label>
                            <div className="col-sm-8"  id="endDate">
                                <input required={true} placeholder="yyyy-mm-dd" className="form-control input-sm pikaday2" name="tripEndDate" id="tripEndDate"></input>
                            </div>
                        </div>
                    </div>
                    <div className="col-md-6">
                        <div className="form-group">
                            <label for="email" className="col-sm-4 control-label">{i18next.t('EMAIL')}</label>
                            <div className="col-sm-8">
                                <input id="email" name="email" placeholder="Email" className="form-control input-sm" required={true}></input>
                            </div>
                        </div>
                    </div>
                    <div className="col-md-6">
                        <div className="form-group">
                            <label for="travellers" className="col-sm-5 control-label">{i18next.t('TRAVELLERS')}</label>
                            <div className="col-sm-7">
                                <select required={true} name="travellers" id="travellers" className="form-control input-sm">
                                    { 
                                        ([1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,
                                            22,23,24,25,26,27,28,29,30,31,32,33,34,35])
                                            .map(n => <option value={n}>{n}</option>)
                                    }
                                </select>
                            </div>
                        </div>
                    </div>
                    <div className="col-md-12">
                        <div className="form-group">
                            <label for="description" className="col-sm-2 control-label">{i18next.t('DESCRIPTION')}</label>
                            <div className="col-sm-10">
                                <textarea id="description" name="description" placeholder="Please let us know your interest, time frame and any additional information here.  We will get back to you with a tailored itinerary that fits your schedule and budget" rows="7" className="form-control input-sm" required={true}></textarea>
                            </div>
                        </div>
                    </div>
                </div>
                <div className="row">                
                    <div className="col-md-6">
                        <div className="form-group">
                            <div className="col-sm-offset-4 col-sm-8">
                                <button type="button" onClick={this.onCreate} className="btn btn-info" disabled = {false}>{i18next.t('SUBMIT')}</button>
                                <input type="submit" id="submit" hidden={true}/>
                            </div>
                        </div>
                    </div>
                    <div className="col-md-6"></div>
                </div>
            </form>
        );
    }
}

export class QuickFinderCars extends React.Component<{}, QFCarState> {
    
    _subscription = null
    onValChanged(val: EventData) {
        if (val.eventType == EventType.COUNTRY_CHANGED) {
            this.forceUpdate(() =>              
                quickFinderStore.getPickupAddress(localeStore.getVisitedCountry())
                .then(res => {
                    if (res.ok) {
                        res.json().then((j: Array<any>) => {
                            this.setState({ pickUpAddress: j });
                        });
                    }
                })            
            );
        }
    }
    

    componentDidMount() {
        this._subscription = quickFinderStore.subscribe(this, this.onValChanged);
        
        quickFinderStore.getPickupAddress(localeStore.getVisitedCountry())
        .then(res => {
            if (res.ok) {
                res.json().then((j: Array<any>) => {
                    this.setState({ pickUpAddress: j });
                });
            }
        })
    }
    
    componentWillUnmount() {
        this._subscription.unsubscribe(this, this.onValChanged);
    }
    
    getInitState() : QFCarState {
        return { pickUpAddress: [] }
    }
    
    getInitState() : QFCarState {
        return { pickUpAddress: [] }
    }
    
    constructor() {
        super();
        this.state = this.getInitState();
    }
    
    
    onBlur(e){
        var dateFormat = "YYYY-MM-DD"
        var $from = $('#pickUpDate').val();
        if($from) {
            $('#endDate').empty();
            $('#endDate').html('<label for="dropOffDate" class="control-label"> Drop off Date </label> <input required={true} placeholder="yyyy-mm-dd" class="form-control input-sm pikaday2" name="dropOffDate" id="dropOffDate"></input> ');
            $('#dropOffDate').each((index, item) => new Pikaday({ field: $(item)[0], minDate: moment($from, dateFormat).toDate(), maxDate: moment('2018-12-31').toDate() })
        }
    }
    
    onFind(e) {
        if((typeof document.createElement('input').checkValidity == 'function')) {
            if (!$('.panel-quick-finder form')[0]['checkValidity']()) {
                $('.panel-quick-finder input[type="submit"]').click();
                return;
            } 
        }else{  
            var elements = $('#carsForm :input');
            for (var i = 0, element; element = elements[i++];) {
                if (element.hasAttribute('required') && element.value == "") {
                    element.style.borderColor="red";
                    e.preventDefault();
                    return false;
                }else{
                    element.style.borderColor="";
                }
            }
        }
        
        quickFinderStore.findCars({
            pickUpDate: formatDate(formatDate($('#pickUpDate').val())),
            pickUpAddress: $('#pickingUp').val(),
            returnAddress: $('#droppingOff').val(),
            returnDate: formatDate($('#dropOffDate').val()),
            pickUpTime: formatDate($('#pickUpTime').val()),
            dropOffTime: formatDate($('#dropOffTime').val()),
            country: localeStore.getVisitedCountry()
        });
    }

    render() {        
        return (
            <form className="text-left" id="carsForm">  
                <div className="row form-horizontal">                
                    <div className="col-md-6">
                        <div className="form-group">
                            <div className="col-sm-12">
                                <label for="pickingUp" className="control-label">{i18next.t('PICKING_UP')}</label>
                                <select name="pickingUp" required={true} id="pickingUp" className="form-control input-sm" defaultValue="">
                                    <option value="" disabled={true}>-- SELECT CITY --</option>
                                    {
                                        this.state.pickUpAddress
                                            .sort((a,b) => a.name.localeCompare(b.name))
                                            .map(tt => <option key={tt.name} value={tt.name}>{tt.name}</option>)
                                    }
                                </select>                            
                            </div>
                        </div>
                    </div>
                    <div className="col-md-6">
                        <div className="form-group">                           
                            <div className="col-sm-12">
                                <label for="droppingOff" className="control-label">{i18next.t('DROPPING_OFF')}</label>
                                <input id="droppingOff" name="droppingOff" placeholder={i18next.t('SAME_AS_PICKUP')} className="form-control" readOnly={true} />
                            </div>
                        </div>
                    </div>
                </div>           
                <div className="row form-horizontal">
                    <div className="col-md-6">
                        <div className="form-group">                            
                            <div className="col-sm-7">
                                <label for="pickUpDate" className="control-label">{i18next.t('PICKUP_DATE')}</label>
                                <input required={true} placeholder="yyyy-mm-dd" className="form-control input-sm pikaday1" name="pickUpDate" id="pickUpDate" onBlur={this.onBlur.bind(this)}></input>
                            </div>
                            <div className="col-sm-5">
                                <label className="control-label visually-hidden" >{i18next.t('PICKUP_TIME')}</label>
                                <div className="form-group">
                                    <select required name="pickUpTime" id="pickUpTime" class="form-control input-sm">
                                    {
                                        getTimePoint().map(c => <option key={c} value={c}>{c}</option>)
                                    }
                                    </select>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div className="col-md-6">
                        <div className="form-group">                            
                            <div className="col-sm-7" id="endDate">
                                <label for="dropOffDate" className="control-label">{i18next.t('DROPOFF_DATE')}</label>
                                <input required={true} placeholder="yyyy-mm-dd" className="form-control input-sm pikaday2" name="dropOffDate" id="dropOffDate"></input>
                            </div>
                            <div className="col-sm-5">
                                <label className="control-label visually-hidden" >{i18next.t('PICKUP_TIME')}</label>
                                <div className="form-group">
                                    <select required name="dropOffTime" id="dropOffTime" class="form-control input-sm">
                                    {
                                        getTimePoint().map(c => <option key={c} value={c}>{c}</option>)
                                    }
                                    </select>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div className="row">                
                    <div className="col-md-6">
                        <div className="form-group">
                            <div className="col-sm-offset-1 col-sm-7">
                                <button type="button" onClick={this.onFind} className="btn btn-info">{i18next.t('SEARCH')}</button>
                                <input type="submit" id="submit" value={i18next.t('SEARCH')} hidden={true}/>
                            </div>
                        </div>
                    </div>
                    <div className="col-md-6"></div>
                </div>
            </form>
        );
    }
}