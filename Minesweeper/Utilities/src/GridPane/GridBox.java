package GridPane;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;

/**
 * This file has been created by:
 * pvaughn on
 * 3/18/2021 at
 * 2:25 PM
 */
public class GridBox extends GridPane {

    // region Constructor

    public GridBox() {
        items = new SimpleListProperty<>(FXCollections.observableArrayList());
        bindItems();
    }

    // endregion

    // region Binds

    private void bindItems() {
        itemsProperty().addListener((ov, o, n) -> {
            getChildren().setAll(n);
        });
    }

    // endregion

    // region Properties

    public ObservableList<Node> getItems() {
        return this.items == null ? null : this.items.get();
    }

    public ListProperty<Node> itemsProperty() {
        if (this.items == null) this.items = new SimpleListProperty<>(this, "items");
        return this.items;
    }

    public void setItems(ObservableList<Node> items) {
        this.items.set(items);
    }

    private ListProperty<Node> items;

    // endregion

}
