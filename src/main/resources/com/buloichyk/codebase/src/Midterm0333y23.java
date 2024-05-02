import java.util.Scanner;

public class Midterm0333y23 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int [] A = new int[4];
        for (int i = 0; i < A.length; i++) {
              A[i]= sc.nextInt();
        }
        int first = A[0];
        int second = A[0];
        for (int i = 0; i < A.length; i++) {
            if (A[i]>first){
                first=A[i];
            }
        }
        for (int i = 0; i < A.length; i++) {
            if (A[i]>second && A[i]!=first){
                second=A[i];
            }
        }
        System.out.println();
        System.out.println(second);
        int sum=0;
        for (int i = A.length/2; i < A.length; i++) {
            sum+=A[i];
        }
    }
}
