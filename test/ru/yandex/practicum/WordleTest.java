package ru.yandex.practicum;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class WordleGameTest {

    //Создаёт начальный словарь
    private WordleDictionary createDictionary() {
        return new WordleDictionary(List.of("герой", "гонец", "город", "ветка", "машина"));
    }

    //Создаёт игру
    private String runGame(String input) {
        WordleGame game = new WordleGame(createDictionary());
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream oldOut = System.out;

        try {
            System.setOut(new PrintStream(output, true, StandardCharsets.UTF_8));
            game.playGame(new Scanner(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8))));

            return output.toString(StandardCharsets.UTF_8);

        } finally {
            System.setOut(oldOut);
        }
    }

    // 1. Пустой ввод должен привести к генерации подсказки
    @Test
    void shouldGenerateHintForEmptyInput() {

        String output = runGame("\n");

        assertTrue(output.contains("Подсказка:"));
    }


    // 2. Слово неправильной длины
    @Test
    void shouldRejectWordWithWrongLength() {

        String output = runGame("кот\n");

        assertTrue(output.contains("Слово должно содержать 5 букв"));
    }

    // 3. Слово должно состоять только из русских букв
    @Test
    void shouldRejectNonRussianWord() {

        String output = runGame("hello\n");
        assertTrue(output.contains("Вы должны использовать только русские слова!"));
    }

    // 4. Слово отсутствует в словаре
    @Test
    void shouldRejectWordNotInDictionary() {

        String output = runGame("абвде\n");
        assertTrue(output.contains("Введённое вами слово отсутствует в словаре!"));
    }

    // 5. Корректное слово должно обрабатываться
    @Test
    void shouldAcceptWordFromDictionary() {

        String output = runGame("гонец\n");
        assertTrue(output.contains("гонец"));
    }

    // 6. После шести неправильных попыток игра заканчивается
    @Test
    void shouldLoseAfterSixAttempts() {

        String input =
                "гонец\n" +
                        "гонец\n" +
                        "гонец\n" +
                        "гонец\n" +
                        "гонец\n" +
                        "гонец\n";

        String output = runGame(input);
        assertTrue(output.contains("Вы проиграли!"));
    }


}
