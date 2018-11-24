package Bluetooth;

import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.UUID;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;
import javax.microedition.io.StreamConnectionNotifier;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class WaitThread implements Runnable {

    /** Constructor */
    public WaitThread() {
    }

    @Override
    public void run() {
        LocalDevice local = null;
        StreamConnectionNotifier notifier;
        StreamConnection connection = null;

        // setup the server to listen for connection
        try {
            local = LocalDevice.getLocalDevice();
            local.setDiscoverable(DiscoveryAgent.GIAC);

            UUID uuid = new UUID(80087355); // "04c6093b-0000-1000-8000-00805f9b34fb"
            String url = "btspp://localhost:" + uuid.toString() + ";name=RemoteBluetooth";
            notifier = (StreamConnectionNotifier) Connector.open(url);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        // waiting for connection
        while (true) {
            try {
                System.out.println("waiting for connection...");
                connection = notifier.acceptAndOpen();
                synchronized (WaitThread.class) {
                    // prepare to receive data
                    InputStream inputStream = connection.openInputStream();
                    OutputStream os = connection.openOutputStream();
                    System.out.println("Connection Successful\nWaiting for compiler");
                    byte data[] = "1".getBytes(StandardCharsets.ISO_8859_1);
                    WaitThread.class.wait();
                    System.out.println("Proccess out");

                    while (true) {
                        os.write(data);
                        int command = inputStream.read();
                        System.out.println("This is the command " + command);
                    }
            }
        } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
}
}