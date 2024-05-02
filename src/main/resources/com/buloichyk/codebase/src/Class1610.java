import java.util.Scanner;

public class Class1610 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the word: ");
        String a = scanner.next();
        System.out.println("The length of this word: " + a.length());
        System.out.print("Enter the index:");
        int index = scanner.nextInt();
        System.out.println("Letter "+a.charAt(index)+" has an index "+index);
        System.out.println("Insert some letter, which you would like to hide: ");
        String ch = scanner.next();
        String string = a.replace(ch," ");
        System.out.println("New string: " + string);
        int couter = 0;
        for (int i = 0; i < a.length(); i++) {
            System.out.println(a.charAt(i));
        }
        System.out.println();
        for (int i = 0; i < a.length(); i++) {
            if (a.charAt(i) == 'o') {
                couter+=1;
            }
        }
        System.out.println("Number of letters 'o' in the word "+a+": "+couter);
    }
}