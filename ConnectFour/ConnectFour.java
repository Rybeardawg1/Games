import java.util.Scanner;

public class ConnectFour {

    char[][] board = new char[7][6];
    boolean[][] check = new boolean[7][6];
    int turns;
    int difficulty;

    public static void main(String[] args) {
        ConnectFour connectfour = new ConnectFour();
        connectfour.playGame();
        for(int i = 0; i < 1;) {
            connectfour.playGameAgain();
        }
    }

    public void playGame() {
        for (int z = 0; z < 7; z++) {
            for (int i = 0; i < 6; i++) {
                board[z][i] = '-';
                check[z][i] = false;
            }
        }
        turns = 0;
        System.out.print("CPU Difficulty (1-7): ");
        String input = getInput('N');
        int count = 0;
        for(int i = 1; i < 8; i++) {
            if(input.equals(Integer.toString(i))) {
                count++;
            }
        }if(count != 1) {
            playGame();
        }
        difficulty = Integer.valueOf(input);
        System.out.print("Who's going first?\nU = User, C = Computer, R = Random\n");
        Move(getInput('N').charAt(0));
    }

    public void Move(char input) {
        char winner = Done();
        if (turns == 42 || winner == 'X' || winner == 'O') {
            if (winner == 'X') {
                System.out.println("User Wins!");
                return;
            } else if (winner == 'O') {
                System.out.println("Computer Wins!");
                return;
            } else if (turns == 42) {
                System.out.println("It's a tie!");
                return;
            }
        } else if (input == 'U') {
            System.out.print("Column: ");
            String colString = getInput('U');
            int col = 0;
            try {
            col = Integer.valueOf(colString) - 1;
            }catch (NumberFormatException e) {
                System.out.println(e);
                Move('U');
            }
            if (col < 0 || col > 6) {
                System.out.println("Invalid Coordinates");
                Move('U');
            }
            int row = FindRow(col);
            if (check[col][row]) {
                System.out.println("Space Taken");
                Move('U');
            }
            board[col][FindRow(col)] = 'X';
            check[col][row] = true;
            turns++;
            Display();
            Move('C');
        } else if (input == 'C') {
            int col = ComputerMove();
            int row = FindRow(col);
            if (check[col][row]) {
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

    public char Done() {
        char winner = ' ';
        // horizontals
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 4; j++) {
                if (board[j][i] == board[j + 1][i] && board[j + 1][i] == board[j + 2][i]
                        && board[j + 2][i] == board[j + 3][i]) {
                    if (board[j][i] != '-') {
                        return board[j][i];
                    }
                }
            }
        }
        // verticals
        for (int j = 0; j < 7; j++) {
            for (int i = 0; i < 3; i++) {
                if (board[j][i] == board[j][i + 1] && board[j][i + 1] == board[j][i + 2]
                        && board[j][i + 2] == board[j][i + 3]) {
                    if (board[j][i] != '-') {
                        return board[j][i];
                    }
                }
            }
        }
        // down diagnals
        for (int col = 0; col < 4; col++) {
            for (int row = 0; row < 3; row++) {
                if (board[col][row] == board[col + 1][row + 1] && board[col + 1][row + 1] == board[col + 2][row + 2]
                        && board[col + 2][row + 2] == board[col + 3][row + 3]) {
                    if (board[col][row] != '-') {
                        return board[col][row];
                    }
                }
            }
        }
        // up diagnals
        for (int col = 6; col > 2; col--) {
            for (int row = 0; row < 3; row++) {
                if (board[col][row] == board[col - 1][row + 1] && board[col - 1][row + 1] == board[col - 2][row + 2]
                        && board[col - 2][row + 2] == board[col - 3][row + 3]) {
                    if (board[col][row] != '-') {
                        return board[col][row];
                    }
                }
            }
        }
        return winner;
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
    }

    public void Display() {
        System.out.println("_____________________");
        for (int z = 0; z < 6; z++) {
            System.out.print("|");
            for (int i = 0; i < 7; i++) {
                System.out.print(board[i][z]);
                if (i != 6)
                    System.out.print("  ");
            }
            System.out.println("|");
        }
        System.out.println("_____________________");
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

    public int FindRow(int col) {
        int row = 0;
        for (int i = 0; i < 6; i++) {
            if (board[col][i] == '-') {
                row = i;
            }
        }
        return row;
    }

    public int ComputerMove() {
        int col = 0;
        if(turns == 1 || turns == 0) {
            col = 3;
        } else {
            col = bestMove();
        }
        return col;
    }

    public int bestMove() {
        int value = -10;
        int col = 0;
        for (int i = 0; i < 7; i++) {
            int row = FindRow(i);
            if (board[i][row] == '-' && !check[i][row]) {
                board[i][row] = 'O';
                check[i][row] = true;
                turns++;
                int score = MiniMax(false, 0);
                board[i][row] = '-';
                check[i][row] = false;
                turns--;
                if (score > value) {
                    value = score;
                    col = i;
                }
            }
        }
        return col;
    }

    public int MiniMax(boolean isMax, int depth) {
        if (depth > difficulty) {
            if(isMax) {
                return -5;
            }else {
                return 5;
            }
        }
        char done = Done();
        if (done == 'O') {
            return 10;
        } else if (done == 'X') {
            return -10;
        } else if (turns == 42) {
            return 0;
        }
        if (isMax) {
            int best = -1;
            for (int i = 0; i < 7; i++) {
                int row = FindRow(i);
                if (board[i][row] == '-' && !check[i][row]) {
                    board[i][row] = 'O';
                    check[i][row] = true;
                    turns++;
                    best = Math.max(best, MiniMax(!isMax, depth + 1));
                    turns--;
                    board[i][row] = '-';
                    check[i][row] = false;
                }
            }
            return best;
        } else {
            int best = 1;
            for (int i = 0; i < 7; i++) {
                int row = FindRow(i);
                if (board[i][row] == '-' && !check[i][row]) {
                    board[i][row] = 'X';
                    check[i][row] = true;
                    turns++;
                    best = Math.min(best, MiniMax(!isMax, depth + 1));
                    turns--;
                    board[i][row] = '-';
                    check[i][row] = false;
                }
            }
            return best;
        }
    }
}