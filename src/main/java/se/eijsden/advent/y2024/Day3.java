package se.eijsden.advent.y2024;

import lombok.Getter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Math.abs;

public class Day3 {
    String input;

    public Day3() {
        try {
            input = readData();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String solve() {
        Day3 day3 = new Day3();
        return "Day 3: Product from corrupt data is " + day3.getProductFromCorruptData() + ", including do/don't " + day3.getProductFromCorruptDataWithDoDont();
    }

    private Integer getProductFromCorruptData() {
        Pattern pattern = Pattern.compile("(?:mul)\\((\\d+),(\\d+)\\)");
        Matcher matcher = pattern.matcher(input);
        int product = 0;
        while (matcher.find()) {
            product += getProductFromMatcher(matcher);
        }
        return product;
    }

    private Integer getProductFromCorruptDataWithDoDont() {
        Pattern pattern = Pattern.compile("(?:mul)\\((\\d+),(\\d+)\\)");
        int product = 0;
        boolean multiply = true;
        StringBuilder doDontString = new StringBuilder();
        StringBuilder mulString = new StringBuilder();
        for (char c : input.toCharArray()) {
            if (doDontString.isEmpty() && mulString.isEmpty()) {
                // Can only start one string at the time
                if ('d' == c) {
                    doDontString.append(c);
                }
                if ('m' == c) {
                    mulString.append(c);
                }
            } else if (!doDontString.isEmpty()) {
                // doDont has been started
                doDontString.append(c);
            } else {
                // mul has been started
                mulString.append(c);
            }

            if (')' == c) {
                if (doDontString.isEmpty()) {
                    if (multiply && !mulString.isEmpty()) {
                        Matcher matcher = pattern.matcher(mulString.toString());
                        while (matcher.find()) {
                            product += getProductFromMatcher(matcher);
                        }
                    }
                } else {
                    if (doDontString.toString().equals("do()")) {
                        multiply = true;
                    } else if (doDontString.toString().equals("don't()")) {
                        multiply = false;
                    }

                }
                mulString = new StringBuilder();
                doDontString = new StringBuilder();
            }
        }
        return product;
    }

    private int getProductFromMatcher(Matcher matcher) {
        int value1 = Integer.parseInt(matcher.group(1));
        int value2 = Integer.parseInt(matcher.group(2));
        return value1 * value2;
    }

    public String readData() throws IOException {
        InputStream inputStream = Day3.class.getResourceAsStream("/y2024/day3_input.txt");
        InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        BufferedReader reader = new BufferedReader(streamReader);
        String line;
        StringBuilder corruptData = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            corruptData.append(line);
        }
        return corruptData.toString();
    }

}