import java.util.Scanner;

public class Midterm011y23 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String a = sc.nextLine();
        String b = sc.nextLine();
        String merge = a+" "+b;
        System.out.println(merge);
        String smth = merge.replace(" ", "-");
        System.out.println(smth);
    }
}
