package aoc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

class Day2Test {

    @Test
    void shouldReturnExpectedValueWhenCalculatePart1FromInputFile()
            throws IOException, URISyntaxException {
        // Given
        var fileURI = ClassLoader.getSystemResource("day2-input-test.txt").toURI();
        var lines = Files.readAllLines(Paths.get(fileURI));

        // When
        var result = new Day2().calculateTotalGamesAllowed(12, 13, 14, lines);

        // Then
        Assertions.assertEquals(8, result);
    }

    @Test
    void shouldReturnExpectedValueWhenCalculatePart2FromInputFile()
            throws IOException, URISyntaxException {
        // Given
        var fileURI = ClassLoader.getSystemResource("day2-input-test.txt").toURI();
        var lines = Files.readAllLines(Paths.get(fileURI));

        // When
        var result = new Day2().calculatePowerGames(lines);

        // Then
        Assertions.assertEquals(2286, result);
    }
}
