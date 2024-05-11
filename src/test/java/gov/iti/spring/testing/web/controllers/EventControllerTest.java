package gov.iti.spring.testing.web.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import gov.iti.spring.testing.config.WebSecurityConfig;
import gov.iti.spring.testing.domain.Event;
import gov.iti.spring.testing.service.EventService;
import org.junit.jupiter.api.Test;

//import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import org.assertj.core.api.Assertions;
import java.util.List;



@WebMvcTest(EventController.class)
@Import(WebSecurityConfig.class)
class EventControllerTest {

    private EventController eventController;

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private EventService eventServiceMock;

    @Autowired
    private ObjectMapper objectMapper;
    @Test
    void getAllEvents()throws Exception {
        when(eventServiceMock.getAll()).thenReturn(List.of(new Event(), new Event()));
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/events")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        List<Event> events = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>(){});

        Assertions.assertThat(events.size()).isEqualTo(2);
    }
}