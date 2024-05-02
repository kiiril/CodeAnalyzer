import java.util.Scanner;

public class Midterm2_03y23 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Insert some text: ");
        String A = sc.nextLine();
        String [] stringOfA = A.split(" ");
        System.out.println("Word(s) which end(s) with letter -a");
        for (int i = 0; i < stringOfA.length; i++) {
            String word = stringOfA[i];
            if (word.endsWith("a") || word.endsWith("A")){
                System.out.println(word);
            }
        }
    }
}
