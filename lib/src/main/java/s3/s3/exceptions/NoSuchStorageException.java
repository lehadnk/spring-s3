package s3.s3.exceptions;

public class NoSuchStorageException extends RuntimeException {
    public NoSuchStorageException(String message) {
        super(message);
    }
}
