package ru.yandex.practicum.exceptions;

public class NotInDictionaryException extends Exception {

    public NotInDictionaryException() {
        super("Введённое вами слово отсутствует в словаре!");
    }
}
