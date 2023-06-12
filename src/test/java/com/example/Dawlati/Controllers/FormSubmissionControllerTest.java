package com.example.Dawlati.Controllers;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import com.example.Dawlati.controllers.FormSubmissionController;
import com.example.Dawlati.models.*;
import com.example.Dawlati.services.FormSubmissionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(controllers = FormSubmissionController.class)
public class FormSubmissionControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private FormSubmissionService formSubmissionService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(username = "johndoe@gmail.com", password = "password")
    void getHistoryReturns200() throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        List<FormSubmission> formSubmissions = new ArrayList<>();
        Form form = new Form(1, "Civil Extract");
        FormSubmission formSubmission = new FormSubmission("data", Status.PENDING,
                LocalDate.now(), form, null);
        formSubmissions.add(formSubmission);
        when(formSubmissionService.getHistory(1, authentication)).thenReturn(formSubmissions);
        this.mvc.perform(get("http://localhost:8090/api/v1/formsubmission/history/{id}", 1))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].status").value("PENDING"));
        verify(formSubmissionService).getHistory(1, authentication);
    }

    @Test
    @WithMockUser(username = "johndoe@gmail.com", password = "password")
    void getSavedExtractReturns200() throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        when(formSubmissionService.getSavedExtract(1, authentication)).thenReturn("data");
        this.mvc.perform(get("http://localhost:8090/api/v1/formsubmission/getSavedData/{id}",
                        1))
                .andExpect(content().string(containsString("data")))
                .andExpect(status().isOk()
                );
        verify(formSubmissionService).getSavedExtract(1, authentication);
    }

    @Test
    @WithMockUser(username = "johndoe@gmail.com", password = "password")
    void auditPrintShouldReturn200() throws Exception {
        String formName = "Civil Extract";
        String json = this.objectMapper.writeValueAsString(formName);
        this.mvc.perform(post("http://localhost:8090/api/v1/formsubmission/formPrinted")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andDo(print());
        verify(formSubmissionService).printForm();
    }

    @Test
    @WithMockUser(username = "johndoe@gmail.com", password = "password")
    void saveExtractShouldReturn201() throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Form form = new Form(1, "Civil Extract");
        FormSubmission formSubmission = new FormSubmission("data", Status.PENDING,
                LocalDate.now(), form, null);
        String json = this.objectMapper.writeValueAsString(formSubmission);
        when(formSubmissionService.saveForm(formSubmission, authentication)).thenReturn(formSubmission);

        this.mvc.perform(post("http://localhost:8090/api/v1/formsubmission/save")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("status").value("PENDING"))
                .andExpect(jsonPath("formData").value("data"))
                .andExpect(status().isCreated());

        verify(formSubmissionService).saveForm(formSubmission, authentication);
    }

    @Test
    @WithMockUser(username = "johndoe@gmail.com", password = "password")
    void submitExtractShouldReturn201() throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = null;
        Form form = new Form(1, "Civil Extract");
        FormSubmission formSubmission = new FormSubmission("data", Status.SUBMITTED, LocalDate.now(), form, user);
        when(formSubmissionService.submitForm(formSubmission, authentication)).thenReturn(formSubmission);
        String json = this.objectMapper.writeValueAsString(formSubmission);

        this.mvc.perform(post("http://localhost:8090/api/v1/formsubmission/submit")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        )
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("formData").value("data"))
                .andExpect(jsonPath("status").value("SUBMITTED"))
                .andExpect(status().isCreated());
        verify(formSubmissionService).submitForm(formSubmission, authentication);

    }


    @Test
    @WithMockUser(username = "johndoe@gmail.com", password = "password")
    void deleteExtractCallsDeleteExtract() throws Exception {
        this.mvc.perform(delete("http://localhost:8090/api/v1/formsubmission/deleteExtract/4", 4)
                .with(csrf())
        );
        verify(formSubmissionService).deleteExtract(4);
    }



}
