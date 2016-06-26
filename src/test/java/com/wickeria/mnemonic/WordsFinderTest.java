package com.wickeria.mnemonic;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * Unit test for WordsFinder.
 */
public class WordsFinderTest {

    @Test
    public void findWords() {
        WordsFinder finder = new WordsFinder(createDictionary());
        List<String> actual = finder.find("bsb", "tnmrpbsvk");
        List<String> expected = new ArrayList<>();
        expected.add("baseball");
        expected.add("bohoslužba");
        expected.add("bohoslužbách");
        expected.add("bohoslužbě");
        expected.add("bohoslužbu");
        expected.add("bohoslužby");
        expected.add("bohoslužeb");
        assertThat(actual, is(expected));
    }

    private String[] createDictionary() {
        try {
            String fileContent = new String(Files.readAllBytes(Paths.get(WordsFinder.class.getResource("dictionary_cz").toURI())), Charset.forName("UTF-8"));
            return fileContent.split("\n");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
