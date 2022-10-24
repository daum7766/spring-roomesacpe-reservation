package nextstep.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import nextstep.dto.ThemaRequest;
import nextstep.domain.Thema;
import nextstep.service.ThemaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/themes")
public class ThemaController {

    private final ThemaService themaService;

    public ThemaController(ThemaService themaService) {
        this.themaService = themaService;
    }

    @PostMapping
    ResponseEntity<Void> save(@RequestBody ThemaRequest themaRequest) throws URISyntaxException {
        long id = themaService.save(themaRequest.getName(), themaRequest.getDesc(), themaRequest.getPrice());
        return ResponseEntity.created(new URI("/themes/" + id)).build();
    }

    @GetMapping
    ResponseEntity<List<Thema>> findAll() {
        List<Thema> themes = themaService.findAll();
        return ResponseEntity.ok(themes);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteById(@PathVariable long id) {
        themaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
