package com.example.Dawlati.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "roles")
@AllArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "ROLE_NAME")
    private String roleName;

    @OneToMany(mappedBy = "role")
    private List<User> users;

    public Role(Integer id, String roleName) {
        this.id = id;
        this.roleName = roleName;
    }
}
