package nextstep.service;

import java.util.List;
import nextstep.domain.Theme;
import nextstep.repository.ThemeH2Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ThemeService {

    private final ThemeH2Repository themeH2Repository;

    public ThemeService(ThemeH2Repository themeH2Repository) {
        this.themeH2Repository = themeH2Repository;
    }

    @Transactional
    public long save(String name, String desc, long price) {
        return themeH2Repository.save(name, desc, price);
    }

    @Transactional
    public List<Theme> findAll() {
        return themeH2Repository.findAll();
    }

    @Transactional
    public void deleteById(long id) {
        themeH2Repository.deleteById(id);
    }
}
