package logic;

import java.io.File;
import java.util.ArrayList;

public class Prefix implements Renamer {
    public String process(String string) {
        return "." + string;
    }

    public String reverse(String string) {
        String result;

        if (string.startsWith(".")) {
            result = string.substring(1);
        } else {
            result = string;
        }

        return result;
    }

    //TODO: more accurate conflict checking
    public boolean checkProcess(ArrayList<Dictate> arrayList) {
        for (Dictate item : arrayList) {
            if (new File(item.getString0()).getName().startsWith(".")) {
                return false;
            }
        }

        return true;
    }

    //TODO: more accurate conflict checking
    public boolean checkReverse(ArrayList<Dictate> arrayList) {
        for (Dictate item : arrayList) {
            if (!new File(item.getString0()).getName().startsWith(".")) {
                return false;
            }
        }

        return true;
    }
}
