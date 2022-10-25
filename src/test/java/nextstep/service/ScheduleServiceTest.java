package nextstep.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import nextstep.domain.Schedule;
import nextstep.domain.Theme;
import nextstep.repository.ThemeH2Repository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ScheduleServiceTest {

    @Autowired
    private ScheduleService scheduleService;
    
    @Autowired
    private ThemeH2Repository themeH2Repository;

    private long themeId;
    final LocalTime time = LocalTime.parse("16:00");

    @BeforeEach
    void setup() {
        themeId = themeH2Repository.save("테마", "설명", 10000);
    }

    @Test
    @DisplayName("테마 id 와 날짜, 시간을 이용해 스케줄을 저장한다.")
    void save() {
        final long id = scheduleService.save(themeId, LocalDate.parse("2022-08-10"), time);

        assertThat(id).isNotZero();
    }

    @Test
    @DisplayName("테마 id 와 날짜를 이용해 스케줄을 조회한다.")
    void findSchedulesByThemeIdAndDate() {
        final LocalDate date = LocalDate.parse("2022-08-11");
        scheduleService.save(themeId, date, time);
        scheduleService.save(themeId, date, LocalTime.parse("15:00"));

        final List<Schedule> schedulesByThemeIdAndDate = scheduleService
            .findSchedulesByThemeIdAndDate(themeId, date);

        assertThat(schedulesByThemeIdAndDate).hasSizeGreaterThanOrEqualTo(2);
    }

    @Test
    @DisplayName("스케줄 id 를 이용해 스케줄을 삭제한다.")
    void deleteById() {
        final LocalDate date = LocalDate.parse("2022-08-12");
        final long scheduleId = scheduleService.save(themeId, date, time);
        final Theme theme = new Theme(themeId, "테마", "설명", 10000);
        final Schedule expected = new Schedule(scheduleId, theme, date, time);

        assertThat(scheduleService
            .findSchedulesByThemeIdAndDate(themeId, date)).containsExactly(expected);

        scheduleService.deleteById(scheduleId);

        final List<Schedule> schedules = scheduleService
            .findSchedulesByThemeIdAndDate(themeId, date);

        assertThat(schedules).doesNotContain(expected);
    }
}
