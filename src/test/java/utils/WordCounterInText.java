package utils;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

class WordCounterInText extends BaseWordCounter {

    @Override
    void countWords(XWPFDocument doc) {
        for (XWPFParagraph p : doc.getParagraphs()) {
            if (paragraphNotNullAndHasRuns(p)) {
                checkInParagraph(p);
            }
        }
    }


}
