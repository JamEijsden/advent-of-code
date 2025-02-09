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

import static java.lang.Math.abs;

public class Day2 {

    public Map.Entry<Integer, Integer> getSafeReports() throws IOException {
        BufferedReader reader = getReader();
        final Pattern pattern = Pattern.compile("(\\d+)+");

        int safeReports = 0;
        int safeReportsWithDamper = 0;
        String line;
        while ((line = reader.readLine()) != null) {
            Matcher matcher = pattern.matcher(line);
            int currentIndex = 0;
            List<Integer> reportValues = new ArrayList<>();
            ReportResult reportResult = null;

            while(matcher.find()) {
                reportValues.add(Integer.parseInt(matcher.group(0)));
                if(currentIndex != 0) {
                    reportResult = calcReportResult(reportResult, reportValues, currentIndex);
                    if(reportResult == ReportResult.UNSAFE) {
                        if(currentIndex > 2) {
                            reportResult = calcReportResult(reportResult, reportValues, currentIndex, true);
                            if(reportResult != ReportResult.UNSAFE && reportResult != ReportResult.IRREDEEMABLY_UNSAFE) {
                                safeReportsWithDamper++;
                            }
                        } else {
                            continue;
                        }
                    }
                }
                currentIndex++;
            }
            if(reportResult == ReportResult.UNSAFE || reportResult == ReportResult.IRREDEEMABLY_UNSAFE) {
                continue;
            }
            safeReports++;
        }
        return new AbstractMap.SimpleEntry<>(safeReports, safeReportsWithDamper);
    }

    private ReportResult calcReportResult(ReportResult previousReportResult, List<Integer> resultValues, int currentIndex) {
        return calcReportResult(previousReportResult, resultValues, currentIndex, false);
    }

    private ReportResult calcReportResult(ReportResult previousReportResult, List<Integer> resultValues, int currentIndex, boolean includeDampener) {
        int diff = resultValues.get(currentIndex - 1) - resultValues.get(currentIndex);
        if(isValidDiff(diff)) {
            return getReportResult(previousReportResult, diff);
        }

        if(includeDampener) {
            diff = resultValues.get(currentIndex - 2) - resultValues.get(currentIndex);
            if(isValidDiff(diff)) {
                return getReportResult(previousReportResult, diff);
            }
        }
        return ReportResult.UNSAFE;
    }

    private ReportResult getReportResult(ReportResult previousReportResult, int diff) {
        ReportResult reportResult = diff < 0 ? ReportResult.INCREASING : ReportResult.DECREASING;
        if(previousReportResult == null || reportResult == previousReportResult) {
            return reportResult;
        }
        return ReportResult.IRREDEEMABLY_UNSAFE;
    }

    private boolean isValidDiff(int diff) {
        return 0 < abs(diff) && abs(diff) <= 3;
    }

    public static String solve() {
        Map.Entry<Integer, Integer> safeReports = null;
        try {
            safeReports = (new Day2()).getSafeReports();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "Day 2: " +  safeReports.getKey() + " reports are safe in which " + safeReports.getValue() + " reports utilized dampener";
    }

    private BufferedReader getReader() {
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