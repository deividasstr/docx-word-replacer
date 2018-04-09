package utils;

import com.sun.istack.internal.NotNull;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.*;
import java.util.List;

public class ReplacedChecker {

    private static int DEFAULT_POS = 0;

    public static boolean fileContainsWordInText(@NotNull File file, @NotNull String word)
            throws Exception {
        InputStream inputStream = new FileInputStream(file);
        XWPFDocument doc = new XWPFDocument(inputStream);
        return docContainsWordInText(doc, word);
    }

    /**
     * Checks if XWPFDocument contains a given word. Checks runs of all paragraphs if searchable text is in one or
     * scattered in runs around it. It does not check separate paragraphs if text is scattered amongst them.
     * @param doc XWPFDocument
     * @param word to be searched
     * @return boolean
     * @throws NullPointerException
     */
    public static boolean docContainsWordInText(@NotNull XWPFDocument doc, @NotNull String word) {
        for (XWPFParagraph p : doc.getParagraphs()) {
            if (paragraphNotNullAndHasRuns(p)) {
                if (checkParagraph(word, p)) return true;
            }
        }
        return false;
    }

    private static boolean checkParagraph(String word, XWPFParagraph p) {
        List<XWPFRun> runs = p.getRuns();
        for (int runIndex = 0; runIndex < runs.size(); runIndex++) {
            XWPFRun run = p.getRuns().get(runIndex);
            if (isRunNotNullAndNotEmpty(run)) {
                String text = run.getText(DEFAULT_POS);
                if (text.contains(word)) {
                    return true;
                } else if (isNotFirstRun(runIndex) && lastRunHasText(runs, runIndex)) {
                    if (lastAndCurrentRunsText(runs, runIndex, text).contains(word)) {
                        return true;
                    } else if (nextRunHasText(runs, runIndex) && lastThisNextRunText(runs, runIndex).contains(word)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static boolean paragraphNotNullAndHasRuns(XWPFParagraph p) {
        return p != null && !p.getRuns().isEmpty();
    }

    private static String lastThisNextRunText(List<XWPFRun> runs, int runIndex) {
        String text = runs.get(runIndex).getText(DEFAULT_POS);
        return lastAndCurrentRunsText(runs, runIndex, text) + nextRunsText(runs, runIndex);
    }

    private static boolean nextRunHasText(List<XWPFRun> runs, int runIndex) {
        return runs.size() > runIndex + 1
                && runs.get(runIndex + 1).getText(DEFAULT_POS) != null
                && !runs.get(runIndex + 1).getText(DEFAULT_POS).isEmpty();
    }

    private static String nextRunsText(List<XWPFRun> runs, int i) {
        return runs.get(i + 1).getText(DEFAULT_POS);
    }

    private static String lastAndCurrentRunsText(List<XWPFRun> runs, int runIndex, String text) {
        return runs.get(runIndex - 1).getText(DEFAULT_POS) + text;
    }

    private static boolean lastRunHasText(List<XWPFRun> runs, int runIndex) {
        return runs.get(runIndex - 1).getText(DEFAULT_POS) != null
                && !runs.get(runIndex - 1).getText(DEFAULT_POS).isEmpty();
    }

    private static boolean isNotFirstRun(int runIndex) {
        return runIndex > 0;
    }

    private static boolean isRunNotNullAndNotEmpty(XWPFRun run) {
        return run != null && run.getText(DEFAULT_POS) != null;
    }
}
