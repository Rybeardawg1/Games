import java.util.Stack;

public class MagicSquare {
    
    int[][] square = new int[3][3];
    Stack<Integer> stack= new Stack<>();  
    boolean[] nums = new boolean[9];

    public static void main(String[]args) {
        MagicSquare square = new MagicSquare();
        square.Solve();
    }
    public void Solve() {
        int start = 0;
        for(int i = 0; i < 3; i++) {
            for(int z = 0; z < 3; z++) {
                if(square[i][z] == 0) {
                    start = Integer.parseInt(i + "" + z);
                    break;
                }
            }
        }
        stack.push(start);
        int col = start/10;
        int row = start%10;
        int num = 0;
        for(int i = 0; i < 9; i++) {
            if(nums[i] == false) {
                num = i++;
                break;
            }
        }
        square[col][row] = num;

        if(!Check()) {
            Solve();
        } 
    }

    public boolean Check() {
        for(int i = 0; i < 3; i++) {
            for(int z = 0; z < 3; z++) {
                if(square[i][z] == 0) {
                    return false;
                }
            }
        }
        if(square[0][0] + square[0][1] + square[0][2] != 15) {
            return false;
        }if(square[1][0] + square[1][1] + square[1][2] != 15) {
            return false;
        }if(square[2][0] + square[2][1] + square[2][2] != 15) {
            return false;
        }if(square[0][0] + square[1][0] + square[2][0] != 15) {
            return false;
        }if(square[0][1] + square[1][1] + square[2][1] != 15) {
            return false;
        }if(square[0][2] + square[1][2] + square[2][2] != 15) {
            return false;
        }if(square[0][0] + square[1][1] + square[2][2] != 15) {
            return false;
        }if(square[0][2] + square[1][1] + square[2][0] != 15) {
            return false;
        }
        return true;
    }

    public void Display() {
        for (int col = 0; col < square.length; col++) {
            System.out.println();
            for (int row = 0; row < square[0].length; row++) {
                System.out.print(" " + square[col][row] + " ");
            }
        }
    }
}