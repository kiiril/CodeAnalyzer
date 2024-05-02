import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Class0501 {
    public static void main(String[] args) {
//        ArrayList<String> string_al = new ArrayList<>(); // creating empty list
//        string_al.add("String1"); // string on the position 0
//        string_al.add("String2"); // on the position 1
//        string_al.add(0,"string0");
//        //System.out.println(string_al);
//        string_al.remove(1); // removing on the position 1
//        //System.out.println(string_al);

        ArrayList<Integer> a = createlist(10);
        System.out.println(a);
        System.out.println(div5sorted(a));
    }
    public static ArrayList<Integer> createlist(int n){
        ArrayList<Integer> a = new ArrayList<>(n);
        Random r = new Random();
        for (int i = 0; i < n; i++) {
            a.add(r.nextInt(10));
        }
        return a;
    }
    public static ArrayList<Integer> div5sorted(ArrayList<Integer> a){
        int n = a.size(); // length/size of the array a
        ArrayList<Integer> finalArrayList = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if(a.get(i)%5==0){
                finalArrayList.add(a.get(i));
            }
        }
        Collections.sort(finalArrayList);
        return finalArrayList;
    }
}
