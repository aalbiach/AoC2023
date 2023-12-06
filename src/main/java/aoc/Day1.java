package aoc;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

public class Day1 {

    public static void main(String[] args) throws URISyntaxException, IOException {
        var fileURI = ClassLoader.getSystemResource("day1-input.txt").toURI();
        var lines = Files.readAllLines(Paths.get(fileURI));

        System.out.println(new Day1().calculeCoordinates(lines));
    }

    private static final Pattern STRING_NUMBER_PATTERN =
            Pattern.compile("(?=(?<number>\\d)|(?<string>one|two|three|four|five|six|seven|eight|nine))");

    public int calculeCoordinates(List<String> lines) {
        return lines.stream()
                .map(Day1::calculateNumberFromString)  // assuming the refactored code resides in a class named RefactoredClass
                .mapToInt(Integer::parseInt)
                .sum();
    }

    private static String calculateNumberFromString(String line) {
        var results = STRING_NUMBER_PATTERN.matcher(line).results().toList();
        var first = convertGroupToNumber(results.getFirst());
        var last = convertGroupToNumber(results.getLast());
        return first + "" + last;
    }

    private static int convertGroupToNumber(MatchResult result) {
        var number = result.group("number");
        var string = result.group("string");

        if (number != null) {
            return Integer.parseInt(number);
        } else {
            return convertStringToNumber(string);
        }
    }

    private static int convertStringToNumber(String number) {
        return switch (number) {
            case "one" -> 1;
            case "two" -> 2;
            case "three" -> 3;
            case "four" -> 4;
            case "five" -> 5;
            case "six" -> 6;
            case "seven" -> 7;
            case "eight" -> 8;
            case "nine" -> 9;
            default -> throw new IllegalStateException("Unexpected value: " + number);
        };
    }
}
