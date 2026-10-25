
import javax.swing.*;
import java.awt.*;
import java.util.*;

public class AIChatbot extends JFrame {

    private JTextArea chatArea;
    private JTextField inputField;
    private JButton sendButton;
    private JButton clearButton;
    private JLabel statusLabel;

    private final Random random = new Random();

    private final Color blue = new Color(55, 105, 220);
    private final Color background = new Color(243, 247, 253);

    // Multiple replies for different types of messages
    private final Map<String, String[]> knowledgeBase =
            new LinkedHashMap<>();

    public AIChatbot() {
        setTitle("AI Chatbot");
        setSize(750, 600);
        setMinimumSize(new Dimension(500, 400));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        getContentPane().setBackground(background);

        loadKnowledgeBase();
        createGUI();

        addMessage("Bot",
                "Hello! Welcome to AI Chatbot.\n"
                + "Ask me about Java, programming, studies or more.");
    }

    // Store questions and possible responses
    private void loadKnowledgeBase() {

        knowledgeBase.put("greeting", new String[]{
            "Hello! How can I help you today?",
            "Hi! What would you like to know?",
            "Hey! Ask me anything about my knowledge topics."
        });

        knowledgeBase.put("how_are_you", new String[]{
            "I'm working perfectly! How can I help you?",
            "I'm doing great. What would you like to discuss?"
        });

        knowledgeBase.put("identity", new String[]{
            "I'm a rule-based AI chatbot built using Java Swing.",
            "I'm your Java chatbot! I answer using keywords and predefined rules."
        });

        knowledgeBase.put("java", new String[]{
            "Java is a high-level, object-oriented programming language.",
            "Java is used to build desktop, web and enterprise applications.",
            "Java programs commonly run on the Java Virtual Machine (JVM)."
        });

        knowledgeBase.put("programming", new String[]{
            "Programming means writing instructions for a computer.",
            "Start with variables, conditions, loops, methods and arrays.",
            "Practice small programs regularly to improve your coding skills."
        });

        knowledgeBase.put("study", new String[]{
            "Make a timetable and divide big topics into smaller tasks.",
            "Study one topic at a time and revise it regularly.",
            "Try solving practice questions after learning a concept."
        });

        knowledgeBase.put("exam", new String[]{
            "Revise important concepts and practise previous questions.",
            "Prepare short notes and practise writing answers in your own words.",
            "Take short breaks between study sessions to stay focused."
        });

        knowledgeBase.put("college", new String[]{
            "College is a great place to learn, build projects and meet people.",
            "Try participating in coding events, hackathons and student clubs.",
            "Balance your coursework with practical projects."
        });

        knowledgeBase.put("project", new String[]{
            "Start by defining the problem, features and expected output.",
            "Build a small working version first, then add more features.",
            "Document your code and test different inputs."
        });

        knowledgeBase.put("nlp", new String[]{
            "NLP stands for Natural Language Processing.",
            "NLP helps computers process and analyse human language.",
            "This chatbot uses simple text normalization and keyword matching."
        });

        knowledgeBase.put("thanks", new String[]{
            "You're welcome!",
            "Happy to help!",
            "Glad I could help. Ask me another question!"
        });

        knowledgeBase.put("goodbye", new String[]{
            "Goodbye! Have a great day.",
            "See you later! Keep learning.",
            "Bye! Come back whenever you need help."
        });

        knowledgeBase.put("help", new String[]{
            "You can ask me about Java, programming, college, exams, "
                    + "projects or NLP.",
            "Try asking: What is Java? What is NLP? Give me study tips."
        });
    }

    // Build the graphical interface
    private void createGUI() {

        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(blue);
        header.setBorder(
                BorderFactory.createEmptyBorder(18, 22, 18, 22));

        JLabel title = new JLabel("AI Chatbot");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 26));

        JLabel subtitle = new JLabel(
                "Java Swing | Rule-Based NLP");
        subtitle.setForeground(Color.WHITE);
        subtitle.setFont(new Font("Arial", Font.PLAIN, 13));

        JPanel headingPanel = new JPanel(new GridLayout(2, 1, 0, 5));
        headingPanel.setOpaque(false);
        headingPanel.add(title);
        headingPanel.add(subtitle);

        header.add(headingPanel, BorderLayout.WEST);

        clearButton = new JButton("Clear Chat");
        clearButton.setFocusPainted(false);
        header.add(clearButton, BorderLayout.EAST);

        add(header, BorderLayout.NORTH);

        // Conversation area
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setLineWrap(true);
        chatArea.setWrapStyleWord(true);
        chatArea.setFont(new Font("Arial", Font.PLAIN, 15));
        chatArea.setBackground(Color.WHITE);
        chatArea.setMargin(new Insets(15, 15, 15, 15));

        JScrollPane scrollPane = new JScrollPane(chatArea);
        scrollPane.setBorder(
                BorderFactory.createEmptyBorder());

        statusLabel = new JLabel("  Ready to chat");
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        statusLabel.setForeground(new Color(80, 100, 120));

        JPanel centerPanel = new JPanel(new BorderLayout(0, 8));
        centerPanel.setBackground(background);
        centerPanel.setBorder(
                BorderFactory.createEmptyBorder(10, 15, 0, 15));

        centerPanel.add(statusLabel, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);

        // Input area
        JPanel bottomPanel = new JPanel(new BorderLayout(10, 0));
        bottomPanel.setBackground(background);
        bottomPanel.setBorder(
                BorderFactory.createEmptyBorder(0, 15, 15, 15));

        inputField = new JTextField();
        inputField.setFont(new Font("Arial", Font.PLAIN, 15));
        inputField.setPreferredSize(new Dimension(100, 44));

        sendButton = new JButton("Send");
        sendButton.setBackground(blue);
        sendButton.setForeground(Color.WHITE);
        sendButton.setFont(new Font("Arial", Font.BOLD, 14));
        sendButton.setFocusPainted(false);
        sendButton.setPreferredSize(new Dimension(100, 44));

        bottomPanel.add(inputField, BorderLayout.CENTER);
        bottomPanel.add(sendButton, BorderLayout.EAST);

        add(bottomPanel, BorderLayout.SOUTH);

        // Button and keyboard actions
        sendButton.addActionListener(e -> sendMessage());

        inputField.addActionListener(e -> sendMessage());

        clearButton.addActionListener(e -> clearChat());

        getRootPane().setDefaultButton(sendButton);
    }

    // Show a message in the chat window
    private void addMessage(String sender, String message) {
        chatArea.append(sender + ":\n" + message + "\n\n");
        chatArea.setCaretPosition(
                chatArea.getDocument().getLength());
    }

    // Normalize user text for simple NLP processing
    private String normalizeText(String text) {
        return text.toLowerCase(Locale.ROOT)
                .replaceAll("[^\\p{L}\\p{N}\\s]", " ")
                .replaceAll("\\s+", " ")
                .trim();
    }

    // Check whether a message contains a complete keyword
    private boolean containsWord(String text, String word) {
        return (" " + text + " ").contains(" " + word + " ");
    }

    // Choose a random response from a category
    private String randomReply(String category) {
        String[] replies = knowledgeBase.get(category);

        if (replies == null || replies.length == 0) {
            return "Sorry, I don't have an answer for that yet.";
        }

        return replies[random.nextInt(replies.length)];
    }

    // Match user input against rules
    private String getBotResponse(String userInput) {

        String text = normalizeText(userInput);

        if (text.isEmpty()) {
            return "Please type a message first.";
        }

        // Specific questions first
        if (text.contains("how are you")
                || text.contains("how are u")) {
            return randomReply("how_are_you");
        }

        if (text.contains("who are you")
                || text.contains("your name")
                || text.contains("what are you")) {
            return randomReply("identity");
        }

        if (text.contains("what is nlp")
                || text.contains("meaning of nlp")
                || containsWord(text, "nlp")) {
            return randomReply("nlp");
        }

        if (text.contains("what is java")
                || text.contains("explain java")
                || containsWord(text, "java")) {
            return randomReply("java");
        }

        if (text.contains("study tips")
                || text.contains("how to study")
                || text.contains("study plan")) {
            return randomReply("study");
        }

        if (containsWord(text, "exam")
                || containsWord(text, "exams")
                || text.contains("exam preparation")) {
            return randomReply("exam");
        }

        if (containsWord(text, "college")) {
            return randomReply("college");
        }

        if (containsWord(text, "project")
                || containsWord(text, "projects")) {
            return randomReply("project");
        }

        if (text.contains("programming")
                || text.contains("coding")
                || text.contains("learn to code")) {
            return randomReply("programming");
        }

        if (text.contains("thank you")
                || containsWord(text, "thanks")
                || containsWord(text, "thank")) {
            return randomReply("thanks");
        }

        if (containsWord(text, "bye")
                || text.contains("goodbye")
                || text.contains("see you")) {
            return randomReply("goodbye");
        }

        if (containsWord(text, "help")
                || text.contains("what can you do")) {
            return randomReply("help");
        }

        // Greetings are checked after specific questions
        if (containsWord(text, "hello")
                || containsWord(text, "hi")
                || containsWord(text, "hey")) {
            return randomReply("greeting");
        }

        // Unknown question
        return "I'm still learning! I can answer questions about "
                + "Java, programming, NLP, studies, exams and projects. "
                + "Try asking one of these topics.";
    }

    // Process and display the message
    private void sendMessage() {

        try {
            String message = inputField.getText();

            if (message == null || message.trim().isEmpty()) {
                statusLabel.setText("Please enter a message.");
                Toolkit.getDefaultToolkit().beep();
                inputField.requestFocusInWindow();
                return;
            }

            addMessage("You", message.trim());

            String response = getBotResponse(message);

            addMessage("Bot", response);

            inputField.setText("");
            statusLabel.setText("Ready to chat");
            inputField.requestFocusInWindow();

        } catch (Exception ex) {
            statusLabel.setText("Something went wrong.");

            JOptionPane.showMessageDialog(
                    this,
                    "Could not process your message.\n"
                            + ex.getMessage(),
                    "Chatbot Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // Clear conversation
    private void clearChat() {

        int choice = JOptionPane.showConfirmDialog(
                this,
                "Do you want to clear the conversation?",
                "Clear Chat",
                JOptionPane.YES_NO_OPTION
        );

        if (choice == JOptionPane.YES_OPTION) {
            chatArea.setText("");
            statusLabel.setText("Conversation cleared");
            addMessage("Bot", "Hello again! How can I help you?");
            inputField.requestFocusInWindow();
        }
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(
                        UIManager.getSystemLookAndFeelClassName());

                AIChatbot chatbot = new AIChatbot();
                chatbot.setVisible(true);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(
                        null,
                        "Unable to start the chatbot.\n" + ex.getMessage(),
                        "Startup Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });
    }
}
