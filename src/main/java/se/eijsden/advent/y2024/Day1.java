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

public class Day1 {
    Input input;

    public Day1() {
        try {
            input = readAndParseInput();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String solve() {
        Day1 day1 = new Day1();
        return "Day 1: Distance is " + day1.getDistance() + " and Similarity score is " + day1.getSimilarityScore();
    }

    private Integer getDistance() {
        int distance = 0;
        for (int i = 0; i < input.size(); i++) {
            distance += abs(input.getLeft().get(i) - input.getRight().get(i));
        }
        return distance;
    }

    private Integer getSimilarityScore() {
        Map<Integer, Integer> occurrencesRightList = new HashMap<>();
        input.getRight().forEach(value -> {
            occurrencesRightList.putIfAbsent(value, 0);
            occurrencesRightList.compute(value, (key, val) -> val + 1);
        });

        return input.getLeft().stream().map(value -> value * occurrencesRightList.getOrDefault(value, 0)).reduce(0, Integer::sum);
    }

    public Input readAndParseInput() throws IOException {
        Input input = new Input();
        Pattern pattern = Pattern.compile("(\\d+)\\s+(\\d+)");

        InputStream inputStream = Day1.class.getResourceAsStream("/y2024/day1_input.txt");
        InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        BufferedReader reader = new BufferedReader(streamReader);
        String line;
        while ((line = reader.readLine()) != null) {
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {

                int leftLocationId = Integer.parseInt(matcher.group(1));
                int rightLocationId = Integer.parseInt(matcher.group(2));

                input.left.add(leftLocationId);
                input.right.add(rightLocationId);
            }
        }
        input.sort();
        return input;
    }

    @Getter
    public static class Input {
        private final List<Integer> left = new ArrayList<>();
        private final List<Integer> right = new ArrayList<>();

        public void sort() {
            this.left.sort(Comparator.comparingInt(o -> o));
            this.right.sort(Comparator.comparingInt(o -> o));
        }

        public int size() {
            return left.size();
        }
    }
}