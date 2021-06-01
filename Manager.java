import Animals.DomesticAnimals;
import LevelDesign.Bucket;
import LevelDesign.Level;
import LevelDesign.Map;

public class Manager {

    Level level = new Level();
    public void Buy (String name, Level level) {
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

    public void PickUp (int x, int y, Level level){
        if (level!= null) {
            int t=0;
            for (int i = 0; i < level.ingredients.size(); i++) {
                if (level.ingredients.get(i).x == x && level.ingredients.get(i).y == y) {
                    t = 1;
                    if (level.storage.capacity >= level.ingredients.get(i).size) {
                        level.storage.names.add(level.ingredients.get(i).name);
                        level.storage.quantities.add(1);
                        level.ingredients.remove(i);
                        break;
                    } else {
                        System.out.println("There is not enough space in the storage!");
                    }
                }
            }
            if (t==0){
                    System.out.println("Please enter valid coordinates!");
                }
        }
    }

    public void expirings (Level level){
        int a = level.ingredients.size();
        for (int i=0 ;i < a; i++){
            if (level.ingredients.get(i).expire == 0){
                level.ingredients.remove(i);
            }
        }
    }

    public void Well(Level level, int counter){
        if (level.bucket.duration == level.bucket.maxDuration){
            level.bucket.full = true;
            level.bucket.capacity = 5;
            counter = 0;
        }
        else{
            level.bucket.duration = counter;
            // counter bayad ziad shavad
        }
    }

    public void Plant (int x, int y, Level level) {
        if (x >= 1 && x <= level.map.length && y >= 1 && y <= level.map.height) {
            if (level.bucket.capacity > 0) {
                level.map.map[x - 1][y - 1].grass++;
                level.bucket.capacity--;
                if (level.bucket.capacity == 0) {
                    level.bucket.full = false;
                    level.bucket.capacity = 0;
                    level.bucket.duration = -1;

                }
            } else {
                System.out.println("Bucket is empty! Fill it again!");
            }
        }
        else {
            System.out.println("Coordinates are not valid! Try again!");
        }
    }

    public void Build (Level level, String name){
        if (name.equals("WeaveFactory")){
            if (level.weaveFactory.existence == false){
                if (level.coin >= level.weaveFactory.buildPrice){
                    level.weaveFactory.existence = true;
                    level.coin -= level.weaveFactory.buildPrice;
                }
                else
                    System.out.println("There is not enough money to build the factory.");
            }
            else{
                System.out.println("This factory can't be built again.");
            }
        }

        else if (name.equals("MillFactory")){
            if (level.millFactory.existence == false){
                if (level.coin >= level.millFactory.buildPrice){
                    level.millFactory.existence = true;
                    level.coin -= level.millFactory.buildPrice;
                }
                else
                    System.out.println("There is not enough money to build the factory.");
            }
            else{
                System.out.println("This factory can't be built again.");
            }
        }

        else if (name.equals("MilkFactory")){
            if (level.milkFactory.existence == false){
                if (level.coin >= level.milkFactory.buildPrice){
                    level.milkFactory.existence = true;
                    level.coin -= level.milkFactory.buildPrice;
                }
                else
                    System.out.println("There is not enough money to build the factory.");
            }
            else{
                System.out.println("This factory can't be built again.");
            }

        }

        else if (name.equals("Bakery")){
            if (level.bakery.existence == false){
                if (level.coin >= level.bakery.buildPrice){
                    level.bakery.existence = true;
                    level.coin -= level.bakery.buildPrice;
                }
                else
                    System.out.println("There is not enough money to build the factory.");
            }
            else{
                System.out.println("This factory can't be built again.");
            }

        }

        else if (name.equals("SewingFactory")){
            if (level.sewingFactory.existence == false){
                if (level.coin >= level.sewingFactory.buildPrice){
                    level.sewingFactory.existence = true;
                    level.coin -= level.sewingFactory.buildPrice;
                }
                else
                    System.out.println("There is not enough money to build the factory.");
            }
            else{
                System.out.println("This factory can't be built again.");
            }

        }

        else if (name.equals("IceFactory")){
            if (level.iceFactory.existence == false){
                if (level.coin >= level.iceFactory.buildPrice){
                    level.iceFactory.existence = true;
                    level.coin -= level.iceFactory.buildPrice;
                }
                else
                    System.out.println("There is not enough money to build the factory.");
            }
            else{
                System.out.println("This factory can't be built again.");
            }

        }

        else{
            System.out.println("Not a valid name!");
        }
    }

    public void Work (Level level, String name){
        String[] names = {"WeaveFactory" , "MillFactory", "MilkFactory" , "Bakery", "SewingFactory", "IceFactory"};
        int a= -1;
        for (int i=0; i<names.length; i++){
            if (name.equals(names[i])){
                a=i;
                break;
            }
        }
        if (a==-1){
            System.out.println("Not a valid name.");
        }
        else {
            int t=0;
            int r= level.storage.names.size();
            if (a==0){
                if (level.weaveFactory.existence){
                    if (level.weaveFactory.duration ==-1) {
                        for (int i = 0; i < r; i++) {
                            if (level.storage.names.get(i).equals(level.weaveFactory.ingredient)) {
                                t = 1;
                                level.storage.names.remove(i);
                                level.storage.quantities.remove(i);
                                // Production
                            }
                        }
                        if (t == 0)
                            System.out.println("There is not enough ingredient to start!");
                    }
                    else{
                        System.out.println("The factory is working now, You must wait to end the production.");
                    }
                }
                else {
                    System.out.println("You must first build the factory.");
                }
            }

            if (a==1){
                if (level.millFactory.existence){
                    if (level.millFactory.duration ==-1) {
                        for (int i = 0; i < r; i++) {
                            if (level.storage.names.get(i).equals(level.millFactory.ingredient)) {
                                t = 1;
                                level.storage.names.remove(i);
                                level.storage.quantities.remove(i);
                                // Production
                            }
                        }
                        if (t == 0)
                            System.out.println("There is not enough ingredient to start!");
                    }
                    else{
                        System.out.println("The factory is working now, You must wait to end the production.");
                    }
                }
                else {
                    System.out.println("You must first build the factory.");
                }
            }

            if (a==2){
                if (level.milkFactory.existence){
                    if (level.milkFactory.duration ==-1) {
                        for (int i = 0; i < r; i++) {
                            if (level.storage.names.get(i).equals(level.milkFactory.ingredient)) {
                                t = 1;
                                level.storage.names.remove(i);
                                level.storage.quantities.remove(i);
                                // Production
                            }
                        }
                        if (t == 0)
                            System.out.println("There is not enough ingredient to start!");
                    }
                    else{
                        System.out.println("The factory is working now, You must wait to end the production.");
                    }
                }
                else {
                    System.out.println("You must first build the factory.");
                }
            }

            if (a==3){
                if (level.bakery.existence){
                    if (level.bakery.duration ==-1) {
                        for (int i = 0; i < r; i++) {
                            if (level.storage.names.get(i).equals(level.bakery.ingredient)) {
                                t = 1;
                                level.storage.names.remove(i);
                                level.storage.quantities.remove(i);
                                // Production
                            }
                        }
                        if (t == 0)
                            System.out.println("There is not enough ingredient to start!");
                    }
                    else{
                        System.out.println("The factory is working now, You must wait to end the production.");
                    }
                }
                else {
                    System.out.println("You must first build the factory.");
                }
            }

            if (a==4){
                if (level.sewingFactory.existence){
                    if (level.sewingFactory.duration ==-1) {
                        for (int i = 0; i < r; i++) {
                            if (level.storage.names.get(i).equals(level.sewingFactory.ingredient)) {
                                t = 1;
                                level.storage.names.remove(i);
                                level.storage.quantities.remove(i);
                                // Production
                            }
                        }
                        if (t == 0)
                            System.out.println("There is not enough ingredient to start!");
                    }
                    else{
                        System.out.println("The factory is working now, You must wait to end the production.");
                    }
                }
                else {
                    System.out.println("You must first build the factory.");
                }
            }

            if (a==5){
                if (level.iceFactory.existence){
                    if (level.iceFactory.duration ==-1) {
                        for (int i = 0; i < r; i++) {
                            if (level.storage.names.get(i).equals(level.iceFactory.ingredient)) {
                                t = 1;
                                level.storage.names.remove(i);
                                level.storage.quantities.remove(i);
                                // Production
                            }
                        }
                        if (t == 0)
                            System.out.println("There is not enough ingredient to start!");
                    }
                    else{
                        System.out.println("The factory is working now, You must wait to end the production.");
                    }
                }
                else {
                    System.out.println("You must first build the factory.");
                }
            }
        }
    }
    int P = 3;


}
