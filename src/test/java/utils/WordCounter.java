package com.xandryex.utils;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.util.List;

public class WordCounter extends WordFinder {

    private int foundWords;

    @Override
    public void onWordFoundInRun(XWPFRun run) {
        foundWords++;
    }

    @Override
    public void onWordFoundInPreviousCurrentNextRun(List<XWPFRun> runs, int currentRun) {
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
