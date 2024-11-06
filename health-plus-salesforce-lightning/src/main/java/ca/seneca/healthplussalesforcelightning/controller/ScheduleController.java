package ca.seneca.healthplussalesforcelightning.controller;

import ca.seneca.healthplussalesforcelightning.model.request.ScheduleRequest;
import ca.seneca.healthplussalesforcelightning.model.response.ScheduleResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "v1/schedule")
public class ScheduleController {

    @GetMapping(path = "")
    @ResponseBody
    public ResponseEntity<List<ScheduleResponse>> searchSchedules() {
        return new ResponseEntity<List<ScheduleResponse>>(new ArrayList<>(), HttpStatus.OK);
    }

    //TODO: Implement searches by other fields
    @Operation(summary = "Search an schedule by ID")
    @GetMapping(path = "/{scheduleId}")
    @ResponseBody
    public ResponseEntity<ScheduleResponse> searchScheduleById(@PathVariable(name = "id") String id) {
        return new ResponseEntity<ScheduleResponse>((ScheduleResponse) null, HttpStatus.OK);
    }

    @Operation(summary = "Create an schedule")
    @PostMapping(path = "")
    @ResponseBody
    public ResponseEntity<ScheduleResponse> createSchedules(@RequestBody ScheduleRequest newSchedule) {
        return new ResponseEntity<ScheduleResponse>((ScheduleResponse) null, HttpStatus.OK);
    }

    @Operation(summary = "Update an schedule")
    @PutMapping(path = "")
    @ResponseBody
    public ResponseEntity<ScheduleResponse> updateSchedules(@RequestBody ScheduleRequest newSchedule) {
        return new ResponseEntity<ScheduleResponse>((ScheduleResponse) null, HttpStatus.OK);
    }

    @Operation(summary = "Delete an schedule by ID")
    @DeleteMapping(path = "/{scheduleId}")
    @ResponseBody
    public ResponseEntity<ScheduleResponse> deleteScheduleById(@PathVariable(name = "id") String id) {
        return new ResponseEntity<ScheduleResponse>((ScheduleResponse) null, HttpStatus.OK);
    }
}
