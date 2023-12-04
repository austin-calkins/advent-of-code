package com.austincalkins.adventofcode.y2023.day1;


import com.austincalkins.adventofcode.util.IOUtils;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.function.Function;

public class Part2 {


    public static void main(String[] args) throws IOException {

        LinkedHashMap<String, String> numberPairs = new LinkedHashMap<>();
        numberPairs.put("zero", "0");
        numberPairs.put("one", "1");
        numberPairs.put("two", "2");
        numberPairs.put("three", "3");
        numberPairs.put("four", "4");
        numberPairs.put("five", "5");
        numberPairs.put("six", "6");
        numberPairs.put("seven", "7");
        numberPairs.put("eight", "8");
        numberPairs.put("nine", "9");

        Function<String, String> elfCalibrationParser = line -> {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < line.length(); i++) {
                if (Character.isDigit(line.charAt(i))) {
                    builder.append(line.charAt(i));
                }

                for(var pair: numberPairs.entrySet()) {
                    if(line.indexOf(pair.getKey()) == i || line.lastIndexOf(pair.getKey()) == i) {
                        builder.append(pair.getValue());
                    }
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