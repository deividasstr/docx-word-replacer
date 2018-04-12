package utils;

public interface OnWordFoundCallback {

    void onWordFoundInRun();
    void onWordFoundInPreviousAndCurrentRun();
    void onWordFoundInPreviousCurrentNextRun();
}
