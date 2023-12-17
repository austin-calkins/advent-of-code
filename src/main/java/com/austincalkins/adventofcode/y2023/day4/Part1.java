package com.austincalkins.adventofcode.y2023.day4;

import com.austincalkins.adventofcode.util.IOUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Part1 {





    public static void main(String[] args) throws IOException {
        var score = IOUtils.fileToLines("2023/day4.txt")
                .stream()
                .map(Part1::readCardGameData)
                .map(Part1::countGameScore)
                .mapToInt(i -> i)
                .sum();

        System.out.println(score);
    }

    public static Integer countGameScore(CardGame cardGame) {

        var matchingNumberCount = cardGame.getGameNumbers().stream()
                .filter(number -> cardGame.getWinningNumbers().contains(number))
                .count();

        return Math.toIntExact(matchingNumberCount <= 2 ? matchingNumberCount : (long) Math.pow(2, matchingNumberCount - 1));
    }

    public static CardGame readCardGameData(String gameString) {

        Pattern pattern = Pattern.compile("(.*):(.*)\\|(.*)");
        Matcher matcher = pattern.matcher(gameString);

        matcher.find();

        String cardNumber = matcher.group(1);
        String[] cardNumberParts = cardNumber.split(" ");

        return CardGame.builder()
                .gameId(Character.getNumericValue(cardNumber.charAt(cardNumber.length() - 1)))
                .gameNumbers(parseNumbersFromString(matcher.group(3).trim()))
                .winningNumbers(parseNumbersFromString(matcher.group(2).trim()))
                .build();
    }

    private static List<Integer> parseNumbersFromString(String numbers) {
        return Arrays.stream(numbers.trim().split(" "))
                .filter(s -> !s.isEmpty())
                .map(Integer::parseInt)
                .toList();
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    private static class CardGame {
        private int gameId;
        private List<Integer> gameNumbers;
        private List<Integer> winningNumbers;
    }

}
