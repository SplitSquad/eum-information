package com.information.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class InformationResDto {
    Long informationId;
    Long views;
    Long isState;
    String title;
    String content;
    String userName;
    String createdAt;
    String category;

    @Builder
    public InformationResDto(String category, String createdAt, String userName, String content, String title, Long isState, Long views, Long informationId) {
        this.category = category;
        this.createdAt = createdAt;
        this.userName = userName;
        this.content = content;
        this.title = title;
        this.isState = isState;
        this.views = views;
        this.informationId = informationId;
    }
}
