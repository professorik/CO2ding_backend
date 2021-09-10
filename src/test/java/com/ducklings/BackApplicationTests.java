package com.ducklings;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
class BackApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void contextLoads() {
    }

    @Test
    void whenGetAllRegions_returnArrayOfRegions() throws Exception {
        this.mockMvc.perform(get("http://localhost:8080/v1/co2/distribution/regions"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.results.*").isArray())
                .andExpect(jsonPath("$.results.[1].id").value(2));
    }

    @Test
    void whenGetAllTypes_returnArrayOfDataTypes() throws Exception {
        this.mockMvc.perform(get("http://localhost:8080/v1/co2/distribution/dataTypes"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.results.*").isArray())
                .andExpect(jsonPath("$.results.[0].name").value("CO2"))
                .andExpect(jsonPath("$.results.[0].id").value(1));
    }

    @Test
    void whenGetSummary_returnArrayOfDistribution() throws Exception {
        this.mockMvc.perform(get("http://localhost:8080/v1/co2/distribution/summary?year=2019&region=1&dataType=2"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.dataType.id").value(2))
                .andExpect(jsonPath("$.region.id").value(1))
                .andExpect(jsonPath("$.results.*").isArray())
                .andExpect(jsonPath("$.results.[0].dateStart").value("2019-01-01"))
                .andExpect(jsonPath("$.results.[0].value").value("704.058"));
    }
}
