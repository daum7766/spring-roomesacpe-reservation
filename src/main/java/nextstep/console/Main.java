package nextstep.console;

import nextstep.repository.ReservationInMemoryRepository;
import nextstep.repository.ReservationRepository;
import nextstep.service.ReservationService;

public class Main {

    public static void main(String[] args) {
        ReservationRepository reservationRepository = new ReservationInMemoryRepository();
        ReservationService reservationService = new ReservationService(reservationRepository);
        Console console = new Console(reservationService);

        console.execute();
    }
}
