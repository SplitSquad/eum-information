package com.information.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "information_file")
@NoArgsConstructor
public class InformationFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long informationFileId;

    @ManyToOne
    @JoinColumn(name = "information_id")
    private Information information;

    private String url;

    @Builder
    public InformationFile(Long informationFileId, Information information, String url) {
        this.informationFileId = informationFileId;
        this.information = information;
        this.url = url;
    }
}
