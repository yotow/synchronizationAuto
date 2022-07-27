import java.util.Stack;

public class Main {
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

        Runnable toyotaReceiver = dealer::receiveToyota;
        Runnable nissanReceiver = dealer::receiveNissan;


        for (int i = 0; i < 5; i++) {
            new Thread(null, toyotaReceiver).start();
            new Thread(null, nissanReceiver).start();
        }

        for (int i = 0; i < 5; i++) {
            new Thread(null, () -> clients.pop().buyCar(dealer, Toyota.LABEL)).start();
            new Thread(null, () -> clients.pop().buyCar(dealer, Nissan.LABEL)).start();
        }
    }
}
