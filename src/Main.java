//edytyted by 16:33 02-08-2023
//Version 1.21

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

class WinnerWasCalled extends Exception {
}

class Log {

    protected static void info() {
        System.out.println("");
    }

    protected static void info(String message) {
        System.out.println(message);
    }

}

class Dice {

    protected int MaxDice = 6;

    protected int roll() {
        Random rand = new Random();
        int result = rand.nextInt(MaxDice) + 1;

        Log.info("Dice roll: " + result);
        return result;
    }

    public Dice(int MDice){
        MaxDice = MDice;
    }

}

class Pawn {

    private int position;
    public String name;

    public Pawn(String name) {
        this.position = 0;
        this.name = name;

        Log.info(this.name + " joined the game.");
    }

    protected void SetPosition(int Max)
    {
        this.position=Max;
    }

    protected void AddPosition(int Step)
    {
        this.position+=Step;
    }

    protected int GetPosition()
    {
        return position;
    }

    protected String GetName()
    {
        return name;
    }

}

class Board {

    private int max_position = 100;

    public ArrayList<Pawn> pawns;
    private Dice dice;
    public Pawn winner;
    private int turnsCounter;

    public Board(int M_P) {
        this.pawns = new ArrayList<Pawn>();
        this.dice = new Dice(0);
        this.winner = null;
        this.turnsCounter = 0;
        SetMaxRoad(M_P);
    }

    public void SetDice(int MaxD)
    {
        this.dice.MaxDice=MaxD;
    }

    public void performTurn() throws WinnerWasCalled {
        this.turnsCounter++;
        Log.info();
        Log.info("Turn " + this.turnsCounter);

        for(Pawn pawn : this.pawns) {
            int rollResult = this.dice.roll();
            if(rollResult==1 && pawn.GetPosition()%2==1)
            {
                //Log.info("Wypadła 1 i jest liczba połozenia nieparzysta");
                rollResult = -this.dice.roll();
            } else if (rollResult==this.dice.MaxDice && pawn.GetPosition()%7==0) {
                rollResult+=this.dice.roll();
                Log.info("Podwojny rzut");
            }
            pawn.AddPosition(rollResult);
            Log.info(pawn.name + " new position: " + pawn.GetPosition());

            if(pawn.GetPosition() >= GetMaxRoad()) {
                this.winner = pawn;
                throw new WinnerWasCalled();
            }
        }
    }

    private void SetMaxRoad(int NewRoad)
    {
        max_position = NewRoad;
    }

    protected int GetMaxRoad()
    {
        return max_position;
    }

}

public class Main {

    public static void main(String[] args) {

        Log.info("Podaj długość sciezki:");//pobranie długości ścieżki;
        Scanner scan = new Scanner(System.in);
        int A = scan.nextInt();
        Board board = new Board(A);
        //dorobic pobranie zagrywanej kości;
        Log.info("Podaj wielkość kości:");//pobranie długości ścieżki;
        A = scan.nextInt();
        board.SetDice(A);

        Random rand = new Random();
        int LiczbaGraczy = rand.nextInt(7) + 3;
        for (int x=0; x<=LiczbaGraczy; x++)
        {
            board.pawns.add(new Pawn("Gracz " + x ));
        }

        try {
            while(true) {
                board.performTurn();
            }
        } catch(WinnerWasCalled exception) {
            Log.info();
            Log.info(board.winner.GetName() + " won.");
        }
    }

}