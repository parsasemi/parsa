import java.util.Scanner;

public class InputProcessor {
    Manager manager = new Manager();

    private void usernameProcess(String username) {

    }

    private void passwordProcess(String password) {

    }

    private void loginProcess() {

    }

    private void signupProcess() {

    }

    private void startProcess() {

    }

    private void logoutProcess() {

    }

    private void settingsProcess() {

    }


    public void loginMenu() {
        Scanner scanner = new Scanner(System.in);
        String Command;
        while (!(Command = scanner.nextLine()).equals("logout")) {
            String command = Command.toLowerCase();

            String[] split = command.split("\\s+");

            if (command.startsWith("username")) {
                usernameProcess(split[1]);

            } else if (command.startsWith("password")) {
                passwordProcess(split[1]);

            } else if (command.startsWith("log in")) {
                loginProcess();

            } else if (command.startsWith("signup")) {
                signupProcess();

            }

        }
    }

    public void mainMenu() {
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

}




