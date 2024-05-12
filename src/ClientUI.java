import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ClientUI {
    private JFrame frame;
    private JPanel mainPanel;
    private JPanel inputPanel;
    private JTextField messageField;
    private JTextArea chatArea;
    private JButton sendButton;
    private String username;
    private JLabel nameLabel; // Label to display the client's name
    private Client client;

    public ClientUI(Client client) {
        this.client = client;

        frame = new JFrame("Group Chat");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLayout(new BorderLayout());

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        nameLabel = new JLabel();
        nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        nameLabel.setHorizontalAlignment(SwingConstants.LEFT);
        mainPanel.add(nameLabel, BorderLayout.NORTH); // Add the name label to the top of the UI

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setBackground(Color.WHITE);
        chatArea.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(chatArea);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        inputPanel = new JPanel(new BorderLayout());
        inputPanel.setBackground(Color.WHITE);
        messageField = new JTextField();
        messageField.setFont(new Font("Arial", Font.PLAIN, 14));
        sendButton = new JButton("Send");
        sendButton.setFont(new Font("Arial", Font.BOLD, 14));
        sendButton.setBackground(new Color(0, 153, 204));
        sendButton.setForeground(Color.WHITE);
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });
        inputPanel.add(messageField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);
        mainPanel.add(inputPanel, BorderLayout.SOUTH);

        frame.add(mainPanel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Prompt user for username
        username = JOptionPane.showInputDialog(frame, "Enter your username:");
        nameLabel.setText("Logged in as: " + username); // Set the username label

        // Start client and listen for messages
        client.setUsername(username);
        client.listenForMessage();
    }

    private void sendMessage() {
        String message = messageField.getText().trim();
        if (!message.isEmpty()) {
            try {
                // Append message to client's own UI
                appendMessage(username + ": " + message);
                // Send message to server
                client.sendMessage(message);
                messageField.setText("");
            } catch (IOException e) {
                e.printStackTrace(); // Handle the exception (printStackTrace is just an example)
            }
        }
    }

    public void appendMessage(String message) {
        chatArea.append(message + "\n");
        chatArea.setCaretPosition(chatArea.getDocument().getLength());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Client client = new Client("localhost", 1234);
                new ClientUI(client);
            }
        });
    }
}
