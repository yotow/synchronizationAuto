import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class AutoDealer {

    ReentrantLock providerLoker = new ReentrantLock();
    Condition condition = providerLoker.newCondition();

    static final Toyota toyotaProvider = new Toyota();
    static final Nissan nissanProvider = new Nissan();

    static final List<Car> toyotaCars = new ArrayList<>();
    static final List<Car> nissanCars = new ArrayList<>();
    private static final int CREATE_ORDER_TIME = 500;

    public Car sellCar(String label) {
        AutoManufacture provider = determineProvider(label);
        List<Car> currentList = determineList(label);

        System.out.println("Салон: Заявка на покупку  " + provider.getLabelName() + "  принята");

        providerLoker.lock();
        try {
            Thread.sleep(CREATE_ORDER_TIME);
            while (currentList.isEmpty()) {
                System.out.println("Салон: " + provider.getLabelName() + " пока нет, ожидайте");
                condition.await();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            providerLoker.unlock();
        }
        System.out.println("Салон:  " + provider.getLabelName() + "  продана");
        return currentList.remove(0);
    }


    public void receiveCar(String label) {
        AutoManufacture provider = determineProvider(label);
        List<Car> currentList = determineList(label);

        providerLoker.lock();
        try {
            System.out.println("Салон: заказал " + provider.label);
            currentList.add(provider.supply());
            condition.signal();
            System.out.println("Салон: приняли " + provider.label);
        } catch (IllegalMonitorStateException e) {
            throw new RuntimeException(e);
        } finally {
            providerLoker.unlock();
        }
    }

    private List<Car> determineList(String label) {
        if (label.equals(Toyota.LABEL)) {
            return toyotaCars;
        } else {
            return nissanCars;
        }
    }

    private AutoManufacture determineProvider(String label) {
        if (label.equals(Toyota.LABEL)) {
            return toyotaProvider;
        } else {
            return nissanProvider;
        }
    }
}
