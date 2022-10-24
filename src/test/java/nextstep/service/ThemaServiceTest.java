package nextstep.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import nextstep.domain.Thema;
import nextstep.dto.ThemaRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ThemaServiceTest {

    @Autowired
    private ThemaService themaService;

    @Test
    @DisplayName("이름과 설명, 가격을 이용해 테마를 저장한다.")
    void save() {
        final ThemaRequest themaRequest = new ThemaRequest("테마1", "설명1", 10000);
        long id = themaService
            .save(themaRequest.getName(), themaRequest.getDesc(), themaRequest.getPrice());

        assertThat(id).isNotZero();
    }

    @Test
    @DisplayName("모든 테마의 정보를 가져온다.")
    void findAll() {
        themaService
            .save("테마1", "설명1", 12000);
        themaService
            .save("테마2", "설명2", 22000);

        List<Thema> themes = themaService.findAll();

        assertThat(themes).hasSizeGreaterThanOrEqualTo(2);
    }

    @Test
    @DisplayName("id 를 이용해 테마를 삭제한다.")
    void deleteById() {
        ThemaRequest themaRequest = new ThemaRequest("테마1", "설명1", 12000);
        long id = themaService
            .save(themaRequest.getName(), themaRequest.getDesc(), themaRequest.getPrice());
        final Thema expected = themaRequest.toThema(id);

        themaService.deleteById(id);
        List<Thema> themes = themaService.findAll();

        assertThat(themes).doesNotContain(expected);
    }
}
