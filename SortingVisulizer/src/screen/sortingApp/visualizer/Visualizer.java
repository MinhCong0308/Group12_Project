package screen.sortingApp.visualizer;
import javax.swing.JOptionPane;
import java.awt.image.BufferStrategy;
import java.awt.Color;
import java.awt.Graphics;
import screen.sortingApp.color.*;
import screen.sortingApp.bar.Bar;
import sorting.*;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;
public class Visualizer {
	private static final int PADDING = 20;
	private static final int MAX_BAR_HEIGHT = 350, MIN_BAR_HEIGHT = 30;
	private Integer [] array;
	private int speed, capacity, colo;
	private Bar[] bars;
	private boolean existedArray;
	
	
	//Value for statistic
	private long startTime, time;
	private int comp, swapping;
	
	private Color originalColor, swappingColor, comparingColor;
	private BufferStrategy bs;
	private Graphics g;
	
	private SortedListener listener;
	
	public Visualizer(int capacity, int fps, SortedListener listener) {
		this.capacity = capacity;
        this.speed = (int) (1000.0/fps);
        this.listener = listener;
        startTime = time = comp = swapping = 0;
        originalColor = ColorManager.BAR_WHITE;
        comparingColor = Color.YELLOW;
        swappingColor = ColorManager.BAR_RED;
        bs = listener.getBufferStrategy();
        existedArray = false;
	}
	
	public void createRandomArray(int canvasWidth, int canvasHeight) {
        array = new Integer[capacity];
        bars = new Bar[capacity];
        existedArray = true;

        // initial position
        double x = PADDING;
        int y = canvasHeight- PADDING;

        // width of all bars
        double width = (double) (canvasWidth - PADDING*2) / capacity;

        // get graphics
        g = bs.getDrawGraphics();
        g.setColor(ColorManager.SORTAPP_BACKGROUND);
        g.fillRect(0, 0, canvasWidth, canvasHeight);

        Random rand = new Random();
        int value;
        Bar bar;
        for (int i = 0; i < array.length; i++)
        {
            value = rand.nextInt(MAX_BAR_HEIGHT) + MIN_BAR_HEIGHT;
            array[i] = value;

            bar = new Bar((int)x, y, (int) width, value, originalColor);
            bar.draw(g);
            bars[i] = bar;

            // move to the next bar
            x += width;
        }

        bs.show();
        g.dispose();
    }
	private Color getBarColor(int value) {
	        int interval = (int) (array.length / 5.0);
	        if (value < interval)
	            return ColorManager.BAR_ORANGE;
	        else if (value < interval * 2)
	            return ColorManager.BAR_YELLOW;
	        else if (value < interval * 3)
	            return ColorManager.BAR_GREEN;
	        else if (value < interval * 4)
	            return ColorManager.BAR_CYAN;
	        return ColorManager.BAR_BLUE;

	}
	private void colorPair(int i, int j, Color color)
    {
        Color color1 = bars[i].getColor(), color2 = bars[j].getColor();
        // drawing
        bars[i].setColor(color);
        bars[i].draw(g);

        bars[j].setColor(color);
        bars[j].draw(g);

        bs.show();

        // delay
        try {
            TimeUnit.MILLISECONDS.sleep(speed);
        } catch (Exception ex) {}

        // put back to original color
        bars[i].setColor(color1);
        bars[i].draw(g);

        bars[j].setColor(color2);
        bars[j].draw(g);

        bs.show();
    }


    // color the bar in speed time and put it
    // back to its original color
    private void colorBar(int index, Color color)
    {
        Bar bar = bars[index];
        Color oldColor = bar.getColor();

        bar.setColor(color);
        bar.draw(g);
        bs.show();

        try {
            TimeUnit.MILLISECONDS.sleep(speed);
        } catch (Exception ex) {}

        bar.setColor(oldColor);
        bar.draw(g);

        bs.show();
    }

    private void swap(int i, int j)
    {
        // swap the elements
        int temp = array[j];
        array[j] = array[i];
        array[i] = temp;

        // clear the bar
        bars[i].clear(g);
        bars[j].clear(g);

        // swap the drawings
        bars[j].setValue(bars[i].getValue());
        bars[i].setValue(temp);

        colorPair(i, j, swappingColor);
    }
    // swiping effect when the sorting is finished
    private void finishAnimation()
    {
        // swiping to green
        for (int i = 0; i < bars.length; i++)
        {
            colorBar(i, comparingColor);
            bars[i].setColor(getBarColor(i));
            bars[i].draw(g);
            bs.show();
        }

        // show elapsed time and comparisons
        listener.onArraySorted(time, comp, swapping);
    }


    // for restore purpose
    public void drawArray()
    {
        if (!existedArray)
            return;

        g = bs.getDrawGraphics();

        for (int i = 0; i < bars.length; i++)
        {
            bars[i].draw(g);
        }

        bs.show();
        g.dispose();
    }
    void countSort(Integer arr[], int n, int exp, int comp)
    {
        int output[] = new int[n]; // output array
        int i;
        int count[] = new int[10];
        Arrays.fill(count,0);

        // Store count of occurrences in count[]
        for (i = 0; i < n; i++)
            count[ (arr[i]/exp)%10 ]++;

        // Change count[i] so that count[i] now contains
        // actual position of this digit in output[]
        for (i = 1; i < 10; i++)
            count[i] += count[i - 1];

        // Build the output array
        for (i = n - 1; i >= 0; i--)
        {
            output[count[ (arr[i]/exp)%10 ] - 1] = arr[i];
            swapping++;
            count[ (arr[i]/exp)%10 ]--;
        }

        // Copy the output array to arr[], so that arr[] now
        // contains sorted numbers according to current digit
        for (i = 0; i < n; i++) {
            arr[i] = output[i];
            bars[i].clear(g);
            bars[i].setValue(arr[i]);
            if(colo%2==0) {
                bars[i].setColor(swappingColor);
            } else {
                bars[i].setColor(comparingColor);
            }
            bars[i].draw(g);
            bs.show();
            try {
                TimeUnit.MILLISECONDS.sleep(speed);
            } catch (Exception ex) {}

        }
    }
    public void countingSort() {
        if (!isCreated())
            return;

        // get graphics
        g = bs.getDrawGraphics();

        // calculate elapsed time
        startTime = System.nanoTime();
        CountingSort.countingSort(array.clone());
        time = System.nanoTime() - startTime;

        comp = swapping = 0;
        int N = array.length;
        int M = 0;

        for (int i = 0; i < N; i++) {
            M = Math.max(M, array[i]);
        }

        int[] countArray = new int[M + 1];

        for (int i = 0; i < N; i++) {
            countArray[array[i]]++;
            swapping++;
        }

        for (int i = 1; i <= M; i++) {
            countArray[i] += countArray[i - 1];
            swapping++;
        }

        int[] outputArray = new int[N];

        for (int i = N - 1; i >= 0; i--) {
            outputArray[countArray[array[i]] - 1] = array[i];
            swapping++;
            countArray[array[i]]--;
        }

        for (int i = 0; i < N; i++) {
            array[i] = outputArray[i];
            swapping++;
            bars[i].clear(g);
            bars[i].setValue(array[i]);
            if(colo%2==0) {
                bars[i].setColor(swappingColor);
            } else {
                bars[i].setColor(comparingColor);
            }
            bars[i].draw(g);
            bs.show();
            try {
                TimeUnit.MILLISECONDS.sleep(speed);
            } catch (Exception ex) {}

        }

        finishAnimation();

        g.dispose();
    }

    // check if array is created
    private boolean isCreated()
    {
        if (!existedArray)
            JOptionPane.showMessageDialog(null, "You need to create an array!", "No Array Created Error", JOptionPane.ERROR_MESSAGE);
        return existedArray;
    }


    public void setCapacity(int capacity)
    {
        this.capacity = capacity;
    }

    public void setFPS(int fps)
    {
        this.speed = (int) (1000.0/fps);
    }


	public interface SortedListener
    {
        void onArraySorted(long elapsedTime, int comparison, int swapping);
        BufferStrategy getBufferStrategy();
    }
	
}
