package sorting;

import java.awt.*;
import java.awt.image.BufferStrategy;
import screen.sortingApp.color.ColorManager;
import screen.sortingApp.bar.Bar;

public class MergeSort extends Sorting {
	public MergeSort(int[] array, Bar[] bars, Graphics g, int speed, BufferStrategy bs) {
        super(array, bars, g, speed, bs);
    }
    @Override
    public void visualize() {
    	g = bs.getDrawGraphics();
        // Set up variables for statistics
        comp = 0;
        swapping = 0;

        mergeSort(0, array.length - 1);

        setAllGreen(array.length);
        g.dispose();
        setAllGreen(array.length);
    }
    
    private void mergeSort(int low, int high) {
        if (low < high) {
            int mid = low + (high - low) / 2;

            mergeSort(low, mid);
            mergeSort(mid + 1, high);

            // Visualize the left and right parts
            visualizeDivide(low, mid, high);

            // Merge the two sorted subarrays
            merge(low, mid, high);
        }
    }

    private void merge(int low, int mid, int high) {
        // Merge the two sorted subarrays
        int[] temp = new int[array.length];
        int i = low, j = mid + 1, k = low;

        while (i <= mid && j <= high) {
            comp++;
            if (array[i] <= array[j]) {
                temp[k++] = array[i++];
            } else {
                temp[k++] = array[j++];
            }
        }

        while (i <= mid) {
            temp[k++] = array[i++];
        }

        while (j <= high) {
            temp[k++] = array[j++];
        }

        // Back to the original array
        for (i = low; i <= high; i++) {
            array[i] = temp[i];
        }

        // Visualize swapping
        for (i = low; i <= high; i++) {
            if (i != k) {
                swapBarMergeSort(i, temp[i]);
                swapping++;
            }
        }
    }

    private void visualizeDivide(int low, int mid, int high) {
        int leftLength = mid - low + 1;
        int rightLength = high - mid;

        // The left part
        for (int i = low; i <= mid; i++) {
            bars[i].clear(g);
            bars[i].setColor(ColorManager.BAR_CYAN); // Change Color here
            bars[i].draw(g);
        }

        // The right part
        for (int i = mid + 1; i <= high; i++) {
            bars[i].clear(g);
            bars[i].setColor(ColorManager.BAR_BLUE); // Change color here
            bars[i].draw(g);
        }

        bs.show();
        sleep(speed * 20);

        // Restore the color of bars
        for (int i = low; i <= high; i++) {
            bars[i].clear(g);
            bars[i].setColor(ColorManager.BAR_WHITE);
            bars[i].draw(g);
        }
    }

    private void swapBarMergeSort(int index, int value) {
        g.setColor(ColorManager.BAR_YELLOW);
        bars[index].clear(g);
        bars[index].setValue(value);
        bars[index].setColor(ColorManager.BAR_RED);
        bars[index].draw(g);
        bs.show();
        sleep(speed * 20);
        bars[index].clear(g);
        bars[index].setValue(array[index]);
        bars[index].setColor(ColorManager.BAR_WHITE);
        bars[index].draw(g);
        bs.show();
    }
}

