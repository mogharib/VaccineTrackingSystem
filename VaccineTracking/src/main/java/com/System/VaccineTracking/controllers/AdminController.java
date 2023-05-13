package com.System.VaccineTracking.controllers;

import com.System.VaccineTracking.dtos.UserDto;
import com.System.VaccineTracking.services.AdminService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@Tag(name = "Admin")
@AllArgsConstructor
public class AdminController extends BaseController {

    private final AdminService adminService;


    @Operation(description = "Get User Data")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200"),
        @ApiResponse(responseCode = "404", description = "No User found with the provided National ID")})
    @GetMapping(path = "/users/{nationalId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> getUserInfo(@PathVariable String nationalId) {
        return new ResponseEntity<>(adminService.getUserInfo(nationalId), HttpStatus.OK);
    }

        @Operation(description = "Delete User")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200"),
        @ApiResponse(responseCode = "404", description =
            "No User found with the provided National ID")})
    @DeleteMapping(path = "/users/{nationalId}")
        @Transactional
    public ResponseEntity<ObjectNode> deleteUserById(@PathVariable String nationalId) {
            adminService.deleteUserInfo(nationalId);
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode tokenJson = objectMapper.createObjectNode();
        tokenJson.put("message", "Deleted Successfully");
        return new ResponseEntity<>(tokenJson, HttpStatus.OK);
    }

    @Operation(description = "Get Users By Number of vaccine Doses")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200")})
    @GetMapping(path = "/users/doses", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserDto>> getUserByNumberOfVaccineDoses(@RequestParam int dose) {
        return new ResponseEntity<>(adminService.getUserByNumberOfVaccineDoses(dose), HttpStatus.OK);
    }

    @Operation(description = "Get Users By Age")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200")})
    @GetMapping(path = "/users/ages", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserDto>> getUserByAge(@RequestParam int age) {
        return new ResponseEntity<>(adminService.getUsersByAge(age), HttpStatus.OK);
    }

    @Operation(description = "Get Waiting List Users")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200")})
    @GetMapping(path = "/users/waiting-list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserDto>> getWaitingListUser() {
        return new ResponseEntity<>(adminService.getWaitingListUsers(), HttpStatus.OK);
    }


    @Operation(description = "Get percentage for Zero Dose Users")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200")})
    @GetMapping(path = "/users/waiting-list/percentage", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getWaitingListUsersPercentage() {
        return new ResponseEntity<>(adminService.getWaitingListUsersPercentage(), HttpStatus.OK);
    }

    @Operation(description = "Get percentage for one Dose Users")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200")})
    @GetMapping(path = "/users/one-dose/percentage", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getOneVaccineDoseUsersPercentage() {
        return new ResponseEntity<>(adminService.getOneVaccineDoseUsersPercentage(), HttpStatus.OK);
    }

    @Operation(description = "Get percentage for Two Doses Users")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200")})
    @GetMapping(path = "/users/two-doses/percentage", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getTwoVaccineDoseUsersPercentage() {
        return new ResponseEntity<>(adminService.getTwoVaccineDoseUsersPercentage(), HttpStatus.OK);
    }
    @Operation(description = "Get Get Males Users percentage")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200")})
    @GetMapping(path = "/users/males/percentage", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getMalesUsersPercentage() {
        return new ResponseEntity<>(adminService.getMalesUsersPercentage(), HttpStatus.OK);
    }
    @Operation(description = "Get Females Users percentage")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200")})
    @GetMapping(path = "/users/females/percentage", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getFemalesUsersPercentage() {
        return new ResponseEntity<>(adminService.getFemalesUsersPercentage(), HttpStatus.OK);
    }

}
