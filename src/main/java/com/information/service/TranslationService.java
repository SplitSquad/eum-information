package com.information.service;

import com.information.dto.InformationReqDto;
import com.information.entity.Information;
import com.information.entity.TranslatedInformation;
import com.information.repository.TranslatedInformationRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
@RequiredArgsConstructor
public class TranslationService {
    @Value("${translation.api-key}")
    private String apiKey;

    private static final String API_URL = "https://translation.googleapis.com/language/translate/v2";

    private final String[] targetLanguage = {"KO", "EN", "JA", "ZH", "DE", "FR", "ES", "RU"};
    private final String[] GoogleTargetLanguage = {"ko", "en", "ja", "zh-CN", "de", "fr", "es", "ru"};

    private final RestTemplate restTemplate = new RestTemplate();

    private final TranslatedInformationRepository translatedInformationRepository;

    @Async
    public void translateInformation(Information information, InformationReqDto informationReqDto,
                                     Long informationId) throws JsonProcessingException {
        for (int i = 0; i < targetLanguage.length; i++) { // 9개 언어로 번역해서 저장
            TranslatedInformation translatedInformation;
            if (informationId == null) {
                translatedInformation = new TranslatedInformation();
            } else {
                translatedInformation = translatedInformationRepository
                        .findByInformation_InformationIdAndLanguage(informationId, targetLanguage[i]);
            }
            translatedInformation.setInformation(information);
            translatedInformation.setLanguage(targetLanguage[i]);

            if (targetLanguage[i].equals("KO")) {
                translatedInformation.setContent(informationReqDto.getContent());
                translatedInformation.setTitle(informationReqDto.getTitle());
                translatedInformationRepository.save(translatedInformation);
                continue;
            }

            String translatedTitle = translate(
                    informationReqDto.getTitle(), "ko", GoogleTargetLanguage[i]);

            String translatedContent = translateRichContent(informationReqDto.getContent(),
                    "ko", GoogleTargetLanguage[i]);

            if (translatedTitle.isEmpty() || translatedContent.isEmpty()) continue;

            translatedInformation.setContent(translatedContent);
            translatedInformation.setTitle(translatedTitle);
            translatedInformationRepository.save(translatedInformation);
        }
    }

    public String translate(String text, String sourceLang, String targetLang) {
        try {
            String url = API_URL + "?key=" + apiKey;

            Map<String, Object> body = new HashMap<>();
            body.put("q", text);
            body.put("source", sourceLang);
            body.put("target", targetLang);
            body.put("format", "text");

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.getBody());
            return root.path("data").path("translations").get(0).path("translatedText").asText();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String translateRichContent(String jsonContent, String fromLang, String toLang) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(jsonContent);

        translateTextNodesRecursively(root, fromLang, toLang);

        return mapper.writeValueAsString(root);
    }

    private void translateTextNodesRecursively(JsonNode node, String fromLang, String toLang) {
        if (node.isObject()) {
            ObjectNode obj = (ObjectNode) node;

            // "text" 필드가 있을 경우 번역
            if (obj.has("text") && obj.get("text").isTextual()) {
                String original = obj.get("text").asText();
                String translated = translate(original, fromLang, toLang);
                if (translated != null) {
                    obj.put("text", translated);
                }
            }

            // 모든 필드에 대해 재귀 호출
            Iterator<Map.Entry<String, JsonNode>> fields = obj.fields();
            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> entry = fields.next();
                translateTextNodesRecursively(entry.getValue(), fromLang, toLang);
            }

        } else if (node.isArray()) {
            for (JsonNode child : node) {
                translateTextNodesRecursively(child, fromLang, toLang);
            }
        }
    }
}
    /*public Optional<String> translate(String text, String sourceLang, String targetLang) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("auth_key", apiKey);
        body.add("text", text);
        body.add("source_lang", sourceLang.toUpperCase()); // ex: KO
        body.add("target_lang", targetLang.toUpperCase()); // ex: EN

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(API_URL, request, Map.class);
            Map<String, Object> result = response.getBody();

            List<Map<String, String>> translations = (List<Map<String, String>>) result.get("translations");
            return Optional.of(translations.get(0).get("text"));
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
}*/