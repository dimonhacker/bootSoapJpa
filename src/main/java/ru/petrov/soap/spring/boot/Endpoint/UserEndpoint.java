package ru.petrov.soap.spring.boot.Endpoint;


import localhost.*;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import ru.petrov.soap.spring.boot.Service.UserService;

import java.util.ArrayList;

@Endpoint
public class UserEndpoint {

    private static final String NAMESPACE_URI = "http://localhost";


    private UserService userService;


    public UserEndpoint(UserService userService) {
        this.userService = userService;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getUserRequest")
    @ResponsePayload
    public GetUserResponse getUser(@RequestPayload GetUserRequest request) {
        GetUserResponse getUserResponse = new GetUserResponse();
        getUserResponse.setUser(userService.findByLogin(request.getLogin()));
        return getUserResponse;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllUsersRequest")
    @ResponsePayload
    public GetAllUsersResponse getAllUsers() {
        GetAllUsersResponse getUsers = new GetAllUsersResponse();
        getUsers.getUsers().addAll(userService.findAll());
        return getUsers;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "delUserRequest")
    @ResponsePayload
    public DelUserResponse getAllUsers(@RequestPayload DelUserRequest request) {
        DelUserResponse delUserResponse=new DelUserResponse();
        boolean success = userService.remove(request.getLogin());
        delUserResponse.setSuccess(success);
        return delUserResponse;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "createUserRequest")
    @ResponsePayload
    public CreateUserResponse createUser(@RequestPayload CreateUserRequest request) {
        CreateUserResponse createUserResponse=new CreateUserResponse();
        boolean result = userService.create(request.getName(),request.getLogin(),request.getPassword(),request.getRoles());
       createUserResponse.setSuccess(result);
       return createUserResponse;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateUserRequest")
    @ResponsePayload
    public UpdateUserResponse updateUser(@RequestPayload UpdateUserRequest request) {
        UpdateUserResponse updateUserResponse=new UpdateUserResponse();
        boolean result = userService.update(request.getName(),request.getLogin(),request.getPassword(),request.getRoles());
        updateUserResponse.setSuccess(result);
        return updateUserResponse;
    }

}
