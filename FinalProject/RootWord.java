package FinalProject;

import java.util.List;

public class RootWord {
    // Has the root word, and a list of all its forms
    String word;
    List<String> forms;
    // Constructor for RootWord class
    public RootWord(String name, List<String> all_forms) {
        word = name;
        forms = all_forms;
    }
}
