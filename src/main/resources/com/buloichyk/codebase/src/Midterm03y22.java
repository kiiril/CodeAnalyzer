import java.util.Scanner;

public class Midterm03y22 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Insert the length for both arrays: ");
        int n = sc.nextInt();
        int [] A = new int[n];
        int [] B = new int[n];
        for (int i=0; i< n; i++){
            A[i]= sc.nextInt();
            B[i]= sc.nextInt();
        }
        System.out.println("Your A array with size "+n);
        for (int i=0; i< n; i++){
            System.out.print(A[i]+" ");
        }
        System.out.println();
        System.out.println("Your B array with size "+n);
        for (int i=0; i< n; i++){
            System.out.print(B[i]+" ");
        }
        System.out.println();
        for (int i=0; i<n; i++){
            System.out.print(A[i]+B[i]+" ");
        }
    }
}
