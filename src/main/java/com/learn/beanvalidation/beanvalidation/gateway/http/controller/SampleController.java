package com.learn.beanvalidation.beanvalidation.gateway.http.controller;

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
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class SampleController {

    private final Validator validator;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity validateMyBean(@RequestBody @Valid @NotNull Person person) {
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "group", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity validateMyGroupBean(@RequestBody @Valid @NotNull final GroupOfPersons groupOfPersons) {
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "groupwithcollection", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity validateCollection(@RequestBody @NotNull Collection<Person> groupOfPersons) {

        Set<Set<ConstraintViolation<Person>>> collect = groupOfPersons
                .stream()
                .map(eachPerson -> validator.validate(eachPerson))
                .collect(Collectors.toSet());

        if (!collect.isEmpty()) {
            return ResponseEntity
                    .badRequest().build();

        }
        return ResponseEntity.ok().build();
    }
}
