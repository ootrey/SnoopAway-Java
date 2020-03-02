package assistant;

import logic.*;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Assistant {
    private File target;
    private Scanner scanner;
    private int task;

    public void launch() {
        System.out.println("Prevent Snooping");
        System.out.println("1. Prefix dots");
        System.out.println("2. Add file extensions");
        System.out.println("3. Scramble filenames");
        System.out.println("Restore Your Files");
        System.out.println("4. Remove dots");
        System.out.println("5. Remove appended extensions");
        System.out.println("6. Restore Scrambled files");
        System.out.println();

        System.out.println("Select a task to perform by entering its preceding number.");

        while (true) {
            scanner = new Scanner(System.in);

            System.out.print("SnoopAway> ");

            try {
                task = Integer.valueOf(scanner.nextLine());

                if (task >= 1 && task <= 6) {
                    break;
                }

                System.out.println("Sorry, unknown number.");
            } catch (Exception e) {
                System.out.println("Sorry, invalid input.");
            }
        }

        System.out.println("Enter the folder to work on.");

        while (true) {
            System.out.print("SnoopAway> ");

            String path = scanner.nextLine();
            target = new File(path);

            if (path.equals("")) {
                System.out.println("Please enter the folder.");
                continue;
            }

            if (!target.exists()) {
                System.out.println(path + " does not exist.");
                continue;
            }

            if (!target.isDirectory()) {
                System.out.println(path + " is not a directory.");
                continue;
            }

            break;
        }

        switch (task) {
            case 1:
                performPrefix();
                break;
            case 2:
                performAppend();
                break;
            case 3:
                performScramble();
                break;
            case 4:
                reversePrefix();
                break;
            case 5:
                reverseAppend();
                break;
            case 6:
                reverseScramble();
                break;
        }
    }

    private void performPrefix() {
        Rename<Prefix> rename = new Rename<>(target, new Prefix());
        rename.initiate();
        System.out.println("Scanning...");
        boolean noConflict = rename.preprocess();
        if (!noConflict) {
            System.out.println("Conflicts found, task aborted.");
            return;
        }
        ArrayList<Dictate> arrayList = rename.getActionArray();
        inquireAndPerformActionList(arrayList, 0);
    }

    private void reversePrefix() {
        Rename<Prefix> rename = new Rename<>(target, new Prefix());
        rename.initiate();
        System.out.println("Scanning...");
        boolean noConflict = rename.reverseProcess();
        if (!noConflict) {
            System.out.println("Conflicts found, task aborted.");
            return;
        }
        ArrayList<Dictate> arrayList = rename.getActionArray();
        inquireAndPerformActionList(arrayList, 2);
    }

    private void performScramble() {
        Rename<Scramble> rename = new Rename<>(target, new Scramble());
        rename.initiate();
        System.out.println("Scanning...");
        boolean noConflict = rename.preprocess();
        if (!noConflict) {
            System.out.println("Conflicts found, task aborted.");
            return;
        }
        ArrayList<Dictate> arrayList = rename.getActionArray();
        inquireAndPerformActionList(arrayList, 0);
    }

    private void reverseScramble() {
        Rename<Scramble> rename = new Rename<>(target, new Scramble());
        rename.initiate();
        System.out.println("Scanning...");
        boolean noConflict = rename.reverseProcess();
        if (!noConflict) {
            System.out.println("Conflicts found, task aborted.");
            return;
        }
        ArrayList<Dictate> arrayList = rename.getActionArray();
        inquireAndPerformActionList(arrayList, 2);
    }

    private void performAppend() {
        Rename<Append> rename = new Rename<>(target, new Append());
        rename.initiate();
        System.out.println("Scanning...");
        boolean noConflict = rename.preprocess();
        if (!noConflict) {
            System.out.println("Conflicts found, task aborted.");
            return;
        }
        ArrayList<Dictate> arrayList = rename.getActionArray();
        inquireAndPerformActionList(arrayList, 0);
    }

    private void reverseAppend() {
        Rename<Append> rename = new Rename<>(target, new Append());
        rename.initiate();
        System.out.println("Scanning...");
        boolean noConflict = rename.reverseProcess();
        if (!noConflict) {
            System.out.println("Conflicts found, task aborted.");
            return;
        }
        ArrayList<Dictate> arrayList = rename.getActionArray();
        inquireAndPerformActionList(arrayList, 2);
    }

    private void inquireAndPerformActionList(ArrayList<Dictate> arrayList, int index) {
        System.out.println("Scanning completed, " + arrayList.size() + " files found.");
        System.out.println("Confirm action? (y/n)");
        System.out.print("SnoopAway> ");
        String confirm = scanner.nextLine();

        if (confirm.equalsIgnoreCase("y")) {
            BufferedOutputStream stream = new BufferedOutputStream(System.out);

            try {
                for (Dictate item : arrayList) {
                    StringBuilder string = new StringBuilder();

                    switch (index) {
                        case 0:
                            string.append("[")
                                    .append(item.getProgress()).append("/")
                                    .append(item.getTotal()).append("] ")
                                    .append(item.getString0()).append("\n");
                            break;
                        case 1:
                            string.append("[")
                                    .append(item.getProgress()).append("/")
                                    .append(item.getTotal()).append("] ")
                                    .append(item.getString1()).append("\n");
                            break;
                        case 2:
                            string.append("[")
                                    .append(item.getProgress()).append("/")
                                    .append(item.getTotal()).append("] ")
                                    .append(item.getString2()).append("\n");
                            break;
                    }

                    stream.write(string.toString().getBytes());
                    item.rename();
                }

                stream.flush();
            } catch (IOException e) {
                try {
                    stream.flush();
                } catch (IOException exc) {
                    exc.getStackTrace();
                }

                e.getStackTrace();
            }

            System.out.println("Task completed. Bye!");
        } else {
            System.out.println("Task aborted.");
        }
    }
}
