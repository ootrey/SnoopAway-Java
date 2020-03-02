import assistant.Assistant;
import gui.GUI;

public class SnoopAway {
    public static void main(String[] args) {
        if (args.length == 1 && args[0].equalsIgnoreCase("assistant")) {
            Assistant assistant = new Assistant();
            assistant.launch();
        } else if (args.length == 1 && args[0].equalsIgnoreCase("gui")) {
            javafx.application.Application.launch(GUI.class);
        } else if (args.length == 3 &&
                args[0].equalsIgnoreCase("gui") &&
                args[1].equals("--locale")) {
            gui.I18n.override(args[2]);
            javafx.application.Application.launch(GUI.class);
        }
    }
}