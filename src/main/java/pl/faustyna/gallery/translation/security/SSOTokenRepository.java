package pl.faustyna.gallery.translation.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class SSOTokenRepository {
    private static final String FIND_BY_TOKEN = "select count(*) from TOKENS where tkn_token=? and tkn_state='Y'";
    private static final String UPDATE_STATUS_BY_TOKEN = "update TOKENS set tkn_state='N' where tkn_token=?";
    private static final Logger LOG = LoggerFactory.getLogger(SecurityService.class);


    @Autowired
    private JdbcTemplate jdbcTemplate;

    Boolean isSSOTokenValid(final String token) {
        final Long tokenCount = jdbcTemplate.queryForObject(FIND_BY_TOKEN, Long.class, token);
        LOG.debug("token: " + token + " # " + tokenCount + " " + tokenCount.equals(0));
        return !tokenCount.equals(0L);
    }

    void invalidateSSOToken(final String token) {
        jdbcTemplate.update(UPDATE_STATUS_BY_TOKEN, token);
    }

}
