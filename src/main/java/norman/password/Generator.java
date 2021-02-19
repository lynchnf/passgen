package norman.password;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Utility for generating random passwords and validating user entered passwords.
 *
 * @author LYNCHNF
 */
public class Generator {

    // These are protected so they can be used by the unit test.
    protected static final char[] LOWERS =
            {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u',
                    'v', 'w', 'x', 'y', 'z'};
    protected static final char[] UPPERS =
            {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U',
                    'V', 'W', 'X', 'Y', 'Z'};
    protected static final char[] NUMBERS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    protected static final char[] SPECIALS = {'!', '@', '#', '$', '%', '^', '&', '*', '_', '~'};
    protected static final char[] DISCOURAGED = {'i', 'l', 'o', 'I', 'O', '0', '1'};

    public enum Optionality {
        PROHIBITED, OPTIONAL, MANDATORY;
    }

    public static void main(String[] args) {
        System.out.println("starting");

        int length = 8;
        boolean allowNumberForFirstChar = false;
        Optionality includeLows = Optionality.MANDATORY;
        Optionality includeCaps = Optionality.MANDATORY;
        Optionality includeNumbers = Optionality.MANDATORY;
        Optionality includeSpecials = Optionality.PROHIBITED;

        String password = generatePassword(length, allowNumberForFirstChar, includeLows, includeCaps, includeNumbers,
                includeSpecials);

        System.out.println("password=\"" + password + "\"");

        System.out.println("finished");
    }

    public static String generatePassword(int length, boolean allowNumberForFirstChar, Optionality includeLows,
            Optionality includeCaps, Optionality includeNumbers, Optionality includeSpecials) {
        Optionality includeNumberForFirstChar = Optionality.PROHIBITED;
        if (allowNumberForFirstChar) {
            includeNumberForFirstChar = includeNumbers;
        }
        List<Character> first = buildCharList(includeLows, includeCaps, includeNumberForFirstChar, includeSpecials);
        List<Character> other = buildCharList(includeLows, includeCaps, includeNumbers, includeSpecials);

        Random random = new Random();
        StringBuilder sb;
        do {
            sb = new StringBuilder();
            for (int i = 0; i < length; i++) {
                if (i == 0) {
                    int index = random.nextInt(first.size());
                    Character character = first.get(index);
                    sb.append(character.charValue());
                } else {
                    int index = random.nextInt(other.size());
                    Character character = other.get(index);
                    sb.append(character.charValue());
                }
            }
        } while (!isPasswordValid(sb.toString(), length, false, includeLows, includeCaps, includeNumbers,
                includeSpecials) || containsDiscouragedCharacters(sb.toString()));
        return sb.toString();
    }

    public static boolean isPasswordValid(String password, int minimumLength, boolean allowOtherChars,
            Optionality includeLows, Optionality includeCaps, Optionality includeNumbers, Optionality includeSpecials) {
        boolean result = true;
        if (password.length() < minimumLength) {
            result = false;
        } else if (includeLows == Optionality.MANDATORY && StringUtils.containsNone(password, LOWERS)) {
            result = false;
        } else if (includeLows == Optionality.PROHIBITED && StringUtils.containsAny(password, LOWERS)) {
            result = false;
        } else if (includeCaps == Optionality.MANDATORY && StringUtils.containsNone(password, UPPERS)) {
            result = false;
        } else if (includeCaps == Optionality.PROHIBITED && StringUtils.containsAny(password, UPPERS)) {
            result = false;
        } else if (includeNumbers == Optionality.MANDATORY && StringUtils.containsNone(password, NUMBERS)) {
            result = false;
        } else if (includeNumbers == Optionality.PROHIBITED && StringUtils.containsAny(password, NUMBERS)) {
            result = false;
        } else if (includeSpecials == Optionality.MANDATORY && StringUtils.containsNone(password, SPECIALS)) {
            result = false;
        } else if (includeSpecials == Optionality.PROHIBITED && StringUtils.containsAny(password, SPECIALS)) {
            result = false;
        } else if (!allowOtherChars) {
            List<Character> charList = buildCharList(includeLows, includeCaps, includeNumbers, includeSpecials);
            char[] validChars = new char[charList.size()];
            for (int i = 0; i < charList.size(); i++) {
                validChars[i] = charList.get(i).charValue();
            }
            if (!StringUtils.containsOnly(password, validChars)) {
                result = false;
            }
        }
        return result;
    }

    public static boolean containsDiscouragedCharacters(String password) {
        return StringUtils.containsAny(password, DISCOURAGED);
    }

    private static List<Character> buildCharList(Optionality includeLows, Optionality includeCaps,
            Optionality includeNumbers, Optionality includeSpecials) {
        List<Character> charList = new ArrayList<Character>();
        if (Optionality.PROHIBITED != includeLows) {
            for (char c : LOWERS) {
                charList.add(Character.valueOf(c));
            }
        }
        if (Optionality.PROHIBITED != includeCaps) {
            for (char c : UPPERS) {
                charList.add(Character.valueOf(c));
            }
        }
        if (Optionality.PROHIBITED != includeNumbers) {
            for (char c : NUMBERS) {
                charList.add(Character.valueOf(c));
            }
        }
        if (Optionality.PROHIBITED != includeSpecials) {
            for (char c : SPECIALS) {
                charList.add(Character.valueOf(c));
            }
        }
        return charList;
    }
}