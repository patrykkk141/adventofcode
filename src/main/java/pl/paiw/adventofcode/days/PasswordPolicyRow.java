package pl.paiw.adventofcode.days;

public class PasswordPolicyRow {

  private int minOccurs;
  private int maxOccurs;
  private char character;
  private String password;

  public PasswordPolicyRow() {}

  public PasswordPolicyRow(int minOccurs, int maxOccurs, char character, String password) {
    this.minOccurs = minOccurs;
    this.maxOccurs = maxOccurs;
    this.character = character;
    this.password = password;
  }

  public int getMinOccurs() {
    return minOccurs;
  }

  public void setMinOccurs(int minOccurs) {
    this.minOccurs = minOccurs;
  }

  public int getMaxOccurs() {
    return maxOccurs;
  }

  public void setMaxOccurs(int maxOccurs) {
    this.maxOccurs = maxOccurs;
  }

  public char getCharacter() {
    return character;
  }

  public void setCharacter(char character) {
    this.character = character;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public String toString() {
    return "PasswordPolicyRow{"
        + "minOccurs="
        + minOccurs
        + ", maxOccurs="
        + maxOccurs
        + ", character="
        + character
        + ", password='"
        + password
        + '\''
        + '}';
  }
}
