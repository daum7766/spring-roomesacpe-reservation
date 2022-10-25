package nextstep.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import nextstep.domain.Reservation;
import nextstep.domain.Reservation2;

public interface ReservationRepository {

    List<Reservation> findReservationsByDate(LocalDate date);

    Optional<Reservation2> findByScheduleId(long scheduleId);

    long save(LocalDate date, LocalTime time, String name);

    long save2(long scheduleId, String name);

    int deleteByLocalDateAndLocalTime(LocalDate date, LocalTime time);

    boolean existReservationByDateAndTime(LocalDate date, LocalTime time);
}
