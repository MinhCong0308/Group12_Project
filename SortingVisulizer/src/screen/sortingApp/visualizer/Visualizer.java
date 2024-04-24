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
	private int capacity;
	private Bar[] bars;
	private boolean existedArray;
    private int speed = 20;
	
	//Value for statistic
	//private long startTime, time;
	//private int comp, swapping;
	
	private Color originalColor; /*swappingColor, comparingColor;*/
	private BufferStrategy bs;
	private Graphics g;
	
	private SortedListener listener;
	
	public Visualizer(int capacity, SortedListener listener) {
		this.capacity = capacity;
        //this.speed = (int) (1000.0/fps);
        this.listener = listener;
        //startTime = time = comp = swapping = 0;
        originalColor = ColorManager.BAR_WHITE;
        //comparingColor = Color.YELLOW;
        //swappingColor = ColorManager.BAR_RED;
        bs = listener.getBufferStrategy();
        existedArray = false;
	}
	
	public void createManualArray(int canvasWidth, int canvasHeight) {
	    array = new Integer[capacity];
	    bars = new Bar[capacity];
	    existedArray = true;

	    // Get graphics
	    g = bs.getDrawGraphics();
	    g.setColor(ColorManager.SORTAPP_BACKGROUND);
	    g.fillRect(0, 0, canvasWidth, canvasHeight);

	    // Prompt user to enter array elements manually
	    for (int i = 0; i < array.length; i++) {
	        String input = JOptionPane.showInputDialog("Enter value for element " + (i + 1) + ":");
	        try {
	            int value = Integer.parseInt(input);
	            array[i] = value;
	        } catch (NumberFormatException e) {
	            // Handle invalid input
	            JOptionPane.showMessageDialog(null, "Invalid input! Please enter a valid integer.", "Error", JOptionPane.ERROR_MESSAGE);
	            return;
	        }

	        // Draw the bar representing the entered value
	        double x = PADDING + i * ((canvasWidth - PADDING * 2.0) / capacity);
	        int barHeight = Math.min(MAX_BAR_HEIGHT, Math.max(MIN_BAR_HEIGHT, array[i]));
	        Bar bar = new Bar((int) x, canvasHeight - PADDING, (int) ((canvasWidth - PADDING * 2.0) / capacity), barHeight, originalColor);
	        bar.draw(g);
	        bars[i] = bar;
	    }

	    bs.show();
	    g.dispose();
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

    public void setCapacity(int capacity)
    {
        this.capacity = capacity;
    }

    public void visualizeBubbleSort() {
        g = bs.getDrawGraphics();

    for (int i = 0; i < array.length - 1; i++) {
        for (int j = 0; j < array.length - i - 1; j++) {
            if (array[j] > array[j + 1]) {
                // swap arr[j+1] and arr[j]
                swap(j, j + 1);
                swapBar(j, j + 1);
            }
        }
            
            bs.show(); // Move this line here to update the visualization after each pass
        try {
            TimeUnit.MILLISECONDS.sleep(speed); // Sleep after each pass
        } catch (Exception ex) {
            // Handle exception
        }
    }
        g.dispose();
    }


	public interface SortedListener
    {
        void onArraySorted(long elapsedTime, int comparison, int swapping);
        BufferStrategy getBufferStrategy();
    }

    public void swap(int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
    
    public void swapBar(int i, int j) {
        bars[i].setColor(ColorManager.BAR_GREEN);
        bars[j].setColor(ColorManager.BAR_GREEN);
        bars[i].draw(g);
        bars[j].draw(g);
        bs.show();
    // Delay to see the swapped bars
        try {
            TimeUnit.MILLISECONDS.sleep(speed);
        } catch (Exception ex) {
            // Handle exception
        }
        bars[i].clear(g);
        bars[i].clear(g);
        bs.show();
    // Restore original values and colors
        bars[i].setValue(array[i]);
        bars[j].setValue(array[j]);
        bars[i].setColor(ColorManager.BAR_WHITE);
        bars[j].setColor(ColorManager.BAR_WHITE);
        bars[i].draw(g);
        bars[j].draw(g);
        bs.show();
    }
}