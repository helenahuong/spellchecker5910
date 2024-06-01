import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.nio.file.Path;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class WordRecommenderTest {

    WordRecommender wordRecommender;
    String testDictionaryFileName;

    @BeforeEach
    void setUp(@TempDir Path tempDir) throws IOException {
        testDictionaryFileName = tempDir.resolve("testDictionary.txt").toString();

        // Create a test file
        ArrayList<String> lines = new ArrayList<>();
        lines.add("word");
        lines.add("world");
        lines.add("ward");
        // Add more dictionary words as needed for testing
        Files.write(Path.of(testDictionaryFileName), lines);

        wordRecommender = new WordRecommender(testDictionaryFileName);
    }

    @Test
    void getWordSuggestions() {
        // Assuming 'word' is a word in your dictionary and you are testing for suggestions
        ArrayList<String> suggestions = wordRecommender.getWordSuggestions("wrd", 1, 50.0, 3);
        assertNotNull(suggestions, "Suggestions should not be null");
        assertFalse(suggestions.isEmpty(), "Suggestions should not be empty");
        assertTrue(suggestions.contains("word"), "Suggestions should contain 'word'");
    }

    @Test
    void loadDictionary() {
        ArrayList<String> dictionary = wordRecommender.loadDictionary(testDictionaryFileName);
        assertNotNull(dictionary, "Dictionary should not be null");
        assertFalse(dictionary.isEmpty(), "Dictionary should not be empty");
        assertEquals(3, dictionary.size(), "Dictionary should contain 3 words");
    }

    @Test
    void getSimilarity() {
        double similarity = wordRecommender.getSimilarity("word", "word");
        assertEquals(4.0, similarity, 0.01, "Similarity of identical words should be 1.0");

        similarity = wordRecommender.getSimilarity("word", "ward");
        assertNotEquals(4.0, similarity, "Similarity of different words should not be 1.0");
        // You may need to adjust the expected value based on your similarity calculation logic
    }
}
