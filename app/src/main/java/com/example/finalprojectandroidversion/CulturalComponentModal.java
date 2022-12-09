package com.example.finalprojectandroidversion;

public class CulturalComponentModal {
    private  int ComponentId;
    private String componentName;

    public CulturalComponentModal() {
    }

    public CulturalComponentModal(int componetId, String componentName) {
        this.ComponentId = componetId;
        this.componentName = componentName;
    }

    public int getComponentId() {
        return ComponentId;
    }

    public void setComponentId(int componentId) {
        this.ComponentId = componentId;
    }

    public String getComponentName() {
        return componentName;
    }

    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }
}
