package aoc;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

public class Day3 {

    public static void main(String[] args) throws URISyntaxException, IOException {
        var fileURI = ClassLoader.getSystemResource("day3-input.txt").toURI();
        var lines = Files.readAllLines(Paths.get(fileURI));

        System.out.println(new Day3().sumEngineNumber(lines));
        System.out.println(new Day3().sumEngineGears(lines));
    }

    private static final String SYMBOL = "(?>.*[^\\d\\n.].*)";
    private static final Pattern NUMBER_PATTERN = Pattern.compile("\\d+");
    private static final Pattern ASTERISK_PATTERN = Pattern.compile("\\*");
    private static final Pattern ENDS_WITH_NUMBER_PATTERN = Pattern.compile("(\\d+)$");
    private static final Pattern STARTS_WITH_NUMBER_PATTERN = Pattern.compile("^(\\d+)");


    public int sumEngineNumber(List<String> lines) {
        int sum = 0;

        var size = lines.size();
        for (int i = 0; i < size; i++) {
            var readContext = new ReadContext(lines, i);

            var results = NUMBER_PATTERN.matcher(readContext.getCurrentLine()).results();
            for (MatchResult result : results.toList()) {
                var number = result.group();

                int startIndex = result.start();
                int endIndex = result.end();

                if (hasSymbolBetweenSubstring(readContext.getCurrentLine(), startIndex, endIndex)
                        || !readContext.isFirstLine() && hasSymbolBetweenSubstring(readContext.getPreviousLine(), startIndex, endIndex)
                        || !readContext.isLastLine() && hasSymbolBetweenSubstring(readContext.getNextLine(), startIndex, endIndex)) {
                    sum += Integer.parseInt(number);
                }
            }
        }

        return sum;
    }

    private boolean hasSymbolBetweenSubstring(String line, int startIndex, int endIndex) {
        var startInBounds = Math.max(startIndex - 1, 0);
        var endInBounds = Math.min(endIndex + 1, line.length());
        return line.substring(startInBounds, endInBounds).matches(SYMBOL);
    }

    public int sumEngineGears(List<String> lines) {
        int sum = 0;

        var size = lines.size();
        for (int i = 0; i < size; i++) {
            var readContext = new ReadContext(lines, i);
            var stack = new ArrayList<Integer>();

            var results = ASTERISK_PATTERN.matcher(readContext.getCurrentLine()).results();
            for (MatchResult result : results.toList()) {
                findSideNumber(readContext.getCurrentLine(), result.start(), result.end(), stack);
                findAdjacentNumber(readContext.getPreviousLine(), result.start(), result.end(), stack);
                findAdjacentNumber(readContext.getNextLine(), result.start(), result.end(), stack);

                if (stack.size() == 2) {
                    sum += stack.getFirst() * stack.getLast();
                }
                stack.clear();
            }
        }

        return sum;
    }

    private void findSideNumber(String line, int startIndex, int endIndex, List<Integer> stack) {
        var matcher = ENDS_WITH_NUMBER_PATTERN.matcher(line.substring(0, startIndex));
        if (matcher.find()) {
            stack.add(Integer.parseInt(matcher.group()));
        }

        matcher = STARTS_WITH_NUMBER_PATTERN.matcher(line.substring(endIndex));
        if (matcher.find()) {
            stack.add(Integer.parseInt(matcher.group()));
        }
    }

    private void findAdjacentNumber(String line, int startIndex, int endIndex, List<Integer> stack) {
        if (line != null) {
            var results = NUMBER_PATTERN.matcher(line).results();
            for (MatchResult result : results.toList()) {
                var number = result.group();

                var range = new Range(result.start(), result.end());
                if (range.contains(startIndex) || range.contains(endIndex)) {
                    stack.add(Integer.parseInt(number));
                }
            }
        }
    }

    static final class ReadContext {
        private final List<String> lines;
        private final int index;
        private final int totalLines;

        private String currentLine;
        private String previousLine;
        private String nextLine;

        ReadContext(List<String> lines, int index) {
            this.lines = lines;
            this.totalLines = lines.size();
            this.index = index;
        }

        boolean isFirstLine() {
            return index == 0;
        }

        boolean isLastLine() {
            return index == totalLines - 1;
        }

        String getCurrentLine() {
            if (currentLine == null) {
                currentLine = lines.get(index);
            }

            return currentLine;
        }

        String getPreviousLine() {
            if (previousLine == null) {
                previousLine = isFirstLine() ? null : lines.get(index - 1);
            }

            return previousLine;
        }

        String getNextLine() {
            if (nextLine == null) {
                nextLine = isLastLine() ? null : lines.get(index + 1);
            }

            return nextLine;
        }
    }

    static class Range {
        private final int low;
        private final int high;

        public Range(int low, int high) {
            this.low = low;
            this.high = high;
        }

        public boolean contains(int number) {
            return (number >= low && number <= high);
        }
    }
}
