package com.programmers;

public class ApplicationManager {
    private boolean isRun;

    public ApplicationManager(boolean isRun) {
        this.isRun = isRun;
    }

    public boolean isRun() {
        return isRun;
    }

    public void setRun(boolean run) {
        isRun = run;
    }
}
