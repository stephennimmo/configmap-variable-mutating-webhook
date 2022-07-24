package com.snimmo.kube.cvmw;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class MutateResourceTest {

    private static final Logger log = LoggerFactory.getLogger(MutateResourceTest.class);

    @Test
    public void replaceYaml() {
        String result = given()
                .when()
                .contentType(ContentType.JSON)
                .body(this.getClass().getResourceAsStream("/replace.yaml"))
                .post("/mutate")
                .then()
                .statusCode(200)
                .extract().asPrettyString();
        log.info("{}", result);
    }

}