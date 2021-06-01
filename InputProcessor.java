import java.io.IOException;
import java.util.Scanner;

public class InputProcessor {
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_YELLOW = "\u001B[33m";

    Manager manager = new Manager();

    public InputProcessor(Manager manager) {
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

    private void startProcess() {

    }

    private void logoutProcess() {

    }

    private void settingsProcess() {

    }
    private void saveProcess(){

    }

    boolean flag_logout = true;


    public void Menu() throws IOException {
        while (flag_logout) {
            Scanner scanner = new Scanner(System.in);
            String Command;
            System.out.println("Please enter your username: ");
            String username = scanner.nextLine();
            boolean flag_end1 = true;

            while (!((Command = scanner.nextLine()).equals("logout"))) {
                String command = Command.toLowerCase();
                String[] split = command.split("\\s+");
                boolean bool = false;

                if (command.startsWith("log in")) {
                    if (!usernameCheckProcess(username)) {
                        System.out.println(ANSI_RED + "Error! because there is no such account." + ANSI_RESET);
                    } else {
                        System.out.println("Please enter your password: ");
                        while (!bool) {

                            String password = scanner.nextLine();
                            bool = loginProcess(username, password);
                            if (bool) {
                                System.out.println(ANSI_CYAN + "Logged in successfully!" + ANSI_RESET);
                                flag_end1 = false;
                            } else {
                                System.out.println(ANSI_RED + "Password incorrect! please enter your password again:" + ANSI_RED);
                            }

                        }
                    }

                } else if (command.startsWith("signup")) {
                    if (usernameCheckProcess(username)) {
                        System.out.println(ANSI_RED + "Error! because the username already exists." + ANSI_RESET);
                    } else {
                        System.out.println("Please enter a password to create a new account: ");
                        String password = scanner.nextLine();
                        signupProcess(username, password);
                        System.out.println(ANSI_CYAN + "Account created successfully!" + ANSI_RESET);
                        flag_end1 = false;
                    }

                } else if (command.startsWith("exit")) {
                    System.exit(0);

                } else {
                    System.out.println(ANSI_RED + "Invalid Input! Please enter your command again:" + ANSI_RESET)
                    ;
                }
                if (!flag_end1) {
                    break;
                }

            }

            System.out.println(ANSI_YELLOW + "=-=-=-=-=-=-=-=-=-=-=-=-=-MAIN MENU-=-=-=-=-=-=-=-=-=-=-=-=-=-=" + ANSI_YELLOW);
            System.out.println("Your commands are: ");
            System.out.println("1.Start [level]");
            System.out.println("2.log out");
            System.out.println("3.settings");
            System.out.println("4.exit");

            boolean flag_end = false;
            while (!(Command = scanner.nextLine()).equals("log out")) {
                String command = Command.toLowerCase();
                String[] split = command.split("\\s+");
                if (command.startsWith("start")) {
                    flag_logout = false;
                    flag_end1 = true;

                } else if (command.startsWith("settings")) {
                    settingsProcess();

                } else if (command.startsWith("exit")) {
                    System.exit(0);
                } else {
                    System.out.println(ANSI_RED + "Invalid Input! Please enter your command again:" + ANSI_RESET)
                    ;
                }
                if (flag_end1) {
                    break;
                }
            }

        }
    }

    public void gameplay() {
        Scanner scanner = new Scanner(System.in);
        String Command;
        while (!(Command = scanner.nextLine()).equals("exit")){
            String command = Command.toLowerCase();
            String[] split = command.split("\\s+");

            if(command.startsWith("save")){
                saveProcess();
            }
        }


    }

    public void run() throws IOException {
        while (flag_logout) {
            Menu();
        }
        gameplay();


    }

}




