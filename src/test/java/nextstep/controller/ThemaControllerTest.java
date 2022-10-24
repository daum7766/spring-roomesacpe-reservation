package nextstep.controller;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import nextstep.domain.Thema;
import nextstep.dto.ThemaRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ThemaControllerTest {

    private final ThemaRequest themaRequest = new ThemaRequest("테마", "설명", 10000);

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    @DisplayName("이름과 설명, 가격을 이용해 테마를 저장한다.")
    void save() {
        ExtractableResponse<Response> response = createThema(themaRequest);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    @DisplayName("모든 테마의 정보를 가져온다.")
    void findAll() {
        createThema(themaRequest);
        createThema(themaRequest);

        List<Thema> themes = findAllThema();

        assertThat(themes).hasSizeGreaterThanOrEqualTo(2);
    }

    @Test
    @DisplayName("id 를 이용해 테마를 삭제한다.")
    void deleteById() {
        ExtractableResponse<Response> response = createThema(themaRequest);
        String id = response.header("location").split("/")[2];
        deleteThema(id);

        Thema thema = themaRequest.toThema(Long.parseLong(id));
        assertThat(findAllThema()).doesNotContain(thema);
    }

    private ExtractableResponse<Response> createThema(ThemaRequest themaRequest) {
        return RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(themaRequest)
            .when().post("/themes")
            .then().log().all()
            .extract();
    }

    private List<Thema> findAllThema() {
        return RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when().get("/themes")
            .then().log().all()
            .extract().body().as(new TypeRef<>() {
            });
    }

    private ExtractableResponse<Response> deleteThema(String id) {
        return RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when().delete("/themes/" + id)
            .then().log().all()
            .extract();
    }
}
