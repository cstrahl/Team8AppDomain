package com.appdomain.accesscontrol.security.utils;

import org.passay.CharacterData;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.LengthRule;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.Rule;

import java.util.ArrayList;
import java.util.List;

public class ConstraintUtils {

    //TODO: Check if static utils like this need an annotation (like @Component)

    private static PasswordValidator passwordValidator;
    private static List<CharacterRule> characterRules;

    public static boolean isValidPassword(final String candidate) {
        if (passwordValidator == null) {
            initPasswordValidator();
        }
        return passwordValidator.validate(new PasswordData(candidate)).isValid();
    }

    public static List<CharacterRule> getPasswordRules() {
        if (characterRules == null) {
            initCharacterRules();
        }
        return characterRules;
    }

    private static synchronized void initPasswordValidator() {
        if (passwordValidator != null) {
            return;
        }
        final List<Rule> passwordValidatorRules = new ArrayList<>(getPasswordRules());
        passwordValidatorRules.add(new LengthRule(8, 25));
        passwordValidator = new PasswordValidator(passwordValidatorRules);
    }

    private static synchronized void initCharacterRules() {
        if (characterRules != null) {
            return;
        }
        final CharacterData lowerCaseChars = EnglishCharacterData.LowerCase;
        final CharacterData upperCaseChars = EnglishCharacterData.UpperCase;
        final CharacterData digitChars = EnglishCharacterData.Digit;
        final CharacterData specialChars = new CharacterData() {
            public String getErrorCode() {
                return "INSUFFICIENT_SPECIAL";
            }

            public String getCharacters() {
                return "!@#$%^&*()_+";
            }
        };
        characterRules = List.of(
                new CharacterRule(lowerCaseChars),
                new CharacterRule(upperCaseChars),
                new CharacterRule(digitChars),
                new CharacterRule(specialChars));
    }
}
