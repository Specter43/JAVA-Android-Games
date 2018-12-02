package fall2018.csc2017.GameCenter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test for Mine board class.
 */
public class MineBoardTest {
    /**
     * The mine board for test.
     */
    private MineBoard mineBoard;

    /**
     * Number of booms for test.
     */
    private int testBoomNumber = 40;

    /**
     * Test position.
     */
    private int testPosition = 0;

    /**
     * Create a initial list of Tiles for game with matching sizes.
     *
     * @return list of Tiles.
     */
    private List<MineTile> createTiles() {
        List<MineTile> mineTiles = new ArrayList<>();
        int numTiles = MineBoard.getSize() * MineBoard.getSize();
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            mineTiles.add(new MineTile(0, false));
        }
        return mineTiles;
    }

    /**
     * Set up the mine board for test.
     */
    @Before
    public void setUp() {
        mineBoard = new MineBoard(createTiles(), testBoomNumber, new Random());
    }

    /**
     * Tear down.
     */
    @After
    public void tearDown() {
        mineBoard = null;
    }

    /**
     * Test whether getSize works.
     */
    @Test
    public void testGetSize() {
        setUp();
        assertEquals(16, MineBoard.getSize());
    }

    /**
     * Test whether getNumber works.
     */
    @Test
    public void testGetNumBoom() {
        setUp();
        assertEquals(testBoomNumber, mineBoard.getNumBoom());
    }

    /**
     * Test whether getMineTile works.
     */
    @Test
    public void testGetMineTile() {
        setUp();
        int row = 8;
        int col = 8;
        assertEquals(0, mineBoard.getMineTile(row, col).getValue());
        assertFalse(mineBoard.getMineTile(row, col).getIsOpened());
    }

    /**
     * Test whether setNumBoom works.
     */
    @Test
    public void testSetNumBoom() {
        setUp();
        assertEquals(testBoomNumber, mineBoard.getNumBoom());
        mineBoard.setNumBoom(52);
        assertEquals(52, mineBoard.getNumBoom());
    }

    /**
     * Test whether setFirstTapToFalse works.
     */
    @Test
    public void testSetFirstTapToFalse() {
        setUp();
        assertTrue(mineBoard.isFirstTap());
        mineBoard.setFirstTapToFalse();
        assertFalse(mineBoard.isFirstTap());
    }

    /**
     * Test before touchOpen.
     */
    @Test
    public void testBeforeTouchOpen() {
        setUp();
        assertEquals(0, testCreateBooms());
        assertFalse(mineBoard.getMineTile(testPosition / MineBoard.getSize(),
                testPosition % MineBoard.getSize()).getIsOpened());
    }

    /**
     * Test create booms after touchOpen.
     */
    @Test
    public void testCreateBoomsAfterTouchOpen() {
        setUp();
        mineBoard.touchOpen(testPosition);
        assertEquals(testBoomNumber, testCreateBooms());
    }

    /**
     * Test open tile after touchOpen.
     */
    @Test
    public void testOpenTileAfterTouchOpen() {
        setUp();
        mineBoard.touchOpen(testPosition);
        assertTrue(mineBoard.getMineTile(testPosition / MineBoard.getSize(),
                testPosition % MineBoard.getSize()).getIsOpened());
    }

    /**
     * Test display booms after touchOpen fail.
     */
    @Test
    public void testDisplayBoomsAfterTouchOpenFail() {
        setUp();
        mineBoard.touchOpen(testPosition);

        for (int row = 0; row < MineBoard.getSize(); row++) {
            for (int col = 0; col < MineBoard.getSize(); col++) {
                if (mineBoard.getMineTile(row, col).getValue() == -1) {
                    int boomLocation = row * MineBoard.getSize() + col;
                    mineBoard.touchOpen(boomLocation);
                    assertEquals(testBoomNumber, testOpenedBooms());
                    break;
                }
            }
        }
    }

    /**
     * Test touch open when not firstTap.
     */
    @Test
    public void testTouchOpenWhenNotFirstTap() {
        setUp();
        mineBoard.touchOpen(testPosition);

        // test createBooms without first tap.
        mineBoard = new MineBoard(createTiles(), testBoomNumber, new Random());
        assertEquals(0, testCreateBooms());
        mineBoard.touchOpen(testPosition);
        assertEquals(40, testCreateBooms());
    }

    /**
     * Test createBooms().
     *
     * @return num of booms are created.
     */
    private int testCreateBooms() {
        int numberOfBooms = 0;
        for (int row = 0; row < MineBoard.getSize(); row++) {
            for (int col = 0; col < MineBoard.getSize(); col++) {
                if (mineBoard.getMineTile(row, col).getValue() == -1) {
                    numberOfBooms++;
                }
            }
        }
        return numberOfBooms;
    }

    /**
     * Test displayBooms().
     *
     * @return num of booms are opened.
     */
    private int testOpenedBooms() {
        int numberOfDisplay = 0;
        for (int boomRow = 0; boomRow < MineBoard.getSize(); boomRow++) {
            for (int boomCol = 0; boomCol < MineBoard.getSize(); boomCol++) {
                if (mineBoard.getMineTile(boomRow, boomCol).getValue() == -1
                        && mineBoard.getMineTile(boomRow, boomCol).getIsOpened()) {
                    numberOfDisplay++;
                }
            }
        }
        return numberOfDisplay;
    }

    /**
     * Test whether replaceToFlag works.
     */
    @Test
    public void testReplaceToFlag() {
        setUp();
        int row = 8;
        int col = 8;
        assertFalse(mineBoard.getMineTile(row, col).getIsOpened());
        assertEquals(0, mineBoard.getMineTile(row, col).getValue());
        assertEquals(R.drawable.tile_closed, mineBoard.getMineTile(row, col).getBackground());
        mineBoard.replaceToFlag(row, col);
        assertFalse(mineBoard.getMineTile(row, col).getIsOpened());
        assertEquals(0, mineBoard.getMineTile(row, col).getValue());
        assertEquals(R.drawable.tile_flagged, mineBoard.getMineTile(row, col).getBackground());
        mineBoard.replaceToFlag(row, col);
        assertFalse(mineBoard.getMineTile(row, col).getIsOpened());
        assertEquals(0, mineBoard.getMineTile(row, col).getValue());
        assertEquals(R.drawable.tile_closed, mineBoard.getMineTile(row, col).getBackground());
    }

    /**
     * Test whether iterator works.
     */
    @Test
    public void iterator() {
        assertTrue(mineBoard.iterator().hasNext());
        assertEquals(MineTile.class, mineBoard.iterator().next().getClass());
    }
}