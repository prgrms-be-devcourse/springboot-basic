package prgms.vouchermanagementapp.controller;

public class RunningState {

    private boolean state;

    public RunningState() {
        this.state = true;
    }

    public boolean isRunning() {
        return this.state;
    }

    public void exit() {
        this.state = false;
    }
}
