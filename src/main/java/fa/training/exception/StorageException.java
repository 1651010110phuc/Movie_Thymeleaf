package fa.training.exception;

public class StorageException extends RuntimeException {

    public StorageException(String message) {
        super(message);
    }

    public StorageException(String fail_to_store_file, Exception e) {
        super(fail_to_store_file, e);
    }
}
