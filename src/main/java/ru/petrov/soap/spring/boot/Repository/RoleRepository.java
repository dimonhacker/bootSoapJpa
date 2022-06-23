package ru.petrov.soap.spring.boot.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.petrov.soap.spring.boot.Entity.SoapRoleEntity;

@Repository
public interface RoleRepository extends JpaRepository<SoapRoleEntity, Long> {


}
