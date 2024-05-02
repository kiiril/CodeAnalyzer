public class class2212 {
    public static void main(String[] args) {
        circle2212 c1 = new circle2212();
        c1.info();
        circle2212 c2 = new circle2212(5, "Green");
        c2.info();
    }
}
class circle2212{
    private int r;
    private String color;
    // constructors:
    circle2212(){
        r=2;
        color="Red";
    }
    circle2212(int r, String color){
        this.r=r;
        this.color=color;
    }
    int getR(){
        return r;
    }
    double area(){
        return Math.pow(r,2)*Math.PI;
    }
    String getC(){
        return color;
    }
    // setter:
    void setR(int new_r){
        r=new_r;
    }
    void info(){
        System.out.println("The circle has radius of "+getR()+" and color is "+getC()+" and area: "+area());
    }
}
