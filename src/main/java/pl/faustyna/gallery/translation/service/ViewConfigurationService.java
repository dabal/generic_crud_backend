package pl.faustyna.gallery.translation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.faustyna.gallery.translation.model.ViewConfiguration;
import pl.faustyna.gallery.translation.repository.ViewConfigurationRepository;

import java.util.Map;
import java.util.Set;

@Service
public class ViewConfigurationService {
    @Autowired
    private final ViewConfigurationRepository viewConfigurationRepository;

    public ViewConfigurationService(final ViewConfigurationRepository viewConfigurationRepository) {
        this.viewConfigurationRepository = viewConfigurationRepository;
    }

    public ViewConfiguration getConfigurationForView(final String viewid) {
        return viewConfigurationRepository.getConfigurationForView(viewid);
    }


    public Object getViews() {
        return this.viewConfigurationRepository.getViews();
    }

    public String getUrl(final String viewId) {
        return viewConfigurationRepository.getConfigurationForView(viewId).getUrl();
    }

    public boolean viewHasDataFromWebService(final String viewId) {
        return viewConfigurationRepository.getConfigurationForView(viewId).viewHasDataFromWebService();
    }

    public String getProcedureName(final String viewId) {
        return viewConfigurationRepository.getConfigurationForView(viewId).getProcedureName();
    }

    public Map<String, Object> getColumns(final String viewId) {
        return viewConfigurationRepository.getConfigurationForView(viewId).getColumns();
    }

    public Map<String, Object> getFrontSettings(final String viewId) {
        return viewConfigurationRepository.getConfigurationForView(viewId).getFrontSettings();
    }

    public void reloadConfiguration() {
        viewConfigurationRepository.loadConfiguration();
    }

    public Set<String> getColumnsSet(final String viewId) {
        return viewConfigurationRepository.getConfigurationForView(viewId).getColumns().keySet();
    }
}
