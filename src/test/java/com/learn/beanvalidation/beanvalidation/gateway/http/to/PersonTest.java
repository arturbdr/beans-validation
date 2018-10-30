package com.learn.beanvalidation.beanvalidation.gateway.http.to;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.assertj.core.api.BDDAssertions.then;

@RunWith(SpringRunner.class)
public class PersonTest {

    private static Validator validator;

    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void shouldCheckInvalidPersonName() {
        Person invalidPersonName = Person.builder()
                .address(Address.builder().streetName("Teste").build())
                .name("")
                .build();

        Set<ConstraintViolation<Person>> validate = validator.validate(invalidPersonName);
        then(validate).hasSize(1);
        then(validate.stream().findFirst().get().getMessage()).isEqualTo("Name cant be null");
    }

    @Test
    public void shouldCheckInvalidPersonMinAge() {
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
    public void shouldCheckGroupdValidationForInvalidPersonMaxAge() {
        Person invalidPersonName = Person.builder()
                .name("Any")
                .age(101)
                .build();
        Set<ConstraintViolation<Person>> validate = validator.validate(invalidPersonName, Person.CompanyPerson.class);
        then(validate).hasSize(1);
        then(validate.stream().findFirst().get().getMessage()).isEqualTo("The maximum value allowed is 100");
    }

    @Test
    public void shouldCheckForInvalidAddress() {
        Person invalidPersonName = Person.builder()
                .address(Address.builder().streetName("").build())
                .build();
        Set<ConstraintViolation<Address>> validate = validator.validate(invalidPersonName.getAddress());
        then(validate).hasSize(1);
        then(validate.stream().findFirst().get().getMessage()).isEqualTo("Street name cant be empty");
    }

    @Test
    public void shouldCheckForInvalidNestedAddress() {
        Person invalidPersonName = Person.builder()
                .name("Sample name")
                .address(Address.builder().streetName("").build())
                .build();
        Set<ConstraintViolation<Person>> validate = validator.validate(invalidPersonName);
        then(validate).hasSize(1);
        then(validate.stream().findFirst().get().getMessage()).isEqualTo("Street name cant be empty");
    }

    @Test
    public void shouldCheckForInvalidNestedAddressGroupValidation() {
        Person invalidPersonName = Person.builder()
                .name("Sample name")
                .address(Address.builder().streetName("Sample Name").build())
                .build();
        Set<ConstraintViolation<Person>> validate = validator.validate(invalidPersonName, Address.AddressUSA.class);
        then(validate).hasSize(1);
        then(validate.stream().findFirst().get().getMessage()).isEqualTo("USA Addresses require zipcode");
    }


}