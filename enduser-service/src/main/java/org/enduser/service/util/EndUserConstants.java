package org.enduser.service.util;

import org.springframework.stereotype.Component;

@Component
public class EndUserConstants {

    public static final int OK_200 = 200;
    public static final int Created_201 = 201;
    public static final int Accepted_202 = 202;
    public static final int NoContent_204 = 204;
    public static final int MovedPermanently_301 = 301;
    public static final int BadRequest_400 = 400;
    public static final int Unauthorized_401 = 401;
    public static final int Forbidden_403 = 403;
    public static final int NotFound_404 = 404;
    public static final int MethodNotAllowed_405 = 405;
    public static final int Conflict_409 = 409;
    public static final int InternalServerError_500 = 500;
    public static final int ServiceUnavailable_503 = 503;
    
    public static final int HttpErrorCode_450 = 450;   // Our defined error code for general/generic exceptions

    public static final int HttpErrorCode_551 = 551;    // "Invalid Email Address Format";
    public static final int HttpErrorCode_552 = 552;    //"Saving Tourist Failed: Account Associated with this Email address exists";
    public static final int HttpErrorCode_553 = 553;    //"Updating User failed: User does not exist or multiple records found";
    public static final int HttpErrorCode_554 = 554;    //"Deleting User failed: User does not exist or multiple records found";
    public static final int HttpErrorCode_555 = 555;    //"Sorry, We couldn't find and account with that Email Address";
    public static final int HttpErrorCode_556 = 556;    //"Your account is not activated. please activate your account first";
    public static final int HttpErrorCode_557 = 557;    //"Wrong Password";
    public static final int HttpErrorCode_558 = 558;    //"Deleting OperatorTour Failed: OperatorTour does not exist or multiple records found";
    public static final int HttpErrorCode_559 = 559;    //"Update Group failed: Group does not exist or multiple records found";
    public static final int HttpErrorCode_560 = 560;    //"Deleting Group failed: Group does not exist or multiple records found";
    public static final int HttpErrorCode_561 = 561;    //"The requested Group is not found";
    public static final int HttpErrorCode_562 = 562;    //"Removing user from group failed. You are not in the group";
    public static final int HttpErrorCode_563 = 563;    //"Updating Tour Operator failed: Tour Operator does not exist or multiple records found";    
    public static final int HttpErrorCode_564 = 564;    //"Deleting Tour Operator: Tour Operator does not exist";
    public static final int HttpErrorCode_565 = 565;    //"Update Tour failed: Tour does not exist or multiple records found";
    public static final int HttpErrorCode_566 = 566;    //"Deleting Tour failed: Tour does not exist or multiple records found";
    public static final int HttpErrorCode_567 = 567;    //"Deleting Tour Route failed: Tour Route does not exist";
    public static final int HttpErrorCode_568 = 568;    //"Group Created but we are unable to send you email";
    public static final int HttpErrorCode_569 = 569;    //"Unable to create account. We are unable to send email to the email address you provided";
    public static final int HttpErrorCode_570 = 570;    //"Data not fund";
    public static final int HttpErrorCode_571 = 571;    //"Tour successfully booked but minor error happened while processing. please contact us by email or phone as soon as possible";
    public static final int HttpErrorCode_572 = 572;    //"Error happened! Refund not processed";
    public static final int HttpErrorCode_573 = 573;    //"Danger! suspicious activity detected";    
    public static final int HttpErrorCode_574 = 574;    //"Tour successfully booked but unable to send confirmation email. please contact us by email or phone";
    public static final int HttpErrorCode_575 = 575;    //"Saving Tour Failed: a tour with the given tour id exist";
    public static final int HttpErrorCode_576 = 576;    //"You are already in this group";
    public static final int HttpErrorCode_577 = 577;    //"Tour Operator already exists";
    public static final int HttpErrorCode_578 = 578;    //"There is no account associated with this email address";
    public static final int HttpErrorCode_579 = 579;    //"There is no account associated with this Operator Id";
    public static final int HttpErrorCode_580 = 580;    //"Tour Price is already set for the specified season of this tour";
    public static final int HttpErrorCode_581 = 581;    //"A car with this car Id exists in the system";
    public static final int HttpErrorCode_582 = 582;    //"Update Vehicle record failed: This vehicle does not exist or multiple records found";
    public static final int HttpErrorCode_583 = 583;    //"Deleting Vehicle failed: Vehicle record does not exist";
    public static final int HttpErrorCode_584 = 584;    //"Wrong Token";
    public static final int HttpErrorCode_585 = 585;    //"Password has successfully changed but we are unable to send you an email";
    public static final int HttpErrorCode_586 = 586;    //"Current Password you provided don't match what we have in file";
    public static final int HttpErrorCode_587 = 587;    //"Car successfully saved but we are unable to send you email";
    public static final int HttpErrorCode_588 = 588;    //"Unable to process credit card";
    public static final int HttpErrorCode_589 = 589;    //"Updating Tour Failed: We are unable to find the tour";
    public static final int HttpErrorCode_590 = 590;    //"Tour Operator doesn't exist";
    
}
