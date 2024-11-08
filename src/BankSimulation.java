import java.util.logging.Logger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BankSimulation {
    private static final Logger logger = Logger.getLogger(BankSimulation.class.getName());

    public static void main(String[] args) {
        // Create a bank account
        BankAccount account = new BankAccount(1235690, 500.00);

        // Set up an executor service to handle multiple clients concurrently
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        // Create multiple clients working on the same account
        Client client1 = new Client(account, "Client 1");
        Client client2 = new Client(account, "Client 2");
        Client client3 = new Client(account, "Client 3");

        // Execute clients
        executorService.execute(client1);
        executorService.execute(client2);
        executorService.execute(client3);

        // Shutdown the executor
        executorService.shutdown();
    }
}
