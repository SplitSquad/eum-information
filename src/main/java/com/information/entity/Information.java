package com.information.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "information")
@NoArgsConstructor
public class Information {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long informationId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String createdAt;
    private Long views;
    private String category;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now().toString();
    }

    @Builder
    public Information(Long informationId, User user, String createdAt, Long views, String category) {
        this.informationId = informationId;
        this.user = user;
        this.createdAt = createdAt;
        this.views = views;
        this.category = category;
    }
}