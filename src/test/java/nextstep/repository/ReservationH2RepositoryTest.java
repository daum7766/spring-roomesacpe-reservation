package nextstep.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import nextstep.domain.Reservation;
import nextstep.dto.ReservationRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ReservationH2RepositoryTest {

    @Autowired
    private ReservationH2Repository reservationH2Repository;

    @Test
    @DisplayName("날짜와 시간, 이름을 넣어 Reservation 을 저장한다.")
    void save() {
        ReservationRequest request = createRequest(LocalDate.parse("2022-08-10"));
        long id = reservationH2Repository
            .save(request.getDate(), request.getTime(), request.getName());

        assertThat(id).isNotZero();
    }

    @Test
    @DisplayName("날짜를 이용해 Reservations 를 조회한다.")
    void findReservationsByDate() {
        LocalDate date = LocalDate.parse("2022-08-12");
        ReservationRequest request = createRequest(date);
        long id = reservationH2Repository
            .save(request.getDate(), request.getTime(), request.getName());
        final Reservation expected = new Reservation(id, request.getDate(), request.getTime(),
            request.getName());

        List<Reservation> reservations = reservationH2Repository
            .findReservationsByDate(date);

        assertThat(reservations).hasSize(1);
        assertThat(reservations.get(0)).usingRecursiveComparison()
            .isEqualTo(expected);
    }

    @Test
    @DisplayName("날짜와 시간을 넣어 Reservation 을 삭제한다.")
    void deleteByLocalDateAndLocalTime01() {
        LocalDate date = LocalDate.parse("2022-08-13");
        ReservationRequest request = createRequest(date);
        reservationH2Repository.save(request.getDate(), request.getTime(), request.getName());

        assertThat(reservationH2Repository.findReservationsByDate(date)).hasSize(1);
        int count = reservationH2Repository
            .deleteByLocalDateAndLocalTime(date, LocalTime.parse("13:00"));
        assertThat(count).isEqualTo(1);
        assertThat(reservationH2Repository.findReservationsByDate(date)).isEmpty();
    }

    @Test
    @DisplayName("존재하지 않는 데이터를 삭제하면 0을 반환한다.")
    void deleteByLocalDateAndLocalTime02() {
        LocalDate date = LocalDate.parse("2022-08-13");
        int count = reservationH2Repository
            .deleteByLocalDateAndLocalTime(date, LocalTime.parse("13:00"));
        assertThat(count).isZero();
    }

    private ReservationRequest createRequest(LocalDate date) {
        final LocalTime time = LocalTime.parse("13:00");
        final String name = "mungto";
        return new ReservationRequest(date, time, name);
    }
}
