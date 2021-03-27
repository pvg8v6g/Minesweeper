package ListView;

import javafx.beans.DefaultProperty;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import list.Enumerable;

import java.lang.reflect.Field;
import java.util.stream.Collectors;

/**
 * This file has been created by:
 * philb on
 * 2/28/2021 at
 * 2:24 PM
 */
@DefaultProperty("items")
public class ListBox<T> extends ScrollPane {

    //region Constructor

    public ListBox() {
        getStyleClass().add("list-box");
        vBox = new VBox();
        items = new SimpleListProperty<>(FXCollections.observableArrayList());
        selectedItem = new SimpleObjectProperty<>();
        selectedIndex = new SimpleIntegerProperty(-1);
        displayPath = new SimpleStringProperty();
        initializeSize();
        installTooltip();
        installBindings();
        installContextMenu();
    }

    //endregion

    //region Bindings

    private void installBindings() {
        itemsProperty().addListener((ov, o, n) -> {
            indexCounter = 0;
            if (n == null) return;

            var nodeList = new Enumerable<Node>();
            var list = new Enumerable<>(n);
            list.forEach(x -> {
                Node node;
                if (x instanceof Node nd) node = nd;
                else node = new Label(getCellDisplay(x));
                var cell = new ListBoxCell(node);
                cell.setOnMouseClicked(cellEvent(indexCounter));
                if (getRealWidth() > 0) cell.setPrefWidth(getRealWidth() - 2);
                nodeList.add(cell);
                indexCounter++;
            });

            vBox.getChildren().setAll(nodeList);
        });

        selectedIndexProperty().addListener((ov, o, n) -> {
            var index = n.intValue();
            for (var i = 0; i < vBox.getChildren().size(); i++) {
                var node = vBox.getChildren().get(i);
                if (!(node instanceof ListBoxCell cell)) continue;
                cell.setSelected(i == index);
            }

            if (index >= 0) setSelectedItem(getItems().get(index));
        });
    }

    //endregion

    //region Methods

    private void initializeSize() {
        setContent(vBox);
        setMinWidth(0.0);
        setMinHeight(0.0);
        setMinViewportWidth(0.0);
        setMinViewportHeight(0.0);
        prefWidthProperty().bind(Bindings.when(realWidthProperty().greaterThan(0)).then(realWidthProperty()).otherwise(vBoxWidth));
        prefHeightProperty().bind(Bindings.when(realHeightProperty().greaterThan(0)).then(realHeightProperty()).otherwise(vBoxHeight));
        vBoxWidth.bind(vBox.widthProperty().add(2));
        vBoxHeight.bind(vBox.heightProperty().add(26));
    }

    private void installTooltip() {
        tooltipTextProperty().addListener((ov, o, n) -> getTooltip().setText(n));

        tooltipShowDelayProperty().addListener((ov, o, n) -> getTooltip().setShowDelay(Duration.millis((double) n)));

        tooltipHideDelayProperty().addListener((ov, o, n) -> getTooltip().setHideDelay(Duration.millis((double) n)));
    }

    private String getCellDisplay(T item) {
        try {
            Field field = item.getClass().getField(getDisplayPath());
            var result = field.get(item);
            return result.toString();
        } catch (Exception e) {
            return item.toString();
        }
    }

    //endregion

    //region Context Menu

    private void installContextMenu() {
        contextMenu = new ContextMenu();
        setOnMouseClicked(event -> {
            contextMenu.hide();
            if (event.getButton() != MouseButton.SECONDARY) return;
            addMenuItems();
            contextMenu.show(this, event.getScreenX(), event.getScreenY());
        });
    }

    private void addMenuItems() {
        var orphanedItems = getMenuItems().stream().filter(x -> !contextMenu.getItems().contains(x)).collect(Collectors.toCollection(Enumerable::new));
        contextMenu.getItems().addAll(orphanedItems);
    }

    //endregion

    //region Event Handler

    private EventHandler<MouseEvent> cellEvent(int index) {
        return mouseEvent -> {
            setSelectedIndex(-1);
            setSelectedIndex(index);
        };
    }

    //endregion

    //region Properties

    public ObservableList<T> getItems() {
        return this.items == null ? null : this.items.get();
    }

    public ListProperty<T> itemsProperty() {
        if (this.items == null) this.items = new SimpleListProperty<>(this, "items");
        return this.items;
    }

    public void setItems(ObservableList items) {
        this.items.set(items);
    }

    public double getRealWidth() {
        return realWidth.get();
    }

    public SimpleDoubleProperty realWidthProperty() {
        return realWidth;
    }

    public void setRealWidth(double realWidth) {
        this.realWidth.set(realWidth);
    }

    public double getRealHeight() {
        return realHeight.get();
    }

    public SimpleDoubleProperty realHeightProperty() {
        return realHeight;
    }

    public void setRealHeight(double realHeight) {
        this.realHeight.set(realHeight);
    }

    public T getSelectedItem() {
        return selectedItem.get();
    }

    public ObjectProperty<T> selectedItemProperty() {
        return selectedItem;
    }

    public void setSelectedItem(T selectedItem) {
        this.setSelectedIndex(getItems().indexOf(selectedItem));
        this.selectedItem.set(selectedItem);
    }

    public int getSelectedIndex() {
        return selectedIndex.get();
    }

    public IntegerProperty selectedIndexProperty() {
        return selectedIndex;
    }

    public void setSelectedIndex(int selectedIndex) {
        this.selectedIndex.set(selectedIndex);
    }

    public double getTooltipHideDelay() {
        return tooltipHideDelay.get();
    }

    public SimpleDoubleProperty tooltipHideDelayProperty() {
        return tooltipHideDelay;
    }

    public void setTooltipHideDelay(double tooltipHideDelay) {
        if (getTooltip() == null) setTooltip(new Tooltip());
        this.tooltipHideDelay.set(tooltipHideDelay);
    }

    public double getTooltipShowDelay() {
        return tooltipShowDelay.get();
    }

    public SimpleDoubleProperty tooltipShowDelayProperty() {
        return tooltipShowDelay;
    }

    public void setTooltipShowDelay(double tooltipShowDelay) {
        if (getTooltip() == null) setTooltip(new Tooltip());
        this.tooltipShowDelay.set(tooltipShowDelay);
    }

    public String getTooltipText() {
        return tooltipText.get();
    }

    public SimpleStringProperty tooltipTextProperty() {
        return tooltipText;
    }

    public void setTooltipText(String tooltipText) {
        if (getTooltip() == null) setTooltip(new Tooltip());
        this.tooltipText.set(tooltipText);
    }

    public String getDisplayPath() {
        return displayPath.get();
    }

    public StringProperty displayPathProperty() {
        return displayPath;
    }

    public void setDisplayPath(String displayPath) {
        this.displayPath.set(displayPath);
    }

    private ListProperty<T> items;
    private final SimpleDoubleProperty realWidth = new SimpleDoubleProperty(-1);
    private final SimpleDoubleProperty realHeight = new SimpleDoubleProperty(-1);
    private final ObjectProperty<T> selectedItem;
    private final IntegerProperty selectedIndex;
    private final SimpleDoubleProperty tooltipHideDelay = new SimpleDoubleProperty();
    private final SimpleDoubleProperty tooltipShowDelay = new SimpleDoubleProperty();
    private final SimpleStringProperty tooltipText = new SimpleStringProperty();
    private final StringProperty displayPath;

    //endregion

    //region Fields

    public ObservableList<MenuItem> getMenuItems() {
        return menuItems;
    }

    private final ObservableList<MenuItem> menuItems = FXCollections.observableArrayList();
    private ContextMenu contextMenu;

    private final DoubleProperty vBoxWidth = new SimpleDoubleProperty();
    private final DoubleProperty vBoxHeight = new SimpleDoubleProperty();
    private final VBox vBox;
    private int indexCounter;

    //endregion

}
