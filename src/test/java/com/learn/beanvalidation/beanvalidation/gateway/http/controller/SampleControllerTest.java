package com.learn.beanvalidation.beanvalidation.gateway.http.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learn.beanvalidation.beanvalidation.gateway.http.to.GroupOfPersons;
import com.learn.beanvalidation.beanvalidation.gateway.http.to.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SampleControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void shouldReturnBadRequestDueToNullBody() throws Exception {
        ResultActions perform = mockMvc.perform(post("/"));
        perform.andExpect(status().is4xxClientError());
    }

    @Test
    public void shouldReturnBadRequestDueToEmptyPersonName() throws Exception {
        Person person = Person.builder()
                .age(10)
                .build();
        ResultActions perform = mockMvc.perform(post("/")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(objectMapper.writeValueAsString(person)));

        perform.andExpect(status().is4xxClientError());
        perform.andExpect(content().string(""));

    }


    @Test
    public void shouldReturnBadRequestDueToNullGroupOfPersonsBody() {
//        mockMvc.perform(post("/group"))

        GroupOfPersons groupOfPersons = null;
    }


}