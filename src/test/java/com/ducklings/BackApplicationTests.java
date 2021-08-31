package com.ducklings;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultHandler;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import javax.crypto.SecretKey;
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
    void generateSecretKey() {
        SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
        String base64Key = Encoders.BASE64.encode(key.getEncoded());
        System.out.println(base64Key);
    }

    @Test
    void whenGetAllCredentials_returnArrayOfBranches() throws Exception {
        this.mockMvc.perform(get("http://localhost:8082/v1/co2/all"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[?(@.id==\"db127d6b-f2f3-4115-b20a-5b5f67c384aa\")].name")
                        .value("Aviation industry"))
                .andExpect(jsonPath("$[?(@.id==\"db127d6b-f2f3-4115-b20a-5b5f67c384aa\")].year")
                        .value(2021))
                .andExpect(jsonPath("$[?(@.id==\"db127d6b-f2f3-4115-b20a-5b5f67c384aa\")].ejection")
                        .value(122.2));
    }

    @Test
    void whenGetSummary_returnArrayOfEjectionSums() throws Exception {
        this.mockMvc.perform(get("http://localhost:8082/v1/co2/summary"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[?(@.year==\"2021\")].num")
                        .value(233.3));
    }

    @Test
    void whenGetDistribution() throws Exception {
        this.mockMvc.perform(get("http://localhost:8082/v1/co2/distribution"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[?(@.name==\"Automotive industry\")].num")
                        .value(0.4762108872696099));
    }

    @Test
    void whenGetDiffs() throws Exception {
        this.mockMvc.perform(get("http://localhost:8082/v1/co2/diffs"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[?(@.year==\"2021\")].num")
                        .value(0.6261406333870102));
    }

    @Test
    void whenGetPeaks() throws Exception {
        this.mockMvc.perform(get("http://localhost:8082/v1/co2/peaks"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[?(@.year==\"2021\")].name")
                        .value("Aviation industry"));
    }
    //todo: more tests
}
