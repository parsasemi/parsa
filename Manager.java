import Animals.DomesticAnimals;
import LevelDesign.Level;

public class Manager {

    Level level = new Level();
    public static void Buy (String name, Level level) {
        if (name.equals("buffalo")){
            DomesticAnimals.Buffalo buffalo = new DomesticAnimals.Buffalo(1,1);
            if (level.coin >= buffalo.BuyPrice) {
                level.buffalos.add(buffalo);
                level.coin -= buffalo.BuyPrice;
            }
            else{
                System.out.println("NOT ENOUGH COIN TO BUY!");
            }
        }
        else if (name.equals("turkey")){
            DomesticAnimals.Turkey turkey = new DomesticAnimals.Turkey(1,1);
            if (level.coin >= turkey.BuyPrice ) {
                level.turkies.add(turkey);
                level.coin -= turkey.BuyPrice;
            }
            else {
                System.out.println("NOT ENOUGH COIN TO BUY!");
            }
        }
        else if (name.equals("chicken")){
            DomesticAnimals.Chicken chicken = new DomesticAnimals.Chicken(1,1 );
            if (level.coin >= chicken.BuyPrice) {
                level.chickens.add(chicken);
                level.coin -= chicken.BuyPrice;
            }
            else {
                System.out.println("NOT ENOUGH COIN TO BUY!");
            }
        }
    }

    public static void PickUp (int x, int y, Level level){
        if (level!= null) {
            for (int i = 0; i < level.ingredients.size(); i++) {
                if (level.ingredients.get(i).x == x && level.ingredients.get(i).y == y){

                }
            }
        }
    }

    public static void main(String[] args) {

    }

}
