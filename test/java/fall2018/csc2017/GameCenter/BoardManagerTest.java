package fall2018.csc2017.GameCenter;

import android.content.Context;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BoardManagerTest {
    /**
     * The Boardmanagers for test. No4 is being solvable shuffle
     */
    private BoardManager boardManager1;
    private BoardManager boardManager2;
    private BoardManager boardManager3;
    private BoardManager boardManager4;
    /**
     * Context for test.
     */
    private Context context;

    /**
     * Set up three BoardManager for tests
     */
    @Before
    public void setUp() {
        context = Mockito.mock(Context.class);
        boardManager1 = new BoardManager(context, 3, true);
        boardManager2 = new BoardManager(context, 4, true);
        boardManager3 = new BoardManager(context, 5, true);
        boardManager4 = new BoardManager(context, 4, false);
    }

    /**
     * Tear down after test
     */
    @After
    public void tearDown() {
        context = null;
        boardManager1 = null;
        boardManager2 = null;
        boardManager3 = null;
        boardManager4 = null;
    }

    /**
     * Test getUndoLimit method
     */
    @Test
    public void getUndoLimit() {
        assertEquals(0, boardManager1.getUndoLimit());
        assertEquals(0, boardManager2.getUndoLimit());
        assertEquals(0, boardManager3.getUndoLimit());
    }

    /**
     * Test getUndoLimit3 method
     */
    @Test
    public void getUndoLimit3() {
        assertEquals(3, boardManager1.getUndoLimit3());
        assertEquals(3, boardManager2.getUndoLimit3());
        assertEquals(3, boardManager3.getUndoLimit3());
    }

    /**
     * Test createTiles method
     */
    @Test
    public void testCreateTiles() {
    }

    /**
     * Test getNumMoves method
     */
    @Test
    public void getNumMoves() {
        assertEquals(0, boardManager1.getNumMoves());
        boardManager1.touchMove(7);
        assertEquals(1, boardManager1.getNumMoves());
    }

    /**
     * Test setScore method
     */
    @Test
    public void setScore(){
        int testScore = 0;
        boardManager1.setScore(testScore);
        assertEquals(testScore, boardManager1.getScore());
    }

    /**
     * Test getScore method
     */
    @Test
    public void getScore() {
        int testScore = 0;
        boardManager1.setScore(testScore);
        assertEquals(testScore, boardManager1.getScore());
    }

    /**
     * Test getSlidingTileDifficulty method
     */
    @Test
    public void getSlidingTileDifficulty() {
        assertEquals("Easy", boardManager1.getSlidingTileDifficulty());
        assertEquals("Medium", boardManager2.getSlidingTileDifficulty());
        assertEquals("Hard", boardManager3.getSlidingTileDifficulty());
    }

    /**
     * Test puzzleSolved method
     */
    @Test
    public void puzzleSolved() {
        assertTrue(boardManager2.puzzleSolved());
        assertFalse(boardManager4.puzzleSolved());
    }

    /**
     * Test isValidTap method
     */
    @Test
    public void isValidTap() {
//        assertEquals(True, boardManager.isValidTap());
    }

    /**
     * Test touchMove method
     */
    @Test
    public void touchMove() {
        boardManager1.touchMove(7);
        assertEquals(1, boardManager1.getNumMoves());
        boardManager1.touchMove(4);
        assertEquals(2, boardManager1.getNumMoves());
        boardManager1.touchMove(7);
        assertEquals(3, boardManager1.getNumMoves());
        boardManager1.touchMove(8);
        assertEquals(4, boardManager1.getNumMoves());
        assertTrue(boardManager1.puzzleSolved());
    }

    /**
     * Test undo method
     */
    @Test
    public void undo() {
        boardManager2.touchMove(14);
        boardManager2.touchMove(10);
        boardManager2.touchMove(11);
        boardManager2.touchMove(15);
        boardManager2.undo();
        boardManager2.undo();
        boardManager2.undo();
        boardManager2.undo();
        assertTrue(boardManager2.puzzleSolved());
        assertEquals(0,boardManager2.getUndoLimit());
    }

    /**
     * Test undo3 method
     */
    @Test
    public void undo3() {
        boardManager2.touchMove(14);
        boardManager2.touchMove(10);
        boardManager2.touchMove(11);
        boardManager2.touchMove(15);
        boardManager2.undo3();
        boardManager2.undo3();
        assertEquals(1, boardManager2.getUndoLimit3());
        boardManager2.undo3();
        boardManager2.undo3();
        assertFalse(boardManager2.puzzleSolved());
        boardManager2.touchMove(15);
        assertTrue(boardManager2.puzzleSolved());
    }

    /**
     * Test getUserName method
     */
    @Test
    public void getUserName() {
        boardManager4.setUserName("A");
        assertEquals("A", boardManager4.getUserName());
    }

    /**
     * Test setUserName method
     */
    @Test
    public void setUserName() {
        boardManager1.setUserName("George");
        assertEquals("George", boardManager1.getUserName());
    }

    /**
     * Test getLevel method
     */
    @Test
    public void getLevel() {
        assertEquals(3, boardManager1.getLevel());
        assertEquals(4, boardManager2.getLevel());
        assertEquals(5, boardManager3.getLevel());
    }

    /**
     * Test Get SlidingTile.
     */
    @Test
    public void testGetSlidingTile(){
        setUp();
        int expectLevel = 3;
        SlidingTile testSlidingTile = boardManager1.getSlidingTile();

        assertEquals(expectLevel, testSlidingTile.getLevel());
    }
}