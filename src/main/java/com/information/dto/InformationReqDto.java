package com.information.dto;

import lombok.Data;

import java.util.List;

@Data
public class InformationReqDto {
    String title;
    String content;
    String category;
    List<String> files;
}
