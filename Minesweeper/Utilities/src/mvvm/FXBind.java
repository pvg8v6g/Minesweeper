package mvvm;

import javafx.beans.binding.Bindings;
import javafx.beans.property.Property;

/**
 * This file has been created by:
 * pvaughn on
 * 3/18/2021 at
 * 1:32 PM
 */
public class FXBind {

    private Object controller;
    private String controllerMethod;

    public Object getController() {
        return controller;
    }

    public void setController(Object controller) {
        this.controller = controller;
        bind();
    }

    public String getControllerMethod() {
        return controllerMethod;
    }

    public void setControllerMethod(String controllerMethod) {
        this.controllerMethod = controllerMethod;
        bind();
    }

    private Object node;
    private String nodeMethod;

    public Object getNode() {
        return node;
    }

    public void setNode(Object node) {
        this.node = node;
        bind();
    }

    public String getNodeMethod() {
        return nodeMethod;
    }

    public void setNodeMethod(String nodeMethod) {
        this.nodeMethod = nodeMethod;
        bind();
    }

    private <T> void bind() {
        if (controller == null || controllerMethod == null || node == null || nodeMethod == null) return;
        try {
            @SuppressWarnings("unchecked")
            Property<T> property1 = (Property<T>) controller.getClass().getMethod(controllerMethod + "Property").invoke(controller);
            @SuppressWarnings("unchecked")
            Property<T> property2 = (Property<T>) node.getClass().getMethod(nodeMethod + "Property").invoke(node);
            Bindings.bindBidirectional(property1, property2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        controller = node = null;
        controllerMethod = nodeMethod = null;
    }

}
