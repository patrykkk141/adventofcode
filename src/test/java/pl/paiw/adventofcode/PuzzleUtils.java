package pl.paiw.adventofcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import pl.paiw.adventofcode.days.PasswordPolicyRow;

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

      String[] params = row.split(" |\\n");

      for (String param : params) {
        String[] singleParam = param.split(":");
        passport.put(singleParam[0], singleParam[1]);
      }
    }
    result.add(passport);

    return result;
  }
}
