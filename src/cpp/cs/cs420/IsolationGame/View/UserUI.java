package cpp.cs.cs420.IsolationGame.View;

import java.util.Scanner;

/**
 * Created by mayalake on 5/17/18.
 */
public class UserUI {
    Scanner sc = new Scanner(System.in);

    public void welcomeMessage(){
        System.out.printf("Welcome to the Isolation Game. To win, be the last one to make a move\n");
    }

    public Character enterFirstPlayer(){
        char firstPlayer;
        do{
            System.out.printf("Enter 'O' to play first or enter 'X' for computer to play first\n");
            firstPlayer = sc.nextLine().toUpperCase().charAt(0);
        }while((firstPlayer != 'X') &&  (firstPlayer != '0'));

        return firstPlayer;
    }
}
