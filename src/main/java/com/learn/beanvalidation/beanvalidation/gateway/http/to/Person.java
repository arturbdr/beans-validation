package com.learn.beanvalidation.beanvalidation.gateway.http.to;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person {

    @NotEmpty(message = "Name cant be null")
    private String name;

    @Min(value = 0, message = "The minimum value allowed is 0")
    @Max(value = 100, message = "The maximum value allowed is 100", groups = CompanyPerson.class)
    private Integer age;

    @Valid
    @NotNull
    private Address address;

    interface CompanyPerson {}

}
