package sorting;

import java.awt.*;
import java.awt.image.BufferStrategy;
import screen.sortingApp.color.ColorManager;
import screen.sortingApp.bar.Bar;

public class InsertionSort extends Sorting {
	public InsertionSort(int[] array, Bar[] bars, Graphics g, int speed, BufferStrategy bs) {
        super(array, bars, g, speed, bs);
    }
    @Override
    public void visualize() {
        g = bs.getDrawGraphics();

        for (int i = 1; i < array.length; i++) {
            int key = array[i];
            int j = i - 1;

            // Visualize the comparison
            bars[i].setColor(ColorManager.BAR_RED);
            bars[i].draw(g);
            bs.show();
            sleep(speed * 10);
            comp++;

            while (j >= 0 && array[j] > key) {
                // Shift elements greater than key to the right
                // Visualize swapping
                swap(j+1, j);
                swapBar(j + 1, j);
                swapping++;
                j--;

                if (j >= 0) {
                    setColorComparing(j, i);
                    bs.show();
                    comp++;
                    sleep(speed * 20);
                    if(array[j] <= key) bars[j].clear(g);
                }
            }
            for(int k = 0; k < i; k ++) {
                bars[k].clear(g);
                bars[k].setColor(ColorManager.BAR_CYAN);
                bars[k].draw(g);
            }
            bs.show();
            //shifting to the right for next key.
            array[j + 1] = key;
            sleep(speed);
        }
        setAllGreen(array.length);
    }

    private void setColorComparing(int j, int i) {
        bars[j].clear(g);
        bars[j].setColor(ColorManager.BAR_YELLOW);
        bars[j].draw(g);
        sleep(speed);
        bs.show();
    }

    private void swapBar(int i, int j) {
        bars[i].clear(g);
        bars[j].clear(g);
        bars[i].setValue(array[i]);
        bars[j].setValue(array[j]);
        bars[i].setColor(ColorManager.BAR_CYAN);
        bars[j].setColor(ColorManager.BAR_RED);
        bars[i].draw(g);
        bars[j].draw(g);
        bs.show();
        if (i < array.length - 1) sleep(speed);
    }
}

