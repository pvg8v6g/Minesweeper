package models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import java.net.URL;

/**
 * This file has been created by:
 * pvaughn on
 * 3/18/2021 at
 * 2:10 PM
 */
public class SweepGrid extends StackPane {

    // region Constructor

    public SweepGrid(int column, int row) {
        this.column = new SimpleIntegerProperty();
        this.row = new SimpleIntegerProperty();
        label = new Label();
        label.setMouseTransparent(true);
        location = SweepGrid.class.getClassLoader().getResource("images/");
        imageView = new ImageView();
        graphic = new StackPane();
        graphic.getStyleClass().setAll("sweep-grid");
        graphic.getChildren().addAll(label, imageView);
        getChildren().addAll(graphic);
        setMinSize(28.0, 28.0);
        setMaxSize(28.0, 28.0);
        graphic.setMinSize(24.0, 24.0);
        graphic.setMaxSize(24.0, 24.0);
        installListeners();
        setColumn(column);
        setRow(row);
    }

    // endregion

    // region

    public void debug() {
        graphic.getStyleClass().setAll("selected-sweep-grid");
        image("mine");
    }

    public void boom() {
        selected = true;
        graphic.getStyleClass().setAll("selected-sweep-grid");
        image("mine");
    }

    public void select(int bombs) {
        selected = true;
        graphic.getStyleClass().setAll("selected-sweep-grid");
        if (bombs > 0) label.setText(String.valueOf(bombs));
    }

    public void flag() {
        state = 0;
        image("flag");
    }

    public void question() {
        state = 1;
        label.setText("?");
        imageView.setImage(null);
    }

    public void returnState() {
        state = -1;
        label.setText("");
        imageView.setImage(null);
    }

    private void image(String file) {
        imageView.setImage(new Image(location + file + ".png"));
        imageView.setFitWidth(24.0);
        imageView.setFitHeight(24.0);
    }

    // endregion

    // region Listener

    private void installListeners() {
        columnProperty().addListener((ov, o, n) -> {
            GridPane.setColumnIndex(this, n.intValue());
        });
        rowProperty().addListener((ov, o, n) -> {
            GridPane.setRowIndex(this, n.intValue());
        });
    }

    // endregion

    // region Properties

    public int getColumn() {
        return column.get();
    }

    public IntegerProperty columnProperty() {
        return column;
    }

    public void setColumn(int column) {
        this.column.set(column);
    }

    public int getRow() {
        return row.get();
    }

    public IntegerProperty rowProperty() {
        return row;
    }

    public void setRow(int row) {
        this.row.set(row);
    }

    public final IntegerProperty column;
    public final IntegerProperty row;

    // endregion

    // region Fields

    private final Label label;
    private final StackPane graphic;
    public boolean selected;
    public int state = -1;
    public ImageView imageView;
    public URL location;

    // endregion

}
