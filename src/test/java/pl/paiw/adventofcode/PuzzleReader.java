package pl.paiw.adventofcode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PuzzleReader {

  public static List<String> getPuzzleLines(String filename) {
    List<String> result = Collections.emptyList();
    try (Stream<String> lines = Files.lines(Paths.get(filename).toAbsolutePath())) {
      result = lines.collect(Collectors.toList());
    } catch (IOException ex) {
      ex.printStackTrace();
    }

    return result;
  }
}
