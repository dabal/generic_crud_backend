package pl.faustyna.gallery.translation.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class FilterService {
    public List<Map<String, Object>> filterResultList(final List<Map<String, Object>> results, final Set<String> columns) {
        for (int i = 0; i < results.size(); i++) {
            results.set(i, filterSingleResult(results.get(i), columns));
        }
        return results;

    }

    public Map<String, Object> filterSingleResult(final Map<String, Object> result, final Set<String> columns) {
        result.keySet().removeIf(column -> !columns.contains(column));

        return result;
    }
}
