import com.sun.istack.internal.NotNull;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.*;

public class DocWordReplacer {

    private XWPFDocument document;
    private Replacer replacer;

    public DocWordReplacer(@NotNull File docxFile) throws IOException {
        InputStream inputStream = new FileInputStream(docxFile);
        init(new XWPFDocument(inputStream));
    }

    public DocWordReplacer(@NotNull XWPFDocument xwpfDoc) {
        init(xwpfDoc);
    }

    private void init(XWPFDocument xwpfDoc) {
        if (xwpfDoc == null) throw new NullPointerException();
        document = xwpfDoc;
        replacer = new Replacer();
    }

    public void replaceWordsInText(String toReplace, String replacement) {
        replacer.replaceInText(document, toReplace, replacement);
    }

    public void replaceWordsInTables(String toReplace, String replacement) {
        replacer.replaceInTable(document, toReplace, replacement);
    }

    public File getModdedFile() {
        return null;
    }

    public XWPFDocument getModdedXWPFDoc() {
        return document;
    }


}
