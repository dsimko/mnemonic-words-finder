package com.wickeria.mnemonic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Purpose of this class is finding words from given dictionary which contains
 * given characters. The main aim is finding relevant words for memorizing
 * numbers by technique called Mnemonic system
 * https://en.wikipedia.org/wiki/Mnemonic_major_system
 * 
 * For usage please see {@link WordsFinderTest}.
 * 
 */
public class WordsFinder {

    private final String[] dictionary;

    public WordsFinder(String[] dictionary) {
        this.dictionary = dictionary;
    }

    /**
     * Finds words which contains given characters and doesn't contains rest of
     * characters from all.
     * 
     * @param contains
     *            e.g. "tnk" which could mean 129
     * @param all
     *            e.g. "tnmrpbsvk" which could mean 123456789
     * @return list of found words for example {"tank", "tatínka", "tenký"}
     * 
     */
    public List<String> find(String contains, String all) {
        List<String> words = new ArrayList<String>();
        Pattern pattern = Pattern.compile(createRegex(contains, all));
        for (String word : dictionary) {
            Matcher matcher = pattern.matcher(word);
            if (matcher.find()) {
                words.add(word);
            }
        }
        return words;
    }

    /**
     * Creates regular expression for finding words.
     * 
     * @param contains
     *            e.g. "tnk"
     * @param all
     *            e.g. "tnmrpbsvk"
     * @return regex based on given params e.g.
     *         ^t(?=.*[n].*[k])((?!m)(?!r)(?!p)(?!b)(?!s)(?!v).)*$
     */
    private static String createRegex(String contains, String all) {
        StringBuilder pattern = new StringBuilder();
        pattern.append("^");
        pattern.append(contains.substring(0, 1));
        createContainsGroups(contains.substring(1), pattern);
        createNotContainsGroups(contains, all, pattern);
        pattern.append("*$");
        return pattern.toString();
    }

    private static void createContainsGroups(String contains, StringBuilder pattern) {
        pattern.append("(?=");
        for (String s : contains.split("(?!^)")) {
            pattern.append(".*[" + s + "]");
        }
        pattern.append(")");
    }

    private static void createNotContainsGroups(String contains, String all, StringBuilder pattern) {
        pattern.append("(");
        for (String s : Arrays.stream(all.split("(?!^)")).filter(s -> !contains.contains(s)).toArray(String[]::new)) {
            pattern.append("(?!" + s + ")");
        }
        pattern.append(".)");
    }

}
