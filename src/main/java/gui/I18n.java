package gui;

import java.util.Locale;
import java.util.ResourceBundle;

public class I18n {
    static ResourceBundle bundle;

    static {
        Locale system = Locale.getDefault();
        bundle = ResourceBundle.getBundle("gui.bundles.LocaleBundle", system);
    }

    public static void override(String language) {
        Locale preference = Locale.forLanguageTag(language);
        bundle = ResourceBundle.getBundle("gui.bundles.LocaleBundle", preference);
    }

    static String getString(String key) {
        return bundle.getString(key);
    }
}
