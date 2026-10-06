package ru.yandex.practicum;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

/*
в главном классе нам нужно:
    создать лог-файл (он должен передаваться во все классы)
    создать загрузчик словарей WordleDictionaryLoader
    загрузить словарь WordleDictionary с помощью класса WordleDictionaryLoader
    затем создать игру WordleGame и передать ей словарь
    вызвать игровой метод в котором в цикле опрашивать пользователя и передавать информацию в игру
    вывести состояние игры и конечный результат
 */
public class Wordle {

    public static void main(String[] args) {
        final String log = createLog();
        try {
            Scanner scanner = new Scanner(System.in);
            final WordleDictionary dictionary = WordleDictionaryLoader.loadDictionary("words_ru.txt");
            final WordleGame game = new WordleGame(dictionary);
            game.playGame(scanner);
        } catch (Exception exception) {
            writeStackTraceToFile(log, exception);
        }
    }

    public static String createLog() {
        try {
            Path log = Paths.get("log.txt");
            if (!log.toFile().isFile()) {
                Files.createFile(log);
            }
            return log.getFileName().toString();
        } catch (IOException e) {
            throw new RuntimeException("Не удалось создать лог игры", e);
        }
    }

    public static void writeStackTraceToFile(String filename, Exception exception) {

        try (PrintWriter writer = new PrintWriter(
                new FileWriter(filename, true))) {

            exception.printStackTrace(writer);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
