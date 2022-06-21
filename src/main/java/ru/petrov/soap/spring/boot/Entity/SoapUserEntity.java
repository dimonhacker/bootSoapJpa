package ru.petrov.soap.spring.boot.Entity;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "SoapUser")
public class SoapUserEntity {
    private String name;

    @Id
    private String login;

    private String password;
    @ManyToMany(mappedBy = "users")
    private List<SoapRoleEntity> roles;

    public SoapUserEntity() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<SoapRoleEntity> getRole() {
        return roles;
    }

    public void setRole(List<SoapRoleEntity> role) {
        this.roles = role;
    }
}
