package logic;

import java.util.ArrayList;

public interface Renamer {
    String process(String string);

    String reverse(String string);

    //Return true if no conflicts found, otherwise false
    boolean checkProcess(ArrayList<Dictate> arrayList);

    //Return true if no conflicts found, otherwise false
    boolean checkReverse(ArrayList<Dictate> arrayList);
}
