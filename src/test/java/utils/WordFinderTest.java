package utils;

import org.apache.poi.openxml4j.exceptions.NotOfficeXmlFileException;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

public class WordFinderTest {

    // Entries in the docx file
    private final static String TEST_DOC_TEST_CASE_1 = "test_case_1";
    private final static String TEST_DOC_TEST_CASE_2 = "test_case_2";
    private final static String TEST_DOC_TEST_CASE_3 = "test_case_3";

    private File docxFile;
    private WordCounterInText wordCounterInText;
    private WordCounterInTable wordCounterInTable;

    @Before
    public void setup() {
        docxFile = new File("/home/d/Documents/projects/docx-word-replacer/src/test/resources/docxfile.docx");
        wordCounterInText = new WordCounterInText();
    }

    @Test
    public void fileContainsWordInText_whenFilenull_throwsNPE() {
        try {
            wordCounterInText.wordCountInFile(null, "");
            fail("Should have thrown nullpointer e");
        } catch (Exception e) {
            assertTrue("Should have thrown nullpointer e", e instanceof NullPointerException);
        }
    }

    @Test
    public void fileContainsWordInText_whenFileNonExistent_throwsIOE() {
        try {
            wordCounterInText.wordCountInFile(new File("non existent"), "");
            fail("Should have thrown IOE");
        } catch (Exception e) {
            assertTrue("Should have thrown IOE", e instanceof IOException);
        }
    }

    @Test
    public void fileContainsWordInText_whenFileNotDocx_throwsNotOfficeE() {
        try {
            File nonDocxFile = new File(
                    "/home/d/Documents/projects/docx-word-replacer/src/test/resources/notDocxFile.txt");
            wordCounterInText.wordCountInFile(nonDocxFile, "");
            fail("Should have thrown NotOffice e");
        } catch (Exception e) {
            assertTrue("Should have thrown NotOffice e", e instanceof NotOfficeXmlFileException);
        }
    }

    @Test
    public void wordCountInFileText() throws Exception {
        assertEquals(wordCounterInText.wordCountInFile(docxFile, TEST_DOC_TEST_CASE_1), 1);
        assertEquals(wordCounterInText.wordCountInFile(docxFile, TEST_DOC_TEST_CASE_3), 1);
        assertEquals(wordCounterInText.wordCountInFile(docxFile, "test_case"), 3);
        assertEquals(wordCounterInText.wordCountInFile(docxFile, TEST_DOC_TEST_CASE_2), 0); // Scattered in different paragraphs
        assertEquals(wordCounterInText.wordCountInFile(docxFile, "Uga-chaga"), 0);
    }

    @Test
    public void wordCountInFileTable() throws Exception {
        assertEquals(wordCounterInTable.wordCountInFile(docxFile, TEST_DOC_TEST_CASE_1), 1);
        assertEquals(wordCounterInTable.wordCountInFile(docxFile, TEST_DOC_TEST_CASE_3), 1);
        assertEquals(wordCounterInTable.wordCountInFile(docxFile, "test_case"), 3);
        assertEquals(wordCounterInTable.wordCountInFile(docxFile, TEST_DOC_TEST_CASE_2), 0); // Scattered in different paragraphs
        assertEquals(wordCounterInTable.wordCountInFile(docxFile, "Uga-chaga"), 0);
    }
}
