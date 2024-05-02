public class Midterm2_02 {
    /*We want to present the company's fleet.
    In the Java programming language, implement the following entities (select interface or class and define all appropriate properties)
    Vehicle (features color, number of wheelsand method print),
    Truck (all properties of Vehicle plus load capacity) and Engine (Vehicle features only).
    Correctly establish relations between entities.
    Also show the use of these classes in a special new class (1 Truck and 1 Engine).*/
    public static void main(String[] args) {
        Truck truck = new Truck("black", 12, 23.5);
        truck.print();
        Engine engine = new Engine("red", 23);
        engine.print();
    }
}
class Vehicle {
    private String featuresColor;
    private int numberOfWheelsand;
    public Vehicle(String featuresColor, int numberOfWheelsand) {
        this.featuresColor = featuresColor;
        this.numberOfWheelsand = numberOfWheelsand;
    }
    public void print() {
        System.out.println("Features color: " + featuresColor);
        System.out.println("Number of whhelsand:" + numberOfWheelsand);
    }

    public void setFeaturesColor(String featuresColor) {
        this.featuresColor = featuresColor;
    }
    public int getNumberOfWheelsand() {
        return numberOfWheelsand;
    }
}
class Truck extends Vehicle {
    private double loadCapacity;
    public Truck(String featuresColor, int numberOfWheelsand, double loadCapacity) {
        super(featuresColor, numberOfWheelsand);
        this.loadCapacity = loadCapacity;
    }
    @Override
    public void print() {
        super.print();
        System.out.println("Load capacity: " + loadCapacity);
    }
}
class Engine extends Vehicle {
    public Engine(String featuresColor, int numberOfWheelsand) {
        super(featuresColor, numberOfWheelsand);
    }
}
