package nextstep.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import nextstep.domain.Theme;
import nextstep.dto.ThemeRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ThemeH2RepositoryTest {

    @Autowired
    private ThemeH2Repository themeH2Repository;

    @Test
    @DisplayName("이름과 설명, 가격을 이용해 테마를 저장한다.")
    void save() {
        final ThemeRequest themeRequest = new ThemeRequest("테마1", "설명1", 10000);
        long id = themeH2Repository
            .save(themeRequest.getName(), themeRequest.getDesc(), themeRequest.getPrice());

        assertThat(id).isNotZero();
    }

    @Test
    @DisplayName("모든 테마의 정보를 가져온다.")
    void findAll() {
        themeH2Repository
            .save("테마1", "설명1", 12000);
        themeH2Repository
            .save("테마2", "설명2", 22000);

        List<Theme> themes = themeH2Repository.findAll();

        assertThat(themes).hasSizeGreaterThanOrEqualTo(2);
    }

    @Test
    @DisplayName("id 를 이용해 테마를 삭제한다.")
    void deleteById() {
        ThemeRequest themeRequest = new ThemeRequest("테마1", "설명1", 12000);
        long id = themeH2Repository
            .save(themeRequest.getName(), themeRequest.getDesc(), themeRequest.getPrice());
        final Theme expected = themeRequest.toThema(id);

        themeH2Repository.deleteById(id);
        List<Theme> themes = themeH2Repository.findAll();

        assertThat(themes).doesNotContain(expected);
    }
}
