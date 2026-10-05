package ru.yandex.practicum.exceptions;

public class NotARussianWordException extends Exception {

    public NotARussianWordException() {
        super("Вы должны использовать только русские слова!");
    }
}
