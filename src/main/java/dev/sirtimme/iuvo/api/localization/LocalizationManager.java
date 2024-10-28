package dev.sirtimme.iuvo.api.localization;

import net.dv8tion.jda.api.interactions.DiscordLocale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class LocalizationManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(LocalizationManager.class);
    private static final Map<DiscordLocale, ResourceBundle> internalBundles = new HashMap<>();
    private static final Map<DiscordLocale, ResourceBundle> userBundles = new HashMap<>();

    public static void addBundles(final String baseName, final DiscordLocale... discordLocales) {
        for (final var discordLocale : discordLocales) {
            final var locale = discordLocale.toLocale();
            try {
                // add user-defined bundles
                final var userBundle = ResourceBundle.getBundle(baseName, locale);
                userBundles.put(discordLocale, userBundle);

                // add iuvo-internal bundles
                final var iuvoBundle = ResourceBundle.getBundle("localization/iuvo", locale);
                internalBundles.put(discordLocale, iuvoBundle);

                LOGGER.info("Loaded bundles for locale '{}'", locale);
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
        if (key.startsWith("iuvo")) {
            return internalBundles.get(locale);
        }
        return userBundles.get(locale);
    }
}