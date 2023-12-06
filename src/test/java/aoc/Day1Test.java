package aoc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

class Day1Test {

    @ParameterizedTest
    @CsvSource(textBlock = """
            1aa0, 10
            a1b0c, 10
            aa1bb0cc, 10
            1aa2bb3cc0, 10
            aa1bb2cc3dd0ee, 10
            aa1bb, 11
            1one0, 10
            one2zero, 12
            onetwothree, 13,
            oneeight, 18
            eighthree, 83
            sevenine, 79
            """)
    void shouldReturnExpectedValueWhenCalculate(String line, int expected) {
        // Given
        var lines = List.of(line);

        // When
        var result = new Day1().calculeCoordinates(lines);

        // Then
        Assertions.assertEquals(expected, result);
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
            day1-input-part1-test.txt, 142
            day1-input-part2-test.txt, 281
            """)
    void shouldReturnExpectedValueWhenCalculateFromInputFile(String file, int expected)
            throws IOException, URISyntaxException {
        // Given
        var fileURI = ClassLoader.getSystemResource(file).toURI();
        var lines = Files.readAllLines(Paths.get(fileURI));

        // When
        var result = new Day1().calculeCoordinates(lines);

        // Then
        Assertions.assertEquals(expected, result);
    }

}
