package logic;

import java.io.File;
import java.util.ArrayList;

public class Rename<T extends Renamer> {
    private T processor;
    private File target;
    private ArrayList<Dictate> arrayList;

    private int progress = 1;

    public Rename(File dir, T type) {
        processor = type;
        target = dir;
        arrayList = new ArrayList<>();
    }

    //Walk through the target directory
    public void initiate() {
        walk(target);

        for (Dictate item : arrayList) {
            item.setTotal(arrayList.size());
        }
    }

    //Return true if no conflicts found, otherwise false
    public boolean preprocess() {
        for (int index = 0; index < arrayList.size(); index++) {
            File file = new File(arrayList.get(index).getString1());
            String parent = file.getParent();
            String name = file.getName();
            String string2 = parent + File.separator + processor.process(name);

            arrayList.get(index).setString2(string2);

            for (int next = index + 1; next < arrayList.size() && arrayList.get(next).getString1().startsWith(file.getAbsolutePath() + File.separator); next++) {
                String string1 = arrayList.get(next).getString1();
                string1 = replaceStart(string1, file.getAbsolutePath(), string2);

                arrayList.get(next).setString1(string1);
                arrayList.get(next).setString2(string1);
            }
        }

        return processor.checkProcess(arrayList);
    }

    //Return true if no conflicts found, otherwise false
    public boolean reverseProcess() {
        for (int index = 0; index < arrayList.size(); index++) {
            File file = new File(arrayList.get(index).getString1());
            String parent = file.getParent();
            String name = file.getName();

            String string2;

            try {
                string2 = parent + File.separator + processor.reverse(name);
            } catch (Exception e) {
                return false;
            }

            arrayList.get(index).setString2(string2);

            for (int next = index + 1; next < arrayList.size() && arrayList.get(next).getString1().startsWith(file.getAbsolutePath() + File.separator); next++) {
                String string1 = arrayList.get(next).getString1();
                string1 = replaceStart(string1, file.getAbsolutePath(), string2);

                arrayList.get(next).setString1(string1);
                arrayList.get(next).setString2(string1);
            }
        }

        return processor.checkReverse(arrayList);
    }

    public ArrayList getActionArray() {
        return arrayList;
    }

    //Method for debugging
    public void showArrayList(int index) {
        if (index != 0 && index != 1 && index != 2) {
            System.out.println("Invalid index");
            return;
        }

        for (Dictate item : arrayList) {
            switch (index) {
                case 0:
                    System.out.println(item.getString0());
                    break;
                case 1:
                    System.out.println(item.getString1());
                    break;
                case 2:
                    System.out.println(item.getString2());
                    break;
            }
        }
    }

    //Method for debugging
    public void action() {
        for (Dictate item : arrayList) {
            item.rename();
        }
    }

    private void walk(File folder) {
        for (File item : folder.listFiles()) {
            Dictate dictate = new Dictate(item.getAbsolutePath());
            dictate.setProgress(progress);
            arrayList.add(dictate);
            progress++;

            if (item.isDirectory()) {
                walk(item);
            }
        }
    }

    private String replaceStart(String text, String searchString, String replacement) {
        String result;

        if (text.startsWith(searchString)) {
            result = replacement + text.substring(searchString.length());
        } else {
            result = text;
        }

        return result;
    }
}
