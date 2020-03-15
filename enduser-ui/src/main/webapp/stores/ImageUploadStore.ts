import NanoFlux = require('../bundle/nanoflux')

import * as i18next from 'i18next';
import {dispatcher} from '../stores/Dispatcher'
import {messageStore} from './MessageStore'
import {applicationStore} from './ApplicationStore'


export class ImageUrlProps {
    url: string[],
    info: Array<any>

    constructor(url: string[], info: Array<any>) {
        this.url = url;
        this.into = info;
    }   
}

export interface SliderProps{
    sldier?: any
}

export class ImageUploadStoreClass {
    private _imageUrls: ImageUrlProps
    private sliderInit: SliderProps
    private sliderInitH: SliderProps

    clearImageUrls = function(){
        this._imageUrls = null;
    }

    onImgUrlLoad = function(urls: ImageUrlProps) {
        this._imageUrls = urls;
        this.notify();
    }

    getImageUrls = function(): ImageUrlProps { return this._imageUrls; }

    saveSlider = function(sliders: SliderProps){
        this.sliderInit = sliders;
    }
    
    getSlider = function(): SliderProps { return this.sliderInit;}

    saveSliderH = function(sliders: SliderProps){
        this.sliderInitH = sliders;
    }
    
    getSliderH = function(): SliderProps { return this.sliderInitH;}

    getBucketName = function() : string{
        return 'touranbemailbucket';
    }
    getBucketRegion = function() : string{
        return 'us-east-1';
    }
    getIdentityPoolId = function() : string{
        return 'us-east-1:c19e8747-bfb8-4c25-ab93-fa74ee1cf47f';
    }

    FolderUtilFunction = function (country: string, company: string, tourId: string, tourFlag: boolean){
        var albumKey = null;

        AWS.config.update({
            region: this.getBucketRegion(),
            credentials: new AWS.CognitoIdentityCredentials({
                IdentityPoolId: this.getIdentityPoolId()
            })
        });
        var s3 = new AWS.S3({
            apiVersion: '2006-03-01',
            params: {Bucket: this.getBucketName()}
        });

        var albumName = company;
        albumName = albumName.trim();
        if (!albumName) {
           // return alert('Album names must contain at least one non-space character.');
        }
        if (albumName.indexOf('/') !== -1) {
          //  return alert('Album names cannot contain slashes.');
        }
        if(tourFlag){
            albumKey = encodeURIComponent(country) + '/' + albumName + '/' + tourId;
        }
        else{
            albumKey = encodeURIComponent(country) + '/' + albumName;
        }
        s3.headObject({Key: albumKey}, function(err, data) {
            if (!err) {
              //  return alert('Album already exists.');
            }
            if (err.code !== 'NotFound') {
              //  return alert('There was an error creating your album: ' + err.message);
            }
            s3.putObject({Key: albumKey}, function(err, data) {
                if (err) {
                  //  return alert('There was an error creating your album: ' + err.message);
                }
            //    alert('Successfully created album.');
            });
        });
    }

    DocumentUploadUtil = function(country: string, company: string, fileId: string){
        applicationStore.onSetBusyStatus(true);
        AWS.config.update({
            region: this.getBucketRegion(),
            credentials: new AWS.CognitoIdentityCredentials({
                IdentityPoolId: this.getIdentityPoolId();
            })
        });

        var s3 = new AWS.S3({
            apiVersion: '2006-03-01',
            params: {Bucket: this.getBucketName()}
        });

        var files = document.getElementById(fileId).files; 
        if (!files.length) {
          //  return alert('Please choose a file to upload first.');
        }
        var file = files[0];
        var fileName = file.name;
        var albumPhotosKey = encodeURIComponent(country) + '/' + company + '/' + fileId + '/';

        var photoKey = albumPhotosKey + fileName;
        s3.upload({
            Key: photoKey,
            Body: file
        }, function(err, data) {
            applicationStore.onSetBusyStatus(false);
            if (err) {
                messageStore.onShowError('File upload failed. Please try again');
            }else{
                messageStore.onShowSuccess('File successfully uploaded');
            }
        });
    }

    CreateCompanyFolder = function(country : string, company : string){
        this.FolderUtilFunction(country, company, null, false);
    }
    
    CreateTourFolder = function(country : string, company : string, tourId: string){
        this.FolderUtilFunction(country, company, tourId, true);
    }

    AddPhoto = function(country: string, company: string, tourId: string){
        applicationStore.onSetBusyStatus(true);
        AWS.config.update({
            region: this.getBucketRegion(),
            credentials: new AWS.CognitoIdentityCredentials({
                IdentityPoolId: this.getIdentityPoolId();
            })
        });

        var s3 = new AWS.S3({
            apiVersion: '2006-03-01',
            params: {Bucket: this.getBucketName()}
        });

        var files = document.getElementById(tourId).files; 
        if (!files.length) {
          //  return alert('Please choose a file to upload first.');
        }
        var file = files[0];
        var fileName = file.name;
        var albumPhotosKey = encodeURIComponent(country) + '/' + company + '/' + tourId + '/';

        
        var photoKey = albumPhotosKey + fileName;
        s3.upload({
            Key: photoKey,
            Body: file
        }, function(err, data) {
            applicationStore.onSetBusyStatus(false);
            if (err) {
                messageStore.onShowError('Picture upload failed. Please try again');
            }else{
                messageStore.onShowSuccess('Picture successfully uploaded');
            }
        });
    }

    UploadIdentification = function(country: string, company: string, fileId: string){
        this.DocumentUploadUtil(country, company, fileId);
    }

    UploadLegalDoc = function(country: string, company: string, fileId: string){
        this.DocumentUploadUtil(country, company, fileId);
    }
    
    UploadLicense = function(country: string, company: string, fileId: string){
        this.DocumentUploadUtil(country, company, fileId);
    }

    DeleteTourFolder = function (country: string, company: string, tourId: string, pictureId: string) {
        var self = this;
        AWS.config.update({
            region: this.getBucketRegion(),
            credentials: new AWS.CognitoIdentityCredentials({
                IdentityPoolId: this.getIdentityPoolId();
            })
        });

        var s3 = new AWS.S3({
            apiVersion: '2006-03-01',
            params: {Bucket: this.getBucketName()}
        });

        var albumKey = (pictureId == null) ? encodeURIComponent(country) + '/' + company + '/' + tourId + '/' 
            : encodeURIComponent(country) + '/' + company + '/' + tourId + '/' + pictureId 
        
        s3.listObjects({Prefix: albumKey}, function(err, data) {
            if (err) {
             //   return alert('There was an error deleting your album: ', err.message);
            }
            var objects = data.Contents.map(function(object) {
                return {Key: object.Key};
            });

            s3.deleteObjects({
                Delete: {Objects: objects, Quiet: true}
            }, function(err, data) {
                if (err) {
                 //   return alert('There was an error deleting your album: ', err.message);
                }
             //   alert('Successfully deleted album.');
                else if(pictureId != null) {
                    self.ViewPhotos(country, company, tourId);
                }

            });
        });
    }

    ViewPhotos = function (country: string, company: string, tourId: string) : string[] {
        var self = this;
        self.clearImageUrls();

        AWS.config.update({
            region: this.getBucketRegion(),
            credentials: new AWS.CognitoIdentityCredentials({
                IdentityPoolId: this.getIdentityPoolId();
            })
        });

        var s3 = new AWS.S3({
            apiVersion: '2006-03-01',
            params: {Bucket: 'touranbemailbucket'}
        });

        var urls = [];
        var albumPhotosKey = encodeURIComponent(country) + '/' + company + '/' + tourId + '/';
        s3.listObjects({Prefix: albumPhotosKey}, function(err, data) {
            if (err) {
            //  return alert('There was an error viewing your album: ' + err.message);
            }
            // `this` references the AWS.Response instance that represents the response
            var href = this.request.httpRequest.endpoint.href;
            var bucketUrl = href + 'touranbemailbucket' + '/';

            data.Contents.map(function(photo) {
                var photoKey = photo.Key;
                var photoUrl = bucketUrl + encodeURIComponent(photoKey);
                urls.push(photoUrl);

            });
        //    self.onImgUrlLoad(urls)
            self.onImgUrlLoad({
                url: urls,
                info: [
                    { country: country, company: company, tourId: tourId }
                ]
            })  
        });
    }


    notify(args?: any) {}
    subscribe(subscriber?: any, callback?: any) {}
    
}

    
NanoFlux.createStore('imageUploadStore', new ImageUploadStoreClass())
export var imageUploadStore : ImageUploadStoreClass = NanoFlux.getStore('imageUploadStore');

dispatcher.connectTo(imageUploadStore);