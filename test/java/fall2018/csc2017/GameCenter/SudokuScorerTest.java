package fall2018.csc2017.GameCenter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test for sudoku scorer class.
 */
public class SudokuScorerTest {
    /**
     * The sudokuScorer for test.
     */
    private SudokuScorer sudokuScorer;

    /**
     * Set up a scorer for testing it.
     */
    @Before
    public void setUp() {
        sudokuScorer = new SudokuScorer();
    }

    /**
     * Tear down.
     */
    @After
    public void tearDown() {
        sudokuScorer = null;
    }

    /**
     * Test whether calculateScore works.
     */
    @Test
    public void testCalculateScore() {
        assertEquals(495, sudokuScorer.calculateScore(1,1));
        assertEquals(904, sudokuScorer.calculateScore(2, 10));
        assertEquals(549, sudokuScorer.calculateScore(3, 100));
    }
}