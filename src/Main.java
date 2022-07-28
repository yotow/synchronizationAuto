import java.util.Stack;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static final int WAIT = 2000;

    public static void main(String[] args) throws InterruptedException {
        Stack<Client> clients = new Stack<>();

        clients.push(new Client("Иван"));
        clients.push(new Client("Игорь"));
        clients.push(new Client("Юрий"));
        clients.push(new Client("Филип"));
        clients.push(new Client("Григорий"));
        clients.push(new Client("Саманта"));
        clients.push(new Client("Катя"));
        clients.push(new Client("Паша"));
        clients.push(new Client("Алексей"));
        clients.push(new Client("Света"));
        clients.push(new Client("Никита"));

        AutoDealer dealer = new AutoDealer();

        ExecutorService service = Executors.newFixedThreadPool(1);
        ExecutorService service2 = Executors.newFixedThreadPool(1);

        for (int i = 0; i < 5; i++) {
            service2.submit(() -> clients.pop().buyCar(dealer, Nissan.LABEL));
            service2.submit(() -> clients.pop().buyCar(dealer, Toyota.LABEL));
            Thread.sleep(WAIT);

            service.submit(() -> dealer.receiveCar(Nissan.LABEL));

            Thread.sleep(WAIT);

            service.submit(() -> dealer.receiveCar(Toyota.LABEL));
            //new Thread(null, () -> dealer.receiveCar(Toyota.LABEL)).start();
            //new Thread(null, () -> dealer.receiveCar(Nissan.LABEL)).start();
        }
        service.shutdown();
        service2.shutdown();
    }
}
