import org.apache.poi.openxml4j.exceptions.NotOfficeXmlFileException;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class WordReplacerTest {

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

}