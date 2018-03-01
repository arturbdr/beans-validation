package com.learn.beanvalidation.beanvalidation.gateway.http.to;

import lombok.Builder;
import lombok.Data;

import javax.validation.Valid;
import java.util.Collection;

@Data
@Builder
public class GroupOfPersons {

    @Valid
    private Collection<Person> personCollections;
}
