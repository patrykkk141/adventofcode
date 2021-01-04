package pl.paiw.adventofcode;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.junit.Test;

public class Results {

    private final List<String> puzzle_Day1 = PuzzleReader.getPuzzleLines("day1.txt");
    private final List<String> puzzle_Day2 = PuzzleReader.getPuzzleLines("day2.txt");
    private final List<String> puzzle_Day3 = PuzzleReader.getPuzzleLines("day3.txt");
    private final List<String> puzzle_Day4 = PuzzleReader.getPuzzleLines("day4.txt");
    private final List<String> puzzle_Day5 = PuzzleReader.getPuzzleLines("day5.txt");

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
        for (int[] param : params) {
            int count = 0;
            int currentRow;
            int currentCol;

            for (int i = param[1], j = param[0]; i < rows; i += param[1], j += param[0]) {
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

        System.out.printf("Valid passports: %d%n", validPassports);
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
                                        map.forEach((key, value) -> System.out.print(key + ":" + value)))
                        .count();

        System.out.printf("Valid passports: %d%n", allValidReports);
    }

    @Test
    public void getResults_day5() {
        int candidate = -1;

        for (String row : puzzle_Day5) {
            char[] chars = row.toCharArray();
            int minRowRange = 0;
            int maxRowRange = 127;
            int minColRange = 0;
            int maxColRange = 7;

            for (char c : chars) {
                int rowRange = maxRowRange - minRowRange + 1;
                int colRange = maxColRange - minColRange + 1;
                switch (c) {
                    case 'F':
                        maxRowRange = maxRowRange - rowRange / 2;
                        break;
                    case 'B':
                        minRowRange = minRowRange + rowRange / 2;
                        break;
                    case 'L':
                        maxColRange = maxColRange - colRange / 2;
                        break;
                    case 'R':
                        minColRange = minColRange + colRange / 2;
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid sign");
                }
            }

            int seatId = maxRowRange * 8 + maxColRange;
            candidate = Math.max(seatId, candidate);
        }

        System.out.printf("Max seat id is: %d", candidate);
    }

    @Test
    public void getResults_day5_1() {
        List<Integer> ids = new ArrayList<>();

        for (String row : puzzle_Day5) {
            char[] chars = row.toCharArray();
            int minRowRange = 0;
            int maxRowRange = 127;
            int minColRange = 0;
            int maxColRange = 7;

            for (char c : chars) {
                int rowRange = maxRowRange - minRowRange + 1;
                int colRange = maxColRange - minColRange + 1;
                switch (c) {
                    case 'F':
                        maxRowRange = maxRowRange - rowRange / 2;
                        break;
                    case 'B':
                        minRowRange = minRowRange + rowRange / 2;
                        break;
                    case 'L':
                        maxColRange = maxColRange - colRange / 2;
                        break;
                    case 'R':
                        minColRange = minColRange + colRange / 2;
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid sign");
                }
            }
            ids.add(maxRowRange * 8 + maxColRange);
        }

        Collections.sort(ids);

        int lastVal = -1;
        for (Integer id : ids) {
            if (lastVal < 0) {
                lastVal = id;
                continue;
            }

            if (id != ++lastVal && ids.contains(id - 2) && id - lastVal == 1) {
                System.out.printf("Twoje miejsce: %d", id - 1);
                break;
            }
        }

    }

}
