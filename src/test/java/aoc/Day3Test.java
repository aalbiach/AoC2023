package aoc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

class Day3Test {

    @Test
    void shouldReturnExpectedValueWhenSumEngineNumber()
            throws IOException, URISyntaxException {
        // Given
        var fileURI = ClassLoader.getSystemResource("day3-input-test.txt").toURI();
        var lines = Files.readAllLines(Paths.get(fileURI));

        // When
        var result = new Day3().sumEngineNumber(lines);

        // Then
        Assertions.assertEquals(925, result);
    }

    @Test
    void shouldReturnExpectedValueWhenSumGears()
            throws IOException, URISyntaxException {
        // Given
        var fileURI = ClassLoader.getSystemResource("day3-input-test.txt").toURI();
        var lines = Files.readAllLines(Paths.get(fileURI));

        // When
        var result = new Day3().sumEngineGears(lines);

        // Then
        Assertions.assertEquals(62844, result);
    }
}
