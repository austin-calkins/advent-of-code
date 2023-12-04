package com.austincalkins.adventofcode.y2023.day1;

import com.austincalkins.adventofcode.util.IOUtils;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.function.Function;

public class Part1 {


    public static void main(String[] args) throws IOException {

        Function<String, String> elfCalibrationParser = line -> {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < line.length(); i++) {
                if (Character.isDigit(line.charAt(i))) {
                    builder.append(line.charAt(i));
                }
            }
            return builder.toString();
        };

        long sum = IOUtils.fileToLines("2023/day1.txt").stream()
                .map(elfCalibrationParser)
                .map(line -> line.substring(0, 1).concat(line.substring(line.length() - 1)))
                .mapToInt(Integer::parseInt)
                .sum();

        System.out.println(sum);
    }
}