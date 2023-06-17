package FinalProject;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;

public class RootWords {
    // List for all words
    List<RootWord> contents;
    // The RootWord class

    // Constructor for RootWords class
    public RootWords(){
        // Create array list
        contents = new ArrayList<>();

        // Text file of all root words and forms
        File file = new File("C:\\Users\\Brandon Salim\\IdeaProjects\\Data_structures\\src\\FinalProject\\list_of_root_words.txt");
        Scanner scan;
        String tmp_input;
        String[] tmp_words;
        List<String> tmp_forms;

        // Scan contents of file
        try {
            scan = new Scanner(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        // For each line in the text file
        while (scan.hasNextLine()){
            tmp_input = scan.nextLine();
            // Convert to lowercase and remove commas
            tmp_words = tmp_input.replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+");

            // Make an arraylist to store root words
            tmp_forms = new ArrayList<>(Arrays.asList(tmp_words));

            // The first index is the root word, so remove it
            tmp_forms.remove(0);

            // Add the root word and its forms to all_words
            contents.add(new RootWord(tmp_words[0], tmp_forms));
        }
    }
}

