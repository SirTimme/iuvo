package dev.sirtimme.iuvo.internal.localization;

import java.text.MessageFormat;
import java.util.*;

import static dev.sirtimme.iuvo.api.listener.interaction.InteractionListener.USER_LOCALE;

public class LocalizationManager {
    public static String getResponse(final String key, final Object... values) {
        final var bundle = ResourceBundle.getBundle("localization/iuvo", USER_LOCALE.get());
        final var template = new MessageFormat(bundle.getString(key));

        return template.format(values);
    }
}