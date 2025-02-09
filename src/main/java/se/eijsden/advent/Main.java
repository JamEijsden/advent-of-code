package se.eijsden.advent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.eijsden.advent.y2024.Day1;
import se.eijsden.advent.y2024.Day2;

import java.io.IOException;

public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) throws IOException {
        System.out.println("\nAdvent of code 2024:");
        System.out.println("\t" + Day1.solve());
        System.out.println("\t" + Day2.solve());
        System.out.println("\n");
    }

}
