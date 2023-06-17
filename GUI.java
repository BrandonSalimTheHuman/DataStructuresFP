package FinalProject;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Objects;

public class GUI implements ActionListener {
    JFrame frame;
    JTextArea text_area;
    JScrollPane scroll_pane;
    JButton button;
    JLabel method_label;
    JLabel time_label;
    JLabel[] top_10_words;
    JPanel main_panel;
    JCheckBox pronouns;
    boolean filter_pronouns;
    JCheckBox prepositions;
    boolean filter_prepositions;
    JCheckBox articles;
    boolean filter_articles;
    JCheckBox word_forms;
    boolean change_words_to_root;
    JPanel checkbox_panel;
    Font button_font;
    Font title_and_time_font;
    String chosen_method;

    public GUI(String method){
        chosen_method = method;
        // Create new frame, set size, center it, set its title
        // and make the program terminate when the close button is clicked
        frame = new JFrame();
        frame.setSize(700, 900);
        frame.setLocationRelativeTo(null);
        frame.setTitle("Top 10 Used Words Finder");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a panel to store all the component, set its layout into a vertical box layout
        // with center horizontal alignment
        main_panel = new JPanel();
        main_panel.setLayout(new BoxLayout(main_panel, BoxLayout.Y_AXIS));
        main_panel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Create new text area and set its maximum size
        text_area = new JTextArea();

        // Create a scroll pane for the text area for large inputs
        scroll_pane = new JScrollPane(text_area);
        scroll_pane.setMaximumSize(new Dimension(600, 200));

        // Create 3 checkboxes: One for pronouns, one for prepositions, one for articles
        // Also, add item listeners
        pronouns = new JCheckBox("Filter pronouns");
        pronouns.addItemListener(new ItemListener() {
            // For each of these checkboxes, update the boolean value to know whether they should be executed
            @Override
            public void itemStateChanged(ItemEvent e) {
                filter_pronouns = e.getStateChange() == ItemEvent.SELECTED;
            }
        });
        prepositions = new JCheckBox("Filter prepositions");
        prepositions.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                filter_prepositions = e.getStateChange() == ItemEvent.SELECTED;
            }
        });
        articles = new JCheckBox("Filter articles");
        articles.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                filter_articles = e.getStateChange() == ItemEvent.SELECTED;
            }
        });

        word_forms = new JCheckBox("Change words to root");
        word_forms.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                change_words_to_root = e.getStateChange() == ItemEvent.SELECTED;
            }
        });

        // Create a panel to store the three checkboxes in a horizontal box layout
        checkbox_panel = new JPanel();
        checkbox_panel.setLayout(new BoxLayout(checkbox_panel, BoxLayout.X_AXIS));
        checkbox_panel.add(pronouns);
        checkbox_panel.add(prepositions);
        checkbox_panel.add(articles);
        checkbox_panel.add(word_forms);

        // Create new button, set its maximum size, set its font and set its alignment
        button = new JButton("Click to calculate");
        button_font = new Font("Georgia", Font.PLAIN, 25);
        button.setFont(button_font);
        button.setMaximumSize(new Dimension(250, 45));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.addActionListener(this);

        // Create a dimension for the two labels that will be created next
        Dimension dimension = new Dimension(700, 75);

        // Create first new label to show the method being used along with its font, maximum size and alignment
        method_label = new JLabel();
        method_label.setText("Using " + chosen_method);
        title_and_time_font = new Font("Georgia", Font.PLAIN, 40);
        method_label.setFont(title_and_time_font);
        method_label.setMaximumSize(dimension);
        method_label.setAlignmentX(Component.CENTER_ALIGNMENT);
        method_label.setHorizontalAlignment(JLabel.CENTER);

        // Create second new label to show the time taken
        time_label = new JLabel();
        time_label.setText("");
        time_label.setFont(title_and_time_font);
        time_label.setMaximumSize(dimension);
        time_label.setAlignmentX(Component.CENTER_ALIGNMENT);
        time_label.setHorizontalAlignment(JLabel.CENTER);

        // Create a dimension for the top 10 words
        Dimension dimension2 = new Dimension(700, 40);

        // Create labels for the top 10 words, set their font, maximum size and alignment
        top_10_words = new JLabel[10];
        for (int i = 0; i < 10; i++){
            top_10_words[i] = new JLabel();
            top_10_words[i].setFont(button_font);
            top_10_words[i].setText("");
            top_10_words[i].setMaximumSize(dimension2);
            top_10_words[i].setAlignmentX(Component.CENTER_ALIGNMENT);
            top_10_words[i].setHorizontalAlignment(JLabel.CENTER);
        }


        // Add everything to the panel
        main_panel.add(method_label);
        main_panel.add(scroll_pane);
        // Adding a strut here so that there's space between the text area and the checkboxes
        main_panel.add(Box.createVerticalStrut(10));
        main_panel.add(checkbox_panel);
        // Another strut so that there's space between the checkboxes and the button
        main_panel.add(Box.createVerticalStrut(10));
        main_panel.add(button);
        // Another strut so that there's space between the button and the top 10 words
        main_panel.add(Box.createVerticalStrut(15));
        for (int i = 0; i < 10; i++){
            main_panel.add(top_10_words[i]);
        }
        // One last strut so there's space between the list and the label for the time taken
        main_panel.add(Box.createVerticalStrut(10));
        main_panel.add(time_label);


        // Add the panel to the frame
        frame.add(main_panel);

        // Make frame visible
        frame.setVisible(true);
    }

    // This is for the button to calculate
    @Override
    public void actionPerformed(ActionEvent e) {
        // Gets the text from the input area
        String text_area_input = text_area.getText();

        // If the text box is empty
        if (Objects.equals(text_area_input, "")){
            top_10_words[0].setText("Text area is empty");
            for(int i = 1; i < 10; i++){
                top_10_words[i].setText("");
            }
            return;
        }

        // If the chosen method is hashmap
        if (Objects.equals(chosen_method, "Hashmap")){
            // Create new instance of UsingHashMap
            UsingHashMap hashmap = new UsingHashMap(text_area_input);

            // Call the method to calculate the top 10 words
            hashmap.calculate_top_10_hashmap(filter_pronouns, filter_prepositions, filter_articles, change_words_to_root);

            // Set the text on the gui based on the list of the top 10
            for (int i = 0; i < Math.min(10, hashmap.list_result.size()); i++){
                top_10_words[i].setText(i+1 + ". " + hashmap.list_result.get(i).getKey() + ": " + hashmap.list_result.get(i).getValue());
            }

            // If there are less than 10 words, clear the rest
            // This is because the user might've used all 10 lines on a previous run
            if (hashmap.list_result.size() < 10){
                for (int j = hashmap.list_result.size(); j < 10; j++){
                    top_10_words[j].setText("");
                }
            }

            // Show time taken
            time_label.setText("Time taken: " + hashmap.time_taken + "ms");
        }

        // IF the chosen method is BST
        if (Objects.equals(chosen_method, "BST")){
            // Create new instance of UsingBinarySearchTree
            UsingBinarySearchTree BST = new UsingBinarySearchTree(text_area_input);

            // Call the method to calculate the top 10 words
            BST.calculate_top_10_BST(filter_pronouns, filter_prepositions, filter_articles, change_words_to_root);

            // Set the text on the gui based on the list of the top 10
            for (int i = 0; i < BST.list_result.size(); i++){
                top_10_words[i].setText(i+1 + ". " + BST.list_result.get(i).word + ": " + BST.list_result.get(i).count);
            }

            // If there are less than 10 words, clear the rest
            // This is because the user might've used all 10 lines on a previous run
            if (BST.list_result.size() < 10){
                for (int j = BST.list_result.size(); j < 10; j++){
                    top_10_words[j].setText("");
                }
            }

            // Show time taken
            time_label.setText("Time taken: " + BST.time_taken + "ms");
        }

    }

    public static void main(String[] args){
        GUI gui = new GUI("BST");
    }
}
