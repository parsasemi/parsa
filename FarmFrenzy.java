/*
import Animals.DomesticAnimals;
import Animals.SpecialAnimals;
import Animals.WildAnimals;
import LevelDesign.*;
import LevelDesign.Map;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class FarmFrenzy {

    import Animals.DomesticAnimals;
import Animals.SpecialAnimals;
import Animals.WildAnimals;
import LevelDesign.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jdk.swing.interop.SwingInterOpUtils;


    public class Graphics.Manager {
        public static final String ANSI_RED = "\u001B[31m";
        public static final String ANSI_RESET = "\u001B[0m";
        public static final String ANSI_CYAN = "\u001B[36m";
        public static final String ANSI_YELLOW = "\u001B[33m";
        public static final String ANSI_PURPLE = "\u001B[35m";
        public static final String ANSI_BLUE = "\u001B[34m";

        ArrayList<LevelDesign.Level> levels = new ArrayList<>();
        private ArrayList<Graphics.LoginUser> Users = new ArrayList<>();
        String log = "";

        public void Buy(String name, LevelDesign.Level level) throws IOException {
            Random random = new Random();
            if (name.equals("buffalo")) {
                Animals.DomesticAnimals.Buffalo buffalo = new Animals.DomesticAnimals.Buffalo(random.nextInt(6) + 1, random.nextInt(6) + 1);
                if (level.coin >= buffalo.BuyPrice) {
                    level.buffalos.add(buffalo);
                    level.coin -= buffalo.BuyPrice;
                    logger("info", "buy buffalo", level);
                } else {
                    System.out.println("NOT ENOUGH COIN TO BUY!");
                    logger("error", "low coin", level);
                }
            } else if (name.equals("turkey")) {
                Animals.DomesticAnimals.Turkey turkey = new Animals.DomesticAnimals.Turkey(random.nextInt(6) + 1, random.nextInt(6) + 1);
                if (level.coin >= turkey.BuyPrice) {
                    level.turkies.add(turkey);
                    level.coin -= turkey.BuyPrice;
                    logger("info", "buy turkey", level);
                } else {
                    System.out.println("NOT ENOUGH COIN TO BUY!");
                    logger("error", "low coin", level);
                }
            } else if (name.equals("chicken")) {
                Animals.DomesticAnimals.Chicken chicken = new Animals.DomesticAnimals.Chicken(random.nextInt(6) + 1, random.nextInt(6) + 1);
                if (level.coin >= chicken.BuyPrice) {
                    level.chickens.add(chicken);
                    level.coin -= chicken.BuyPrice;
                    logger("info", "buy chicken", level);
                } else {
                    System.out.println("NOT ENOUGH COIN TO BUY!");
                    logger("error", "low coin", level);
                }
            } else if (name.equals("cat")) {
                Random r = new Random();
                Animals.SpecialAnimals.Cat cat = new Animals.SpecialAnimals.Cat(random.nextInt(6) + 1, random.nextInt(6) + 1);
                if (level.coin >= cat.buyPrice) {
                    level.cats.add(cat);
                    level.coin -= cat.buyPrice;
                    logger("info", "buy cat", level);
                } else {
                    System.out.println("NOT ENOUGH COIN TO BUY!");
                    logger("error", "low coin", level);
                }

            } else if (name.equals("dog")) {
                Random r = new Random();
                Animals.SpecialAnimals.Dog dog = new Animals.SpecialAnimals.Dog(random.nextInt(6) + 1, random.nextInt(6) + 1);
                if (level.coin >= dog.buyPrice) {
                    level.dogs.add(dog);
                    level.coin -= dog.buyPrice;
                    logger("info", "buy dog", level);
                } else {
                    System.out.println("NOT ENOUGH COIN TO BUY!");
                    logger("error", "low coin", level);
                }

            } else {
                System.out.println("Invalid Input because there is no such animal.");
            }
            save();
        }

        public void PickUp(int x, int y, LevelDesign.Level level) throws IOException {
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
                            } else if (level.ingredients.get(i).name.equals("flour")) {
                                level.task.flourCounter++;
                            } else if (level.ingredients.get(i).name.equals("cmilk")) {
                                level.task.cmilkCounter++;
                            } else if (level.ingredients.get(i).name.equals("bread")) {
                                level.task.milkCounter++;
                            } else if (level.ingredients.get(i).name.equals("icecream")) {
                                level.task.iceCreamCounter++;
                            } else if (level.ingredients.get(i).name.equals("cloth")) {
                                level.task.clothCounter++;
                            } else if (level.ingredients.get(i).name.equals("weave")) {
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
            save();
        }

        public void Expirings(LevelDesign.Level level) throws IOException {
            Date date = new Date();
            int e = level.ingredients.size();
            for (int i = e - 1; i >= 0; i--) {
                if (level.ingredients.get(i).expire == 1) {
                    log += ("[info] " + date + " " + level.ingredients.get(i).name + " x: " + level.ingredients.get(i).x + " y: " + level.ingredients.get(i).y);
                    logWriter();
                    level.ingredients.remove(i);

                } else {
                    level.ingredients.get(i).expire--;
                }
            }
            save();
        }

        public void Well(LevelDesign.Level level) throws IOException {
            if (level.bucket.duration >= level.bucket.maxDuration) {
                Date date = new Date();
                level.bucket.full = true;
                level.bucket.capacity = 5;
                level.bucket.duration = -1;

                log += ("[info] " + date + " Bucket full!\n");
                logWriter();

            } else if (level.bucket.duration >= 0) {
                level.bucket.duration++;

            }
            save();
        }

        public void Plant(int x, int y, LevelDesign.Level level) throws IOException {
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
            save();
        }

        public void Build(LevelDesign.Level level, String name) throws IOException {
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
            save();
        }

        public void Cage(int x, int y, LevelDesign.Level level) throws IOException {
            int l = -1;
            int b = -1;
            int t = -1;
            for (int i = 0; i < level.lions.size(); i++) {
                if (level.lions.get(i).x == x && level.lions.get(i).y == y) {
                    l = i;
                    break;
                }
            }
            if (l == -1) {
                for (int i = 0; i < level.bears.size(); i++) {
                    if (level.bears.get(i).x == x && level.bears.get(i).y == y) {
                        b = i;
                        break;
                    }
                }
                if (b == -1) {
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
                level.lions.get(l).cageCounter++;
                if (level.lions.get(l).cageCounter == level.lions.get(l).cageP) {
                    level.lions.get(l).inCage = true;
                }
                level.lions.get(l).cagePlus = true;
            } else if (b != -1) {
                level.bears.get(b).cageCounter++;
                if (level.bears.get(b).cageCounter == level.bears.get(b).cageP) {
                    level.bears.get(b).inCage = true;
                }
                level.bears.get(b).cagePlus = true;
            } else if (t != -1) {
                level.tigers.get(t).cageCounter++;
                if (level.tigers.get(t).cageCounter == level.tigers.get(t).cageP) {
                    level.tigers.get(t).inCage = true;
                }
                level.tigers.get(t).cagePlus = true;
            }
            save();
        }

        public void CageCounter(LevelDesign.Level level) throws IOException {
            for (int i = 0; i < level.lions.size(); i++) {
                if (!level.lions.get(i).cagePlus) {
                    if (level.lions.get(i).cageCounter > 0)
                        level.lions.get(i).cageCounter--;
                } else {
                    level.lions.get(i).cagePlus = false;
                }
            }
            for (int i = 0; i < level.bears.size(); i++) {
                if (!level.bears.get(i).cagePlus) {
                    if (level.bears.get(i).cageCounter > 0)
                        level.bears.get(i).cageCounter--;
                } else {
                    level.bears.get(i).cagePlus = false;
                }
            }
            for (int i = 0; i < level.tigers.size(); i++) {
                if (!level.tigers.get(i).cagePlus) {
                    if (level.tigers.get(i).cageCounter > 0)
                        level.tigers.get(i).cageCounter--;
                } else {
                    level.tigers.get(i).cagePlus = false;
                }
            }
            save();

        }

        public void InCageCounter(LevelDesign.Level level) throws IOException {
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
            save();
        }

        public void CageEnd(LevelDesign.Level level) throws IOException {
            int e = level.lions.size();
            for (int i = e - 1; i >= 0; i--) {
                if (level.lions.get(i).inCageCounter == 5) {
                    level.lions.remove(i);
                }
            }
            e = level.bears.size();
            for (int i = e - 1; i >= 0; i--) {
                if (level.bears.get(i).inCageCounter == 5) {
                    level.bears.remove(i);
                }
            }
            e = level.tigers.size();
            for (int i = e - 1; i >= 0; i--) {
                if (level.tigers.get(i).inCageCounter == 5) {
                    level.tigers.remove(i);
                }
            }
            save();
        }

        public void CageStore(LevelDesign.Level level) throws IOException {
            for (int i = 0; i < level.lions.size(); i++) {
                if (level.lions.get(i).inCage) {
                    if (level.storage.capacity >= level.lions.get(i).capacity) {
                        level.storage.names.add("lion");
                        level.storage.quantities.add(1);
                        level.storage.capacity -= level.lions.get(i).capacity;
                        level.lions.remove(i);
                    }
                }
            }
            for (int i = 0; i < level.bears.size(); i++) {
                if (level.bears.get(i).inCage) {
                    if (level.storage.capacity >= level.bears.get(i).capacity) {
                        level.storage.names.add("bear");
                        level.storage.quantities.add(1);
                        level.storage.capacity -= level.bears.get(i).capacity;
                        level.bears.remove(i);
                    }
                }
            }
            for (int i = 0; i < level.tigers.size(); i++) {
                if (level.tigers.get(i).inCage) {
                    if (level.storage.capacity >= level.tigers.get(i).capacity) {
                        level.storage.names.add("tiger");
                        level.storage.quantities.add(1);
                        level.storage.capacity -= level.tigers.get(i).capacity;
                        level.tigers.remove(i);
                    }
                }
            }
            save();
        }

        public void AnimalCounter(LevelDesign.Level level) throws IOException {
            int e = level.chickens.size();
            for (int i = e - 1; i >= 0; i--) {
                if (level.chickens.get(i).time >= level.chickens.get(i).productTime - 1) {
                    int a = level.chickens.get(i).x;
                    int b = level.chickens.get(i).y;
                    level.chickens.get(i).time = -1;
                    LevelDesign.Ingredient.Egg egg = new LevelDesign.Ingredient.Egg(a, b);
                    level.ingredients.add(egg);
                } else {
                    level.chickens.get(i).time++;
                }
                level.chickens.get(i).health -= 10;
                if (level.chickens.get(i).health <= 0) {
                    level.chickens.remove(i);
                }
            }
            e = level.buffalos.size();
            for (int i = e - 1; i >= 0; i--) {
                if (level.buffalos.get(i).time >= level.buffalos.get(i).productTime - 1) {
                    int a = level.buffalos.get(i).x;
                    int b = level.buffalos.get(i).y;
                    level.buffalos.get(i).time = -1;
                    LevelDesign.Ingredient.Milk milk = new LevelDesign.Ingredient.Milk(a, b);
                    level.ingredients.add(milk);
                } else {
                    level.buffalos.get(i).time++;
                }
                level.buffalos.get(i).health -= 10;
                if (level.buffalos.get(i).health <= 0) {
                    level.buffalos.remove(i);
                }
            }
            e = level.turkies.size();
            for (int i = e - 1; i >= 0; i--) {
                if (level.turkies.get(i).time >= level.turkies.get(i).productTime - 1) {
                    int a = level.turkies.get(i).x;
                    int b = level.turkies.get(i).y;
                    level.turkies.get(i).time = -1;
                    LevelDesign.Ingredient.Feather feather = new LevelDesign.Ingredient.Feather(a, b);
                    level.ingredients.add(feather);
                } else {
                    level.turkies.get(i).time++;
                }
                level.turkies.get(i).health -= 10;
                if (level.turkies.get(i).health <= 0) {
                    level.turkies.remove(i);
                }
            }
            save();
        }

        public ArrayList<Animals.DomesticAnimals> AnimalHealth(LevelDesign.Level level) throws IOException {
            ArrayList<Animals.DomesticAnimals> needed = new ArrayList<>();
            for (int i = 0; i < level.chickens.size(); i++) {
            int x = level.chickens.get(i).x;
            int y = level.chickens.get(i).y;

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
            save();
            return needed;


        }

        public void NeededFiller(LevelDesign.Level level, ArrayList<Animals.DomesticAnimals> needed) throws IOException {
            Collections.sort(needed, (o1, o2) -> {
                if (o1.health > o2.health) return 1;
                return -1;
            });
            for (int i = 0; i < needed.size(); i++) {
                int x = needed.get(i).x;
                int y = needed.get(i).y;
                if (level.map.map[x - 1][y - 1].grass >= 1) {
                    level.map.map[x - 1][y - 1].grass--;
                    needed.get(i).health = 100;
                }
            }
            save();
        }

        public LevelDesign.Ingredient StringToIngr(String name) throws IOException {
            if (name.equals("bread")) {
                save();
                return new LevelDesign.Ingredient.Bread(1, 1);
            } else if (name.equals("flour")) {
                save();
                return new LevelDesign.Ingredient.Flour(1, 1);
            } else if (name.equals("feather")) {
                save();
                return new LevelDesign.Ingredient.Feather(1, 1);
            } else if (name.equals("milk")) {
                save();
                return new LevelDesign.Ingredient.Milk(1, 1);
            } else if (name.equals("cmilk")) {
                save();
                return new LevelDesign.Ingredient.CMilk(1, 1);
            } else if (name.equals("weave")) {
                save();
                return new LevelDesign.Ingredient.Weave(1, 1);
            } else if (name.equals("egg")) {
                save();
                return new LevelDesign.Ingredient.Egg(1, 1);
            } else {
                return null;
            }

        }

        public Animals.WildAnimals StringToAnimal(String name) throws IOException {
            if (name.equals("lion")) {
                save();
                return new Animals.WildAnimals.Lion(1, 1);
            }
            if (name.equals("tiger")) {
                save();
                return new Animals.WildAnimals.Tiger(1, 1);
            }
            if (name.equals("bear")) {
                save();
                return new Animals.WildAnimals.Bear(1, 1);
            } else
                return null;
        }

        public void DogAction(LevelDesign.Level level) throws IOException {
            if (level.dogs.size() > 0) {
                for (int i = 0; i < level.dogs.size(); i++) {
                    for (int j = 0; j < level.lions.size(); j++) {
                        if (level.dogs.get(i).x == level.lions.get(j).x && level.dogs.get(i).y == level.lions.get(j).y) {
                            level.dogs.remove(i);
                            level.lions.remove(j);
                            break;
                        }
                    }
                    for (int j = 0; j < level.bears.size(); j++) {
                        if (level.dogs.get(i).x == level.bears.get(j).x && level.dogs.get(i).y == level.bears.get(j).y) {
                            level.dogs.remove(i);
                            level.bears.remove(j);
                            break;
                        }
                    }
                    for (int j = 0; j < level.tigers.size(); j++) {
                        if (level.dogs.get(i).x == level.tigers.get(j).x && level.dogs.get(i).y == level.tigers.get(j).y) {
                            level.dogs.remove(i);
                            level.tigers.remove(j);
                            break;
                        }
                    }
                }
            }
            save();
        }

        public void CatAction(LevelDesign.Level level) throws IOException {
            if (level.cats.size() > 0) {
                for (int i = 0; i < level.cats.size(); i++) {
                    int x = level.cats.get(i).x;
                    int y = level.cats.get(i).y;
                    int e = level.ingredients.size();
                    for (int j = e - 1; j >= 0; j--) {
                        if (level.ingredients.get(j).x == x && level.ingredients.get(j).y == y) {
                            if (level.storage.capacity >= level.ingredients.get(j).size) {
                                level.storage.names.add(level.ingredients.get(j).name);
                                level.storage.quantities.add(1);
                                level.storage.capacity -= level.ingredients.get(j).size;
                                if (level.ingredients.get(j).name.equals("egg")) {
                                    level.task.eggCounter++;
                                } else if (level.ingredients.get(j).name.equals("flour")) {
                                    level.task.flourCounter++;
                                } else if (level.ingredients.get(j).name.equals("milk")) {
                                    level.task.milkCounter++;
                                } else if (level.ingredients.get(j).name.equals("cmilk")) {
                                    level.task.cmilkCounter++;
                                } else if (level.ingredients.get(j).name.equals("weave")) {
                                    level.task.weaveCounter++;
                                } else if (level.ingredients.get(j).name.equals("icecream")) {
                                    level.task.iceCreamCounter++;
                                } else if (level.ingredients.get(j).name.equals("feather")) {
                                    level.task.featherCounter++;
                                } else if (level.ingredients.get(j).name.equals("cloth")) {
                                    level.task.clothCounter++;
                                } else if (level.ingredients.get(j).name.equals("bread")) {
                                    level.task.breadCounter++;
                                }
                            }
                        }
                    }
                }
            }
            save();
        }

        public void LionAdder(LevelDesign.Level level) {
            Random random = new Random();
            int a = random.nextInt(6) + 1;
            int b = random.nextInt(6) + 1;
            Animals.WildAnimals.Lion lion = new Animals.WildAnimals.Lion(a, b);
            level.lions.add(lion);
        }

        public void BearAdder(LevelDesign.Level level) {
            Random random = new Random();
            int a = random.nextInt(6) + 1;
            int b = random.nextInt(6) + 1;
            Animals.WildAnimals.Bear lion = new Animals.WildAnimals.Bear(a, b);
            level.bears.add(lion);
        }

        public void TigerAdder(LevelDesign.Level level) {
            Random random = new Random();
            int a = random.nextInt(6) + 1;
            int b = random.nextInt(6) + 1;
            Animals.WildAnimals.Tiger lion = new Animals.WildAnimals.Tiger(a, b);
            level.tigers.add(lion);
        }

        public void wildAnimalAddTimeChecker(LevelDesign.Level level) throws IOException {
            Random random = new Random();
            if (level.task.timeCounter == level.wildAnimalAddTimer1 && level.wildAnimalAddTimer1 != 10000) {
                int a = random.nextInt(3) + 1;

                if (a == 1) {
                    LionAdder(level);

                } else if (a == 2) {
                    BearAdder(level);

                } else if (a == 3) {
                    TigerAdder(level);

                }
            }
            if (level.task.timeCounter == level.wildAnimalAddTimer2 && level.wildAnimalAddTimer2 != 10000) {
                int a = random.nextInt(3) + 1;
                if (a == 1) {
                    LionAdder(level);

                } else if (a == 2) {
                    BearAdder(level);

                } else if (a == 3) {
                    TigerAdder(level);

                }
            }
            if (level.task.timeCounter == level.wildAnimalAddTimer3 && level.wildAnimalAddTimer3 != 10000) {
                int a = random.nextInt(3) + 1;
                if (a == 1) {
                    LionAdder(level);

                } else if (a == 2) {
                    BearAdder(level);

                } else if (a == 3) {
                    TigerAdder(level);

                }
            }
            if (level.task.timeCounter == level.wildAnimalAddTimer4 && level.wildAnimalAddTimer4 != 10000) {
                int a = random.nextInt(3) + 1;
                if (a == 1) {
                    LionAdder(level);

                } else if (a == 2) {
                    BearAdder(level);

                } else if (a == 3) {
                    TigerAdder(level);

                }
            }
            if (level.task.timeCounter == level.wildAnimalAddTimer5 && level.wildAnimalAddTimer5 != 10000) {
                int a = random.nextInt(3) + 1;
                if (a == 1) {
                    LionAdder(level);

                } else if (a == 2) {
                    BearAdder(level);

                } else if (a == 3) {
                    TigerAdder(level);

                }
            }
            if (level.task.timeCounter == level.wildAnimalAddTimer6 && level.wildAnimalAddTimer6 != 10000) {
                int a = random.nextInt(3) + 1;
                if (a == 1) {
                    LionAdder(level);

                } else if (a == 2) {
                    BearAdder(level);

                } else if (a == 3) {
                    TigerAdder(level);

                }
            }
            if (level.task.timeCounter == level.wildAnimalAddTimer7 && level.wildAnimalAddTimer7 != 10000) {
                int a = random.nextInt(3) + 1;
                if (a == 1) {
                    LionAdder(level);

                } else if (a == 2) {
                    BearAdder(level);

                } else if (a == 3) {
                    TigerAdder(level);

                }
            }
            save();
        }

        public boolean tasksChecker(LevelDesign.Level level) throws IOException {
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
            if (level.buffalos.size() >= level.task.buffaloObj && level.task.buffaloObj != 10000 && !level.task.buffaloCheck) {
                level.task.buffaloCheck = true;
                level.task.taskCounter++;
            }
            if (level.turkies.size() >= level.task.turkeyObj && level.task.turkeyObj != 10000 && !level.task.turkeyCheck) {
                level.task.turkeyCheck = true;
                level.task.taskCounter++;
            }
            if (level.task.breadCounter >= level.task.breadObj && level.task.breadObj != 10000 && !level.task.breadCheck) {
                level.task.breadCheck = true;
                level.task.taskCounter++;
            }
            if (level.task.flourCounter >= level.task.flourObj && level.task.flourObj != 10000 && !level.task.flourCheck) {
                level.task.flourCheck = true;
                level.task.taskCounter++;
            }
            if (level.task.milkCounter >= level.task.milkObj && level.task.milkObj != 10000 && !level.task.milkCheck) {
                level.task.milkCheck = true;
                level.task.taskCounter++;
            }
            if (level.task.cmilkCounter >= level.task.cmilkObj & level.task.cmilkObj != 10000 && !level.task.cmilkCheck) {
                level.task.cmilkCheck = true;
                level.task.taskCounter++;
            }
            if (level.task.iceCreamCounter >= level.task.iceCreamObj && level.task.iceCreamObj != 10000 && !level.task.iceCreamCheck) {
                level.task.iceCreamCheck = true;
                level.task.taskCounter++;
            }
            if (level.task.clothCounter >= level.task.clothObj && level.task.clothObj != 10000 && !level.task.clothCheck) {
                level.task.clothCheck = true;
                level.task.taskCounter++;
            }
            if (level.task.eggCounter >= level.task.eggObj && level.task.eggObj != 10000 && !level.task.eggCheck) {
                level.task.eggCheck = true;
                level.task.taskCounter++;
            }
            if (level.task.weaveCounter >= level.task.weaveObj && level.task.weaveObj != 10000 && !level.task.weaveCheck) {
                level.task.weaveCheck = true;
                level.task.taskCounter++;
            }
            if (level.task.featherCounter >= level.task.featherObj && level.task.featherObj != 10000 && !level.task.featherCheck) {
                level.task.featherCheck = true;
                level.task.taskCounter++;
            }


            if (level.task.taskCounter == level.task.taskObj) {

                if (!level.task.timeCheck1 && !level.task.timeCheck2 && !level.task.timeCheck3) {

                    System.out.println(ANSI_YELLOW + "Level finished! Level " + (level.levelNumber + 1) + " unlocked!" + ANSI_RESET);
                    System.out.println(ANSI_PURPLE + "You recieve a golden Medal for finishing the level before the first time objective!" + ANSI_RESET);
                    taskCheck = true;
                } else if (level.task.timeCheck1 && !level.task.timeCheck2 && !level.task.timeCheck3) {

                    System.out.println(ANSI_YELLOW + "Level finished! Level " + (level.levelNumber + 1) + " unlocked!" + ANSI_RESET);
                    System.out.println(ANSI_PURPLE + "You recieve a silver Medal for finishing the level before the second time objective!" + ANSI_RESET);
                    taskCheck = true;
                } else if (level.task.timeCheck1 && level.task.timeCheck2 && !level.task.timeCheck3) {

                    System.out.println(ANSI_YELLOW + "Level finished! Level " + (level.levelNumber + 1) + " unlocked!" + ANSI_RESET);
                    System.out.println(ANSI_PURPLE + "You recieve a bronze Medal for finishing the level before the third time objective!" + ANSI_RESET);
                    taskCheck = true;
                } else if (level.task.timeCheck1 && level.task.timeCheck2 && level.task.timeCheck3) {

                    System.out.println(ANSI_YELLOW + "Meeeeeh okay... Level finished! Level " + (level.levelNumber + 1) + " unlocked!" + ANSI_RESET);
                    System.out.println(ANSI_PURPLE + "You recieve a golden banana for finishing the level after the third time objective!" + ANSI_RESET);
                    taskCheck = true;
                }
                taskCheck = true;

            }
            save();
            return taskCheck;

        }

        public void timeTaskchecker(LevelDesign.Level level) {
            if (level.task.timeCounter >= level.task.timeObj1) {
                level.task.timeCheck1 = true;
            }

            if (level.task.timeCounter >= level.task.timeObj2) {
                level.task.timeCheck2 = true;
            }

            if (level.task.timeCounter >= level.task.timeObj3) {
                level.task.timeCheck3 = true;
            }

        }

        public void readUsersInfo() throws IOException {
            File file1 = new File("");
            String absolutePath = file1.getAbsolutePath();
            absolutePath += "/usersInfo.json";
            File file2 = new File(absolutePath);
            if (file2.exists()) {
                Gson gson = new Gson();
                Reader reader = Files.newBufferedReader(Paths.get("usersInfo.json"));
                Users.clear();
                List<Graphics.LoginUser> temp = Arrays.asList(gson.fromJson(reader, Graphics.LoginUser[].class));
                Users.addAll(temp);
                reader.close();
            }

        }

        public boolean usernameCheck(String username) throws IOException {

            boolean found = false;

            File file1 = new File("");
            String absolutePath = file1.getAbsolutePath();
            absolutePath += "/usersInfo.json";
            File file2 = new File(absolutePath);
            if (file2.exists()) {
                Gson gson = new Gson();
                Reader reader = Files.newBufferedReader(Paths.get("usersInfo.json"));
                Users.clear();
                List<Graphics.LoginUser> temp = Arrays.asList(gson.fromJson(reader, Graphics.LoginUser[].class));
                Users.addAll(temp);
                reader.close();
                for (Graphics.LoginUser u : Users) {
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
            Graphics.LoginUser user = new Graphics.LoginUser(username, password);
            Users.add(user);

            Gson gson = new Gson();
            Writer writer = Files.newBufferedWriter(Paths.get("usersInfo.json"));
            gson.toJson(Users, writer);
            writer.close();

        }

        public boolean login(String username, String password) throws IOException {
            readUsersInfo();
            boolean flag = false;
            for (Graphics.LoginUser u : Users) {
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
            for (LevelDesign.Level l : levels) {
                if (l.levelNumber == 1) {
                    absolutePath += "/LevelsInfo" + l.playerName + ".json";
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
            absolutePath += "/LevelsInfo" + username + ".json";
            File file2 = new File(absolutePath);
            if (file2.exists()) {
                Gson gson = new Gson();
                Reader reader = Files.newBufferedReader(Paths.get(absolutePath));
                List<LevelDesign.Level> temp = Arrays.asList(gson.fromJson(reader, LevelDesign.Level[].class));
                levels.clear();
                levels.addAll(temp);

                for (LevelDesign.Level l : levels) {
                    if (levelNumber == l.levelNumber) {
                        level_check = true;
                        break;
                    }
                }
                if (!level_check) {
                    System.out.println(ANSI_RED + "This level has not been unlocked yet!" + ANSI_RESET);
                    System.out.println("Your unlocked levels are: ");
                    int max = 1;
                    for (LevelDesign.Level l : levels) {
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

                    LevelDesign.Level level = new LevelDesign.Level();
                    level = readBasicLevelInfo(level, 1);
                    level.playerName = username;
                    level.levelStarted = false;
                    levels.add(level);
                    for (LevelDesign.Level l : levels) {
                        System.out.println(l.levelNumber);
                    }
                    save();
                    level_check = true;
                }

            }
            save();

            return level_check;

        }

        public LevelDesign.Level levelReturner(int levelNumber) throws IOException {
            for (LevelDesign.Level l : levels) {
                if (l.levelNumber == levelNumber) {
                    save();
                    return l;
                }
            }
            save();
            return null;
        }

        public LevelDesign.Level levelEnd(LevelDesign.Level playerLevel) throws IOException {
            for (LevelDesign.Level l : levels) {
                if (l.levelNumber == (playerLevel.levelNumber + 1)) {
                    playerLevel.levelEnd = true;
                    playerLevel.levelStarted = true;
                    save();
                    return l;
                }
            }
            LevelDesign.Level newLevel = new LevelDesign.Level();
            newLevel = readBasicLevelInfo(newLevel, playerLevel.levelNumber + 1);
            playerLevel.levelEnd = true;
            playerLevel.levelStarted = true;
            newLevel.levelStarted = false;
            newLevel.levelEnd = false;
            levels.add(newLevel);
            save();
            return newLevel;

        }

        public LevelDesign.Level newLevel(int levelNumber, LevelDesign.Level level) throws IOException {

            for (LevelDesign.Level l : levels) {
                if (l.levelNumber == levelNumber) {
                    levels.remove(l);
                    break;
                }
            }
            LevelDesign.Level newLevel = new LevelDesign.Level();
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

            LevelDesign.Level level = new LevelDesign.Level();
            level.levelNumber = 1;
            level.coin = 500;
            Random random1 = new Random();

            Animals.DomesticAnimals.Chicken chicken1 = new Animals.DomesticAnimals.Chicken(random1.nextInt(6) + 1, random1.nextInt(6) + 1);
            Animals.DomesticAnimals.Chicken chicken2 = new Animals.DomesticAnimals.Chicken(random1.nextInt(6) + 1, random1.nextInt(6) + 1);
            level.chickens.add(chicken1);
            level.chickens.add(chicken2);
            level.bucket.full = true;
            level.bucket.capacity = 5;
            level.wildAnimalAddTimer1 = 4;

            //task
            level.task.taskCounter = 0;
            level.task.taskObj = 1;
            level.task.eggObj = 6;
            level.task.timeObj1 = 8;
            level.task.timeObj2 = 10;
            level.task.timeObj3 = 12;


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
            LevelDesign.Level level = new LevelDesign.Level();
            level.levelNumber = 2;
            level.coin = 500;
            Random random1 = new Random();
            Animals.DomesticAnimals.Chicken chicken1 = new Animals.DomesticAnimals.Chicken(random1.nextInt(6) + 1, random1.nextInt(6) + 1);
            Animals.DomesticAnimals.Chicken chicken2 = new Animals.DomesticAnimals.Chicken(random1.nextInt(6) + 1, random1.nextInt(6) + 1);

            level.chickens.add(chicken1);
            level.chickens.add(chicken2);
            level.bucket.full = true;
            level.bucket.capacity = 5;
            level.wildAnimalAddTimer1 = 4;
            level.wildAnimalAddTimer2 = 8;


            //task
            level.task.taskCounter = 0;
            level.task.eggObj = 6;
            level.task.chickenObj = 4;
            level.task.taskObj = 2;
            level.task.timeObj1 = 10;
            level.task.timeObj2 = 12;
            level.task.timeObj3 = 16;


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
            LevelDesign.Level level = new LevelDesign.Level();
            level.levelNumber = 3;
            level.coin = 300;
            Random random1 = new Random();

            Animals.DomesticAnimals.Chicken chicken1 = new Animals.DomesticAnimals.Chicken(random1.nextInt(6) + 1, random1.nextInt(6) + 1);
            Animals.DomesticAnimals.Chicken chicken2 = new Animals.DomesticAnimals.Chicken(random1.nextInt(6) + 1, random1.nextInt(6) + 1);
            Animals.DomesticAnimals.Turkey turkey = new Animals.DomesticAnimals.Turkey(random1.nextInt(6) + 1, random1.nextInt(6) + 1);

            level.chickens.add(chicken1);
            level.chickens.add(chicken2);
            level.turkies.add(turkey);
            level.bucket.full = true;
            level.bucket.capacity = 5;
            level.wildAnimalAddTimer1 = 4;
            level.wildAnimalAddTimer2 = 10;
            level.wildAnimalAddTimer2 = 11;
            level.wildAnimalAddTimer3 = 14;

            //task
            level.task.taskCounter = 0;
            level.task.taskObj = 4;
            level.task.eggObj = 8;
            level.task.featherObj = 2;
            level.task.flourObj = 4;
            level.task.chickenObj = 6;
            level.task.chickenCounter = 4;
            level.task.timeObj1 = 16;
            level.task.timeObj2 = 18;
            level.task.timeObj3 = 22;


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
            LevelDesign.Level level = new LevelDesign.Level();
            level.levelNumber = 4;
            level.coin = 600;
            Random random1 = new Random();
            Animals.DomesticAnimals.Chicken chicken1 = new Animals.DomesticAnimals.Chicken(random1.nextInt(6) + 1, random1.nextInt(6) + 1);
            level.millFactory = new LevelDesign.Factory.MillFactory();
            level.millFactory.existence = true;
            level.chickens.add(chicken1);
            level.bucket.full = true;
            level.bucket.capacity = 5;
            level.wildAnimalAddTimer1 = 6;
            level.wildAnimalAddTimer2 = 8;
            level.wildAnimalAddTimer3 = 10;
            level.wildAnimalAddTimer4 = 16;

            //task
            level.task.taskCounter = 0;
            level.task.taskObj = 5;
            level.task.coinObj = 800;
            level.task.totalCoin = 400;
            level.task.buffaloObj = 2;
            level.task.flourObj = 4;
            level.task.breadObj = 2;
            level.task.timeObj1 = 18;
            level.task.timeObj2 = 22;
            level.task.timeObj3 = 26;


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
            LevelDesign.Level level = new LevelDesign.Level();
            level.levelNumber = 5;
            level.coin = 550;

            level.millFactory = new LevelDesign.Factory.MillFactory();
            level.millFactory.existence = true;

            level.bakery = new LevelDesign.Factory.Bakery();
            level.bakery.existence = true;

            level.millFactory = new LevelDesign.Factory.MillFactory();
            level.millFactory.existence = true;

            level.bucket.full = true;
            level.bucket.capacity = 5;
            level.wildAnimalAddTimer1 = 6;
            level.wildAnimalAddTimer2 = 6;
            level.wildAnimalAddTimer3 = 10;
            level.wildAnimalAddTimer4 = 15;
            level.wildAnimalAddTimer5 = 22;

            //task
            level.task.taskCounter = 0;
            level.task.taskObj = 3;
            level.task.breadObj = 8;
            level.task.flourObj = 10;
            level.task.weaveObj = 2;
            level.task.timeObj1 = 22;
            level.task.timeObj2 = 26;
            level.task.timeObj3 = 32;


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
            LevelDesign.Level level = new LevelDesign.Level();
            level.levelNumber = 6;
            level.coin = 400;
            Random random1 = new Random();
            Animals.DomesticAnimals.Chicken chicken1 = new Animals.DomesticAnimals.Chicken(random1.nextInt(6) + 1, random1.nextInt(6) + 1);
            Animals.DomesticAnimals.Buffalo buffalo1 = new Animals.DomesticAnimals.Buffalo(random1.nextInt(6) + 1, random1.nextInt(6) + 1);
            level.millFactory = new LevelDesign.Factory.MillFactory();
            level.millFactory.existence = true;
            level.bakery = new LevelDesign.Factory.Bakery();
            level.bakery.existence = true;

            level.chickens.add(chicken1);

            level.bucket.full = true;
            level.bucket.capacity = 5;
            level.wildAnimalAddTimer1 = 10;
            level.wildAnimalAddTimer2 = 14;
            level.wildAnimalAddTimer3 = 16;
            level.wildAnimalAddTimer4 = 24;
            level.wildAnimalAddTimer5 = 35;


            //task
            level.task.taskCounter = 0;
            level.task.taskObj = 4;
            level.task.chickenObj = 4;
            level.task.breadObj = 4;
            level.task.milkObj = 6;
            level.task.buffaloObj = 3;
            level.task.timeObj1 = 35;
            level.task.timeObj2 = 40;
            level.task.timeObj3 = 45;


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
            LevelDesign.Level level = new LevelDesign.Level();
            level.levelNumber = 7;
            level.coin = 1000;
            level.weaveFactory = new LevelDesign.Factory.WeaveFactory();
            level.weaveFactory.existence = true;
            level.bakery = new LevelDesign.Factory.Bakery();
            level.bakery.existence = true;

            Random random1 = new Random();
            Animals.DomesticAnimals.Chicken chicken1 = new Animals.DomesticAnimals.Chicken(random1.nextInt(6) + 1, random1.nextInt(6) + 1);


            level.bucket.full = true;
            level.bucket.capacity = 5;
            level.wildAnimalAddTimer1 = 4;
            level.wildAnimalAddTimer2 = 8;
            level.wildAnimalAddTimer3 = 15;
            level.wildAnimalAddTimer4 = 25;
            level.wildAnimalAddTimer5 = 40;
            level.wildAnimalAddTimer6 = 50;

            //task
            level.task.taskCounter = 0;
            level.task.taskObj = 4;
            level.task.flourObj = 10;
            level.task.breadObj = 8;
            level.task.buffaloObj = 4;
            level.task.weaveObj = 4;
            level.task.timeObj1 = 50;
            level.task.timeObj2 = 60;
            level.task.timeObj3 = 65;

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
            LevelDesign.Level level = new LevelDesign.Level();
            level.levelNumber = 8;
            level.coin = 900;
            Random random1 = new Random();
            Animals.DomesticAnimals.Chicken chicken1 = new Animals.DomesticAnimals.Chicken(random1.nextInt(6) + 1, random1.nextInt(6) + 1);
            level.millFactory = new LevelDesign.Factory.MillFactory();
            level.millFactory.existence = true;
            level.bakery = new LevelDesign.Factory.Bakery();
            level.bakery.existence = true;
            level.weaveFactory = new LevelDesign.Factory.WeaveFactory();
            level.weaveFactory.existence = true;
            level.chickens.add(chicken1);

            level.bucket.full = true;
            level.bucket.capacity = 5;
            level.wildAnimalAddTimer1 = 4;
            level.wildAnimalAddTimer2 = 10;
            level.wildAnimalAddTimer3 = 25;
            level.wildAnimalAddTimer4 = 40;
            level.wildAnimalAddTimer5 = 60;
            level.wildAnimalAddTimer6 = 60;
            level.wildAnimalAddTimer7 = 80;

            //task
            level.task.taskCounter = 0;
            level.task.taskObj = 6;
            level.task.flourObj = 10;
            level.task.iceCreamObj = 6;
            level.task.milkObj = 10;
            level.task.weaveObj = 2;
            level.task.buffaloObj = 4;
            level.task.coinObj = 2000;
            level.task.totalCoin = 900;
            level.task.timeObj1 = 80;
            level.task.timeObj2 = 90;
            level.task.timeObj3 = 100;


            File file1 = new File("");
            String absolutePath = file1.getAbsolutePath();
            absolutePath += ("/Level" + level.levelNumber + ".json");
            Gson write = new GsonBuilder().setPrettyPrinting().create();
            FileWriter fileWriter = new FileWriter(absolutePath);
            write.toJson(level, fileWriter);
            fileWriter.flush();
            fileWriter.close();

        }

        public LevelDesign.Level readBasicLevelInfo(LevelDesign.Level level, int levelNumber) throws IOException {
            Gson gson = new Gson();
            File file1 = new File("");
            String absolutePath = file1.getAbsolutePath();
            absolutePath += ("/Level" + levelNumber + ".json");
            FileReader fileReader = new FileReader(absolutePath);
            level = gson.fromJson(fileReader, LevelDesign.Level.class);
            fileReader.close();
            return level;
        }

        public void printTasks(LevelDesign.Level level) {

            System.out.println(ANSI_PURPLE + "Level goals:" + ANSI_RESET);

            System.out.println(ANSI_PURPLE + "If you're a proffessional, best time to finish the level: " + level.task.timeObj1);
            System.out.println("Second time objective: " + level.task.timeObj2);
            System.out.println("Third time objective: " + level.task.timeObj3 + ANSI_RESET);

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

        public void printInfo(LevelDesign.Level level) {
            System.out.println(ANSI_CYAN + "Passed time: " + level.task.timeCounter + " time units");
            System.out.println("Your coins: " + level.coin + "$");
            System.out.println(ANSI_RED + "--------------------------------" + ANSI_RESET);
            System.out.print(ANSI_CYAN);
            for (int i = 1; i <= 6; i++) {
                for (int j = 1; j <= 6; j++) {
                    System.out.print(level.map.map[i - 1][j - 1].grass + " ");
                }
                System.out.println();
            }
            System.out.println(ANSI_RED + "--------------------------------" + ANSI_RESET);
            System.out.println(ANSI_CYAN + "Animals:");

            for (Animals.DomesticAnimals.Chicken chicken : level.chickens) {
                System.out.println("Chicken " + chicken.health + "% " + "[" + chicken.x + " " + chicken.y + "]");
            }
            for (Animals.DomesticAnimals.Turkey turkey : level.turkies) {
                System.out.println("Turkey " + turkey.health + "% " + "[" + turkey.x + " " + turkey.y + "]");
            }
            for (Animals.DomesticAnimals.Buffalo buffalo : level.buffalos) {
                System.out.println("Buffalo " + buffalo.health + "% " + "[" + buffalo.x + " " + buffalo.y + "]");
            }
            for (Animals.WildAnimals.Lion lion : level.lions) {
                System.out.println("Lion " + (lion.cageP - lion.cageCounter) + " [" + lion.x + " " + lion.y + "]");
            }
            for (Animals.WildAnimals.Bear bear : level.bears) {
                System.out.println("Bear " + (bear.cageP - bear.cageCounter) + " [" + bear.x + " " + bear.y + "]");
            }
            for (Animals.WildAnimals.Tiger tiger : level.tigers) {
                System.out.println("Tiger " + (tiger.cageP - tiger.cageCounter) + " [" + tiger.x + " " + tiger.y + "]");
            }
            for (Animals.SpecialAnimals.Cat cat : level.cats) {
                System.out.println("Cat " + " [" + cat.x + " " + cat.y + "]");
            }
            for (Animals.SpecialAnimals.Dog dog : level.dogs) {
                System.out.println("Dog " + " [" + dog.x + " " + dog.y + "]");
            }
            System.out.println(ANSI_RED + "--------------------------------" + ANSI_RESET);

            System.out.println(ANSI_CYAN + "Factories:");
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
            System.out.println(ANSI_RED + "--------------------------------" + ANSI_RESET);

            System.out.println(ANSI_CYAN + "Products: ");
            for (LevelDesign.Ingredient i : level.ingredients) {
                System.out.println(i.name + " [" + i.x + " " + i.y + "]");
            }
            System.out.println(ANSI_RED + "--------------------------------" + ANSI_RESET);

            System.out.println(ANSI_CYAN + "Tasks: ");

            if (!level.task.timeCheck1 && !level.task.timeCheck2 && !level.task.timeCheck3) {
                System.out.println("Time: " + level.task.timeCounter + "/" + level.task.timeObj1);
            }
            if (level.task.timeCheck1 && !level.task.timeCheck2 && !level.task.timeCheck3) {
                System.out.println("Time: " + level.task.timeCounter + "/" + level.task.timeObj2);
            }
            if (level.task.timeCheck1 && level.task.timeCheck2 && !level.task.timeCheck3) {
                System.out.println("Time: " + level.task.timeCounter + "/" + level.task.timeObj3);
            }
            if (level.task.timeCheck1 && level.task.timeCheck2 && level.task.timeCheck3) {
                System.out.println("Time: " + level.task.timeCounter + "/" + level.task.timeObj3);
            }

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
            if (level.task.milkObj != 10000) {
                System.out.println("Milks: " + level.task.milkCounter + "/" + level.task.milkObj);
            }
            if (level.task.cmilkObj != 10000) {
                System.out.println("Compact Milks: " + level.task.cmilkCounter + "/" + level.task.cmilkObj);
            }
            if (level.task.featherObj != 10000) {
                System.out.println("Feathers: " + level.task.featherCounter + "/" + level.task.featherObj);
            }
            if (level.task.coinObj != 10000) {
                System.out.println("Coins: " + level.task.totalCoin + "/" + level.task.coinObj);
            }
            System.out.println(ANSI_RED + "--------------------------------" + ANSI_RESET);


        }

        public void mapInitialize(LevelDesign.Level level) throws IOException {

            for (int i = 1; i <= 6; i++) {
                for (int j = 1; j <= 6; j++) {
                    level.map.map[i - 1][j - 1] = new LevelDesign.Cell(i - 1, j - 1);
                    level.map.map[i - 1][j - 1].grass = 0;
                }

            }
            logger("info", "map", level);

            save();
        }

        public void logger(String category, String type, LevelDesign.Level level) throws IOException {
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
                } else if (type.equals("buy cat")) {
                    log += ("[info] " + date + " Cat baught!\n");
                } else if (type.equals("buy dog")) {
                    log += ("[info] " + date + " Dog baught!\n");
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

        public void AnimalEater(LevelDesign.Level level) throws IOException {
            ArrayList<Animals.WildAnimals> tAnimals = new ArrayList<>();
            for (int i = 0; i < level.lions.size(); i++) {
                if (!level.lions.get(i).inCage) {
                    tAnimals.add(level.lions.get(i));
                }
            }
            for (int i = 0; i < level.bears.size(); i++) {
                if (!level.bears.get(i).inCage) {
                    tAnimals.add(level.bears.get(i));
                }
            }
            for (int i = 0; i < level.tigers.size(); i++) {
                if (!level.tigers.get(i).inCage) {
                    tAnimals.add(level.tigers.get(i));
                }
            }
            int e = level.chickens.size();
            for (int i = e - 1; i >= 0; i--) {
                for (int j = 0; j < tAnimals.size(); j++) {
                    if (tAnimals.get(j).x == level.chickens.get(i).x && tAnimals.get(j).y == level.chickens.get(i).y) {
                        level.chickens.remove(i);
                        break;
                    }
                }
            }
            e = level.buffalos.size();
            for (int i = e - 1; i >= 0; i--) {
                for (int j = 0; j < tAnimals.size(); j++) {
                    if (tAnimals.get(j).x == level.buffalos.get(i).x && tAnimals.get(j).y == level.buffalos.get(i).y) {
                        level.buffalos.remove(i);
                        break;
                    }
                }
            }
            e = level.turkies.size();
            for (int i = e - 1; i >= 0; i--) {
                for (int j = 0; j < tAnimals.size(); j++) {
                    if (tAnimals.get(j).x == level.turkies.get(i).x && tAnimals.get(j).y == level.turkies.get(i).y) {
                        level.turkies.remove(i);
                        break;
                    }
                }
            }
            e = level.ingredients.size();
            for (int i = e - 1; i >= 0; i--) {
                for (int j = 0; j < tAnimals.size(); j++) {
                    if (tAnimals.get(j).x == level.ingredients.get(i).x && tAnimals.get(j).y == level.ingredients.get(i).y)
                        level.ingredients.remove(i);
                    break;
                }
            }
            save();

        }

        public void RandomWildAnimalMove(LevelDesign.Level level) throws IOException {
            Random random = new Random();
            int a;
            for (int i = 0; i < level.lions.size(); i++) {
                if (!level.lions.get(i).inCage) {
                    if (level.lions.get(i).x > 1 && level.lions.get(i).x < 6 && level.lions.get(i).y > 1 && level.lions.get(i).y < 6) {
                        a = random.nextInt(4);
                        if (a == 0)
                            level.lions.get(i).y--;
                        if (a == 1)
                            level.lions.get(i).x++;
                        if (a == 2)
                            level.lions.get(i).y++;
                        if (a == 3)
                            level.lions.get(i).x--;
                    } else if (level.lions.get(i).x == 1 && level.lions.get(i).y > 1 && level.lions.get(i).y < 6) {
                        a = random.nextInt(3);
                        if (a == 0)
                            level.lions.get(i).y--;
                        if (a == 1)
                            level.lions.get(i).x++;
                        if (a == 2)
                            level.lions.get(i).y++;
                    } else if (level.lions.get(i).x == 6 && level.lions.get(i).y > 1 && level.lions.get(i).y < 6) {
                        a = random.nextInt(3);
                        if (a == 0)
                            level.lions.get(i).y--;
                        if (a == 1)
                            level.lions.get(i).x--;
                        if (a == 2)
                            level.lions.get(i).y++;
                    } else if (level.lions.get(i).x == 1 && level.lions.get(i).y == 1) {
                        a = random.nextInt(2);
                        if (a == 0)
                            level.lions.get(i).y++;
                        if (a == 1)
                            level.lions.get(i).x++;
                    } else if (level.lions.get(i).x == 1 && level.lions.get(i).y == 6) {
                        a = random.nextInt(2);
                        if (a == 0)
                            level.lions.get(i).y--;
                        if (a == 1)
                            level.lions.get(i).x++;
                    } else if (level.lions.get(i).y == 1 && level.lions.get(i).x > 1 && level.lions.get(i).x < 6) {
                        a = random.nextInt(3);
                        if (a == 0)
                            level.lions.get(i).x++;
                        if (a == 1)
                            level.lions.get(i).y++;
                        if (a == 2)
                            level.lions.get(i).x--;
                    } else if (level.lions.get(i).y == 6 && level.lions.get(i).x > 1 && level.lions.get(i).x < 6) {
                        a = random.nextInt(3);
                        if (a == 0)
                            level.lions.get(i).x++;
                        if (a == 1)
                            level.lions.get(i).y--;
                        if (a == 2)
                            level.lions.get(i).x--;
                    } else if (level.lions.get(i).x == 6 && level.lions.get(i).y == 1) {
                        a = random.nextInt(2);
                        if (a == 0)
                            level.lions.get(i).y++;
                        if (a == 1)
                            level.lions.get(i).x--;
                    } else if (level.lions.get(i).x == 6 && level.lions.get(i).y == 6) {
                        a = random.nextInt(2);
                        if (a == 0)
                            level.lions.get(i).y--;
                        if (a == 1)
                            level.lions.get(i).x--;
                    }
                }
            }

            for (int i = 0; i < level.bears.size(); i++) {
                if (!level.bears.get(i).inCage) {
                    if (level.bears.get(i).x > 1 && level.bears.get(i).x < 6 && level.bears.get(i).y > 1 && level.bears.get(i).y < 6) {
                        a = random.nextInt(4);
                        if (a == 0)
                            level.bears.get(i).y--;
                        if (a == 1)
                            level.bears.get(i).x++;
                        if (a == 2)
                            level.bears.get(i).y++;
                        if (a == 3)
                            level.bears.get(i).x--;
                    } else if (level.bears.get(i).x == 1 && level.bears.get(i).y > 1 && level.bears.get(i).y < 6) {
                        a = random.nextInt(3);
                        if (a == 0)
                            level.bears.get(i).y--;
                        if (a == 1)
                            level.bears.get(i).x++;
                        if (a == 2)
                            level.bears.get(i).y++;
                    } else if (level.bears.get(i).x == 6 && level.bears.get(i).y > 1 && level.bears.get(i).y < 6) {
                        a = random.nextInt(3);
                        if (a == 0)
                            level.bears.get(i).y--;
                        if (a == 1)
                            level.bears.get(i).x--;
                        if (a == 2)
                            level.bears.get(i).y++;
                    } else if (level.bears.get(i).x == 1 && level.bears.get(i).y == 1) {
                        a = random.nextInt(2);
                        if (a == 0)
                            level.bears.get(i).y++;
                        if (a == 1)
                            level.bears.get(i).x++;
                    } else if (level.bears.get(i).x == 1 && level.bears.get(i).y == 6) {
                        a = random.nextInt(2);
                        if (a == 0)
                            level.bears.get(i).y--;
                        if (a == 1)
                            level.bears.get(i).x++;
                    } else if (level.bears.get(i).y == 1 && level.bears.get(i).x > 1 && level.bears.get(i).x < 6) {
                        a = random.nextInt(3);
                        if (a == 0)
                            level.bears.get(i).x++;
                        if (a == 1)
                            level.bears.get(i).y++;
                        if (a == 2)
                            level.bears.get(i).x--;
                    } else if (level.bears.get(i).y == 6 && level.bears.get(i).x > 1 && level.bears.get(i).x < 6) {
                        a = random.nextInt(3);
                        if (a == 0)
                            level.bears.get(i).x++;
                        if (a == 1)
                            level.bears.get(i).y--;
                        if (a == 2)
                            level.bears.get(i).x--;
                    } else if (level.bears.get(i).x == 6 && level.bears.get(i).y == 1) {
                        a = random.nextInt(2);
                        if (a == 0)
                            level.bears.get(i).y++;
                        if (a == 1)
                            level.bears.get(i).x--;
                    } else if (level.bears.get(i).x == 6 && level.bears.get(i).y == 6) {
                        a = random.nextInt(2);
                        if (a == 0)
                            level.bears.get(i).y--;
                        if (a == 1)
                            level.bears.get(i).x--;
                    }
                }
            }

            for (int i = 0; i < level.tigers.size(); i++) {
                if (!level.tigers.get(i).inCage) {
                    if (level.tigers.get(i).x > 1 && level.tigers.get(i).x < 6 && level.tigers.get(i).y > 1 && level.tigers.get(i).y < 6) {
                        a = random.nextInt(4);
                        if (a == 0)
                            level.tigers.get(i).y--;
                        if (a == 1)
                            level.tigers.get(i).x++;
                        if (a == 2)
                            level.tigers.get(i).y++;
                        if (a == 3)
                            level.tigers.get(i).x--;
                    } else if (level.tigers.get(i).x == 1 && level.tigers.get(i).y > 1 && level.tigers.get(i).y < 6) {
                        a = random.nextInt(3);
                        if (a == 0)
                            level.tigers.get(i).y--;
                        if (a == 1)
                            level.tigers.get(i).x++;
                        if (a == 2)
                            level.tigers.get(i).y++;
                    } else if (level.tigers.get(i).x == 6 && level.tigers.get(i).y > 1 && level.tigers.get(i).y < 6) {
                        a = random.nextInt(3);
                        if (a == 0)
                            level.tigers.get(i).y--;
                        if (a == 1)
                            level.tigers.get(i).x--;
                        if (a == 2)
                            level.tigers.get(i).y++;
                    } else if (level.tigers.get(i).x == 1 && level.tigers.get(i).y == 1) {
                        a = random.nextInt(2);
                        if (a == 0)
                            level.tigers.get(i).y++;
                        if (a == 1)
                            level.tigers.get(i).x++;
                    } else if (level.tigers.get(i).x == 1 && level.tigers.get(i).y == 6) {
                        a = random.nextInt(2);
                        if (a == 0)
                            level.tigers.get(i).y--;
                        if (a == 1)
                            level.tigers.get(i).x++;
                    } else if (level.tigers.get(i).y == 1 && level.tigers.get(i).x > 1 && level.tigers.get(i).x < 6) {
                        a = random.nextInt(3);
                        if (a == 0)
                            level.tigers.get(i).x++;
                        if (a == 1)
                            level.tigers.get(i).y++;
                        if (a == 2)
                            level.tigers.get(i).x--;
                    } else if (level.tigers.get(i).y == 6 && level.tigers.get(i).x > 1 && level.tigers.get(i).x < 6) {
                        a = random.nextInt(3);
                        if (a == 0)
                            level.tigers.get(i).x++;
                        if (a == 1)
                            level.tigers.get(i).y--;
                        if (a == 2)
                            level.tigers.get(i).x--;
                    } else if (level.tigers.get(i).x == 6 && level.tigers.get(i).y == 1) {
                        a = random.nextInt(2);
                        if (a == 0)
                            level.tigers.get(i).y++;
                        if (a == 1)
                            level.tigers.get(i).x--;
                    } else if (level.tigers.get(i).x == 6 && level.tigers.get(i).y == 6) {
                        a = random.nextInt(2);
                        if (a == 0)
                            level.tigers.get(i).y--;
                        if (a == 1)
                            level.tigers.get(i).x--;
                    }
                    AnimalEater(level);
                    if (level.tigers.get(i).x > 1 && level.tigers.get(i).x < 6 && level.tigers.get(i).y > 1 && level.tigers.get(i).y < 6) {
                        a = random.nextInt(4);
                        if (a == 0)
                            level.tigers.get(i).y--;
                        if (a == 1)
                            level.tigers.get(i).x++;
                        if (a == 2)
                            level.tigers.get(i).y++;
                        if (a == 3)
                            level.tigers.get(i).x--;
                    } else if (level.tigers.get(i).x == 1 && level.tigers.get(i).y > 1 && level.tigers.get(i).y < 6) {
                        a = random.nextInt(3);
                        if (a == 0)
                            level.tigers.get(i).y--;
                        if (a == 1)
                            level.tigers.get(i).x++;
                        if (a == 2)
                            level.tigers.get(i).y++;
                    } else if (level.tigers.get(i).x == 6 && level.tigers.get(i).y > 1 && level.tigers.get(i).y < 6) {
                        a = random.nextInt(3);
                        if (a == 0)
                            level.tigers.get(i).y--;
                        if (a == 1)
                            level.tigers.get(i).x--;
                        if (a == 2)
                            level.tigers.get(i).y++;
                    } else if (level.tigers.get(i).x == 1 && level.tigers.get(i).y == 1) {
                        a = random.nextInt(2);
                        if (a == 0)
                            level.tigers.get(i).y++;
                        if (a == 1)
                            level.tigers.get(i).x++;
                    } else if (level.tigers.get(i).x == 1 && level.tigers.get(i).y == 6) {
                        a = random.nextInt(2);
                        if (a == 0)
                            level.tigers.get(i).y--;
                        if (a == 1)
                            level.tigers.get(i).x++;
                    } else if (level.tigers.get(i).y == 1 && level.tigers.get(i).x > 1 && level.tigers.get(i).x < 6) {
                        a = random.nextInt(3);
                        if (a == 0)
                            level.tigers.get(i).x++;
                        if (a == 1)
                            level.tigers.get(i).y++;
                        if (a == 2)
                            level.tigers.get(i).x--;
                    } else if (level.tigers.get(i).y == 6 && level.tigers.get(i).x > 1 && level.tigers.get(i).x < 6) {
                        a = random.nextInt(3);
                        if (a == 0)
                            level.tigers.get(i).x++;
                        if (a == 1)
                            level.tigers.get(i).y--;
                        if (a == 2)
                            level.tigers.get(i).x--;
                    } else if (level.tigers.get(i).x == 6 && level.tigers.get(i).y == 1) {
                        a = random.nextInt(2);
                        if (a == 0)
                            level.tigers.get(i).y++;
                        if (a == 1)
                            level.tigers.get(i).x--;
                    } else if (level.tigers.get(i).x == 6 && level.tigers.get(i).y == 6) {
                        a = random.nextInt(2);
                        if (a == 0)
                            level.tigers.get(i).y--;
                        if (a == 1)
                            level.tigers.get(i).x--;
                    }
                }
            }
            save();
        }

        public void UpgradeFactory(LevelDesign.Level level, String name) throws IOException {
            if (name.equals("weavefactory")) {
                if (level.coin >= level.weaveFactory.upgradePrice) {
                    if (level.weaveFactory.level == 1) {
                        level.coin -= level.weaveFactory.upgradePrice;
                        level.weaveFactory.level++;
                        ////
                    } else
                        System.out.println("This factory is on its maximum level!");
                } else {
                    System.out.println("Not enough coin to upgrade!");
                }
            } else if (name.equals("millfactory")) {
                if (level.coin >= level.millFactory.upgradePrice) {
                    if (level.millFactory.level == 1) {
                        level.coin -= level.millFactory.upgradePrice;
                        level.millFactory.level++;
                        ////
                    } else
                        System.out.println("This factory is on its maximum level!");
                } else {
                    System.out.println("Not enough coin to upgrade!");
                }
            } else if (name.equals("milkfactory")) {
                if (level.coin >= level.milkFactory.upgradePrice) {
                    if (level.milkFactory.level == 1) {
                        level.coin -= level.milkFactory.upgradePrice;
                        level.milkFactory.level++;
                        ////
                    } else
                        System.out.println("This factory is on its maximum level!");
                } else {
                    System.out.println("Not enough coin to upgrade!");
                }
            } else if (name.equals("bakery")) {
                if (level.coin >= level.bakery.upgradePrice) {
                    if (level.bakery.level == 1) {
                        level.coin -= level.bakery.upgradePrice;
                        level.bakery.level++;
                        ////
                    } else
                        System.out.println("This factory is in its maximum level!");
                } else {
                    System.out.println("Not enough coin to upgrade!");
                }
            } else if (name.equals("icefactory")) {
                if (level.coin >= level.iceFactory.upgradePrice) {
                    if (level.iceFactory.level == 1) {
                        level.coin -= level.iceFactory.upgradePrice;
                        level.iceFactory.level++;
                        ////
                    } else
                        System.out.println("This factory is in its maximum level!");
                } else {
                    System.out.println("Not enough coin to upgrade!");
                }
            } else if (name.equals("sewingfactory")) {
                if (level.coin >= level.sewingFactory.upgradePrice) {
                    if (level.sewingFactory.level == 1) {
                        level.coin -= level.sewingFactory.upgradePrice;
                        level.sewingFactory.level++;
                        ////
                    } else
                        System.out.println("This factory is in its maximum level!");
                } else {
                    System.out.println("Not enough coin to upgrade!");
                }
            } else
                System.out.println("Not a valid name.");
            save();
        }

        public LevelDesign.Cell ClosestGrass(LevelDesign.Level level, Animals.DomesticAnimals animal) {
            int min = 100000;
            int minIndexX = -1;
            int minIndexY = -1;
            int distance = 0;
            for (int i = 0; i < level.map.length; i++) {
                for (int j = 0; j < level.map.height; j++) {
                    if (level.map.map[i][j].grass > 0) {
                        distance = Math.abs(i + 1 - animal.x) + Math.abs(j + 1 - animal.y);
                        if (min > distance) {
                            min = distance;
                            minIndexX = i;
                            minIndexY = j;
                        }
                    }
                }
            }
            return level.map.map[minIndexX][minIndexY];
        }

        public LevelDesign.Cell ClosestIngredientC(LevelDesign.Level level, Animals.SpecialAnimals animal) {
            int min = 100000;
            int minIndexX = -1;
            int minIndexY = -1;
            int distance = 0;
            for (int i = 0; i < level.ingredients.size(); i++) {
                distance = Math.abs(level.ingredients.get(i).x - animal.x) + Math.abs(level.ingredients.get(i).y - animal.y);
                if (min > distance) {
                    min = distance;
                    minIndexX = level.ingredients.get(i).x;
                    minIndexY = level.ingredients.get(i).y;
                }
            }
            return level.map.map[minIndexX - 1][minIndexY - 1];
        }

        public void RandomDogMove(LevelDesign.Level level) {
            Random random = new Random();
            int a = 0;
            for (int i = 0; i < level.dogs.size(); i++) {
                if (level.dogs.get(i).x > 1 && level.dogs.get(i).x < 6 && level.dogs.get(i).y > 1 && level.dogs.get(i).y < 6) {
                    a = random.nextInt(4);
                    if (a == 0)
                        level.dogs.get(i).y--;
                    if (a == 1)
                        level.dogs.get(i).x++;
                    if (a == 2)
                        level.dogs.get(i).y++;
                    if (a == 3)
                        level.dogs.get(i).x--;
                } else if (level.dogs.get(i).x == 1 && level.dogs.get(i).y > 1 && level.dogs.get(i).y < 6) {
                    a = random.nextInt(3);
                    if (a == 0)
                        level.dogs.get(i).y--;
                    if (a == 1)
                        level.dogs.get(i).x++;
                    if (a == 2)
                        level.dogs.get(i).y++;
                } else if (level.dogs.get(i).x == 6 && level.dogs.get(i).y > 1 && level.dogs.get(i).y < 6) {
                    a = random.nextInt(3);
                    if (a == 0)
                        level.dogs.get(i).y--;
                    if (a == 1)
                        level.dogs.get(i).x--;
                    if (a == 2)
                        level.dogs.get(i).y++;
                } else if (level.dogs.get(i).x == 1 && level.dogs.get(i).y == 1) {
                    a = random.nextInt(2);
                    if (a == 0)
                        level.dogs.get(i).y++;
                    if (a == 1)
                        level.dogs.get(i).x++;
                } else if (level.dogs.get(i).x == 1 && level.dogs.get(i).y == 6) {
                    a = random.nextInt(2);
                    if (a == 0)
                        level.dogs.get(i).y--;
                    if (a == 1)
                        level.dogs.get(i).x++;
                } else if (level.dogs.get(i).y == 1 && level.dogs.get(i).x > 1 && level.dogs.get(i).x < 6) {
                    a = random.nextInt(3);
                    if (a == 0)
                        level.dogs.get(i).x++;
                    if (a == 1)
                        level.dogs.get(i).y++;
                    if (a == 2)
                        level.dogs.get(i).x--;
                } else if (level.dogs.get(i).y == 6 && level.dogs.get(i).x > 1 && level.dogs.get(i).x < 6) {
                    a = random.nextInt(3);
                    if (a == 0)
                        level.dogs.get(i).x++;
                    if (a == 1)
                        level.dogs.get(i).y--;
                    if (a == 2)
                        level.dogs.get(i).x--;
                } else if (level.dogs.get(i).x == 6 && level.dogs.get(i).y == 1) {
                    a = random.nextInt(2);
                    if (a == 0)
                        level.dogs.get(i).y++;
                    if (a == 1)
                        level.dogs.get(i).x--;
                } else if (level.dogs.get(i).x == 6 && level.dogs.get(i).y == 6) {
                    a = random.nextInt(2);
                    if (a == 0)
                        level.dogs.get(i).y--;
                    if (a == 1)
                        level.dogs.get(i).x--;
                }
            }
        }

        public void FactoryCounter(LevelDesign.Level level) throws IOException {
            Random random = new Random();
            int a = random.nextInt(6) + 1;
            int b = random.nextInt(6) + 1;
            if (level.sewingFactory.existence) {
                if (level.sewingFactory.productTime >= level.sewingFactory.maxDuration) {
                    if (level.sewingFactory.ingredientExistence) {
                        level.sewingFactory.productTime = -1;
                        level.sewingFactory.ingredientExistence = false;
                        LevelDesign.Ingredient.Cloth product = new LevelDesign.Ingredient.Cloth(a, b);
                        level.ingredients.add(product);
                    }
                    if (level.sewingFactory.ingredientExistence2) {
                        level.sewingFactory.productTime = -1;
                        level.sewingFactory.ingredientExistence2 = false;
                        LevelDesign.Ingredient.Cloth product = new LevelDesign.Ingredient.Cloth(a, b);
                        level.ingredients.add(product);
                        level.ingredients.add(product);
                    }
                } else if (level.sewingFactory.productTime > -1) {
                    if (level.sewingFactory.ingredientExistence) {
                        level.sewingFactory.productTime += level.sewingFactory.level;
                    }
                    if (level.sewingFactory.ingredientExistence2) {
                        level.sewingFactory.productTime++;
                    }
                }
            }

            if (level.millFactory.existence) {
                if (level.millFactory.productTime >= level.millFactory.maxDuration) {
                    if (level.millFactory.ingredientExistence) {
                        level.millFactory.productTime = -1;
                        level.millFactory.ingredientExistence = false;
                        level.millFactory.ingredientExistence2 = false;
                        LevelDesign.Ingredient.Flour product = new LevelDesign.Ingredient.Flour(a, b);
                        level.ingredients.add(product);
                    }
                    if (level.millFactory.ingredientExistence2) {
                        level.millFactory.productTime = -1;
                        level.millFactory.ingredientExistence2 = false;
                        level.millFactory.ingredientExistence = false;
                        LevelDesign.Ingredient.Flour product = new LevelDesign.Ingredient.Flour(a, b);
                        level.ingredients.add(product);
                        level.ingredients.add(product);
                    }
                } else if (level.millFactory.productTime > -1) {
                    if (level.millFactory.ingredientExistence) {
                        level.millFactory.productTime += level.millFactory.level;
                        System.out.println(" Line 2466 : " + level.millFactory.level);
                    }
                    if (level.millFactory.ingredientExistence2) {
                        level.millFactory.productTime++;
                    }
                }
            }

            if (level.milkFactory.existence) {
                if (level.milkFactory.productTime >= level.milkFactory.maxDuration) {
                    if (level.milkFactory.ingredientExistence) {
                        level.milkFactory.productTime = -1;
                        level.milkFactory.ingredientExistence = false;
                        LevelDesign.Ingredient.CMilk product = new LevelDesign.Ingredient.CMilk(a, b);
                        level.ingredients.add(product);
                    }
                    if (level.milkFactory.ingredientExistence2) {
                        level.milkFactory.productTime = -1;
                        level.milkFactory.ingredientExistence2 = false;
                        LevelDesign.Ingredient.CMilk product = new LevelDesign.Ingredient.CMilk(a, b);
                        level.ingredients.add(product);
                        level.ingredients.add(product);
                    }
                } else if (level.milkFactory.productTime > -1) {
                    if (level.milkFactory.ingredientExistence) {
                        level.milkFactory.productTime += level.milkFactory.level;
                    }
                    if (level.milkFactory.ingredientExistence2) {
                        level.milkFactory.productTime++;
                    }
                }
            }

            if (level.bakery.existence) {
                if (level.bakery.productTime >= level.bakery.maxDuration) {
                    if (level.bakery.ingredientExistence) {
                        level.bakery.productTime = -1;
                        level.bakery.ingredientExistence = false;
                        LevelDesign.Ingredient.Bread product = new LevelDesign.Ingredient.Bread(a, b);
                        level.ingredients.add(product);
                    }
                    if (level.bakery.ingredientExistence2) {
                        level.bakery.productTime = -1;
                        level.bakery.ingredientExistence2 = false;
                        LevelDesign.Ingredient.Bread product = new LevelDesign.Ingredient.Bread(a, b);
                        level.ingredients.add(product);
                        level.ingredients.add(product);
                    }
                } else if (level.bakery.productTime > -1) {
                    if (level.bakery.ingredientExistence) {
                        level.bakery.productTime += level.bakery.level;
                    }
                    if (level.bakery.ingredientExistence2) {
                        level.bakery.productTime++;
                    }
                }
            }

            if (level.weaveFactory.existence) {
                if (level.weaveFactory.productTime >= level.weaveFactory.maxDuration) {
                    if (level.weaveFactory.ingredientExistence) {
                        level.weaveFactory.productTime = -1;
                        level.weaveFactory.ingredientExistence = false;
                        LevelDesign.Ingredient.Weave product = new LevelDesign.Ingredient.Weave(a, b);
                        level.ingredients.add(product);
                    }
                    if (level.weaveFactory.ingredientExistence2) {
                        level.weaveFactory.productTime = -1;
                        level.weaveFactory.ingredientExistence2 = false;
                        LevelDesign.Ingredient.Weave product = new LevelDesign.Ingredient.Weave(a, b);
                        level.ingredients.add(product);
                        level.ingredients.add(product);
                    }
                } else if (level.weaveFactory.productTime > -1) {
                    if (level.weaveFactory.ingredientExistence) {
                        level.weaveFactory.productTime += level.weaveFactory.level;
                    }
                    if (level.weaveFactory.ingredientExistence2) {
                        level.weaveFactory.productTime++;
                    }
                }
            }

            if (level.iceFactory.existence) {
                if (level.iceFactory.productTime >= level.iceFactory.maxDuration) {
                    if (level.iceFactory.ingredientExistence) {
                        level.iceFactory.productTime = -1;
                        level.iceFactory.ingredientExistence = false;
                        LevelDesign.Ingredient.IceCream product = new LevelDesign.Ingredient.IceCream(a, b);
                        level.ingredients.add(product);
                    }
                    if (level.iceFactory.ingredientExistence2) {
                        level.iceFactory.productTime = -1;
                        level.iceFactory.ingredientExistence2 = false;
                        LevelDesign.Ingredient.IceCream product = new LevelDesign.Ingredient.IceCream(a, b);
                        level.ingredients.add(product);
                        level.ingredients.add(product);
                    }
                } else if (level.iceFactory.productTime > -1) {
                    if (level.iceFactory.ingredientExistence) {
                        level.iceFactory.productTime += level.iceFactory.level;
                    }
                    if (level.iceFactory.ingredientExistence2) {
                        level.iceFactory.productTime++;
                    }
                }
            }
            save();
        }

        public void Work(LevelDesign.Level level, String name) throws IOException {
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
                if (a == 0) {
                    if (level.weaveFactory.existence) {
                        if (level.weaveFactory.productTime == 0) {
                            if (level.weaveFactory.level == 1) {
                                for (int i = 0; i < level.storage.names.size(); i++) {
                                    if (level.storage.names.get(i).equals(level.weaveFactory.ingredient)) {
                                        t = 1;
                                        level.storage.names.remove(i);
                                        level.storage.quantities.remove(i);
                                        LevelDesign.Ingredient.Feather feather = new LevelDesign.Ingredient.Feather(1, 1);
                                        level.storage.capacity -= feather.size;
                                        level.weaveFactory.ingredientExistence = true;
                                        // Production
                                    }
                                }
                            }

                            if (level.weaveFactory.level == 2) {
                                int r = 0;
                                for (int i = 0; i < level.storage.names.size(); i++) {
                                    if (level.storage.names.get(i).equals(level.weaveFactory.ingredient)) {
                                        r++;
                                    }
                                }
                                if (r >= 2) {
                                    r = 2;
                                    int e = level.storage.names.size();
                                    for (int i = e - 1; i >= 0; i--) {
                                        if (level.storage.names.get(i).equals(level.weaveFactory.ingredient) && r > 0) {
                                            r--;
                                            t = 1;
                                            level.storage.names.remove(i);
                                            level.storage.quantities.remove(i);
                                            LevelDesign.Ingredient.Feather feather = new LevelDesign.Ingredient.Feather(1, 1);
                                            level.storage.capacity -= feather.size;
                                            level.weaveFactory.ingredientExistence2 = true;
                                        }
                                    }
                                }
                                if (r == 1) {
                                    int e = level.storage.names.size();
                                    for (int i = e - 1; i >= 0; i--) {
                                        if (level.storage.names.get(i).equals(level.weaveFactory.ingredient) && r != 0) {
                                            r--;
                                            t = 1;
                                            level.storage.names.remove(i);
                                            level.storage.quantities.remove(i);
                                            LevelDesign.Ingredient.Feather feather = new LevelDesign.Ingredient.Feather(1, 1);
                                            level.storage.capacity -= feather.size;
                                            level.weaveFactory.ingredientExistence = true;
                                        }
                                    }
                                }
                            }

                            if (t == 0)
                                System.out.println("There is not enough ingredient to start!");

                        } else if (level.weaveFactory.productTime > 0) {
                            System.out.println("The factory is working now, You must wait to end the production.");
                        }
                    } else {
                        System.out.println("You must first build the factory.");
                    }
                }

                if (a == 1) {
                    if (level.millFactory.existence) {
                        if (level.millFactory.productTime == 0) {
                            if (level.millFactory.level == 1) {
                                for (int i = 0; i < level.storage.names.size(); i++) {
                                    if (level.storage.names.get(i).equals(level.millFactory.ingredient)) {
                                        t = 1;
                                        level.storage.names.remove(i);
                                        level.storage.quantities.remove(i);
                                        LevelDesign.Ingredient.Egg feather = new LevelDesign.Ingredient.Egg(1, 1);
                                        level.storage.capacity -= feather.size;
                                        level.millFactory.ingredientExistence = true;
                                        level.millFactory.ingredientExistence2 = false;
                                        // Production
                                    }
                                }
                            }

                            if (level.millFactory.level == 2) {
                                int r = 0;
                                for (int i = 0; i < level.storage.names.size(); i++) {
                                    if (level.storage.names.get(i).equals(level.millFactory.ingredient)) {
                                        r++;
                                    }
                                }
                                if (r >= 2) {
                                    r = 2;
                                    int e = level.storage.names.size();
                                    for (int i = e - 1; i >= 0; i--) {
                                        if (level.storage.names.get(i).equals(level.millFactory.ingredient) && r > 0) {
                                            r--;
                                            t = 1;
                                            level.storage.names.remove(i);
                                            level.storage.quantities.remove(i);
                                            LevelDesign.Ingredient.Egg feather = new LevelDesign.Ingredient.Egg(1, 1);
                                            level.storage.capacity -= feather.size;
                                            level.millFactory.ingredientExistence2 = true;
                                            level.millFactory.ingredientExistence = false;
                                        }
                                    }
                                }
                                if (r == 1) {
                                    int e = level.storage.names.size();
                                    for (int i = e - 1; i >= 0; i--) {
                                        if (level.storage.names.get(i).equals(level.millFactory.ingredient) && r != 0) {
                                            r--;
                                            t = 1;
                                            level.storage.names.remove(i);
                                            level.storage.quantities.remove(i);
                                            LevelDesign.Ingredient.Egg feather = new LevelDesign.Ingredient.Egg(1, 1);
                                            level.storage.capacity -= feather.size;
                                            level.millFactory.ingredientExistence = true;
                                            level.millFactory.ingredientExistence2 = false;
                                        }
                                    }
                                }
                            }

                            if (t == 0)
                                System.out.println("There is not enough ingredient to start!");

                        } else if (level.millFactory.productTime > 0) {
                            System.out.println("The factory is working now, You must wait to end the production.");
                        }
                    } else {
                        System.out.println("You must first build the factory.");
                    }
                }

                if (a == 2) {
                    if (level.milkFactory.existence) {
                        if (level.milkFactory.productTime == 0) {
                            if (level.milkFactory.level == 1) {
                                for (int i = 0; i < level.storage.names.size(); i++) {
                                    if (level.storage.names.get(i).equals(level.milkFactory.ingredient)) {
                                        t = 1;
                                        level.storage.names.remove(i);
                                        level.storage.quantities.remove(i);
                                        LevelDesign.Ingredient.Milk feather = new LevelDesign.Ingredient.Milk(1, 1);
                                        level.storage.capacity -= feather.size;
                                        level.milkFactory.ingredientExistence = true;
                                        // Production
                                    }
                                }
                            }

                            if (level.milkFactory.level == 2) {
                                int r = 0;
                                for (int i = 0; i < level.storage.names.size(); i++) {
                                    if (level.storage.names.get(i).equals(level.milkFactory.ingredient)) {
                                        r++;
                                    }
                                }
                                if (r >= 2) {
                                    r = 2;
                                    int e = level.storage.names.size();
                                    for (int i = e - 1; i >= 0; i--) {
                                        if (level.storage.names.get(i).equals(level.milkFactory.ingredient) && r > 0) {
                                            r--;
                                            t = 1;
                                            level.storage.names.remove(i);
                                            level.storage.quantities.remove(i);
                                            LevelDesign.Ingredient.Milk feather = new LevelDesign.Ingredient.Milk(1, 1);
                                            level.storage.capacity -= feather.size;
                                            level.milkFactory.ingredientExistence2 = true;
                                        }
                                    }
                                }
                                if (r == 1) {
                                    int e = level.storage.names.size();
                                    for (int i = e - 1; i >= 0; i--) {
                                        if (level.storage.names.get(i).equals(level.milkFactory.ingredient) && r != 0) {
                                            r--;
                                            t = 1;
                                            level.storage.names.remove(i);
                                            level.storage.quantities.remove(i);
                                            LevelDesign.Ingredient.Milk feather = new LevelDesign.Ingredient.Milk(1, 1);
                                            level.storage.capacity -= feather.size;
                                            level.milkFactory.ingredientExistence = true;
                                        }
                                    }
                                }
                            }

                            if (t == 0)
                                System.out.println("There is not enough ingredient to start!");

                        } else if (level.milkFactory.productTime > 0) {
                            System.out.println("The factory is working now, You must wait to end the production.");
                        }
                    } else {
                        System.out.println("You must first build the factory.");
                    }
                }

                if (a == 3) {
                    if (level.bakery.existence) {
                        if (level.bakery.productTime == 0) {
                            if (level.bakery.level == 1) {
                                for (int i = 0; i < level.storage.names.size(); i++) {
                                    if (level.storage.names.get(i).equals(level.bakery.ingredient)) {
                                        t = 1;
                                        level.storage.names.remove(i);
                                        level.storage.quantities.remove(i);
                                        LevelDesign.Ingredient.Flour feather = new LevelDesign.Ingredient.Flour(1, 1);
                                        level.storage.capacity -= feather.size;
                                        level.bakery.ingredientExistence = true;
                                        // Production
                                    }
                                }
                            }

                            if (level.bakery.level == 2) {
                                int r = 0;
                                for (int i = 0; i < level.storage.names.size(); i++) {
                                    if (level.storage.names.get(i).equals(level.bakery.ingredient)) {
                                        r++;
                                    }
                                }
                                if (r >= 2) {
                                    r = 2;
                                    int e = level.storage.names.size();
                                    for (int i = e - 1; i >= 0; i--) {
                                        if (level.storage.names.get(i).equals(level.bakery.ingredient) && r > 0) {
                                            r--;
                                            t = 1;
                                            level.storage.names.remove(i);
                                            level.storage.quantities.remove(i);
                                            LevelDesign.Ingredient.Flour feather = new LevelDesign.Ingredient.Flour(1, 1);
                                            level.storage.capacity -= feather.size;
                                            level.bakery.ingredientExistence2 = true;
                                        }
                                    }
                                }
                                if (r == 1) {
                                    int e = level.storage.names.size();
                                    for (int i = e - 1; i >= 0; i--) {
                                        if (level.storage.names.get(i).equals(level.bakery.ingredient) && r != 0) {
                                            r--;
                                            t = 1;
                                            level.storage.names.remove(i);
                                            level.storage.quantities.remove(i);
                                            LevelDesign.Ingredient.Flour feather = new LevelDesign.Ingredient.Flour(1, 1);
                                            level.storage.capacity -= feather.size;
                                            level.bakery.ingredientExistence = true;
                                        }
                                    }
                                }
                            }

                            if (t == 0)
                                System.out.println("There is not enough ingredient to start!");

                        } else if (level.bakery.productTime > 0) {
                            System.out.println("The factory is working now, You must wait to end the production.");
                        }
                    } else {
                        System.out.println("You must first build the factory.");
                    }
                }

                if (a == 4) {
                    if (level.sewingFactory.existence) {
                        if (level.sewingFactory.productTime == 0) {
                            if (level.sewingFactory.level == 1) {
                                for (int i = 0; i < level.storage.names.size(); i++) {
                                    if (level.storage.names.get(i).equals(level.sewingFactory.ingredient)) {
                                        t = 1;
                                        level.storage.names.remove(i);
                                        level.storage.quantities.remove(i);
                                        LevelDesign.Ingredient.Weave feather = new LevelDesign.Ingredient.Weave(1, 1);
                                        level.storage.capacity -= feather.size;
                                        level.sewingFactory.ingredientExistence = true;
                                        // Production
                                    }
                                }
                            }

                            if (level.sewingFactory.level == 2) {
                                int r = 0;
                                for (int i = 0; i < level.storage.names.size(); i++) {
                                    if (level.storage.names.get(i).equals(level.sewingFactory.ingredient)) {
                                        r++;
                                    }
                                }
                                if (r >= 2) {
                                    r = 2;
                                    int e = level.storage.names.size();
                                    for (int i = e - 1; i >= 0; i--) {
                                        if (level.storage.names.get(i).equals(level.sewingFactory.ingredient) && r > 0) {
                                            r--;
                                            t = 1;
                                            level.storage.names.remove(i);
                                            level.storage.quantities.remove(i);
                                            LevelDesign.Ingredient.Weave feather = new LevelDesign.Ingredient.Weave(1, 1);
                                            level.storage.capacity -= feather.size;
                                            level.sewingFactory.ingredientExistence2 = true;
                                        }
                                    }
                                }
                                if (r == 1) {
                                    int e = level.storage.names.size();
                                    for (int i = e - 1; i >= 0; i--) {
                                        if (level.storage.names.get(i).equals(level.sewingFactory.ingredient) && r != 0) {
                                            r--;
                                            t = 1;
                                            level.storage.names.remove(i);
                                            level.storage.quantities.remove(i);
                                            LevelDesign.Ingredient.Weave feather = new LevelDesign.Ingredient.Weave(1, 1);
                                            level.storage.capacity -= feather.size;
                                            level.sewingFactory.ingredientExistence = true;
                                        }
                                    }
                                }
                            }

                            if (t == 0)
                                System.out.println("There is not enough ingredient to start!");

                        } else {
                            System.out.println("The factory is working now, You must wait to end the production.");
                        }
                    } else {
                        System.out.println("You must first build the factory.");
                    }
                }

                if (a == 5) {
                    if (level.iceFactory.existence) {
                        if (level.iceFactory.productTime == 0) {
                            if (level.iceFactory.level == 1) {
                                for (int i = 0; i < level.storage.names.size(); i++) {
                                    if (level.storage.names.get(i).equals(level.iceFactory.ingredient)) {
                                        t = 1;
                                        level.storage.names.remove(i);
                                        level.storage.quantities.remove(i);
                                        LevelDesign.Ingredient.CMilk feather = new LevelDesign.Ingredient.CMilk(1, 1);
                                        level.storage.capacity -= feather.size;
                                        level.iceFactory.ingredientExistence = true;
                                        // Production
                                    }
                                }
                            }

                            if (level.iceFactory.level == 2) {
                                int r = 0;
                                for (int i = 0; i < level.storage.names.size(); i++) {
                                    if (level.storage.names.get(i).equals(level.iceFactory.ingredient)) {
                                        r++;
                                    }
                                }
                                if (r >= 2) {
                                    r = 2;
                                    int e = level.storage.names.size();
                                    for (int i = e - 1; i >= 0; i--) {
                                        if (level.storage.names.get(i).equals(level.iceFactory.ingredient) && r > 0) {
                                            r--;
                                            t = 1;
                                            level.storage.names.remove(i);
                                            level.storage.quantities.remove(i);
                                            LevelDesign.Ingredient.CMilk feather = new LevelDesign.Ingredient.CMilk(1, 1);
                                            level.storage.capacity -= feather.size;
                                            level.iceFactory.ingredientExistence2 = true;
                                        }
                                    }
                                }
                                if (r == 1) {
                                    int e = level.storage.names.size();
                                    for (int i = e - 1; i >= 0; i--) {
                                        if (level.storage.names.get(i).equals(level.iceFactory.ingredient) && r != 0) {
                                            r--;
                                            t = 1;
                                            level.storage.names.remove(i);
                                            level.storage.quantities.remove(i);
                                            LevelDesign.Ingredient.CMilk feather = new LevelDesign.Ingredient.CMilk(1, 1);
                                            level.storage.capacity -= feather.size;
                                            level.iceFactory.ingredientExistence = true;
                                        }
                                    }
                                }
                            }

                            if (t == 0)
                                System.out.println("There is not enough ingredient to start!");

                        } else if (level.iceFactory.productTime > 0) {
                            System.out.println("The factory is working now, You must wait to end the production.");
                        }
                    } else {
                        System.out.println("You must first build the factory.");
                    }
                }
            }
        }

        public void RandomDomesticAnimalMove(LevelDesign.Level level) throws IOException {
            Random random = new Random();
            int a;
            for (int i = 0; i < level.chickens.size(); i++) {
                if (level.chickens.get(i).x > 1 && level.chickens.get(i).x < 6 && level.chickens.get(i).y > 1 && level.chickens.get(i).y < 6) {
                    a = random.nextInt(4);
                    if (a == 0)
                        level.chickens.get(i).y--;
                    if (a == 1)
                        level.chickens.get(i).x++;
                    if (a == 2)
                        level.chickens.get(i).y++;
                    if (a == 3)
                        level.chickens.get(i).x--;
                } else if (level.chickens.get(i).x == 1 && level.chickens.get(i).y > 1 && level.chickens.get(i).y < 6) {
                    a = random.nextInt(3);
                    if (a == 0)
                        level.chickens.get(i).y--;
                    if (a == 1)
                        level.chickens.get(i).x++;
                    if (a == 2)
                        level.chickens.get(i).y++;
                } else if (level.chickens.get(i).x == 6 && level.chickens.get(i).y > 1 && level.chickens.get(i).y < 6) {
                    a = random.nextInt(3);
                    if (a == 0)
                        level.chickens.get(i).y--;
                    if (a == 1)
                        level.chickens.get(i).x--;
                    if (a == 2)
                        level.chickens.get(i).y++;
                } else if (level.chickens.get(i).x == 1 && level.chickens.get(i).y == 1) {
                    a = random.nextInt(2);
                    if (a == 0)
                        level.chickens.get(i).y++;
                    if (a == 1)
                        level.chickens.get(i).x++;
                } else if (level.chickens.get(i).x == 1 && level.chickens.get(i).y == 6) {
                    a = random.nextInt(2);
                    if (a == 0)
                        level.chickens.get(i).y--;
                    if (a == 1)
                        level.chickens.get(i).x++;
                } else if (level.chickens.get(i).y == 1 && level.chickens.get(i).x > 1 && level.chickens.get(i).x < 6) {
                    a = random.nextInt(3);
                    if (a == 0)
                        level.chickens.get(i).x++;
                    if (a == 1)
                        level.chickens.get(i).y++;
                    if (a == 2)
                        level.chickens.get(i).x--;
                } else if (level.chickens.get(i).y == 6 && level.chickens.get(i).x > 1 && level.chickens.get(i).x < 6) {
                    a = random.nextInt(3);
                    if (a == 0)
                        level.chickens.get(i).x++;
                    if (a == 1)
                        level.chickens.get(i).y--;
                    if (a == 2)
                        level.chickens.get(i).x--;
                } else if (level.chickens.get(i).x == 6 && level.chickens.get(i).y == 1) {
                    a = random.nextInt(2);
                    if (a == 0)
                        level.chickens.get(i).y++;
                    if (a == 1)
                        level.chickens.get(i).x--;
                } else if (level.chickens.get(i).x == 6 && level.chickens.get(i).y == 6) {
                    a = random.nextInt(2);
                    if (a == 0)
                        level.chickens.get(i).y--;
                    if (a == 1)
                        level.chickens.get(i).x--;
                }
            }
            for (int i = 0; i < level.buffalos.size(); i++) {
                if (level.buffalos.get(i).x > 1 && level.buffalos.get(i).x < 6 && level.buffalos.get(i).y > 1 && level.buffalos.get(i).y < 6) {
                    a = random.nextInt(4);
                    if (a == 0)
                        level.buffalos.get(i).y--;
                    if (a == 1)
                        level.buffalos.get(i).x++;
                    if (a == 2)
                        level.buffalos.get(i).y++;
                    if (a == 3)
                        level.buffalos.get(i).x--;
                } else if (level.buffalos.get(i).x == 1 && level.buffalos.get(i).y > 1 && level.buffalos.get(i).y < 6) {
                    a = random.nextInt(3);
                    if (a == 0)
                        level.buffalos.get(i).y--;
                    if (a == 1)
                        level.buffalos.get(i).x++;
                    if (a == 2)
                        level.buffalos.get(i).y++;
                } else if (level.buffalos.get(i).x == 6 && level.buffalos.get(i).y > 1 && level.buffalos.get(i).y < 6) {
                    a = random.nextInt(3);
                    if (a == 0)
                        level.buffalos.get(i).y--;
                    if (a == 1)
                        level.buffalos.get(i).x--;
                    if (a == 2)
                        level.buffalos.get(i).y++;
                } else if (level.buffalos.get(i).x == 1 && level.buffalos.get(i).y == 1) {
                    a = random.nextInt(2);
                    if (a == 0)
                        level.buffalos.get(i).y++;
                    if (a == 1)
                        level.buffalos.get(i).x++;
                } else if (level.buffalos.get(i).x == 1 && level.buffalos.get(i).y == 6) {
                    a = random.nextInt(2);
                    if (a == 0)
                        level.buffalos.get(i).y--;
                    if (a == 1)
                        level.buffalos.get(i).x++;
                } else if (level.buffalos.get(i).y == 1 && level.buffalos.get(i).x > 1 && level.buffalos.get(i).x < 6) {
                    a = random.nextInt(3);
                    if (a == 0)
                        level.buffalos.get(i).x++;
                    if (a == 1)
                        level.buffalos.get(i).y++;
                    if (a == 2)
                        level.buffalos.get(i).x--;
                } else if (level.buffalos.get(i).y == 6 && level.buffalos.get(i).x > 1 && level.buffalos.get(i).x < 6) {
                    a = random.nextInt(3);
                    if (a == 0)
                        level.buffalos.get(i).x++;
                    if (a == 1)
                        level.buffalos.get(i).y--;
                    if (a == 2)
                        level.buffalos.get(i).x--;
                } else if (level.buffalos.get(i).x == 6 && level.buffalos.get(i).y == 1) {
                    a = random.nextInt(2);
                    if (a == 0)
                        level.buffalos.get(i).y++;
                    if (a == 1)
                        level.buffalos.get(i).x--;
                } else if (level.buffalos.get(i).x == 6 && level.buffalos.get(i).y == 6) {
                    a = random.nextInt(2);
                    if (a == 0)
                        level.buffalos.get(i).y--;
                    if (a == 1)
                        level.buffalos.get(i).x--;
                }
            }
            for (int i = 0; i < level.turkies.size(); i++) {
                if (level.turkies.get(i).x > 1 && level.turkies.get(i).x < 6 && level.turkies.get(i).y > 1 && level.turkies.get(i).y < 6) {
                    a = random.nextInt(4);
                    if (a == 0)
                        level.turkies.get(i).y--;
                    if (a == 1)
                        level.turkies.get(i).x++;
                    if (a == 2)
                        level.turkies.get(i).y++;
                    if (a == 3)
                        level.turkies.get(i).x--;
                } else if (level.turkies.get(i).x == 1 && level.turkies.get(i).y > 1 && level.turkies.get(i).y < 6) {
                    a = random.nextInt(3);
                    if (a == 0)
                        level.turkies.get(i).y--;
                    if (a == 1)
                        level.turkies.get(i).x++;
                    if (a == 2)
                        level.turkies.get(i).y++;
                } else if (level.turkies.get(i).x == 6 && level.turkies.get(i).y > 1 && level.turkies.get(i).y < 6) {
                    a = random.nextInt(3);
                    if (a == 0)
                        level.turkies.get(i).y--;
                    if (a == 1)
                        level.turkies.get(i).x--;
                    if (a == 2)
                        level.turkies.get(i).y++;
                } else if (level.turkies.get(i).x == 1 && level.turkies.get(i).y == 1) {
                    a = random.nextInt(2);
                    if (a == 0)
                        level.turkies.get(i).y++;
                    if (a == 1)
                        level.turkies.get(i).x++;
                } else if (level.turkies.get(i).x == 1 && level.turkies.get(i).y == 6) {
                    a = random.nextInt(2);
                    if (a == 0)
                        level.turkies.get(i).y--;
                    if (a == 1)
                        level.turkies.get(i).x++;
                } else if (level.turkies.get(i).y == 1 && level.turkies.get(i).x > 1 && level.turkies.get(i).x < 6) {
                    a = random.nextInt(3);
                    if (a == 0)
                        level.turkies.get(i).x++;
                    if (a == 1)
                        level.turkies.get(i).y++;
                    if (a == 2)
                        level.turkies.get(i).x--;
                } else if (level.turkies.get(i).y == 6 && level.turkies.get(i).x > 1 && level.turkies.get(i).x < 6) {
                    a = random.nextInt(3);
                    if (a == 0)
                        level.turkies.get(i).x++;
                    if (a == 1)
                        level.turkies.get(i).y--;
                    if (a == 2)
                        level.turkies.get(i).x--;
                } else if (level.turkies.get(i).x == 6 && level.turkies.get(i).y == 1) {
                    a = random.nextInt(2);
                    if (a == 0)
                        level.turkies.get(i).y++;
                    if (a == 1)
                        level.turkies.get(i).x--;
                } else if (level.turkies.get(i).x == 6 && level.turkies.get(i).y == 6) {
                    a = random.nextInt(2);
                    if (a == 0)
                        level.turkies.get(i).y--;
                    if (a == 1)
                        level.turkies.get(i).x--;
                }
            }
            save();
        }

        public void RandomCatMove(LevelDesign.Level level) {
            int a = 0;
            Random random = new Random();
            for (int i = 0; i < level.cats.size(); i++) {
                if (level.cats.get(i).x > 1 && level.cats.get(i).x < 6 && level.cats.get(i).y > 1 && level.cats.get(i).y < 6) {
                    a = random.nextInt(4);
                    if (a == 0)
                        level.cats.get(i).y--;
                    if (a == 1)
                        level.cats.get(i).x++;
                    if (a == 2)
                        level.cats.get(i).y++;
                    if (a == 3)
                        level.cats.get(i).x--;
                } else if (level.cats.get(i).x == 1 && level.cats.get(i).y > 1 && level.cats.get(i).y < 6) {
                    a = random.nextInt(3);
                    if (a == 0)
                        level.cats.get(i).y--;
                    if (a == 1)
                        level.cats.get(i).x++;
                    if (a == 2)
                        level.cats.get(i).y++;
                } else if (level.cats.get(i).x == 6 && level.cats.get(i).y > 1 && level.cats.get(i).y < 6) {
                    a = random.nextInt(3);
                    if (a == 0)
                        level.cats.get(i).y--;
                    if (a == 1)
                        level.cats.get(i).x--;
                    if (a == 2)
                        level.cats.get(i).y++;
                } else if (level.cats.get(i).x == 1 && level.cats.get(i).y == 1) {
                    a = random.nextInt(2);
                    if (a == 0)
                        level.cats.get(i).y++;
                    if (a == 1)
                        level.cats.get(i).x++;
                } else if (level.cats.get(i).x == 1 && level.cats.get(i).y == 6) {
                    a = random.nextInt(2);
                    if (a == 0)
                        level.cats.get(i).y--;
                    if (a == 1)
                        level.cats.get(i).x++;
                } else if (level.cats.get(i).y == 1 && level.cats.get(i).x > 1 && level.cats.get(i).x < 6) {
                    a = random.nextInt(3);
                    if (a == 0)
                        level.cats.get(i).x++;
                    if (a == 1)
                        level.cats.get(i).y++;
                    if (a == 2)
                        level.cats.get(i).x--;
                } else if (level.cats.get(i).y == 6 && level.cats.get(i).x > 1 && level.cats.get(i).x < 6) {
                    a = random.nextInt(3);
                    if (a == 0)
                        level.cats.get(i).x++;
                    if (a == 1)
                        level.cats.get(i).y--;
                    if (a == 2)
                        level.cats.get(i).x--;
                } else if (level.cats.get(i).x == 6 && level.cats.get(i).y == 1) {
                    a = random.nextInt(2);
                    if (a == 0)
                        level.cats.get(i).y++;
                    if (a == 1)
                        level.cats.get(i).x--;
                } else if (level.cats.get(i).x == 6 && level.cats.get(i).y == 6) {
                    a = random.nextInt(2);
                    if (a == 0)
                        level.cats.get(i).y--;
                    if (a == 1)
                        level.cats.get(i).x--;
                }
            }
        }

        public void ProcessedAnimalMove(LevelDesign.Level level) throws IOException {
            int t = 0;
            for (int i = 0; i < level.map.length; i++) {
                for (int j = 0; j < level.map.height; j++) {
                    if (level.map.map[i][j].grass > 0) {
                        t = 1;
                        break;
                    }
                }
                if (t == 1)
                    break;
            }
            if (t == 1) {
                for (int i = 0; i < level.chickens.size(); i++) {
                    LevelDesign.Cell closestCell = ClosestGrass(level, level.chickens.get(i));
                    if (level.chickens.get(i).x > closestCell.x + 1) {
                        level.chickens.get(i).x--;
                    } else if (level.chickens.get(i).x < closestCell.x + 1) {
                        level.chickens.get(i).x++;
                    } else if (level.chickens.get(i).x == closestCell.x + 1) {
                        if (level.chickens.get(i).y > closestCell.y + 1) {
                            level.chickens.get(i).y--;
                        } else if (level.chickens.get(i).y < closestCell.y + 1) {
                            level.chickens.get(i).y++;
                        }
                    }
                }
                for (int i = 0; i < level.buffalos.size(); i++) {
                    LevelDesign.Cell closestCell = ClosestGrass(level, level.buffalos.get(i));
                    if (level.buffalos.get(i).x > closestCell.x + 1) {
                        level.buffalos.get(i).x--;
                    } else if (level.buffalos.get(i).x < closestCell.x + 1) {
                        level.buffalos.get(i).x++;
                    } else if (level.buffalos.get(i).x == closestCell.x + 1) {
                        if (level.buffalos.get(i).y > closestCell.y + 1) {
                            level.buffalos.get(i).y--;
                        } else if (level.buffalos.get(i).y < closestCell.y + 1) {
                            level.buffalos.get(i).y++;
                        }
                    }
                }
                for (int i = 0; i < level.turkies.size(); i++) {
                    LevelDesign.Cell closestCell = ClosestGrass(level, level.turkies.get(i));
                    if (level.turkies.get(i).x > closestCell.x + 1) {
                        level.turkies.get(i).x--;
                    } else if (level.turkies.get(i).x < closestCell.x + 1) {
                        level.turkies.get(i).x++;
                    } else if (level.turkies.get(i).x == closestCell.x + 1) {
                        if (level.turkies.get(i).y > closestCell.y + 1) {
                            level.turkies.get(i).y--;
                        } else if (level.turkies.get(i).y < closestCell.y + 1) {
                            level.turkies.get(i).y++;
                        }
                    }
                }
            }
            save();
        }

        public void ProcessedCatMove(LevelDesign.Level level) {
            for (int i = 0; i < level.cats.size(); i++) {
                LevelDesign.Cell closestCell = ClosestIngredientC(level, level.cats.get(i));
                if (level.cats.get(i).x > closestCell.x + 1) {
                    level.cats.get(i).x--;
                } else if (level.cats.get(i).x < closestCell.x + 1) {
                    level.cats.get(i).x++;
                } else if (level.cats.get(i).x == closestCell.x + 1) {
                    if (level.cats.get(i).y > closestCell.y + 1) {
                        level.cats.get(i).y--;
                    } else if (level.cats.get(i).y < closestCell.y + 1) {
                        level.cats.get(i).y++;
                    }
                }
            }
        }

        public void TotalMove(LevelDesign.Level level) throws IOException {
            int t = 0;
            for (int i = 0; i < level.map.length; i++) {
                for (int j = 0; j < level.map.height; j++) {
                    if (level.map.map[i][j].grass > 0) {
                        t = 1;
                        break;
                    }
                }
                if (t == 1)
                    break;
            }
            if (t == 0) {
                RandomDomesticAnimalMove(level);
                RandomWildAnimalMove(level);
                RandomDogMove(level);
            }
            if (t == 1) {
                ProcessedAnimalMove(level);
                RandomWildAnimalMove(level);
                RandomDogMove(level);
            }
            if (level.ingredients.size() > 0) {
                ProcessedCatMove(level);
            }
            if (level.ingredients.size() == 0) {
                RandomCatMove(level);
            }
        }

        public void MotorLoad(LevelDesign.Level level, String name) throws IOException {
            LevelDesign.Ingredient ingr = StringToIngr(name);
            Animals.WildAnimals wild = StringToAnimal(name);
            Animals.DomesticAnimals dom = StringToAnimalDomestic(name);
            int t = 0;
            if (level.motorCycle.counter == -1) {
                if (ingr != null) {
                    int e = level.storage.names.size();
                    for (int i = e - 1; i >= 0; i--) {
                        if (level.storage.names.get(i).equals(name)) {
                            t = 1;
                            if (level.motorCycle.capacity >= ingr.size) {
                                level.motorCycle.capacity -= ingr.size;
                                level.storage.names.remove(i);
                                level.storage.quantities.remove(i);
                                level.storage.capacity += ingr.size;
                                level.motorCycle.names.add(name);
                                level.motorCycle.quantities.add(1);
                                level.motorCycle.coin += ingr.price;
                                System.out.println(name + " loaded successfully!");
                            } else {
                                System.out.println("There is not enough space in the truck!");
                            }
                            break;
                        }
                    }
                    if (t == 0)
                        System.out.println("There is not such an ingredient.");
                } else if (wild != null) {
                    int e = level.storage.names.size();
                    for (int i = e - 1; i >= 0; i--) {
                        if (level.storage.names.get(i).equals(name)) {
                            t = 1;
                            if (level.motorCycle.capacity >= wild.capacity) {
                                level.motorCycle.capacity -= wild.capacity;
                                level.storage.names.remove(i);
                                level.storage.quantities.remove(i);
                                level.storage.capacity += wild.capacity;
                                level.motorCycle.names.add(name);
                                level.motorCycle.quantities.add(1);
                                level.motorCycle.coin += wild.sellPrice;
                                System.out.println(name + " loaded successfully!");
                            } else {
                                System.out.println("There is not enough space in the truck!");
                            }
                            break;
                        }
                    }
                    if (t == 0)
                        System.out.println("There is not such an animal.");
                } else if (dom != null) {
                    if (name.equals("chicken")) {
                        int e = level.chickens.size();
                        for (int i = e - 1; i >= 0; i--) {
                            if (true) {
                                t = 1;
                                if (level.motorCycle.capacity >= dom.capacity) {
                                    level.motorCycle.capacity -= dom.capacity;
                                    level.chickens.remove(i);
                                    level.motorCycle.names.add(name);
                                    level.motorCycle.quantities.add(1);
                                    level.motorCycle.coin += dom.sellPrice;
                                    System.out.println(name + " loaded successfully!");
                                } else {
                                    System.out.println("There is not enough space in the truck!");
                                }
                                break;
                            }
                        }
                    }
                    if (name.equals("buffalo")) {
                        int e = level.buffalos.size();
                        for (int i = e - 1; i >= 0; i--) {
                            if (true) {
                                t = 1;
                                if (level.motorCycle.capacity >= dom.capacity) {
                                    level.motorCycle.capacity -= dom.capacity;
                                    level.buffalos.remove(i);
                                    level.motorCycle.names.add(name);
                                    level.motorCycle.quantities.add(1);
                                    level.motorCycle.coin += dom.sellPrice;
                                    System.out.println(name + " loaded successfully!");
                                } else {
                                    System.out.println("There is not enough space in the truck!");
                                }
                                break;
                            }
                        }
                    }
                    if (name.equals("turkey")) {
                        int e = level.turkies.size();
                        for (int i = e - 1; i >= 0; i--) {
                            if (true) {
                                t = 1;
                                if (level.motorCycle.capacity >= dom.capacity) {
                                    level.motorCycle.capacity -= dom.capacity;
                                    level.turkies.remove(i);
                                    level.motorCycle.names.add(name);
                                    level.motorCycle.quantities.add(1);
                                    level.motorCycle.coin += dom.sellPrice;
                                    System.out.println(name + " loaded successfully!");
                                } else {
                                    System.out.println("There is not enough space in the truck!");
                                }
                                break;
                            }
                        }
                    }

                    if (t == 0)
                        System.out.println("There is not such an animal.");
                }
                if (ingr == null && wild == null && dom == null)
                    System.out.println("Please enter a valid name.");
            } else
                System.out.println("Truck is on the way!");
            save();
        }

        public void MotorUnload(LevelDesign.Level level, String name) throws IOException {
            LevelDesign.Ingredient ingr = StringToIngr(name);
            Animals.WildAnimals wild = StringToAnimal(name);
            Animals.DomesticAnimals dom = StringToAnimalDomestic(name);
            int t = 0;
            if (level.motorCycle.counter == -1) {
                if (ingr != null) {
                    int e = level.motorCycle.names.size();
                    for (int i = e - 1; i >= 0; i--) {
                        if (level.motorCycle.names.get(i).equals(name)) {
                            if (level.storage.capacity >= ingr.size) {
                                t = 1;
                                level.motorCycle.names.remove(i);
                                level.motorCycle.quantities.remove(i);
                                level.motorCycle.capacity += ingr.size;
                                level.storage.capacity -= ingr.size;
                                level.storage.names.add(name);
                                level.storage.quantities.add(1);
                                level.motorCycle.coin -= ingr.price;
                                System.out.println(name + " unloaded successfully!");
                            } else {
                                System.out.println("There is not enough space in the storage.");
                            }
                        }
                    }
                    if (t == 0) {
                        System.out.println("There is not such an ingredient.");
                    }
                } else if (wild != null) {
                    int e = level.motorCycle.names.size();
                    for (int i = e - 1; i >= 0; i--) {
                        if (level.motorCycle.names.get(i).equals(name)) {
                            if (level.storage.capacity >= wild.capacity) {
                                t = 1;
                                level.motorCycle.names.remove(i);
                                level.motorCycle.quantities.remove(i);
                                level.motorCycle.capacity += wild.capacity;
                                level.storage.capacity -= wild.capacity;
                                level.storage.names.add(name);
                                level.storage.quantities.add(1);
                                level.motorCycle.coin -= wild.sellPrice;
                                System.out.println(name + " unloaded successfully!");
                            } else {
                                System.out.println("There is not enough space in the storage.");
                            }
                        }
                    }
                    if (t == 0) {
                        System.out.println("There is not such an animal.");
                    }
                } else if (dom != null) {
                    if (name.equals("chicken")) {
                        int e = level.motorCycle.names.size();
                        for (int i = e - 1; i >= 0; i--) {
                            if (level.motorCycle.names.get(i).equals(name)) {
                                if (level.storage.capacity >= dom.capacity) {
                                    t = 1;
                                    level.motorCycle.names.remove(i);
                                    level.motorCycle.quantities.remove(i);
                                    level.motorCycle.capacity += dom.capacity;
                                    level.chickens.add(new Animals.DomesticAnimals.Chicken(1, 1));
                                    level.motorCycle.coin -= dom.sellPrice;
                                    System.out.println(name + " unloaded successfully!");
                                } else {
                                    System.out.println("There is not enough space in the storage.");
                                }
                            }
                        }
                    }
                    if (name.equals("buffalo")) {
                        int e = level.motorCycle.names.size();
                        for (int i = e - 1; i >= 0; i--) {
                            if (level.motorCycle.names.get(i).equals(name)) {
                                if (level.storage.capacity >= dom.capacity) {
                                    t = 1;
                                    level.motorCycle.names.remove(i);
                                    level.motorCycle.quantities.remove(i);
                                    level.motorCycle.capacity += dom.capacity;
                                    level.buffalos.add(new Animals.DomesticAnimals.Buffalo(1, 1));
                                    level.motorCycle.coin -= dom.sellPrice;
                                    System.out.println(name + " unloaded successfully!");
                                } else {
                                    System.out.println("There is not enough space in the storage.");
                                }
                            }
                        }
                    }
                    if (name.equals("turkey")) {
                        int e = level.motorCycle.names.size();
                        for (int i = e - 1; i >= 0; i--) {
                            if (level.motorCycle.names.get(i).equals(name)) {
                                if (level.storage.capacity >= dom.capacity) {
                                    t = 1;
                                    level.motorCycle.names.remove(i);
                                    level.motorCycle.quantities.remove(i);
                                    level.motorCycle.capacity += dom.capacity;
                                    level.turkies.add(new Animals.DomesticAnimals.Turkey(1, 1));
                                    level.motorCycle.coin -= dom.sellPrice;
                                    System.out.println(name + " unloaded successfully!");
                                } else {
                                    System.out.println("There is not enough space in the storage.");
                                }
                            }
                        }
                    }
                    if (t == 0) {
                        System.out.println("There is not such an animal.");
                    }
                }
                if (ingr == null && wild == null && dom == null)
                    System.out.println("Please enter a valid name.");
            } else
                System.out.println("Truck is on the way!");
            save();
        }

        public void MotorStart(LevelDesign.Level level) throws IOException {
            if (level.motorCycle.counter >= level.motorCycle.Max) {
                level.motorCycle.counter = -1;
                level.motorCycle.capacity = 10;
                level.motorCycle.names.clear();
                level.motorCycle.quantities.clear();
                level.coin += level.motorCycle.coin;
                level.task.totalCoin += level.motorCycle.coin;
            } else if (level.motorCycle.counter >= 0)
                level.motorCycle.counter++;
            // check kardan por boodan truck
            save();

        }

        public Animals.DomesticAnimals StringToAnimalDomestic(String name) throws IOException {
            if (name.equals("chicken")) {
                save();
                return new Animals.DomesticAnimals.Chicken(1, 1);
            }
            if (name.equals("turkey")) {
                save();
                return new Animals.DomesticAnimals.Turkey(1, 1);
            }
            if (name.equals("buffalo")) {
                save();
                return new Animals.DomesticAnimals.Buffalo(1, 1);
            } else
                return null;
        }

        public void turn(LevelDesign.Level playerLevel, int turnCounter) throws IOException {
            for (int i = 0; i < turnCounter; i++) {
                playerLevel.task.timeCounter++;
                timeTaskchecker(playerLevel);
                TotalMove(playerLevel);
                MotorStart(playerLevel);
                FactoryCounter(playerLevel);
                Expirings(playerLevel);
                Well(playerLevel);
                NeededFiller(playerLevel, AnimalHealth(playerLevel));
                AnimalCounter(playerLevel);
                wildAnimalAddTimeChecker(playerLevel);
                AnimalEater(playerLevel);
                DogAction(playerLevel);
                CatAction(playerLevel);
                CageCounter(playerLevel);
                CageStore(playerLevel);
                InCageCounter(playerLevel);
                CageEnd(playerLevel);
                if (tasksChecker(playerLevel)) {
                    playerLevel.TaskCheck = true;
                }

            }
            save();
            if (!playerLevel.TaskCheck) {
                printInfo(playerLevel);
            }
            Date date = new Date();
            log += ("[Info] " + date + turnCounter + "Time units passed.\n");
            logWriter();
        }


    }


    public class Graphics.InputProcessor {
        public static final String ANSI_RED = "\u001B[31m";
        public static final String ANSI_RESET = "\u001B[0m";
        public static final String ANSI_CYAN = "\u001B[36m";
        public static final String ANSI_YELLOW = "\u001B[33m";

        Graphics.Manager manager = new Graphics.Manager();

        public Graphics.InputProcessor(Graphics.Manager manager) {

            this.manager = manager;
        }

        private boolean usernameCheckProcess(String username) throws IOException {
            return manager.usernameCheck(username);

        }

        private boolean loginProcess(String username, String password) throws IOException {
            return manager.login(username, password);

        }

        private void signupProcess(String username, String password) throws IOException {
            manager.signUp(username, password);

        }

        private boolean startProcess(String username, int levelNumber) throws IOException {
            return manager.levelCheck(username, levelNumber);

        }

        private void settingsProcess() {

        }

        private Level levelReturnerProcess(int levelNumber) throws IOException {
            saveProcess();
            return manager.levelReturner(levelNumber);

        }

        private void buyProcess(String name, Level level) throws IOException {
            manager.Buy(name, level);
            saveProcess();

        }

        private void pickupProcess(int x, int y, Level level) throws IOException {
            manager.PickUp(x, y, level);
            saveProcess();

        }

        private void wellProcess(Level level) throws IOException {
            manager.Well(level);
            saveProcess();

        }

        private void plantProcess(int x, int y, Level level) throws IOException {
            manager.Plant(x, y, level);
            saveProcess();

        }

        private void buildProcess(Level level, String name) throws IOException {

            manager.Build(level, name);
            saveProcess();
        }

        private void workProcess(String name, Level level) throws IOException {
            manager.Work(level, name);
            saveProcess();

        }

        private void cageProcess(int x, int y, Level level) throws IOException {
            manager.Cage(x, y, level);
            saveProcess();

        }

        private void turnProcess(Level playerLevel, int turnCounter) throws IOException {

            manager.turn(playerLevel, turnCounter);
        }

        private void truckLoadProcess(Level level, String name) throws IOException {
            manager.MotorLoad(level, name);
            saveProcess();

        }

        private void truckUnloadProcess(Level level, String name) throws IOException {
            manager.MotorUnload(level, name);
            saveProcess();

        }

        private void truckGoProcess() throws IOException {
            saveProcess();

        }

        private void saveProcess() throws IOException {
            manager.save();
        }

        private void printInfoProcess(Level level) {

            manager.printInfo(level);
        }

        private void mapInitializeProcess(Level level) throws IOException {

            manager.mapInitialize(level);
        }

        private void upgradeFactoryProcess(Level level, String name) throws IOException {

            manager.UpgradeFactory(level, name);
        }

        boolean flag_logout = true, flag_endgame = true, flag_end1 = true;
        Level playerLevel;
        String username;


        public void Graphics.Menu() throws IOException {

            while (true) {
                while (flag_logout) {
                    Scanner scanner = new Scanner(System.in);
                    String Command;
                    boolean flag2 = false;

                    if (flag_endgame) {
                        System.out.println("Please enter your username: ");
                        username = scanner.nextLine();
                        manager.logger("info", "username entered", playerLevel);

                        while (true) {
                            Command = scanner.nextLine();
                            String command = Command.toLowerCase();
                            String[] split = command.split("\\s+");
                            boolean bool = false;

                            if (command.equals("log in")) {
                                if (!usernameCheckProcess(username)) {
                                    System.out.println(ANSI_RED + "Error! because there is no such account." + ANSI_RESET);
                                    manager.logger("error", "no account", playerLevel);
                                } else {
                                    System.out.println("Please enter your password: ");
                                    while (!bool) {

                                        String password = scanner.nextLine();
                                        bool = loginProcess(username, password);
                                        if (bool) {
                                            System.out.println(ANSI_CYAN + "Logged in successfully!" + ANSI_RESET);
                                            manager.logger("info", "log in", playerLevel);
                                            flag_end1 = false;
                                        } else {
                                            System.out.println(ANSI_RED + "Password incorrect! please enter your password again:" + ANSI_RED);
                                            manager.logger("error", "wrong pass", playerLevel);
                                        }

                                    }
                                }

                            } else if (command.equals("signup")) {
                                if (usernameCheckProcess(username)) {
                                    System.out.println(ANSI_RED + "Error! because the username already exists." + ANSI_RESET);
                                    manager.logger("error", "account exists", playerLevel);
                                } else {
                                    System.out.println("Please enter a password to create a new account: ");
                                    String password = scanner.nextLine();
                                    signupProcess(username, password);
                                    System.out.println(ANSI_CYAN + "Account created successfully!" + ANSI_RESET);
                                    manager.logger("info", "account created", playerLevel);
                                    flag_end1 = false;
                                }

                            } else if (command.equals("exit")) {
                                manager.logger("info", "exit", playerLevel);
                                System.exit(0);


                            } else if (command.equals("log out")) {
                                flag2 = true;
                                manager.logger("info", "log out", playerLevel);
                                break;

                            } else {
                                System.out.println(ANSI_RED + "Invalid Input! Please enter your command again:" + ANSI_RESET);
                                manager.logger("error", "invalid input", playerLevel);
                            }
                            if (!flag_end1) {
                                flag_end1 = true;
                                break;
                            }

                        }
                    }
                    boolean flag_end2 = false;

                    if (!flag2) {
                        System.out.println(ANSI_YELLOW + "=-=-=-=-=-=-=-=-=-=-=-=-=-MAIN MENU-=-=-=-=-=-=-=-=-=-=-=-=-=-=" + ANSI_RESET);
                        System.out.println("Your commands are: ");
                        System.out.println("1.Start [level]");
                        System.out.println("2.log out");
                        System.out.println("3.settings");
                        System.out.println("4.exit");

                        while (!(Command = scanner.nextLine()).equals("log out")) {
                            String command = Command.toLowerCase();
                            String[] split = command.split("\\s+");
                            if (command.startsWith("start")) {
                                if (split.length == 2 && startProcess(username, Integer.parseInt(split[1]))) {
                                    flag_logout = false;
                                    flag_end2 = true;
                                    playerLevel = levelReturnerProcess(Integer.parseInt(split[1]));
                                    playerLevel.playerName = username;
                                    manager.logger("info", "level started", playerLevel);
                                    mapInitializeProcess(playerLevel);
                                    if (playerLevel.levelNumber == 1) {
                                        if (!playerLevel.levelEnd && playerLevel.levelStarted) {
                                            System.out.println("Enter [continue] to load your previous game or [new] to create a new game:");
                                            boolean flag_temp = true;
                                            while (flag_temp) {
                                                String temp = scanner.nextLine();
                                                if (temp.equals("continue")) {
                                                    flag_temp = false;
                                                    manager.logger("info", "level continue", playerLevel);

                                                } else if (temp.equals("new")) {
                                                    playerLevel = manager.newLevel(playerLevel.levelNumber, playerLevel);
                                                    mapInitializeProcess(playerLevel);
                                                    manager.logger("info", "new level", playerLevel);
                                                    flag_temp = false;

                                                } else {
                                                    System.out.println(ANSI_RED + "Invalid input! please enter your input again:" + ANSI_RESET);
                                                    manager.logger("error", "invalid input", playerLevel);

                                                }
                                            }
                                        } else if (playerLevel.levelEnd && playerLevel.levelStarted) {//baraye zamani ke user bazi ra end mikonad va dobare level ra run mikonad
                                            playerLevel = manager.newLevel(playerLevel.levelNumber, playerLevel);
                                            mapInitializeProcess(playerLevel);
                                            manager.logger("info", "new level", playerLevel);
                                        }
                                    } else if (playerLevel.levelStarted) {
                                        if (!playerLevel.levelEnd) {
                                            System.out.println("Enter [continue] to load your previous game or [new] to create a new game:");
                                            boolean flag_temp = true;
                                            while (flag_temp) {
                                                String temp = scanner.nextLine();
                                                if (temp.equals("continue")) {
                                                    flag_temp = false;
                                                    manager.logger("info", "level continue", playerLevel);

                                                } else if (temp.equals("new")) {
                                                    playerLevel = manager.newLevel(playerLevel.levelNumber, playerLevel);
                                                    mapInitializeProcess(playerLevel);
                                                    manager.logger("info", "new level", playerLevel);
                                                    flag_temp = false;

                                                } else {
                                                    System.out.println(ANSI_RED + "Invalid input! please enter your input again:" + ANSI_RESET);
                                                    manager.logger("error", "invalid input", playerLevel);
                                                }
                                            }
                                        } else {
                                            playerLevel = manager.newLevel(playerLevel.levelNumber, playerLevel);
                                            mapInitializeProcess(playerLevel);
                                            manager.logger("info", "new level", playerLevel);
                                        }
                                    }
                                    playerLevel.levelStarted = true;

                                }

                            } else if (command.equals("settings")) {
                                settingsProcess();
                                manager.logger("info", "settings", playerLevel);

                            } else if (command.equals("exit")) {
                                manager.logger("info", "exit", playerLevel);
                                System.exit(0);
                            } else {
                                System.out.println(ANSI_RED + "Invalid Input! Please enter your command again:" + ANSI_RESET)
                                ;
                                manager.logger("error", "invalid input", playerLevel);
                            }
                            if (flag_end2) {
                                break;
                            }
                        }
                        if (Command.equals("log out")) {
                            manager.logger("info", "log out", playerLevel);
                        }
                        flag_endgame = true;
                    }
                    flag_end2 = false;

                }
                flag_logout = true;

                System.out.println(ANSI_CYAN + "=-=-=-=-=-=-=-=-=-=-=-=- GamePlay -=-=-=-=-=-=-=-=-=-=-=-=-=" + ANSI_RESET);
                manager.printTasks(playerLevel);
                Scanner scanner = new Scanner(System.in);
                String Command;
                Boolean turnCheck = false;
                int turnCounter = 0;

                while (!(Command = scanner.nextLine()).equals("exit")) {
                    if (playerLevel.TaskCheck) {
                        playerLevel = manager.levelEnd(playerLevel);
                        System.out.println("Please enter [menu] to go to Level menu");
                        while (!scanner.nextLine().equals("menu")) {

                        }
                        break;
                    }

                    if (!turnCheck) {

                        String command = Command.toLowerCase();
                        String[] split = command.split("\\s+");

                        if (command.startsWith("buy") && split.length == 2) {
                            buyProcess(split[1], playerLevel);
                        } else if (command.startsWith("pickup") && split.length == 3) {
                            pickupProcess(Integer.parseInt(split[1]), Integer.parseInt(split[2]), playerLevel);

                        } else if (command.startsWith("well")) {
                            if (playerLevel.bucket.duration > -1) {
                                System.out.println("The bucket is getting filled now.");
                            } else if (playerLevel.bucket.full) {
                                System.out.println("The bucket must be empty to fill it again!");
                            } else if (playerLevel.bucket.duration == -1) {
                                playerLevel.bucket.duration = 0;
                            }

                        } else if (command.startsWith("plant") && split.length == 3) {
                            plantProcess(Integer.parseInt(split[1]), Integer.parseInt(split[2]), playerLevel);

                        } else if (command.startsWith("work") && split.length == 2) {
                            if (split[1].equals("weavefactory")) {
                                if (playerLevel.weaveFactory.productTime == -1) {
                                    playerLevel.weaveFactory.productTime = 0;
                                }
                            } else if (split[1].equals("bakery")) {
                                if (playerLevel.bakery.productTime == -1) {
                                    playerLevel.bakery.productTime = 0;
                                }
                            } else if (split[1].equals("millfactory")) {
                                if (playerLevel.millFactory.productTime == -1) {
                                    playerLevel.millFactory.productTime = 0;
                                }
                            } else if (split[1].equals("icefactory")) {
                                if (playerLevel.iceFactory.productTime == -1) {
                                    playerLevel.iceFactory.productTime = 0;
                                }
                            } else if (split[1].equals("milkfactory")) {
                                if (playerLevel.milkFactory.productTime == -1) {
                                    playerLevel.milkFactory.productTime = 0;
                                }
                            } else if (split[1].equals("sewingfactory")) {
                                if (playerLevel.sewingFactory.productTime == -1) {
                                    playerLevel.sewingFactory.productTime = 0;
                                }
                            }
                            workProcess(split[1], playerLevel);

                        } else if (command.startsWith("cage")) {
                            cageProcess(Integer.parseInt(split[1]), Integer.parseInt(split[2]), playerLevel);

                        } else if (command.startsWith("turn") && split.length == 2) {
                            turnCounter = Integer.parseInt(split[1]);
                            turnCheck = true;

                        } else if (command.startsWith("truck load") && split.length == 3) {
                            truckLoadProcess(playerLevel, split[2]);

                        } else if (command.startsWith("truck unload") && split.length == 3) {
                            truckUnloadProcess(playerLevel, split[2]);

                        } else if (command.equals("truck go")) {
                            if (playerLevel.motorCycle.names.size() > 0) {
                                playerLevel.motorCycle.counter = 0;
                            } else {
                                System.out.println(ANSI_RED + "The truck is empty now!" + ANSI_RESET);
                            }

                        } else if (command.startsWith("build")) {
                            buildProcess(playerLevel, split[1]);
                        } else if (command.equals("inquiry")) {
                            printInfoProcess(playerLevel);
                        } else if (command.startsWith("upgrade") && split.length == 2) {
                            upgradeFactoryProcess(playerLevel, split[1]);

                        } else if (command.equals("end")) {
                            System.out.println("Please enter [menu] to go to Level menu");
                            playerLevel = manager.levelEnd(playerLevel);
                            break;
                        } else {
                            System.out.println(ANSI_RED + "Invalid input! Please enter your input again:" + ANSI_RESET);
                        }
                    }
                    if (turnCheck) {
                        turnProcess(playerLevel, turnCounter);
                        turnCheck = false;
                    }
                }
                flag_endgame = false;


            }
        }

        public void run() throws IOException {

            while (flag_logout) {
                Graphics.Menu();
            }
        }

    }

    package Animals;

    public class DomesticAnimals {
        public int sellPrice;
        public int BuyPrice;
        public int x;
        public int y;
        public int health;
        public int speed;
        public int capacity;
        public String product;
        public int productTime;
        public int time = -1;
        public boolean existence = false;

        public DomesticAnimals() {
        }

        public DomesticAnimals(int sellPrice, int buyPrice, int x, int y, int health, int speed, int capacity, String product , int productTime) {
            this.sellPrice = sellPrice;
            this.BuyPrice = buyPrice;
            this.x = x;
            this.y = y;
            this.health = health;
            this.speed = speed;
            this.capacity = capacity;
            this.product = product;
            this.productTime = productTime;
        }

        public static class Buffalo extends Animals.DomesticAnimals {
            public Buffalo() {
            }

            public Buffalo(int x, int y) {
                super(200, 400, x, y, 100, 1, 0, "milk" ,4);
            }
        }

        public static class Chicken extends Animals.DomesticAnimals {
            public Chicken() {
            }

            public Chicken(int x, int y) {
                super(50, 100, x, y, 100, 1, 0, "egg", 1);
            }
        }

        public static class Turkey extends Animals.DomesticAnimals {
            public Turkey() {
            }

            public Turkey(int x, int y) {
                super(100, 200, x, y, 100, 1, 0, "feather", 2);
            }
        }
    }

    package Animals;

    public class SpecialAnimals {
        public int x;
        public int y;
        public int buyPrice;
        public int sellPrice;
        public int size;

        public SpecialAnimals() {
        }

        public SpecialAnimals(int x, int y, int buyPrice) {
            this.x = x;
            this.y = y;
            this.buyPrice = buyPrice;

        }

        public static class Dog extends Animals.SpecialAnimals {
            public Dog(int x, int y) {
                super(x, y, 100);
            }

            public Dog() {
            }
        }

        public static class Cat extends Animals.SpecialAnimals {
            public Cat() {
            }

            public Cat(int x, int y) {
                super(x, y, 150);
            }
        }
    }

    package Animals;

    public class WildAnimals {
        public int sellPrice;
        public int x;
        public int y;
        public int cageP;
        public int cageCounter = 0;
        public int capacity;
        public int speed;
        public boolean inCage = false;
        public int inCageCounter = 0;
        public boolean cagePlus = false;
        public WildAnimals() {
        }

        public WildAnimals(int sellPrice, int x, int y, int cageP, int capacity, int speed) {
            this.sellPrice = sellPrice;
            this.x = x;
            this.y = y;
            this.cageP = cageP;
            this.capacity = capacity;
            this.speed = speed;
        }

        public static class Bear extends Animals.WildAnimals {
            public Bear() {
            }

            public Bear( int x, int y) {
                super(400, x, y, 4, 15, 1);
            }
        }

        public static class Tiger extends Animals.WildAnimals {
            public Tiger() {
            }

            public Tiger(int x, int y) {
                super(500, x, y, 4, 15, 2);
            }
        }

        public static class Lion extends Animals.WildAnimals {
            public Lion() {
            }

            public Lion( int x, int y) {
                super(300, x, y, 3, 15, 1);
            }
        }
    }

    package LevelDesign;

    public class Bucket {
        public int capacity = 5;
        public int duration = -1;
        public int maxDuration = 2;
        public Boolean full = true;

        public Bucket(int capacity, int duration) {
            this.capacity = capacity;
            this.duration = duration;
            this.maxDuration = 5;
            this.full = false;
        }
    }

    package LevelDesign;

    public class Cell {
        public int x;
        public int y;
        public int grass = 0;

        public Cell(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    package LevelDesign;

    public class Factory {
        public int buildPrice;
        public int maxDuration;
        public String ingredient;
        public Ingredient product;
        public int level = 1;
        public int upgradePrice;
        public boolean ingredientExistence = false;
        public int productTime = -1;
        public boolean existence  =false;
        public boolean ingredientExistence2 = false;
        public Factory() {
        }


        public static class WeaveFactory extends LevelDesign.Factory {
            public WeaveFactory() {
                this.buildPrice = 250;
                this.maxDuration = 4;
                this.ingredient = "feather";
                this.product = new Ingredient.Weave(1,1);
                this.upgradePrice = 0;
            }
        }


        public static class MillFactory extends LevelDesign.Factory {
            public MillFactory() {
                this.buildPrice = 150;
                this.maxDuration = 3;
                this.ingredient = "egg";
                this.product = new Ingredient.Flour(1,1);
                this.upgradePrice = 0;
            }
        }

        public static class MilkFactory extends LevelDesign.Factory {
            public MilkFactory() {
                this.buildPrice = 400;
                this.maxDuration = 5;
                this.ingredient = "milk";
                this.product = new Ingredient.CMilk(1,1);
                this.upgradePrice = 0;
            }
        }

        public static class Bakery extends LevelDesign.Factory {
            public Bakery() {
                this.buildPrice = 250;
                this.maxDuration = 4;
                this.ingredient = "flour";
                this.product = new Ingredient.Bread(1,1);
                this.upgradePrice = 0;
            }
        }

        public static class SewingFactory extends LevelDesign.Factory {
            public SewingFactory() {
                this.buildPrice = 400;
                this.maxDuration = 5;
                this.ingredient = "weave";
                this.product = new Ingredient.Cloth(1,1);
                this.upgradePrice = 0;
            }
        }

        public static class IceFactory extends LevelDesign.Factory {
            public IceFactory() {
                this.buildPrice = 550;
                this.maxDuration = 6;
                this.ingredient = "cmilk";
                this.product = new Ingredient.IceCream(1,1);
                this.upgradePrice = 0;
            }
        }


    }

    package LevelDesign;

    public class Ingredient {
        public String name;
        public int price;
        public int size;
        public int x ;
        public int y ;
        public int expire;

        public Ingredient() {
        }

        public Ingredient(String name, int price, int size, int x, int y, int expire) {
            this.name = name;
            this.price = price;
            this.size = size;
            this.expire = expire;
            this.x = x;
            this.y = y;
        }

        public static class Egg extends LevelDesign.Ingredient {
            public Egg( int x, int y) {
                this.name = "egg";
                this.price = 15;
                this.size = 1;
                this.expire = 4;
                this.x = x;
                this.y = y;
            }
        }

        public static class Feather extends LevelDesign.Ingredient {
            public Feather( int x, int y) {
                this.name = "feather";
                this.price = 20;
                this.size = 1;
                this.expire = 4;
                this.x = x;
                this.y = y;
            }
        }

        public static class Milk extends LevelDesign.Ingredient {
            public Milk( int x, int y) {
                this.name = "milk";
                this.price = 25;
                this.size = 1;
                this.expire = 4;
                this.x = x;
                this.y = y;
            }
        }

        public static class Flour extends LevelDesign.Ingredient {
            public Flour( int x, int y) {
                this.name = "flour";
                this.price = 40;
                this.size = 2;
                this.expire = 5;
                this.x = x;
                this.y = y;
            }
        }

        public static class Weave extends LevelDesign.Ingredient {
            public Weave( int x, int y) {
                this.name = "weave";
                this.price = 50;
                this.size = 2;
                this.expire = 5;
                this.x = x;
                this.y = y;
            }
        }

        public static class CMilk extends LevelDesign.Ingredient {
            public CMilk( int x, int y) {
                this.name = "cmilk";
                this.price = 60;
                this.size = 2;
                this.expire = 5;
                this.x = x;
                this.y = y;
            }
        }

        public static class Bread extends LevelDesign.Ingredient {
            public Bread( int x, int y) {
                this.name = "bread";
                this.price = 80;
                this.size = 4;
                this.expire = 6;
                this.x = x;
                this.y = y;
            }
        }

        public static class Cloth extends LevelDesign.Ingredient {
            public Cloth( int x, int y) {
                this.name = "cloth";
                this.price = 100;
                this.size = 4;
                this.expire = 6;
            }
        }

        public static class IceCream extends LevelDesign.Ingredient {
            public IceCream( int x, int y) {
                this.name = "icecream";
                this.price = 120;
                this.size = 4;
                this.expire = 6;
                this.x = x;
                this.y = y;
            }
        }
    }

    package LevelDesign;


    public class Level {
        public int levelNumber;
        public int passedTime = 0;
        public String playerName;
        public boolean levelEnd = false;
        public boolean levelStarted;
        public int coin;
        public ArrayList<LevelDesign.Ingredient> ingredients = new ArrayList<>();
        public ArrayList<Animals.DomesticAnimals.Chicken> chickens = new ArrayList<>();
        public ArrayList<Animals.DomesticAnimals.Buffalo> buffalos = new ArrayList<>();
        public ArrayList<Animals.DomesticAnimals.Turkey> turkies = new ArrayList<>();
        public ArrayList<Animals.WildAnimals.Bear> bears = new ArrayList<>();
        public ArrayList<Animals.WildAnimals.Lion> lions = new ArrayList<>();
        public ArrayList<Animals.WildAnimals.Tiger> tigers = new ArrayList<>();
        public ArrayList<Animals.SpecialAnimals.Cat> cats = new ArrayList<>();
        public ArrayList<Animals.SpecialAnimals.Dog> dogs = new ArrayList<>();
        public LevelDesign.Map map = new Map();
        public LevelDesign.Bucket bucket = new LevelDesign.Bucket(0, -1);
        public LevelDesign.Factory.WeaveFactory weaveFactory = new LevelDesign.Factory.WeaveFactory();
        public LevelDesign.Factory.MillFactory millFactory = new LevelDesign.Factory.MillFactory();
        public LevelDesign.Factory.MilkFactory milkFactory = new LevelDesign.Factory.MilkFactory();
        public LevelDesign.Factory.Bakery bakery = new LevelDesign.Factory.Bakery();
        public LevelDesign.Factory.SewingFactory sewingFactory = new LevelDesign.Factory.SewingFactory();
        public LevelDesign.Factory.IceFactory iceFactory = new LevelDesign.Factory.IceFactory();
        public Storage storage = new Storage();
        public MotorCycle motorCycle = new MotorCycle();
        public Task task = new Task();
        public int wildAnimalAddTimer1 = 10000;
        public int wildAnimalAddTimer2 = 10000;
        public int wildAnimalAddTimer3 = 10000;
        public int wildAnimalAddTimer4 = 10000;
        public int wildAnimalAddTimer5 = 10000;
        public int wildAnimalAddTimer6 = 10000;
        public int wildAnimalAddTimer7 = 10000;
        public boolean TaskCheck = false;

    }

    package LevelDesign;

    public class Map {
        public int length = 6;
        public int height = 6;
        public LevelDesign.Cell[][] map = new LevelDesign.Cell[height][length];
        ////////// Tamam Khane ha 0 beshan.


        public Map() {
        }
    }

    package LevelDesign;

import java.util.ArrayList;

    public class MotorCycle {
        public int capacity = 20;
        public ArrayList<String> names =new ArrayList<>();
        public ArrayList<Integer> quantities = new ArrayList<>();
        public int counter =-1;
        public int Max = 9;
        public int coin = 0;


    }

    package LevelDesign;

import java.util.ArrayList;

    public class Storage {
        public int capacity = 30;
        public ArrayList<String> names =new ArrayList<>();
        public ArrayList<Integer> quantities = new ArrayList<>();
    }

package LevelDesign;


    public class Task {
        public int taskCounter;
        public int taskObj;
        public boolean taskCheck;

        public int timeObj1 = 10000;
        public int timeObj2 = 10000;
        public int timeObj3 = 10000;
        public int chickenObj = 10000;
        public int buffaloObj = 10000;
        public int turkeyObj = 10000;
        public int coinObj = 10000;
        public int eggObj = 10000;
        public int flourObj = 10000;
        public int featherObj = 10000;
        public int milkObj = 10000;
        public int cmilkObj = 10000;
        public int breadObj = 10000;
        public int weaveObj = 10000;
        public int clothObj = 10000;
        public int iceCreamObj = 10000;

        public boolean coinCheck = false;
        public boolean chickenCheck = false;
        public boolean buffaloCheck = false;
        public boolean turkeyCheck = false;
        public boolean eggCheck = false;
        public boolean flourCheck = false;
        public boolean featherCheck = false;
        public boolean milkCheck = false;
        public boolean cmilkCheck = false;
        public boolean breadCheck = false;
        public boolean weaveCheck = false;
        public boolean clothCheck = false;
        public boolean iceCreamCheck = false;
        public boolean timeCheck1 = false;
        public boolean timeCheck2 = false;
        public boolean timeCheck3 = false;

        public int totalCoin = 0;
        public int eggCounter = 0;
        public int flourCounter = 0;
        public int featherCounter = 0;
        public int milkCounter = 0;
        public int cmilkCounter = 0;
        public int breadCounter = 0;
        public int weaveCounter = 0;
        public int clothCounter = 0;
        public int iceCreamCounter = 0;
        public int chickenCounter = 0;
        public int turkeyCounter =0;
        public int buffaloCounter=0;
        public int timeCounter = 0;

    }



import java.io.IOException;
import java.util.ArrayList;

    public class Graphics.Main {

        public static void main(String[] args) throws IOException {
            Graphics.Manager manager = new Graphics.Manager();

            Graphics.InputProcessor inputProcessor = new Graphics.InputProcessor(manager);
            inputProcessor.run();
            // manager.writeBasicLevel2Info();



        }

    }

    public class Graphics.LoginUser {
        String username;
        String password;

        public Graphics.LoginUser(String username, String password) {
            this.username = username;
            this.password = password;
        }
    }

}

*/
