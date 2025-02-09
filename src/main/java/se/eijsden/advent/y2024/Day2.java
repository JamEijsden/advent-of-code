package se.eijsden.advent.y2024;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.lang.Math.abs;

public class Day2 {

    List<List<Integer>> input;

    public Day2() throws IOException {
        this(getInputData());
    }

    public Day2(List<List<Integer>> input) {
        this.input = input;
    }

    public static List<List<Integer>> getInputData() throws IOException {
        BufferedReader reader = getReader();
        List<List<Integer>> input = new ArrayList<>();
        final Pattern pattern = Pattern.compile("(\\d+)+");

        String line;
        while ((line = reader.readLine()) != null) {
            Matcher matcher = pattern.matcher(line);
            List<Integer> reportValues = new ArrayList<>();
            while (matcher.find()) {
                reportValues.add(Integer.parseInt(matcher.group(0)));
            }
            input.add(reportValues);
        }
        return input;
    }

    public Map.Entry<Integer, Integer> getSafeReports() {
        int safeReports = 0;
        int safeReportsWithDamper = 0;
        List<Integer> safeReportRows = new ArrayList<>();
        int row = 0;
        for (List<Integer> reportValues : this.input) {
            ReportResult reportResult = null;
            boolean unsafe = false;
            boolean usedDampener = false;
            row++;
            for (int currentIndex = 0; currentIndex < reportValues.size(); currentIndex++) {
                int currentValue = reportValues.get(currentIndex);
                if (currentIndex != 0) {
                    ReportResult currentReportResult = calcReportResult(reportResult, reportValues.get(currentIndex - 1), currentValue);

//                    // Handle Unsafe Reports
                    if (currentReportResult == ReportResult.UNSAFE) {
                        unsafe = true;
                        if (currentIndex == 1 && !usedDampener) {
                            reportValues.removeFirst();
                            currentIndex--;
                            usedDampener=true;
                        }
                        if (currentIndex > 2 && !usedDampener) {
                            int removeIndex = 1;
                            currentReportResult = calcReportResult(reportResult, reportValues.get(currentIndex - 2), currentValue);
                            unsafe = (currentReportResult == ReportResult.UNSAFE);

                            if(unsafe && currentIndex == reportValues.size() - 1) {
                                removeIndex = currentIndex;
                                currentReportResult = calcReportResult(reportResult, reportValues.get(currentIndex - 2), reportValues.get(currentIndex - 1));
                                unsafe = (currentReportResult == ReportResult.UNSAFE);
                            }

                            if(!unsafe) {
                                reportValues.remove(currentIndex - removeIndex);
                                currentIndex--;
                                usedDampener=true;
                            } else {
                                break;
                            }

                        }
                    }

                    if (!unsafe) {
                        reportResult = currentReportResult;
                    }

                }
            }

            if (unsafe) {
                continue;
            }
            safeReportRows.add(row);
            if(usedDampener) {
                safeReportsWithDamper++;
            }
            safeReports++;
        }
        return new AbstractMap.SimpleEntry<>(safeReports, safeReportsWithDamper);
    }

    private ReportResult calcReportResult(ReportResult previousReportResult, int previousValue, int currentValue) {
        int diff = previousValue - currentValue;
        ReportResult result = ReportResult.UNSAFE;

        if (isValidDiff(diff)) {
            result = getReportResult(previousReportResult, diff);
            if (result != ReportResult.UNSAFE) {
                return result;
            }
        }

        return result;
    }

    private ReportResult getReportResult(ReportResult previousReportResult, int diff) {
        ReportResult reportResult = diff < 0 ? ReportResult.INCREASING : ReportResult.DECREASING;
        return (previousReportResult == null || reportResult == previousReportResult) ? reportResult : ReportResult.UNSAFE;
    }

    private boolean isValidDiff(int diff) {
        return 0 < abs(diff) && abs(diff) <= 3;
    }

    public static String solve() {
        Day2 day2 = null;
        try {
            day2 = new Day2();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Map.Entry<Integer, Integer> safeReports = day2.getSafeReports();
        return "Day 2: " + safeReports.getKey() + " reports are safe in which " + safeReports.getValue() + " reports utilized dampener";
    }

    private static BufferedReader getReader() {
        InputStream inputStream = Day1.class.getResourceAsStream("/y2024/day2_input.txt");
        InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        return new BufferedReader(streamReader);
    }

    private static enum ReportResult {
        INCREASING,
        DECREASING,
        UNSAFE,
        IRREDEEMABLY_UNSAFE
    }
}