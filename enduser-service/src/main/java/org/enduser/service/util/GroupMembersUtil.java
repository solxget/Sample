package org.enduser.service.util;

import org.enduser.service.delegates.GroupMembersDelegate;
import org.enduser.service.model.GroupMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GroupMembersUtil {

    private GroupMembersDelegate GroupMembersDelegate;

    @Autowired
    public void setGroupMembersDelegate(GroupMembersDelegate groupMembersDelegate) {
        GroupMembersDelegate = groupMembersDelegate;
    }

    public GroupMember addTuristToGroup(String groupId, String emailAddress) {
        GroupMember groupMember = new GroupMember(emailAddress, groupId, "White");
        return GroupMembersDelegate.saveGroupMember(groupMember);
    }

}
