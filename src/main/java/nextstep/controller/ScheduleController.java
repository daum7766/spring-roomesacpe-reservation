package nextstep.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;
import nextstep.domain.Schedule;
import nextstep.dto.ScheduleRequest;
import nextstep.service.ScheduleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping
    ResponseEntity<Void> save(@RequestBody ScheduleRequest request) throws URISyntaxException {
        long id = scheduleService.save(request.getThemeId(), request.getDate(), request.getTime());
        return ResponseEntity.created(new URI("/schedules/" + id)).build();
    }

    @GetMapping
    ResponseEntity<List<Schedule>> findSchedulesByThemeIdAndDate(@RequestParam long themeId, @RequestParam String date) {
        LocalDate localDate = LocalDate.parse(date);
        List<Schedule> schedules = scheduleService.findSchedulesByThemeIdAndDate(themeId, localDate);
        return ResponseEntity.ok(schedules);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteById(@PathVariable long id) {
        scheduleService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
