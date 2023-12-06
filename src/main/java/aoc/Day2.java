package aoc;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class Day2 {

    public static void main(String[] args) throws URISyntaxException, IOException {
        var fileURI = ClassLoader.getSystemResource("day2-input.txt").toURI();
        var lines = Files.readAllLines(Paths.get(fileURI));

        System.out.println(new Day2().calculateTotalGamesAllowed(12, 13, 14, lines));
        System.out.println(new Day2().calculatePowerGames(lines));
    }

    private static final Pattern GAME_NUMBER_PATTERN = Pattern.compile("Game (?<game>\\d+):");
    private static final Pattern COLOR_PATTERN = Pattern.compile("(?: (?:(?<red>\\d+) red|(?<green>\\d+) green|(?<blue>\\d+) blue),?)+");

    public int calculateTotalGamesAllowed(int redCubesLimit, int greenCubesLimit, int blueCubesLimit, List<String> games) {
        return games.stream().mapToInt(game -> {
            int redMax = extractAndMaxColor("red", game);
            int greenMax = extractAndMaxColor("green", game);
            int blueMax = extractAndMaxColor("blue", game);

            if (redMax > redCubesLimit || greenMax > greenCubesLimit || blueMax > blueCubesLimit) {
                return 0;
            }

            var gameNumberMatcher = GAME_NUMBER_PATTERN.matcher(game);
            return gameNumberMatcher.find() ? Integer.parseInt(gameNumberMatcher.group("game")) : 0;
        }).sum();
    }

    public int calculatePowerGames(List<String> games) {
        return games.stream().mapToInt(game -> {
            int redMax = extractAndMaxColor("red", game);
            int greenMax = extractAndMaxColor("green", game);
            int blueMax = extractAndMaxColor("blue", game);

            return redMax * greenMax * blueMax;
        }).sum();
    }

    private int extractAndMaxColor(String color, String line) {
        var colorPatternMatcher = COLOR_PATTERN.matcher(line);
        return colorPatternMatcher.results()
                .map(colorMatcher -> Optional.ofNullable(colorMatcher.group(color))
                        .map(Integer::parseInt)
                        .orElse(0))
                .max(Integer::compareTo)
                .orElse(0);
    }

}