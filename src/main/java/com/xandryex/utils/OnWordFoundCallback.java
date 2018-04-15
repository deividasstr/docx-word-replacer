package com.xandryex.utils;

import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.util.List;

interface OnWordFoundCallback {

    void onWordFoundInRun(XWPFRun run);
    void onWordFoundInPreviousCurrentNextRun(List<XWPFRun> runs, int currentRun);
}
