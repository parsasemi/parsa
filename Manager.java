import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Until;

import java.io.*;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;


public class Manager {
    public void run() throws IOException {
        InputProcessor input = new InputProcessor();
        Scanner scanner = new Scanner(System.in);

        input.command = scanner.next();
        ArrayList<Order> ors = new ArrayList<>();
        Codes codha = new Codes();
        Gson load = new Gson();
        FileReader fileReader = new FileReader("DB/Codes.json");
        codha = load.fromJson( fileReader , Codes.class );
        fileReader.close();
        String nameId = new String();
        boolean newName = true;
        Boolean validName = true;
        String cAddress = "";
        int t=0;
        int k=0;
        while (!input.command.equals("End")) {
            if (input.command.equals("login") && k==0) {
                String nameID = scanner.next();
                if (validName == false) {
                    System.out.println("The Name is not Valid!, Enter Again");
                    nameID = "";
                }
                if (validName == true) {
                    nameId = nameID;
                    cAddress  = "DB/Customer/" + nameId + ".json";
                    File file = new File("DB/Customer/" + nameID + ".json");
                    if (!file.exists()) {
                        try {
                            file.createNewFile();
                            newName = true;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    else
                        newName = false;
                    k= 1;
                }
                if (newName) {
                    System.out.println("Welcome to the Shop, Now Choose your Command!");
                }
                if (!newName) {
                    System.out.println("Welcome Back!");
                }
            }

            if (k==1) {
                int ingr = 0;
                if (input.command.equals("order")) {
                    input.command = scanner.next();
                    if (!input.command.equals("-d")) {
                        for (int i = 0; i < codha.codes.size(); i++) {
                            if (codha.codes.get(i) == Integer.parseInt(input.command)) {
                                t = 1;
                                ingr = Integer.parseInt(input.command);
                                break;
                            }
                        }
                        if (t == 0) {
                            System.out.println("Not a valid good ID, Choose correctly.");
                        }
                        ArrayList<Kala> tkalas = tKalaha();
                        String ingrN = "";
                        for (int i = 0; i < tkalas.size(); i++) {
                            if (tkalas.get(i).code == ingr) {
                                ingrN = tkalas.get(i).name;
                                break;
                            }
                        }
                        String iAddress = "DB/Ingridiants/" + ingrN + ".json";
                        if (t == 1) {
                            input.command = scanner.next();
                            if (input.command.equals("-c")) {
                                input.command = scanner.next();
                                if (numberChecker(input.command) <= 0) {
                                    System.out.println("Not a valid amount, try again.");
                                } else {
                                    Orderha pTOrders = fileOrderReader("DB/Order.json");
                                    if (pTOrders == null)
                                        pTOrders = new Orderha();
                                    Order j = new Order(ingrN, Integer.parseInt(input.command), pTOrders.orders.size() + 1);
                                    ors.add(j);
                                    if (kalaChecker(Integer.parseInt(input.command), iAddress) == 1) {
                                        ingriChanger(Integer.parseInt(input.command), iAddress);
                                        customerNOrder(nameId, ors);
                                        ors.clear();
                                        int orderCode = pTOrders.orders.size() + 1;
                                        System.out.println("Your order is submitted, Your order ID is : " + orderCode + " .");
                                        System.out.println("Enter your new command.");
                                    }
                                    else
                                        System.out.println("Not enough amount, try again.");
                                    ors.clear();
                                }
                            }
                        }

                    }

                    if (input.command.equals("-d")) {
                        input.command = scanner.next();
                        Orderha pTOrders = fileOrderReader("DB/Order.json");
                        if (numberChecker(input.command) <= 0 || pTOrders.orders.get(Integer.parseInt(input.command) - 1) == null) {
                            System.out.println("The ID is not valid, please try again.");
                        } else {
                            customerROrder(nameId, Integer.parseInt(input.command));
                            ingriChanger(-1 * pTOrders.orders.get(Integer.parseInt(input.command) - 1).get(0).quantity, "DB/Ingridiants/" + pTOrders.orders.get(Integer.parseInt(input.command) - 1).get(0).name + ".json");
                            System.out.println("Your order has been removed successfully.");
                            System.out.println("Enter your new command.");
                        }
                    }
                }

                else if (input.command.equals("logout")) {
                    Orderha orders = fileOrderReader(cAddress);
                    int price = 0;
                    int quan;
                    Kala kal;
                    int a;
                    if (orders == null)
                        a = 0;
                    else
                        a = orders.orders.size();
                    for (int i = 0; i < a; i++) {
                        quan = orders.orders.get(i).get(0).quantity;
                        kal = fileKalaReader("DB/Ingridiants/" + orders.orders.get(i).get(0).name + ".json");
                        price += kal.price * quan;
                        customerROrder(nameId, orders.orders.get(i).get(0).code);
                    }
                    orderWriter(orders, "DB/POrders.json");
                    if (orders!=null)
                        orders.orders.clear();
                    System.out.println("Your orders cost " + price + "$");
                    System.out.println("Have a great time!, see you again.");
                    k=0;
                }

                else if (input.command.equals("ls")) {
                    System.out.println("List Print");
                    input.command = scanner.next();
                    if (input.command.equals("-r") || input.command.equals("-i") || input.command.equals("-n")) {
                        listPrinter(input.command);
                    } else {
                        System.out.println("The command is not valid, try again");
                    }
                }
                else
                    System.out.println("Enter command.");
            }
            else
                System.out.println("Please login first to continue");
            input.command = scanner.next();
        }

    }







    public static int numberChecker (String string){
        int number = 0;
        char[] chars = string.toCharArray();
        for (int i=0; i<chars.length; i++) {
            if ((int) chars[i] <48 || (int)chars[i]>57){
                number=0;
                break;
            }
            else{
                number*=10;
                number+=(int)chars[i]-48;
            }
        }
        return number;
    }

    public static void fileCreator (String addr){
        File file = new File(addr);
        if (!file.exists ()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static Kala fileKalaReader (String addr) throws IOException {
        Gson load = new Gson();
        try {
            FileReader fileReader = new FileReader(addr);
            Kala kala = load.fromJson( fileReader , Kala.class );
            if (kala == null)
                return null;
            fileReader.close();
            return kala;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Orderha fileOrderReader (String addr) throws IOException {
        Gson load = new Gson();
        try {
            FileReader fileReader = new FileReader(addr);
            Orderha order = new Orderha();
            order = load.fromJson( fileReader ,Orderha.class  );
            if (order == null){
                return null;
            }
            fileReader.close();
            return order ;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void ingriAdder (String name, String type, int price, int quantity, int code, int buy , int totalSale,int totalSaleMon, int count, String addr){
        FileWriter fileWriter = null;
        fileCreator(addr);
        Kala kala = new Kala(name ,type ,price , quantity , code, buy ,totalSale, totalSaleMon, count);
        Gson save = new GsonBuilder().setPrettyPrinting().create();
        Ingridiants kalaha = new Ingridiants();
        Codes codes = new Codes();
        Gson load = new Gson();
        try {
            FileReader fileReader = new FileReader("DB/Ingridiants.json");
            kalaha = load.fromJson( fileReader , Ingridiants.class );
            if (kalaha == null) {
                kalaha = new Ingridiants();
            }
                kalaha.ingridiants.add(name);
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            FileReader fileReader = new FileReader("DB/Codes.json");
            codes = load.fromJson( fileReader , Codes.class );
            if (codes == null) {
                codes = new Codes();
            }
                codes.codes.add(kala.code);
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fileWriter = new FileWriter(addr);
            save.toJson(kala, fileWriter);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fileWriter = new FileWriter("DB/Ingridiants.json");
            save.toJson(kalaha, fileWriter);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fileWriter = new FileWriter("DB/Codes.json");
            save.toJson(codes, fileWriter);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void ingriChanger (int bought, String addr) throws IOException {
        Kala kala = fileKalaReader(addr);
        if (kala.quantity - bought < 0){
            System.out.println("There is not enough good, check the list again.");
        }
        else {
            kala.quantity = Math.max(kala.quantity - bought , 0);
            kala.totalSale += bought;
            kala.totalSaleMon += bought * kala.price;
            kala.count++;
            FileWriter fileWriter = null;
            Gson save = new GsonBuilder().setPrettyPrinting().create();
            try {
                fileWriter = new FileWriter(addr);
                save.toJson(kala, fileWriter);
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void orderWriter (Orderha orders, String addr){
        Gson save = new GsonBuilder().setPrettyPrinting().create();
        try {
            FileWriter fileWriter = new FileWriter(addr);
            save.toJson(orders , fileWriter);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void customerNOrder(String id,ArrayList<Order> order) throws IOException {
        String addr = "DB/Customer/" + id + ".json";
        Orderha pOrders = fileOrderReader(addr);
        if (pOrders == null){
            pOrders = new Orderha();
        }
        pOrders.orders.add(order);
        orderWriter(pOrders, addr);
        Orderha pTOrders = fileOrderReader("DB/Order.json");
        if (pTOrders == null)
            pTOrders = new Orderha();
        pTOrders.orders.add(order);
        int orderID = pTOrders.orders.size();
        orderWriter(pTOrders, "DB/Order.json");


        Orderha ppTOrders = fileOrderReader("DB/POrders.json");
        if (ppTOrders == null){
            ppTOrders = new Orderha();
        }
        ppTOrders.orders.add(order);
        orderWriter(ppTOrders, "DB/POrders.json");
    }

    public static void customerROrder (String id, int orderID) throws IOException {
        String addr = "DB/Customer/" + id + ".json";
        Orderha pOrders = fileOrderReader(addr);
        Orderha pTOrders = fileOrderReader("DB/Order.json");
        int t=0;
        for (int i=0 ;i<pOrders.orders.size() ;i++) {
            if (pOrders.orders.get(i).get(0).code == orderID){
                pOrders.orders.remove(i);
                pTOrders.orders.set(orderID-1, null);
                t=1;
                break;
            }
        }
        if (t==0)
            System.out.println("There is no order with this code.");
        orderWriter(pOrders, addr);
        orderWriter(pTOrders, "DB/Order.json");
    }

    public static void listPrinter (String command) throws IOException {
        ArrayList<Kala> kalaha = tKalaha();
        if (command.equals("-r")) {
            System.out.println("+-----------------+------------+------------+------------+");
            System.out.println("| Good name       | Inventory  | Good ID    | Price(IRR) |");
            System.out.println("+-----------------+------------+------------+------------+");
            for (int i = 0; i < kalaha.size(); i++) {
                System.out.print("| " + kalaha.get(i).name);
                for (int j = 0; j < 16 - kalaha.get(i).name.length(); j++) {
                    System.out.print(" ");
                }
                System.out.print("| ");
                System.out.print(kalaha.get(i).quantity + " " + kalaha.get(i).type);
                String num = kalaha.get(i).quantity + "";
                for (int j = 0; j < 11 - num.length() - kalaha.get(i).type.length(); j++) {
                    System.out.print(" ");
                }
                num = kalaha.get(i).code + "";
                System.out.print("| " + kalaha.get(i).code);
                for (int j = 0; j < 10 - num.length(); j++) {
                    System.out.print(" ");
                }
                num = kalaha.get(i).price + "";
                System.out.print("| " + kalaha.get(i).price);
                for (int j = 0; j < 11 - num.length(); j++) {
                    System.out.print(" ");
                }
                System.out.print("|");
                System.out.println("");
                System.out.print("----------");
                System.out.println("");

            }
        }

        if (command.equals("-i")){
            System.out.println("+-----------------+------------+------------+------------+");
            System.out.println("| Good name       | Inventory  | Good ID    | Price(IRR) |");
            System.out.println("+-----------------+------------+------------+------------+");
            for (int i = 0; i < kalaha.size(); i++) {
                if (kalaha.get(i).quantity>0) {
                    System.out.print("| " + kalaha.get(i).name);
                    for (int j = 0; j < 16 - kalaha.get(i).name.length(); j++) {
                        System.out.print(" ");
                    }
                    System.out.print("| ");
                    System.out.print(kalaha.get(i).quantity + " " + kalaha.get(i).type);
                    String num = kalaha.get(i).quantity + "";
                    for (int j = 0; j < 11 - num.length() - kalaha.get(i).type.length(); j++) {
                        System.out.print(" ");
                    }
                    num = kalaha.get(i).code + "";
                    System.out.print("| " + kalaha.get(i).code);
                    for (int j = 0; j < 10 - num.length(); j++) {
                        System.out.print(" ");
                    }
                    num = kalaha.get(i).price + "";
                    System.out.print("| " + kalaha.get(i).price);
                    for (int j = 0; j < 11 - num.length(); j++) {
                        System.out.print(" ");
                    }
                    System.out.print("|");
                    System.out.println("");
                    System.out.println("----------");
                }
            }
        }

        if (command.equals("-n")){
            System.out.println("+-----------------+------------+------------+------------+");
            System.out.println("| Good name       | Inventory  | Good ID    | Price(IRR) |");
            System.out.println("+-----------------+------------+------------+------------+");
            for (int i = 0; i < kalaha.size(); i++) {
                if (kalaha.get(i).quantity==0) {
                    System.out.print("| " + kalaha.get(i).name);
                    for (int j = 0; j < 16 - kalaha.get(i).name.length(); j++) {
                        System.out.print(" ");
                    }
                    System.out.print("| ");
                    System.out.print(kalaha.get(i).quantity + " " + kalaha.get(i).type);
                    String num = kalaha.get(i).quantity + "";
                    for (int j = 0; j < 11 - num.length() - kalaha.get(i).type.length(); j++) {
                        System.out.print(" ");
                    }
                    num = kalaha.get(i).code + "";
                    System.out.print("| " + kalaha.get(i).code);
                    for (int j = 0; j < 10 - num.length(); j++) {
                        System.out.print(" ");
                    }
                    num = kalaha.get(i).price + "";
                    System.out.print("| " + kalaha.get(i).price);
                    for (int j = 0; j < 11 - num.length(); j++) {
                        System.out.print(" ");
                    }
                    System.out.print("|");
                    System.out.println("");
                    System.out.println("----------");
                    System.out.println("");
                }
            }
        }

        if (command.equals("-o")){
            Orderha pTOrders = fileOrderReader("DB/Order.json");
            for (int i=0 ; i<pTOrders.orders.size(); i++){
                if (pTOrders.orders.get(i)!=null){
                    System.out.println("Order : "+ pTOrders.orders.get(i).get(0).code +" , Good Name : " + pTOrders.orders.get(i).get(0).name + " , Quantity : " + pTOrders.orders.get(i).get(0).quantity);
                }
            }
        }

        if (command.equals("-ho")){
            Orderha pTOrders = fileOrderReader("DB/POrders.json");
            for (int i=0 ; i<pTOrders.orders.size(); i++){
                if (pTOrders.orders.get(i)!=null){
                    System.out.println("Order : "+ pTOrders.orders.get(i).get(0).code +" , Good Name : " + pTOrders.orders.get(i).get(0).name + " , Quantity : " + pTOrders.orders.get(i).get(0).quantity);
                }
            }
        }
    }

    public static ArrayList<String> ingReader() {
        Gson load = new Gson();
        try {
            FileReader fileReader = new FileReader("DB/Ingridiants.json");
            Ingridiants ings = load.fromJson(fileReader,Ingridiants.class);
            fileReader.close();
            if (ings == null){
                return null;
            }
            else
                return ings.ingridiants;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<Kala> tKalaha () throws IOException {
        ArrayList<String> ings = ingReader();
        ArrayList<Kala> kalaha = new ArrayList<>();
        int a;
        if (ings == null){
            a=0;
        }
        else {
            a = ings.size();
        }
        for (int i=0; i<a ; i++){
            String address = "DB/Ingridiants/"+ ings.get(i) +".json";
            kalaha.add(fileKalaReader(address));
        }
        return kalaha;
    }

    public static int kalaChecker (int bought, String addr) throws IOException {
        Kala kala = fileKalaReader(addr);
        if (kala.quantity - bought <0)
            return -1;
        else
            return 1;
    }



}
