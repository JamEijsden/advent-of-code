package se.eijsden.advent.y2024;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


class Day2Test {

    @Test
    public void testDefaultSuccessful() {
        List<List<Integer>> input = List.of(List.of(1,3,5,6,7,8));
        Day2 d = new Day2(input);
        assertEquals(1, (int) d.getSafeReports().getKey());
    }

    @Test
    public void testDampenerSuccessful() {
        List<List<Integer>> input = List.of(new ArrayList<>(List.of(1,3,9,6,7,8)));
        Day2 d = new Day2(input);
        assertEquals(1, (int) d.getSafeReports().getKey());
    }

    @Test
    public void testDampenerLastElementSuccessful() {
        List<List<Integer>> input = List.of(new ArrayList<>(List.of(1,3,4,6,7,19)));
        Day2 d = new Day2(input);
        assertEquals(1, (int) d.getSafeReports().getKey());
    }

    @Test
    public void testDampenerFirstElementSuccessful() {
        List<List<Integer>> input = List.of(new ArrayList<>(List.of(1,3,4,6,7,8)));
        Day2 d = new Day2(input);
        assertEquals(1, (int) d.getSafeReports().getKey());
    }

    @Test
    public void testDampenerCanOnlyBeCheckedOnceFail() {
        List<List<Integer>> input = List.of(new ArrayList<>(List.of(18,3,4,18,7,8)));
        Day2 d = new Day2(input);
        assertEquals(0, (int) d.getSafeReports().getKey());
    }

    @Test
    public void testDampenerFail() {
        List<List<Integer>> input = List.of(new ArrayList<>(List.of(18,3,4,6,7,8)));
        Day2 d = new Day2(input);
        assertEquals(0, (int) d.getSafeReports().getKey());
    }
}