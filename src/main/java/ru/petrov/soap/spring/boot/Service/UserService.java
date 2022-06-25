package ru.petrov.soap.spring.boot.Service;

import localhost.SoapRole;

import java.util.List;

public interface UserService {

    boolean remove(String login);
    boolean update(String name, String login, String password, List<SoapRole> roles);
    boolean create(String name, String login, String password, List<SoapRole> roles);
}
