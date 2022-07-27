import java.util.ArrayList;
import java.util.List;

public class AutoDealer {

    static final Toyota toyotaProvider = new Toyota();
    static final Nissan nissanProvider = new Nissan();

    static final List<Car> toyotaCars = new ArrayList<>();
    static final List<Car> nissanCars = new ArrayList<>();
    private static final int CREATE_ORDER_TIME = 1000;
    private static final int DELIVERED_TIME = 1000;


    public synchronized Car sellCar(String label) {
        AutoManufacture provider = null;
        List<Car> currentList = null;
        if(label.equals(Toyota.LABEL)){
            provider = toyotaProvider;
            currentList = toyotaCars;
        } else if (label.equals(Nissan.LABEL)) {
            provider = nissanProvider;
            currentList = nissanCars;
        }

        System.out.println("Салон: Заявка на покупку  " + provider.getLabelName() + "  принята");

        try {
            Thread.sleep(CREATE_ORDER_TIME);
            while (currentList.isEmpty()) {
                System.out.println("Салон: " + provider.getLabelName() + " пока нет, ожидайте");
                wait(2000);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Салон:  " + provider.getLabelName() + "  продана");
        return currentList.remove(0);
    }

    public synchronized void receiveToyota() {
        System.out.println("Салон: ожидает " + Toyota.LABEL);
        toyotaCars.add(toyotaProvider.supply());
        notifyAll();
        try {
            Thread.sleep(DELIVERED_TIME);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Салон: приняли " + Toyota.LABEL);
    }

    public synchronized void receiveNissan() {
        System.out.println("Салон: ожидает " + Nissan.LABEL);
        nissanCars.add(nissanProvider.supply());
        notifyAll();
        try {
            Thread.sleep(DELIVERED_TIME);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Салон: приняли " + Nissan.LABEL);
    }
}
