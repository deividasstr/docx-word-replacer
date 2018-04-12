package utils;

import org.apache.poi.xwpf.usermodel.XWPFDocument;

public class WordCounter extends WordFinder {

    private int foundWords;

    @Override
    public void onWordFoundInRun() {
        foundWords++;
    }

    @Override
    public void onWordFoundInPreviousAndCurrentRun() {
        foundWords++;
    }

    @Override
    public void onWordFoundInPreviousCurrentNextRun() {
        foundWords++;
    }

    public int countWordsInText(XWPFDocument doc, String toFind) {
        foundWords = 0;
        findWordsInText(doc, toFind);
        return foundWords;
    }

    public int countWordsInTable(XWPFDocument doc, String toFind) {
        foundWords = 0;
        findWordsInTable(doc, toFind);
        return foundWords;
    }
}
