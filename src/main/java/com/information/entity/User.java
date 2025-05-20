package com.information.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "user")
@NoArgsConstructor
public class User {
    @Id
    private Long userId;

    private String name;
    private String nation;
    private String language;
    private String role;
    private String address;

    @Builder
    public User(Long userId, String name, String nation, String language, String role, String address) {
        this.userId = userId;
        this.name = name;
        this.nation = nation;
        this.language = language;
        this.role = role;
        this.address = address;
    }
}
