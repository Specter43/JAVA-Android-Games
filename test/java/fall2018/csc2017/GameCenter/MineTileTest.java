package fall2018.csc2017.GameCenter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Test for mine tile class.
 */
public class MineTileTest {
    /**
     * The mineTile for test.
     */
    private MineTile mineTile;

    /**
     * Set up a empty mine tile for test.
     */
    @Before
    public void setUp() {
        mineTile = new MineTile(0, false);
    }

    /**
     * Tear down.
     */
    @After
    public void tearDown() {
        mineTile = null;
    }

    /**
     * Test whether the getValue works.
     */
    @Test
    public void testGetValue() {
        assertEquals(0, mineTile.getValue());
        for (int i=-1; i < 9; i++) {
            mineTile = new MineTile(i, true);
            assertEquals(i, mineTile.getValue());
        }
    }

    /**
     * Test whether the getIsOpened works.
     */
    @Test
    public void testGetIsOpened() {
        assertFalse(mineTile.getIsOpened());
    }

    /**
     * Test whether the getX works.
     */
    @Test
    public void testGetX() {
        assertEquals(0, mineTile.getX());
    }

    /**
     * Test whether the getY works.
     */
    @Test
    public void testGetY() {
        assertEquals(0, mineTile.getY());
    }

    /**
     * Test whether the getBackground works.
     */
    @Test
    public void testGetBackground() {
        assertEquals(R.drawable.tile_closed, mineTile.getBackground());
    }

    /**
     * Test whether the setValue works.
     */
    @Test
    public void testSetValue() {
        assertEquals(0, mineTile.getValue());
        mineTile.setValue(-1);
        assertEquals(-1, mineTile.getValue());
    }

    /**
     * Test whether the setX works.
     */
    @Test
    public void testSetX() {
        assertEquals(0, mineTile.getX());
        mineTile.setX(8);
        assertEquals(8, mineTile.getX());
    }

    /**
     * Test whether the setY works.
     */
    @Test
    public void testSetY() {
        assertEquals(0, mineTile.getY());
        mineTile.setY(8);
        assertEquals(8, mineTile.getY());
    }

    /**
     * Test whether the setBackground works.
     */
    @Test
    public void testSetBackground() {
        assertEquals(R.drawable.tile_closed, mineTile.getBackground());
        mineTile.setBackground(R.drawable.tile_boom);
        assertEquals(R.drawable.tile_boom, mineTile.getBackground());
    }
}