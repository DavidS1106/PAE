package exceptions;

public class BizzException extends RuntimeException {
  private int codeError;

  public BizzException() {
    super();
  }

  public BizzException(int codeError) {
    this.codeError = codeError;
  }

  public int getCodeError() {
    return codeError;
  }
}
