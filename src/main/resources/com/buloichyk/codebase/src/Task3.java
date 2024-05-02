public class Task3 {
    public static void main(String[] args) {

    }
}
class Monster{
    String name;
    float health;
    float damage;

    public Monster(String name, float health, float damage) {
        this.name = name;
        this.health = health;
        this.damage = damage;
    }

    public void attack(){
        System.out.println("1"+name+"2"+health+"3"+damage);
    }
    public void heal(float amount){
        health+=amount;
    }
}
class one extends Monster{

    public one(String name, float health, float damage) {
        super(name, health, damage);
    }
    @Override
    public void attack() {
        super.attack();
        heal(10);
    }
}

class two extends Monster{
    private float bonusDamage;
    public two(String name, float health, float damage, float bonusDamage) {
        super(name, health, damage);
        this.bonusDamage = bonusDamage;
    }

    @Override
    public void attack() {
        super.attack();
        this.damage += bonusDamage;
    }
}