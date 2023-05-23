package com.learn.beanvalidation.beanvalidation.gateway.http.to;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {

    @NotEmpty(message = "Street name cant be empty")
    private String streetName;

    @NotEmpty(groups = AddressUSA.class, message = "USA Addresses require zipcode")
    private String zipCode;

    public interface AddressUSA {
    }
}
