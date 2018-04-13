import com.sun.istack.internal.NotNull;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.*;

public class WordReplacer {

    private XWPFDocument document;
    private TextReplacer replacer;

    public WordReplacer(@NotNull File docxFile) throws IOException {
        InputStream inputStream = new FileInputStream(docxFile);
        init(new XWPFDocument(inputStream));
    }

    public WordReplacer(@NotNull XWPFDocument xwpfDoc) {
        init(xwpfDoc);
    }

    private void init(XWPFDocument xwpfDoc) {
        if (xwpfDoc == null) throw new NullPointerException();
        document = xwpfDoc;
        replacer = new TextReplacer();
    }

    public void replaceWordsInText(String toReplace, String replacement) {
       replacer.replaceInText(document, toReplace, replacement);
    }

    public void replaceWordsInTables(String toReplace, String replacement) {
        replacer.replaceInTable(document, toReplace, replacement);
    }

    public File saveAndGetModdedFile(String path) throws IOException {
        File file = new File(path);
        return saveToFile(file);
    }

    public File saveAndGetModdedFile(File file) throws IOException {
        return saveToFile(file);
    }

    public XWPFDocument getModdedXWPFDoc() {
        return document;
    }

    private File saveToFile(File file) throws IOException {
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file, false);
            document.write(out);
            document.close();
            return file;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (out != null) {
                out.flush();
                out.close();
            }
        }
    }
}
