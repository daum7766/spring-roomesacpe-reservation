package nextstep.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import nextstep.domain.Reservation;
import org.springframework.stereotype.Repository;

@Repository
public class ReservationInMemoryRepository implements ReservationRepository {

    private final Map<LocalDate, Map<LocalTime, Reservation>> dataBase = new ConcurrentHashMap<>();
    private final AtomicLong id = new AtomicLong(1L);

    @Override
    public long save(LocalDate date, LocalTime time, String name) {
        Map<LocalTime, Reservation> localTimeReservationMap = dataBase
            .computeIfAbsent(date, k -> new ConcurrentHashMap<>());
        Reservation reservation = localTimeReservationMap
            .computeIfAbsent(time, k -> new Reservation(id.getAndIncrement(), date, time, name));
        return reservation.getId();
    }

    @Override
    public List<Reservation> findReservationsByDate(LocalDate date) {
        Map<LocalTime, Reservation> localTimeReservationMap = dataBase
            .computeIfAbsent(date, k -> new ConcurrentHashMap<>());
        return new ArrayList<>(localTimeReservationMap.values());
    }

    @Override
    public int deleteByLocalDateAndLocalTime(LocalDate date, LocalTime time) {
        Map<LocalTime, Reservation> localTimeReservationMap = dataBase
            .computeIfAbsent(date, k -> new ConcurrentHashMap<>());
        if (localTimeReservationMap.containsKey(time)) {
            localTimeReservationMap.remove(time);
            return 1;
        }
        return 0;
    }

    @Override
    public boolean existReservationByDateAndTime(LocalDate date, LocalTime time) {
        Map<LocalTime, Reservation> localTimeReservationMap = dataBase
            .computeIfAbsent(date, k -> new ConcurrentHashMap<>());
        return localTimeReservationMap.containsKey(time);
    }
}
