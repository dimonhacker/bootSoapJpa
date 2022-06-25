package ru.petrov.soap.spring.boot.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.petrov.soap.spring.boot.Entity.SoapUserEntity;

@Repository
public interface UserRepository extends JpaRepository<SoapUserEntity, String> {


    @Query(value = "SELECT * FROM soap_user as s WHERE s.login  = :login", nativeQuery = true)
    SoapUserEntity findByLogin(String login);

    @Modifying
    @Query(value = "Delete  from  users_roles  where user_login = :login", nativeQuery = true)
    void deleteByLogin(String login);
}
