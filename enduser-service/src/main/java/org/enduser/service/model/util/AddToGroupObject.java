package org.enduser.service.model.util;

import java.io.Serializable;

import org.springframework.stereotype.Component;

/**
 * @author solxget
 *
 */
@Component
public class AddToGroupObject implements Serializable {
    private static final long serialVersionUID = 1L;

    private String groupId;
    private String emailAddress;

    public AddToGroupObject() {
        super();
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }


}
