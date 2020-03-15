export function getErrorMsg(code: number) {
    return {
        401:    "Sorry, You don't have permission to access this page. Please re-login",
        403:    "Sorry, You don't have permission to access this page",
        503:    "Sorry, we're having trouble connecting to our servers. Try again later",
        
        450:    "Sorry, Unexpected error happend",

        551:    "Invalid Email Address Format",
        552:    "Saving Tourist Failed: Account Associated with this Email address exists",
        553:    "Updating User failed: User does not exist or multiple records found",
        554:    "Deleting User failed: User does not exist or multiple records found",
        555:    "Sorry, We couldn't find an account with that Email Address",
        556:    "Your account is not activated. please activate your account first",
        557:    "Wrong Password",
        558:    "Deleting OperatorTour Failed: OperatorTour does not exist or multiple records found",
        559:    "Update Group failed: Group does not exist or multiple records found",
        560:    "Deleting Group failed: Group does not exist or multiple records found",
        561:    "The requested Group is not found",
        562:    "Removing user from group failed. You are not in the group",
        563:    "Updating Tour Operator failed: Tour Operator does not exist or multiple records found",    
        564:    "Deleting Tour Operator: Tour Operator does not exist",
        565:    "Update Tour failed: Tour does not exist or multiple records found",
        566:    "Deleting Tour failed: Tour does not exist or multiple records found",
        567:    "Deleting Tour Route failed: Tour Route does not exist",
        568:    "Group Created but we are unable to send you email",
        569:    "Unable to create account. We are unable to send email to the email address you provided",
        570:    "Data not fund",
        571:    "Tour successfully booked but minor error happened while processing. please contact us by email or phone as soon as possible",
        572:    "Error happened! Refund not processed",
        573:    "Danger! suspicious activity detected",    
        574:    "Tour successfully booked but unable to send confirmation email. please contact us by email or phone",
        575:    "Saving Tour Failed: a tour with the given tour id exist",
        576:    "You are already in this group",
        577:    "There is an account associated with this email address.",
        578:    "There is no account associated with this email address",
        579:    "There is no account associated with this user name",
        580:    "Tour Price is already set for the specified season of this tour",
        581:    "A car with this car Id exists in the system",
        582:    "Update Vehicle record failed: This vehicle does not exist or multiple records found",
        583:    "Deleting Vehicle failed: Vehicle record does not exist",
        584:    "Wrong Token",
        585:    "Password has successfully changed but we are unable to send you an email",
        586:    "Current Password you provided don't match what we have in file",
        587:    "Car successfully saved but we are unable to send you email",
        588:    "Credit card processing failed",
        589:    "Updating Tour Failed: We are unable to find the tour",
        590:    "Tour Operator doesn't exist",

    }[code];
}