package com.jwtAuth.test.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jwtAuth.test.service.ContentService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContentController {

    @Autowired
    ContentService contentService;

    @GetMapping("/jobs")
    public ResponseEntity<?> listJob()throws JsonProcessingException{
        return contentService.listJob();
    }

    @GetMapping("/jobDetails")
    public ResponseEntity<?> jobDetails(@RequestParam(name = "id")String id) throws JsonProcessingException {
        return contentService.jobDetails(id);
    }

}
