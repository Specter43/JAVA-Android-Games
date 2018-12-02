package fall2018.csc2017.GameCenter;

import android.content.Context;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Timer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class SudokuBoardManagerTest {

    /**
     * The SudokuBoardManager for test.
     */
    private SudokuBoardManager sudokuBoardManager1;
    private SudokuBoardManager sudokuBoardManager2;
    private SudokuBoardManager sudokuBoardManager3;

    /**
     * Timer for test.
     */
    private Timer timer = null;

    /**
     * Context for test.
     */
    private Context context;

    /**
     * Set up three sudokuBoardManager for tests
     */
    @Before
    public void setUp() {
        context = Mockito.mock(Context.class);
        sudokuBoardManager1 = new SudokuBoardManager(context,"Easy");
        sudokuBoardManager2 = new SudokuBoardManager(context,"Medium");
        sudokuBoardManager3 = new SudokuBoardManager(context,"Hard");
    }

    /**
     * Tear down after test
     */
    @After
    public void tearDown() {
        context = null;
        sudokuBoardManager1 = null;
        sudokuBoardManager2 = null;
        sudokuBoardManager3 = null;
    }

    /**
     * Test getTime method
     */
    @Test
    public void getTime() {
        assertEquals(0, sudokuBoardManager1.getTime());
    }

    /**
     * Test getScore method
     */
    @Test
    public void getScore() {
        assertEquals(0, sudokuBoardManager1.getScore());
    }

    /**
     * Test setScore method
     */
    @Test
    public void setScore() {
        sudokuBoardManager1.setScore(100);
        assertEquals(100, sudokuBoardManager1.getScore());
    }

    /**
     * Test getSudokuDifficulty method
     */
    @Test
    public void getSudokuDifficulty() {
        assertEquals("Easy", sudokuBoardManager1.getSudokuDifficulty());
        assertEquals("Medium", sudokuBoardManager2.getSudokuDifficulty());
        assertEquals("Hard", sudokuBoardManager3.getSudokuDifficulty());
    }

    /**
     * Test setTime method
     */
    @Test
    public void setTime() {
        sudokuBoardManager1.setTime(19);
        assertEquals(19 , sudokuBoardManager1.getTime());
    }

    /**
     * Test getUserName method
     */
    @Test
    public void getUserName() {
        assertNull(sudokuBoardManager1.getUserName());
    }

    /**
     * Test setUserName method
     */
    @Test
    public void setUserName() {
        sudokuBoardManager1.setUserName("Tom");
        assertEquals("Tom", sudokuBoardManager1.getUserName());
    }

    /**
     * Test getDifficulty method
     */
    @Test
    public void getDifficulty(){
        assertEquals(30, sudokuBoardManager1.getDifficulty());
        assertEquals(40, sudokuBoardManager2.getDifficulty());
        assertEquals(50, sudokuBoardManager3.getDifficulty());
    }

    /**
     * Test getTimer method
     */
    @Test
    public void getTimer(){
        sudokuBoardManager1.setTimer(null);
        assertEquals(timer, sudokuBoardManager1.getTimer());
    }

    /**
     * Test addTime method
     */
    @Test
    public void addTime(){
        sudokuBoardManager1.addTime(100);
        assertEquals(100, sudokuBoardManager1.getTime());
    }

    /**
     * Test setTimer method
     */
    @Test
    public void setTimer(){
        sudokuBoardManager1.setTimer(timer);
        assertEquals(0, sudokuBoardManager1.getTime());
    }

    /**
     * Test puzzleSolved method
     */
    @Test
    public void puzzleSolved() {
        assertFalse(sudokuBoardManager1.puzzleSolved());
    }

    /**
     * Test wining method
     */
    @Test
    public void wining() {
        sudokuBoardManager1.wining();
        assertEquals(0, sudokuBoardManager1.getTime());
    }

    /**
     * Test checkCol method
     */
    @Test
    public void checkCol() {
        int[] a = new int[]{1,2,3,4,5,6,7,8,9,1,2,3,4,5,6,7,8,9,1,2,3,4,5,6,7,8,9,1,2,3,4,5,6,7,8,9,1,2,3,4,5,6,7,8,9,1,2,3,4,5,6,7,8,9,1,2,3,4,5,6,7,8,9,1,2,3,4,5,6,7,8,9,1,2,3,4,5,6,7,8,9};
        int[] b = new int[]{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1};
        int[] c = new int[]{1,2,3,4,5,6,7,8,9,1,2,3,4,5,6,7,8,9,1,2,3,4,5,6,7,8,9,1,2,3,4,5,6,7,8,9,1,2,3,4,5,6,7,8,9,101,2,3,4,5,6,7,8,9,1,2,3,4,5,6,7,8,9,1,2,3,4,5,6,7,8,9,1,2,3,4,5,6,7,8,9};
        assertTrue(sudokuBoardManager1.checkCol(a));
        assertFalse(sudokuBoardManager1.checkCol(b));
        assertTrue(sudokuBoardManager1.checkCol(c));
    }

    /**
     * Test checkRow method
     */
    @Test
    public void checkRow() {
        int[] a = new int[]{1,2,3,4,5,6,7,8,9,1,2,3,4,5,6,7,8,9,1,2,3,4,5,6,7,8,9,1,2,3,4,5,6,7,8,9,1,2,3,4,5,6,7,8,9,1,2,3,4,5,6,7,8,9,1,2,3,4,5,6,7,8,9,1,2,3,4,5,6,7,8,9,1,2,3,4,5,6,7,8,9};
        int[] b = new int[]{2,3,1,6,7,8,4,9,5,9,8,6,5,4,1,7,2,3,4,7,5,2,9,3,1,6,8,8,9,2,4,1,7,3,5,6,5,1,4,3,8,6,9,7,2,7,6,3,9,2,5,8,1,4,6,5,8,7,3,9,2,4,1,1,4,7,8,6,2,5,3,9,3,2,9,1,5,4,6,8,7};
        int[] c = new int[]{102,3,1,6,7,8,4,9,5,9,8,6,5,4,1,7,2,3,4,7,5,2,9,3,1,6,8,8,9,2,4,1,7,3,5,6,5,1,4,3,8,6,9,7,2,7,6,3,9,2,5,8,1,4,6,5,8,7,3,9,2,4,1,1,4,7,8,6,2,5,3,9,3,2,9,1,5,4,6,8,7};
        assertFalse(sudokuBoardManager1.checkRow(a));
        assertTrue(sudokuBoardManager1.checkRow(b));
        assertTrue(sudokuBoardManager1.checkRow(c));
    }

    /**
     * Test checkSquare method
     */
    @Test
    public void checkSquare() {
        int[] a = new int[]{1,2,3,4,5,6,7,8,9,1,2,3,4,5,6,7,8,9,1,2,3,4,5,6,7,8,9,1,2,3,4,5,6,7,8,9,1,2,3,4,5,6,7,8,9,1,2,3,4,5,6,7,8,9,1,2,3,4,5,6,7,8,9,1,2,3,4,5,6,7,8,9,1,2,3,4,5,6,7,8,9};
        int[] b = new int[]{2,3,1,6,7,8,4,9,5,9,8,6,5,4,1,7,2,3,4,7,5,2,9,3,1,6,8,8,9,2,4,1,7,3,5,6,5,1,4,3,8,6,9,7,2,7,6,3,9,2,5,8,1,4,6,5,8,7,3,9,2,4,1,1,4,7,8,6,2,5,3,9,3,2,9,1,5,4,6,8,7};
        int[] c = new int[]{-98,3,1,6,7,8,4,9,5,9,8,6,5,4,-99,7,2,3,4,7,5,2,9,3,1,6,8,8,9,2,4,1,7,3,5,6,5,1,4,3,8,6,9,7,2,7,6,3,9,2,5,8,1,4,6,5,8,7,3,9,2,4,1,1,4,7,8,6,2,5,3,9,3,2,9,1,5,4,6,8,7};
        assertFalse(sudokuBoardManager1.checkSquare(a));
        assertTrue(sudokuBoardManager1.checkSquare(b));
        assertFalse(sudokuBoardManager1.checkRow(c));
    }

    /**
     * Test makeMove method
     */
    @Test
    public void makeMove() {
        sudokuBoardManager1.setDifficulty(1);
        assertFalse(sudokuBoardManager1.puzzleSolved());
        sudokuBoardManager1.setMove(1);
        sudokuBoardManager1.makeMove(1);
        assertFalse(sudokuBoardManager1.puzzleSolved());
    }

    /**
     * Test clear method
     */
    @Test
    public void clear() {
        sudokuBoardManager1.clear();
        assertFalse(sudokuBoardManager1.puzzleSolved());
    }

    /**
     * Test undo method
     */
    @Test
    public void undo() {
        sudokuBoardManager1.makeMove(1);
        sudokuBoardManager1.makeMove(10);
        sudokuBoardManager1.undo();
        sudokuBoardManager1.undo();
        assertFalse(sudokuBoardManager1.puzzleSolved());
    }
}