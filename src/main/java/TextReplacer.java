import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import utils.WordFinder;

import java.util.List;

public class TextReplacer extends WordFinder {

    private String replacement;
    private String bookmark;

    public void replaceInText(XWPFDocument document, String bookmark, String replacement) {
        this.replacement = replacement;
        this.bookmark = bookmark;
        findWordsInText(document, bookmark);
    }

    public void replaceInTable(XWPFDocument document, String bookmark, String replacement) {
        this.replacement = replacement;
        this.bookmark = bookmark;
        findWordsInTable(document, bookmark);
    }

    @Override
    public void onWordFoundInRun(XWPFRun run) {
        replaceWordInRun(run);
    }

    @Override
    public void onWordFoundInPreviousAndCurrentRun(List<XWPFRun> runs, int currentRun) {
        replaceInPreviousRun(runs, currentRun);
        cleanRunTextStart(runs.get(currentRun));
    }

    @Override
    public void onWordFoundInPreviousCurrentNextRun(List<XWPFRun> runs, int currentRun) {
        replaceInPreviousRun(runs, currentRun - 1);
        deleteTextFromRun(runs.get(currentRun));
        cleanRunTextStart(runs.get(currentRun + 1));
    }

    private void deleteTextFromRun(XWPFRun run) {
        run.setText("", 0);
    }

    private void replaceWordInRun(XWPFRun run) {
        String replacedText = run.getText(0).replace(bookmark, replacement);
        run.setText(replacedText, 0);
    }

    private void replaceInPreviousRun(List<XWPFRun> runs, int currentRun) {
        String previousRunText = runs.get(currentRun - 1).getText(0);
        previousRunText = replaceBookmarkFromPrevious(previousRunText, bookmark);
        runs.get(currentRun - 1).setText(previousRunText, 0);
    }

    private void cleanRunTextStart(XWPFRun run) {
        String currentRunText = removeRemainingBookmark(run.getText(0) , bookmark);
        run.setText(currentRunText, 0);
    }

    private String replaceBookmarkFromPrevious(String previousRunText, String bookmark) {
        if (!previousRunText.contains(bookmark)) {
            return replaceBookmarkFromPrevious(previousRunText, bookmark.substring(0, bookmark.length() - 2));
        } else {
            return previousRunText.replace(bookmark, replacement);
        }
    }

    private String removeRemainingBookmark(String text, String bookmark) {
        if (!text.contains(bookmark)) {
            return removeRemainingBookmark(text, bookmark.substring(1, bookmark.length() - 1));
        } else {
            return text.replace(bookmark, "");
        }
    }
}
