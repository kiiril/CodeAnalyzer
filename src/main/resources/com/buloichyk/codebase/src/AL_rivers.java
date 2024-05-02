import java.util.*;
public class AL_rivers {
    public static void main(String[] args) {
        String [] r = {"Volga", "Ottawa", "Danube", "Colorado", "Nile", "Dniester", "Ural", "Amazon", "Mississipi", "Dnieper"};
        Integer [] l = {3645, 1271, 2888, 2333, 6650, 1411, 2428, 6575, 6275, 2287};
        List<String> river_list = Arrays.asList(r);
        List<Integer> length_list = Arrays.asList(l);
        List<Integer> primary_lengths = new ArrayList<>(length_list); // = length_list
        System.out.println(river_list);
        System.out.println(length_list);
        System.out.println(primary_lengths);
        Collections.sort(length_list);
        System.out.println(length_list);
        ArrayList<String> final_list = new ArrayList<>();
        for (Integer el: length_list) {
            final_list.add(river_list.get(primary_lengths.indexOf(el)));
        }
        System.out.println(final_list);
    }
}
