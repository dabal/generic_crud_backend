package pl.faustyna.gallery.translation.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import pl.faustyna.gallery.translation.json.JsonUtils;
import pl.faustyna.gallery.translation.model.ViewConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Repository
public class ViewConfigurationRepository {
    private static final String CONFIGURATION_SELECT = "select * from java_configuration order by order_ asc";
    private static final Logger LOG = LoggerFactory.getLogger(ViewConfigurationRepository.class);
    @Autowired
    private final SqlQueryResultRowMapper sqlQueryResultRowMapper;
    @Autowired
    private final JdbcTemplate jdbcTemplate;

    Map<String, ViewConfiguration> map;
    List<String> views;

    public ViewConfigurationRepository(final SqlQueryResultRowMapper sqlQueryResultRowMapper, final JdbcTemplate jdbcTemplate) {
        this.sqlQueryResultRowMapper = sqlQueryResultRowMapper;
        this.jdbcTemplate = jdbcTemplate;
        loadConfiguration();

    }

    public void loadConfiguration() {
        final Map<String, ViewConfiguration> configuration = new TreeMap<>();
        final List<Map<String, Object>> objectList = jdbcTemplate.query(CONFIGURATION_SELECT, sqlQueryResultRowMapper);
        final List<String> viewsList = new ArrayList<>();
        for (final Map<String, Object> m : objectList) {
            LOG.debug(JsonUtils.convertToString(JsonUtils.convertToJson((String) m.get("definition"))));
            configuration.put((String) m.get("view_id"), new ViewConfiguration((Map<String, Object>) JsonUtils.convertToJson((String) m.get("definition"))));
            viewsList.add((String) m.get("view_id"));
        }
        LOG.info(JsonUtils.convertToString(configuration));
        this.map = configuration;
        this.views = viewsList;
    }

    public ViewConfiguration getConfigurationForView(final String viewid) {
        final ViewConfiguration m = (ViewConfiguration) map.get(viewid);
        if (m == null) {
            throw new IllegalArgumentException("View with this id does not exist" + viewid);
        }
        return m;
    }


    public Object getViews() {
        return getConfigurationForView("menu").getConfiguration();
    }
}
