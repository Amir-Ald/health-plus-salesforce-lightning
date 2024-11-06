package ca.seneca.healthplussalesforcelightning.controller;

import ca.seneca.healthplussalesforcelightning.model.request.MembershipRequest;
import ca.seneca.healthplussalesforcelightning.model.response.MembershipResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "v1/membership")
public class MembershipController {

    @GetMapping(path = "")
    @ResponseBody
    public ResponseEntity<List<MembershipResponse>> searchMemberships() {
        return new ResponseEntity<List<MembershipResponse>>(new ArrayList<MembershipResponse>(), HttpStatus.OK);
    }

    //TODO: Implement searches by other fields
    @Operation(summary = "Search a membership by ID")
    @GetMapping(path = "/{membershipId}")
    @ResponseBody
    public ResponseEntity<MembershipResponse> searchMembershipById(@PathVariable(name = "id") String id) {
        return new ResponseEntity<MembershipResponse>((MembershipResponse) null, HttpStatus.OK);
    }

    @Operation(summary = "Create a membership")
    @PostMapping(path = "")
    @ResponseBody
    public ResponseEntity<MembershipResponse> createMembership(@RequestBody MembershipRequest newMembership) {
        return new ResponseEntity<MembershipResponse>((MembershipResponse) null, HttpStatus.OK);
    }

    @Operation(summary = "Update a membership")
    @PutMapping(path = "")
    @ResponseBody
    public ResponseEntity<MembershipResponse> updateMembership(@RequestBody MembershipRequest newMembership) {
        return new ResponseEntity<MembershipResponse>((MembershipResponse) null, HttpStatus.OK);
    }

    @Operation(summary = "Delete a membership by ID")
    @DeleteMapping(path = "/{membershipId}")
    @ResponseBody
    public ResponseEntity<MembershipResponse> deleteMembershipById(@PathVariable(name = "id") String id) {
        return new ResponseEntity<MembershipResponse>((MembershipResponse) null, HttpStatus.OK);
    }
}
