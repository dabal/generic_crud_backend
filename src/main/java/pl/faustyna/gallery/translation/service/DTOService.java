package pl.faustyna.gallery.translation.service;

import org.springframework.stereotype.Service;
import pl.faustyna.gallery.translation.json.JsonUtils;

import java.util.HashMap;
import java.util.Map;

@Service
public class DTOService {

    public String buildJsonForStoredProcedure(final String viewId, final Long id, final String json_str) {
        final Map<String, Object> metaMap = new HashMap<>();
        metaMap.put("viewId", viewId);
        metaMap.put("id", id);

        final Map<String, Object> mapForJson = new HashMap<>();
        mapForJson.put("meta", metaMap);
        mapForJson.put("input_data", JsonUtils.convertToJson(json_str));
        return JsonUtils.convertToString(mapForJson);
    }

    public Map<String, Object> buildJsonForDataRequest(final Object meta, final Object data) {
        final Map<String, Object> map = new HashMap<>();
        map.put("meta", meta);
        map.put("data", data);
        return map;

    }
}
