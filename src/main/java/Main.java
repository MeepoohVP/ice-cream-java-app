import ui.App;

import java.io.Console;

public class Main {

    public static void main(String[] args) {
        Console console = System.console();
        String user =  console.readLine("Enter your username: ");
        char[] passwordarr = console.readPassword("Enter your password: ");
        String password = new String(passwordarr);
        if (password.equals("icecream123")) {
            App.start();
        }
    }
}
