package ru.petrov.soap.spring.boot.Repository;

import localhost.SoapUser;
import ru.petrov.soap.spring.boot.Entity.SoapUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<SoapUserEntity,String> {


    @Query(value = "SELECT * FROM soap_user as s WHERE s.login  = :login", nativeQuery = true)
    SoapUserEntity findByLogin(String login);
}
