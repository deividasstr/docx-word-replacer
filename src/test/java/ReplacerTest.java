import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;


public class ReplacerTest {

    private static int DEFAULT_POS = 0;

    private boolean fileContainsWordInText(File file, String word) throws IOException {
        InputStream inputStream = new FileInputStream(file);
        XWPFDocument doc = new XWPFDocument(inputStream);
        return docContainsWordInText(doc, word);
    }

    private boolean docContainsWordInText(XWPFDocument doc, String word) {
        for (XWPFParagraph p : doc.getParagraphs()) {
            if (paragraphNotNullAndHasRuns(p)) {
                if (checkParagraph(word, p)) return true;
            }
        }
        return false;
    }

    private boolean checkParagraph(String word, XWPFParagraph p) {
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

    private boolean paragraphNotNullAndHasRuns(XWPFParagraph p) {
        return p != null && !p.getRuns().isEmpty();
    }

    private String lastThisNextRunText(List<XWPFRun> runs, int runIndex) {
        String text = runs.get(runIndex).getText(DEFAULT_POS);
        return lastAndCurrentRunsText(runs, runIndex, text) + nextRunsText(runs, runIndex);
    }

    private boolean nextRunHasText(List<XWPFRun> runs, int runIndex) {
        return runs.size() > runIndex + 1
                && runs.get(runIndex + 1).getText(DEFAULT_POS) != null
                && !runs.get(runIndex + 1).getText(DEFAULT_POS).isEmpty();
    }

    private String nextRunsText(List<XWPFRun> runs, int i) {
        return runs.get(i + 1).getText(DEFAULT_POS);
    }

    private String lastAndCurrentRunsText(List<XWPFRun> runs, int runIndex, String text) {
        return runs.get(runIndex - 1).getText(DEFAULT_POS) + text;
    }

    private boolean lastRunHasText(List<XWPFRun> runs, int runIndex) {
        return runs.get(runIndex - 1).getText(DEFAULT_POS) != null
                && !runs.get(runIndex - 1).getText(DEFAULT_POS).isEmpty();
    }

    private boolean isNotFirstRun(int runIndex) {
        return runIndex > 0;
    }

    private boolean isRunNotNullAndNotEmpty(XWPFRun run) {
        return run != null && run.getText(DEFAULT_POS) != null;
    }
}
