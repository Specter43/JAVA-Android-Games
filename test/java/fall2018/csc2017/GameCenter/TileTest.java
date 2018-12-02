package fall2018.csc2017.GameCenter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * The tile test.
 */
public class TileTest {
    /**
     * New tile for test
     */
    private Tile tile;

    /**
     * Set up a tile for test.
     */
    @Before
    public void setUp() {
        tile = new Tile(0);
    }

    /**
     * Tear down.
     */
    @After
    public void tearDown() {
        tile = null;
    }

    /**
     * Test whether getBackground. works.
     */
    @Test
    public void getBackground() {
        assertEquals(R.drawable.tile_0, tile.getBackground());
        tile = new Tile(1);
        assertEquals(R.drawable.tile_1, tile.getBackground());
        tile = new Tile(2);
        assertEquals(R.drawable.tile_2, tile.getBackground());
        tile = new Tile(3);
        assertEquals(R.drawable.tile_3, tile.getBackground());
        tile = new Tile(4);
        assertEquals(R.drawable.tile_4, tile.getBackground());
        tile = new Tile(5);
        assertEquals(R.drawable.tile_5, tile.getBackground());
        tile = new Tile(6);
        assertEquals(R.drawable.tile_6, tile.getBackground());
        tile = new Tile(7);
        assertEquals(R.drawable.tile_7, tile.getBackground());
        tile = new Tile(8);
        assertEquals(R.drawable.tile_8, tile.getBackground());
        tile = new Tile(9);
        assertEquals(R.drawable.tile_9, tile.getBackground());
        tile = new Tile(10);
        assertEquals(R.drawable.tile_10, tile.getBackground());
        tile = new Tile(11);
        assertEquals(R.drawable.tile_11, tile.getBackground());
        tile = new Tile(12);
        assertEquals(R.drawable.tile_12, tile.getBackground());
        tile = new Tile(13);
        assertEquals(R.drawable.tile_13, tile.getBackground());
        tile = new Tile(14);
        assertEquals(R.drawable.tile_14, tile.getBackground());
        tile = new Tile(15);
        assertEquals(R.drawable.tile_15, tile.getBackground());
        tile = new Tile(16);
        assertEquals(R.drawable.tile_16, tile.getBackground());
        tile = new Tile(17);
        assertEquals(R.drawable.tile_17, tile.getBackground());
        tile = new Tile(18);
        assertEquals(R.drawable.tile_18, tile.getBackground());
        tile = new Tile(19);
        assertEquals(R.drawable.tile_19, tile.getBackground());
        tile = new Tile(20);
        assertEquals(R.drawable.tile_20, tile.getBackground());
        tile = new Tile(21);
        assertEquals(R.drawable.tile_21, tile.getBackground());
        tile = new Tile(22);
        assertEquals(R.drawable.tile_22, tile.getBackground());
        tile = new Tile(23);
        assertEquals(R.drawable.tile_23, tile.getBackground());
        tile = new Tile(24);
        assertEquals(R.drawable.tile_24, tile.getBackground());
        tile = new Tile(101);
        assertEquals(R.drawable.tile_101, tile.getBackground());
        tile = new Tile(102);
        assertEquals(R.drawable.tile_102, tile.getBackground());
        tile = new Tile(103);
        assertEquals(R.drawable.tile_103, tile.getBackground());
        tile = new Tile(104);
        assertEquals(R.drawable.tile_104, tile.getBackground());
        tile = new Tile(105);
        assertEquals(R.drawable.tile_105, tile.getBackground());
        tile = new Tile(106);
        assertEquals(R.drawable.tile_106, tile.getBackground());
        tile = new Tile(107);
        assertEquals(R.drawable.tile_107, tile.getBackground());
        tile = new Tile(108);
        assertEquals(R.drawable.tile_108, tile.getBackground());
        tile = new Tile(109);
        assertEquals(R.drawable.tile_109, tile.getBackground());
    }

    /**
     * Test whether getId works.
     */
    @Test
    public void getId() {
        tile = new Tile (0);
        assertEquals(0 ,tile.getId());
    }

    /**
     * Test whether compareTo works.
     */
    @Test
    public void compareTo() {
        Tile tile_test = new Tile(6);
        int result = tile.compareTo(tile_test);
        assertEquals(6, result);
    }
}