package utils;

import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.util.List;

public interface OnWordFoundCallback {

    void onWordFoundInRun(XWPFRun run);
    void onWordFoundInPreviousAndCurrentRun(List<XWPFRun> runs, int currentRun);
    void onWordFoundInPreviousCurrentNextRun(List<XWPFRun> runs, int currentRun);
}
