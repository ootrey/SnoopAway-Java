package logic;

import java.io.File;

public class Dictate {
    private int progress;
    private int total;
    //Original path
    private String string0;
    //Path to rename
    private String string1;
    //Final path
    private String string2;

    Dictate(String string) {
        setString0(string);
        setString1(string);
        setString2(string);
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public void setString0(String string0) {
        this.string0 = string0;
    }

    public void setString1(String string1) {
        this.string1 = string1;
    }

    public void setString2(String string2) {
        this.string2 = string2;
    }

    public int getProgress() {
        return progress;
    }

    public int getTotal() {
        return total;
    }

    public String getString0() {
        return string0;
    }

    public String getString1() {
        return string1;
    }

    public String getString2() {
        return string2;
    }

    public void rename() {
        File file1 = new File(string1);
        File file2 = new File(string2);

        file1.renameTo(file2);
    }
}
