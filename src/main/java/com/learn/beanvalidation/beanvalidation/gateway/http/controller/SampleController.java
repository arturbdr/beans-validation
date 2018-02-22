package com.learn.beanvalidation.beanvalidation.gateway.http.controller;

import com.learn.beanvalidation.beanvalidation.gateway.http.to.GroupOfPersons;
import com.learn.beanvalidation.beanvalidation.gateway.http.to.Person;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
public class SampleController {


    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity validateMyBean(@RequestBody @Valid @NotNull Person person) {
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "group", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity validateMyGroupBean(@RequestBody @Valid @NotNull GroupOfPersons groupOfPersons) {
        return ResponseEntity.ok().build();
    }
}
