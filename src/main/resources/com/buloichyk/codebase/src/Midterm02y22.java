import java.util.Scanner;

public class Midterm02y22 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Insert some text: ");
        String a = sc.nextLine();
        String [] a_words = a.split(" ");
        for (int i =0; i<a_words.length; i++){
            String newstring = a_words[i];
            if(newstring.endsWith("g")){
                System.out.println("word with ending 'g' "+newstring);
            }
        }
    }
}
