import java.util.Scanner;

public class Leapyear {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter year:");
        int year = scan.nextInt();
        if (year%4==0 && year%100!=0) {
            System.out.println("Leap year " +year);
        } else if (year%400==0) {
            System.out.println("Leap year " +year);
        }
        else {
            System.out.println("Not leap year " +year);
        }
    }
}
