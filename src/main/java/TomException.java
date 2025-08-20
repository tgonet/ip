public class TomException extends Exception{
    public TomException(String message) {
        super(message);
    }

    @Override
    public String toString(){
        return this.getMessage();
    }
}
