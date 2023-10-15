import java.util.concurrent.atomic.AtomicBoolean;

public class AndersonLock {
    private AtomicBoolean state;

    public AndersonLock() {
        state = new AtomicBoolean(false);
    }

    public void lock() {
        while (state.getAndSet(true)) {
            // spin while waiting for the lock to be released
        }
    }

    public void unlock() {
        state.set(false);
    }
}
