package fall2018.csc2017.GameCenter;

/*
Taken from:
https://github.com/DaveNOTDavid/sample-puzzle/blob/master/app/src/main/java/com/davenotdavid/samplepuzzle/CustomAdapter.java

This Class is an overwrite of the Base Adapter class
It is designed to aid setting the button sizes and positions in the GridView
 */

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * The custom adapter.
 */
public class CustomAdapter extends BaseAdapter implements Serializable {
    /**
     * The list of buttons.
     */
    private ArrayList<Button> mButtons;
    /**
     * The matrix width and height.
     */
    private int mColumnWidth, mColumnHeight;

    /**
     * The constructor for the custom adapter.
     *
     * @param buttons      the buttons.
     * @param columnWidth  the width of column.
     * @param columnHeight the height of column.
     */
    CustomAdapter(ArrayList<Button> buttons, int columnWidth, int columnHeight) {
        mButtons = buttons;
        mColumnWidth = columnWidth;
        mColumnHeight = columnHeight;
    }

    /**
     * Getter for number of buttons.
     *
     * @return the number of buttons.
     */
    @Override
    public int getCount() {
        return mButtons.size();
    }

    /**
     * Getter for position of button.
     *
     * @param position the position of the item.
     * @return the position of the button on the screen.
     */
    @Override
    public Object getItem(int position) {
        return mButtons.get(position);
    }

    /**
     * Getter for position of item.
     *
     * @param position the position of the item.
     * @return the position of the item.
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Get the view of the button.
     *
     * @param position the position of the button.
     * @param convertView the converted view of the item on that position.
     * @param parent the parent group of the view.
     * @return the button's view.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Button button;

        if (convertView == null) {
            button = mButtons.get(position);
        } else {
            button = (Button) convertView;
        }

        android.widget.AbsListView.LayoutParams params =
                new android.widget.AbsListView.LayoutParams(mColumnWidth, mColumnHeight);
        button.setLayoutParams(params);

        return button;
    }
}

