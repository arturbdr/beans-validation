package com.learn.beanvalidation.beanvalidation.gateway.http.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.learn.beanvalidation.beanvalidation.gateway.http.to.Error;
import com.learn.beanvalidation.beanvalidation.gateway.http.to.GroupOfPersons;
import com.learn.beanvalidation.beanvalidation.gateway.http.to.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class SampleController {

    private final Validator validator;
    private final ObjectMapper objectMapper;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity validateMyBean(@RequestBody @Valid @NotNull Person person) {
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "group", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity validateMyGroupBean(@RequestBody @Valid @NotNull final GroupOfPersons groupOfPersons) {
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "groupwithcollection", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity validateCollection(@RequestBody @NotNull Collection<Person> groupOfPersons) throws JsonProcessingException {

        Set<Set<ConstraintViolation<Person>>> collect = groupOfPersons
                .stream()
                .map(eachPerson -> validator.validate(eachPerson))
                .collect(Collectors.toSet());

        if (!collect.isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .body(collect);

        }
        return ResponseEntity.ok().build();
    }
}
