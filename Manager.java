import Animals.DomesticAnimals;
import LevelDesign.Level;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Manager {
    public static final String ANSI_RED = "\u001B[31m";

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
            for (int i = 0; i < level.ingredients.size(); i++) {
                if (level.ingredients.get(i).x == x && level.ingredients.get(i).y == y) {

                }
            }
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
            for (LoginUser u : Users){
                if(u.username.equals(username)){
                    System.out.println("username found!");
                    found = true;
                }
            }
        }
        if(!found){
            System.out.println("not found");
        }
        return found;
    }

    public void signUp(String username, String password) throws IOException {
        LoginUser user = new LoginUser(username,password);
        Users.add(user);

        Gson gson = new Gson();
        Writer writer = Files.newBufferedWriter(Paths.get("usersInfo.json"));
        gson.toJson(Users, writer);
        writer.close();

    }

    public boolean login(String username, String password) throws IOException {
        readUsersInfo();
        boolean flag = false;
        for (LoginUser u: Users) {
            if(u.username.equals(username)){
                if(u.password.equals(password)){
                    flag = true;
                }
            }
        }
        return flag;


    }
    int P = 3;
    final boolean iliyaisoskol = true;


}
