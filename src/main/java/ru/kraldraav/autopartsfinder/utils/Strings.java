package ru.kraldraav.autopartsfinder.utils;

import java.util.ArrayList;
import java.util.List;

public interface Strings {

    public static boolean IsNullOrEmpty(String s) {
        return s.isEmpty() || s == null;
    }

    public static List<String> splitEqually(String text, int size) {
        // Give the list the right capacity to start with. You could use an array
        // instead if you wanted.
        List<String> ret = new ArrayList<String>((text.length() + size - 1) / size);

        for (int start = 0; start < text.length(); start += size) {
            ret.add(text.substring(start, Math.min(text.length(), start + size)));
        }
        return ret;
    }
}
