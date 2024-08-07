package screen.sortingApp.button;

import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.*;
import java.awt.event.ActionListener;
import screen.sortingApp.color.ColorManager;
import java.util.ArrayList;

public class ButtonPanel extends JPanel {
    public static final long serialVersionUID = 1L;
    private static final int BUTTON_WIDTH = 200, BUTTON_HEIGHT = 80;
    private JButton[] buttons;
    private SortButtonListener listener;
    private ArrayList<Color> colorButton = new ArrayList<Color>();
    private int number = 8;

    public ButtonPanel(SortButtonListener listener) {
        super();
        this.listener = listener;
        buttons = new JButton[number];
        colorButton.add(ColorManager.CREATE_BUTTON);
        colorButton.add(ColorManager.CREATE_BUTTON);
        colorButton.add(ColorManager.BUTTON_BUBBLE);
        colorButton.add(ColorManager.BUTTON_ORANGE);
        colorButton.add(ColorManager.BUTTON_PINK);
        colorButton.add(ColorManager.BUTTON_YELLOW);
        colorButton.add(ColorManager.BUTTON_BUBBLE);
        colorButton.add(ColorManager.EXIT_BUTTON);

        // Initialize buttons
        String[] buttonLabels = {"Manually Create", "Random Create", "Bubble Sort", "Selection Sort", "Insertion Sort", "Merge Sort", "ChatBot" ,"Back to Menu"};
        for (int i = 0; i < buttons.length; i++) {
        	Color color = colorButton.get(i);
            buttons[i] = new JButton(buttonLabels[i]);
            buttons[i].setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
            buttons[i].setFont(new Font("Arial", Font.PLAIN, 16));
            buttons[i].setForeground(Color.BLACK);
            buttons[i].setBackground(color);
            buttons[i].addActionListener(createButtonActionListener(i));
            add(buttons[i]);
        }
        
        // Set layout
        setLayout(new GridLayout(number, 1, 0, 10));
    }

    private ActionListener createButtonActionListener(int id) {
        return e -> listener.sortButtonClicked(id);
    }

    public interface SortButtonListener {
        void sortButtonClicked(int id);
    }
}
