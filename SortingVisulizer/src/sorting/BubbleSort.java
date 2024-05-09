package sorting;

import java.awt.*;
import java.awt.image.BufferStrategy;
import screen.sortingApp.color.ColorManager;
import screen.sortingApp.bar.Bar;

public class BubbleSort extends Sorting{
    public BubbleSort(int[] array, Bar[] bars, Graphics g, int speed, BufferStrategy bs) {
        super(array, bars, g, speed, bs);
    }
    @Override
    public void visualize(){
        // Get graphics from buffer
        g = bs.getDrawGraphics();
        //set up variables for statistics
        comp = 0;
        swapping = 0;
        for (int i = 0; i < array.length - 1; i++) {
            boolean hasSwap = false;
            for (int j = 0; j < array.length - i - 1; j++) {
                // Set color for 2 bars that are being compared
                comp++;
                setColorComparing(j, j + 1);
                sleep(speed*20);
                if (array[j] > array[j + 1]) {
                    // swap arr[j+1] and arr[j]
                    swapping++;
                    swap(j, j + 1);
                    swapBarBubbleSort(j, j + 1, i);
                    hasSwap = true;
                } else {
                    // Set the bar back to normal color if not swapping
                    setColorNormal(j, j + 1);
                }
            }
            // Set the bar with the correct position green
            bars[bars.length - i - 1].clear(g);
            bars[bars.length - i - 1].setColor(ColorManager.BAR_GREEN);
            bars[bars.length - i - 1].draw(g);
            bs.show();
            sleep(speed);
            if (!hasSwap){
                for (int j = 0; j < array.length - 1; j++){
                    bars[j].clear(g);
                    bars[j].setColor(ColorManager.BAR_GREEN);
                    bars[j].draw(g);
                }
                bs.show();
                return;
            }
        }
        // Set the first bar to green
        bars[0].clear(g);
        bars[0].setColor(ColorManager.BAR_GREEN);
        bars[0].draw(g);
        bs.show();
        g.dispose();
    }

    public void swapBarBubbleSort(int i, int j, int pivot) {
        // Change color of 2 swapping bar to red
        bars[i].clear(g);
        bars[j].clear(g);
        bars[i].setColor(ColorManager.BAR_RED);
        bars[j].setColor(ColorManager.BAR_RED);
        bars[i].draw(g);
        bars[j].draw(g);
        bs.show();
        sleep(speed*5);
        // Change color of the corrected bar to green, others to normal
        bars[i].clear(g);
        bars[j].clear(g);
        bars[i].setValue(array[i]);
        bars[j].setValue(array[j]);
        bars[i].setColor(ColorManager.BAR_WHITE);
        if (j == bars.length - pivot - 1) {
            bars[j].setColor(ColorManager.BAR_GREEN);
        } else {
            bars[j].setColor(ColorManager.BAR_WHITE);
        }
        bars[i].draw(g);
        bars[j].draw(g);
        bs.show();
    }    

    public void setColorComparing(int minIndex, int j) {
        bars[j].clear(g);
        bars[minIndex].clear(g);
        bars[minIndex].setColor(ColorManager.BAR_YELLOW);
        bars[minIndex].draw(g);
        bars[j].setColor(ColorManager.BAR_YELLOW);
        
        bars[j].draw(g);
        sleep(speed);
        bs.show();
    }

    public void setColorNormal(int i, int j) {
        bars[i].clear(g);
        bars[j].clear(g);
        bars[i].setColor(ColorManager.BAR_WHITE);
        bars[j].setColor(ColorManager.BAR_WHITE);
        bars[i].draw(g);
        bars[j].draw(g);
        bs.show();
    }
}
