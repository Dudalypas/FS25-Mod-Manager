package org.dudafs;

import java.util.Map;

public interface LocalizationResolver {
    static final Map<String, String> FALLBACK_TRANSLATIONS = Map.of(
            "$l10n_info_transmission_manual", "Manual",
            "$l10n_info_transmission_automatic", "Automatic",
            "$l10n_info_transmission_cvt", "CVT"
    );

    static String resolve(String key){
        return FALLBACK_TRANSLATIONS.get(key);
    };
}
