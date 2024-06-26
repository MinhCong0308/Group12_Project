package sorting;
import java.awt.*;
import screen.sortingApp.bar.Bar;
import java.awt.image.BufferStrategy;
import screen.sortingApp.color.ColorManager;
public abstract class Sorting {
    protected int[] array;
    protected Bar[] bars;
    protected Graphics g;
    protected int comp = 0;
    protected int swapping = 0;
    protected int speed;
    protected BufferStrategy bs;

    public Sorting(int[] array, Bar[] bars, Graphics g, int speed, BufferStrategy bs) {
        this.array = array;
        this.bars = bars;
        this.g = g;
        this.speed = speed;
        this.bs = bs;
    }

    public abstract void visualize();

    protected void setAllGreen(int i) {
        for (int j = 0; j < i; j++) {
            bars[j].clear(g);
            bars[j].setColor(ColorManager.BAR_GREEN);
            bars[j].draw(g);
        }
        bs.show();
    }

    protected void swap(int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    protected void swapBars(int i, int j, Color color1, Color color2) {
        bars[i].clear(g);
        bars[j].clear(g);
        bars[i].setValue(array[i]);
        bars[j].setValue(array[j]);
        bars[i].setColor(color1);
        bars[j].setColor(color2);
        bars[i].draw(g);
        bars[j].draw(g);
        bs.show();
        if (i < array.length - 1) sleep(speed);
    }

    protected void sleep(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public int getComp() {
    	return this.comp;
    }
    public int getSwap() {
    	return this.swapping;
    }
 }

