package nextstep.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class Schedule {

    private long id;
    private Theme theme;
    private LocalDate localDate;
    private LocalTime localTime;

    public Schedule() {
    }

    public Schedule(long id, Theme theme, LocalDate localDate, LocalTime localTime) {
        this.id = id;
        this.theme = theme;
        this.localDate = localDate;
        this.localTime = localTime;
    }

    public long getId() {
        return id;
    }

    public Theme getTheme() {
        return theme;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public LocalTime getLocalTime() {
        return localTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Schedule schedule = (Schedule) o;
        return id == schedule.id && Objects.equals(theme, schedule.theme) && Objects
            .equals(localDate, schedule.localDate) && Objects
            .equals(localTime, schedule.localTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, theme, localDate, localTime);
    }
}
