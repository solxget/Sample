import * as React from 'react';
import * as i18next from 'i18next';
import * as Pikaday from 'pikaday';
import * as moment from 'moment';

import {Comm} from '../../stores/CommunicationStore'
import {messageStore} from '../../stores/MessageStore'
import {applicationStore} from '../../stores/ApplicationStore'
import {securityStore} from '../../stores/SecurityStore'
import {getAllCountries} from '../../models/Country'
import {getErrorMsg} from '../../models/ErrorCodes'
import {quickFinderStore} from '../../stores/QuickFinderStore'


export class SeasonReg extends React.Component<{}, {}> {
        
    componentDidMount() {
    	$('.pikaday').each((index, item) => new Pikaday({ field: $(item)[0], minDate: moment('2018-01-01').toDate(), maxDate: moment('2018-12-31').toDate() }));
        Comm.get({url: 'tour/getgroupseason?operatorId=' + securityStore.getCurrentUserEmail(), reqMethod: 'get'})
            .then(res => {
                if (res.ok) {        
                    res.json().then(j => {       
                        $('#season1StartDate').val((j.season1StartDate).split("T")[0]);
                        $('#season2StartDate').val((j.season2StartDate).split("T")[0]);
                        $('#season3StartDate').val((j.season3StartDate).split("T")[0]);
                        $('#season4StartDate').val((j.season4StartDate).split("T")[0]);
                        $('#season5StartDate').val((j.season5StartDate).split("T")[0]); 
                        $('#season5EndDate').val((j.season5EndDate).split("T")[0]);
                        $('#group1MinSize').val(j.g1MinSize);
                        $('#group2MinSize').val(j.g2MinSize);
                        $('#group3MinSize').val(j.g3MinSize);
                        $('#group4MinSize').val(j.g4MinSize);
                        $('#group5MinSize').val(j.g5MinSize);
                        $('#group5MaxSize').val(j.g5MaxSize);                        
                    });
                }
            })
            .catch(err => messageStore.onShowError('Sorry we couldn\'t load Group Season data. Something went wrong.'));
    }
    
	onBlur2(e){
		var dateFormat = "YYYY-MM-DD"
		var $start = $('#season2StartDate').val();
		if($start) {
			$('#season2EndDate').empty();
			$('#season2EndDate').append('<input required={true} placeholder="mm/dd/yyyy" class="form-control input-sm pikaday " id="season3StartDate" name="season3StartDate" ></input> ');
			$('#season3StartDate').each((index, item) => new Pikaday({ field: $(item)[0], minDate: moment($start, dateFormat).toDate(), maxDate: moment('2018-12-31').toDate() })
		}
	}

	onBlur4(e){
		var dateFormat = "YYYY-MM-DD"
		var $start = $('#season4StartDate').val();
		if($start) {
			$('#season4EndDate').empty();
			$('#season4EndDate').append('<input required={true} placeholder="mm/dd/yyyy" class="form-control input-sm pikaday " id="season5StartDate" name="season5StartDate" ></input> ');
			$('#season5StartDate').each((index, item) => new Pikaday({ field: $(item)[0], minDate: moment($start, dateFormat).toDate(), maxDate: moment('2018-12-31').toDate() })
		}
	}
	
    saveGSeason(registrationData){
    	applicationStore.onSetBusyStatus(true);
        Comm.get({ url: '/tour/savegroupseason', body: registrationData, reqMethod: 'post' })
        .then(res => {
            applicationStore.onSetBusyStatus(false);
            if (res.ok) {                    
                securityStore.onLogout();
                messageStore.onShowSuccess('Group Season form saved succesfully. Please log back in');
            }
            else if (res.status > 550 && res.status < 600) {
                messageStore.onShowError(getErrorMsg(res.status));
            }
            else {
                messageStore.onShowError('Registration failed.');
            }
        })
        .catch(err => {
            applicationStore.onSetBusyStatus(false);
            messageStore.onShowError('Registration Failed: ' + err);
        });
    }
    
    saveGroupSeason(registrationData){
    	
    	if(securityStore.getCurrentUserProfile().groupSeasonFlag){
    		 if (!confirm('Updating this will affect Tour Packages Price. Are you sure you want to do this operation?')){
    			 applicationStore.onSetBusyStatus(false);
    			 return;
    		 }
		        this.saveGSeason(registrationData);    		 
    	} else{
    		this.saveGSeason(registrationData);
    	}
    }
    
    onRegister(e) {
    	if((typeof document.createElement('input').checkValidity == 'function')) {
            if (!$('#form-registration')[0]['checkValidity']()) {
                $('#submit-registration').click();
                return;
            }   
	   	}else{  
	   		var elements = $('#form-registration :input');
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
    	
        e.preventDefault();
        var registrationData = {
        	 operatorId : securityStore.getCurrentUserEmail(),
			 season1StartDate : `${$('#season1StartDate').val()}T00:00:00`,
			 season2StartDate : `${$('#season2StartDate').val()}T00:00:00`,
			 season3StartDate : `${$('#season3StartDate').val()}T00:00:00`,
			 season4StartDate : `${$('#season4StartDate').val()}T00:00:00`,
			 season5StartDate : `${$('#season5StartDate').val()}T00:00:00`,
			 season5EndDate : `${$('#season5EndDate').val()}T00:00:00`,
			 g1MinSize : $('#group1MinSize').val(),
			 g2MinSize : $('#group2MinSize').val(),
			 g3MinSize : $('#group3MinSize').val(),
			 g4MinSize : $('#group4MinSize').val(),
			 g5MinSize : $('#group5MinSize').val(),  
			 g5MaxSize : $('#group5MaxSize').val(),  
        };
        
        this.saveGroupSeason(registrationData);
    }
    
    render() {
    	var seasonFlag = securityStore.getCurrentUserProfile().groupSeasonFlag
    	
        return (
            <div className="panel panel-default">
                <div className="panel-heading">Group/Season Registration</div>
                <div className="panel-body">                                
	                <form method="post" className="form-horizontal" id="form-registration">
		                <div className="row">
		                    <div className="col-md-6">							
								<div className="form-group">
									<label for="birthday" className="control-label col-sm-5">{'Season 1 Start Date'}</label>
									<div className="col-sm-7">
										<input required={true} readOnly={true} placeholder="mm/dd/yyyy" className="form-control input-sm"  id="season1StartDate" name="season1StartDate" value="2017-01-01"></input>
									</div>
								</div>
								<div className="form-group">
									<label for="birthday" className="control-label col-sm-5">{'Season 2 Start Date'}</label>
									<div className="col-sm-7">
										<input required={true} placeholder="mm/dd/yyyy" className={"form-control input-sm pikaday " + (seasonFlag? 'grey-background' : '')} id="season2StartDate" name="season2StartDate" onBlur={this.onBlur2.bind(this)} ></input>
									</div>
								</div>
								<div className="form-group">
									<label for="birthday" className="control-label col-sm-5">{'Season 3 Start Date'}</label>
									<div className="col-sm-7" id="season2EndDate">
										<input required={true} placeholder="mm/dd/yyyy" className={"form-control input-sm pikaday " + (seasonFlag? 'grey-background' : '')} id="season3StartDate" name="season3StartDate"></input>
									</div>
								</div>
								<div className="form-group">
									<label for="birthday" className="control-label col-sm-5">{'Season 4 Start Date'}</label>
									<div className="col-sm-7">
										<input required={true} placeholder="mm/dd/yyyy" className={"form-control input-sm pikaday " + (seasonFlag? 'grey-background' : '')} id="season4StartDate" name="season4StartDate" onBlur={this.onBlur4.bind(this)} ></input>
									</div>
								</div>
								<div className="form-group">
									<label for="birthday" className="control-label col-sm-5">{'Season 5 Start Date'}</label>
									<div className="col-sm-7" id="season4EndDate">
										<input required={true} placeholder="mm/dd/yyyy" className={"form-control input-sm pikaday " + (seasonFlag? 'grey-background' : '')} id="season5StartDate" name="season5StartDate" ></input>
									</div>
								</div>
								<div className="form-group">
									<label for="birthday" className="control-label col-sm-5">{'Season 5 End Date'}</label>
									<div className="col-sm-7">
										<input required={true} readOnly={true} placeholder="mm/dd/yyyy" className="form-control input-sm" id="season5EndDate" name="season5EndDate" value="2018-12-31"></input>
									</div>
								</div>
								
		                    </div>
		                    <div className="col-md-6">
		                        <div className="form-group">
		                            <label for="city" className="control-label col-sm-6">{'Group 1 Minimum size'}</label>
		                            <div className="col-sm-6">
		                                <input required  type="text" name="group1MinSize" id="group1MinSize" className="form-control"></input>
		                            </div>
		                        </div>
								<div className="form-group">
		                            <label for="city" className="control-label col-sm-6">{'Group 2 Minimum size'}</label>
		                            <div className="col-sm-6">
		                                <input required type="text" name="group2MinSize" id="group2MinSize" className={"form-control " + (seasonFlag? 'grey-background' : '')}></input>
		                            </div>
		                        </div>
								<div className="form-group">
		                            <label for="city" className="control-label col-sm-6">{'Group 3 Minimum size'}</label>
		                            <div className="col-sm-6">
		                                <input required type="text" name="group3MinSize" id="group3MinSize" className={"form-control " + (seasonFlag? 'grey-background' : '')}></input>
		                            </div>
		                        </div>
								<div className="form-group">
		                            <label for="city" className="control-label col-sm-6">{'Group 4 Minimum size'}</label>
		                            <div className="col-sm-6">
		                                <input required type="text" name="group4MinSize" id="group4MinSize" className={"form-control " + (seasonFlag? 'grey-background' : '')}></input>
		                            </div>
		                        </div>
								<div className="form-group">
		                            <label for="city" className="control-label col-sm-6">{'Group 5 Minimum size'}</label>
		                            <div className="col-sm-6">
		                                <input required type="text" name="group5MinSize" id="group5MinSize" className={"form-control " + (seasonFlag? 'grey-background' : '')}></input>
		                            </div>
		                        </div>  
		                        <div className="form-group">
		                            <label for="city" className="control-label col-sm-6">{'Group 5 Maximum size'}</label>
		                            <div className="col-sm-6">
		                                <input type="text" name="group5MaxSize" id="group5MaxSize" className={"form-control " + (seasonFlag? 'grey-background' : '')}></input>
		                            </div>
		                        </div>  
		                    </div>
		                </div>                        
		                <div className="pull-right">
		                    <button onClick={this.onRegister.bind(this)} type="submit" className="btn btn-success"><i className="fa fa-sign-in"></i>{seasonFlag? ' Modify' : ' Submit'}</button>
		                    <input type="submit" hidden={true} id="submit-registration" />
		                </div>
		            </form>

                </div>
                <div className="panel-footer">
                    
                </div>
            </div>                     
        );
    }
}

