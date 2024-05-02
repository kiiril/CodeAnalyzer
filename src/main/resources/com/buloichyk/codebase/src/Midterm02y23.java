import java.util.Scanner;

public class Midterm02y23 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Insert some text: ");
        String text = sc.nextLine();
        String [] words = text.split(" ");
        for (int i = 0; i< words.length; i++){
            String word = words[i];
            if (!word.isEmpty()) {  // Check if the word is not empty
                char firstLetter = word.charAt(0); // Get the first letter of the word
                System.out.print(firstLetter + " ");
            }
        }
    }
}
