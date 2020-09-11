package pl.red.rocket.validation.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.nullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
public class RegistrationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void when_accessingForm_then_emptyFormIsReturned() throws Exception {
        this.mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk())
                .andExpect(view().name("registration"))
                .andExpect(content().string(containsString("name")))
                .andExpect(content().string(containsString("salary")))
                .andExpect(model().attribute("form", hasProperty("name", nullValue())))
                .andExpect(model().attribute("form", hasProperty("salary", nullValue())));
    }


    @Test
    public void given_validUser_when_sendingForm_thenRedirectedToSuccess() throws Exception {
        this.mockMvc.perform(
                post("/")
                        .param("name", "Jakub")
                        .param("salary", "100000")
        ).andDo(print())
                .andExpect(redirectedUrl("/success"));
    }

    @Test
    public void given_userWithShortName_when_sendingForm_thenError() throws Exception {
        this.mockMvc.perform(
                post("/")
                        .param("name", "J")
                        .param("salary", "100000")
        ).andDo(print())
                .andExpect(model().errorCount(1))
                .andExpect(model().attributeHasFieldErrorCode("form", "name", "Size"));
    }

    @Test
    public void given_userWithNoSalary_when_sendingForm_thenError() throws Exception {
        this.mockMvc.perform(
                post("/")
                        .param("name", "Jakub")
        ).andDo(print())
                .andExpect(model().errorCount(1))
                .andExpect(model().attributeHasFieldErrorCode("form", "salary", "NotNull"));
    }

    @Test
    public void given_userWithLowSalary_when_sendingForm_thenError() throws Exception {
        this.mockMvc.perform(
                post("/")
                        .param("name", "Jakub")
                        .param("salary", "1")
        ).andDo(print())
                .andExpect(model().errorCount(1))
                .andExpect(model().attributeHasFieldErrorCode("form", "salary", "Min"));
    }

    @Test
    public void given_noData_when_sendingForm_thenError() throws Exception {
        this.mockMvc.perform(
                post("/")
        ).andDo(print())
                .andExpect(model().errorCount(2))
                .andExpect(model().attributeHasFieldErrorCode("form", "salary", "NotNull"))
                .andExpect(model().attributeHasFieldErrorCode("form", "name", "NotEmpty"));
    }
}