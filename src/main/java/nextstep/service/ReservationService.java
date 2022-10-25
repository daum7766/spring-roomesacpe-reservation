package nextstep.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import nextstep.domain.Reservation;
import nextstep.exception.ReservationCreateException;
import nextstep.exception.ReservationDeleteException;
import nextstep.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ReservationService {

    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationService(
        @Qualifier("reservationH2Repository") ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    // 예약 생성
    @Transactional
    public long save(LocalDate date, LocalTime time, String name) {
        if (reservationRepository.existReservationByDateAndTime(date, time)) {
            throw new ReservationCreateException("해당 시간에 예약이 있습니다.");
        }
        return reservationRepository.save(date, time, name);
    }

    // 예약 조회
    @Transactional
    public List<Reservation> findReservationsByDate(LocalDate date) {
        return reservationRepository.findReservationsByDate(date);
    }

    // 예약 취소
    @Transactional
    public void deleteByLocalDateAndLocalTime(LocalDate date, LocalTime time) {
        int deleteCount = reservationRepository.deleteByLocalDateAndLocalTime(date, time);
        if (deleteCount == 0) {
            throw new ReservationDeleteException("존재하지 않는 예약입니다.");
        }
    }
}
