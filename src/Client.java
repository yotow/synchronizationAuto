public class Client {
    private final String name;
    private Car car;

    public Client(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Car getCar() {
        return car;
    }

    public void buyCar(AutoDealer dealer, String label) {
        System.out.println("Клиент: " + name + " заказал " + label);
        car = dealer.sellCar(label);
        if (car != null)
            System.out.println("++++++Клиент: " + getName() + " уехал на " + getCar().getLabel());
        else
            System.out.println("------Клиент: " + getName() + " ушел пешком");
    }
}
