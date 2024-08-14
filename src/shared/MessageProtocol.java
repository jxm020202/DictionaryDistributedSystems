package shared;

import java.util.List;

public class MessageProtocol {
    public static final String ADD = "ADD";
    public static final String REMOVE = "REMOVE";
    public static final String QUERY = "QUERY";
    public static final String ADD_MEANING = "ADD_MEANING";
    public static final String UPDATE_MEANING = "UPDATE_MEANING";

    public static String processRequest(String request, Dictionary dictionary) {
        String[] tokens = request.split(" ", 3); // Split the request into parts
        String command = tokens[0];
        String word, meaning, oldMeaning, newMeaning;

        switch (command) {
            case ADD:
                word = tokens[1];
                meaning = tokens[2];
                if (dictionary.addWord(word, meaning)) {
                    return "Success: Word added";
                } else {
                    return "Error: Word already exists";
                }

            case REMOVE:
                word = tokens[1];
                if (dictionary.removeWord(word)) {
                    return "Success: Word removed";
                } else {
                    return "Error: Word not found";
                }

            case QUERY:
                word = tokens[1];
                List<String> meanings = dictionary.queryWord(word);
                if (meanings != null) {
                    return "Meanings: " + String.join(", ", meanings);
                } else {
                    return "Error: Word not found";
                }

            case ADD_MEANING:
                word = tokens[1];
                meaning = tokens[2];
                if (dictionary.addMeaning(word, meaning)) {
                    return "Success: Meaning added";
                } else {
                    return "Error: Word not found or meaning already exists";
                }

            case UPDATE_MEANING:
                word = tokens[1];
                oldMeaning = tokens[2];
                newMeaning = tokens[3];
                if (dictionary.updateMeaning(word, oldMeaning, newMeaning)) {
                    return "Success: Meaning updated";
                } else {
                    return "Error: Word or meaning not found";
                }

            default:
                return "Error: Invalid command";
        }
    }
}

