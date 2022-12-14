package com.sinensia.wineshop;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/*
 * API tests using MockMvc
 */

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class WineControllerTestMvc {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void all() throws Exception {
        mockMvc.perform(get("/api/wine/")
                .contentType("application/json")
        );
    }

    @Test
    void one() throws Exception {
        mockMvc.perform(get("/api/wine/1")
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(jsonPath("$.id",is(1)))
                .andExpect(jsonPath("$.name",is("Tinto")));
    }

    @Test
    // very unnatural - we'd expect a 404 status response
    void notFound() throws Exception {
        assertThrows(Exception.class, ()->{
            mockMvc.perform(get("/api/wine/0")
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is5xxServerError());
        });
    }

    @Test
    // this is more like it
    void notFoundHAL() throws Exception {
        mockMvc.perform(get("/api/hal/wine/0")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound());
    }

    @Test
    void notFoundHAL2() throws Exception {
        mockMvc.perform(get("/api/hal2/wine/0")
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isNotFound());
    }

}
