package com.learn.beanvalidation.beanvalidation.gateway.http.to;


import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class LombokAnnotations {
    String name;
    String email;
    String password;
    String confirmPassword;
    String phone;
    String address;
    String city;
    String state;
    String country;
    String zip;
}


