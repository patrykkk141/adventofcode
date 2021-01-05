package pl.paiw.adventofcode;

import java.util.*;
import java.util.stream.Collectors;

import pl.paiw.adventofcode.PasswordPolicyRow;

public class PuzzleUtils {

    public static List<Integer> parseStringsListToIntegersList(List<String> puzzle) {
        return puzzle.stream().map(Integer::parseInt).collect(Collectors.toList());
    }

    public static List<PasswordPolicyRow> parseToPasswordPolicyRow(List<String> puzzle) {
        List<PasswordPolicyRow> parsed = new ArrayList<>();
        puzzle.forEach(
                row -> {
                    String[] passwordPolicyElements = row.split(" ");
                    String[] instanceRestrictions = passwordPolicyElements[0].split("-");
                    parsed.add(
                            new PasswordPolicyRow(
                                    Integer.parseInt(instanceRestrictions[0]),
                                    Integer.parseInt(instanceRestrictions[1]),
                                    passwordPolicyElements[1].charAt(0),
                                    passwordPolicyElements[2]));
                });

        return parsed;
    }

    public static List<Map<String, String>> parsePassports(List<String> puzzle) {
        List<Map<String, String>> result = new ArrayList<>();

        Map<String, String> passport = new HashMap<>();
        for (String row : puzzle) {
            if (row.isEmpty()) {
                result.add(passport);
                passport = new HashMap<>();
                continue;
            }

            String[] params = row.split("[ \\n]");

            for (String param : params) {
                String[] singleParam = param.split(":");
                passport.put(singleParam[0], singleParam[1]);
            }
        }
        result.add(passport);

        return result;
    }

    public static List<List<String>> parseCustomDeclarationGroups(List<String> puzzle) {
        List<List<String>> result = new ArrayList<>();

        List<String> group = new ArrayList<>();
        for (String row : puzzle) {
            if (row.isEmpty()) {
                result.add(group);
                group = new ArrayList<>();
                continue;
            }

            group.add(row);
        }
        result.add(group);

        return result;
    }

    public static Map<String, Set<String>> parseBagPolicy(List<String> puzzle) {
        Map<String, Set<String>> result = new HashMap<>();

        for(String row : puzzle) {
            String[] split = row.split(" contain");
            String[] rules = split[1].split(",");

            result.put(split[0], Set.of(rules));
        }

        return result;
    }
}
