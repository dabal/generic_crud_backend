package pl.faustyna.gallery.translation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.faustyna.gallery.translation.service.ViewConfigurationService;

@RestController
@RequestMapping("/meta")
public class MetaDataController {

    @Autowired
    private ViewConfigurationService viewConfigurationService;

    @GetMapping("/views")
    public Object getViews() {
        return viewConfigurationService.getViews();
    }

    @GetMapping("/reload")
    public void reloadConfiguration() {
        viewConfigurationService.reloadConfiguration();
    }


}
