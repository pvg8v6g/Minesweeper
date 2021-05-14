package access;

import data.GameData;
import encryption.Encryption;
import list.Enumerable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * This file has been created by:
 * pvaughn on
 * 4/29/2021 at
 * 14:54
 */
@SuppressWarnings("ResultOfMethodCallIgnored")
public class Access {

    // region Initialize Data

    public static void buildDataAccess() throws IOException, InterruptedException {
        setUpDocumentsLocation();
        setUpGameFolder();
        createFolderStructure();
        createGameData();
    }

    private static void setUpDocumentsLocation() throws IOException, InterruptedException {
        var roaming = System.getenv("APPDATA");
        var size = roaming.length();
        documents = roaming.substring(0, (size - 1) - 14) + "Documents\\";
    }

    private static void setUpGameFolder() {
        var path = documents + "Minesweeper\\";
        var directory = new File(path);
        if (!directory.exists()) directory.mkdir();
        gameFolder = path;
    }

    private static void createFolderStructure() {
        var list = new Enumerable<File>();
        list.add(new File(gameFolder + "Data\\"));

        list.where(x -> !x.exists()).forEach(File::mkdir);
    }

    private static void createGameData() {
        var dataFile = new File(gameFolder + "Data\\GameData.data");
        try {
            gameData = (GameData) Encryption.decryptData(new FileInputStream(dataFile));
        } catch (FileNotFoundException e) {
            gameData = new GameData();
        }
    }

    // endregion

    // region Fields

    private static String documents;
    public static String gameFolder;
    public static GameData gameData;

    // endregion

}
