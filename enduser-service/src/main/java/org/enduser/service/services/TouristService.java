package org.enduser.service.services;

import java.util.List;

import org.enduser.service.model.GroupMember;
import org.enduser.service.model.Tourist;
import org.enduser.service.model.TouristGroup;
import org.enduser.service.model.util.AddToGroupObject;

/**
 * @author solxget
 * 
 */
public interface TouristService {

    public List<Tourist> getTourist(String emailAddress);
    public Tourist createTourist(Tourist tourist);
    public Tourist updateTourist(String key, String value, String emailAddress);
    public void deleteTourist(String emailAddress, String statusChangeReason);
    public GroupMember addToGroup(AddToGroupObject addToGroupObject);
    public void deleteFromGroup(GroupMember groupMember);
    public List<TouristGroup> getMyGroups(String emailAddress);

}
