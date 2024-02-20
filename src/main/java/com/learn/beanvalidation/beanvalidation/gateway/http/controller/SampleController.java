package com.learn.beanvalidation.beanvalidation.gateway.http.controller;

import com.learn.beanvalidation.beanvalidation.gateway.http.to.GroupOfPersons;
import com.learn.beanvalidation.beanvalidation.gateway.http.to.LombokAnnotations;
import com.learn.beanvalidation.beanvalidation.gateway.http.to.Person;
import com.learn.beanvalidation.beanvalidation.gateway.http.to.SimpleReturn;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@Slf4j
public class SampleController {


    /**
     * Testing the CORS using the browser:
     * <p>
     * fetch("https://XXXX/corsEnabled", {
     * "headers" : {
     * 'Access-Control-Allow-Origin': 'x-requested-with '
     * },
     * mode: 'no-cors'
     * }).
     * then(res => res.json())
     * .then(console.log)
     * <p>
     * <p>
     * <p>
     * fetch("https://XXXX/corsDisabled", {
     * "headers" : {
     * 'Access-Control-Allow-Origin': 'x-requested-with '
     * },
     * mode: 'cors'
     * }).
     * then(res => res.json())
     * .then(console.log)
     */

    private final Validator validator;

    @GetMapping(value = "/corsEnabled", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<SimpleReturn> helloBeansValidationWithCorsEnabled() {
        return ResponseEntity.ok(SimpleReturn.builder().message("Cors is Enabled here").build());
    }

    @GetMapping(value = "/corsDisabled", produces = APPLICATION_JSON_VALUE)
    @CrossOrigin
    public ResponseEntity<SimpleReturn> helloBeansValidationWithCorsDisabled() {
        return ResponseEntity.ok(SimpleReturn.builder().message("Cors is DISABLED here").build());
    }

    @PostMapping(value = "/", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity validateMyBean(@RequestBody @Valid @NotNull Person person) {
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "group", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity validateMyGroupBean(@RequestBody @Valid @NotNull final GroupOfPersons groupOfPersons) {
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "groupwithcollection", consumes = APPLICATION_JSON_VALUE)
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

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleIllegalArgumentException(Exception ex) {
        log.error("Illegal argument error", ex);
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @GetMapping
    public ResponseEntity<String> justTest() {
        LombokAnnotations instance = LombokAnnotations.builder()
                .name("some name")
                .email("some email")
                .password("some password")
                .confirmPassword("some password")
                .phone("some phone")
                .address("some address")
                .city("some city")
                .build();
        log.info(instance.toString());
        return ResponseEntity.ok().build();
    }

}
