package ru.petrov.soap.spring.boot.Model;

import javax.persistence.*;

@Table(name = "SoapRole")
@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    public Role() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
                '}';
    }
}
