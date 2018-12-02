package fall2018.csc2017.GameCenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The detail score board.
 */
public class DetailScoreBoard implements Serializable {

    /**
     * The serialVersionUID.
     */
    public static final long serialVersionUID = -2227760083029889845L;

    /**
     * The String gameType.
     */
    private String gameType;

    /**
     * The BoardManager boardManager.
     */
    private BoardManager boardManager;

    /**
     * The SudokuBoardManager sudokuBoardManager.
     */
    private SudokuBoardManager sudokuBoardManager;

    /**
     * The MineManager mineManager.
     */
    private MineManager mineManager;

    /**
     * The Map<Integer, List<String>> easyMap.
     */
    @SuppressLint("UseSparseArrays")
    private Map<Integer, List<String>> easyMap = new HashMap<>();

    /**
     * The Map<Integer, List<String>> mediumMap.
     */
    @SuppressLint("UseSparseArrays")
    private Map<Integer, List<String>> mediumMap = new HashMap<>();

    /**
     * The Map<Integer, List<String>> hardMap.
     */
    @SuppressLint("UseSparseArrays")
    private Map<Integer, List<String>> hardMap = new HashMap<>();

    /**
     * The List<Integer> easyScoreList.
     */
    private List<Integer> easyScoreList = new ArrayList<>();

    /**
     * The List<Integer> mediumScoreList.
     */
    private List<Integer> mediumScoreList = new ArrayList<>();

    /**
     * The List<Integer> hardScoreList.
     */
    private List<Integer> hardScoreList = new ArrayList<>();

    /**
     * The int easyTopOneScore.
     */
    private int easyTopOneScore;

    /**
     * The String easyTopOneName.
     */
    private String easyTopOneName;

    /**
     * The int mediumTopOneScore.
     */
    private int mediumTopOneScore;

    /**
     * The String mediumTopOneName.
     */
    private String mediumTopOneName;

    /**
     * The int hardTopOneScore.
     */
    private int hardTopOneScore;

    /**
     * The String hardTopOneName.
     */
    private String hardTopOneName;

    /**
     * The int score.
     */
    private int score = 0;

    /**
     * The String username.
     */
    private String username;

    /**
     * The String easyLevel.
     */
    private String easyLevel;

    /**
     * The String mediumLevel.
     */
    private String mediumLevel;

    /**
     * The String hardLevel.
     */
    private String hardLevel;

    /**
     * The String level.
     */
    private String level = "neverPlayed";

    /**
     * The transient Context context.
     */
    private transient Context context;

    /**
     * Init AccountManager.
     *
     * @param gameType the game's type.
     * @param context the context.
     */
    DetailScoreBoard(String gameType, Context context) {
        this.gameType = gameType;
        this.context = context;
    }

    /**
     * Set all manager in this DetailScoreBoard to null.
     */
    void destroyAllManager() {
        sudokuBoardManager = null;
        boardManager = null;
        mineManager = null;
    }

    /**
     * Set Context on this DetailScoreBoard.
     *
     * @param context allow to save and load from file.
     */
    public void setContext(Context context) {
        this.context = context;
    }

    /**
     * Set level on this DetailScoreBoard.
     *
     * @param level level of difficulty
     */
    public void setLevel(String level) {
        this.level = level;
    }

    /**
     * Update this score, userName, and manager of this DetailScoreBoard by gameType.
     */
    void collectScoreLevel() {
        switch (gameType) {
            case "SlidingTile":
                loadFromFile(username + "sliding_tmp.ser");
                if (boardManager == null) {
                    boardManager = new BoardManager(this.context, 3, false);
                }
                score = boardManager.getScore();
                if (boardManager.getSlidingTileDifficulty() != null) {
                    level = boardManager.getSlidingTileDifficulty();
                }
                username = boardManager.getUserName();
                break;
            case "Mine":
                loadFromFile(username + "mine_tmp.ser");
                if (mineManager == null) {
                    mineManager = new MineManager(this.context, username, "EASY");
                }
                score = mineManager.getScore();
                if (mineManager.getMineDifficulty() != null) {
                    level = mineManager.getMineDifficulty();
                }
                username = mineManager.getUserName();
                break;
            case "Sudoku":
                loadFromFile(username + "sudoku_tmp.ser");
                if (sudokuBoardManager == null) {
                    sudokuBoardManager = new SudokuBoardManager(this.context, "Easy");
                }
                score = sudokuBoardManager.getScore();
                if (sudokuBoardManager.getSudokuDifficulty() != null) {
                    level = sudokuBoardManager.getSudokuDifficulty();
                }
                username = sudokuBoardManager.getUserName();
                break;
        }
        if (mineManager != null) {
            if (mineManager.getLose() || mineManager.getWin()) {
                updateScore();
            }
        } else {
            if (score != 0) {
                updateScore();
            }
        }
    }

    /**
     * Update Score.
     */
    private void updateScore() {
        switch (level) {
            case "Easy":
                System.out.println(score);
                if (!easyMap.containsKey(score)) {
                    List<String> l = new ArrayList<>();
                    l.add(username);
                    easyMap.put(score, l);
                } else {
                    easyMap.get(score).add(username);
                }
                break;
            case "Medium":
                if (!mediumMap.containsKey(score)) {
                    List<String> l = new ArrayList<>();
                    l.add(username);
                    mediumMap.put(score, l);
                } else {
                    mediumMap.get(score).add(username);
                }
                break;
            case "Hard":
                if (!hardMap.containsKey(score)) {
                    List<String> l = new ArrayList<>();
                    l.add(username);
                    hardMap.put(score, l);
                } else {
                    hardMap.get(score).add(username);
                }
                break;
        }
    }

    /**
     * create SortedList by level.
     */
    void createSortedList() {
        switch (level) {
            case "Easy":
                if (!easyScoreList.contains(score)) {
                    easyScoreList.add(score);
                    Collections.sort(easyScoreList);
                }
                break;
            case "Medium":
                if (!mediumScoreList.contains(score)) {
                    mediumScoreList.add(score);
                    Collections.sort(mediumScoreList);
                }
                break;
            case "Hard":
                if (!hardScoreList.contains(score)) {
                    hardScoreList.add(score);
                    Collections.sort(hardScoreList);
                }
                break;
            case "neverPlayed":
                break;
        }
    }

    /**
     * The find the user name of top one player.
     *
     * @param oldTopOneScore the score of previous top one player.
     * @param oldTopOneName  the name of previous top one player.
     * @param newScore       the score of current player.
     * @param newName        the name of current player.
     * @return The top one player.
     */
    private String findTopOne(int oldTopOneScore, String oldTopOneName,
                              int newScore, String newName) {
        if (newScore > oldTopOneScore) {
            return newName;
        } else if (!gameType.equals("Mine")&&newScore == 0 && oldTopOneName != null) {
            if (oldTopOneName.equals("No data")) {
                return newName;
            }
        }
        return oldTopOneName;
    }

    /**
     * Get easy level.
     *
     * @return easyLevel.
     */
    String getEasyLevel() {
        return easyLevel;
    }

    /**
     * Set easy level.
     *
     * @param easyLevel easy level.
     */
    void setEasyLevel(String easyLevel) {
        this.easyLevel = easyLevel;
    }

    /**
     * Get Easy TopOne Name.
     *
     * @return Easy TopOne Name.
     */
    String getEasyTopOneName() {
        return easyTopOneName;
    }

    /**
     * Set Easy TopOne Name.
     *
     * @param easyTopOneName Easy TopOne Name.
     */
    void setEasyTopOneName(String easyTopOneName) {
        this.easyTopOneName = easyTopOneName;
    }

    /**
     * Get Easy TopOne score.
     *
     * @return Easy TopOne score.
     */
    int getEasyTopOneScore() {
        return easyTopOneScore;
    }

    /**
     * Set Easy TopOne score.
     *
     * @param easyTopOneScore Easy TopOne score.
     */
    void setEasyTopOneScore(int easyTopOneScore) {
        this.easyTopOneScore = easyTopOneScore;
    }

    /**
     * Get Easy ScoreList.
     *
     * @return Easy ScoreList.
     */
    List<Integer> getEasyScoreList() {
        return easyScoreList;
    }

    /**
     * Set Easy ScoreList.
     *
     * @param easyScoreList Easy ScoreList.
     */
    void setEasyScoreList(List<Integer> easyScoreList) {
        this.easyScoreList = easyScoreList;
    }

    /**
     * modify Easy TopOne.
     */
    void modifyEasyTopOne() {
        if (easyScoreList.isEmpty()) {
            easyLevel = "neverPlayed";
        } else {
            easyLevel = "played";
        }
        if (level.equals("neverPlayed") || easyLevel.equals("neverPlayed") ||
                findTopOne(easyTopOneScore, easyTopOneName, score, username) == null) {
            easyTopOneName = "No data";
        } else {
            easyTopOneName = findTopOne(easyTopOneScore, easyTopOneName, score, username);
            easyTopOneScore = easyScoreList.get(easyScoreList.size() - 1);
        }
    }

    /**
     * get Medium Level.
     *
     * @return Medium Level.
     */
    String getMediumLevel() {
        return mediumLevel;
    }

    /**
     * set Medium Level.
     *
     * @param mediumLevel Medium Level.
     */
    void setMediumLevel(String mediumLevel) {
        this.mediumLevel = mediumLevel;
    }

    /**
     * get Medium TopOne Name.
     *
     * @return Medium TopOne Name.
     */
    String getMediumTopOneName() {
        return mediumTopOneName;
    }

    /**
     * set Medium TopOne Name.
     *
     * @param mediumTopOneName Medium TopOne Name.
     */
    void setMediumTopOneName(String mediumTopOneName) {
        this.mediumTopOneName = mediumTopOneName;
    }

    /**
     * get Medium TopOne Score.
     *
     * @return Medium TopOne Score.
     */
    int getMediumTopOneScore() {
        return mediumTopOneScore;
    }

    /**
     * set Medium TopOne Score.
     *
     * @param mediumTopOneScore Medium TopOne Score.
     */
    void setMediumTopOneScore(int mediumTopOneScore) {
        this.mediumTopOneScore = mediumTopOneScore;
    }

    /**
     * get Medium ScoreList.
     *
     * @return Medium ScoreList.
     */
    List<Integer> getMediumScoreList() {
        return mediumScoreList;
    }

    /**
     * set MediumScore List.
     *
     * @param mediumScoreList MediumScore List.
     */
    void setMediumScoreList(List<Integer> mediumScoreList) {
        this.mediumScoreList = mediumScoreList;
    }

    /**
     * modify Medium TopOne.
     */
    void modifyMediumTopOne() {
        if (mediumScoreList.isEmpty()) {
            mediumLevel = "neverPlayed";
        } else {
            mediumLevel = "played";
        }
        if (level.equals("neverPlayed") || mediumLevel.equals("neverPlayed")
                || findTopOne(mediumTopOneScore, mediumTopOneName, score, username) == null) {
            mediumTopOneName = "No data";
        } else {
            mediumTopOneName = findTopOne(mediumTopOneScore, mediumTopOneName, score, username);
            mediumTopOneScore = mediumScoreList.get(mediumScoreList.size() - 1);
        }
    }

    /**
     * get Hard Level.
     *
     * @return Hard Level.
     */
    String getHardLevel() {
        return hardLevel;
    }

    /**
     * set Hard Level.
     *
     * @param hardLevel Hard Level.
     */
    void setHardLevel(String hardLevel) {
        this.hardLevel = hardLevel;
    }

    /**
     * get Hard TopOne Score.
     *
     * @return Hard TopOne Score.
     */
    int getHardTopOneScore() {
        return hardTopOneScore;
    }

    /**
     * set Hard TopOne Score.
     *
     * @param hardTopOneScore Hard TopOne Score.
     */
    void setHardTopOneScore(int hardTopOneScore) {
        this.hardTopOneScore = hardTopOneScore;
    }

    /**
     * get Hard TopOne Name.
     *
     * @return Hard TopOne Name.
     */
    String getHardTopOneName() {
        return hardTopOneName;
    }

    /**
     * set Hard TopOne Name.
     *
     * @param hardTopOneName Hard TopOne Name.
     */
    void setHardTopOneName(String hardTopOneName) {
        this.hardTopOneName = hardTopOneName;
    }

    /**
     * get Hard ScoreList.
     *
     * @return Hard ScoreList.
     */
    List<Integer> getHardScoreList() {
        return hardScoreList;
    }

    /**
     * set Hard ScoreList.
     *
     * @param hardScoreList Hard ScoreList.
     */
    void setHardScoreList(List<Integer> hardScoreList) {
        this.hardScoreList = hardScoreList;
    }

    /**
     * modify Hard TopOne.
     */
    void modifyHardTopOne() {
        if (hardScoreList.isEmpty()) {
            hardLevel = "neverPlayed";
        } else {
            hardLevel = "played";
        }
        if (level.equals("neverPlayed") || hardLevel.equals("neverPlayed") ||
                findTopOne(hardTopOneScore, hardTopOneName, score, username) == null) {
            hardTopOneName = "No data";
        } else {
            hardTopOneName = findTopOne(hardTopOneScore, hardTopOneName, score, username);
            hardTopOneScore = hardScoreList.get(hardScoreList.size() - 1);
        }
    }

    /**
     * get Easy TopOne.
     *
     * @return Easy TopOne.
     */
    public String getEasyTopOne() {
        if (easyTopOneName.equals("No data")) {
            return easyTopOneName;
        }
        return easyTopOneScore + "\n" + easyTopOneName;
    }

    /**
     * get Medium TopOne.
     *
     * @return Medium TopOne.
     */
    public String getMediumTopOne() {
        if (mediumTopOneName.equals("No data")) {
            return mediumTopOneName;
        }
        return mediumTopOneScore + "\n" + mediumTopOneName;
    }

    /**
     * get Hard TopOne.
     *
     * @return Hard TopOne.
     */
    public String getHardTopOne() {
        if (hardTopOneName.equals("No data")) {
            return hardTopOneName;
        }
        return hardTopOneScore + "\n" + hardTopOneName;
    }

    /**
     * get Easy Map.
     *
     * @return Easy Map.
     */
    Map<Integer, List<String>> getEasyMap() {
        return easyMap;
    }

    /**
     * get Easy SortedList.
     *
     * @return SortedList.
     */
    ArrayList<String> getEasySortedList() {
        ArrayList<String> sortedList = new ArrayList<>();
        if (!gameType.equals("Mine")&&easyScoreList.size()>0&&easyScoreList.get(0)==0)
            easyScoreList.remove(0);
        if (!level.equals("neverPlayed") && !easyLevel.equals("neverPlayed")
                && !easyTopOneName.equals("No data") && easyScoreList.size() > 1) {
            easyMap.get(easyTopOneScore).remove(easyTopOneName);
            if (easyMap.get(easyTopOneScore).isEmpty() && easyScoreList.size() == 1) {
                sortedList.add("No data");
            }
            for (int i = easyScoreList.size() - 1; i > 0; i--) {
                for (int j = 0; j < easyMap.get(easyScoreList.get(i)).size(); j++) {
                    sortedList.add(easyScoreList.get(i) + "  " +
                            easyMap.get(easyScoreList.get(i)).get(j));
                }
            }
            if (easyMap.get(easyScoreList.get(0)) != null) {
                if (easyScoreList.get(0) != 0 || (gameType.equals("Mine"))) {
                    for (int j = 0; j < easyMap.get(easyScoreList.get(0)).size(); j++) {
                        sortedList.add(easyScoreList.get(0) + "  " +
                                easyMap.get(easyScoreList.get(0)).get(j));
                    }
                }
            }
            easyMap.get(easyTopOneScore).add(0, easyTopOneName);
        } else {
            sortedList.add("No data");
        }
        if (sortedList.size() > 1) sortedList.remove("No data");
        return sortedList;
    }

    /**
     * get Medium Map.
     *
     * @return Medium Map.
     */
    Map<Integer, List<String>> getMediumMap() {
        return mediumMap;
    }

    /**
     * get Medium SortedList.
     *
     * @return Medium SortedList.
     */
    ArrayList<String> getMediumSortedList() {
        ArrayList<String> sortedList = new ArrayList<>();
        if (!gameType.equals("Mine")&&mediumScoreList.size()>0&&mediumScoreList.get(0)==0) mediumScoreList.remove(0);
        if (!level.equals("neverPlayed") && !mediumLevel.equals("neverPlayed") &&
                !mediumTopOneName.equals("No data") && mediumScoreList.size() > 1) {
            mediumMap.get(mediumTopOneScore).remove(mediumTopOneName);
            if (mediumMap.get(mediumTopOneScore).isEmpty() && mediumScoreList.size() == 1) {
                sortedList.add("No data");
            }
            for (int i = mediumScoreList.size() - 1; i > 0; i--) {
                for (int j = 0; j < mediumMap.get(mediumScoreList.get(i)).size(); j++) {
                    sortedList.add(mediumScoreList.get(i) + "  " +
                            mediumMap.get(mediumScoreList.get(i)).get(j));
                }
            }
            if (mediumMap.get(mediumScoreList.get(0)) != null) {
                if (mediumScoreList.get(0) != 0 || (gameType.equals("Mine"))) {
                    for (int j = 0; j < mediumMap.get(mediumScoreList.get(0)).size(); j++) {
                        sortedList.add(mediumScoreList.get(0) + "  " +
                                mediumMap.get(mediumScoreList.get(0)).get(j));
                    }
                }
            }
            mediumMap.get(mediumTopOneScore).add(0, mediumTopOneName);
        } else {
            sortedList.add("No data");
        }
        if (sortedList.size() > 1) {
            sortedList.remove("No data");
        }
        return sortedList;
    }

    /**
     * get HardMap.
     *
     * @return HardMap.
     */
    Map<Integer, List<String>> getHardMap() {
        return hardMap;
    }

    /**
     * Get hard sorted list.
     *
     * @return the sorted list.
     */
    ArrayList<String> getHardSortedList() {
        ArrayList<String> sortedList = new ArrayList<>();
        if (!gameType.equals("Mine")&&hardScoreList.size()>0&&hardScoreList.get(0)==0)
            hardScoreList.remove(0);
        if (!level.equals("neverPlayed") && !hardLevel.equals("neverPlayed") &&
                !hardTopOneName.equals("No data") && hardScoreList.size() > 1) {
            hardMap.get(hardTopOneScore).remove(hardTopOneName);
            if (hardMap.get(hardTopOneScore).isEmpty() && hardScoreList.size() == 1) {
                sortedList.add("No data");
            }
            for (int i = hardScoreList.size() - 1; i > 0; i--) {
                for (int j = 0; j < hardMap.get(hardScoreList.get(i)).size(); j++) {
                    sortedList.add(hardScoreList.get(i) + "  " +
                            hardMap.get(hardScoreList.get(i)).get(j));
                }
            }
            if (hardMap.get(hardScoreList.get(0)) != null) {
                if (hardScoreList.get(0) != 0 || (gameType.equals("Mine"))) {
                    for (int j = 0; j < hardMap.get(hardScoreList.get(0)).size(); j++) {
                        sortedList.add(hardScoreList.get(0) + "  " +
                                hardMap.get(hardScoreList.get(0)).get(j));
                    }
                }
            }
            hardMap.get(hardTopOneScore).add(0, hardTopOneName);
        } else {
            sortedList.add("No data");
        }
        if (sortedList.size() > 1) {
            sortedList.remove("No data");
        }
        return sortedList;
    }

    /**
     * Return Highest score by a given username;
     *
     * @param username given username
     * @return Highest score
     */
    int getHighestScoreByUser(String username) {
        int highestScore = 0;
        List<Integer> scores = new ArrayList<>();
        for (int index = 0; index < easyScoreList.size(); index++) {
            if (easyMap.get(easyScoreList.get(index)) != null) {
                if (easyMap.get(easyScoreList.get(index)).contains(username)) {
                    scores.add(easyScoreList.get(index));
                }
            }
        }
        for (int index = 0; index < mediumScoreList.size(); index++) {
            if (mediumMap.get(mediumScoreList.get(index)) != null) {
                if (mediumMap.get(mediumScoreList.get(index)).contains(username)) {
                    scores.add(mediumScoreList.get(index));
                }
            }
        }
        for (int index = 0; index < hardScoreList.size(); index++) {
            if (hardMap.get(hardScoreList.get(index)) != null) {
                if (hardMap.get(hardScoreList.get(index)).contains(username)) {
                    scores.add(hardScoreList.get(index));
                }
            }
        }
        if (scores.size() > 0) {
            Collections.sort(scores);
            highestScore = scores.get(scores.size() - 1);
        }
        return highestScore;
    }

    /**
     * Load the slidingTile manager from fileName.
     *
     * @param fileName the name of the file
     */
    private void loadFromFile(String fileName) {

        try {
            InputStream inputStream = this.context.openFileInput(fileName);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                switch (gameType) {
                    case "SlidingTile":
                        boardManager = (BoardManager) input.readObject();
                        break;
                    case "Mine":
                        mineManager = (MineManager) input.readObject();
                        break;
                    case "Sudoku":
                        sudokuBoardManager = (SudokuBoardManager) input.readObject();
                        break;
                }
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("login activity", "File contained unexpected data type: " + e.toString());
        }
    }
}
