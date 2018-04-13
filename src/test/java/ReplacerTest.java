import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import utils.WordCounter;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;

public class ReplacerTest {

    private static final String REPLACED_WORD = "pogo";

    private final static String TEST_DOC_TEST_CASE = "test_case";

    private static File docxFile;
    private XWPFDocument document;

    @BeforeClass
    public static void before() throws Exception {
        docxFile = new File("./src/test/resources/docxfile.docx");
    }

    @Before
    public void setUp() throws Exception {
        InputStream inputStream = new FileInputStream(docxFile);
        document = new XWPFDocument(inputStream);
    }

    @Test
    public void replaceInText_shouldReplace4words() throws Exception {
        new TextReplacer().replaceInText(document, TEST_DOC_TEST_CASE, REPLACED_WORD);
        assertEquals(4, new WordCounter().countWordsInText(document, REPLACED_WORD));
    }
}
