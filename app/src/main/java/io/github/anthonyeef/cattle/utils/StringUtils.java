package io.github.anthonyeef.cattle.utils;

import org.jetbrains.annotations.NotNull;

/**
 *
 */
public class StringUtils {
    public static boolean isEmptyOrNull(String text) {
        return text == null || isEmpty(text);
    }

    public static boolean isEmpty(@NotNull String text) {
        return text.length() <= 0;
    }
}
