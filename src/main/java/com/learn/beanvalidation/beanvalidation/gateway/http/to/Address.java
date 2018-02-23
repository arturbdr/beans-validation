package com.learn.beanvalidation.beanvalidation.gateway.http.to;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

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
