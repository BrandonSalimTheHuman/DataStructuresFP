package FinalProject;

import java.util.*;

public class UsingHashMap {
    List<Map.Entry<String, Integer>> list_result;
    long time_taken;
    String input;
    List<String> to_insert;
    List<Integer> to_insert_count;
    List<String> to_delete;

    // Constructor for UsingHashMap
    public UsingHashMap(String input_from_text_area) {
        input = input_from_text_area;
    }
    public void calculate_top_10_hashmap(boolean filter_pronouns, boolean filter_prepositions, boolean filter_articles, boolean change_words_to_root){
        // Deletes everything except for lowercase and uppercase letters, whitespace, dashes and apostrophe
        // Then set all to lower case and splitting them based on 1 or more whitespaces
        String[] words = input.replaceAll("[^a-zA-Z-' ]", "").toLowerCase().split("\\s+");
        long time1 = System.currentTimeMillis();

        // Create hashmap
        HashMap<String, Integer> map = new HashMap<>();

        // If the key doesn't exist, it'll put 0 + 1 = 1 and make a new key-value pair
        // Else, it'll increment the current value by 1 and update the key-value pair
        for (String word : words) {
            map.put(word, map.getOrDefault(word, 0) + 1);
        }

        // Apply filtering
        if (filter_pronouns){
            // Filter pronouns
            Pronouns pronouns = new Pronouns();
            for (String pronoun : pronouns.contents){
                map.remove(pronoun);
            }
        }
        if (filter_prepositions){
            // Filter prepositions
            Prepositions prepositions = new Prepositions();
            for (String preposition : prepositions.contents){
                map.remove(preposition);
            }
        }
        if (filter_articles){
            // Filter articles
            Articles articles = new Articles();
            for (String article : articles.contents){
                map.remove(article);
            }
        }

        if (change_words_to_root){
            // Convert to root words

            // Create array lists
            to_insert = new ArrayList<>();
            to_insert_count = new ArrayList<>();
            to_delete = new ArrayList<>();

            // Create new instance of RootWords
            RootWords root_words = new RootWords();

            // For each key (word) in the map
            for (String key : map.keySet()){
                // Check against each root word in all_words
                for (RootWord root_word : root_words.contents) {
                    // If the root word's forms contain the key
                    if (root_word.forms.contains(key)){
                        // Add the root word to insert
                        to_insert.add(root_word.word);
                        // Add the number of times the root word is to be inserted
                        to_insert_count.add(map.get(key));
                        // Delete the key-value pair of the root word's word form
                        to_delete.add(key);
                    }
                }
            }

            for (String key : to_delete) {
                // Delete everything to be deleted
                map.remove(key);
            }

            for (int i = 0; i < to_insert.size(); i++){
                // Insert everything to be inserted with their correct count
                map.put(to_insert.get(i), map.getOrDefault(to_insert.get(i), 0) + to_insert_count.get(i));
            }
        }

        // Create arraylist of Map.Entry objects, where each Map.Entry object is a key-value pair of String and Integer
        List<Map.Entry<String, Integer>> list = new ArrayList<>(map.entrySet());

        // Sort the list based on the value of the keys (descending)
        list.sort((entry1, entry2) -> {
            // If negative, entry1.getValue() > entry2.getValue()
            // If positive, entry1.getValue() < entry2.getValue()
            int frequency_difference = entry2.getValue() - entry1.getValue();
            // If tied,the two keys are compared lexicographically
            if (frequency_difference == 0) {
                return entry1.getKey().compareTo(entry2.getKey());
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

        long time2 = System.currentTimeMillis();

        time_taken = time2 - time1;
    }
}
