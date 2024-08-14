package shared;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class Dictionary {
    private HashMap<String, List<String>> dictionary;

    public Dictionary() {
        dictionary = new HashMap<>();
    }
    
    
    public List<String> queryWord(String word) {
        return dictionary.get(word);
    }

    public boolean addWord(String word, String meaning) {
        if (!dictionary.containsKey(word)) {
            List<String> meanings = new ArrayList<>();
            meanings.add(meaning);
            dictionary.put(word, meanings);
            return true;
        }
        return false;
    }

    public boolean removeWord(String word) {
        return dictionary.remove(word) != null;
    }

    public boolean addMeaning(String word, String meaning) {
        List<String> meanings = dictionary.get(word);
        if (meanings != null && !meanings.contains(meaning)) {
            meanings.add(meaning);
            return true;
        }
        return false;
    }

    public boolean updateMeaning(String word, String oldMeaning, String newMeaning) {
        List<String> meanings = dictionary.get(word);
        if (meanings != null && meanings.remove(oldMeaning)) {
            meanings.add(newMeaning);
            return true;
        }
        return false;
    }

}

