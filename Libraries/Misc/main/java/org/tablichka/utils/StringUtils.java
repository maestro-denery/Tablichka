package org.tablichka.utils;

import java.util.List;
import java.util.stream.Collectors;

public class StringUtils {
    public static List<String> copyPartialInnerMatches(String whatToFind, List<String> whereToFind) {
        return whereToFind.stream().filter(s -> s.contains(whatToFind)).collect(Collectors.toList());
    }
}
