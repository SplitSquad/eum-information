package com.information.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "translated_information")
@NoArgsConstructor
public class TranslatedInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long translatedInformationId;

    @ManyToOne
    @JoinColumn(name = "information_id")
    private Information information;

    private String title;
    @Column(columnDefinition = "TEXT")
    private String content;
    private String language;

    @Builder
    public TranslatedInformation(Long translatedInformationId, Information information, String title, String content, String language) {
        this.translatedInformationId = translatedInformationId;
        this.information = information;
        this.title = title;
        this.content = content;
        this.language = language;
    }
}
