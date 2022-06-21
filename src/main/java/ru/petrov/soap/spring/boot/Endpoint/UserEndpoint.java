package ru.petrov.soap.spring.boot.Endpoint;


import ru.petrov.soap.spring.boot.Generated.GetUserRequest;
import ru.petrov.soap.spring.boot.Generated.GetUserResponse;
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
    public GetUserResponse getUserResponse(@RequestPayload GetUserRequest request){

        GetUserResponse getUserResponse = new GetUserResponse();
        getUserResponse.setUser(userService.findByLogin(request.getLogin()));
        return getUserResponse;
    }
}
