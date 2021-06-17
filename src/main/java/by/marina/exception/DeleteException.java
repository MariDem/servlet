package by.marina.exception;

public class DeleteException extends Exception {
    private final int status;

    public DeleteException(int status, String message){
        super(message);
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
