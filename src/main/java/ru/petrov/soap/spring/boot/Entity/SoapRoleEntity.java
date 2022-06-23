package ru.petrov.soap.spring.boot.Entity;

import javax.persistence.*;
import java.util.List;

@Table(name = "SoapRole")
@Entity
public class SoapRoleEntity {

    @Id
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "roles", cascade = CascadeType.ALL)
    private List<SoapUserEntity> users;

    public SoapRoleEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<SoapUserEntity> getUsers() {
        return users;
    }

    public void setUsers(List<SoapUserEntity> users) {
        this.users = users;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "SoapRoleEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", users=" + users +
                '}';
    }
}
