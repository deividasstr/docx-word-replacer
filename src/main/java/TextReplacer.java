import org.apache.poi.xwpf.usermodel.XWPFDocument;
import utils.WordFinder;

public class TextReplacer extends WordFinder {

    public void replaceInText(XWPFDocument document, String toReplace, String replacement) {
        //findWordsInText();
    }

    public void replaceInTable(XWPFDocument document, String toReplace, String replacement) {

    }

    @Override
    public void onWordFoundInRun() {

    }

    @Override
    public void onWordFoundInPreviousAndCurrentRun() {

    }

    @Override
    public void onWordFoundInPreviousCurrentNextRun() {

    }
}
