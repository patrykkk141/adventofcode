package pl.paiw.adventofcode;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.junit.Test;
import pl.paiw.adventofcode.days.PasswordPolicyRow;

public class Results {

  private final List<String> puzzle_Day1 = PuzzleReader.getPuzzleLines("day1.txt");
  private final List<String> puzzle_Day2 = PuzzleReader.getPuzzleLines("day2.txt");
  private final List<String> puzzle_Day3 = PuzzleReader.getPuzzleLines("day3.txt");
  private final List<String> puzzle_Day4 = PuzzleReader.getPuzzleLines("day4.txt");

  @Test
  public void getResult_Day1() {
    List<Integer> puzzle = PuzzleUtils.parseStringsListToIntegersList(puzzle_Day1);
    int expected = 2020;

    for (int i = 0; i < puzzle.size(); i++) {
      for (int j = i + 1; j < puzzle.size(); j++) {
        if (puzzle.get(i) + puzzle.get(j) == expected)
          System.out.println("Result is: " + puzzle.get(i) * puzzle.get(j));
      }
    }
  }

  @Test
  public void getResult_Day1_1() {
    List<Integer> puzzle = PuzzleUtils.parseStringsListToIntegersList(puzzle_Day1);
    int expected = 2020;

    for (int i = 0; i < puzzle.size(); i++) {
      for (int j = i + 1; j < puzzle.size(); j++) {
        for (int k = j + 1; k < puzzle.size(); k++) {
          int tmp1 = puzzle.get(i);
          int tmp2 = puzzle.get(j);
          int tmp3 = puzzle.get(k);

          if (tmp1 + tmp2 + tmp3 == expected) {
            System.out.println("Result is: " + tmp1 * tmp2 * tmp3);
          }
        }
      }
    }
  }

  @Test
  public void getResult_Day2() {
    List<PasswordPolicyRow> puzzle = PuzzleUtils.parseToPasswordPolicyRow(puzzle_Day2);

    long validPasswords =
        puzzle.stream()
            .filter(
                row -> {
                  final long charOccurs =
                      row.getPassword().chars().filter(ch -> ch == row.getCharacter()).count();

                  return charOccurs >= row.getMinOccurs() && charOccurs <= row.getMaxOccurs();
                })
            .count();

    System.out.println("Valid password: " + validPasswords);
  }

  @Test
  public void getResult_Day2_1() {
    List<PasswordPolicyRow> puzzle = PuzzleUtils.parseToPasswordPolicyRow(puzzle_Day2);

    long validPasswords =
        puzzle.stream()
            .peek(System.out::println)
            .filter(
                row -> {
                  final String pass = row.getPassword();

                  return (pass.charAt(row.getMinOccurs() - 1) == row.getCharacter()
                          && pass.charAt(row.getMaxOccurs() - 1) != row.getCharacter()
                      || pass.charAt(row.getMinOccurs() - 1) != row.getCharacter()
                          && pass.charAt(row.getMaxOccurs() - 1) == row.getCharacter());
                })
            .count();

    System.out.println("Valid password: " + validPasswords);
  }

  @Test
  public void getResult_Day3() {
    List<String> puzzle = puzzle_Day3;

    int rows = puzzle.size();
    int cols = puzzle.get(0).length();
    String[][] puzz = new String[puzzle.size()][puzzle.get(0).length()];
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        puzz[i][j] = String.valueOf(puzzle.get(i).charAt(j));
      }
    }

    long result = 1;
    int[][] params = {{1, 1}, {3, 1}, {5, 1}, {7, 1}, {1, 2}};
    for (int k = 0; k < params.length; k++) {
      int count = 0;
      int currentRow;
      int currentCol;

      for (int i = params[k][1], j = params[k][0]; i < rows; i += params[k][1], j += params[k][0]) {
        currentRow = i;
        currentCol = j;
        if (currentCol > cols - 1) {
          currentCol = currentCol % cols;
        }

        if (puzz[currentRow][currentCol].equals("#")) count++;
      }
      System.out.println("count: " + count);
      result *= count;
    }

    System.out.println(result);
  }

  @Test
  public void getResult_Day4() {
    List<Map<String, String>> passports = PuzzleUtils.parsePassports(puzzle_Day4);
    Set<String> requiredPassportFields = Set.of("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid");

    long validPassports =
        passports.stream()
            .filter(passport -> passport.keySet().containsAll(requiredPassportFields))
            .count();

    System.out.println(String.format("Valid passports: %d", validPassports));
  }

  @Test
  public void getResult_Day4_1() {
    List<Map<String, String>> passports = PuzzleUtils.parsePassports(puzzle_Day4);
    Set<String> requiredPassportFields = Set.of("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid");
    Map<String, Predicate<String>> validators =
        Map.of(
            "byr",
            x -> x.matches("\\d{4}") && Integer.parseInt(x) >= 1920 && Integer.parseInt(x) <= 2002,
            "iyr",
            x -> x.matches("\\d{4}") && Integer.parseInt(x) >= 2010 && Integer.parseInt(x) <= 2020,
            "eyr",
            x -> x.matches("\\d{4}") && Integer.parseInt(x) >= 2020 && Integer.parseInt(x) <= 2030,
            "hgt",
            x -> {
              boolean regexMatches = x.matches("\\d+(cm|in)");
              boolean isCm = x.contains("cm");

              if (regexMatches) {
                int number = Integer.parseInt(x.substring(0, x.length() - 2));

                if (isCm) {
                  return number >= 150 && number <= 193;
                } else {
                  return number >= 59 && number <= 76;
                }
              }
              return false;
            },
            "hcl",
            x -> x.matches("#([0-9a-f]{6})"),
            "ecl",
            x -> x.matches("amb|blu|brn|gry|grn|hzl|oth"),
            "pid",
            x -> x.matches("\\d{9}"),
            "cid",
            x -> true);

    List<Map<String, String>> validPassports =
        passports.stream()
            .filter(passport -> passport.keySet().containsAll(requiredPassportFields))
            .collect(Collectors.toList());

    long allValidReports =
        validPassports.stream()
            .filter(
                passport ->
                    passport.entrySet().stream()
                        .allMatch(prop -> validators.get(prop.getKey()).test(prop.getValue())))
            .peek(
                map ->
                    map.entrySet()
                        .forEach(
                            entry -> {
                              System.out.print(entry.getKey() + ":" + entry.getValue());
                            }))
            .count();

    System.out.println(String.format("Valid passports: %d", allValidReports));
  }
}
