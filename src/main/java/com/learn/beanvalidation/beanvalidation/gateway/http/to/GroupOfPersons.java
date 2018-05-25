package com.learn.beanvalidation.beanvalidation.gateway.http.to;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import java.util.Collection;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroupOfPersons {

    @Valid
    private Collection<Person> personCollections;
}
