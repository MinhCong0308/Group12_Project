package screen.sortingApp.bar;
//Class for bar that represent value of array
import screen.sortingApp.color.ColorManager;
import java.awt.Graphics;
import java.awt.Color;
//import java.awt.image.BufferStrategy;
public class Bar {
	private final int MARGIN = 1;
	private int x,y,value,width;
	private Color color;
	
	public Bar(int x, int y, int value, int width, Color color) {
		this.x = x;
		this.y = y;
		this.value = value;
		this.width = width;
		this.color = color;
	}
	public void draw(Graphics g) {
		g.setColor(color);
		g.fillRect(x + MARGIN, y-value, width - MARGIN*2, value);
	}
	public void clear(Graphics g) {
		g.setColor(ColorManager.SORTAPP_BACKGROUND);
		g.fillRect(x + MARGIN, y-value, width - MARGIN*2, value);
	}
	public void setValue(int value) {
		this.value = value;
	}
	public int getValue() {
		return value;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	public Color getColor() {
		return color;
	}
}