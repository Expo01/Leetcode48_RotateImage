import java.util.HashSet;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
    }
}


// this is from youtube with clockwise rotation algorithm. there is another algorithm that
// transposes across diagonal and then reverses, but not gunna do that right now
class Solution {
    public void rotate(int[][] matrix) {
        int n = matrix.length;
        int layers = n/2;

        for(int layer = 0; layer < layers; layer++){
            int start = layer;
            int end = n-1-layer;

            for(int i = start; i < end; i++){
                int temp = matrix[start][i];
                matrix[start][i] = matrix[n-1-i][start];
                matrix[n-1-i][start] = matrix[end][n-1-i];
                matrix[end][n-1-i] = matrix[i][end];
                matrix[i][end] = temp;
            }
        }
    }
}


class Solution {
    public void rotate(int[][] matrix) {
        int n = matrix.length;
        for (int i = 0; i < (n + 1) / 2; i ++) {
            for (int j = 0; j < n / 2; j++) {
                int temp = matrix[n - 1 - j][i]; // bottom L
                matrix[n - 1 - j][i] = matrix[n - 1 - i][n - j - 1]; // bottom L = bottom R
                matrix[n - 1 - i][n - j - 1] = matrix[j][n - 1 -i]; // bottom R = top R
                matrix[j][n - 1 - i] = matrix[i][j]; // top R = top L
                matrix[i][j] = temp; // temp = top L
            }
            // this doesn't loop full perimeter and worrk in. its strange. it swaps all non-middle
            // from outteer to in thn goes back and swaps the middles in each layeer
        }
    }
}





//attempt
class Solution {

    public void rotate(int[][] matrix) {
        int U_Bound = 0, R_Bound = matrix[0].length-1, D_Bound = matrix.length-1, L_Bound = 0;

        int layers = matrix.length; // can derive required depth. if odd length say 3. then 3/2 = 1 int val
        if(layers % 2 == 0){        // for layers 0,1 with single center point
            layers = layers/2 -1;   // but even say 4/2 = 2 requires -1 to get layer 0,1 with 4 center points
        } else{
            layers /= 2;
        }

        for(int layer = 0; layer <= layers; layer++){ // layer indicates perimeter layer of image to cycle swap

            for(int i = layer; i < matrix.length-1; i++){ // image is a square, if we traverse the whole row -1 then all
                // cells swapped. don't want to repeat a swap which is why -1 since if matrix.len = 4 oon first iteration
                // would swap top L for top R and oon 4th iteration would swap top R which is already done
                cycleSwap(layer,i, matrix, U_Bound,R_Bound,D_Bound,L_Bound);
            }
        }

    }

    private void cycleSwap(int r, int c, int[][] matrix, int U_Bound, int R_Bound, int D_Bound, int L_Bound){
        int offset = c;
        int swap = matrix[r][c]; // top L val  = 1
        int temp = matrix[r+offset][R_Bound]; // top R val    = 2                   // 43
        matrix[r+offset][R_Bound] = swap; // top R = 1

        swap = temp; // swap = 2. at this point top L and top R are start cell, 3 more cycles to go
        temp = matrix[D_Bound][R_Bound - offset]; // temp reassigned 2 -> 3
        matrix[D_Bound][R_Bound - offset] = swap; // bottom R becomes 2


        swap = temp; // swap = 3
        temp = matrix[D_Bound - offset][L_Bound]; // temp = 4
        matrix[D_Bound - offset][L_Bound] = swap; // bottom L = 3

        swap = temp; // 4
        matrix[U_Bound][L_Bound + offset] = swap; // top L = 4

        // all cells rotated and added to seen hashset and are swapped by derived offset

    }

}


/*

 while(seen.size() < D_Bound * R_Bound)  use of seen but can eliminatee to reeeduce mem


this was going to scan cell by ceell

        while(seen.size() < D_Bound * R_Bound){ //
            for(int r = 0; r < matrix.length; r++){
                for(int c = 0; c < matrix[0].length; c++){
                    int cell = matrix[r][c];
                    if(!seen.contains(cell)){
                        cycleSwap(r,c, matrix, U_Bound,R_Bound,D_Bound,L_Bound);
                        // modify bounds here?
                    }
                }

            }
        }

 private void cycleSwap(int r, int c, int[][] matrix, int U_Bound, int R_Bound, int D_Bound, int L_Bound){
        int swap = matrix[r][c]; // top L val  = 1
        seen.add(swap); // add 1
        int temp = matrix[U_Bound][R_Bound]; // top R val    = 2                   // 43
        matrix[U_Bound][R_Bound] = swap; // top R = 1


        swap = temp; // swap = 2. at this point top L and top R are start cell, 3 more cycles to go
        seen.add(swap); // add 2
        temp = matrix[D_Bound][R_Bound]; // temp reassigned 2 -> 3
        matrix[D_Bound][R_Bound] = swap; // bottom R becomes 2


        swap = temp; // swap = 3
        seen.add(swap); // add 3
        temp = matrix[D_Bound][L_Bound]; // temp = 4
        matrix[D_Bound][L_Bound] = swap; // bottom L = 3

        swap = temp; // 4
        seen.add(swap); // add 4
        matrix[U_Bound][L_Bound] = swap; // top L = 4

        // all cells rotated and added to seen hashset.
        // don't know how i would modify the bounds though? i can't shrink the whole perimeteer by 1 until first
        // perimeter layer complte, but how?

        // maybe not bounds, but instead derive swap coordinates without storing a new bound
        // int offset = c   since move to R is first position indicator....
        // like TL cell would be [r][c] where c incremented to 1 on second looping
        //      TR               [r+off][RBound]
        //      BR               [DBound][Rbound - off]
        //      BL               [DBound - off][LBound]



    }
 */


/*

use exmple 2. take 5 as temp 1, 11 as temp 2. reassign top R cell as 5
then reassign temp 1 as 11. go forward reassigning temps and swapping corners, recording cell coordinate in dataset. all examples though
show numbers are continuous so for now just store the val of the
cell in a hashset, assuming no redundancies. this would be the inner
while loop condition to call on a swap method or soemthing while coners
left unswapped

then outer for loop would change which has an iterative approach from
L to R top to bottom and saying if cell not in hashset then swap again, this
time starting with 13 but would have so say to swap from row index 1 to right
most col index 1 to rightmost col-1  to bottommost row -1. would need swap
condition where it will look for swap at furthest, R,D,L,U. will involve
some mathing here with indexes...

then eventually get to cell with val 3 in outer looptraversal, flip the final
4 cells and thats it

*/