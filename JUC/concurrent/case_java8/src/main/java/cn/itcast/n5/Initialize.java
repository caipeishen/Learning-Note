package cn.itcast.n5;

public class Initialize {

    private boolean initialized = false;

    public void init() {

        synchronized (this) {
            if (initialized) {
                return;
            }
            doInit();
            initialized = true;
        }
    }

    private void doInit() {

    }
}
