package com.information.controller;

import com.information.dto.InformationReqDto;
import com.information.service.InformationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/information")
@RequiredArgsConstructor
public class InformationController {
    private final InformationService informationService;

    @PostMapping // 정보글 작성
    public ResponseEntity<?> write(@RequestHeader("Authorization") String token,
                                   @RequestBody InformationReqDto informationReqDto) throws JsonProcessingException {
        return informationService.write(token,informationReqDto);
    }

    @PostMapping("/file") // 파일 업로드
    public ResponseEntity<?> uploadFile(MultipartFile file) {
        return informationService.uploadFile(file);
    }

    @DeleteMapping("/file/{url}") // 파일 삭제
    public ResponseEntity<?> deleteFile(@PathVariable String url) {
        return informationService.deleteFile(url);
    }

    @GetMapping // 정보글 목록 조회
    public ResponseEntity<?> getInformationList(@RequestHeader("Authorization") String token,
                                            int page, int size, String category, String sort) {
        return informationService.getInformationList(token, page, size, category, sort);
    }

    @GetMapping("/{informationId}")  // 정보글 상세 조회
    public ResponseEntity<?> getInformation(@RequestHeader("Authorization") String token,
                                            @PathVariable long informationId) {
        return informationService.getInformation(token, informationId);
    }

    @DeleteMapping("/{informationId}") // 정보글 삭제
    public ResponseEntity<?> deleteInformation(@RequestHeader("Authorization") String token,
                                               @PathVariable long informationId) {
        return informationService.deleteInformation(token, informationId);
    }

    @PostMapping("/{informationId}") // 정보글 북마크
    public ResponseEntity<?> bookmarking(@RequestHeader("Authorization") String token,
                                         @PathVariable long informationId) {
        return informationService.bookmarking(token, informationId);
    }

    @PatchMapping("/{informationId}") // 정보글 수정
    public ResponseEntity<?> updateInformation(@RequestHeader("Authorization") String token,
                                               @PathVariable long informationId,
                                               @RequestBody InformationReqDto informationReqDto) throws JsonProcessingException {
        return informationService.updateInformation(token, informationId, informationReqDto);
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchInformation(@RequestHeader("Authorization") String token,
                                               String keyword, int page, int size, String category, String sort){
        return informationService.searchInformation(token, keyword, page, size, category, sort);
    }

    @GetMapping("/bookmark")
    public ResponseEntity<?> getBookmarking(@RequestHeader("Authorization") String token,
                                            long userId ,int page, int size){
        return informationService.getBookmarking(token, userId, page, size);
    }
}
