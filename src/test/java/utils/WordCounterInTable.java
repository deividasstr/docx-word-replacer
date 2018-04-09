package utils;

import org.apache.poi.xwpf.usermodel.*;

class WordCounterInTable extends BaseWordCounter {

    @Override
    void countWords(XWPFDocument doc) {
        for (XWPFTable t : doc.getTables()) {
            checkTable(t);
        }
    }

    private void checkTable(XWPFTable t) {
        if (t.getRows() == null) return;
            for (XWPFTableRow r : t.getRows()) {
               checkRow(r);
            }
    }

    private void checkRow(XWPFTableRow r) {
        if (r.getTableCells() == null) return;
        for (XWPFTableCell cell : r.getTableCells()) {
            checkCell(cell);
        }
    }

    private void checkCell(XWPFTableCell cell) {
        if (cell.getParagraphs() == null) return;
        for (XWPFParagraph p : cell.getParagraphs()) {
            if (paragraphNotNullAndHasRuns(p)) {
                checkInParagraph(p);
            }
        }
    }
}
