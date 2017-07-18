import java.io.*;

public class LabReport1 {

    public static void main(String[] args) throws FileNotFoundException,UnsupportedEncodingException {
        
        Sender S = new Sender();
        S.application();
        Receiver R = new Receiver();
        R.physical();
    }
}