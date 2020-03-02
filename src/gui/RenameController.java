package gui;

import logic.*;

import javafx.concurrent.Task;

import java.io.File;
import java.util.ArrayList;

public class RenameController<T extends Renamer> extends Task<Void> {
    Rename rename;
    ArrayList<Dictate> arrayList;

    int index;
    boolean reverse;

    RenameController(File dir, T type) {
        rename = new Rename(dir, type);
    }

    void setResultIndex(int index) {
        this.index = index;
    }

    void setReverse(boolean reverse) {
        this.reverse = reverse;
    }

    protected Void call() throws Exception {
        rename.initiate();

        //Check conflicts
        if (dispatchPreprocess(rename) == false) {
            this.updateMessage(I18n.getString("RenameController.conflictsFound"));
            this.updateProgress(1, 1);
        } else {
            performActionList();
        }

        return null;
    }

    private boolean dispatchPreprocess(Rename rename) {
        if (!reverse) {
            return rename.preprocess();
        }

        if (reverse) {
            return rename.reverseProcess();
        }

        return false;
    }

    private void performActionList() {
        arrayList = rename.getActionArray();

        for (Dictate item : arrayList) {
            switch (index) {
                case 0:
                    this.updateMessage(item.getString0());
                    break;
                case 1:
                    this.updateMessage(item.getString1());
                    break;
                case 2:
                    this.updateMessage(item.getString2());
                    break;
            }

            this.updateProgress(item.getProgress(), item.getTotal());

            item.rename();
        }

        updateMessage(I18n.getString("RenameController.success"));
    }
}
