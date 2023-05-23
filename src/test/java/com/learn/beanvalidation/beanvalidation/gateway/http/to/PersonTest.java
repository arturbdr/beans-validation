package com.learn.beanvalidation.beanvalidation.gateway.http.to;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Set;

import static org.assertj.core.api.BDDAssertions.then;

@ExtendWith(SpringExtension.class)
class PersonTest {

    private static Validator validator;

    @BeforeEach
    void setUp() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        } catch (Exception e) {
            // Handle any exceptions
        }
    }

    @Test
    void shouldCheckInvalidPersonName() {
        Person invalidPersonName = Person.builder()
                .address(Address.builder().streetName("Teste").build())
                .name("")
                .build();

        Set<ConstraintViolation<Person>> validate = validator.validate(invalidPersonName);
        then(validate).hasSize(1);
        then(validate.stream().findFirst().get().getMessage()).isEqualTo("Name cant be null");
    }

    @Test
    void shouldCheckInvalidPersonMinAge() {
        Person invalidPersonName = Person.builder()
                .address(Address.builder().streetName("Teste").build())
                .name("Any")
                .age(-1)
                .build();
        Set<ConstraintViolation<Person>> validate = validator.validate(invalidPersonName);
        then(validate).hasSize(1);
        then(validate.stream().findFirst().get().getMessage()).isEqualTo("The minimum value allowed is 0");
    }

    @Test
    void shouldCheckGroupdValidationForInvalidPersonMaxAge() {
        Person invalidPersonName = Person.builder()
                .name("Any")
                .age(101)
                .build();
        Set<ConstraintViolation<Person>> validate = validator.validate(invalidPersonName, Person.CompanyPerson.class);
        then(validate).hasSize(1);
        then(validate.stream().findFirst().get().getMessage()).isEqualTo("The maximum value allowed is 100");
    }

    @Test
    void shouldCheckForInvalidAddress() {
        Person invalidPersonName = Person.builder()
                .address(Address.builder().streetName("").build())
                .build();
        Set<ConstraintViolation<Address>> validate = validator.validate(invalidPersonName.getAddress());
        then(validate).hasSize(1);
        then(validate.stream().findFirst().get().getMessage()).isEqualTo("Street name cant be empty");
    }

    @Test
    void shouldCheckForInvalidNestedAddress() {
        Person invalidPersonName = Person.builder()
                .name("Sample name")
                .address(Address.builder().streetName("").build())
                .build();
        Set<ConstraintViolation<Person>> validate = validator.validate(invalidPersonName);
        then(validate).hasSize(1);
        then(validate.stream().findFirst().get().getMessage()).isEqualTo("Street name cant be empty");
    }

    @Test
    void shouldCheckForInvalidNestedAddressGroupValidation() {
        Person invalidPersonName = Person.builder()
                .name("Sample name")
                .address(Address.builder().streetName("Sample Name").build())
                .build();
        Set<ConstraintViolation<Person>> validate = validator.validate(invalidPersonName, Address.AddressUSA.class);
        then(validate).hasSize(1);
        then(validate.stream().findFirst().get().getMessage()).isEqualTo("USA Addresses require zipcode");
    }


}