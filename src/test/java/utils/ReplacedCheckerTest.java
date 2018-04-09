package utils;

import org.apache.poi.openxml4j.exceptions.NotOfficeXmlFileException;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class ReplacedCheckerTest {

    private final static String TEST_DOC_TEST_CASE_1 = "test_case_1";
    private final static String TEST_DOC_TEST_CASE_2 = "test_case_2";
    private final static String TEST_DOC_TEST_CASE_3 = "test_case_3";

    private File docxFile;

    @Before
    public void setup() {
        docxFile = new File("/home/d/Documents/projects/docx-word-replacer/src/test/resources/docxfile.docx");
    }

    @Test
    public void fileContainsWordInText_whenFilenull_throwsNPE() {
        try {
            ReplacedChecker.fileContainsWordInText(null, "");
            fail("Should have thrown nullpointer e");
        } catch (Exception e) {
            assertTrue("Should have thrown nullpointer e", e instanceof NullPointerException);
        }
    }

    @Test
    public void fileContainsWordInText_whenFileNonExistent_throwsIOE() {
        try {
            ReplacedChecker.fileContainsWordInText(new File("non existent"), "");
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
            ReplacedChecker.fileContainsWordInText(nonDocxFile, "");
            fail("Should have thrown NotOffice e");
        } catch (Exception e) {
            assertTrue("Should have thrown NotOffice e", e instanceof NotOfficeXmlFileException);
        }
    }

    @Test
    public void fileContainsWordInText() throws Exception {
        assertTrue(ReplacedChecker.fileContainsWordInText(docxFile, TEST_DOC_TEST_CASE_1));
        assertFalse(ReplacedChecker.fileContainsWordInText(docxFile, TEST_DOC_TEST_CASE_2)); // Scattered in different paragraphs
        assertTrue(ReplacedChecker.fileContainsWordInText(docxFile, TEST_DOC_TEST_CASE_3));
    }
}
