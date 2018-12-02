package fall2018.csc2017.GameCenter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * The sliding tile scorer test.
 */
public class SlidingTileScorerTest {
    /**
     * The SlidingTileScorer for test.
     */
    private SlidingTileScorer slidingTileScorer;

    /**
     * Set up a sliding tile scorer for test.
     */
    @Before
    public void setUp() {
        slidingTileScorer = new SlidingTileScorer();
    }

    /**
     * Tear down.
     */
    @After
    public void tearDown() {
        slidingTileScorer = null;
    }

    /**
     * Test whether calculate score works.
     */
    @Test
    public void calculateScore() {
        assertEquals(10000, slidingTileScorer.calculateScore(3,0));
        assertEquals(10000, slidingTileScorer.calculateScore(4, 100));
        assertEquals(1000000, slidingTileScorer.calculateScore(5, 100));
    }
}