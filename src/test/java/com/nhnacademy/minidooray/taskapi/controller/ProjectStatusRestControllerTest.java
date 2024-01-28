package com.nhnacademy.minidooray.taskapi.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.nhnacademy.minidooray.taskapi.domain.ProjectStatusResponse;
import com.nhnacademy.minidooray.taskapi.service.ProjectStatusService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(ProjectStatusRestController.class)
class ProjectStatusRestControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private ProjectStatusService projectStatusService;

    @Test
    void getStatuses() throws Exception {
        given(projectStatusService.getProjectStatuses()).willReturn(List.of(new ProjectStatusResponse() {
            @Override
            public Long getStatusId() {
                return 1L;
            }

            @Override
            public String getStatusName() {
                return "name";
            }
        }));

        mockMvc.perform(get("/api/status"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].statusId", equalTo(1)))
                .andExpect(jsonPath("$[0].statusName", equalTo("name")));
    }
}