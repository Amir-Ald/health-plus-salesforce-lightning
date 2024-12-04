package ca.seneca.healthplussalesforcelightning.service;

import ca.seneca.healthplussalesforcelightning.model.Members;
import ca.seneca.healthplussalesforcelightning.repository.MembersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {
    @Autowired
    private MembersRepository membersRepository;

    public List<Members> getAllMembers() {
        return membersRepository.findAll();
    }

    public Members getMemberById(Long id) {
        return membersRepository.findById(id).orElse(null);
    }

    public Members addMember(Members member) {
        return membersRepository.save(member);
    }

    public Members updateMember(Long id, Members member) {
        member.setId(id);
        return membersRepository.save(member);
    }

    public void deleteMember(Long id) {
        membersRepository.deleteById(id);
    }
}