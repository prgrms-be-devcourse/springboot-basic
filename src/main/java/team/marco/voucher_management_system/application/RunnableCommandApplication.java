package team.marco.voucher_management_system.application;

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
