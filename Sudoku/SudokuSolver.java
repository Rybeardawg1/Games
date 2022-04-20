import java.util.Stack;
import java.util.Arrays;

public class SudokuSolver {
    public static void main(String[] args) {
        int[][] puzzle = { { 3, 0, 6, 5, 0, 8, 4, 0, 0 },
                { 5, 2, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 8, 7, 0, 0, 0, 0, 3, 1 },
                { 0, 0, 3, 0, 1, 0, 0, 8, 0 },
                { 9, 0, 0, 8, 6, 3, 0, 0, 5 },
                { 0, 5, 0, 0, 9, 0, 6, 0, 0 },
                { 1, 3, 0, 0, 0, 0, 2, 5, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 7, 4 },
                { 0, 0, 5, 2, 0, 6, 3, 0, 0 } };
        System.out.println("Puzzle:");
        Display(puzzle);
        backTrack(puzzle);
    }

    public static void backTrack(int[][] puzzle) {

        Stack<Integer> rowsIntegers = new Stack<>();
        Stack<Integer> colsIntegers = new Stack<>();
        int[][] nums = new int[9][9];
        boolean[][] check = new boolean[9][9];

        for (int col = 0; col < puzzle.length; col++) {
            for (int row = 0; row < puzzle[0].length; row++) {
                if (puzzle[col][row] == 0) {
                    /*
                     * rowsIntegers.push(row);
                     * colsIntegers.push(col);
                     */
                    check[col][row] = true;
                }
            }
        }

        while (!isDone(puzzle)) {
            colsIntegers.push(0);
            rowsIntegers.push(0);
            for (int col = 0; col < puzzle.length; col++) {
                for (int row = 0; row < puzzle[0].length; row++) {
                    colsIntegers.push(col);
                    rowsIntegers.push(row);
                    if (check[col][row] == true) {
                        do {
                            nums[col][row]++;
                            puzzle[col][row] = nums[col][row];
                            if (nums[col][row] == 10) {
                                nums[col][row] = 0;
                                puzzle[col][row] = 0;
                                col = colsIntegers.pop();
                                row = rowsIntegers.pop();
                            }
                        } while (!Check(puzzle));
                    }
                }
            }
        }
        System.out.println("\nSolved:");
        Display(puzzle);
    }

    public static boolean isDone(int[][] puzzle) {
        boolean done = true;
        for (int col = 0; col < puzzle.length; col++) {
            for (int row = 0; row < puzzle[0].length; row++) {
                if(puzzle[col][row] == 0) {
                    done = false;
                }
            }
        }
        return done;
    }

    public static boolean Check(int[][] puzzle) {
        boolean legal = true;
        for (int i = 0; i < 9; i++) {
            if (puzzle[i][0] == puzzle[i][1] || puzzle[i][1] == puzzle[i][2] || puzzle[i][2] == puzzle[i][3]
                    || puzzle[i][3] == puzzle[i][4] || puzzle[i][4] == puzzle[i][5] || puzzle[i][5] == puzzle[i][6]
                    || puzzle[i][6] == puzzle[i][7] || puzzle[i][7] == puzzle[i][8]) {
                legal = false;
            }
            if (puzzle[0][i] == puzzle[1][i] || puzzle[1][i] == puzzle[2][i] || puzzle[2][i] == puzzle[3][i]
                    || puzzle[3][i] == puzzle[4][i] || puzzle[4][i] == puzzle[5][i] || puzzle[5][i] == puzzle[6][i]
                    || puzzle[6][i] == puzzle[7][i] || puzzle[7][i] == puzzle[8][i]) {
                legal = false;
            }
        }
        for (int i = 0; i < 9; i = i + 3) {
            if (puzzle[i][i] == puzzle[i + 1][i] || puzzle[i + 1][i] == puzzle[i + 2][i]
                    || puzzle[i + 2][i] == puzzle[i][i + 1]
                    || puzzle[i][i + 1] == puzzle[i][i + 2] || puzzle[i + 1][i + 1] == puzzle[i][i + 2]
                    || puzzle[i + 1][i + 1] == puzzle[i + 2][i + 1]
                    || puzzle[i + 2][i + 1] == puzzle[i + 1][i + 2] || puzzle[i + 2][i + 2] == puzzle[i + 1][i + 2]) {
                legal = false;
            }
        }
        for (int i = 0; i < 6; i = i + 3) {
            if (puzzle[i + 3][i] == puzzle[i + 4][i] || puzzle[i + 4][i] == puzzle[i + 5][i]
                    || puzzle[i + 5][i] == puzzle[i + 3][i + 1]
                    || puzzle[i][i + 1] == puzzle[i][i + 2] || puzzle[i + 3][i + 1] == puzzle[i + 3][i + 2]
                    || puzzle[i + 4][i + 1] == puzzle[i + 4][i + 1]
                    || puzzle[i + 4][i + 1] == puzzle[i + 4][i + 2] || puzzle[i + 5][i + 2] == puzzle[i + 4][i + 2]) {
                legal = false;
            }
            if (puzzle[i][i + 3] == puzzle[i][i + 4] || puzzle[i][i + 4] == puzzle[i][i + 5]
                    || puzzle[i][i + 5] == puzzle[i + 1][i + 3]
                    || puzzle[i + 1][i] == puzzle[i + 2][i] || puzzle[i + 1][i + 3] == puzzle[i + 2][i + 3]
                    || puzzle[i + 1][i + 4] == puzzle[i + 1][i + 4]
                    || puzzle[i + 1][i + 4] == puzzle[i + 2][i + 4] || puzzle[i + 2][i + 5] == puzzle[i + 2][i + 4]) {
                legal = false;
            }
        }
        if (puzzle[6][0] == puzzle[7][0] || puzzle[7][0] == puzzle[8][0] || puzzle[8][0] == puzzle[6][1]
                || puzzle[6][1] == puzzle[6][2] || puzzle[6][2] == puzzle[7][1] || puzzle[7][1] == puzzle[7][2]
                || puzzle[7][2] == puzzle[8][1] || puzzle[8][1] == puzzle[8][2]) {
            legal = false;
        }
        if (puzzle[0][6] == puzzle[0][7] || puzzle[0][7] == puzzle[0][8] || puzzle[0][8] == puzzle[1][6]
                || puzzle[1][6] == puzzle[2][6] || puzzle[2][6] == puzzle[1][7] || puzzle[1][7] == puzzle[2][7]
                || puzzle[2][7] == puzzle[1][8] || puzzle[1][8] == puzzle[2][8]) {
            legal = false;
        }

        return legal;
    }

    public static void Display(int[][] puzzle) {
        for (int col = 0; col < puzzle.length; col++) {
            System.out.println();
            for (int row = 0; row < puzzle[0].length; row++) {
                System.out.print(" " + puzzle[col][row] + " ");
                if (row == 2 || row == 5) {
                    System.out.print("|");
                }
            }
            if (col == 2 || col == 5) {
                System.out.print("\n_____________________________");
            }
        }
    }
}