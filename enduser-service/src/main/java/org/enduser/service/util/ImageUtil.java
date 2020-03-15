package org.enduser.service.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.enduser.service.exception.EndUserException;
import org.enduser.service.model.util.ImageObject;
import org.springframework.stereotype.Component;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Component
public class ImageUtil {

    private static final Logger logger = Logger.getLogger(ImageUtil.class);

    public void saveImageToS3(ImageObject imageObject) {  //keyName is file name
        logger.info("ImageUtil saveImageToS3 method begin");

        AWSCredentials credientaials =
                new BasicAWSCredentials("AKIAI237EJP5BWHZEHCA", "6MpNclxvdGZBTsb5QfcWblA/YlMYG2Gr4JvpiLlD");

        AmazonS3 s3client = new AmazonS3Client(credientaials);
    //    byte[] data = Base64.decodeBase64(imageObject.getUploaded_uri());
        try {
            File tempFile = File.createTempFile("test", ".jpg");

            File ff = new File("/Volumes/C/test/sol.jpg");
            FileOutputStream fo = new FileOutputStream(ff);

            fo.write(imageObject.getUploaded_uri().getBytes());
            fo.flush();
            fo.close();
            
            FileOutputStream outputStream = new FileOutputStream(tempFile);
            outputStream.write(imageObject.getUploaded_uri().getBytes());
            outputStream.close();
            s3client.putObject(new PutObjectRequest(imageObject.getBucketName(), imageObject.getKeyName(), tempFile));

        } catch (AmazonServiceException ase) {
            logger.error("ImageUtil saveImageToS3 method throw exception Error Message: " + ase.getMessage()
                    + " ase.getStatusCode() " + ase.getStatusCode() + " ase.getErrorCode() " + ase.getErrorCode()
                    + " ase.getErrorType() " + ase.getErrorType() + " ase.getRequestId() " + ase.getRequestId());
            logger.error("ImageUtil saveImageToS3 method throw exception AWS's explanation:- Caught an AmazonServiceException, which "
                    + "means your request made it to Amazon S3, but was rejected with an error response for some reason.");
            throw new EndUserException(ase);
        } catch (AmazonClientException ace) {
            logger.error("ImageUtil saveImageToS3 method throw exception Error Message: " + ace.getMessage());
            logger.info("ImageUtil saveImageToS3 method throw exception AWS's explanation:- Caught an AmazonClientException, which "
                    + "means the client encountered an internal error while trying to communicate with S3, such as not being able to access the network.");
            throw new EndUserException(ace);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            logger.error("ImageUtil saveImageToS3 method throw IO exception Error Message :" + e);
            throw new EndUserException(e);
        }

    }

}
