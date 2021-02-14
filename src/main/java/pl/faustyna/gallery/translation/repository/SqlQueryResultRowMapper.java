package pl.faustyna.gallery.translation.repository;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Component
public class SqlQueryResultRowMapper implements RowMapper<Map<String, Object>> {
    @Override
    public Map<String, Object> mapRow(final ResultSet resultSet, final int i) throws SQLException {
        if (resultSet == null) {
            throw new IllegalArgumentException("ResultSet is null");
        }
        final Map<String, Object> map = new HashMap<>();
        final ResultSetMetaData rsmd = resultSet.getMetaData();
        for (int j = 1; j <= rsmd.getColumnCount(); j++) {
            map.put(rsmd.getColumnLabel(j), resultSet.getObject(j));
        }
        return map;
    }
}