package exception;

import lombok.Data;

@Data
public class TheSelectedServiceDoesNotExistException extends Exception{


    public TheSelectedServiceDoesNotExistException(String message) {
        super("message");
    }


}
