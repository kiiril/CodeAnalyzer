public class Class2710 {
    public static void main(String[] args) {
/*        Scanner scan = new Scanner(System.in);
        //three sides of the triangle
        System.out.print("Insert a:");
        double a = scan.nextInt();
        System.out.print("Insert b:");
        double b = scan.nextInt();
        System.out.print("Insert c:");
        double c = scan.nextInt();
        if (a + b > c && b + c > a && a + c > b) {
            double s = (a + b + c) / 2;
            //double P = Math.sqrt(s*(s-a)*(s-b)*(s-c)); // if you didn't import Math package in the beginning
            double P = sqrt(s * (s - a) * (s - b) * (s - c));
            System.out.println("P=" + P);
        } else {
            System.out.println("IT IS NOT TRIANGLE");
        }*/
        String s1 = "Welcome to Koper";
        int counter=0; //counter of the char 'e'
        for (int i=0; i<s1.length(); i++){
            //System.out.println(s1.charAt(i));
            if(s1.charAt(i)=='e'){
                counter++; //counter = counter + 1;
            }
        }
        String s2 = s1.replace('l',' ');
        System.out.println(s2);
    }
}