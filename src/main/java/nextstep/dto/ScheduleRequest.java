package nextstep.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class ScheduleRequest {

    private long themeId;
    private LocalDate date;
    private LocalTime time;

    public ScheduleRequest() {
    }

    public ScheduleRequest(long themeId, LocalDate date, LocalTime time) {
        this.themeId = themeId;
        this.date = date;
        this.time = time;
    }

    public long getThemeId() {
        return themeId;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }
}
