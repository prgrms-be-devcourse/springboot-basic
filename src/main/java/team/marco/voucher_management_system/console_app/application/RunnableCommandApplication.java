package team.marco.voucher_management_system.console_app.application;

public abstract class RunnableCommandApplication {
    protected boolean runningFlag;

    public void run() {
        runningFlag = true;

        while (runningFlag) {
            start();
        }

        close();
    }

    protected abstract void start();

    protected abstract void close();
}
