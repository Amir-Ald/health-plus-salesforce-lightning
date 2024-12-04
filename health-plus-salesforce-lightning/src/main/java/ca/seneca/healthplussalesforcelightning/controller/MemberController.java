package ca.seneca.healthplussalesforcelightning.controller;

import ca.seneca.healthplussalesforcelightning.model.Members;
import ca.seneca.healthplussalesforcelightning.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/members")
public class MemberController {
    @Autowired
    private MemberService memberService;

    @GetMapping
    public ResponseEntity<List<Members>> getAllMembers() {
        List<Members> members = memberService.getAllMembers();
        return ResponseEntity.ok(members);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMemberById(@PathVariable Long id) {
        Optional<Members> member = Optional.ofNullable(memberService.getMemberById(id));
        if (member.isPresent()) {
            return ResponseEntity.ok(member.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Member not found");
        }
    }

    @PostMapping
    public ResponseEntity<Members> addMember(@RequestBody Members member) {
        Members savedMember = memberService.addMember(member);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMember);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateMember(@PathVariable Long id, @RequestBody Members member) {
        Optional<Members> existingMember = Optional.ofNullable(memberService.getMemberById(id));
        if (existingMember.isPresent()) {
            Members updatedMember = memberService.updateMember(id, member);
            return ResponseEntity.ok(updatedMember);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Member not found");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMember(@PathVariable Long id) {
        Optional<Members> existingMember = Optional.ofNullable(memberService.getMemberById(id));
        if (existingMember.isPresent()) {
            memberService.deleteMember(id);
            return ResponseEntity.ok().body("Member deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Member not found");
        }
    }
}