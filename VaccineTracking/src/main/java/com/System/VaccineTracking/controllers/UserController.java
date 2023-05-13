package com.System.VaccineTracking.controllers;

import com.System.VaccineTracking.dtos.UserDto;
import com.System.VaccineTracking.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.security.Principal;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@Tag(name = "V3-User")
public class UserController extends BaseController {

    private final UserService userService;

    @Qualifier("modelMapper")
    @Autowired
    protected ModelMapper modelMapper;


    public UserController(UserService userService) {this.userService = userService;}


    @Operation(description = "Get User Data")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200"),
        @ApiResponse(responseCode = "404", description = "No User found with the provided "
            + "National ID")})
    @GetMapping(path = "/info", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> getUserInfo(Principal principal) {
        if (!principal.getName().equals(principal.getName())) {
            return new ResponseEntity("please Use your National ID", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(userService.getUserInfo(principal.getName()), HttpStatus.OK);
    }

    @Operation(description = "Add User Data")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201")})
    @PostMapping(path = "/info", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> addUserInfo(Principal principal,
        @RequestBody @Valid CreateUserInfoRequest user) {
        UserDto userDto = userService.addUserInfo(modelMapper.map(user, UserDto.class),
            principal.getName());
        return new ResponseEntity<>(userDto, HttpStatus.CREATED);
    }

    @Operation(description = "Delete User Data ")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200"),
        @ApiResponse(responseCode = "404", description = "No User found with the provided "
            + "National ID")})
    @DeleteMapping(path = "/info")
    @Transactional
    public ResponseEntity<ObjectNode> deleteUserById(Principal principal) {
        if (!principal.getName().equals(principal.getName())) {
            return new ResponseEntity("please Use your National ID", HttpStatus.BAD_REQUEST);
        }
        userService.deleteUserInfo(principal.getName());
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode tokenJson = objectMapper.createObjectNode();
        tokenJson.put("message", "Deleted Successfully");
        return new ResponseEntity<>(tokenJson, HttpStatus.OK);
    }

    @Operation(description = "Update User Date")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200"),
        @ApiResponse(responseCode = "404", description = "No User found with the provided "
            + "National ID")})
    @PatchMapping(path = "/info", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> updateUserById(Principal principal,
        @Valid @RequestBody UpdateUserInfoRequest user) {
        if (!principal.getName().equals(principal.getName())) {
            return new ResponseEntity("please Use your National ID", HttpStatus.BAD_REQUEST);
        }
        UserDto userDto = userService.updateUserInfo(modelMapper.map(user, UserDto.class),
            principal.getName());
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @Data
    @NoArgsConstructor
    private static class CreateUserInfoRequest {

        @NotNull
        @Size(min = 3, max = 64)
        private String fullName;

        @NotNull
        private int age;

        @NotNull
        private String governorate;

        @NotNull
        private String gender;

        @NotNull
        @Min(0)
        @Max(2)
        private int doses;

    }

    @Data
    @NoArgsConstructor
    private static class UpdateUserInfoRequest {

        @Size(min = 8, max = 32)
        private String password;

        @Size(min = 3, max = 64)
        private String fullName;
        private int age;
        private String governorate;
        private String gender;

        @Min(0)
        @Max(2)
        private int doses;

    }

}
