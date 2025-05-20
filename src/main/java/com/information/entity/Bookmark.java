package com.information.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "bookmark")
@NoArgsConstructor
public class Bookmark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookmarkId;

    @ManyToOne
    @JoinColumn(name = "information_id")
    private Information information;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Bookmark(Long bookmarkId, Information information, User user) {
        this.bookmarkId = bookmarkId;
        this.information = information;
        this.user = user;
    }
}
