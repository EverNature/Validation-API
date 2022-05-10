package eus.evernature.evern.service.expert;

import java.util.List;

import eus.evernature.evern.models.Expert;

public interface ExpertService {
    Expert saveUser(Expert expert);
    void addRoleToUser(String username, String roleName);
    Expert getExpert(String username);
    List<Expert> getExperts();
}
