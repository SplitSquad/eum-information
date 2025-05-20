package com.information.dto;

import lombok.Data;

@Data
public class KafkaUserDto {
    private Long userId;
    private String name;
    private String nation;
    private String language;
    private String role;
    private String address;
}
