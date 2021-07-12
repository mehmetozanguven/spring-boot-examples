package com.mehmetozanguven.springbootjwtexample.dto;

import javax.persistence.*;

@Entity
@Table(name = "user_roles")
public class UserRoleDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String role;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserDTO userDTO;

    public long getId() {
        return id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }
}
