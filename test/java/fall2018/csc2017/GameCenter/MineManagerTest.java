package fall2018.csc2017.GameCenter;

import android.content.Context;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Timer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.spy;

/**
 * Test mine manager test.
 */
public class MineManagerTest {

    /**
     * The mocked context for mine manager test.
     */
    private Context context;
    /**
     * The mine manager for test.
     */
    private MineManager mineManager;

    /**
     * Set up a mine manager for test.
     */
    @Before
    public void setUp() {
        context = Mockito.mock(Context.class);
        mineManager = new MineManager(context, "userName", "Medium");
        mineManager = spy(mineManager);
    }

    /**
     * Tear down.
     */
    @After
    public void tearDown() {
        mineManager = null;
    }

    /**
     * Test whether SetLose works.
     */
    @Test
    public void testSetLose() {
        setUp();
        assertFalse(mineManager.getLose());
        mineManager.setLose();
        assertTrue(mineManager.getLose());
    }

    /**
     * Test whether SetTime works.
     */
    @Test
    public void testSetTime(){
        setUp();
        mineManager.setTime(0);
        assertEquals(0 , mineManager.getTime());
    }

    /**
     * Test whether SetScore works.
     */
    @Test
    public void testSetScore() {
        setUp();
        mineManager.setScore(0);
        assertEquals(0 , mineManager.getScore());
    }

    /**
     * Test whether GetMineBoard works.
     */
    @Test
    public void testGetMineBoard() {
        setUp();
        assertFalse(mineManager.getMineBoard().getMineTile(15, 15).getIsOpened());
    }

    /**
     * Test whether GetContext works.
     */
    @Test
    public void testGetContext() {
        setUp();
        assertEquals(context, mineManager.getContext());
    }

    /**
     * Test whether GetMineDifficulty works.
     */
    @Test
    public void testGetMineDifficulty() {
        setUp();
        assertEquals("Medium", mineManager.getMineDifficulty());
        mineManager = new MineManager(context, "userName", "Easy");
        assertEquals("Easy", mineManager.getMineDifficulty());
        mineManager = new MineManager(context, "userName", "Hard");
        assertEquals("Hard", mineManager.getMineDifficulty());
        mineManager = new MineManager(context, "userName", "");
        assertEquals(0, mineManager.getMineBoard().getNumBoom());
    }

    /**
     * Test whether GetMineTiles works.
     */
    @Test
    public void testGetMineTiles() {
        setUp();
        assertEquals(256, mineManager.getMineTiles().size());
    }

    /**
     * Test whether addTime works.
     */
    @Test
    public void testAddTime() {
        setUp();
        mineManager.addTime(10);
        assertEquals(10, mineManager.getTime());
    }

    /**
     * Test whether getScorer works.
     */
    @Test
    public void testGetScorer() {
        setUp();
        assertEquals(MineScorer.class, mineManager.getScorer().getClass());
    }

    /**
     * Test whether setTimer works.
     */
    @Test
    public void testSetTimer() {
        setUp();
        mineManager.setTimer(new Timer());
        assertEquals(Timer.class, mineManager.getTimer().getClass());
    }

    /**
     * Test whether setWin works.
     */
    @Test
    public void testSetWin() {
        setUp();
        mineManager.setWin();
        assertTrue(mineManager.getWin());
    }

    /**
     * Test whether CreateTiles works.
     */
    @Test
    public void testCreateTiles() {
        setUp();
        assertEquals(256, mineManager.getMineTiles().size());
    }

    /**
     * Test whether PuzzleSolved works.
     */
    @Test
    public void testPuzzleSolved() {
        setUp();
        assertFalse(mineManager.puzzleSolved());
        mineManager.getMineBoard().touchOpen(0);
        for (int row = 0; row < MineBoard.getSize(); row++) {
            for (int col = 0; col < MineBoard.getSize(); col++) {
                if (mineManager.getMineBoard().getMineTile(row, col).getValue() == -1) {
                    mineManager.getMineBoard().replaceToFlag(row, col);
                } else {
                    mineManager.getMineBoard().replaceToTrue(row, col);
                }
            }
        }
        assertTrue(mineManager.puzzleSolved());
    }

    /**
     * Test whether IsValidTap works.
     */
    @Test
    public void testIsValidTap() {
        setUp();
        assertFalse(mineManager.getMineBoard().getMineTile(0,0).getIsOpened());
        assertTrue(mineManager.isValidTap(0));
        mineManager.getMineBoard().touchOpen(0);
        assertFalse(mineManager.isValidTap(0));
    }

    /**
     * Test whether Failing works.
     */
    @Test
    public void testFailing() {
        setUp();
        assertEquals(0, mineManager.getScore());
        mineManager.setScore(50000);
        assertEquals(50000, mineManager.getScore());
        mineManager.failing();
        assertEquals(-1, mineManager.getScore());

    }

    /**
     * Test whether Winning works.
     */
    @Test
    public void testWinning() {
        setUp();
        for (int i = 0; i != 10; i++) {
            mineManager.scorer.run();
        }
        mineManager.getMineBoard().setNumBoom(10);
        mineManager.winning();
        assertTrue(9463 == mineManager.getScore() || mineManager.getScore() == 9511);
    }

    /**
     * Test whether SetUserName works.
     */
    @Test
    public void testSetUserName() {
        setUp();
        assertEquals("userName", mineManager.getUserName());
        mineManager.setUserName("A");
        assertEquals("A", mineManager.getUserName());
    }
}