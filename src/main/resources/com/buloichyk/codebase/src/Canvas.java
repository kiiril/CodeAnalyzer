public class Canvas {
    public static void main(String[] args) {
        Points point1 = new Points(1,2);
        Points point2 = new Points(2,3);
        Points point3 = new Points(3,4);
        Points point4 = new Points(4,5);

        Triangle triangle = new Triangle("Name 1", point1, point2, point3);
        Rectangle rectangle = new Rectangle("Name 2", point1, point2, point3, point4);
        Circle circle = new Circle("Name 3", 3, point1);

        triangle.render();
        rectangle.render();
        circle.render();
    }
}
class Points{
    private final int x;
    private final int y;
    public Points(int x, int y){
        this.x=x;
        this.y=y;
    }
    public String toString(){
        return "("+x+","+y+")";
    }
}
class Shape {
    public String name;

    public Shape (String name){
        this.name=name;
    }
    public void render(){
        System.out.println("Rendering "+name);
    }
}
class Triangle extends Shape{
    private final Points point1;
    private final Points point2;
    private final Points point3;
    public Triangle(String name, Points point1, Points point2, Points point3) {
        super(name);
        this.point1=point1;
        this.point2=point2;
        this.point3=point3;
    }
}
class Rectangle extends Shape{
    private final Points point1;
    private final Points point2;
    private final Points point3;
    private final Points point4;
    public Rectangle (String name, Points point1, Points point2, Points point3, Points point4) {
        super(name);
        this.point1=point1;
        this.point2=point2;
        this.point3=point3;
        this.point4=point4;
    }
}
class Circle extends Shape{
    private final int radius;
    private final Points point1;
    public Circle(String name, int radius, Points point1) {
        super(name);
        this.radius=radius;
        this.point1=point1;
    }
}