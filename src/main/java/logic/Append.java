package logic;

import java.io.File;
import java.util.ArrayList;

public class Append implements Renamer {
    public String process(String string) {
        return string + ".snoopaway";
    }

    public String reverse(String string) {
        String result;

        if (string.endsWith(".snoopaway")) {
            result = string.substring(0, string.length() - 10);
        } else {
            result = string;
        }

        return result;
    }

    //TODO: more accurate conflict checking
    public boolean checkProcess(ArrayList<Dictate> arrayList) {
        for (Dictate item : arrayList) {
            if (new File(item.getString0()).getName().endsWith(".snoopaway")) {
                return false;
            }
        }

        return true;
    }

    //TODO: more accurate conflict checking
    public boolean checkReverse(ArrayList<Dictate> arrayList) {
        for (Dictate item : arrayList) {
            if (!new File(item.getString0()).getName().endsWith(".snoopaway")) {
                return false;
            }
        }

        return true;
    }
}
