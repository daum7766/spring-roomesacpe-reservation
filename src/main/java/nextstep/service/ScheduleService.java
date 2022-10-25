package nextstep.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import nextstep.domain.Schedule;
import nextstep.repository.ScheduleH2Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ScheduleService {

    private final ScheduleH2Repository scheduleH2Repository;

    public ScheduleService(ScheduleH2Repository scheduleH2Repository) {
        this.scheduleH2Repository = scheduleH2Repository;
    }

    @Transactional
    public long save(long themeId, LocalDate date, LocalTime time) {
        return scheduleH2Repository.save(themeId, date, time);
    }

    public List<Schedule> findSchedulesByThemeIdAndDate(long themeId, LocalDate localDate) {
        return scheduleH2Repository.findSchedulesByThemeIdAndDate(themeId, localDate);
    }

    public void deleteById(long id) {
        scheduleH2Repository.deleteById(id);
    }
}
