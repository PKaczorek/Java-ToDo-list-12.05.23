package warsztat01;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import java.util.ArrayList;
import java.util.Scanner;

public class Main01 {
    public static class ToDoListApp {
        public static ArrayList<String> tasks = new ArrayList<>();
        public static Scanner scanner = new Scanner(System.in);
        public static String fileName = "tasks.txt";

        public static void main(String[] args) {
            loadTasks();
            System.out.println(ConsoleColors.GREEN + "To-Do list:");
            System.out.print(ConsoleColors.RESET);
            MenuText();
            String input;

            do {
                System.out.println("Enter command: ");
                input = scanner.nextLine();
                exeCommands(input);
            } while (!input.equals("exit"));
            saveTasks();
            System.out.println("Closing program.");
        }

        public static void MenuText() {
            System.out.println("Available commands:");
            System.out.println(ConsoleColors.GREEN_UNDERLINED + "list - Shows tasks");
            System.out.println(ConsoleColors.BLUE + "add (task) - adds new task");
            System.out.println(ConsoleColors.YELLOW + "remove (index of task) - removes task from list");
            System.out.println(ConsoleColors.RED + "exit - exits application");
            System.out.print(ConsoleColors.RESET);
        }
        public static void exeCommands(String input) {
            String[] exe = input.split(" ", 2);
            String command = exe[0];
            switch (command) {
                case "list":
                    listTask();
                    break;
                case "add":
                    addTask(exe[1]);
                    break;
                case "remove":
                    try {
                        int index = Integer.parseInt(exe[1]);
                        removeTask(index);
                    } catch (Exception e) {
                        System.out.println("incorrect task " + exe);
                        System.out.println(e.getMessage());
                    }
                    break;
                case "exit":
                    break;
                default:
                    System.out.println("command error " + input);
                    break;
            }
        }

        public static void listTask() {
            if (tasks.isEmpty()) {
                System.out.println("Task list empty");
            } else {
                for (int i = 0; i < tasks.size(); i++) {
                    System.out.println(i + ". " +tasks.get(i));
                }
            }
        }

        public static void addTask(String task) {
            tasks.add(task);
            System.out.println("task - " + task + " - has been added to list.");
        }

        public static void removeTask(int index) {

            if (index >= 0 && index < tasks.size()) {
                String task = tasks.remove(index);
                System.out.println("Task removed: " + task);
            } else {
                System.out.println("Task error : " + index);
            }
        }

        public static class ConsoleColors {
            public static final String RED = "\033[0;31m";   //RED
            public static final String GREEN = "\033[0;32m";   //GREEN
            public static final String GREEN_UNDERLINED = "\033[4;32m";   //
            public static final String RESET = "\033[0m"; // Text Reset
            public static final String YELLOW = "\033[1;33m";
            public static final String BLUE = "\033[1;34m";
        }

        public static void loadTasks() {
            File file = new File(fileName);
            if (!file.exists()) {
                return;
            }
            try (Scanner fileScanner = new Scanner(file)) {
                while (fileScanner.hasNextLine()) {
                    String line = fileScanner.nextLine();
                    tasks.add(line);
                }
                System.out.println("Loading " + tasks.size() + " tasks from file");
            } catch (FileNotFoundException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }


        public static void saveTasks() {
            try (PrintWriter writer = new PrintWriter(fileName)) {
                for (String task : tasks) {
                    writer.println(task);
                }
                System.out.println("Saved " + tasks.size());
            } catch (FileNotFoundException e) {
                System.out.println("Error : " + e.getMessage());
            }

        }
    }
}
