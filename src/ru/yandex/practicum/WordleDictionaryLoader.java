package ru.yandex.practicum;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/*
этот класс содержит в себе всю рутину по работе с файлами словарей и с кодировками
    ему нужны методы по загрузке списка слов из файла по имени файла
    на выходе должен быть класс WordleDictionary
 */
public class WordleDictionaryLoader {

    public static WordleDictionary loadDictionary(String filename) {
        try (BufferedReader bf = new BufferedReader(new FileReader(filename, StandardCharsets.UTF_8))) {
            List<String> words = new ArrayList<>();
            String word;

            while ((word = bf.readLine()) != null) {
                if (!word.isBlank()) {
                    words.add(word.trim());
                }
            }
            return new WordleDictionary(words);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Файл со словами не найден", e);
        } catch (IOException e) {
            throw new RuntimeException("Нераспознанная ошибка при работе с файлом источником слов",e);
        }
    }
}
