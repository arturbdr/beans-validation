package com.learn.beanvalidation.beanvalidation.gateway.http.to;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Arrays;
import java.util.Set;

import static org.assertj.core.api.BDDAssertions.then;

class GroupOfPersonsTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void shouldValidateNestedInvalidPersonName() {
        Person person1 = Person.builder()
                .name("Sample name")
                .age(10)
                .address(Address.builder().streetName("Sample Name").build())
                .build();

        Person person2 = Person.builder()
                .age(10)
                .address(Address.builder().streetName("Sample Name").build())
                .build();

        GroupOfPersons build = GroupOfPersons.builder()
                .personCollections(Arrays.asList(person1, person2))
                .build();

        Set<ConstraintViolation<GroupOfPersons>> validate = validator.validate(build);
        then(validate).hasSize(1);
        then(validate.stream().findFirst().get().getMessage()).isEqualTo("Name cant be null");
    }

    @Test
    void shouldCreateSuccessfullyAGroupOfPerson() {
        Person person1 = Person.builder()
                .name("Sample name")
                .age(10)
                .address(Address.builder().streetName("Sample Name").build())
                .build();

        Person person2 = Person.builder()
                .name("Another person name")
                .age(10)
                .address(Address.builder().streetName("Sample Name").build())
                .build();

        GroupOfPersons build = GroupOfPersons.builder()
                .personCollections(Arrays.asList(person1, person2))
                .build();

        Set<ConstraintViolation<GroupOfPersons>> validate = validator.validate(build);
        then(validate).hasSize(0);
    }

    @Test
    void shouldValidateGroupOfPerson() {
        Person person1 = Person.builder()
                .name("Sample name")
                .age(-1)
                .address(Address.builder().streetName("Sample Name").build())
                .build();

        Person person2 = Person.builder()
                .name("Another person name")
                .age(10)
                .address(Address.builder().streetName("Sample Name").build())
                .build();

        GroupOfPersons build = GroupOfPersons.builder()
                .personCollections(Arrays.asList(person1, person2))
                .build();

        Set<ConstraintViolation<GroupOfPersons>> validate = validator.validate(build);
        then(validate).hasSize(1);
        then(validate.stream().findFirst().get().getMessage()).isEqualTo("The minimum value allowed is 0");
    }

    @Test
    void shouldValidateGroupOfPersonWithTwoInvalidPersons() {
        Person person1 = Person.builder()
                .name("Sample name")
                .age(-1)
                .address(Address.builder().streetName("Sample Name").build())
                .build();

        Person person2 = Person.builder()
                .name("Another person name")
                .age(-5)
                .address(Address.builder().streetName("Sample Name").build())
                .build();

        GroupOfPersons build = GroupOfPersons.builder()
                .personCollections(Arrays.asList(person1, person2))
                .build();

        Set<ConstraintViolation<GroupOfPersons>> validate = validator.validate(build);
        then(validate).hasSize(2);
        then(validate.stream().findFirst().get().getMessage()).isEqualTo("The minimum value allowed is 0");
    }
}
