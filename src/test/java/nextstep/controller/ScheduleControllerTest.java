package nextstep.controller;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import nextstep.domain.Schedule;
import nextstep.dto.ScheduleRequest;
import nextstep.dto.ThemeRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ScheduleControllerTest {

    private final ThemeRequest defaultThemeRequest = new ThemeRequest("테마", "설명", 10000);
    private final LocalTime time = LocalTime.parse("13:00");

    @LocalServerPort
    int port;

    private long themeId;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        themeId = Long.parseLong(createTheme(defaultThemeRequest).header("location").split("/")[2]);
    }

    @Test
    @DisplayName("테마 id 와 날짜, 시간을 이용해 스케줄을 저장한다.")
    void save() {
        LocalDate date = LocalDate.parse("2023-08-11");
        ExtractableResponse<Response> response = createSchedule(
            new ScheduleRequest(themeId, date, time));

        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    @DisplayName("테마 id 와 날짜를 이용해 스케줄을 조회한다.")
    void findSchedulesByThemeIdAndDate() {
        LocalDate date = LocalDate.parse("2023-08-12");
        createSchedule(new ScheduleRequest(themeId, date, time));

        List<Schedule> schedules = findSchedules(date);

        assertThat(schedules).hasSizeGreaterThanOrEqualTo(1);
    }

    @Test
    @DisplayName("id 를 이용해 스케줄을 삭제한다.")
    void deleteById() {
        LocalDate date = LocalDate.parse("2023-08-13");
        ExtractableResponse<Response> createResponse = createSchedule(
            new ScheduleRequest(themeId, date, time));
        String id = createResponse.header("location").split("/")[2];
        int expected = findSchedules(date).size() - 1;

        ExtractableResponse<Response> deleteResponse = deleteSchedule(id);
        assertThat(deleteResponse.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());

        assertThat(findSchedules(date)).hasSize(expected);
    }

    private ExtractableResponse<Response> createTheme(ThemeRequest themeRequest) {
        return RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(themeRequest)
            .when().post("/themes")
            .then().log().all()
            .extract();
    }

    private ExtractableResponse<Response> createSchedule(ScheduleRequest scheduleRequest) {
        return RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(scheduleRequest)
            .when().post("/schedules")
            .then().log().all()
            .extract();
    }

    private List<Schedule> findSchedules(LocalDate date) {
        return RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .param("themeId", themeId)
            .param("date", date.toString())
            .when().get("/schedules")
            .then().log().all()
            .extract().body().as(new TypeRef<>() {
            });
    }

    private ExtractableResponse<Response> deleteSchedule(String id) {
        return RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when().delete("/schedules/" + id)
            .then().log().all()
            .extract();
    }
}
