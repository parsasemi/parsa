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
        manager.signUp(username,password);

    }

    private void startProcess() {

    }

    private void logoutProcess() {

    }

    private void settingsProcess() {

    }

    public void loginMenu () throws IOException {
        Scanner scanner = new Scanner(System.in);
        String Command;
        System.out.println("Please enter your username: ");
        String username = scanner.nextLine();
        boolean flag_end = true;

        while (!((Command = scanner.nextLine()).equals("logout"))) {
            String command = Command.toLowerCase();
            boolean bool = false;

            String[] split = command.split("\\s+");

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
                            flag_end = false;
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
                    flag_end = false;
                }

            }else{
                System.out.println(ANSI_RED+"Invalid Input! Please enter your command again:"+ANSI_RESET)
                ;
            }
            if(!flag_end){
                break;
            }

        }
    }
    public void mainMenu () {
        System.out.println(ANSI_YELLOW+"You're in the main menu!"+ANSI_YELLOW);
        Scanner scanner = new Scanner(System.in);
        String Command;
        while (!(Command = scanner.nextLine()).equals("logout")) {
            String command = Command.toLowerCase();
            String[] split = command.split("\\s+");
            if (command.startsWith("start")) {
                startProcess();

            } else if (command.startsWith("log out")) {
                logoutProcess();

            } else if (command.startsWith("settings")) {
                settingsProcess();

            }
        }

    }

    public void run() throws IOException {
        loginMenu();
        mainMenu();

    }

}




