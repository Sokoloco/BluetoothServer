import Bluetooth.WaitThread;

import static java.lang.Thread.sleep;

public class RemoteBluetoothServer{

    public static void main(String[] args) throws InterruptedException {
        Thread waitThread = new Thread(new WaitThread());
        waitThread.start();
        sleep(10000);
        ready();
    }

    /**
     *Awakens the Sleeping thread, call when compilation is ready
     */
    public static void ready(){
        synchronized (WaitThread.class){
            WaitThread.class.notify();
        }
    }
}

