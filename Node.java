package FinalProject;

public class Node {
    // A node has the key (word), the value (count) and
    // branches into two nodes, where the left is smaller and the right is larger
    String word;
    int count;
    Node left;
    Node right;

    // Constructor for a node
    public Node(String new_word){
        word = new_word;
        count = 1;
        left = right = null;
    }
}
