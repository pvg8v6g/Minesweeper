/**
 * This file has been created by:
 * philb on
 * 3/1/2021 at
 * 6:04 PM
 */
package ListView;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

public class ListBoxCell extends StackPane {

    //region Constructor

    public ListBoxCell(Node node) {
        getChildren().add(node);
        setMinHeight(40.0);
        setAlignment(Pos.CENTER_LEFT);
        StackPane.setMargin(node, new Insets(0, 3, 0, 3));
        setSelected(false);
    }

    //endregion

    //region Properties

    public boolean isSelected() {
        return selected.get();
    }

    public BooleanProperty selectedProperty() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected.set(selected);
        getStyleClass().clear();
        if (isSelected()) getStyleClass().add("list-box-cell-selected");
        else getStyleClass().add("list-box-cell");
    }

    private final BooleanProperty selected = new SimpleBooleanProperty();

    //endregion

}
