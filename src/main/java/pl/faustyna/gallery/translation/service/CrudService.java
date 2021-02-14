package pl.faustyna.gallery.translation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import pl.faustyna.gallery.translation.json.JsonUtils;
import pl.faustyna.gallery.translation.repository.CrudDatabaseRepository;
import pl.faustyna.gallery.translation.repository.CrudWebServiceRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CrudService {


    @Autowired
    CrudDatabaseRepository crudDatabaseRepository;

    @Autowired
    CrudWebServiceRepository crudWebServiceRepository;

    @Autowired
    ViewConfigurationService viewConfigurationService;

    @Autowired
    FilterService filterService;
    @Autowired
    DTOService dTOService;

    public List<Map<String, Object>> getObjectList(final String viewId, final Map<String, String> filters) {
        final List<Map<String, Object>> resultList;
        if (viewConfigurationService.viewHasDataFromWebService(viewId)) {
            resultList = crudWebServiceRepository.findAllObjects(viewConfigurationService.getUrl(viewId));
        } else {
            resultList = crudDatabaseRepository.findAllObjects(viewConfigurationService.getProcedureName(viewId), filters);
        }
        return filterService.filterResultList(resultList, viewConfigurationService.getColumnsSet(viewId));
    }

    public Map<String, Object> getObjectDetails(final String viewId, final Long id) {
        final Map<String, Object> map = crudDatabaseRepository.findById(viewConfigurationService.getProcedureName(viewId), id).orElseThrow(() -> new ResourceNotFoundException());
        return filterService.filterSingleResult(map, viewConfigurationService.getColumnsSet(viewId));
    }

    public void updateDBObject(final String viewId, final String inputToDb) {
        crudDatabaseRepository.save(viewConfigurationService.getProcedureName(viewId), inputToDb);
    }

    public void updateWebObject(final String viewId, final String input) {
        final String url = viewConfigurationService.getConfigurationForView(viewId).getUpdateUrl();
        crudWebServiceRepository.save(url, input);
    }

    public void createWebObject(final String viewId, final String input) {
        final String url = viewConfigurationService.getConfigurationForView(viewId).getCreateUrl();
        crudWebServiceRepository.save(url, input);
    }

    public void updateObject(final String viewId, final String json, final Long id) {
        if (viewConfigurationService.getUrl(viewId) != null) {
            final String params = buildStringFromMap(mapFieldsFromInputForm(viewId, json));
            updateWebObject(viewId, params);
        } else {
            final String inputToDb = dTOService.buildJsonForStoredProcedure(viewId, id, json);
            updateDBObject(viewId, inputToDb);
        }
    }

    public void createObject(final String viewId, final String json) {
        if (viewConfigurationService.getUrl(viewId) != null) {
            final String params = buildStringFromMap(mapFieldsFromInputForm(viewId, json));
            createWebObject(viewId, params);
        } else {
            final String inputToDb = dTOService.buildJsonForStoredProcedure(viewId, null, json);
            updateDBObject(viewId, inputToDb);
        }
    }

    public void delete(final String viewId, final Long id, final String json) {
        if (viewConfigurationService.getUrl(viewId) != null) {
            final String params = buildStringFromMap(mapFieldsFromInputForm(viewId, json));
            final String url = viewConfigurationService.getConfigurationForView(viewId).getDeleteUrl();
            crudWebServiceRepository.save(url, params);
        }
    }

    private String buildStringFromMap(final Map<String, Object> map) {
        final List<String> list = new ArrayList<>(map.keySet().size());
        for (final String key : map.keySet()) {
            list.add(new StringBuilder().append(key).append("=").append(map.get(key).toString().trim()).toString());
        }
        return list.stream().collect(Collectors.joining("&"));
    }

    private Map<String, Object> mapFieldsFromInputForm(final String viewId, final String json) {
        final Map<String, Object> map = (Map<String, Object>) JsonUtils.convertToJson(json);
        final Map<String, String> mapping = viewConfigurationService.getConfigurationForView(viewId).getFormFieldMapping();
        if (mapping != null) {
            for (final String key : mapping.keySet()) {
                map.put(mapping.get(key), map.get(key));
                map.remove(key);
            }
        }
        return map;
    }
}
