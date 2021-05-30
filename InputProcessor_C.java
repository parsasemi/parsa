import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class InputProcessor_C {
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_RED = "\u001B[31m";
    Manager_C manager_c;
    Scanner sc = new Scanner(System.in);

    public InputProcessor_C(Manager_C manager_c) {
        this.manager_c = manager_c;
    }

    public void printListGoodsProcess(String type) throws IOException {
        manager_c.printListGoods(type);
    }

    private void addOrderProcess(String[] split, String name) throws IOException {
        Random rand = new Random();
        int int_random = rand.nextInt(8999) + 1000;
        if (split[4].equals("kg")) {

            Good good = new Good(split[1], Double.parseDouble(split[3]), 1);
            Order order = new Order(name, good, manager_c.setDate(), int_random);
            manager_c.addOrder(order);

        } else if (split[4].equals("item")) {
            Good good = new Good(split[1], Double.parseDouble(split[3]), 2);
            Order order = new Order(name, good, manager_c.setDate(), int_random);
            manager_c.addOrder(order);

        }

    }

    private void deleteOrderProcess(int orderID) throws IOException {

        manager_c.deleteOrder(orderID);
    }

    private void welcomeProcess(String name) {
        manager_c.welcome(name);
    }

    public void run() throws IOException {
        boolean flag_end = false;
        while (!flag_end) {
            boolean flag = false;
            System.out.println(ANSI_PURPLE + "Please login...");
            String login = sc.nextLine();
            if (login.startsWith("login")) ;
            String[] loginSplit = login.split("\\s+");
            String customerID = loginSplit[1];
            boolean bool = false;

            if (manager_c.customerFirstStepValidation(customerID)) {
                System.out.println("Account found! Please enter your password: ");
                while (!bool) {
                    String password = sc.nextLine();
                    bool = manager_c.customerSecondStepValidation(customerID, password);
                    if (bool) {
                        System.out.println("Logged in successfully");
                    }
                    if (!bool) {
                        System.out.println("Password incorrect! please enter your password again: ");
                    }
                }
            } else {
                System.out.println("You don't have an account! Enter a password to create a new account: ");
                String password = sc.nextLine();
                User user = new User(customerID, password);
                manager_c.users.add(user);
                manager_c.writeUsersInfo();
                System.out.println("New account created!");

            }
            welcomeProcess(customerID);

            String command;
            while (!(command = sc.nextLine()).equals("logout")) {
                String[] split = command.split("\\s+");

                if (command.equals("ls -r")) {
                    printListGoodsProcess("r");
                    flag = true;

                } else if (command.equals("ls -i")) {
                    printListGoodsProcess("i");
                    flag = true;

                } else if (command.equals("ls -n")) {
                    printListGoodsProcess("n");
                    flag = true;

                } else if (command.startsWith("order")) {
                    flag = true;

                    if (split[1].equals("-d")) {
                        deleteOrderProcess(Integer.parseInt(split[2]));
                    } else if (split[2].equals("-c")) {
                        addOrderProcess(split, customerID);
                    }

                }
                if (!flag) {
                    System.out.println(ANSI_RED + "Wrong input! Please enter your input again: " + ANSI_RED);
                }

            }
            System.out.println("Thanks for your purchase " + customerID + "!");
            System.out.println("We'll be looking forward to see you again! :)");
        }
    }

}
