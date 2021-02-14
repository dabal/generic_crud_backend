package pl.faustyna.gallery.translation.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Map;

public class ViewConfiguration {
    private Map<String, Object> configuration;

    public ViewConfiguration(final Map<String, Object> configuration) {
        this.configuration = configuration;
    }

    public ViewConfiguration() {
    }

    public Map<String, Object> getConfiguration() {
        return configuration;
    }

    public void setConfiguration(final Map<String, Object> configuration) {
        this.configuration = configuration;
    }

    @JsonIgnore
    public String getProcedureName() {
        return (String) (this.configuration.get("procedure_name"));
    }

    @JsonIgnore
    public String getUrl() {
        return (String) (this.configuration.get("url"));
    }

    @JsonIgnore
    public boolean viewHasDataFromWebService() {
        return this.configuration.get("url") != null;
    }

    @JsonIgnore
    public Map<String, Object> getColumns() {
        return (Map<String, Object>) this.getFrontSettings().get("columns");
    }

    @JsonIgnore
    public String getLabel() {
        return (String) this.getFrontSettings().get("label");
    }

    @JsonIgnore
    public Map<String, Object> getFrontSettings() {
        return ((Map<String, Object>) this.configuration.get("front_settings"));
    }

    @JsonIgnore
    public String getUpdateUrl() {
        return (String) this.configuration.get("update_url");
    }

    @JsonIgnore
    public String getCreateUrl() {
        return (String) this.configuration.get("create_url");
    }

    @JsonIgnore
    public Map<String, String> getFormFieldMapping() {
        return (Map<String, String>) this.configuration.get("form_field_mapping");
    }

    @JsonIgnore
    public String getDeleteUrl() {
        return (String) this.configuration.get("delete_url");
    }

}
