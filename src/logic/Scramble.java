package logic;

import java.util.ArrayList;
import java.util.Base64;

public class Scramble implements Renamer {
    public String process(String string) {
        return Base64.getEncoder().withoutPadding().encodeToString(string.getBytes());
    }

    public String reverse(String string) {
        return new String(Base64.getDecoder().decode(string));
    }

    //TODO: conflict checking
    public boolean checkProcess(ArrayList<Dictate> arrayList) {
        return true;
    }

    //TODO: conflict checking
    public boolean checkReverse(ArrayList<Dictate> arrayList) {
        return true;
    }
}
