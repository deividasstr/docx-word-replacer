package com.xandryex.utils;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.util.List;
import java.util.regex.Pattern;

public class TextReplacer extends WordFinder {

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
        replaceWordInPreviousCurrentNextRuns(runs, currentRun);
    }

    private void replaceWordInPreviousCurrentNextRuns(List<XWPFRun> runs, int currentRun) {
        boolean replacedInPreviousRun = replaceRunTextStart(runs.get(currentRun - 1));
        if (replacedInPreviousRun) {
            deleteTextFromRun(runs.get(currentRun));
        } else {
            replaceRunTextStart(runs.get(currentRun));
        }
        cleanRunTextStart(runs.get(currentRun + 1));
    }

    private void deleteTextFromRun(XWPFRun run) {
        run.setText("", DEFAULT_TEXT_POS);
    }

    //replaceAll() first parameter is used as regex pattern so normally special chars have to be escaped.
    //Pattern.quote() transforms given string into literal where special chars are ignored, thus can be used without escaping
    private void replaceWordInRun(XWPFRun run) {
        String replacedText = run.getText(DEFAULT_TEXT_POS).replaceAll(Pattern.quote(bookmark), replacement);
        run.setText(replacedText, DEFAULT_TEXT_POS);
    }

    private boolean replaceRunTextStart(XWPFRun run) {
        String text = run.getText(DEFAULT_TEXT_POS);
        String remainingBookmark = getRemainingBookmarkStart(text, bookmark);
        if (!remainingBookmark.isEmpty()) {
            text = text.replace(remainingBookmark, replacement);
            run.setText(text, DEFAULT_TEXT_POS);
            return true;
        }
        return false;
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
