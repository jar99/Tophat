import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class MainWindowSingleton {
    private static MainWindowSingleton ourInstance = new MainWindowSingleton();

    public static MainWindowSingleton getInstance() {
        return ourInstance;
    }

    private List<Callback> callbackQueue;
    int count;

    private MainWindowSingleton() {
        callbackQueue = new ArrayList<>();
    }

    public synchronized void updateCount(){
        count++;
        callCallback();
    }

    int getCount(){
        return count;
    }

    public void addCallback(Callback callback) {
        callbackQueue.add(callback);
    }

    public void removeCallback(Callback callback) {
        callbackQueue.remove(callback);
    }

    private void callCallback(){
        for (Callback callback : callbackQueue){
            callback.invoke();
        }
    }
}
