package com.information.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.information.dto.KafkaUserDto;
import com.information.entity.User;
import com.information.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics="updateUser", groupId = "eum-information")
    public void updateUser(String message){
        KafkaUserDto kafkaUserDto;
        try{
            kafkaUserDto = objectMapper.readValue(message ,KafkaUserDto.class);
        }catch (Exception e){
            e.printStackTrace();
            return;
        }
        User user = userRepository.findById(kafkaUserDto.getUserId()).get();
        user.setName(kafkaUserDto.getName());
        user.setNation(kafkaUserDto.getNation());
        user.setLanguage(kafkaUserDto.getLanguage());
        user.setRole(kafkaUserDto.getRole());
        user.setAddress(kafkaUserDto.getAddress());
        userRepository.save(user);
    }

    @KafkaListener(topics="updateLanguage", groupId = "eum-information")
    public void updateLanguage(String message){
        KafkaUserDto kafkaUserDto;
        try{
            kafkaUserDto = objectMapper.readValue(message ,KafkaUserDto.class);
        }catch (Exception e){
            e.printStackTrace();
            return;
        }
        User user = userRepository.findById(kafkaUserDto.getUserId()).get();
        user.setLanguage(kafkaUserDto.getLanguage());
        userRepository.save(user);
    }

    @KafkaListener(topics="createUser", groupId = "eum-information")
    public void createUser(String message) {
        KafkaUserDto kafkaUserDto;
        try {
            kafkaUserDto = objectMapper.readValue(message, KafkaUserDto.class);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        User user = User.builder()
                .name(kafkaUserDto.getName())
                .nation(kafkaUserDto.getNation())
                .language(kafkaUserDto.getLanguage())
                .role(kafkaUserDto.getRole())
                .address(kafkaUserDto.getAddress())
                .userId(kafkaUserDto.getUserId())
                .build();
        userRepository.save(user);
    }

    @Transactional
    @KafkaListener(topics="deleteUser", groupId = "eum-information")
    public void deleteUser(String message){
        KafkaUserDto kafkaUserDto;
        try{
            kafkaUserDto = objectMapper.readValue(message ,KafkaUserDto.class);
        }catch (Exception e){
            e.printStackTrace();
            return;
        }
        User user = userRepository.findById(kafkaUserDto.getUserId()).get();
        userRepository.delete(user);
    }
}
