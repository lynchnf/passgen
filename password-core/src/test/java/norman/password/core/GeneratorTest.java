package norman.password.core;

import norman.password.core.Generator.Optionality;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import static norman.password.core.Generator.Optionality.MANDATORY;
import static norman.password.core.Generator.Optionality.PROHIBITED;
import static org.junit.Assert.*;

public class GeneratorTest {
    @Test
    public void testGeneratePassword() {
        int length = 8;
        boolean allowNumberForFirstChar = false;
        Optionality includeLows = MANDATORY;
        Optionality includeCaps = MANDATORY;
        Optionality includeNumbers = MANDATORY;
        Optionality includeSpecials = PROHIBITED;
        String password = Generator
                .generatePassword(length, allowNumberForFirstChar, includeLows, includeCaps, includeNumbers,
                        includeSpecials);
        assertEquals(length, password.length());
        assertTrue(StringUtils.containsAny(password, Generator.LOWERS));
        assertTrue(StringUtils.containsAny(password, Generator.UPPERS));
        assertTrue(StringUtils.containsAny(password, Generator.NUMBERS));
        assertTrue(StringUtils.containsNone(password.substring(0, 1), Generator.NUMBERS));
        assertTrue(StringUtils.containsNone(password, Generator.SPECIALS));
    }

    @Test
    public void testIsPasswordValid() {
        int minimumLength = 8;
        boolean allowOtherChars = false;
        Optionality includeLows = MANDATORY;
        Optionality includeCaps = MANDATORY;
        Optionality includeNumbers = MANDATORY;
        Optionality includeSpecials = MANDATORY;
        boolean valid1 = Generator
                .isPasswordValid("PSCpsc123~", minimumLength, allowOtherChars, includeLows, includeCaps, includeNumbers,
                        includeSpecials);
        assertTrue(valid1);
        boolean valid2 = Generator
                .isPasswordValid("Ps3~", minimumLength, allowOtherChars, includeLows, includeCaps, includeNumbers,
                        includeSpecials);
        assertFalse("length", valid2);
        boolean valid3 = Generator
                .isPasswordValid("PSC/psc.123~", minimumLength, allowOtherChars, includeLows, includeCaps,
                        includeNumbers, includeSpecials);
        assertFalse("otherChar", valid3);
        boolean valid4 = Generator
                .isPasswordValid("PSCPSC123~", minimumLength, allowOtherChars, includeLows, includeCaps, includeNumbers,
                        includeSpecials);
        assertFalse("lowerCase", valid4);
        boolean valid5 = Generator
                .isPasswordValid("pscpsc123~", minimumLength, allowOtherChars, includeLows, includeCaps, includeNumbers,
                        includeSpecials);
        assertFalse("upperCase", valid5);
        boolean valid6 = Generator
                .isPasswordValid("PSCpscpsc~", minimumLength, allowOtherChars, includeLows, includeCaps, includeNumbers,
                        includeSpecials);
        assertFalse("numbers", valid6);
        boolean valid7 = Generator
                .isPasswordValid("PSCpsc1234", minimumLength, allowOtherChars, includeLows, includeCaps, includeNumbers,
                        includeSpecials);
        assertFalse("specials", valid7);
    }

    @Test
    public void testContainsDiscouragedCharacters() {
        boolean valid1 = Generator.containsDiscouragedCharacters("XYZxyz234");
        assertFalse(valid1);
        boolean valid2 = Generator.containsDiscouragedCharacters("FO0BAR");
        assertTrue("discouragedChar", valid2);
    }
}
