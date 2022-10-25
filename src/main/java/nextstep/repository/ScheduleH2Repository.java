package nextstep.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import nextstep.domain.Schedule;
import nextstep.domain.Theme;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class ScheduleH2Repository {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Schedule> mapper =
        (resultSet, rowNum) -> new Schedule(
            resultSet.getLong("id"),
            new Theme(
                resultSet.getLong("themeId"),
                resultSet.getString("name"),
                resultSet.getString("desc"),
                resultSet.getLong("price")
            ),
            resultSet.getDate("date").toLocalDate(),
            resultSet.getTime("time").toLocalTime()
        );

    public ScheduleH2Repository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public long save(long themeId, LocalDate date, LocalTime time) {
        final String sql = "insert into schedule (themeId, date, time) values (?, ?, ?)";

        final KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update((Connection con) -> {
            PreparedStatement pstmt = con.prepareStatement(
                sql,
                Statement.RETURN_GENERATED_KEYS
            );
            pstmt.setLong(1, themeId);
            pstmt.setObject(2, date);
            pstmt.setObject(3, time);
            return pstmt;
        }, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public List<Schedule> findSchedulesByThemeIdAndDate(long themeId, LocalDate date) {
        final String sql = "select schedule.id as id, date, time, themeId, name, desc, price "
            + "from schedule "
            + "join theme "
            + "on theme.id = themeId and themeId = ? and date = ?";

        return jdbcTemplate.query(sql, mapper, themeId, date);
    }

    public void deleteById(long id) {
        final String sql = "delete from schedule where id = ?";
        jdbcTemplate.update(sql, id);
    }
}
