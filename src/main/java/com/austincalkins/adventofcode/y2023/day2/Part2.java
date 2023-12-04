package com.austincalkins.adventofcode.y2023.day2;

import com.austincalkins.adventofcode.util.IOUtils;
import lombok.Data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Part2 {
    public static void main(String[] args) throws IOException {


        List<Part2.Game> games = IOUtils.fileToLines("2023/day2.txt").stream()
                .map(Part2::parseGameFromLine).toList();


        long sum = 0;
        for(var game : games) {
            int maxBlue = game.getGameSet().stream().map(GameCounts::getBlueCount).mapToInt(i -> i).max().getAsInt();
            int maxGreen = game.getGameSet().stream().map(GameCounts::getGreenCount).mapToInt(i -> i).max().getAsInt();
            int maxRed = game.getGameSet().stream().map(GameCounts::getRedCount).mapToInt(i -> i).max().getAsInt();

            sum += (maxBlue * maxGreen * maxRed);
            }
        System.out.println(sum);
    }

    private static Part2.Game parseGameFromLine(String line) {
        Part2.Game game = new Part2.Game();
        String id = line.split(":")[0].split(" ")[1];
        game.setId(Integer.parseInt(id));

        String[] games = line.split(":")[1].split(";");

        game.setGameSet(new ArrayList<>());
        for(var gameSequence: games) {
            Part2.GameCounts gameCounts = new Part2.GameCounts();
            String[] counts = gameSequence.split(",");
            for(var count: counts) {
                gameCounts.setCount(count);
            }
            game.getGameSet().add(gameCounts);
        }

        return game;
    }

    private static boolean isGamePossible(Part1.Game game) {

        for(var gameSet : game.getGameSet()) {
            if(gameSet.getBlueCount() > 14 || gameSet.getRedCount() > 12 || gameSet.getGreenCount() > 13) {
                return false;
            }
        }


        return true;
    }

    @Data
    static class Game {

        private Integer id;
        private List<Part2.GameCounts> gameSet;
    }

    @Data
    static class GameCounts {
        private Integer redCount = 0;
        private Integer greenCount = 0;
        private Integer blueCount = 0;

        public void setCount(String count) {
            String number = count.trim().split(" ")[0];
            String color = count.trim().split(" ")[1];

            switch (color) {
                case "red" -> {redCount = Integer.parseInt(number);}
                case "green" -> {greenCount = Integer.parseInt(number);}
                case "blue" -> {blueCount = Integer.parseInt(number);}
            }
        }
    }
}
