import javax.swing.*;
import java.awt.*;

public class ServerUI {
    private JFrame frame;
    private JPanel mainPanel;
    private JLabel titleLabel;
    private JTextArea displayArea;

    public ServerUI() {
        frame = new JFrame("Server");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create main panel
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // Create title label
        titleLabel = new JLabel("Server");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setVerticalAlignment(SwingConstants.CENTER);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Create display area
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Add main panel to frame
        frame.add(mainPanel);

        // Set frame size and visibility
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null); // Center the frame on screen
        frame.setVisible(true);
    }

    // Method to update the display area with new text
    public void appendToDisplay(String text) {
        displayArea.append(text + "\n");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ServerUI::new);
    }
}
