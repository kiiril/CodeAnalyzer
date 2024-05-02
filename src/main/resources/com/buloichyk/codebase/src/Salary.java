public class Salary {
    public static void main(String[] args) {
        Thesalary emp = new Thesalary(234, 500, "Polina", "Koshkina");
        System.out.println("Basic salary: "+emp.getMonthlysalary());
        emp.applystaz(5);
        emp.raiseSalary(3);
        emp.getInfo();
        System.out.println(emp.getyearsalary());
    }
}
class Thesalary {
    private int ID;
    private double monthlysalary;
    private String name;
    private String lastname;
//    thesalary(){
//        ID=89221044;
//        monthlysalary=1200.5;
//        name="Polina";
//        lastname="Koshkina";
//    }
    Thesalary(int ID, double monthlysalary, String name, String lastname){
        this.ID=ID;
        this.monthlysalary=monthlysalary;
        this.name=name;
        this.lastname=lastname;
    }
    int getID(){
        return ID;
    }
    String getNameLastname(){
        return name+" "+lastname;
    }
    void raiseSalary(double percent){
        monthlysalary+=monthlysalary*percent/100;
    }
    double getMonthlysalary(){
        return monthlysalary;
    }
    void applystaz(int years){
        monthlysalary += monthlysalary*years*0.33/100;
    }
    void getInfo(){
        System.out.println("Emlpoyer ID: "+ID+" with name and lastname "+getNameLastname()+" has monthly salary: "+getMonthlysalary());
    }
    double getyearsalary(){
        return getMonthlysalary()*12;
    }
}
