import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import utils.WordFinder;

import java.util.List;

 class TextReplacer extends WordFinder {

    private static int DEFAULT_TEXT_POS = 0;

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
    public void onWordFoundInPreviousCurrentNextRun(List<XWPFRun> runs, int currentRun) {
        replaceNotFullBookmarkInRun(runs.get(currentRun - 1));
        deleteTextFromRun(runs.get(currentRun));
        cleanRunTextStart(runs.get(currentRun + 1));
    }

    private void deleteTextFromRun(XWPFRun run) {
        run.setText("", DEFAULT_TEXT_POS);
    }

    private void replaceWordInRun(XWPFRun run) {
        String replacedText = run.getText(DEFAULT_TEXT_POS).replace(bookmark, replacement);
        run.setText(replacedText, DEFAULT_TEXT_POS);
    }

    private void replaceNotFullBookmarkInRun(XWPFRun run) {
        String text = run.getText(DEFAULT_TEXT_POS);
        String remainingBookmark = getRemainingBookmarkStart(text, bookmark);
        text = text.replace(remainingBookmark, replacement);
        run.setText(text, DEFAULT_TEXT_POS);
    }

    private void cleanRunTextStart(XWPFRun run) {
        String text = run.getText(DEFAULT_TEXT_POS);
        String remainingBookmark = getRemainingBookmarkEnd(text, bookmark);
        text = text.replace(remainingBookmark, "");
        run.setText(text, DEFAULT_TEXT_POS);
    }

    private String getRemainingBookmarkEnd(String text, String bookmark) {
        if (!text.startsWith(bookmark)) {
            return getRemainingBookmarkEnd(text, bookmark.substring(1, bookmark.length()));
        } else {
            return bookmark;
        }
    }

    private String getRemainingBookmarkStart(String text, String bookmark) {
        if (!text.endsWith(bookmark)) {
            return getRemainingBookmarkStart(text, bookmark.substring(0, bookmark.length() - 1));
        } else {
            return bookmark;
        }
    }
}
