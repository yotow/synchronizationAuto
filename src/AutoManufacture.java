import java.util.concurrent.Callable;

public abstract class AutoManufacture {
    protected int TIME_FOR_CREATE = 2000;
    protected String label = null;

    protected Car supply() {
        System.out.println("Поставщик " + label + ": машина будет готова через " + TIME_FOR_CREATE);
        try {
            Thread.sleep(TIME_FOR_CREATE);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return new Car(label);
    }

    public String getLabelName() {
        return label;
    }
}
