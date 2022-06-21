package ru.petrov.soap.spring.boot.Repository;

import ru.petrov.soap.spring.boot.Generated.SoapUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<SoapUser,String> {


    @Query(value = "SELECT * FROM soap-user as s WHERE s.login  = :login", nativeQuery = true)
    SoapUser findByLogin(String login);
}
