package me.gasthiiml.nicks.utils;

import lombok.experimental.UtilityClass;

import java.util.regex.Pattern;

@UtilityClass
public class ChatColorUtils {

    private final Pattern FORMAT_STRIP = Pattern.compile("(?i)" + '§' + "[K-OR]");
    private final Pattern COLOR_STRIP = Pattern.compile("(?i)" + '§' + "[0-9A-F]");

    public String stripFormats(String input) {
        return input == null ? null : FORMAT_STRIP.matcher(input).replaceAll("");
    }

    public String stripColors(String input) {
        return input == null ? null : COLOR_STRIP.matcher(input).replaceAll("");
    }

}
