import java.util.Scanner;
public class Class0812 {

    //RECURSION
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        //System.out.println("Insert a: ");
        //int a = sc.nextInt();
        System.out.println("Insert n: ");
        int n = sc.nextInt();
        //System.out.println("sum of first "+n+" numbers is: "+sumOFnum(n));
        //System.out.println(n+"-th power of number "+a+" is: "+powerOFnum(n, a));
        //System.out.println("Factorial of the number "+n+" is: "+factorialOFnum(n));
        //System.out.print("The Fibonacci sequence: ");
        /*for (int i = 0; i <= n; i++) {
            System.out.print(fibonacci(i)+" ");
        }*/
    }
    public static int sumOFnum(int n){
        if(n>=1){
            //return n+sumOFnum(n-1); //all numbers
            //return 2*n+sumOFnum(n-1); //even numbers
            return 2*n-1+sumOFnum(n-1); //odd numbers
        }
        else{
            return 0;
        }
    }
    public static int powerOFnum(int a, int n){
        if (n>=1 && a>=0){
            return a*powerOFnum(a,n-1);
        }else{
            return 1;
        }
    }
    public static int factorialOFnum(int n){
        if(n>=1){
            return n*factorialOFnum(n-1);
        }else{
            return 1;
        }
    }
    public static int fibonacci(int n){
        if(n<=0 || n==1){
            return 1;
        }else{
            return fibonacci(n-2)+fibonacci(n-1);
        }
    }

}
