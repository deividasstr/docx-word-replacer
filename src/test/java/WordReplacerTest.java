import org.apache.poi.openxml4j.exceptions.NotOfficeXmlFileException;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import utils.WordCounter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class WordReplacerTest {

    private WordReplacer wordReplacer;
    private static File docxFile;
    private static WordCounter wordCounter;
    private static File replacedFile;

    @BeforeClass
    public static void before() throws Exception {
        docxFile = new File("./src/test/resources/docxfile.docx");
        wordCounter = new WordCounter();
        replacedFile = new File("./src/test/resources/docxfilereplaced.docx");
    }

    @Before
    public void setUp() throws Exception {
        wordReplacer = new WordReplacer(docxFile);
    }

    @Test
    public void initDocWordReplacer_whenFilenull_throwsNPE() {
        try {
            new WordReplacer((File) null);
            fail("Should have thrown nullpointer e");
        } catch (Exception e) {
            assertTrue("Should have thrown nullpointer e", e instanceof NullPointerException);
        }
    }

    @Test
    public void initDocWordReplacer_whenFileNonExistent_throwsIOE() {
        try {
            new WordReplacer(new File("non existent"));
            fail("Should have thrown IOE");
        } catch (Exception e) {
            assertTrue("Should have thrown IOE", e instanceof IOException);
        }
    }

    @Test
    public void initDocWordReplacer_whenFileNotDocx_throwsNotOfficeE() {
        try {
            File nonDocxFile = new File(
                    "./src/test/resources/notDocxFile.txt");
            new WordReplacer(nonDocxFile);
            fail("Should have thrown NotOffice e");
        } catch (Exception e) {
            assertTrue("Should have thrown NotOffice e", e instanceof NotOfficeXmlFileException);
        }
    }

    @Test
    public void initDocWordReplacer_whenXWPFDocNull_throwsNPE() {
        try {
            new WordReplacer((XWPFDocument) null);
            fail("Should have thrown nullpointer e");
        } catch (Exception e) {
            assertTrue("Should have thrown nullpointer e", e instanceof NullPointerException);
        }
    }

    @Test
    public void testReplaceWordsInText_And_getModdedXWPFDoc() {
        wordReplacer.replaceWordsInText(WordCounterTest.TEST_DOC_TEST_CASE_TEXT_1, TextReplacerTest.REPLACED_WORD);
        XWPFDocument modDoc = wordReplacer.getModdedXWPFDoc();
        assertEquals(1, wordCounter.countWordsInText(modDoc, TextReplacerTest.REPLACED_WORD));
    }

    @Test
    public void testReplaceWordsInTable_And_SaveToFile_And_getModdedFile() throws IOException {
        wordReplacer.replaceWordsInTables(WordCounterTest.TEST_DOC_TEST_CASE, TextReplacerTest.REPLACED_WORD_2);

        File modFile = wordReplacer.saveAndGetModdedFile("./src/test/resources/docxfilereplaced.docx");

        XWPFDocument modDoc = fromFile(modFile);
        assertEquals(4, wordCounter.countWordsInTable(modDoc, TextReplacerTest.REPLACED_WORD_2));
    }

    @Test
    public void testFromXWPFDoc_ReplaceWordsInText_And_SaveToFileFromPath_And_getModdedFile() throws IOException {
        XWPFDocument doc = fromFile(docxFile);
        WordReplacer replacer = new WordReplacer(doc);

        replacer.replaceWordsInText("test", TextReplacerTest.REPLACED_WORD_2);

        XWPFDocument modDoc = fromFile(replacer.saveAndGetModdedFile(replacedFile));
        assertEquals(5, wordCounter.countWordsInText(modDoc, TextReplacerTest.REPLACED_WORD_2));
    }

    private XWPFDocument fromFile(File file) throws IOException {
        InputStream inputStream = new FileInputStream(file);
        return new XWPFDocument(inputStream);
    }
}