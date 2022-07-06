package ru.petrov.soap.spring.boot.Model;


import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "SoapUser")
public class User {
    private String name;

    @Id
    @Column(unique = true)
    private String login;

    private String password;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_login"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles;

    public User() {
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

    public List<Role> getRole() {
        return roles;
    }

    public void setRole(List<Role> role) {
        this.roles = role;
    }

    @Override
    public String toString() {
        return "SoapUserEntity{" +
                "name='" + name + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", roles=" + roles +
                '}';
    }
}
