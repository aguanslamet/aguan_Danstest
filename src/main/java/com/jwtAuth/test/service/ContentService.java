package com.jwtAuth.test.service;

import com.jwtAuth.test.dto.ApiJobs;
import com.jwtAuth.test.dto.DefaultResponse;
import com.jwtAuth.test.dto.GetJobResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

@Service
@Slf4j
public class ContentService {
    @Value("${urlJobs}")
    private String urlJobs;
    @Value("${urlJobDetail}")
    private String urlJobDetail;

    public ResponseEntity<?> listJob(){
        try {
            var getResponse = get("http://dev3.dansmultipro.co.id/api/recruitment/positions.json", ApiJobs[].class);
            List<GetJobResponse> response = new ArrayList<>();
            for (ApiJobs apiResponseDto : getResponse) {
                GetJobResponse getJobResponseDto = GetJobResponse
                        .builder()
                        .id(apiResponseDto.getId())
                        .company(apiResponseDto.getCompany())
                        .type(apiResponseDto.getType())
                        .companyUrl(apiResponseDto.getCompany_url())
                        .url(apiResponseDto.getUrl())
                        .createdAt(apiResponseDto.getCreated_at())
                        .title(apiResponseDto.getTitle())
                        .location(apiResponseDto.getLocation())
                        .description(apiResponseDto.getDescription())
                        .howToApply(apiResponseDto.getHow_to_apply())
                        .companyLogo(apiResponseDto.getCompany_logo())
                        .build();
                response.add(getJobResponseDto);
            }
                return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Request failed with error: " + e.getMessage());
        }
    }
    public ResponseEntity<?> jobDetails(String id) {
        try {
            var getResponse = get(urlJobDetail + id, ApiJobs.class);
            GetJobResponse getJobResponseDto = GetJobResponse
                    .builder()
                    .id(getResponse.getId())
                    .company(getResponse.getCompany())
                    .type(getResponse.getType())
                    .companyUrl(getResponse.getCompany_url())
                    .url(getResponse.getUrl())
                    .createdAt(getResponse.getCreated_at())
                    .title(getResponse.getTitle())
                    .location(getResponse.getLocation())
                    .description(getResponse.getDescription())
                    .howToApply(getResponse.getHow_to_apply())
                    .companyLogo(getResponse.getCompany_logo())
                    .build();
            return ResponseEntity.ok(
                    new DefaultResponse(
                            "200",
                            "Success",
                            getJobResponseDto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Request failed with error: " + e.getMessage());
        }
    }


    public <T> T get(String url, Class<T> responseBody) throws TimeoutException {
        RestTemplate restTemplate = new RestTemplate();
        T responseObject;
        try {
            ResponseEntity<T> response = restTemplate.getForEntity(url, responseBody);
            responseObject = response.getBody();
        } catch (Exception e) {
            log.error("timeout : ", e);
            throw new TimeoutException("timeout when calling from url :" + url);
        }
        return responseObject;
    }
}
