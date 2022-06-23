package ru.petrov.soap.spring.boot.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.petrov.soap.spring.boot.Entity.SoapRoleEntity;
import ru.petrov.soap.spring.boot.Entity.SoapUserEntity;

@Repository
public interface RoleRepository extends JpaRepository<SoapRoleEntity, Long> {
    @Query(value = "SELECT * FROM soap_role as s WHERE s.name  = :name", nativeQuery = true)
    SoapRoleEntity findByName(String name);

}
