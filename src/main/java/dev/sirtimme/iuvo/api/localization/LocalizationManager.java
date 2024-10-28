package dev.sirtimme.iuvo.api.localization;

import net.dv8tion.jda.api.interactions.DiscordLocale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.*;

public class LocalizationManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(LocalizationManager.class);
    private static final Map<DiscordLocale, ResourceBundle> bundles = new HashMap<>();

    public static void addBundles(final String baseName, final DiscordLocale... discordLocales) {
        for (final var discordLocale : discordLocales) {
            final var locale = discordLocale.toLocale();
            try {
                final var userBundle = ResourceBundle.getBundle(baseName, locale);
                bundles.put(discordLocale, userBundle);

                LOGGER.info("Loaded bundle for locale '{}'", locale);
            } catch (final MissingResourceException error) {
                LOGGER.warn("Loading of bundles for locale '{}' failed: {}", locale, error.getMessage());
            }
        }
    }

    public static String getResponse(final String key, final DiscordLocale locale, final Object... values) {
        final var bundle = getBundle(key, locale);
        final var template = new MessageFormat(bundle.getString(key));

        return template.format(values);
    }

    private static ResourceBundle getBundle(final String key, final DiscordLocale locale) {
        final var bundle = bundles.get(locale);

        if (bundle == null) {
            return bundles.get(DiscordLocale.ENGLISH_US);
        }

        return bundle;
    }
}