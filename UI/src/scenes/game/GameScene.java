package scenes.game;

import access.Access;
import enumerations.GridStatuses;
import enumerations.Scenes;
import javafx.application.Platform;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import list.Enumerable;
import mathematics.Mathematics;
import models.GameModel;
import models.SweepGrid;
import models.TimerModel;
import position.Position;
import scenes.controller.Controller;
import windowmanager.WindowManager;

import java.io.IOException;

/**
 * This file has been created by:
 * pvaughn on
 * 3/18/2021 at
 * 11:19 AM
 */
public class GameScene extends Controller<GameModel> {

    // region Constructor

    public GameScene() {
        sweepGrids = new SimpleListProperty<>(FXCollections.observableArrayList());
        gameOver = new SimpleStringProperty();
        remainingBombs = new SimpleStringProperty();
        timer = new SimpleStringProperty();
        timerModel = new TimerModel();
    }

    // endregion

    // region Overrides

    public void start() {
        super.start();
        installTimer();
        installFieldGrid();
        setRemainingBombs("Bombs: " + variable.bombs);
    }

    // endregion

    // region Reset

    private void reset() {
        getSweepGrids().clear();
        mines.clear();
        installFieldGrid();
        setRemainingBombs("Bombs: " + variable.bombs);
        setGameOver("");
        timerModel.resetTimer();
        firstClick = true;
    }

    // endregion

    // region Methods

    private void installFieldGrid() {
        if (variable == null) return;
        for (int x = 0; x < variable.width; x++) {
            for (int y = 0; y < variable.height; y++) {
                var sweep = new SweepGrid(x, y);
                sweep.setOnMouseClicked(gridEventHandler(x, y));
                getSweepGrids().add(sweep);
            }
        }
    }

    private void setUpMineField(int x, int y) {
        var ox = Mathematics.random(0, variable.width - 1);
        var oy = Mathematics.random(0, variable.height - 1);
        if (ox == x && oy == y) setUpMineField(x, y);
        var mine = new Position(ox, oy);
        if (mines.any(m -> m.equals(mine))) setUpMineField(x, y);
        if (mines.size() >= variable.bombs) return;
        mines.add(mine);
        setUpMineField(x, y);
    }

    private void gridSelection(SweepGrid grid) {
        if (mines.any(mine -> mine.equals(new Position(grid.getColumn(), grid.getRow())))) {
            // game over
            Access.gameData.gamesFinished += 1;
            Access.gameData.totalTimePlayed += timerModel.getTotalTime();
            timerModel.stopTimer();
            for (var sweepGrid : getSweepGrids()) {
                sweepGrid.setMouseTransparent(true);
                if (!isMine(sweepGrid) || sweepGrid.getStatus() == GridStatuses.GridStatus.Flagged) continue;
                sweepGrid.setStatus(GridStatuses.GridStatus.Exploded);
            }

            grid.hitMine();
            setGameOver("Game Over");
        } else {
            findBombs(grid);
        }
    }

    private void findBombs(SweepGrid sweepGrid) {
        var checks = getPositionsToCheck(sweepGrid.getColumn(), sweepGrid.getRow());

        var mineCount = 0;
        for (var check : checks) {
            if (isMine(check)) mineCount++;
        }

        if (mineCount > 0) {
            sweepGrid.surroundingBombs = mineCount;
            sweepGrid.setStatus(GridStatuses.GridStatus.Selected);
        } else {
            sweepGrid.surroundingBombs = mineCount;
            sweepGrid.setStatus(GridStatuses.GridStatus.Selected);
            checks.forEach(this::findBombs);
        }

    }

    private Enumerable<SweepGrid> getPositionsToCheck(int x, int y) {
        var result = new Enumerable<SweepGrid>();

        if (y - 1 >= 0) {
            if (x - 1 >= 0) result.add(getGrid(x - 1, y - 1));
            result.add(getGrid(x, y - 1));
            if (x + 1 <= variable.width - 1) result.add(getGrid(x + 1, y - 1));
        }

        if (x - 1 >= 0) result.add(getGrid(x - 1, y));
        if (x + 1 <= variable.width - 1) result.add(getGrid(x + 1, y));

        if (y + 1 <= variable.height - 1) {
            if (x - 1 >= 0) result.add(getGrid(x - 1, y + 1));
            result.add(getGrid(x, y + 1));
            if (x + 1 <= variable.width - 1) result.add(getGrid(x + 1, y + 1));
        }

        return result;
    }

    private SweepGrid getGrid(int x, int y) {
        var stream = getSweepGrids().stream()
                .filter(sweepGrid -> sweepGrid.getStatus() != GridStatuses.GridStatus.Selected)
                .filter(sweepGrid -> sweepGrid.getColumn() == x && sweepGrid.getRow() == y);
        var optional = stream.findFirst();

        return optional.orElse(null);
    }

    private boolean isMine(SweepGrid grid) {
        return mines.any(mine -> mine.x() == grid.getColumn() && mine.y() == grid.getRow());
    }

    private void debugMines() {
        var list = new Enumerable<>(getSweepGrids());

        for (var sweepGrid : list) {
            if (!isMine(sweepGrid)) continue;
            sweepGrid.setStatus(GridStatuses.GridStatus.Debug);
        }
    }

    // endregion

    // region Listeners

    private void installTimer() {
        setTimer("00 : 00 : 00");
        timerModel.timeDisplayProperty().addListener((ov, o, n) -> {
            setTimer(n);
        });
        Platform.runLater(() -> root.getScene().getWindow().focusedProperty().addListener((ov, o, n) -> {
            if (firstClick) return;
            if (n) timerModel.startTimer();
            else timerModel.stopTimer();
        }));
    }

    private EventHandler<MouseEvent> gridEventHandler(int x, int y) {
        return mouseEvent -> {
            var grid = (SweepGrid) (mouseEvent.getSource());
            if (mouseEvent.getButton() == MouseButton.SECONDARY) {
                switch (grid.getStatus()) {
                    case Normal -> grid.setStatus(GridStatuses.GridStatus.Flagged);
                    case Flagged -> grid.setStatus(GridStatuses.GridStatus.Questioned);
                    case Questioned -> grid.setStatus(GridStatuses.GridStatus.Normal);
                }

                var bombs = variable.bombs - getSweepGrids().stream().filter(g -> g.getStatus() == GridStatuses.GridStatus.Flagged).count();
                setRemainingBombs("Bombs: " + bombs);
            } else {
                if (grid.getStatus() != GridStatuses.GridStatus.Normal) return;
                if (firstClick) {
                    setUpMineField(x, y);
                    firstClick = false;
                    gridSelection(grid);
                    timerModel.startTimer();
//                    debugMines();
                } else {
                    gridSelection(grid);
                }

                if (checkVictoryCondition()) {
                    timerModel.stopTimer();
                    Access.gameData.gamesFinished += 1;
                    Access.gameData.gamesWon += 1;
                    Access.gameData.totalTimePlayed += timerModel.getTotalTime();
                    setGameOver("YOU WIN!!");
                    getSweepGrids().forEach(g -> g.setMouseTransparent(true));
                }
            }
        };
    }

    private boolean checkVictoryCondition() {
        var list = new Enumerable<>(getSweepGrids());
        return list.where(x -> !isMine(x)).where(x -> x.getStatus() == GridStatuses.GridStatus.Selected).size() >= (list.size() - variable.bombs);
    }

    // endregion

    // region Actions

    @FXML
    private void exitAction() throws IOException {
        WindowManager.changeScene(Scenes.Scene.Main, null);
    }

    @FXML
    private void resetAction() throws IOException {
        reset();
    }

    // endregion

    // region Properties

    public ObservableList<SweepGrid> getSweepGrids() {
        if (sweepGrids.get() == null) sweepGrids.set(FXCollections.observableArrayList());
        return sweepGrids.get();
    }

    public ListProperty<SweepGrid> sweepGridsProperty() {
        return sweepGrids;
    }

    public void setSweepGrids(ObservableList<SweepGrid> sweepGrids) {
        this.sweepGrids.set(sweepGrids);
    }

    public String getGameOver() {
        return gameOver.get();
    }

    public StringProperty gameOverProperty() {
        return gameOver;
    }

    public void setGameOver(String gameOver) {
        this.gameOver.set(gameOver);
    }

    public String getRemainingBombs() {
        return remainingBombs.get();
    }

    public StringProperty remainingBombsProperty() {
        return remainingBombs;
    }

    public void setRemainingBombs(String remainingBombs) {
        this.remainingBombs.set(remainingBombs);
    }

    public String getTimer() {
        return timer.get();
    }

    public StringProperty timerProperty() {
        return timer;
    }

    public void setTimer(String timer) {
        this.timer.set(timer);
    }

    private final ListProperty<SweepGrid> sweepGrids;
    private final StringProperty gameOver;
    private final StringProperty remainingBombs;
    private final StringProperty timer;

    // endregion

    // region Fields

    private boolean firstClick = true;
    private final Enumerable<Position> mines = new Enumerable<>();
    private final TimerModel timerModel;

    // endregion

}
