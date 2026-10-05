package ru.yandex.practicum;

import ru.yandex.practicum.exceptions.NotARussianWordException;
import ru.yandex.practicum.exceptions.NotInDictionaryException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

/*
в этом классе хранится словарь и состояние игры
    текущий шаг
    всё что пользователь вводил
    правильный ответ

в этом классе нужны методы, которые
    проанализируют совпадение слова с ответом
    предложат слово-подсказку с учётом всего, что вводил пользователь ранее

не забудьте про специальные типы исключений для игровых и неигровых ошибок
 */
public class WordleGame {

    private final String answer;
    private int steps;
    private final WordleDictionary dictionary;

    WordleGame(WordleDictionary dictionary) {
        this.dictionary = dictionary;
        this.answer = generateWord();
        this.steps = 1;
    }

    //Конструктор для тестов (с заранее определённым ответом)
    WordleGame(WordleDictionary dictionary, String answer) {
        this.dictionary = dictionary;
        this.answer = answer;
        this.steps = 1;
    }

    public void playGame(Scanner scanner) {

        //Переменная для подсчёта верных букв в прошлой подсказке
        int hintsCount = 0;

        while (steps <= 6) {
            try {
                System.out.println("Введите слово: ");
                String playerWord = scanner.nextLine().toLowerCase();
                String tint;
                if (playerWord.isEmpty()) {
                    playerWord = generateTint(hintsCount);
                    System.out.println("Подсказка: " + playerWord);
                    hintsCount++;
                }
                if (playerWord.length() != 5) {
                    System.out.println("Слово должно содержать 5 букв");
                    continue;
                }

                if (!playerWord.matches("^[а-яА-ЯёЁ]+$")) {
                    throw new NotARussianWordException();
                }

                if (!isInDictionary(playerWord)) throw new NotInDictionaryException();


                String tintString = generateStringTint(playerWord);
                System.out.println(playerWord);
                System.out.println(tintString);

                if (playerWord.equals(answer)) {
                    System.out.println("Поздравляем! Вы угадали слово!");
                    return;
                }

                steps++;

                if (steps <= 6) {
                    System.out.println("Осталось попыток: " + (7 - steps));
                }
            } catch (NotARussianWordException | NotInDictionaryException exception) {
                System.out.println(exception.getMessage());
            }
        }

        System.out.println("Вы проиграли!");
        System.out.println("Загаданное слово: " + answer);
    }

    private String generateWord() {
        String result = "";

        while (result.length() != 5) {
            int randomIndex = ThreadLocalRandom.current().nextInt(dictionary.getSize());
            result = dictionary.getElement(randomIndex);
        }


        return result.toLowerCase();
    }

    //Генерирует подсказку на основе введённой строки
    private String generateStringTint(String word) {

        char[] result = new char[5];
        boolean[] used = new boolean[5];

        // По умолчанию все буквы отсутствуют
        for (int i = 0; i < 5; i++) {
            result[i] = '-';
        }

        // '+'
        for (int i = 0; i < 5; i++) {

            if (word.charAt(i) == answer.charAt(i)) {
                result[i] = '+';
                used[i] = true;
            }
        }

        // '^'
        for (int i = 0; i < 5; i++) {

            if (result[i] == '+') {
                continue;
            }

            for (int j = 0; j < 5; j++) {

                if (!used[j] && word.charAt(i) == answer.charAt(j)) {

                    result[i] = '^';
                    used[j] = true;
                    break;
                }
            }
        }

        return new String(result);
    }

    //Анализирует весь словарь и ищет слова, на основе предыдущих подсказок
    private String generateTint(int hintsCount) {

        List<String> possibleWords = new ArrayList<>();

        for (int i = 0; i < dictionary.getSize(); i++) {

            String candidate = dictionary.getElement(i).toLowerCase();

            if (candidate.length() != 5) {
                continue;
            }

            if (isSuitable(candidate, hintsCount)) {
                possibleWords.add(candidate);
            }
        }

        // Если подходящих слов нет
        if (possibleWords.isEmpty()) {
            return "Подходящих слов не найдено";
        }

        int index = ThreadLocalRandom.current()
                .nextInt(possibleWords.size());

        return possibleWords.get(index);
    }

    //Проверка слова "кандидата" на подсказку
    private boolean isSuitable(String candidate, int prevHintCount) {
        String tint = generateStringTint(candidate);
        int count = 0;
        for (char c : tint.toCharArray()) {
            if (c == '+') count++;
        }

        return count == prevHintCount + 1;
    }

    private boolean isInDictionary(String word) {
        for (int i = 0; i < dictionary.getSize(); i++) {
            if (dictionary.getElement(i).equalsIgnoreCase(word)) {
                return true;
            }
        }
        return false;
    }

}

