package screen.sortingApp.chatbox;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class ChatBox extends JFrame {

    private JTextArea chatArea;
    private JTextField inputField;
    private JButton sendButton;

    private static final String url = "https://api.openai.com/v1/chat/completions";
    private static final String apiKey = "";  // Add your API key here
    private static final String model = "gpt-3.5-turbo";

    public ChatBox() {
        // Set up the frame
        setTitle("ChatGPT Swing Chatbox");
        setSize(350, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Chat area
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setLineWrap(true);  // Enable line wrap
        chatArea.setWrapStyleWord(true);  // Wrap at word boundaries
        JScrollPane scrollPane = new JScrollPane(chatArea);
        add(scrollPane, BorderLayout.CENTER);

        // Input field
        inputField = new JTextField();
        add(inputField, BorderLayout.SOUTH);

        // Send button
        sendButton = new JButton("Send");
        add(sendButton, BorderLayout.EAST);

        // Action listener for send button
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userInput = inputField.getText();
                inputField.setText("");
                if (!userInput.trim().isEmpty()) {
                    chatArea.append("User: " + userInput + "\n\n");
                    String response = chatGPT(userInput);
                    // Replace \n\n with actual newlines and also handle single \n
                    response = response.replace("\\n\\n", "\n\n").replace("\\n", "\n");
                    chatArea.append("ChatGPT: " + response + "\n\n");
                    inputField.setText("");
                }
            }
        });

        // Show the frame
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ChatBox();
            }
        });
    }

    private static String chatGPT(String prompt) {
        String chatGPTResponse;

        // Check for specific prompts locally
        if (prompt.toLowerCase().contains("sort")) {
            try {
                // If user input contains "sort", send to OpenAI API for response
                URL obj = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Authorization", "Bearer " + apiKey);
                connection.setRequestProperty("Content-Type", "application/json");

                // The request body
                String body = "{\"model\": \"" + model + "\", \"messages\": [{\"role\": \"user\", \"content\": \"" + prompt + "\"}]}";
                connection.setDoOutput(true);
                OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
                writer.write(body);
                writer.flush();
                writer.close();

                // Response from ChatGPT
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                StringBuffer response = new StringBuffer();

                while ((line = br.readLine()) != null) {
                    response.append(line);
                }
                br.close();

                // Calls the method to extract the message.
                chatGPTResponse = extractMessageFromJSONResponse(response.toString());

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else if (prompt.toLowerCase().contains("hello") || prompt.toLowerCase().contains("hi")) {
            // If user input contains greeting, respond accordingly
            chatGPTResponse = "Hello! How can I assist you today?";
        }
        	else if (prompt.toLowerCase().contains("who are you")) {
        		// If user asks "who are you", respond with specific message
        		chatGPTResponse = "I am a supportive chatbot from Group 12. I can help you learn about sorting.";
        	}
        else {
            // For other inputs, respond with a default message
            chatGPTResponse = "We only support for sorting problems.";
        }

        return chatGPTResponse;
    }

    private static String extractMessageFromJSONResponse(String response) {
        int start = response.indexOf("content") + 11;
        int end = response.indexOf("\"", start);
        return response.substring(start, end);
    }
}
