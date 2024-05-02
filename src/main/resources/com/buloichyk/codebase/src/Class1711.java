import java.util.Random;
import java.util.Scanner;

public class Class1711 {
    public static void main(String[] args) {
        int a = 4;
        for (int i = 0; i < a; i++) {
            for (int j = 0; j < a; j++) {
                System.out.print(j+1);
            }
            System.out.println();
        }
        Scanner sc = new Scanner(System.in);
        //palindrome
        System.out.println("Insert string: ");
        String s =sc.nextLine();
        int n = s.length();
        int counter = 0;
        for( int i = 0; i<n; i++){
            if(s.charAt(i)==s.charAt(n-i-1)){
                counter++;
            }
        }
        if (counter==n){
            System.out.println("PALINDROME");
        }
        else{
            System.out.println("NOT PALINDROME");
        }
        // void - no need for the return statement
        // int - return (int, value)



        int[] A = cArr(5, 10);
        int[] B = cArr(5, 15);
        int[] C = sumSamePos(A, B);
        pArr(A);
        System.out.println();
        System.out.println("+");
        pArr(B);
        System.out.println();
        System.out.println("=");
        pArr(C);
    }
    // create a method for implementing array
    public static int[] cArr(int n, int bound){
        Random r = new Random();
        int[] A = new int[n];
        for(int i=0; i<n; i++){
            A[i]=r.nextInt(bound);
        }
        return A;
    }
    // create a method for printing array
    public static void pArr(int[] A){
        int n=A.length;
        for(int i=0; i<n; i++){
            System.out.print(A[i]+" ");
        }
    }
    public static int[] sumSamePos(int[] a, int[] b){
        int n=a.length; //same as: b.length()
        int [] c=new int[n];
        for(int i=0; i<n; i++){
            c[i]=a[i]+b[i];
        }
        return c;
    }
}
