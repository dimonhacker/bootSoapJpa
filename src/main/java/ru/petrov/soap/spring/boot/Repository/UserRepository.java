package ru.petrov.soap.spring.boot.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.petrov.soap.spring.boot.Model.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {


    @Query(value = "SELECT * FROM soap_user as s WHERE s.login  = :login", nativeQuery = true)
    User findByLogin(String login);

}
