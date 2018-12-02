package fall2018.csc2017.GameCenter;

import android.content.Context;

import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Tests for DetailScoreBoard class.
 */
public class DetailScoreBoardTest {

    /**
     * The DetailScoreBoard detailScoreBoard for test.
     */
    private DetailScoreBoard detailScoreBoard;

    /**
     * The Mock Context context for test.
     */
    private Context context = Mockito.mock(Context.class);

    /**
     * The String gameType for test.
     */
    private String gameType;

    /**
     * The setUp of SlidingTile ScoreBoard.
     */
    private void setUpSlidingTileScoreBoard() {
        gameType = "SlidingTile";
        detailScoreBoard = new DetailScoreBoard(gameType, context);
    }

    /**
     * The setUp for Sudoku ScoreBoard.
     */
    private void setUpSudokuScoreBoard() {
        gameType = "Sudoku";
        detailScoreBoard = new DetailScoreBoard(gameType, context);
    }

    /**
     * The setUp for Sudoku ScoreBoard.
     */
    private void setUpMineScoreBoard() {
        gameType = "Mine";
        detailScoreBoard = new DetailScoreBoard(gameType, context);
    }

    /**
     * The test for GetEasyLevel.
     */
    @Test
    public void testGetEasyLevel() {
        setUpSudokuScoreBoard();
        String testLevel = "played";
        detailScoreBoard.setEasyLevel(testLevel);
        assertEquals(testLevel, detailScoreBoard.getEasyLevel());
    }

    /**
     * The test for SetEasyLevel.
     */
    @Test
    public void testSetEasyLevel() {
        setUpSudokuScoreBoard();
        String testLevel = "played";
        detailScoreBoard.setEasyLevel(testLevel);
        assertEquals(testLevel, detailScoreBoard.getEasyLevel());
    }

    /**
     * The test for GetEasyTopOneName.
     */
    @Test
    public void testGetEasyTopOneName() {
        setUpSlidingTileScoreBoard();
        String testUserName = "user";
        detailScoreBoard.setEasyTopOneName(testUserName);
        assertEquals(testUserName, detailScoreBoard.getEasyTopOneName());
    }

    /**
     * test for SetEasyTopOneName.
     */
    @Test
    public void testSetEasyTopOneName() {
        setUpSlidingTileScoreBoard();
        String testUserName = "user";
        detailScoreBoard.setEasyTopOneName(testUserName);
        assertEquals(testUserName, detailScoreBoard.getEasyTopOneName());
    }

    /**
     * The test for GetEasyTopOneScore.
     */
    @Test
    public void testGetEasyTopOneScore() {
        setUpSlidingTileScoreBoard();
        int testScore = 10000;
        detailScoreBoard.setEasyTopOneScore(testScore);
        assertEquals(testScore, detailScoreBoard.getEasyTopOneScore());
    }

    /**
     * The test for SetEasyTopOneScore.
     */
    @Test
    public void testSetEasyTopOneScore() {
        setUpSlidingTileScoreBoard();
        int testScore = 10000;
        detailScoreBoard.setEasyTopOneScore(testScore);
        assertEquals(testScore, detailScoreBoard.getEasyTopOneScore());
    }

    /**
     * The test for GetEasyScoreList.
     */
    @Test
    public void testGetEasyScoreList() {
        setUpSlidingTileScoreBoard();
        List<Integer> testList = new ArrayList<>();
        detailScoreBoard.setEasyScoreList(testList);
        assertEquals(testList, detailScoreBoard.getEasyScoreList());
    }

    /**
     * The test for SetEasyScoreList.
     */
    @Test
    public void testSetEasyScoreList() {
        setUpSlidingTileScoreBoard();
        detailScoreBoard.setEasyScoreList(null);
        assertNull(detailScoreBoard.getEasyScoreList());
    }

    /**
     * The test for SlidingTile ModifyEasyTopOne Never Played.
     */
    @Test
    public void testSlidingTileModifyEasyTopOneNeverPlayed() {
        setUpSlidingTileScoreBoard();
        detailScoreBoard.setEasyScoreList(null);
        assertNull(detailScoreBoard.getEasyLevel());
    }

    /**
     * The test for SlidingTile ModifyEasyTopOneLevel No Data.
     */
    @Test
    public void testSlidingTileModifyEasyTopOneLevelNoData() {
        setUpSlidingTileScoreBoard();
        detailScoreBoard.setLevel("neverPlayed");
        assertNull(detailScoreBoard.getEasyTopOneName());
    }

    /**
     * The test for SlidingTile ModifyEasyTopOneEasyLevel No Data.
     */
    @Test
    public void testSlidingTileModifyEasyTopOneEasyLevelNoData() {
        setUpSlidingTileScoreBoard();
        detailScoreBoard.setEasyLevel("neverPlayed");
        assertNull(detailScoreBoard.getEasyTopOneName());
    }

    /**
     * The test for GetMediumLevel.
     */
    @Test
    public void testGetMediumLevel() {
        setUpSudokuScoreBoard();
        String testLevel = "played";
        detailScoreBoard.setMediumLevel(testLevel);
        assertEquals(testLevel, detailScoreBoard.getMediumLevel());
    }

    /**
     * The test for SetMediumLevel.
     */
    @Test
    public void testSetMediumLevel() {
        setUpSudokuScoreBoard();
        String testLevel = "played";
        detailScoreBoard.setMediumLevel(testLevel);
        assertEquals(testLevel, detailScoreBoard.getMediumLevel());
    }

    /**
     * The test for GetMediumTopOneName.
     */
    @Test
    public void testGetMediumTopOneName() {
        setUpSlidingTileScoreBoard();
        String testUserName = "user";
        detailScoreBoard.setMediumTopOneName(testUserName);
        assertEquals(testUserName, detailScoreBoard.getMediumTopOneName());
    }

    /**
     * The test for SetMediumTopOneName.
     */
    @Test
    public void testSetMediumTopOneName() {
        setUpMineScoreBoard();
        String testUserName = "user";
        detailScoreBoard.setMediumTopOneName(testUserName);
        assertEquals(testUserName, detailScoreBoard.getMediumTopOneName());
    }

    /**
     * The test for GetMediumTopOneScore.
     */
    @Test
    public void testGetMediumTopOneScore() {
        setUpSlidingTileScoreBoard();
        int testScore = 10000;
        detailScoreBoard.setMediumTopOneScore(testScore);
        assertEquals(testScore, detailScoreBoard.getMediumTopOneScore());
    }

    /**
     * The test for SetMediumTopOneScore.
     */
    @Test
    public void testSetMediumTopOneScore() {
        setUpSlidingTileScoreBoard();
        int testScore = 10000;
        detailScoreBoard.setMediumTopOneScore(testScore);
        assertEquals(testScore, detailScoreBoard.getMediumTopOneScore());
    }

    /**
     * The test for GetMediumScoreList.
     */
    @Test
    public void testGetMediumScoreList() {
        setUpSlidingTileScoreBoard();
        List<Integer> testList = new ArrayList<>();
        detailScoreBoard.setMediumScoreList(testList);
        assertEquals(testList, detailScoreBoard.getEasyScoreList());
    }

    /**
     * The test for SetMediumScoreList.
     */
    @Test
    public void testSetMediumScoreList() {
        setUpSlidingTileScoreBoard();
        detailScoreBoard.setMediumScoreList(null);
        assertNull(detailScoreBoard.getMediumScoreList());
    }

    /**
     * The test for SlidingTile ModifyMediumTopOne Never Played.
     */
    @Test
    public void testSlidingTileModifyMediumTopOneNeverPlayed() {
        setUpSlidingTileScoreBoard();
        detailScoreBoard.setMediumScoreList(null);
        assertNull(detailScoreBoard.getMediumLevel());
    }

    /**
     * The test for SlidingTile ModifyMediumTopOneLevel No Data.
     */
    @Test
    public void testSlidingTileModifyMediumTopOneLevelNoData() {
        setUpSlidingTileScoreBoard();
        detailScoreBoard.setLevel("neverPlayed");
        assertNull(detailScoreBoard.getMediumTopOneName());
    }

    /**
     * The test for SlidingTile ModifyMediumTopOneEasyLevel No Data.
     */
    @Test
    public void testSlidingTileModifyMediumTopOneEasyLevelNoData() {
        setUpSlidingTileScoreBoard();
        detailScoreBoard.setMediumLevel("neverPlayed");
        assertNull(detailScoreBoard.getMediumTopOneName());
    }

    /**
     * The test for GetHardLevel.
     */
    @Test
    public void testGetHardLevel() {
        setUpSudokuScoreBoard();
        String testLevel = "played";
        detailScoreBoard.setHardLevel(testLevel);
        assertEquals(testLevel, detailScoreBoard.getHardLevel());
    }

    /**
     * The test for SetHardLevel.
     */
    @Test
    public void testSetHardLevel() {
        setUpSudokuScoreBoard();
        String testLevel = "played";
        detailScoreBoard.setHardLevel(testLevel);
        assertEquals(testLevel, detailScoreBoard.getHardLevel());
    }

    /**
     * The test for GetHardTopOneName.
     */
    @Test
    public void testGetHardTopOneName() {
        setUpSlidingTileScoreBoard();
        String testUserName = "user";
        detailScoreBoard.setHardTopOneName(testUserName);
        assertEquals(testUserName, detailScoreBoard.getHardTopOneName());
    }

    /**
     * The test for SetHardTopOneName.
     */
    @Test
    public void testSetHardTopOneName() {
        setUpSlidingTileScoreBoard();
        String testUserName = "user";
        detailScoreBoard.setHardTopOneName(testUserName);
        assertEquals(testUserName, detailScoreBoard.getHardTopOneName());
    }

    /**
     * The test for GetHardTopOneScore.
     */
    @Test
    public void testGetHardTopOneScore() {
        setUpSlidingTileScoreBoard();
        int testScore = 10000;
        detailScoreBoard.setHardTopOneScore(testScore);
        assertEquals(testScore, detailScoreBoard.getHardTopOneScore());
    }

    /**
     * The test for SetHardTopOneScore.
     */
    @Test
    public void testSetHardTopOneScore() {
        setUpSlidingTileScoreBoard();
        int testScore = 10000;
        detailScoreBoard.setHardTopOneScore(testScore);
        assertEquals(testScore, detailScoreBoard.getHardTopOneScore());
    }

    /**
     * The test for GetHardScoreList.
     */
    @Test
    public void testGetHardScoreList() {
        setUpSlidingTileScoreBoard();
        List<Integer> testList = new ArrayList<>();
        detailScoreBoard.setHardScoreList(testList);
        assertEquals(testList, detailScoreBoard.getHardScoreList());
    }

    /**
     * The test for SetHardScoreList.
     */
    @Test
    public void testSetHardScoreList() {
        setUpSlidingTileScoreBoard();
        detailScoreBoard.setHardScoreList(null);
        assertNull(detailScoreBoard.getHardScoreList());
    }

    /**
     * The test for SlidingTile HardMediumTopOne NeverPlayed.
     */
    @Test
    public void testSlidingTileHardMediumTopOneNeverPlayed() {
        setUpSlidingTileScoreBoard();
        detailScoreBoard.setHardScoreList(null);
        assertNull(detailScoreBoard.getHardLevel());
    }

    /**
     * The test for SlidingTile ModifyHardTopOneLevel NoData.
     */
    @Test
    public void testSlidingTileModifyHardTopOneLevelNoData() {
        setUpSlidingTileScoreBoard();
        detailScoreBoard.setLevel("neverPlayed");
        assertNull(detailScoreBoard.getHardTopOneName());
    }

    /**
     * The test SlidingTile ModifyHardTopOneEasyLevel NoData.
     */
    @Test
    public void testSlidingTileModifyHardTopOneEasyLevelNoData() {
        setUpSlidingTileScoreBoard();
        detailScoreBoard.setHardLevel("neverPlayed");
        assertNull(detailScoreBoard.getHardTopOneName());
    }

    /**
     * The test GetEasyTopOne.
     */
    @Test
    public void testGetEasyTopOne() {
        setUpSlidingTileScoreBoard();
        int testScore = 10000;
        String testUserName = "user";
        String expectMassage = testScore + "\n" + testUserName;
        detailScoreBoard.setEasyTopOneName(testUserName);
        detailScoreBoard.setEasyTopOneScore(testScore);
        assertEquals(expectMassage, detailScoreBoard.getEasyTopOne());
    }

    /**
     * The test for GetMediumTopOne.
     */
    @Test
    public void testGetMediumTopOne() {
        setUpSlidingTileScoreBoard();
        int testScore = 10000;
        String testUserName = "user";
        String expectMassage = testScore + "\n" + testUserName;
        detailScoreBoard.setMediumTopOneName(testUserName);
        detailScoreBoard.setMediumTopOneScore(testScore);
        assertEquals(expectMassage, detailScoreBoard.getMediumTopOne());
    }

    /**
     * The test for GetHardTopOne.
     */
    @Test
    public void testGetHardTopOne() {
        setUpSlidingTileScoreBoard();
        int testScore = 10000;
        String testUserName = "user";
        String expectMassage = testScore + "\n" + testUserName;
        detailScoreBoard.setMediumTopOneName(testUserName);
        detailScoreBoard.setMediumTopOneScore(testScore);
        assertEquals(expectMassage, detailScoreBoard.getMediumTopOne());
    }

    /**
     * The test for GetEasyMap.
     */
    @Test
    public void testGetEasyMap() {
        setUpSlidingTileScoreBoard();
        int testScore = 10000;
        String testUserName = "user";
        List<String> testList = new ArrayList<>();
        testList.add(testUserName);
        detailScoreBoard.getEasyMap().put(testScore, testList);
        assertTrue(detailScoreBoard.getEasyMap().containsKey(testScore));
        assertEquals(testList, detailScoreBoard.getEasyMap().get(testScore));
    }

    /**
     * The test GetMediumMap.
     */
    @Test
    public void testGetMediumMap() {
        setUpSlidingTileScoreBoard();
        int testScore = 10000;
        String testUserName = "user";
        List<String> testList = new ArrayList<>();
        testList.add(testUserName);
        detailScoreBoard.getMediumMap().put(testScore, testList);
        assertTrue(detailScoreBoard.getMediumMap().containsKey(testScore));
        assertEquals(testList, detailScoreBoard.getMediumMap().get(testScore));
    }

    /**
     * The test GetHardMap.
     */
    @Test
    public void testGetHardMap() {
        setUpSlidingTileScoreBoard();
        int testScore = 10000;
        String testUserName = "user";
        List<String> testList = new ArrayList<>();
        testList.add(testUserName);
        detailScoreBoard.getHardMap().put(testScore, testList);
        assertTrue(detailScoreBoard.getHardMap().containsKey(testScore));
        assertEquals(testList, detailScoreBoard.getHardMap().get(testScore));
    }
}