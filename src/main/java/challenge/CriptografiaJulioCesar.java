package challenge;

import static java.lang.Character.isAlphabetic;
import static java.util.stream.Collectors.joining;

public class CriptografiaJulioCesar {

    private static final int QUANTIDADE_DE_LETRAS = 26;

    public static String encrypt(String expression, int number) {
        return expression
                .chars()
                .map(character -> logic(character, number))
                .mapToObj(character -> (char) character)
                .map(String::valueOf)
                .collect(joining());
    }

    private static int logic(int character, int number) {
        number = number % QUANTIDADE_DE_LETRAS;

        int result = character;
        if (isAlphabetic(character)) {
            result = character - number;

            if (result < 'a') {
                result = result + QUANTIDADE_DE_LETRAS;
            }
        }

        return result;
    }

}
