package nextstep.domain;

public class Reservation2 {

    private long id;
    private long scheduleId;
    private String name;

    public Reservation2() {
    }

    public Reservation2(long id, long scheduleId, String name) {
        this.id = id;
        this.scheduleId = scheduleId;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public long getScheduleId() {
        return scheduleId;
    }

    public String getName() {
        return name;
    }
}
