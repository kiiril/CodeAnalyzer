import java.util.Random;
import java.util.Scanner;

public class Class1011 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        //SWITCH OPERATIONS
        /*System.out.print("Insert operation:"); // + - *...
        String operation =sc.next();
        System.out.print("Insert values for a and b");
        int a = sc.nextInt();
        int b = sc.nextInt();
        switch (operation){
            case "+":
                System.out.println(a+b);
                break;
            case "-":
                System.out.println(a-b);
                break;
            case "*":
                System.out.println(a*b);
                break;
            case "/":
                System.out.println(a/b);
                break;
            default:
                System.out.println("WRONG OPERATION");
        }


        //FACTORIALS
        System.out.print("Insert the number n: ");
        int n = sc.nextInt();
        int res = 1;
        for ( int i=1; i<=n; i++){
            res=res*i;
            System.out.println("On the step "+i+" factorial of the number "+n+" is: "+res);
        }
        System.out.println("The factorial of the number "+n+" is: "+res);

        String primorska = "University of Primorska";
        String[] primorska_words = primorska.split(" ");
        // System.out.println(primorska_words); // WRONG! USE ONLY FOR LOOP FOR PRINTING ARRAY
        for (int i=0; i< primorska_words.length; i++){
            System.out.println(primorska_words[i]);
        }
        char[] chars_from_primorska = new char[primorska.length()]; // empty array
        for (int i=0; i<primorska.length(); i++){
            chars_from_primorska[i]=primorska.toUpperCase().charAt(i);
        }
        for (int i=0; i< chars_from_primorska.length; i++){
            System.out.print(chars_from_primorska[i]+" ");
        }
         */
        System.out.println("Insert the length: ");
        int n = sc.nextInt();
        System.out.println("Insert array:" );
        int[] A = new int[n];
        int[] B = new int[n];
        int sumA = 0;
        int sumB = 0;
        Random rand = new Random();//create the random method
        // A is with elements that user will insert:
        // B consist of random elements up to 100
        for(int i=0; i<n; i++){
            A[i]=sc.nextInt();
            B[i]=rand.nextInt(100);
        }
        for(int i = 0; i<n; i++){
            sumA=sumA+A[i];
            sumB=sumB+B[i];
        }
        int maxA = A[0], maxB = B[0];
        for (int i=0; i<n; i++){
            if(maxA<A[i]){
                maxA=A[i];
            }
        }
        for (int i=0; i<n; i++) {
            if (maxB < B[i]) {
                maxB = B[i];
            }
        }
        if(maxA<maxB) {
            System.out.println(sumA + " " + sumB);
        }
        else if(maxA > maxB){
            for (int i = 0; i < n; i++){
                System.out.println(B[i]);
            }
        }
        else{
            for(int i = n-1; i>=0; i--){
                System.out.print(A[i]+" ");
            }
        }
    }
}
