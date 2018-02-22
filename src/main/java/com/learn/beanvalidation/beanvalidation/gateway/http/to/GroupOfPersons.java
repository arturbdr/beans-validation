package com.learn.beanvalidation.beanvalidation.gateway.http.to;

import lombok.Builder;
import lombok.Data;

import java.util.Collection;

@Data
@Builder
public class GroupOfPersons {

    private Collection<Person> personCollections;
}
