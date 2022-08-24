package com.sinensia.wineshop;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

/*
 * API tests using WebFlux WebTestClient
 * We must add the spring-boot-starter-webflux dependency for this to work
 */

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class WineControllerTest {
    @Autowired
    private WebTestClient webTestClient;

    @Test
    void all() {
        webTestClient.get()
                .uri("/api/wine/")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-Type", "application/json");
    }

    @Test
    void one() {
        webTestClient.get()
                .uri("/api/wine/1")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-Type", "application/json")
                .expectBody()
                .jsonPath("$.id").isEqualTo(1)
                .jsonPath("$.name").isEqualTo("Tinto");
    }

    @Test
    void notFound() {
        webTestClient.get()
                .uri("/api/wine/0")
                .exchange()
                .expectStatus().is5xxServerError();
    }

    @Test
    void notFoundHAL() {
        webTestClient.get()
                .uri("/api/hal/wine/0")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void notFoundHAL2() {
        webTestClient.get()
                .uri("/api/hal2/wine/0")
                .exchange()
                .expectStatus().isNotFound();
    }

}
