package nextstep.service;

import java.util.List;
import nextstep.domain.Thema;
import nextstep.repository.H2ThemaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ThemaService {

    private final H2ThemaRepository h2ThemaRepository;

    public ThemaService(H2ThemaRepository h2ThemaRepository) {
        this.h2ThemaRepository = h2ThemaRepository;
    }

    @Transactional
    public long save(String name, String desc, long price) {
        return h2ThemaRepository.save(name, desc, price);
    }

    @Transactional
    public List<Thema> findAll() {
        return h2ThemaRepository.findAll();
    }

    @Transactional
    public void deleteById(long id) {
        h2ThemaRepository.deleteById(id);
    }
}
