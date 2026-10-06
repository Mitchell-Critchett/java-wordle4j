package ru.yandex.practicum;

import java.util.List;

/*
этот класс содержит в себе список слов List<String>
    его методы похожи на методы списка, но учитывают особенности игры
    также этот класс может содержать рутинные функции по сравнению слов, букв и т.д.
 */
public class WordleDictionary {

    private final List<String> words;

    WordleDictionary(List<String> words) {
        this.words = words;
    }

    public int getSize() {
        return words.size();
    }

    public String getElement(int index) {
        return words.get(index);
    }


}
