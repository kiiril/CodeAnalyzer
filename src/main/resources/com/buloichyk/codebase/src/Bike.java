public class Bike {
    static int speed = 80;
    public static void main(String[] args) {
        child_mb bike1 = new child_mb(80, 21, 20);
        bike1.appBreak(2);
        System.out.println(bike1.info());
        bike1.speedup(10);
        bike1.replace_W(24);
        System.out.println(bike1.new_info());
        parent_Bike bike2 = new parent_Bike(speed, 15);
        System.out.println(bike2.info());
    }
}
class parent_Bike{
    int speed, gear;
    //constructors:
    parent_Bike(int speed, int gear){
        this.speed=speed;
        this.gear=gear;
    }
    int appBreak(int dec){
        speed -= dec;
        return speed;
    }
    int speedup(int inc){
        speed+=inc;
        return speed;
    }
    String info(){
        return("This bike has gear: "+gear+" and speed is: "+Bike.speed);
        // Пишем "+Bike.speed" вместо "+speed", если мы хотим сохранить в new_info первоначальную скорость, в нашем случае 80.
        // Если не добавить название класса и static int speed = 80, то скорость и до, и после будет 94.
    }
}
class child_mb extends parent_Bike{
    double wheelsize;
    //constructor;
    child_mb(int speed, int gear, double wheelsize){
        super(speed, gear);
        this.wheelsize=wheelsize;
    }
    double replace_W(double wheelsize){
        speed+=0.25*wheelsize;
        return speed;
    }
    String new_info(){
        return (super.info()+" If wheel is replaced then speed is: "+speed);
    }
}
