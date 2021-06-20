import LevelDesign.Level;

import java.io.IOException;
import java.util.ArrayList;
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


    public void Menu() throws IOException {

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
                          cageProcess(Integer.parseInt(split[1]),Integer.parseInt(split[2]),playerLevel);

                    } else if (command.startsWith("turn") && split.length == 2) {
                        turnCounter = Integer.parseInt(split[1]);
                        turnCheck = true;

                    } else if (command.startsWith("truck load") && split.length == 3) {
                        truckLoadProcess(playerLevel, split[2]);

                    } else if (command.startsWith("truck unload") && split.length == 3) {
                        truckUnloadProcess(playerLevel, split[2]);

                    } else if (command.equals("truck go")) {
                        if (playerLevel.motorCycle.capacity != 15) {
                            playerLevel.motorCycle.counter = 0;
                        } else {
                            System.out.println(ANSI_RED + "The truck is empty now!" + ANSI_RESET);
                        }

                    } else if (command.startsWith("build")) {
                        buildProcess(playerLevel, split[1]);
                    } else if (command.equals("end")) {
                        playerLevel = manager.levelEnd(playerLevel);
                    } else if (command.equals("inquiry")) {
                        printInfoProcess(playerLevel);
                    } else if (command.startsWith("upgrade") && split.length == 2) {
                        upgradeFactoryProcess(playerLevel, split[1]);

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
            Menu();
        }
    }

}




