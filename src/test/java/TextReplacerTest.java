import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.powermock.reflect.Whitebox;
import utils.WordCounter;

import java.io.*;

import static org.junit.Assert.assertEquals;

public class TextReplacerTest {

    public static final String REPLACED_WORD = "pogo";
    public static final String REPLACED_WORD_2 = "stick";

    public static final String TEXT_WITHOUT_BOOKMARK = "Something here";

    private static File docxFile;
    private static TextReplacer replacer;
    private static WordCounter wordCounter;

    private XWPFDocument document;

    @BeforeClass
    public static void before() throws Exception {
        docxFile = new File("./src/test/resources/docxfile.docx");
        replacer = new TextReplacer();
        wordCounter = new WordCounter();
    }

    @Before
    public void setUp() throws Exception {
        InputStream inputStream = new FileInputStream(docxFile);
        document = new XWPFDocument(inputStream);
    }

    @Test
    public void textReplacer_inText_shouldReplace4words() throws Exception {
        replacer.replaceInText(document, WordCounterTest.TEST_DOC_TEST_CASE, REPLACED_WORD);
        assertEquals(4, wordCounter.countWordsInText(document, REPLACED_WORD));
    }

    @Test
    public void textReplacer_inTable_shouldReplace4words() throws Exception {
        replacer.replaceInTable(document, WordCounterTest.TEST_DOC_TEST_CASE, REPLACED_WORD);
        assertEquals(4, wordCounter.countWordsInTable(document, REPLACED_WORD));
    }

    @Test
    public void textReplacer_inText_shouldReplace1word() throws Exception {
        replacer.replaceInText(document, WordCounterTest.TEST_DOC_TEST_CASE_TEXT_1, REPLACED_WORD);
        replacer.replaceInText(document, WordCounterTest.TEST_DOC_TEST_CASE_TEXT_3, REPLACED_WORD_2);

        assertEquals(1, wordCounter.countWordsInText(document, REPLACED_WORD));
        assertEquals(1, wordCounter.countWordsInText(document, REPLACED_WORD_2));
    }

    @Test
    public void textReplacer_inTable_shouldReplace1word() throws Exception {
        replacer.replaceInTable(document, WordCounterTest.TEST_DOC_TEST_CASE_TABLE_1, REPLACED_WORD);
        replacer.replaceInTable(document, WordCounterTest.TEST_DOC_TEST_CASE_TABLE_3, REPLACED_WORD_2);

        assertEquals(1, wordCounter.countWordsInTable(document, REPLACED_WORD));
        assertEquals(1, wordCounter.countWordsInTable(document, REPLACED_WORD_2));
    }

    @Test
    public void textReplacer_inText_shouldReplace0words() throws Exception {
        replacer.replaceInText(document, WordCounterTest.TEST_DOC_TEST_CASE_TABLE_2, REPLACED_WORD);
        replacer.replaceInText(document, WordCounterTest.TEST_DOC_TEST_CASE_RANDOM_WORD, REPLACED_WORD_2);

        assertEquals(0, wordCounter.countWordsInText(document, REPLACED_WORD)); // Scattered in different paragraphs
        assertEquals(0, wordCounter.countWordsInText(document, REPLACED_WORD_2));
    }

    @Test
    public void textReplacer_inTable_shouldReplace0words() throws Exception {
        replacer.replaceInTable(document, WordCounterTest.TEST_DOC_TEST_CASE_TABLE_2, REPLACED_WORD);
        replacer.replaceInTable(document, WordCounterTest.TEST_DOC_TEST_CASE_RANDOM_WORD, REPLACED_WORD_2);

        assertEquals(0, wordCounter.countWordsInTable(document, REPLACED_WORD)); // Scattered in different paragraphs
        assertEquals(0, wordCounter.countWordsInTable(document, REPLACED_WORD_2));
    }

    // Edge case, not required yet
    /*@Test
    public void wordCountInFile_shouldFind2ValsInSameRun() throws Exception {
        assertEquals(2, wordCounter.findWordsInDocx(doc, TEST_DOC_TEST_CASE_SAME_VALS_IN_SAME_RUN));
    }*/

    @Test
    public void textReplacer_shouldFind2ValsInSameParaDiffRuns() throws Exception {
        replacer.replaceInText(document, WordCounterTest.TEST_DOC_TEST_CASE_SAME_VALS_IN_ONE_PARA, REPLACED_WORD);
        replacer.replaceInTable(document, WordCounterTest.TEST_DOC_TEST_CASE_SAME_VALS_IN_ONE_PARA, REPLACED_WORD);

        assertEquals(2, wordCounter.countWordsInText(document, REPLACED_WORD));
        assertEquals(2, wordCounter.countWordsInTable(document, REPLACED_WORD));
    }

    @Test
    public void testPrivateGetRemainingBookmarkEnd() throws Exception {
        String remainingBookmark = Whitebox.invokeMethod(
                replacer,
                "getRemainingBookmarkEnd",
                "se_text_1" + TEXT_WITHOUT_BOOKMARK,
                WordCounterTest.TEST_DOC_TEST_CASE_TEXT_1);

        assertEquals("se_text_1", remainingBookmark);
    }

    @Test
    public void testPrivateGetRemainingBookmarkStart() throws Exception {
        String remainingBookmark = Whitebox.invokeMethod(
                replacer,
                "getRemainingBookmarkStart",
                 TEXT_WITHOUT_BOOKMARK + "test_cas",
                WordCounterTest.TEST_DOC_TEST_CASE_TEXT_1);

        assertEquals("test_cas", remainingBookmark);
    }
}
