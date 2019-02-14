package com.learn.beanvalidation.beanvalidation.gateway.http.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learn.beanvalidation.beanvalidation.gateway.http.to.GroupOfPersons;
import com.learn.beanvalidation.beanvalidation.gateway.http.to.Person;
import org.assertj.core.api.BDDAssertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;
import java.util.Collection;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SampleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void shouldReturnBadRequestDueToNullBody() throws Exception {
        mockMvc.perform(
                post("/"))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void shouldReturnBadRequestDueToEmptyPersonName() throws Exception {
        Person person = Person.builder()
                .age(10)
                .build();

        MvcResult mvcResult = mockMvc.perform(
                post("/")
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .content(objectMapper.writeValueAsString(person)))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        BDDAssertions.then(actualResponseBody).contains("\"fieldName\":\"name\",\"inputValue\":null,\"rejectReason\":\"Name cant be null\"");
    }


    @Test
    public void shouldReturnBadRequestDueToNullGroupOfPersonsBody() throws Exception {
        mockMvc.perform(
                post("/group")
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void shouldReturnBadRequestDueToNullPersonNameInGroup() throws Exception {
        Person person1 = Person.builder()
                .age(10)
                .build();

        Person person2 = Person.builder()
                .age(10)
                .build();

        GroupOfPersons groupOfPersons = GroupOfPersons.builder()
                .personCollections(Arrays.asList(person1, person2))
                .build();

        ResultActions perform = mockMvc.perform(
                post("/group")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(objectMapper.writeValueAsString(groupOfPersons)));

        perform.andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnBadRequestDueToInvalidCollectionOfItens() throws Exception {
        Person person1 = Person.builder()
                .age(10)
                .build();

        Person person2 = Person.builder()
                .age(10)
                .build();

        Collection<Person> collectionOfPerson = Arrays.asList(person1, person2);

        mockMvc.perform(
                post("/groupwithcollection")
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .content(objectMapper.writeValueAsString(collectionOfPerson)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void shouldReturnBadRequestDueToNullContent() throws Exception {

        mockMvc.perform(
                post("/groupwithcollection")
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().is4xxClientError());
    }

}