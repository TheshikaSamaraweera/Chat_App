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

        // Left Panel with gradient background
        JPanel leftPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                int w = getWidth();
                int h = getHeight();
                Color color1 = new Color(51, 153, 255); // Light Blue
                Color color2 = new Color(0, 102, 204); // Dark Blue
                GradientPaint gp = new GradientPaint(0, 0, color1, w, h, color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
            }
        };
        leftPanel.setPreferredSize(new Dimension(150, frame.getHeight()));
        leftPanel.setLayout(new BorderLayout());

        JLabel groupChatLabel = new JLabel("Group Chat", SwingConstants.CENTER);
        groupChatLabel.setForeground(Color.WHITE);
        groupChatLabel.setFont(new Font("Arial", Font.BOLD, 18));
        leftPanel.add(groupChatLabel, BorderLayout.CENTER);

        // Initialize nameLabel here
        nameLabel = new JLabel(); // <-- Initialization
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        leftPanel.add(nameLabel, BorderLayout.NORTH); // Add the nameLabel to the top of the UI

        // Icon
        ImageIcon icon = new ImageIcon("icon.png"); // Replace "icon.png" with the path to your icon
        JLabel iconLabel = new JLabel(icon, SwingConstants.CENTER);
        leftPanel.add(iconLabel, BorderLayout.SOUTH);

        mainPanel.add(leftPanel, BorderLayout.WEST);

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
