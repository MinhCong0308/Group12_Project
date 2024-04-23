package screen.sortingApp.button;

import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.*;
import java.awt.event.ActionListener;

public class ButtonPanel extends JPanel {
    public static final long serialVersionUID = 1L;
    private static final int BUTTON_WIDTH = 200, BUTTON_HEIGHT = 80;
    private JButton[] buttons;
    private SortButtonListener listener;
    private int number = 7;

    public ButtonPanel(SortButtonListener listener) {
        super();
        this.listener = listener;
        buttons = new JButton[number];

        // Initialize buttons
        String[] buttonLabels = {"Manually Create","Random Create", "Sorting Algo 1", "Sorting Algo 2", "Sorting Algo 3", "Sorting Algo 4", "Back to Menu"};
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new JButton(buttonLabels[i]);
            buttons[i].setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
            buttons[i].setFont(new Font("Arial", Font.PLAIN, 16));
            buttons[i].setForeground(Color.BLACK);
            buttons[i].setBackground(Color.WHITE);
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
