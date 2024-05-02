import java.util.Scanner;

public class Midterm033y23 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Insert array");
        int [] A = new int [4];
        for (int i = 0; i< A.length; i++){
            A[i]= scanner.nextInt();
        }
        System.out.println("Reverse order:");
        for (int i = A.length-1; i>=0; i--){
            System.out.print(A[i]+" ");
        }
        int sum = 0;
        for (int i = 0; i< A.length; i++){

            sum += Math.pow(A[i], 2);
        }
        System.out.println(sum);
        //Arrays.sort(A);
        System.out.println(A[2]);

        int max = A[0];
        for (int i = 0; i < A.length; i++) {
            if (A[i] > max) {
                max = A[i];
            }
        }
        int secondMax = A[0];
        for (int i = 0; i < A.length; i++) {
            if (A[i] > secondMax && A[i] != max) {
                secondMax = A[i];
            }
        }
        System.out.println();
        System.out.println(secondMax);
    }
}
