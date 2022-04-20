import java.util.Arrays;
import java.util.Scanner;

public class TicTacToe {

    public static char[][] board = new char[3][3];
    public static int turns;
    public static boolean[][] check = new boolean[3][3];
    int difficulty;

    public static void main(String[] args) {
        TicTacToe tictactoe = new TicTacToe();
        tictactoe.playGame();
    }

    public void playGame() {
        for (int i = 0; i < 3; i++) {
            for (int z = 0; z < 3; z++) {
                board[i][z] = '-';
                check[i][z] = false;
            }
        }
        turns = 0;
        System.out.print("CPU Difficulty (1 or 2): ");
        String input = getInput('N');
        if (!input.equals("1") || !input.equals("2")) {
            difficulty = Integer.valueOf(input);
        }
        System.out.print("Who's going first?\nU = User, C = Computer, R = Random\n");
        Move(getInput('N').charAt(0));
    }

    public void Move(char input) {
        char winner = Done();
        if (turns == 9 || winner == 'X' || winner == 'O') {
            if (winner == 'X') {
                System.out.println("User Wins!");
                playGameAgain();
            } else if (winner == 'O') {
                System.out.println("Computer Wins!");
                playGameAgain();
            } else if (turns == 9) {
                System.out.println("It's a tie!");
                playGameAgain();
            }
        } else if (input == 'U') {
            System.out.print("Coordinates (column, row): ");
            String rowi = getInput('U');
            String coli = getInput('U');
            int row = Integer.valueOf(rowi) - 1;
            int col = Integer.valueOf(coli) - 1;
            if (col < 0 || col > 2 || row < 0 || row > 2 || check[col][row] == true) {
                System.out.println("Invalid Coordinates");
                Move('U');
            }
            board[col][row] = 'X';
            check[col][row] = true;
            turns++;
            Display();
            Move('C');
        } else if (input == 'C') {
            int combine = ComputerMove();
            int col = (combine / 10);
            int row = (combine % 10);
            if (check[col][row] == true) {
                Move('C');
            }
            board[col][row] = 'O';
            check[col][row] = true;
            turns++;
            System.out.println("Computer's turn: ");
            Display();
            Move('U');
        } else if (input == 'R') {
            if ((int) (Math.random() * 2) % 2 == 1) {
                Move('C');
            } else {
                Move('U');
            }
        } else {
            System.out.println("Must be 'U', 'C', 'R'");
            Move(getInput(input).charAt(0));
        }
        return;
    }

    public void playGameAgain() {
        System.out.println("Play Again? (Y/N) ");
        char in = getInput('N').charAt(0);
        if (in == 'Y') {
            playGame();
        } else if (in == 'N') {
            System.exit(0);
        } else {
            System.out.println("Must be 'Y' or 'N' ");
            playGameAgain();
        }
        System.exit(0);
    }

    public char Done() {
        char winner = ' ';
        for (int i = 0; i < 3; i++) {
            if (board[0][0] == '-' && board[1][2] == '-' && board[2][0] == '-') {
                return winner;
            }
            if (board[0][i] == board[1][i] && board[1][i] == board[2][i]) {
                winner = board[0][i];
                break;
            }
            if (board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
                winner = board[i][0];
                break;
            }
            if (board[0][0] == board[1][1] && board[2][2] == board[1][1]) {
                winner = board[0][0];
                break;
            }
            if (board[2][0] == board[1][1] && board[1][1] == board[0][2]) {
                winner = board[2][0];
                break;
            }
        }
        return winner;
    }

    public void Display() {
        System.out.println("_________________");
        for (int i = 0; i < 3; i++) {
            for (int z = 0; z < 3; z++) {
                System.out.print(board[i][z] + "\t");
            }
            System.out.println();
        }
        System.out.println("_________________");
        return;
    }

    public String getInput(char character) {
        String input = " ";
        try {
            Scanner scanner = new Scanner(System.in);
            input = scanner.next().toUpperCase();
        } catch (Exception e) {
            System.out.println("Must be a Character");
            Move(character);
        }
        return input;
    }

    public int ComputerMove() {
        int combined = 0;
        if (difficulty == 1) {
            combined = Integer.valueOf((int) (Math.random() * 3) + "" + (int) (Math.random() * 3));
        } else if (difficulty == 2) {
            combined = bestMove();
        }
        return combined;
    }

    public int bestMove() {
        int value = -10;
        int coordinates = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '-') {
                    board[i][j] = 'O';
                    turns++;
                    int score = MiniMax(false);
                    board[i][j] = '-';
                    turns--;
                    if (score > value) {
                        value = score;
                        coordinates = Integer.valueOf(i + "" + j);
                    }
                }
            }
        }
        return coordinates;
    }

    public int MiniMax(boolean isMax) {
        char done = Done();
        if (done == 'O') {
            return 10;
        } else if (done == 'X') {
            return -10;
        } else if (turns == 9) {
            return 0;
        }
        if (isMax) {
            int best = -10;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == '-') {
                        board[i][j] = 'O';
                        turns++;
                        best = Math.max(best, MiniMax(!isMax));
                        turns--;
                        board[i][j] = '-';
                    }
                }
            }
            return best;
        } else {
            int best = 10;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == '-') {
                        board[i][j] = 'X';
                        turns++;
                        best = Math.min(best, MiniMax(!isMax));
                        turns--;
                        board[i][j] = '-';
                    }
                }
            }
            return best;
        }
    }
}