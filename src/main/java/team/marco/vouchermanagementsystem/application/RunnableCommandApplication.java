package team.marco.vouchermanagementsystem.application;

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
