package com.austincalkins.adventofcode.y2023.day4;

import com.austincalkins.adventofcode.util.IOUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Part2 {





    public static void main(String[] args) throws IOException {
        List<CardGame> numberOfCards = new ArrayList<>();
        List<CardGame> allCardGames = IOUtils.fileToLines("2023/day4.txt")
                .stream()
                .map(Part2::readCardGameData).toList();


        for(var game : allCardGames) {

            addWinningCardsToList(allCardGames, numberOfCards, new ArrayList<>(List.of(game)));
        }

        System.out.println(numberOfCards.size());
    }

    public static void addWinningCardsToList(List<CardGame> allCardGames, List<CardGame> totalNumberOfCards, List<CardGame> stackToProcess) {


        while(!stackToProcess.isEmpty()) {
            var cardGame = stackToProcess.get(0);
            stackToProcess.remove(0);

            totalNumberOfCards.add(cardGame);


            var matchingNumberCount = cardGame.getGameNumbers().stream()
                    .filter(number -> cardGame.getWinningNumbers().contains(number))
                    .count();

            System.out.printf("winning games for card: %s%n", matchingNumberCount);
            for(int index = 0; index < matchingNumberCount; index++) {
                var nextGameId = cardGame.getGameId() + (index + 1);
                System.out.printf("trying to find game: %d to add to stack %n", nextGameId);
                var nextCardGameToPlay = findCardGameByGameId(allCardGames, nextGameId);
                if(nextCardGameToPlay != null) {
                    stackToProcess.add(nextCardGameToPlay);
                } else {
                    System.out.printf("could not find next card to play for gameId: %d%n", nextGameId);
                }
            }
        }
    }

    private static CardGame findCardGameByGameId(List<CardGame> allCardGames, int gameId) {
        return allCardGames.stream()
                .filter(cardGame -> cardGame.getGameId() == gameId)
                .findFirst()
                .orElse(null);
    }

    public static CardGame readCardGameData(String gameString) {

        Pattern pattern = Pattern.compile("(.*):(.*)\\|(.*)");
        Matcher matcher = pattern.matcher(gameString);

        matcher.find();

        String cardNumber = matcher.group(1);
        String[] cardNumberParts = cardNumber.split(" ");
        return CardGame.builder()
                .gameId(Integer.parseInt(cardNumberParts[cardNumberParts.length - 1]))
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
