package ru.petrov.soap.spring.boot.Service;

import org.springframework.stereotype.Service;
import ru.petrov.soap.spring.boot.Entity.SoapRoleEntity;
import ru.petrov.soap.spring.boot.Repository.RoleRepository;

import javax.annotation.PostConstruct;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @PostConstruct
    void initRoles() {
        String[] roles = {"admin", "user", "moderator"};
        for (String roleName : roles) {
            SoapRoleEntity soapRoleEntity = roleRepository.findByName(roleName);
            if (soapRoleEntity == null) {
                soapRoleEntity = new SoapRoleEntity();
                soapRoleEntity.setName(roleName);
                roleRepository.save(soapRoleEntity);
            }
        }
    }
}
