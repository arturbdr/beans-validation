package com.learn.beanvalidation.beanvalidation.gateway.http.to;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person {

    @NotEmpty(message = "Name cant be null")
    private String name;

    @Min(0)
    @Max(100)
    private Integer age;
}
