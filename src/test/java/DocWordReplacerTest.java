import org.apache.poi.openxml4j.exceptions.NotOfficeXmlFileException;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class DocWordReplacerTest {

    @Test
    public void initDocWordReplacer_whenFilenull_throwsNPE() {
        try {
            new DocWordReplacer((File) null);
            fail("Should have thrown nullpointer e");
        } catch (Exception e) {
            assertTrue("Should have thrown nullpointer e", e instanceof NullPointerException);
        }
    }

    @Test
    public void initDocWordReplacer_whenFileNonExistent_throwsIOE() {
        try {
            new DocWordReplacer(new File("non existent"));
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
            new DocWordReplacer(nonDocxFile);
            fail("Should have thrown NotOffice e");
        } catch (Exception e) {
            assertTrue("Should have thrown NotOffice e", e instanceof NotOfficeXmlFileException);
        }
    }

    @Test
    public void initDocWordReplacer_whenXWPFDocNull_throwsNPE() {
        try {
            new DocWordReplacer((XWPFDocument) null);
            fail("Should have thrown nullpointer e");
        } catch (Exception e) {
            assertTrue("Should have thrown nullpointer e", e instanceof NullPointerException);
        }
    }

}