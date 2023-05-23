package com.learn.beanvalidation.beanvalidation.gateway.http.to;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person {

    @NotBlank(message = "Name cant be null")
    private String name;

    @Min(value = 0, message = "The minimum value allowed is 0")
    @Max(value = 100, message = "The maximum value allowed is 100", groups = CompanyPerson.class)
    private Integer age;

    @Valid
    @NotNull
    private Address address;

    public interface CompanyPerson {
    }

}
