package dev.sirtimme.iuvo.api.localization;

import io.github.classgraph.ClassGraph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.*;

import static dev.sirtimme.iuvo.api.listener.interaction.InteractionListener.USER_LOCALE;

public class LocalizationManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(LocalizationManager.class);
    private final HashMap<Locale, MultiResourceBundle> bundles = new HashMap<>();

    public LocalizationManager() {
        registerBundles();
    }

    public String get(final String key, final Locale locale, final Object... values) {
        final var bundle = getBundle(locale);
        final var template = new MessageFormat(bundle.getString(key));

        return template.format(values);
    }

    public String get(final String key, final Object... values) {
        final var bundle = getBundle(USER_LOCALE.get());
        final var template = new MessageFormat(bundle.getString(key));

        return template.format(values);
    }

    private void registerBundles() {
        try (final var scanResult = new ClassGraph().acceptPackages("dev.sirtimme").acceptPathsNonRecursive("localization").scan()) {
            final var availableLocales = getAvailableLocales();

            for (final var locale : availableLocales) {
                final var resourceBundles = new ArrayList<ResourceBundle>();

                for (final var resource : scanResult.getResourcesWithExtension("properties")) {
                    final var bundleName = getResourceBundleName(resource.getPath());
                    final var resourceBundle = ResourceBundle.getBundle(bundleName, locale);

                    LOGGER.debug("Discovered resource bundle '{}' with locale '{}'", bundleName, locale);

                    resourceBundles.add(resourceBundle);
                }

                bundles.put(locale, new MultiResourceBundle(resourceBundles));
                LOGGER.info("Loaded resource bundles for locale '{}'", locale);
            }
        }
    }

    private String getResourceBundleName(final String resourcePath) {
        // limit is set to 2 to apply it (limit - 1) times
        return resourcePath.split("_", 2)[0];
    }

    private Set<Locale> getAvailableLocales() {
        final var availableLocales = new HashSet<Locale>();

        try (final var scanResult = new ClassGraph().acceptPackages("dev.sirtimme").acceptPathsNonRecursive("localization").scan()) {
            for (final var resource : scanResult.getResourcesWithExtension("properties")) {
                final var locale = parseResourcePath(resource.getPath());

                availableLocales.add(locale);
            }
        }

        return availableLocales;
    }

    private Locale parseResourcePath(final String resourcePath) {
        // commands_en_US.properties → en_US
        final var localeFilename = resourcePath.substring(resourcePath.indexOf("_") + 1, resourcePath.lastIndexOf("."));

        // locale only consists of language
        if (!localeFilename.contains("_")) {
            return Locale.of(localeFilename);
        }

        // locale consists of language and country
        final var language = localeFilename.substring(0, localeFilename.indexOf("_"));
        final var country = localeFilename.substring(localeFilename.indexOf("_") + 1);

        return Locale.of(language, country);
    }

    private ResourceBundle getBundle(final Locale locale) {
        final var bundle = bundles.get(locale);

        // fallback to american english if the provided locale is not supported
        if (bundle == null) {
            return bundles.get(Locale.US);
        }

        return bundle;
    }
}