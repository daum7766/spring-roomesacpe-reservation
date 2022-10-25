package nextstep.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;
import nextstep.domain.Theme;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class ThemeH2Repository {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Theme> mapper =
        (resultSet, rowNum) -> new Theme(
          resultSet.getLong("id"),
          resultSet.getString("name"),
          resultSet.getString("desc"),
          resultSet.getLong("price")
        );

    public ThemeH2Repository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public long save(String name, String desc, long price) {
        final String sql = "insert into theme (name, desc, price) values (?, ?, ?)";

        final KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update((Connection con) -> {
            PreparedStatement pstmt = con.prepareStatement(
                sql,
                Statement.RETURN_GENERATED_KEYS
            );
            pstmt.setString(1, name);
            pstmt.setString(2, desc);
            pstmt.setLong(3, price);
            return pstmt;
        }, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public List<Theme> findAll() {
        final String sql = "select * from theme";
        return jdbcTemplate.query(sql, mapper);
    }

    public void deleteById(long id) {
        final String sql = "delete from theme where id = ?";
        jdbcTemplate.update(sql, id);
    }
}
