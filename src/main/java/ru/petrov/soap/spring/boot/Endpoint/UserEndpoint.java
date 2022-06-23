package ru.petrov.soap.spring.boot.Endpoint;


import localhost.*;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import ru.petrov.soap.spring.boot.Service.UserService;

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

}
