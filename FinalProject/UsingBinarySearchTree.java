package FinalProject;

import java.util.ArrayList;
import java.util.List;

public class UsingBinarySearchTree {
    Node root;
    String input;
    long time_taken;
    List<Node> list_result;
    List<String> to_insert;
    List<Integer> to_insert_count;
    List<String> to_delete;

    // Constructor for the binary search tree
    public UsingBinarySearchTree(String input_from_text_area){
        root = null;
        input = input_from_text_area;
    }

    // Method to insert
    public Node insert(Node root, String word, Integer increment){
        // If the tree is empty
        if (root == null){
            root = new Node(word);
            return root;
        }

        // If the tree isn't empty
        int difference = word.compareTo(root.word);

        // If difference < 0, the root is lexicographically larger than the word, and the word is sent to the left node
        if (difference < 0){
            root.left = insert(root.left, word, increment);
        }
        // If difference > 0, the root is lexicographically smaller than the word, and the word is sent to the right node
        else if (difference > 0){
            root.right = insert(root.right, word, increment);
        }
        // Else, difference = 0 and the counter for the word is incremented by 1
        else {
            root.count += increment;
        }

        return root;
    }

    // Method to remove
    public Node delete(Node root, String word){
        // The word isn't in the tree
        if (root == null){
            return null;
        }

        // Get difference, then send on the correct path
        int difference = word.compareTo(root.word);
        // Negative difference means the word is on the left path
        if (difference < 0){
            root.left = delete(root.left, word);
        }
        // positive difference means that the word is on the right path
        else if (difference > 0){
            root.right = delete(root.right, word);
        }

        // Else, difference is 0, and we found the word in the tree
        else {
            // Three are four possibilities from here
            // One, the node we want is a leaf node
            // Two, the node has a child node, to its left
            // Three, the node has a child node, to its right
            // Four, the node has two child nodes

            // Possibility one. Simply delete the node.
            if (root.left == null && root.right == null){
                return null;
            }

            // Possibility two. The parent of the node needs to be connected to the child node.
            // So I directly replace the node with its child node
            else if (root.right == null){
                return root.left;
            }

            // Possibility three. Same thing as the second possibility, but on a different side.
            else if (root.left == null){
                return root.right;
            }

            // Possibility four. I can replace the node either with the leftmost node on the right subtree
            // or with the rightmost node on the left subtree
            // In this code, I will choose the latter.
            else {
                Node rightmost_node = find_rightmost(root.left);
                // I need to actually change the contents in the root because this rightmost_node will be deleted
                // to prevent duplicates
                root.word = rightmost_node.word;
                root.count = rightmost_node.count;
                // Deleting the rightmost node
                root.left = delete(root.left, rightmost_node.word);
            }
        }
        return root;
    }

    public Node find_rightmost(Node node){
        // Keep on finding the right child of the node until there's no more right child
        while(node.right != null){
            node = node.right;
        }

        return node;
    }

    public List<Node> put_tree_into_list(Node root, List<Node> list){
        // Recursively put every node from the tree into a list
        if (root != null) {
            put_tree_into_list(root.left, list);
            list.add(root);
            put_tree_into_list(root.right, list);
        }
        return list;
    }

    // Method to convert word to root form
    public void convert_to_root_word(Node root, RootWords root_words){
        // If root isn't null
        if (root != null) {
            // Check each word in root_words.all_words
            // If the root's word is in any of those word's forms
            for (RootWord root_word : root_words.contents) {
                if (root_word.forms.contains(root.word)){
                    // Add the root word to insert
                    to_insert.add(root_word.word);
                    // Add the number of times the root word is to be inserted
                    to_insert_count.add(root.count);
                    // Delete the root node of the root word's word form
                    to_delete.add(root.word);
                    break;
                }
            }

            // Recursively apply to all the other nodes
            convert_to_root_word(root.left, root_words);
            convert_to_root_word(root.right, root_words);
        }
    }

    public void calculate_top_10_BST(boolean filter_pronouns, boolean filter_prepositions, boolean filter_articles, boolean change_words_to_root){
        // Deletes everything except for lowercase and uppercase letters, whitespace, dashes and apostrophe
        // Then set all to lower case and splitting them based on 1 or more whitespaces
        String[] words = input.replaceAll("[^a-zA-Z-' ]", "").toLowerCase().split("\\s+");

        System.out.println(words.length);


        // Start timer
        long time1 = System.currentTimeMillis();

        // Insert all words into binary search tree
        for (String word : words) {
            root = insert(root, word, 1);
        }

        // Apply filtering
        if (filter_pronouns){
            // Filter pronouns
            Pronouns pronouns = new Pronouns();
            for (String pronoun : pronouns.contents){
                root = delete(root, pronoun);
            }
        }
        if (filter_prepositions){
            // Filter prepositions
            Prepositions prepositions = new Prepositions();
            for (String preposition : prepositions.contents){
                root = delete(root, preposition);
            }
        }
        if (filter_articles){
            // Filter articles
            Articles articles = new Articles();
            for (String article : articles.contents){
                root = delete(root, article);
            }
        }
        if (change_words_to_root){
            to_insert = new ArrayList<>();
            to_insert_count = new ArrayList<>();
            to_delete = new ArrayList<>();

            // Change to root words
            // Create new instance of RootWords
            RootWords root_words = new RootWords();

            // Call method to check for all word forms
            convert_to_root_word(root, root_words);

            // delete all nodes to be deleted
            for (String word : to_delete){
                root = delete(root, word);
            }

            // Insert all nodes to be inserted, with the count from the previous nodes
            for (int i = 0; i < to_insert.size(); i++){
                root = insert(root, to_insert.get(i), to_insert_count.get(i));
            }

        }

        // Create a list of nodes
        List<Node> list = new ArrayList<>();

        // Put tree into list
        list = put_tree_into_list(root, list);

        // Sort the list based on the value of the keys (descending)
        list.sort((node1, node2) -> {
            // If negative, node1.count > node2.count
            // If positive, node1.count < node2.count
            int frequency_difference = node2.count - node1.count;
            // If tied,the two keys are compared lexicographically
            if (frequency_difference == 0) {
                return node1.word.compareTo(node2.word);
            }
            // Else, return the frequency difference
            else {
                return frequency_difference;
            }
        });

        // Lists out the first ten key-value pairs
        list_result = new ArrayList<>();
        for (int i = 0; i < 10 && i < list.size(); i++) {
            list_result.add(list.get(i));
        }

        // Stop timer
        long time2 = System.currentTimeMillis();

        // Calculate time taken
        time_taken = time2 - time1;
    }
}