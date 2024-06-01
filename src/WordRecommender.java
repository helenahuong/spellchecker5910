import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class WordRecommender {
    private ArrayList<String> dictionary;

    public WordRecommender(String dictionaryFile)  {
        // TODO: change this!
        this.dictionary = loadDictionary(dictionaryFile);


    }



    public ArrayList<String> getWordSuggestions(String word, int tolerance, double commonPercent, int topN) {
        // TODO: change this!


        ArrayList<String> suggestions = new ArrayList<>();
        ArrayList<String> topCandidates = new ArrayList<>();

        HashSet<Character> wordSet = new HashSet<>();

        for (char c : word.toCharArray()) {
            wordSet.add(c);
        }

        for (String candidate : dictionary) {
            // Check length difference and common percent
            if (Math.abs(word.length() - candidate.length()) <= tolerance) {
                HashSet<Character> candidateSet = new HashSet<>();
                for (char c : candidate.toCharArray()) {
                    candidateSet.add(c);
                }

                HashSet<Character> intersection = new HashSet<>(wordSet);
                intersection.retainAll(candidateSet);

                HashSet<Character> union = new HashSet<>(wordSet);
                union.addAll(candidateSet);

                double similarity = (double) intersection.size() / union.size();
                if (similarity >= commonPercent/100.0) {
                    suggestions.add(candidate);
                }
            }
        }


        if(suggestions.isEmpty()){
            return suggestions;
        }
        else {
            double maxSimilarity = 0.0;
            String mostSimilar = suggestions.get(0);
            for (int i = 0; i < topN && i<suggestions.size(); i++) {
                for (int j = 0; j < suggestions.size(); j++) {
                    if (getSimilarity(word, suggestions.get(j)) > maxSimilarity) {
                        maxSimilarity = getSimilarity(word, suggestions.get(j));
                        mostSimilar = suggestions.get(j);
                    }

                }
                topCandidates.add(i, mostSimilar);
                suggestions.remove(mostSimilar);
                maxSimilarity = 0.0;
            }

            return topCandidates;

        }
    }





    public ArrayList<String> loadDictionary(String fileName) {
        ArrayList<String> dictionary = new ArrayList<>();
        try (Scanner fileScanner = new Scanner(new File(fileName))) {
            while (fileScanner.hasNext()) {
                dictionary.add(fileScanner.next());
            }
        } catch (IOException e) {

        }
        return dictionary;
    }

    public double getSimilarity(String word1, String word2) {
        double leftSimilarity = 0.0;
        double rightSimilarity = 0.0;

        // Calculate left similarity
        int minLength = Math.min(word1.length(), word2.length());
        for (int i = 0; i < minLength; i++) {
            if (word1.charAt(i) == word2.charAt(i)) {
                leftSimilarity++;
            }
        }

        // Calculate right similarity
        for (int i = 0; i < minLength; i++) {
            if (word1.charAt(word1.length() - 1 - i) == word2.charAt(word2.length() - 1 - i)) {
                rightSimilarity++;
            }
        }

        return (leftSimilarity + rightSimilarity) / 2.0;




    }


}
