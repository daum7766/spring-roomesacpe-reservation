package nextstep.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import nextstep.dto.ThemeRequest;
import nextstep.domain.Theme;
import nextstep.service.ThemeService;
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
public class ThemeController {

    private final ThemeService themeService;

    public ThemeController(ThemeService themeService) {
        this.themeService = themeService;
    }

    @PostMapping
    ResponseEntity<Void> save(@RequestBody ThemeRequest themeRequest) throws URISyntaxException {
        long id = themeService.save(themeRequest.getName(), themeRequest.getDesc(), themeRequest.getPrice());
        return ResponseEntity.created(new URI("/themes/" + id)).build();
    }

    @GetMapping
    ResponseEntity<List<Theme>> findAll() {
        List<Theme> themes = themeService.findAll();
        return ResponseEntity.ok(themes);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteById(@PathVariable long id) {
        themeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
