import com.sun.istack.internal.NotNull;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.*;

public class DocWordReplacer {

    private XWPFDocument document;
    private Replacer replacer;

    public DocWordReplacer(@NotNull File docxFile) throws IOException {
        InputStream inputStream = new FileInputStream(docxFile);
        document = new XWPFDocument(inputStream);
    }

    public DocWordReplacer(@NotNull XWPFDocument xWPFDoc) {
        document = xWPFDoc;
        replacer = new Replacer();
    }

    public File getModdedFile() {
        return null;
    }

    public XWPFDocument getModdedXWPFDoc() {
        return document;
    }

    public void replaceWordsInText(String toReplace, String replacement) {
        replacer.replaceInText(toReplace, replacement);
    }

    public void replaceWordsInTables(String toReplace, String replacement) {
        replacer.replaceInTable(toReplace, replacement);
    }
}
