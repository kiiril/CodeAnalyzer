import java.util.Scanner;

public class Midterm {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.print("The first value: ");
        int a = scan.nextInt();
        System.out.print("The second value: ");
        int b = scan.nextInt();
        if (a>0 && b>0){
            if (a>b){
                System.out.println("Close to zero will be "+b);
            }
            else {
                System.out.println("Close to zero will be "+a);
            }
        } else if (a<0 && b<0) {
            if (a>b){
                System.out.println("Close to zero will be "+a);
            }
            else {
                System.out.println("Close to zero will be "+b);
            }
        }
        else if(a>0 && b<0){
            int c = b*(-1);
            if(a>c){
                System.out.println("Close to zero will be "+c*(-1));
            }
            else {
                System.out.println("Close to zero will be "+a);
            }
        }
        else if(a<0 && b>0){
            int c = a*(-1);
            if(b>c){
                System.out.println("Close to zero will be "+c*(-1));
            }
            else {
                System.out.println("Close to zero will be "+b);
            }
        }
        else {
            System.out.println("Close to zero will be "+0);
        }
        switch (a) {
            case 0:
                break;
            case 1:
                break;
            case 2:
                break;
        }
    }
}