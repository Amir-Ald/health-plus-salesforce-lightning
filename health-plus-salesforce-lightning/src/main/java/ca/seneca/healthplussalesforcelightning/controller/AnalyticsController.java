package ca.seneca.healthplussalesforcelightning.controller;

import ca.seneca.healthplussalesforcelightning.model.request.AnalyticRequest;
import ca.seneca.healthplussalesforcelightning.model.response.AnalyticResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "v1/analytics")
public class AnalyticsController {

    @GetMapping(path = "")
    @ResponseBody
    public ResponseEntity<List<AnalyticResponse>> searchAnalytic() {
        return new ResponseEntity<List<AnalyticResponse>>((List<AnalyticResponse>) null, HttpStatus.OK);
    }

    //TODO: Implement searches by other fields
    @Operation(summary = "Search an analytic by ID")
    @GetMapping(path = "/{analyticId}")
    @ResponseBody
    public ResponseEntity<AnalyticResponse> searchAnalyticById(@PathVariable(name = "id") String id) {
        return new ResponseEntity<AnalyticResponse>((AnalyticResponse) null, HttpStatus.OK);
    }

    @Operation(summary = "Create an analytic")
    @PostMapping(path = "")
    @ResponseBody
    public ResponseEntity<AnalyticResponse> createAnalytic(@RequestBody AnalyticRequest newAnalytic) {
        return new ResponseEntity<AnalyticResponse>((AnalyticResponse) null, HttpStatus.OK);
    }

    @Operation(summary = "Update an analytic")
    @PutMapping(path = "")
    @ResponseBody
    public ResponseEntity<AnalyticResponse> updateAnalytic(@RequestBody AnalyticRequest newAnalytic) {
        return new ResponseEntity<AnalyticResponse>((AnalyticResponse) null, HttpStatus.OK);
    }

    @Operation(summary = "Delete an analytic by ID")
    @DeleteMapping(path = "/{analyticId}")
    @ResponseBody
    public ResponseEntity<AnalyticResponse> deleteAnalyticById(@PathVariable(name = "id") String id) {
        return new ResponseEntity<AnalyticResponse>((AnalyticResponse) null, HttpStatus.OK);
    }
}
