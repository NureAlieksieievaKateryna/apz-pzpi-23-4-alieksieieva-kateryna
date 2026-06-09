package org.example.reminder.controllers;

import lombok.RequiredArgsConstructor;
import org.example.reminder.business.facade.BusinessFacade;
import org.example.reminder.business.facade.dto.BusinessCreateRequest;
import org.example.reminder.common.model.TokenResponse;
import org.example.reminder.user.dto.*;
import org.example.reminder.user.facade.UserFacade;
import org.example.reminder.user.service.UserService;
import org.example.reminder.user.model.UserEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/client")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserFacade userFacade;
    private final BusinessFacade businessFacade;

    @PostMapping("register")
    public void register(@RequestBody UserResisterRequest request) throws Exception {
        userFacade.registerUser(request);
    }

    @PostMapping("register/business")
    public void registerBusiness(@RequestBody BusinessCreateRequest businessCreateRequest) throws Exception {
        businessFacade.createBusiness(businessCreateRequest);
    }

    @PostMapping("login")
    public LoginResponse login(@RequestBody UserSignInRequest userSignInRequest) throws Exception {
        return userService.login(userSignInRequest);
    }

    @GetMapping("{userId}/home")
    public UserHomeResponse getUserHome(@PathVariable Long userId) {
        return userFacade.getUserHome(userId);
    }

    @GetMapping("{id}")
    public UserResponse getUser(@PathVariable Long id){
       return userFacade.findUserById(id);
    }

    @GetMapping("findAll")
    public List<UserEntity> getUsers(){
        return userService.findAllUsers();
    }


}
