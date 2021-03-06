package ru.petrov.soap.spring.boot.Endpoint;


import localhost.*;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import ru.petrov.soap.spring.boot.Service.UserServiceImpl;

import java.util.List;

@Endpoint
public class UserEndpoint {

    private static final String NAMESPACE_URI = "http://localhost";

    private final UserServiceImpl userServiceImpl;


    public UserEndpoint(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getUserRequest")
    @ResponsePayload
    public GetUserResponse getUser(@RequestPayload GetUserRequest request) {
        GetUserResponse getUserResponse = new GetUserResponse();
        getUserResponse.setUser(userServiceImpl.findByLogin(request.getLogin()));
        return getUserResponse;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllUsersRequest")
    @ResponsePayload
    public GetAllUsersResponse getAllUsers() {
        GetAllUsersResponse getUsers = new GetAllUsersResponse();
        getUsers.getUsers().addAll(userServiceImpl.findAll());
        return getUsers;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "delUserRequest")
    @ResponsePayload
    public DelUserResponse getAllUsers(@RequestPayload DelUserRequest request) {
        DelUserResponse delUserResponse = new DelUserResponse();
        boolean success = userServiceImpl.remove(request.getLogin());
        delUserResponse.setSuccess(success);
        return delUserResponse;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "createUserRequest")
    @ResponsePayload
    public CreateUserResponse createUser(@RequestPayload CreateUserRequest request) {
        CreateUserResponse createUserResponse = new CreateUserResponse();
        List<String> errors = userServiceImpl.validatePass(request.getName(), request.getLogin(), request.getPassword());
        if (errors.size() > 0) {
            createUserResponse.setSuccess(false);
            createUserResponse.getErrors().addAll(errors);
            return createUserResponse;
        }
        boolean result = userServiceImpl.create(request.getName(), request.getLogin(), request.getPassword(), request.getRole());
        createUserResponse.setSuccess(result);
        return createUserResponse;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateUserRequest")
    @ResponsePayload
    public UpdateUserResponse updateUser(@RequestPayload UpdateUserRequest request) {
        UpdateUserResponse updateUserResponse = new UpdateUserResponse();
        List<String> errors = userServiceImpl.validatePass(request.getName(), request.getLogin(), request.getPassword());
        if (errors.size() > 0) {
            updateUserResponse.setSuccess(false);
            updateUserResponse.getErrors().addAll(errors);
            return updateUserResponse;
        }
        boolean result = userServiceImpl.update(request.getName(), request.getLogin(), request.getPassword(), request.getRole());
        updateUserResponse.setSuccess(result);
        return updateUserResponse;
    }

}
