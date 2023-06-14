package ua.lviv.iot.course_work.exceptions;

public class DatabaseTableIsEmptyException extends Exception{
    public DatabaseTableIsEmptyException(String message) {
        super(message);
    }
}
