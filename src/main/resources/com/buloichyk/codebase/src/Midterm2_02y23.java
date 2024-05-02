public class Midterm2_02y23 {
    public static void main(String[] args) {
        Diesel d1 = new Diesel(12, 45, 23.1);
        System.out.println(d1.toString());
        System.out.println(d1.price());
    }
}
class Car {
    private final double consumption;
    private final double distance;
    public Car(double consumption, double distance){
        this.consumption=consumption;
        this.distance=distance;
    }
    public double price() {
        return consumption / distance;
    }
    public String toString() {
        return "Consumption: "+consumption + ", Distance: "+distance;
    }
}
 class Diesel extends Car{
    private final double priceFuel;
        Diesel(double consumption, double distance, double priceFuel){
        super(consumption, distance);
        this.priceFuel=priceFuel;
    }
    @Override
     public double price() {
        System.out.print("Price: ");
        return super.price()*priceFuel;
    }
    @Override
     public String toString() {
        return super.toString()+", Price of Diesel:"+priceFuel;
    }
}
class Gasoline extends Car{
    private final double priceFuel;
     Gasoline(double consumption, double distance, double priceFuel) {
        super(consumption, distance);
        this.priceFuel = priceFuel;
    }
    @Override
     public double price() {
        return super.price()*priceFuel;
    }
    @Override
     public String toString() {
        return super.toString()+", Price of Gasoline:"+priceFuel;
    }
}
class LPG extends Car{
    private final double priceFuel;
     LPG(double consumption, double distance, double priceFuel) {
        super(consumption, distance);
        this.priceFuel = priceFuel;
    }
    @Override
     public double price() {
        return super.price()*priceFuel;
    }
    @Override
     public String toString() {
        return super.toString()+", Price of LPG:"+priceFuel;
    }
}
