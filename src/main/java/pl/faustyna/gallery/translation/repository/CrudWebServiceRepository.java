package pl.faustyna.gallery.translation.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Repository
public class CrudWebServiceRepository {

    private static final Logger LOG = LoggerFactory.getLogger(CrudWebServiceRepository.class);
    @Autowired
    private RestTemplate restTemplate;

    public List<Map<String, Object>> findAllObjects(final String url) {
        LOG.debug(url);
        final List<Map<String, Object>> response = (List<Map<String, Object>>) restTemplate.getForObject(url, Object.class);
        return response;
    }

    public Object save(final String url, final String params) {
        final String urlWithParams = new StringBuilder().append(url).append("&").append(params).toString();
        LOG.debug(urlWithParams);
        final Object response = restTemplate.getForObject(urlWithParams, Object.class);
        return response;
    }


}
