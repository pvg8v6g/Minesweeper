package models;

import enumerations.GridStatuses;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

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
        this.status = new SimpleObjectProperty<>();
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
        setStatus(GridStatuses.GridStatus.Normal);
    }

    // endregion

    // region Statuses

//    public void debug() {
//        graphic.getStyleClass().setAll("selected-sweep-grid");
//        image("mine");
//    }
//
//    public void boom() {
//        status = GridStatuses.GridStatus.Exploded;
//        graphic.getStyleClass().setAll("selected-sweep-grid");
//        image("mine");
//    }
//
//    public void select(int bombs) {
//        status = GridStatuses.GridStatus.Selected;
//        graphic.getStyleClass().setAll("selected-sweep-grid");
//        if (bombs > 0) label.setText(String.valueOf(bombs));
//    }
//
//    public void flag() {
//        status = GridStatuses.GridStatus.Flagged;
//        image("flag");
//    }
//
//    public void question() {
//        status = GridStatuses.GridStatus.Questioned;
//        label.setText("?");
//        imageView.setImage(null);
//    }
//
//    public void returnState() {
//        status = GridStatuses.GridStatus.Normal;
//        label.setText("");
//        imageView.setImage(null);
//    }

    private void image(String file) {
        imageView.setImage(new Image(location + file + ".png"));
        imageView.setFitWidth(24.0);
        imageView.setFitHeight(24.0);
    }

    // endregion

    // region Listener

    private void installListeners() {
        columnProperty().addListener((ov, o, n) -> GridPane.setColumnIndex(this, n.intValue()));

        rowProperty().addListener((ov, o, n) -> GridPane.setRowIndex(this, n.intValue()));

        statusProperty().addListener((ov, o, n) -> {
            switch (n) {
                case Normal -> {
                    label.setText("");
                    imageView.setImage(null);
                    graphic.getStyleClass().setAll("sweep-grid");
                }
                case Selected -> {
                    graphic.getStyleClass().setAll("selected-sweep-grid");
                    if (surroundingBombs > 0) {
                        label.setText(String.valueOf(surroundingBombs));
                        label.setStyle("-fx-text-fill: " + getBombColor(surroundingBombs) + ";");
                    } else label.setText("");
                }
                case Flagged -> {
                    image("flag");
                    graphic.getStyleClass().setAll("sweep-grid");
                }
                case Questioned -> {
                    label.setText("?");
                    imageView.setImage(null);
                    graphic.getStyleClass().setAll("sweep-grid");
                }
                case Exploded, Debug -> {
                    graphic.getStyleClass().setAll("selected-sweep-grid");
                    image("mine");
                }
            }
        });
    }

    // endregion

    // region Methods

    private String getBombColor(int bombCount) {
        switch (bombCount) {
            case 2 -> {
                return "#FFBA67";
            }
            case 3 -> {
                return "#C1750D";
            }
            case 4 -> {
                return "#C94000";
            }
            case 5 -> {
                return "#FF0000";
            }
            case 6 -> {
                return "#FF0048";
            }
            case 7 -> {
                return "#AA0014";
            }
            case 8 -> {
                return "#000000";
            }
            default -> {
                return "#FFFFFF";
            }
        }
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

    public GridStatuses.GridStatus getStatus() {
        return status.get();
    }

    public ObjectProperty<GridStatuses.GridStatus> statusProperty() {
        return status;
    }

    public void setStatus(GridStatuses.GridStatus status) {
        this.status.set(status);
    }

    private final IntegerProperty column;
    private final IntegerProperty row;
    private final ObjectProperty<GridStatuses.GridStatus> status;

    // endregion

    // region Fields

    private final Label label;
    private final StackPane graphic;
    public ImageView imageView;
    public URL location;
    public int surroundingBombs;

    // endregion

}
