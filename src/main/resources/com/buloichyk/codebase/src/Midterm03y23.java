import java.util.Scanner;

public class Midterm03y23 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int [] A = new int [3];
        for (int i =0; i< A.length; i++){
            A[i]= sc.nextInt();
        }
        for (int i = A.length-1; i>=0; i--){
            if (A[i]%2==0) {
                System.out.print(A[i]+" "); // in reverse order
            }
        }

        for (int i=0; i< A.length; i++){
            int newA =A[i]*A[i];
            System.out.println(newA+" ");
        }
        /*System.out.println("Squares of all numbers: ");
        for (int i=0; i< A.length; i++){
            System.out.println(A[i]);
        }*/
        int counter = 0;
        for (int i=0; i< A.length; i++){
            counter +=A[i]*A[i];
        }
        System.out.println("Sum of squares: "+counter);
        int gt100 = 0;
        for (int i=0; i< A.length; i++){
            if (A[i]>100){
                gt100++;
            }
        }
        System.out.println("Counter "+gt100);
    }
}
