
package screen.menu;
import javax.print.DocFlavor.URL;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import screen.sortingApp.color.ColorManager;
import screen.sortingApp.*;

public class MainMenu extends JFrame {
    public static final long serialVersionUID = 10L;
    private static final int WIDTH = 1280, HEIGHT = 720;
    private JPanel mainPanel;

    public MainMenu(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMaximumSize(new Dimension(WIDTH, HEIGHT + 200));
        setMinimumSize(new Dimension(WIDTH, HEIGHT + 20));
        setPreferredSize(new Dimension(WIDTH, HEIGHT + 20));
        setLocationRelativeTo(null);
        setResizable(false);
        setBackground(ColorManager.BACKGROUND);
        setTitle("Group 12's project");

        initialize();
    }

    private void initialize(){
        mainPanel = new GradientPanel();
        mainPanel.setLayout(null);
        add(mainPanel);
    
        // Welcome label
        JLabel welcomeLabel = new JLabel("Welcome to Group 12's project!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 40));  // Increased and bold font size
        welcomeLabel.setForeground(ColorManager.BAR_WHITE);  
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Calculate position to center the welcome label horizontally and position it at the top
        int welcomeLabelWidth = 800; // Adjust the width as needed
        int welcomeLabelHeight = 60; // Adjust the height as needed
        int xWelcome = (WIDTH - welcomeLabelWidth) / 2;
        int yWelcome = 20; // Position the label at the top with a slight margin

        welcomeLabel.setBounds(xWelcome, yWelcome, welcomeLabelWidth, welcomeLabelHeight);
        mainPanel.add(welcomeLabel);


        JLabel name = new JLabel("Sorting Visualize");
        name.setFont(new Font("Arial", Font.BOLD, 40));
        name.setForeground(ColorManager.BAR_WHITE);
        name.setHorizontalAlignment(SwingConstants.CENTER);

        int nameLabelWidth = 600;
        int nameLabelHeight = 60;
        int xName = (WIDTH - nameLabelWidth) / 2;
        int yName = 90;
        name.setBounds(xName, yName, nameLabelWidth, nameLabelHeight);
        mainPanel.add(name);
        // Buttons
        CustomButton button1 = new CustomButton("Lets go", Color.RED, Color.BLUE);
        CustomButton button2 = new CustomButton("Exit", Color.GREEN, Color.YELLOW);
        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new MainFrame().setVisible(true);
                dispose();
            }
        });
        button2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int input = JOptionPane.showConfirmDialog(null, "Do you want to quit our application?", "Quit", 2);
                if (input == 0) {
                    System.exit(0);
                }
            }
        });
        // Calculate positions for buttons to be placed at the bottom center of the screen
        int buttonWidth = 300;
        int buttonHeight = 70;
        int xButton1 = (WIDTH - 2 * buttonWidth - 20) / 2;
        int xButton2 = xButton1 + buttonWidth + 20;
        int yButton = HEIGHT - 100;

        button1.setBounds(xButton1, yButton, buttonWidth, buttonHeight);
        button2.setBounds(xButton2, yButton, buttonWidth, buttonHeight);

        mainPanel.add(button1);
        mainPanel.add(button2);

        // Load the original images
        ImageIcon originalIcon1 = new ImageIcon("./SortingVisulizer/src/screen/menu/image/java.png");
        ImageIcon originalIcon2 = new ImageIcon("./SortingVisulizer/src/screen/menu/image/hust.png");

        // Calculate the scaled dimensions
        int scaledWidth = 250; // Adjust as needed
        int scaledHeight = 250; // Adjust as needed

        // Scale the images
        Image scaledImage1 = originalIcon1.getImage().getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);
        Image scaledImage2 = originalIcon2.getImage().getScaledInstance(scaledWidth, scaledHeight + 100, Image.SCALE_SMOOTH);

        // Create scaled image icons
        ImageIcon scaledIcon1 = new ImageIcon(scaledImage1);
        ImageIcon scaledIcon2 = new ImageIcon(scaledImage2);

        // Create JLabels with scaled images
        JLabel imageLabel1 = new JLabel(scaledIcon1);
        JLabel imageLabel2 = new JLabel(scaledIcon2);

        // Calculate positions for images to be placed with a gap between them
        int xImage1 = (WIDTH - 2 * scaledWidth) / 3; // Adjust the divisor for appropriate spacing
        int xImage2 = 2 * (WIDTH - scaledWidth) / 3; // Adjust the divisor for appropriate spacing
        int yImage = (HEIGHT - scaledHeight) / 2;

        // Set bounds for image labels
        imageLabel1.setBounds(xImage1, yImage, scaledWidth, scaledHeight);
        imageLabel2.setBounds(xImage2, yImage - 50, scaledWidth, scaledHeight + 100);

        // Add image labels to the main panel
        mainPanel.add(imageLabel1);
        mainPanel.add(imageLabel2);
    }
}