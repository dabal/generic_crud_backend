package pl.faustyna.gallery.translation.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.faustyna.gallery.translation.json.JsonUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@Transactional
public class CrudDatabaseRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    SqlQueryResultRowMapper sqlQueryResultRowMapper;

    public Optional<Map<String, Object>> findById(final String procedureName, final Long id) {
        final String filterJson = new StringBuilder().append("{\"filter\":{\"id\":").append(id).append("}}").toString();
        final String procedureCall = new StringBuilder().append("call ").append(procedureName).append("(?)").toString();
        return Optional.ofNullable(jdbcTemplate.queryForObject(procedureCall, new Object[]{filterJson}, sqlQueryResultRowMapper));
    }


    public List<Map<String, Object>> findAllObjects(final String procedureName, final Map<String, String> filters) {
        final String procedureCall = new StringBuilder().append("call ").append(procedureName).append("('")
                .append(JsonUtils.convertToString(filters)).append("')").toString();
        final List<Map<String, Object>> objectList = jdbcTemplate.query(procedureCall, sqlQueryResultRowMapper);
        return objectList;
    }

    public void save(final String procedureName, final String inputToDb) {
        final String procedureCall = new StringBuilder().append("call ").append(procedureName).append("Edit(?)").toString();
        jdbcTemplate.update(procedureCall, inputToDb);
    }
}