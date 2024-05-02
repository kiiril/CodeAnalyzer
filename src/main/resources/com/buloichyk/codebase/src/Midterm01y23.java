import java.util.Scanner;

public class Midterm01y23 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Insert 1st value ");
        int a = sc.nextInt();
        System.out.print("Insert 2nd value ");
        int b = sc.nextInt();
        if (a>0 && b>0){
            if (a>b){
                System.out.println("The smallest one is: "+b);
            }
            else {
                System.out.println("The smallest one is: "+a);
            }
        } else if (a<0 && b<0) {
            if (a>b){
                System.out.println("The smallest one is: "+b);
            }
            else {
                System.out.println("The smallest one is: "+a);
            }
        } else if (a>0 && b<0) {
                System.out.println("The smallest one is: "+b);
        } else if (a<0 && b>0) {
                System.out.println("The smallest one is: "+a);
        }
        switch (a) {
            case 1:
                break;
            case 2:
                break;
        }
        while (a != 0) {}
        while (true) {}
        for (int i = 0; i < a; i++) {
            // empty
        }
    }

    public void BadNamedMethod() {

    }

    public void python_convention() {

    }
}
