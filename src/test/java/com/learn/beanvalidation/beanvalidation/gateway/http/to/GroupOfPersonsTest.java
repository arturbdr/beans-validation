package com.learn.beanvalidation.beanvalidation.gateway.http.to;

import org.assertj.core.api.BDDAssertions;
import org.junit.Before;
import org.junit.Test;

import javax.validation.*;
import java.util.Arrays;
import java.util.Set;

public class GroupOfPersonsTest {

    private Validator validator;

    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void shouldValidateNestedInvalidPersonName() {
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
        BDDAssertions.then(validate).hasSize(1);
        BDDAssertions.then(validate.stream().findFirst().get().getMessage()).isEqualTo("Name cant be null");
    }

    @Test
    public void shouldCreateSuccessfullyAGRoupOfPerson() {
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
        BDDAssertions.then(validate).hasSize(0);
    }
}
