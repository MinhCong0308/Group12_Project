
package screen.menu;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends JFrame {
    private JPanel mainPanel, container;
    private JLabel titleLabel, groupLabel; // Added groupLabel
    private JButton quitButton, sortButton;
    private static final int WIDTH = 1024, HEIGHT = 768;

    public MainMenu() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(WIDTH, HEIGHT));
        setLocationRelativeTo(null);
        setTitle("Group 12's sorting visualizer");
        setBackground(new Color(0,255,255));
        initialize();
        setVisible(true);
    }

    private void initialize() {
        int topPadding = 10;
        int leftPadding = 0;
        int rightPadding = 0;
        int bottomPadding = 10;

        Container container = getContentPane();
        container.setLayout(new BorderLayout());
        container.setBackground(new Color(0,255,255));

        titleLabel = new JLabel("Sorting Visualizer Application");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 50f));
        titleLabel.setForeground(Color.RED);
        titleLabel.setBorder(new EmptyBorder(topPadding, leftPadding, bottomPadding, rightPadding));

        // Adding groupLabel
        groupLabel = new JLabel("<html>Made by Group 12<br/>Members:<br/>Bui Nguyen Minh<br/>Nguyen Cong Minh<br/>Pham Duy Hoang<br/>Le Van Hau</html>");
        groupLabel.setHorizontalAlignment(SwingConstants.CENTER);
        groupLabel.setFont(new Font("Arial", Font.BOLD, 20));
        groupLabel.setForeground(Color.RED);
        groupLabel.setBackground(new Color(0, 255, 255));

        sortButton = new JButton("Sorting");
        sortButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        quitButton = new JButton("Quit");
        quitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int input = JOptionPane.showConfirmDialog(null, "Do you want to quit our application?", "Quit", 2);
                if (input == 0) {
                    System.exit(0);
                }
            }
        });

        final int widthButton = 150;
        final int heightButton = 70;
        Font buttonFont = new Font("Arial", Font.PLAIN, 30);
        sortButton.setFont(buttonFont);
        Dimension buttonSize = new Dimension(widthButton, heightButton);
        quitButton.setFont(buttonFont);
        sortButton.setPreferredSize(buttonSize);
        quitButton.setPreferredSize(buttonSize);

        container.add(titleLabel, BorderLayout.NORTH);
        container.add(groupLabel, BorderLayout.CENTER); // Added groupLabel to the center
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 0, 10, 0);

        buttonPanel.add(sortButton, gbc);

        gbc.gridy++;
        buttonPanel.add(quitButton, gbc);
        buttonPanel.setBackground(new Color(0, 255, 255));

        container.add(buttonPanel, BorderLayout.SOUTH);
    }
}