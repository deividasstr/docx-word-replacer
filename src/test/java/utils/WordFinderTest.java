package utils;

import org.apache.poi.openxml4j.exceptions.NotOfficeXmlFileException;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

public class WordFinderTest {

    // Entries in the docx file
    private final static String TEST_DOC_TEST_CASE_TEXT_1 = "test_case_text_1";
    private final static String TEST_DOC_TEST_CASE_TEXT_2 = "test_case_text_2";
    private final static String TEST_DOC_TEST_CASE_TEXT_3 = "test_case_text_3";

    private final static String TEST_DOC_TEST_CASE_TABLE_1 = "test_case_table_1";
    private final static String TEST_DOC_TEST_CASE_TABLE_2 = "test_case_table_2";
    private final static String TEST_DOC_TEST_CASE_TABLE_3 = "test_case_table_3";

    private final static String TEST_DOC_TEST_CASE = "test_case";
    private final static String TEST_DOC_TEST_CASE_WORD = "word";
    private final static String TEST_DOC_TEST_CASE_RANDOM_WORD = "Uga-chaga";

    private final static String TEST_DOC_TEST_CASE_SAME_VALS_IN_SAME_RUN = "same_run_vals";
    private final static String TEST_DOC_TEST_CASE_SAME_VALS_IN_ONE_PARA = "same_vals_one_para";

    private File docxFile;
    private WordCounterInText wordCounterInText;
    private WordCounterInTable wordCounterInTable;

    @Before
    public void setup() {
        docxFile = new File("./src/test/resources/docxfile.docx");
        wordCounterInText = new WordCounterInText();
        wordCounterInTable = new WordCounterInTable();
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
                    "./src/test/resources/notDocxFile.txt");
            wordCounterInText.wordCountInFile(nonDocxFile, "");
            fail("Should have thrown NotOffice e");
        } catch (Exception e) {
            assertTrue("Should have thrown NotOffice e", e instanceof NotOfficeXmlFileException);
        }
    }

    @Test
    public void wordCountInFile_shouldFindOneVal() throws Exception {
        assertEquals(1, wordCounterInTable.wordCountInFile(docxFile, TEST_DOC_TEST_CASE_TABLE_1));
        assertEquals(1, wordCounterInTable.wordCountInFile(docxFile, TEST_DOC_TEST_CASE_TABLE_3));

        assertEquals(1, wordCounterInText.wordCountInFile(docxFile, TEST_DOC_TEST_CASE_TEXT_1));
        assertEquals(1, wordCounterInText.wordCountInFile(docxFile, TEST_DOC_TEST_CASE_TEXT_3));
    }

    @Test
    public void wordCountInFile_shouldFind4Vals() throws Exception {
        assertEquals(4, wordCounterInText.wordCountInFile(docxFile, TEST_DOC_TEST_CASE));
        assertEquals(4, wordCounterInTable.wordCountInFile(docxFile, TEST_DOC_TEST_CASE));
    }

    @Test
    public void wordCountInFile_shouldNoVals() throws Exception {
        assertEquals(0, wordCounterInText.wordCountInFile(docxFile, TEST_DOC_TEST_CASE_TEXT_2)); // Scattered in different paragraphs
        assertEquals(0, wordCounterInText.wordCountInFile(docxFile, TEST_DOC_TEST_CASE_RANDOM_WORD));

        assertEquals(0, wordCounterInTable.wordCountInFile(docxFile, TEST_DOC_TEST_CASE_TABLE_2)); // Scattered in different paragraphs
        assertEquals(0, wordCounterInTable.wordCountInFile(docxFile, TEST_DOC_TEST_CASE_RANDOM_WORD));
    }

    // Edge case, not required yet
    /*@Test
    public void wordCountInFile_shouldFind2ValsInSameRun() throws Exception {
        assertEquals(2, wordCounterInText.wordCountInFile(docxFile, TEST_DOC_TEST_CASE_SAME_VALS_IN_SAME_RUN));
    }*/

    @Test
    public void wordCountInFile_shouldFind2ValsInSameParaDiffRuns() throws Exception {
        assertEquals(2, wordCounterInText.wordCountInFile(docxFile, TEST_DOC_TEST_CASE_SAME_VALS_IN_ONE_PARA));
    }
}