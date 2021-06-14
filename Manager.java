import Animals.DomesticAnimals;
import LevelDesign.Bucket;
import LevelDesign.Ingredient;
import LevelDesign.Level;
import LevelDesign.Map;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Manager {
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_YELLOW = "\u001B[33m";




    ArrayList<Level> levels = new ArrayList<>();

    private ArrayList<LoginUser> Users = new ArrayList<>();


    Level level = new Level();

    public void Buy(String name, Level level) {
        if (name.equals("buffalo")) {
            DomesticAnimals.Buffalo buffalo = new DomesticAnimals.Buffalo(1, 1);
            if (level.coin >= buffalo.BuyPrice) {
                level.buffalos.add(buffalo);
                level.coin -= buffalo.BuyPrice;
            } else {
                System.out.println("NOT ENOUGH COIN TO BUY!");
            }
        } else if (name.equals("turkey")) {
            DomesticAnimals.Turkey turkey = new DomesticAnimals.Turkey(1, 1);
            if (level.coin >= turkey.BuyPrice) {
                level.turkies.add(turkey);
                level.coin -= turkey.BuyPrice;
            } else {
                System.out.println("NOT ENOUGH COIN TO BUY!");
            }
        } else if (name.equals("chicken")) {
            DomesticAnimals.Chicken chicken = new DomesticAnimals.Chicken(1, 1);
            if (level.coin >= chicken.BuyPrice) {
                level.chickens.add(chicken);
                level.coin -= chicken.BuyPrice;
            } else {
                System.out.println("NOT ENOUGH COIN TO BUY!");
            }
        }
    }

    public void PickUp(int x, int y, Level level) {
        if (level != null) {
            int t = 0;
            for (int i = 0; i < level.ingredients.size(); i++) {
                if (level.ingredients.get(i).x == x && level.ingredients.get(i).y == y) {
                    t = 1;
                    if (level.storage.capacity >= level.ingredients.get(i).size) {
                        level.storage.names.add(level.ingredients.get(i).name);
                        level.storage.quantities.add(1);
                        level.storage.capacity -= level.ingredients.get(i).size;
                        level.ingredients.remove(i);
                        break;
                    } else {
                        System.out.println("There is not enough space in the storage!");
                    }
                }
            }

            if (t == 0) {
                System.out.println("Please enter valid coordinates!");
            }
        }
    }

    public void Expirings(Level level) {
        int a = level.ingredients.size();
        for (int i = 0; i < a; i++) {
            if (level.ingredients.get(i).expire == 0) {
                level.ingredients.remove(i);
            }
        }
    }

    public void Well(Level level, int counter) {
        if (level.bucket.duration >= level.bucket.maxDuration) {
            level.bucket.full = true;
            level.bucket.capacity = 5;
            level.bucket.duration = -1;

        } else {
            level.bucket.duration = counter;
            // counter bayad ziad shavad
        }
    }

    public void Plant(int x, int y, Level level) {
        if (level.map == null){
            level.map = new Map();
        }
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
        } else {
            System.out.println("Coordinates are not valid! Try again!");
        }
    }

    public void Build(Level level, String name) {
        if (name.equals("WeaveFactory")) {
            if (level.weaveFactory.existence == false) {
                if (level.coin >= level.weaveFactory.buildPrice) {
                    level.weaveFactory.existence = true;
                    level.coin -= level.weaveFactory.buildPrice;
                } else
                    System.out.println("There is not enough money to build the factory.");
            } else {
                System.out.println("This factory can't be built again.");
            }
        } else if (name.equals("MillFactory")) {
            if (level.millFactory.existence == false) {
                if (level.coin >= level.millFactory.buildPrice) {
                    level.millFactory.existence = true;
                    level.coin -= level.millFactory.buildPrice;
                } else
                    System.out.println("There is not enough money to build the factory.");
            } else {
                System.out.println("This factory can't be built again.");
            }
        } else if (name.equals("MilkFactory")) {
            if (level.milkFactory.existence == false) {
                if (level.coin >= level.milkFactory.buildPrice) {
                    level.milkFactory.existence = true;
                    level.coin -= level.milkFactory.buildPrice;
                } else
                    System.out.println("There is not enough money to build the factory.");
            } else {
                System.out.println("This factory can't be built again.");
            }

        } else if (name.equals("Bakery")) {
            if (level.bakery.existence == false) {
                if (level.coin >= level.bakery.buildPrice) {
                    level.bakery.existence = true;
                    level.coin -= level.bakery.buildPrice;
                } else
                    System.out.println("There is not enough money to build the factory.");
            } else {
                System.out.println("This factory can't be built again.");
            }

        } else if (name.equals("SewingFactory")) {
            if (level.sewingFactory.existence == false) {
                if (level.coin >= level.sewingFactory.buildPrice) {
                    level.sewingFactory.existence = true;
                    level.coin -= level.sewingFactory.buildPrice;
                } else
                    System.out.println("There is not enough money to build the factory.");
            } else {
                System.out.println("This factory can't be built again.");
            }

        } else if (name.equals("IceFactory")) {
            if (level.iceFactory.existence == false) {
                if (level.coin >= level.iceFactory.buildPrice) {
                    level.iceFactory.existence = true;
                    level.coin -= level.iceFactory.buildPrice;
                } else
                    System.out.println("There is not enough money to build the factory.");
            } else {
                System.out.println("This factory can't be built again.");
            }

        } else {
            System.out.println("Not a valid name!");
        }
    }

    public void Work(Level level, String name) {
        String[] names = {"WeaveFactory", "MillFactory", "MilkFactory", "Bakery", "SewingFactory", "IceFactory"};
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
            int t = 0;
            int r = level.storage.names.size();
            if (a == 0) {
                if (level.weaveFactory.existence) {
                    if (level.weaveFactory.productTime == -1) {
                        for (int i = 0; i < r; i++) {
                            if (level.storage.names.get(i).equals(level.weaveFactory.ingredient)) {
                                t = 1;
                                level.storage.names.remove(i);
                                level.storage.quantities.remove(i);
                                Ingredient.Feather feather = new Ingredient.Feather(1,1);
                                level.storage.capacity -= feather.size;
                                level.weaveFactory.productTime ++;
                                level.weaveFactory.ingredientExistence = true;
                                // Production
                            }
                        }
                        if (t == 0)
                            System.out.println("There is not enough ingredient to start!");
                    }
                    else {
                        System.out.println("The factory is working now, You must wait to end the production.");
                    }
                } else {
                    System.out.println("You must first build the factory.");
                }
            }

            if (a == 1) {
                if (level.millFactory.existence) {
                    if (level.millFactory.productTime == -1) {
                        for (int i = 0; i < r; i++) {
                            if (level.storage.names.get(i).equals(level.millFactory.ingredient)) {
                                t = 1;
                                level.storage.names.remove(i);
                                level.storage.quantities.remove(i);
                                Ingredient.Egg feather = new Ingredient.Egg(1,1);
                                level.storage.capacity -= feather.size;
                                level.millFactory.productTime ++;
                                level.millFactory.ingredientExistence = true;
                                // Production
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

            if (a == 2) {
                if (level.milkFactory.existence) {
                    if (level.milkFactory.productTime == -1) {
                        for (int i = 0; i < r; i++) {
                            if (level.storage.names.get(i).equals(level.milkFactory.ingredient)) {
                                t = 1;
                                level.storage.names.remove(i);
                                level.storage.quantities.remove(i);
                                Ingredient.Milk feather = new Ingredient.Milk(1,1);
                                level.storage.capacity -= feather.size;
                                level.milkFactory.productTime ++;
                                level.milkFactory.ingredientExistence = true;
                                // Production
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

            if (a == 3) {
                if (level.bakery.existence) {
                    if (level.bakery.productTime == -1) {
                        for (int i = 0; i < r; i++) {
                            if (level.storage.names.get(i).equals(level.bakery.ingredient)) {
                                t = 1;
                                level.storage.names.remove(i);
                                level.storage.quantities.remove(i);
                                Ingredient.Flour feather = new Ingredient.Flour(1,1);
                                level.storage.capacity -= feather.size;
                                level.bakery.productTime ++;
                                level.bakery.ingredientExistence = true;
                                // Production
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

            if (a == 4) {
                if (level.sewingFactory.existence) {
                    if (level.sewingFactory.productTime == -1) {
                        for (int i = 0; i < r; i++) {
                            if (level.storage.names.get(i).equals(level.sewingFactory.ingredient)) {
                                t = 1;
                                level.storage.names.remove(i);
                                level.storage.quantities.remove(i);
                                Ingredient.Weave feather = new Ingredient.Weave(1,1);
                                level.storage.capacity -= feather.size;
                                level.sewingFactory.productTime ++;
                                level.sewingFactory.ingredientExistence = true;
                                // Production
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
                    if (level.iceFactory.duration == -1) {
                        for (int i = 0; i < r; i++) {
                            if (level.storage.names.get(i).equals(level.iceFactory.ingredient)) {
                                t = 1;
                                level.storage.names.remove(i);
                                level.storage.quantities.remove(i);
                                Ingredient.CMilk feather = new Ingredient.CMilk(1,1);
                                level.storage.capacity -= feather.size;
                                level.iceFactory.duration ++;
                                level.iceFactory.ingredientExistence = true;
                                // Production
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
            if (level.lions.get(l).cageCounter == level.lions.get(l).cageP) {
                level.lions.get(l).inCage = true;
                level.lions.get(l).speed = 0;
            }
            else{
                level.lions.get(l).cageCounter += counter;
            }
        } else if (b != -1) {
            if (level.bears.get(b).cageCounter == level.bears.get(b).cageP) {
                level.bears.get(b).inCage = true;
                level.bears.get(b).speed = 0;
            }
            else{
                level.bears.get(b).cageCounter += counter;
            }


        } else if (t != -1) {
            if ( level.tigers.get(t).cageCounter == level.tigers.get(t).cageP) {
                level.tigers.get(t).inCage = true;
                level.tigers.get(t).speed = 0;
            }
            else{
                level.tigers.get(t).cageCounter += counter;
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

    public void FactoryCounter(Level level){
        if (level.sewingFactory.existence && level.sewingFactory.ingredientExistence){
            if (level.sewingFactory.productTime >=level.sewingFactory.maxDuration){
                level.sewingFactory.productTime = -1;
                level.sewingFactory.ingredientExistence = false;
                Ingredient.Cloth product = new Ingredient.Cloth(1,1 );
                level.ingredients.add(product);
            }
            else{
                level.sewingFactory.productTime++;
            }
        }
        if (level.millFactory.existence && level.millFactory.ingredientExistence){
            if (level.millFactory.productTime >=level.millFactory.maxDuration){
                level.millFactory.productTime = 0;
                level.millFactory.ingredientExistence = false;
                Ingredient.Flour product = new Ingredient.Flour(1,1 );
                level.ingredients.add(product);
            }
            else{
                level.millFactory.productTime++;
            }
        }
        if (level.milkFactory.existence && level.milkFactory.ingredientExistence){
            if (level.milkFactory.productTime >=level.milkFactory.maxDuration){
                level.milkFactory.productTime = 0;
                level.milkFactory.ingredientExistence = false;
                Ingredient.Milk product = new Ingredient.Milk(1,1 );
                level.ingredients.add(product);
            }
            else{
                level.milkFactory.productTime++;
            }
        }

        if (level.bakery.existence && level.bakery.ingredientExistence){
            if (level.bakery.productTime >=level.bakery.maxDuration){
                level.bakery.productTime = 0;
                level.bakery.ingredientExistence = false;
                Ingredient.Bread product = new Ingredient.Bread(1,1 );
                level.ingredients.add(product);
            }
            else{
                level.bakery.productTime++;
            }
        }

        if (level.weaveFactory.existence && level.weaveFactory.ingredientExistence){
            if (level.weaveFactory.productTime >=level.weaveFactory.maxDuration){
                level.weaveFactory.productTime = 0;
                level.weaveFactory.ingredientExistence = false;
                Ingredient.Weave product = new Ingredient.Weave(1,1 );
                level.ingredients.add(product);
            }
            else{
                level.weaveFactory.productTime++;
            }
        }

        if (level.iceFactory.existence && level.iceFactory.ingredientExistence){
            if (level.iceFactory.productTime >=level.iceFactory.maxDuration){
                level.iceFactory.productTime = 0;
                level.iceFactory.ingredientExistence = false;
                Ingredient.IceCream product = new Ingredient.IceCream(1,1 );
                level.ingredients.add(product);
            }
            else{
                level.iceFactory.productTime++;
            }
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
            List<LoginUser> temp = Arrays.asList(gson.fromJson(reader, LoginUser[].class));
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
                for (Level l : levels) {
                    System.out.print(l.levelNumber);
                    if (l.levelNumber != levels.size()) {
                        System.out.print(", ");
                    }
                }
                System.out.println();
            }

        } else {
            System.out.println(ANSI_RED + "You have to start the game from the beginning!" + ANSI_RESET);
            Level level = new Level();
            level.levelNumber = 1;
            level.coin = 10000;
            levels.add(level);
            Gson write = new GsonBuilder().setPrettyPrinting().create();
            FileWriter fileWriter = new FileWriter(absolutePath);
            write.toJson(levels, fileWriter);
            fileWriter.flush();
            fileWriter.close();
        }
        return level_check;

    }

    public Level levelReturner(int levelNumber)  {
        for (Level l : levels) {
            if (l.levelNumber == levelNumber) {
                return l;
            }
        }
        return null;
    }

    public Level levelEnd(Level playerLevel) throws IOException {
        boolean find = false;
        for (Level l : levels) {
            if (l.levelNumber == (playerLevel.levelNumber + 1)) {
                return l;
            }
        }
        Level newLevel = new Level();
        newLevel.levelNumber = playerLevel.levelNumber + 1;
        levels.add(newLevel);
        save();
        return newLevel;
    }

}
