package pl.faustyna.gallery.translation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import pl.faustyna.gallery.translation.service.CrudService;
import pl.faustyna.gallery.translation.service.DTOService;
import pl.faustyna.gallery.translation.service.ViewConfigurationService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/crud")
public class CrudController {

    @Autowired
    CrudService crudService;
    @Autowired
    ViewConfigurationService viewConfigurationService;

    @Autowired
    DTOService DTOService;

    @GetMapping("/{viewId}")
    public Object getTranslationListObjects(@PathVariable final String viewId, @RequestParam(required = false) final Map<String, String> queryParams, final ModelMap model) {
        final Map<String, Object> frontSettings = viewConfigurationService.getFrontSettings(viewId);
        final List<Map<String, Object>> data = crudService.getObjectList(viewId, queryParams);
        return DTOService.buildJsonForDataRequest(frontSettings, data);
    }

    @GetMapping("/details/{viewId}/{id:\\d+}")
    public Object getTranslationDetails(@PathVariable final Long id, @PathVariable final String viewId) {
        final Map<String, Object> frontSettings = viewConfigurationService.getFrontSettings(viewId);
        final Map<String, Object> data = crudService.getObjectDetails(viewId, id);
        return DTOService.buildJsonForDataRequest(frontSettings, data);
    }


    @PostMapping("/update/{viewId}/{id:\\d+}")
    public void update(@PathVariable final String viewId, @PathVariable(required = false) final Long id, @RequestBody final String json) {

        crudService.updateObject(viewId, json, id);
    }

    @PostMapping("/update/{viewId}/")
    public void createNew(@PathVariable final String viewId, @RequestBody final String json) {

        crudService.createObject(viewId, json);
    }

    @DeleteMapping("/delete/{viewId}/{id:\\d+}")
    public void delete(@PathVariable final String viewId, @PathVariable final Long id, @RequestBody final String json) {
        crudService.delete(viewId, id, json);
    }

}
