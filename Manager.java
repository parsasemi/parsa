import Animals.DomesticAnimals;
import LevelDesign.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jdk.swing.interop.SwingInterOpUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Manager {
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_BLUE = "\u001B[34m";

    ArrayList<Level> levels = new ArrayList<>();
    private ArrayList<LoginUser> Users = new ArrayList<>();
    String log = "";

    public void Buy(String name, Level level) throws IOException {
        Random random = new Random();
        if (name.equals("buffalo")) {
            DomesticAnimals.Buffalo buffalo = new DomesticAnimals.Buffalo(random.nextInt(6), random.nextInt(6));
            if (level.coin >= buffalo.BuyPrice) {
                level.buffalos.add(buffalo);
                level.coin -= buffalo.BuyPrice;
                logger("info", "buy buffalo", level);
            } else {
                System.out.println("NOT ENOUGH COIN TO BUY!");
                logger("error", "low coin", level);
            }
        } else if (name.equals("turkey")) {
            DomesticAnimals.Turkey turkey = new DomesticAnimals.Turkey(random.nextInt(6), random.nextInt(6));
            if (level.coin >= turkey.BuyPrice) {
                level.turkies.add(turkey);
                level.coin -= turkey.BuyPrice;
                logger("info", "buy turkey", level);
            } else {
                System.out.println("NOT ENOUGH COIN TO BUY!");
                logger("error", "low coin", level);
            }
        } else if (name.equals("chicken")) {
            DomesticAnimals.Chicken chicken = new DomesticAnimals.Chicken(random.nextInt(6), random.nextInt(6));
            if (level.coin >= chicken.BuyPrice) {
                level.chickens.add(chicken);
                level.coin -= chicken.BuyPrice;
                logger("info", "buy chicken", level);
            } else {
                System.out.println("NOT ENOUGH COIN TO BUY!");
                logger("error", "low coin", level);
            }
        } else {
            System.out.println("Invalid Input because there is no such animal.");
        }
    }

    public void PickUp(int x, int y, Level level) throws IOException {
        if (level != null) {
            int t = 0;
            for (int i = 0; i < level.ingredients.size(); i++) {
                if (level.ingredients.get(i).x == x && level.ingredients.get(i).y == y) {
                    t = 1;
                    if (level.storage.capacity >= level.ingredients.get(i).size) {
                        level.storage.names.add(level.ingredients.get(i).name);
                        level.storage.quantities.add(1);
                        level.storage.capacity -= level.ingredients.get(i).size;
                        Date date = new Date();
                        log += ("[info] " + date + " " + level.ingredients.get(i).name + " x: " + level.ingredients.get(i).x + " y: " + level.ingredients.get(i).y + " !\n");
                        if (level.ingredients.get(i).name.equals("egg")) {
                            level.task.eggCounter++;
                        } else if (level.ingredients.get(i).name.equals("feather")) {
                            level.task.featherCounter++;
                        } else if (level.ingredients.get(i).name.equals("milk")) {
                            level.task.milkCounter++;
                        } else if (level.ingredients.get(i).equals("flour")) {
                            level.task.flourCounter++;
                        } else if (level.ingredients.get(i).equals("cmilk")) {
                            level.task.cmilkCounter++;
                        } else if (level.ingredients.get(i).equals("bread")) {
                            level.task.milkCounter++;
                        } else if (level.ingredients.get(i).equals("icecream")) {
                            level.task.iceCreamCounter++;
                        } else if (level.ingredients.get(i).equals("cloth")) {
                            level.task.clothCounter++;
                        } else if (level.ingredients.get(i).equals("weave")) {
                            level.task.weaveCounter++;
                        }
                        logWriter();
                        level.ingredients.remove(i);
                        break;
                    } else {
                        System.out.println("There is not enough space in the storage!");
                        logger("error", "no space", level);
                    }
                }
            }

            if (t == 0) {
                System.out.println("There's nothing there to pick up!");
                logger("error", "nothing to pickup", level);
            }
        }
    }

    public void Expirings(Level level) throws IOException {
        Date date = new Date();
        int a = level.ingredients.size();
        for (int i = 0; i < a; i++) {
            if (level.ingredients.get(i).expire == 0) {
                log += ("[info] " + date + " " + level.ingredients.get(i).name + " x: " + level.ingredients.get(i).x + " y: " + level.ingredients.get(i).y);
                logWriter();
                level.ingredients.remove(i);

            }
        }
    }

    public void Well(Level level, int counter) throws IOException {
        if (level.bucket.duration == level.bucket.maxDuration) {
            Date date = new Date();
            level.bucket.full = true;
            level.bucket.capacity = 5;
            counter = 0;
            log += ("[info] " + date + " Bucket full!\n");
            logWriter();

        } else {
            level.bucket.duration = counter;
            // counter bayad ziad shavad
        }
    }

    public void Plant(int x, int y, Level level) throws IOException {
        Date date = new Date();
        if (x >= 1 && x <= level.map.length && y >= 1 && y <= level.map.height) {
            if (level.bucket.capacity > 0) {
                level.map.map[x - 1][y - 1].grass++;
                level.bucket.capacity--;
                log += ("[info] " + date + " grass x: " + level.map.map[x - 1][y - 1].x + " y: " + level.map.map[x - 1][y - 1].y) + "\n";
                logWriter();
                if (level.bucket.capacity == 0) {
                    level.bucket.full = false;
                    level.bucket.capacity = 0;
                    level.bucket.duration = -1;
                    log += ("[info] " + date + " Bucket empty!\n");
                    logWriter();

                }
            } else {
                System.out.println("Bucket is empty! Fill it again!");
            }
        } else {
            System.out.println("Coordinates are not valid! Try again!");
        }
    }

    public void Build(Level level, String name) throws IOException {
        if (name.equals("weavefactory")) {
            if (level.weaveFactory.existence == false) {
                if (level.coin >= level.weaveFactory.buildPrice) {
                    level.weaveFactory.existence = true;
                    level.coin -= level.weaveFactory.buildPrice;
                    logger("info", "build weave factory", level);
                } else
                    System.out.println("There is not enough money to build the factory.");
            } else {
                System.out.println("This factory can't be built again.");
            }
        } else if (name.equals("millfactory")) {
            if (level.millFactory.existence == false) {
                if (level.coin >= level.millFactory.buildPrice) {
                    level.millFactory.existence = true;
                    level.coin -= level.millFactory.buildPrice;
                    logger("info", "build mill factory", level);
                } else
                    System.out.println("There is not enough money to build the factory.");
            } else {
                System.out.println("This factory can't be built again.");
            }
        } else if (name.equals("milkfactory")) {
            if (level.milkFactory.existence == false) {
                if (level.coin >= level.milkFactory.buildPrice) {
                    level.milkFactory.existence = true;
                    level.coin -= level.milkFactory.buildPrice;
                    logger("info", "build milk factory", level);
                } else
                    System.out.println("There is not enough money to build the factory.");
            } else {
                System.out.println("This factory can't be built again.");
            }

        } else if (name.equals("bakery")) {
            if (level.bakery.existence == false) {
                if (level.coin >= level.bakery.buildPrice) {
                    level.bakery.existence = true;
                    level.coin -= level.bakery.buildPrice;
                    logger("info", "build bakery", level);
                } else
                    System.out.println("There is not enough money to build the factory.");
            } else {
                System.out.println("This factory can't be built again.");
            }

        } else if (name.equals("sewingfactory")) {
            if (level.sewingFactory.existence == false) {
                if (level.coin >= level.sewingFactory.buildPrice) {
                    level.sewingFactory.existence = true;
                    level.coin -= level.sewingFactory.buildPrice;
                    logger("info", "build sewing", level);
                } else
                    System.out.println("There is not enough money to build the factory.");
            } else {
                System.out.println("This factory can't be built again.");
            }

        } else if (name.equals("icefactory")) {
            if (level.iceFactory.existence == false) {
                if (level.coin >= level.iceFactory.buildPrice) {
                    level.iceFactory.existence = true;
                    level.coin -= level.iceFactory.buildPrice;
                    logger("info", "build ice factory", level);
                } else
                    System.out.println("There is not enough money to build the factory.");
            } else {
                System.out.println("This factory can't be built again.");
            }

        } else {
            System.out.println("Not a valid name!");
        }
    }

    public void Work(Level level, String name) throws IOException {
        String[] names = {"weavefactory", "millfactory", "milkfactory", "bakery", "sewingfactory", "icefactory"};
        int a = -1;
        for (int i = 0; i < names.length; i++) {
            if (name.equals(names[i])) {
                a = i;
                break;
            }
        }
        if (a == -1) {
            System.out.println("Not a valid name.");
        } else {
            logger("info", "build " + name, level);
            int t = 0;
            int r = level.storage.names.size();
            if (a == 0) {
                if (level.weaveFactory.existence) {
                    if (level.weaveFactory.duration == -1) {
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
                    } else {
                        System.out.println("The factory is working now, You must wait for the production to end.");
                    }
                } else {
                    System.out.println("You must first build the factory.");
                }
            }

            if (a == 1) {
                if (level.millFactory.existence) {
                    if (level.millFactory.duration == -1) {
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
                    } else {
                        System.out.println("The factory is working now, You must wait for the production to end.");
                    }
                } else {
                    System.out.println("You must first build the factory.");
                }
            }

            if (a == 2) {
                if (level.milkFactory.existence) {
                    if (level.milkFactory.duration == -1) {
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
                    } else {
                        System.out.println("The factory is working now, You must wait for the production to end.");
                    }
                } else {
                    System.out.println("You must first build the factory.");
                }
            }

            if (a == 3) {
                if (level.bakery.existence) {
                    if (level.bakery.duration == -1) {
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
                    } else {
                        System.out.println("The factory is working now, You must wait for the production to end.");
                    }
                } else {
                    System.out.println("You must first build the factory.");
                }
            }

            if (a == 4) {
                if (level.sewingFactory.existence) {
                    if (level.sewingFactory.duration == -1) {
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
                    } else {
                        System.out.println("The factory is working now, You must wait for the production to end.");
                    }
                } else {
                    System.out.println("You must first build the factory.");
                }
            }

            if (a == 5) {
                if (level.iceFactory.existence) {
                    if (level.iceFactory.duration == -1) {
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
                    } else {
                        System.out.println("The factory is working now, You must wait for the production to end.");
                    }
                } else {
                    System.out.println("You must first build the factory.");
                }
            }
        }
    }

    public void Cage(int x, int y, Level level, int counter) {
        int l = -1;
        int b = -1;
        int t = -1;
        for (int i = 0; i < level.lions.size(); i++) {
            if (level.lions.get(i).x == x && level.lions.get(i).y == y) {
                l = i;
                break;
            }
        }
        if (l != -1) {
            for (int i = 0; i < level.bears.size(); i++) {
                if (level.bears.get(i).x == x && level.bears.get(i).y == y) {
                    b = i;
                    break;
                }
            }
            if (t != -1) {
                for (int i = 0; i < level.tigers.size(); i++) {
                    if (level.tigers.get(i).x == x && level.tigers.get(i).y == y) {
                        t = i;
                        break;
                    }
                }
            }
        }
        if (l == -1 && t == -1 && b == -1) {
            System.out.println("There is no wild animal in that coordination, choose wisely!");
        } else if (l != -1) {
            if (counter == level.lions.get(l).cageP) {
                level.lions.get(l).inCage = true;
                level.lions.get(l).speed = 0;
            }
        } else if (b != -1) {
            if (counter == level.bears.get(b).cageP) {
                level.bears.get(b).inCage = true;
                level.bears.get(b).speed = 0;
            }


        } else if (t != -1) {
            if (counter == level.tigers.get(t).cageP) {
                level.tigers.get(t).inCage = true;
                level.tigers.get(t).speed = 0;
            }

        }
    }

    public void CageCounter(Level level) {
        for (int i = 0; i < level.lions.size(); i++) {
            if (level.lions.get(i).inCage) {
                level.lions.get(i).inCageCounter++;
            }
        }
        for (int i = 0; i < level.bears.size(); i++) {
            if (level.bears.get(i).inCage) {
                level.bears.get(i).inCageCounter++;
            }
        }
        for (int i = 0; i < level.tigers.size(); i++) {
            if (level.tigers.get(i).inCage) {
                level.tigers.get(i).inCageCounter++;
            }
        }
    }

    public void CageEnd(Level level) {
        for (int i = 0; i < level.lions.size(); i++) {
            if (level.lions.get(i).inCageCounter == 5) {
                level.lions.remove(i);
            }
            if (level.bears.get(i).inCageCounter == 5) {
                level.bears.remove(i);
            }
            if (level.tigers.get(i).inCageCounter == 5) {
                level.tigers.remove(i);
            }
        }
    }

    public void CageStore(Level level) {
        for (int i = 0; i < level.lions.size(); i++) {
            if (level.lions.get(i).inCage) {
                if (level.storage.capacity >= level.lions.get(i).capacity) {
                    level.storage.names.add("lion");
                    level.storage.quantities.add(1);
                    level.storage.capacity -= level.lions.get(i).capacity;
                }
            }
        }
        for (int i = 0; i < level.bears.size(); i++) {
            if (level.bears.get(i).inCage) {
                if (level.storage.capacity >= level.bears.get(i).capacity) {
                    level.storage.names.add("bear");
                    level.storage.quantities.add(1);
                    level.storage.capacity -= level.bears.get(i).capacity;
                }
            }
        }
        for (int i = 0; i < level.tigers.size(); i++) {
            if (level.tigers.get(i).inCage) {
                if (level.storage.capacity >= level.tigers.get(i).capacity) {
                    level.storage.names.add("tiger");
                    level.storage.quantities.add(1);
                    level.storage.capacity -= level.tigers.get(i).capacity;
                }
            }
        }
    }

    public void FactoryCounter(Level level) {
        if (level.sewingFactory.existence && level.sewingFactory.ingredientExistence) {
            if (level.sewingFactory.productTime >= level.sewingFactory.maxDuration) {
                level.sewingFactory.productTime = 0;
                level.sewingFactory.ingredientExistence = false;
                Ingredient.Cloth product = new Ingredient.Cloth(1, 1);
                level.ingredients.add(product);
            } else {
                level.sewingFactory.productTime++;
            }
        }
        if (level.millFactory.existence && level.millFactory.ingredientExistence) {
            if (level.millFactory.productTime >= level.millFactory.maxDuration) {
                level.millFactory.productTime = 0;
                level.millFactory.ingredientExistence = false;
                Ingredient.Flour product = new Ingredient.Flour(1, 1);
                level.ingredients.add(product);
            } else {
                level.millFactory.productTime++;
            }
        }
        if (level.milkFactory.existence && level.milkFactory.ingredientExistence) {
            if (level.milkFactory.productTime >= level.milkFactory.maxDuration) {
                level.milkFactory.productTime = 0;
                level.milkFactory.ingredientExistence = false;
                Ingredient.Milk product = new Ingredient.Milk(1, 1);
                level.ingredients.add(product);
            } else {
                level.milkFactory.productTime++;
            }
        }

        if (level.bakery.existence && level.bakery.ingredientExistence) {
            if (level.bakery.productTime >= level.bakery.maxDuration) {
                level.bakery.productTime = 0;
                level.bakery.ingredientExistence = false;
                Ingredient.Bread product = new Ingredient.Bread(1, 1);
                level.ingredients.add(product);
            } else {
                level.bakery.productTime++;
            }
        }

        if (level.weaveFactory.existence && level.weaveFactory.ingredientExistence) {
            if (level.weaveFactory.productTime >= level.weaveFactory.maxDuration) {
                level.weaveFactory.productTime = 0;
                level.weaveFactory.ingredientExistence = false;
                Ingredient.Weave product = new Ingredient.Weave(1, 1);
                level.ingredients.add(product);
            } else {
                level.weaveFactory.productTime++;
            }
        }

        if (level.iceFactory.existence && level.iceFactory.ingredientExistence) {
            if (level.iceFactory.productTime >= level.iceFactory.maxDuration) {
                level.iceFactory.productTime = 0;
                level.iceFactory.ingredientExistence = false;
                Ingredient.IceCream product = new Ingredient.IceCream(1, 1);
                level.ingredients.add(product);
            } else {
                level.iceFactory.productTime++;
            }
        }
    }

    public void AnimalCounter(Level level) {
        for (int i = 0; i < level.chickens.size(); i++) {
            if (level.chickens.get(i).time >= level.chickens.get(i).productTime - 1 && level.chickens.get(i).existence == true) {
                level.chickens.get(i).time = -1;
                int a = level.chickens.get(i).x;
                int b = level.chickens.get(i).y;
                Ingredient.Egg egg = new Ingredient.Egg(a, b);
                level.ingredients.add(egg);
            } else {
                level.chickens.get(i).time++;
            }
            level.chickens.get(i).health -= 10;
            if (level.chickens.get(i).health <= 0) {
                level.chickens.get(i).existence = false;
            }
        }

        for (int i = 0; i < level.buffalos.size(); i++) {
            if (level.buffalos.get(i).time >= level.buffalos.get(i).productTime && level.buffalos.get(i).existence == true) {
                level.buffalos.get(i).time = -1;
                int a = level.chickens.get(i).x;
                int b = level.chickens.get(i).y;
                Ingredient.Milk milk = new Ingredient.Milk(a, b);
                level.ingredients.add(milk);
            } else {
                level.buffalos.get(i).time++;
            }
            level.buffalos.get(i).health -= 10;
            if (level.buffalos.get(i).health <= 0) {
                level.buffalos.get(i).existence = false;
            }
        }

        for (int i = 0; i < level.turkies.size(); i++) {
            if (level.turkies.get(i).time >= level.turkies.get(i).productTime && level.turkies.get(i).existence == true) {
                level.turkies.get(i).time = -1;
                int a = level.chickens.get(i).x;
                int b = level.chickens.get(i).y;
                Ingredient.Feather feather = new Ingredient.Feather(a, b);
                level.ingredients.add(feather);
            } else {
                level.turkies.get(i).time++;
            }
            level.turkies.get(i).health -= 10;
            if (level.turkies.get(i).health <= 0) {
                level.turkies.get(i).existence = false;
            }
        }
    }

    public ArrayList<DomesticAnimals> AnimalHealthChecker(Level level) {
        ArrayList<DomesticAnimals> needed = new ArrayList<>();
        for (int i = 0; i < level.chickens.size(); i++) {
/*            int x = level.chickens.get(i).x;
            int y = level.chickens.get(i).y;*/
            if (level.chickens.get(i).health <= 40) {
                needed.add(level.chickens.get(i));
            }
        }
        for (int i = 0; i < level.buffalos.size(); i++) {
            if (level.buffalos.get(i).health <= 40) {
                needed.add(level.buffalos.get(i));
            }
        }
        for (int i = 0; i < level.turkies.size(); i++) {
            if (level.turkies.get(i).health <= 40) {
                needed.add(level.turkies.get(i));
            }
        }
        return needed;


    }

    public void NeededFiller(Level level, ArrayList<DomesticAnimals> needed) {
        Collections.sort(needed, (o1, o2) -> {
            if (o1.health > o2.health) return 1;
            return -1;
        });
        for (int i = 0; i < needed.size(); i++) {
            int x = needed.get(i).x;
            int y = needed.get(i).y;
            if (level.map.map[x][y].grass >= 1) {
                level.map.map[x][y].grass--;
                needed.get(i).health = 100;
            }
        }
    }

    public Ingredient StringToIngr(String name) {
        if (name.equals("bread"))
            return new Ingredient.Bread(1, 1);
        else if (name.equals("flour"))
            return new Ingredient.Flour(1, 1);
        else if (name.equals("feather"))
            return new Ingredient.Feather(1, 1);
        else if (name.equals("milk"))
            return new Ingredient.Milk(1, 1);
        else if (name.equals("cmilk"))
            return new Ingredient.CMilk(1, 1);
        else if (name.equals("weave"))
            return new Ingredient.Weave(1, 1);
        else {
            return null;
        }
    }

    public boolean tasksChecker(Level level) throws IOException {
        level.task.chickenCounter = level.chickens.size();
        level.task.turkeyCounter = level.turkies.size();
        level.task.buffaloCounter = level.buffalos.size();

        boolean taskCheck = false;

        if (level.task.totalCoin >= level.task.coinObj && level.task.coinObj != 10000) {
            level.task.coinCheck = true;
            level.task.taskCounter++;
        }

        if (level.chickens.size() >= level.task.chickenObj && level.task.chickenObj != 10000 && !level.task.chickenCheck) {
            level.task.chickenCheck = true;
            level.task.taskCounter++;
        }
        if (level.buffalos.size() >= level.task.buffaloObj && level.task.buffaloObj != 10000) {
            level.task.buffaloCheck = true;
            level.task.taskCounter++;
        }
        if (level.turkies.size() >= level.task.turkeyObj && level.task.turkeyObj != 10000) {
            level.task.turkeyCheck = true;
            level.task.taskCounter++;
        }
        if (level.task.breadCounter >= level.task.breadObj && level.task.breadObj != 10000) {
            level.task.breadCheck = true;
            level.task.taskCounter++;
        }
        if (level.task.flourCounter >= level.task.flourObj && level.task.flourObj != 10000) {
            level.task.flourCheck = true;
            level.task.taskCounter++;
        }
        if (level.task.milkCounter >= level.task.milkObj && level.task.milkObj != 10000) {
            level.task.milkCheck = true;
            level.task.taskCounter++;
        }
        if (level.task.cmilkCounter >= level.task.cmilkObj & level.task.cmilkObj != 10000) {
            level.task.cmilkCheck = true;
            level.task.taskCounter++;
        }
        if (level.task.iceCreamCounter >= level.task.iceCreamObj && level.task.iceCreamObj != 10000) {
            level.task.iceCreamCheck = true;
            level.task.taskCounter++;
        }
        if (level.task.clothCounter >= level.task.clothObj && level.task.clothObj != 10000) {
            level.task.clothCheck = true;
            level.task.taskCounter++;
        }
        if (level.task.eggCounter >= level.task.eggObj && level.task.eggObj != 10000) {
            level.task.eggCheck = true;
            level.task.taskCounter++;
        }
        if (level.task.weaveCounter >= level.task.weaveObj && level.task.weaveObj != 10000) {
            level.task.weaveCheck = true;
            level.task.taskCounter++;
        }
        if (level.task.featherCounter >= level.task.featherObj && level.task.featherObj != 10000) {
            level.task.featherCheck = true;
            level.task.taskCounter++;
        }
        if (level.passedTime >= level.task.timeObj && level.task.timeObj != 10000) {
            level.task.timeCheck = true;
            level.task.taskCounter++;
        }

        if (level.task.taskCounter == level.task.taskObj) {
            System.out.println(ANSI_YELLOW + "All tasks done! You won!" + ANSI_RESET);
            taskCheck = true;

        }
        return taskCheck;

    }

    public void printTasks(Level level) {
        if (level.task.timeObj != 10000) {
            System.out.println(ANSI_PURPLE + "Minimum time to finish the level: " + level.task.timeObj + ANSI_RESET);
        }
        if (level.task.featherObj != 10000) {
            System.out.println(ANSI_PURPLE + "Feathers to collect: " + level.task.featherObj + ANSI_RESET);
        }
        if (level.task.weaveObj != 10000) {
            System.out.println(ANSI_PURPLE + "Fabrics to weave: " + level.task.weaveObj + ANSI_RESET);
        }
        if (level.task.eggObj != 10000) {
            System.out.println(ANSI_PURPLE + "Eggs to collect: " + level.task.eggObj + ANSI_RESET);
        }
        if (level.task.clothObj != 10000) {
            System.out.println(ANSI_PURPLE + "Clothes to sew: " + level.task.clothObj + ANSI_RESET);
        }
        if (level.task.cmilkObj != 10000) {
            System.out.println(ANSI_PURPLE + "Compact Milks to produce: " + level.task.cmilkObj + ANSI_RESET);
        }
        if (level.task.flourObj != 10000) {
            System.out.println(ANSI_PURPLE + "Flours to produce: " + level.task.flourObj + ANSI_RESET);
        }
        if (level.task.breadObj != 10000) {
            System.out.println(ANSI_PURPLE + "Breads to bake: " + level.task.breadObj + ANSI_RESET);
        }
        if (level.task.coinObj != 10000) {
            System.out.println(ANSI_PURPLE + "Coins to make: " + level.task.coinObj + ANSI_RESET);
        }
        if (level.task.iceCreamObj != 10000) {
            System.out.println(ANSI_PURPLE + "Ice creams to produce: " + level.task.iceCreamObj + ANSI_RESET);
        }
        if (level.task.chickenObj != 10000) {
            System.out.println(ANSI_PURPLE + "Number of Chickens you must have: " + level.task.chickenObj + ANSI_RESET);
        }
        if (level.task.turkeyObj != 10000) {
            System.out.println(ANSI_PURPLE + "Number of turkies you must have: " + level.task.turkeyObj + ANSI_RESET);
        }
        if (level.task.buffaloObj != 10000) {
            System.out.println(ANSI_PURPLE + "Number of Buffalos you must have: " + level.task.buffaloObj + ANSI_RESET);
        }


    }

    public void readUsersInfo() throws IOException {
        File file1 = new File("");
        String absolutePath = file1.getAbsolutePath();
        absolutePath += "\\usersInfo.json";
        File file2 = new File(absolutePath);
        if (file2.exists()) {
            Gson gson = new Gson();
            Reader reader = Files.newBufferedReader(Paths.get("usersInfo.json"));
            Users.clear();
            List<LoginUser> temp = Arrays.asList(gson.fromJson(reader, LoginUser[].class));
            Users.addAll(temp);
            reader.close();
        }

    }

    public boolean usernameCheck(String username) throws IOException {

        boolean found = false;

        File file1 = new File("");
        String absolutePath = file1.getAbsolutePath();
        absolutePath += "\\usersInfo.json";
        File file2 = new File(absolutePath);
        if (file2.exists()) {
            Gson gson = new Gson();
            Reader reader = Files.newBufferedReader(Paths.get("usersInfo.json"));
            Users.clear();
            List<LoginUser> temp = Arrays.asList(gson.fromJson(reader, LoginUser[].class));
            Users.addAll(temp);
            reader.close();
            for (LoginUser u : Users) {
                if (u.username.equals(username)) {
                    found = true;
                }
            }
        }
        if (!found) {
        }
        return found;
    }

    public void signUp(String username, String password) throws IOException {
        LoginUser user = new LoginUser(username, password);
        Users.add(user);

        Gson gson = new Gson();
        Writer writer = Files.newBufferedWriter(Paths.get("usersInfo.json"));
        gson.toJson(Users, writer);
        writer.close();

    }

    public boolean login(String username, String password) throws IOException {
        readUsersInfo();
        boolean flag = false;
        for (LoginUser u : Users) {
            if (u.username.equals(username)) {
                if (u.password.equals(password)) {
                    flag = true;
                }
            }
        }
        return flag;


    }

    public void save() throws IOException {

        File file1 = new File("");
        String absolutePath = file1.getAbsolutePath();
        for (Level l : levels) {
            if (l.levelNumber == 1) {
                absolutePath += "\\LevelsInfo" + l.playerName + ".json";
                break;
            }
        }
        Gson write = new GsonBuilder().setPrettyPrinting().create();
        FileWriter fileWriter = new FileWriter(absolutePath);
        write.toJson(levels, fileWriter);
        fileWriter.flush();
        fileWriter.close();
    }

    public boolean levelCheck(String username, int levelNumber) throws IOException {
        boolean level_check = false;
        File file1 = new File("");
        String absolutePath = file1.getAbsolutePath();
        absolutePath += "\\LevelsInfo" + username + ".json";
        File file2 = new File(absolutePath);
        if (file2.exists()) {
            Gson gson = new Gson();
            Reader reader = Files.newBufferedReader(Paths.get(absolutePath));
            List<Level> temp = Arrays.asList(gson.fromJson(reader, Level[].class));
            levels.clear();
            levels.addAll(temp);

            for (Level l : levels) {
                if (levelNumber == l.levelNumber) {
                    level_check = true;
                    break;
                }
            }
            if (!level_check) {
                System.out.println(ANSI_RED + "This level has not been unlocked yet!" + ANSI_RESET);
                System.out.println("Your unlocked levels are: ");
                int max = 1;
                for (Level l : levels) {
                    if (l.levelNumber > 1) {
                        max = l.levelNumber;
                    }
                }
                for (int i = 1; i <= max; i++) {
                    System.out.print(i);
                    if (i != levels.size()) {
                        System.out.print(", ");
                    }
                }
                System.out.println();
            }

        } else {
            if (levelNumber != 1) {
                System.out.println(ANSI_RED + "You have to start the game from the beginning!" + ANSI_RESET);
            }
            if (levelNumber == 1) {

                Level level = new Level();
                level = readBasicLevelInfo(level, 1);
                level.playerName = username;
                level.levelStarted = false;
                levels.add(level);
                for (Level l : levels) {
                    System.out.println(l.levelNumber);
                }
                save();
                level_check = true;
            }

        }

        return level_check;

    }

    public Level levelReturner(int levelNumber) {
        for (Level l : levels) {
            if (l.levelNumber == levelNumber) {
                return l;
            }
        }
        return null;
    }

    public Level levelEnd(Level playerLevel) throws IOException {
        for (Level l : levels) {
            if (l.levelNumber == (playerLevel.levelNumber + 1)) {
                playerLevel.levelEnd = true;
                playerLevel.levelStarted = true;
                save();
                return l;
            }
        }
        Level newLevel = new Level();
        newLevel = readBasicLevelInfo(newLevel, playerLevel.levelNumber + 1);
        playerLevel.levelEnd = true;
        playerLevel.levelStarted = true;
        newLevel.levelStarted = false;
        newLevel.levelEnd = false;
        levels.add(newLevel);
        save();
        return newLevel;
    }

    public Level newLevel(int levelNumber, Level level) throws IOException {

        for (Level l : levels) {
            if (l.levelNumber == levelNumber) {
                levels.remove(l);
                break;
            }
        }
        Level newLevel = new Level();
        newLevel = readBasicLevelInfo(newLevel, levelNumber);
        newLevel.playerName = level.playerName;
        newLevel.levelNumber = levelNumber;
        newLevel.levelStarted = false;
        newLevel.levelEnd = false;
        levels.add(newLevel);

        save();
        return newLevel;
    }

    public void writeBasicLevel1Info() throws IOException {

        Level level = new Level();
        level.levelNumber = 1;
        level.coin = 100;
        Random random1 = new Random();

        DomesticAnimals.Chicken chicken1 = new DomesticAnimals.Chicken(random1.nextInt(6), random1.nextInt(6));
        DomesticAnimals.Chicken chicken2 = new DomesticAnimals.Chicken(random1.nextInt(6), random1.nextInt(6));
        level.chickens.add(chicken1);
        level.chickens.add(chicken2);
        level.bucket.full = true;
        level.bucket.capacity = 5;

        //task
        level.task.taskCounter = 0;
        level.task.taskObj = 1;
        level.task.eggObj = 6;


        File file1 = new File("");
        String absolutePath = file1.getAbsolutePath();
        absolutePath += ("/Level" + level.levelNumber + ".json");
        Gson write = new GsonBuilder().setPrettyPrinting().create();
        FileWriter fileWriter = new FileWriter(absolutePath);
        write.toJson(level, fileWriter);
        fileWriter.flush();
        fileWriter.close();

    }

    public void writeBasicLevel2Info() throws IOException {
        Level level = new Level();
        level.levelNumber = 2;
        level.coin = 120;
        Random random1 = new Random();
        DomesticAnimals.Chicken chicken1 = new DomesticAnimals.Chicken(random1.nextInt(6), random1.nextInt(6));
        DomesticAnimals.Chicken chicken2 = new DomesticAnimals.Chicken(random1.nextInt(6), random1.nextInt(6));

        level.chickens.add(chicken1);
        level.chickens.add(chicken2);
        level.bucket.full = true;
        level.bucket.capacity = 5;

        //task
        level.task.taskCounter = 0;
        level.task.eggObj = 6;
        level.task.chickenObj = 4;
        level.task.taskObj = 2;


        File file1 = new File("");
        String absolutePath = file1.getAbsolutePath();
        absolutePath += ("/Level" + level.levelNumber + ".json");
        Gson write = new GsonBuilder().setPrettyPrinting().create();
        FileWriter fileWriter = new FileWriter(absolutePath);
        write.toJson(level, fileWriter);
        fileWriter.flush();
        fileWriter.close();

    }

    public void writeBasicLevel3Info() throws IOException {
        Level level = new Level();
        level.levelNumber = 3;
        level.coin = 90;
        Random random1 = new Random();

        DomesticAnimals.Chicken chicken1 = new DomesticAnimals.Chicken(random1.nextInt(6), random1.nextInt(6));
        DomesticAnimals.Chicken chicken2 = new DomesticAnimals.Chicken(random1.nextInt(6), random1.nextInt(6));
        DomesticAnimals.Chicken chicken3 = new DomesticAnimals.Chicken(random1.nextInt(6), random1.nextInt(6));
        DomesticAnimals.Chicken chicken4 = new DomesticAnimals.Chicken(random1.nextInt(6), random1.nextInt(6));

        level.chickens.add(chicken1);
        level.chickens.add(chicken2);
        level.chickens.add(chicken3);
        level.chickens.add(chicken4);
        level.bucket.full = true;
        level.bucket.capacity = 5;

        //task
        level.task.taskCounter = 0;
        level.task.taskObj = 3;
        level.task.eggObj = 10;
        level.task.flourObj = 4;
        level.task.chickenObj = 6;
        level.task.chickenCounter = 4;


        File file1 = new File("");
        String absolutePath = file1.getAbsolutePath();
        absolutePath += ("/Level" + level.levelNumber + ".json");
        Gson write = new GsonBuilder().setPrettyPrinting().create();
        FileWriter fileWriter = new FileWriter(absolutePath);
        write.toJson(level, fileWriter);
        fileWriter.flush();
        fileWriter.close();

    }

    public void writeBasicLevel4Info() throws IOException {
        Level level = new Level();
        level.levelNumber = 4;
        level.coin = 150;
        Random random1 = new Random();
        DomesticAnimals.Chicken chicken1 = new DomesticAnimals.Chicken(random1.nextInt(6), random1.nextInt(6));
        level.millFactory = new Factory.MillFactory();
        level.millFactory.existence = true;
        level.chickens.add(chicken1);
        level.bucket.full = true;
        level.bucket.capacity = 5;

        //task
        level.task.taskCounter = 0;
        level.task.taskObj = 4;
        level.task.coinObj = 300;
        level.task.totalCoin = 150;
        level.task.eggObj = 6;
        level.task.flourObj = 4;
        level.task.breadObj = 2;


        File file1 = new File("");
        String absolutePath = file1.getAbsolutePath();
        absolutePath += ("/Level" + level.levelNumber + ".json");
        Gson write = new GsonBuilder().setPrettyPrinting().create();
        FileWriter fileWriter = new FileWriter(absolutePath);
        write.toJson(level, fileWriter);
        fileWriter.flush();
        fileWriter.close();

    }

    public void writeBasicLevel5Info() throws IOException {
        Level level = new Level();
        level.levelNumber = 5;
        level.coin = 190;

        level.millFactory = new Factory.MillFactory();
        level.millFactory.existence = true;

        level.bakery = new Factory.Bakery();
        level.bakery.existence = true;

        level.bucket.full = true;
        level.bucket.capacity = 5;

        //task
        level.task.taskCounter = 0;
        level.task.taskObj = 2;
        level.task.breadObj = 8;
        level.task.flourObj = 10;


        File file1 = new File("");
        String absolutePath = file1.getAbsolutePath();
        absolutePath += ("/Level" + level.levelNumber + ".json");
        Gson write = new GsonBuilder().setPrettyPrinting().create();
        FileWriter fileWriter = new FileWriter(absolutePath);
        write.toJson(level, fileWriter);
        fileWriter.flush();
        fileWriter.close();

    }

    public void writeBasicLevel6Info() throws IOException {
        Level level = new Level();
        level.levelNumber = 6;
        level.coin = 90;
        Random random1 = new Random();
        DomesticAnimals.Chicken chicken1 = new DomesticAnimals.Chicken(random1.nextInt(6), random1.nextInt(6));
        level.millFactory = new Factory.MillFactory();
        level.millFactory.existence = true;
        level.bakery = new Factory.Bakery();
        level.bakery.existence = true;

        level.chickens.add(chicken1);

        level.bucket.full = true;
        level.bucket.capacity = 5;

        //task
        level.task.taskCounter = 0;
        level.task.taskObj = 4;
        level.task.chickenObj = 6;
        level.task.breadObj = 10;
        level.task.flourObj = 12;


        File file1 = new File("");
        String absolutePath = file1.getAbsolutePath();
        absolutePath += ("/Level" + level.levelNumber + ".json");
        Gson write = new GsonBuilder().setPrettyPrinting().create();
        FileWriter fileWriter = new FileWriter(absolutePath);
        write.toJson(level, fileWriter);
        fileWriter.flush();
        fileWriter.close();

    }

    public void writeBasicLevel7Info() throws IOException {
        Level level = new Level();
        level.levelNumber = 7;
        level.coin = 400;
        level.weaveFactory = new Factory.WeaveFactory();
        level.weaveFactory.existence = true;
        level.bucket.full = true;
        level.bucket.capacity = 5;

        //task
        level.task.taskCounter = 0;
        level.task.taskObj = 3;
        level.task.flourObj = 10;
        level.task.breadObj = 8;
        level.task.chickenObj = 6;

        File file1 = new File("");
        String absolutePath = file1.getAbsolutePath();
        absolutePath += ("/Level" + level.levelNumber + ".json");
        Gson write = new GsonBuilder().setPrettyPrinting().create();
        FileWriter fileWriter = new FileWriter(absolutePath);
        write.toJson(level, fileWriter);
        fileWriter.flush();
        fileWriter.close();

    }

    public void writeBasicLevel8Info() throws IOException {
        Level level = new Level();
        level.levelNumber = 8;
        level.coin = 250;
        Random random1 = new Random();
        DomesticAnimals.Chicken chicken1 = new DomesticAnimals.Chicken(random1.nextInt(6), random1.nextInt(6));
        level.millFactory = new Factory.MillFactory();
        level.millFactory.existence = true;
        level.bakery = new Factory.Bakery();
        level.bakery.existence = true;
        level.weaveFactory = new Factory.WeaveFactory();
        level.weaveFactory.existence = true;
        level.chickens.add(chicken1);

        level.bucket.full = true;
        level.bucket.capacity = 5;

        //task
        level.task.taskCounter = 0;
        level.task.taskObj = 2;
        level.task.flourObj = 15;
        level.task.coinObj = 600;
        level.task.totalCoin = 250;


        File file1 = new File("");
        String absolutePath = file1.getAbsolutePath();
        absolutePath += ("/Level" + level.levelNumber + ".json");
        Gson write = new GsonBuilder().setPrettyPrinting().create();
        FileWriter fileWriter = new FileWriter(absolutePath);
        write.toJson(level, fileWriter);
        fileWriter.flush();
        fileWriter.close();

    }

    public Level readBasicLevelInfo(Level level, int levelNumber) throws IOException {
        Gson gson = new Gson();
        File file1 = new File("");
        String absolutePath = file1.getAbsolutePath();
        absolutePath += ("/Level" + levelNumber + ".json");
        FileReader fileReader = new FileReader(absolutePath);
        level = gson.fromJson(fileReader, Level.class);
        fileReader.close();
        return level;
    }

    public void printInfo(Level level) {
        System.out.println(ANSI_CYAN + "Passed time: " + level.passedTime + " time units");
        System.out.println("Your coins: " + level.coin + "$");
        for (int i = 1; i <= 6; i++) {
            for (int j = 1; j <= 6; j++) {
                System.out.print(level.map.map[i - 1][j - 1].grass + " ");
            }
            System.out.println();
        }

        for (DomesticAnimals.Chicken chicken : level.chickens) {
            System.out.println("Chicken " + chicken.health + "% " + "[" + chicken.x + " " + chicken.y + "]");
        }
        for (DomesticAnimals.Turkey turkey : level.turkies) {
            System.out.println("Turkey " + turkey.health + "% " + "[" + turkey.x + " " + turkey.y + "]");
        }
        for (DomesticAnimals.Buffalo buffalo : level.buffalos) {
            System.out.println("Buffalo " + buffalo.health + "% " + "[" + buffalo.x + " " + buffalo.y + "]");
        }
        System.out.println("Factories:");
        if (level.bakery.existence) {
            System.out.println("-=Bakery=-");
        }
        if (level.iceFactory.existence) {
            System.out.println("-=Ice Cream Factory=-");
        }
        if (level.milkFactory.existence) {
            System.out.println("-=Milk Factory=-");
        }
        if (level.millFactory.existence) {
            System.out.println("-=Mill Factory=-");
        }
        if (level.weaveFactory.existence) {
            System.out.println("-=Weave Factory=-");
        }
        if (level.sewingFactory.existence) {
            System.out.println("-=Sewing Factory=-");
        }

        System.out.println("Products: ");
        System.out.println("Tasks: " + ANSI_RESET);
        if (level.task.turkeyObj != 10000) {
            System.out.println("Turkies: " + level.task.turkeyCounter + "/" + level.task.turkeyObj);
        }
        if (level.task.chickenObj != 10000) {
            System.out.println("Chickens: " + level.task.chickenCounter + "/" + level.task.chickenObj);
        }
        if (level.task.buffaloObj != 10000) {
            System.out.println("Buffalos: " + level.task.buffaloCounter + "/" + level.task.buffaloObj);
        }
        if (level.task.eggObj != 10000) {
            System.out.println("Eggs: " + level.task.eggCounter + "/" + level.task.eggObj);
        }
        if (level.task.weaveObj != 10000) {
            System.out.println("Fabrics: " + level.task.weaveCounter + "/" + level.task.weaveObj);
        }
        if (level.task.clothObj != 10000) {
            System.out.println("Clothes: " + level.task.clothCounter + "/" + level.task.clothObj);
        }
        if (level.task.iceCreamObj != 10000) {
            System.out.println("Ice creams: " + level.task.iceCreamCounter + "/" + level.task.iceCreamObj);
        }
        if (level.task.flourObj != 10000) {
            System.out.println("Flours: " + level.task.flourCounter + "/" + level.task.flourObj);
        }
        if (level.task.milkObj!= 10000) {
            System.out.println("Milks: " + level.task.milkCounter + "/" + level.task.milkObj);
        }
        if (level.task.cmilkObj != 10000) {
            System.out.println("Compact Milks: " + level.task.cmilkCounter + "/" + level.task.cmilkObj);
        }
        if(level.task.coinObj != 10000){
            System.out.println("Coins: "+level.task.totalCoin+"/"+level.task.coinObj+ANSI_RESET);
        }




    }

    public void mapInitialize(Level level) throws IOException {

        for (int i = 1; i <= 6; i++) {
            for (int j = 1; j <= 6; j++) {
                level.map.map[i - 1][j - 1] = new Cell(i - 1, j - 1);
                level.map.map[i - 1][j - 1].grass = 0;
            }

        }
        logger("info", "map", level);

        save();
    }

    public void logger(String category, String type, Level level) throws IOException {
        Date date = new Date();

        if (category.equals("error")) {
            if (type.equals("wrong pass")) {
                log += ("[error] " + date + " Wrong password!\n");
            } else if (type.equals("no account")) {
                log += ("[error] " + date + " Account doesn't exist!\n");
            } else if (type.equals("account exists")) {
                log += ("[error] " + date + " Account already exists!\n");
            } else if (type.equals("invalid input")) {
                log += ("[error] " + date + " Invalid input entered!\n");
            } else if (type.equals("low coin")) {
                log += ("[error] " + date + " Not enough coin!\n");
            } else if (type.equals("no space")) {
                log += ("[error] " + date + " There's not enough space in the storage!\n");
            } else if (type.equals("nothing to pickup")) {
                log += ("[error] " + date + " There's nothing there to pick up!\n");
            }
        } else if (category.equals("info")) {
            if (type.equals("log in")) {
                log += ("[info] " + date + " Logged in successfully!\n");
            } else if (type.equals("username entered")) {
                log += ("[info] " + date + " username entered!\n");
            } else if (type.equals("account created")) {
                log += ("[info] " + date + " Account created successfully!\n");
            } else if (type.equals("exit")) {
                log += ("[info] " + date + " Exited from the game!\n");
            } else if (type.equals("log out")) {
                log += ("[info] " + date + " Logged out successfully!\n");
            } else if (type.equals("level started")) {
                log += ("[info] " + date + " Level " + level.levelNumber + " started!\n");
            } else if (type.equals("settings")) {
                log += ("[info] " + date + " settings\n");
            } else if (type.equals("new level")) {
                log += ("[info] " + date + " new Level " + level.levelNumber + " created!\n");
            } else if (type.equals("level continue")) {
                log += ("[info] " + date + " Level " + level.levelNumber + " continued!\n");
            } else if (type.equals("map")) {
                log += ("[info] " + date + " Map initialized!\n");
            } else if (type.equals("buy chicken")) {
                log += ("[info] " + date + " Chicken bought!\n");
            } else if (type.equals("buy buffalo")) {
                log += ("[info] " + date + " Buffalo bought!\n");
            } else if (type.equals("buy turkey")) {
                log += ("[info] " + date + " Turkey bought!\n");
            } else if (type.equals("expired")) {
                log += ("[info] " + date + " Item expired!\n");
            } else if (type.equals("build bakery")) {
                log += ("[info] " + date + " Bakery built!\n");
            } else if (type.equals("build sewing")) {
                log += ("[info] " + date + " Sewing factory built!\n");
            } else if (type.equals("build ice factory")) {
                log += ("[info] " + date + " Ice cream factory built!\n");
            } else if (type.equals("build mill factory")) {
                log += ("[info] " + date + " Mill factory built!\n");
            } else if (type.equals("build weave factory")) {
                log += ("[info] " + date + " Weave factory built!\n");
            } else if (type.equals("build milk factory")) {
                log += ("[info] " + date + " Milk factory built!\n");
            } else if (type.equals("work bakery")) {
                log += ("[info] " + date + " Bakery started working!\n");
            } else if (type.equals("work sewingfactory")) {
                log += ("[info] " + date + " Sewing factory started working!\n");
            } else if (type.equals("work icefactory")) {
                log += ("[info] " + date + " Ice cream factory started working!\n");
            } else if (type.equals("work millfactory")) {
                log += ("[info] " + date + " Mill factory started working!\n");
            } else if (type.equals("work weavefactory")) {
                log += ("[info] " + date + " Weave factory started working!\n");
            } else if (type.equals("work milkfactory")) {
                log += ("[info] " + date + " Milk factory started working!\n");
            }

        } else if (category.equals("alarm")) {

        }
        logWriter();
    }

    public void logWriter() throws IOException {
        File file = new File("Log.txt");
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(log);
        fileWriter.close();

    }


}
