package dev.sirtimme.iuvo.internal.localization;

import dev.sirtimme.iuvo.api.localization.MultiResourceBundle;
import net.dv8tion.jda.api.interactions.DiscordLocale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.*;

import static dev.sirtimme.iuvo.api.listener.interaction.InteractionListener.USER_LOCALE;

public class LocalizationManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(LocalizationManager.class);
    private static final HashMap<DiscordLocale, MultiResourceBundle> bundles = new HashMap<>();

    static {
        addBundles(DiscordLocale.ENGLISH_US, DiscordLocale.GERMAN);
    }

    public static String getResponse(final String key, final Object... values) {
        final var bundle = bundles.get(USER_LOCALE.get());
        final var template = new MessageFormat(bundle.getString(key));

        return template.format(values);
    }

    private static void addBundles(final DiscordLocale... discordLocales) {
        for (final var discordLocale : discordLocales) {
            final var locale = discordLocale.toLocale();
            try {
                final var responseBundle = ResourceBundle.getBundle("localization/responses", locale);
                final var combinedBundle = new MultiResourceBundle(responseBundle);

                bundles.put(discordLocale, combinedBundle);
                LOGGER.info("Loaded bundles for locale '{}'", locale);
            } catch (final MissingResourceException error) {
                LOGGER.warn("Loading of bundles for locale '{}' failed: {}", locale, error.getMessage());
            }
        }
    }
}