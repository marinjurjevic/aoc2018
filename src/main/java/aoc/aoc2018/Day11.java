package aoc.aoc2018;

public class Day11 {

    public static final int GRID_SIZE = 300;

    public static final int SQUARE_SIZE = 3;

    public static final int GRID_SERIAL_NUMBER = 8979;

    public static int[][] partial_sums_table;
    
    public static void main(String[] args) {
        bruteForcePart1();
        partialSumsPart2();
    }

    public static void partialSumsPart2() {
        int cellPower = 0;
        partial_sums_table = new int[GRID_SIZE + 1][GRID_SIZE + 1];

        /* Calculate Partial Sums Table */
        for (int i = 1; i <= GRID_SIZE; i++) {
            for (int j = 1; j <= GRID_SIZE; j++) {
                cellPower = calculateCoordinatePower(i, j);
                partial_sums_table[i][j] = cellPower + 
                    partial_sums_table[i - 1][j] + 
                    partial_sums_table[i][j - 1] - 
                    partial_sums_table[i - 1][j - 1];
            }
        }

        /* Find square with largest total Power */
        int totalPowerMax = Integer.MIN_VALUE;
        int x_max = 1;
        int y_max = 1;

        int squarePower = 0;
        int squareSize = 1;
        for(int k = 1; k <= GRID_SIZE; k++ ){
            for(int i = k; i <= GRID_SIZE; i++) {
                for(int j = k; j <= GRID_SIZE; j++) {
                    squarePower = partial_sums_table[i][j] - 
                        partial_sums_table[i - k][j] - 
                        partial_sums_table[i][j - k] + 
                        partial_sums_table[i - k][j - k];

                    if(squarePower > totalPowerMax) {
                        x_max = i - k + 1;
                        y_max = j - k + 1;
                        totalPowerMax = squarePower;
                        squareSize = k;
                    }
                }
            }
        }

        System.out.println(String.format("Maximum (Part 2): %d on (%d,%d,%d)",
            totalPowerMax, x_max, y_max, squareSize));
    }

    public static void bruteForcePart1() {
        int totalPowerMax = 0;
        int x_max = 1;
        int y_max = 1;

        int squarePower = 0;

        for (int i = 1; i <= GRID_SIZE - SQUARE_SIZE + 1; i++) {
            for (int j = 1; j <= GRID_SIZE - SQUARE_SIZE + 1; j++) {
                squarePower = calculateSquareTotalPower(i, j);
                if(squarePower > totalPowerMax)
                {
                    x_max = i;
                    y_max = j;
                    totalPowerMax = squarePower;
                }     
            }
        }

        System.out.println(String.format("Maximum: %d on (%d,%d)",
            totalPowerMax, x_max, y_max));
    }

    public static int calculateSquareTotalPower(int x, int y)
    {
        int totalPower = 0;

        for(int i = x; i < x + SQUARE_SIZE; i++)
        {
            for(int j = y; j < y + SQUARE_SIZE; j++){
                totalPower += calculateCoordinatePower(i, j);
            }
        }

        return totalPower;
    }

    public static int calculateCoordinatePower(int x, int y) {
        int power = 0;

        /* Find the fuel cell's rack ID, which is its X coordinate plus 10 */
        int rack_id = x + 10;

        /* Begin with a power level of the rack ID times the Y coordinate. */
        power = rack_id * y;

        /* Increase the power level by the value of the grid 
         * serial number (your puzzle input).
         */
        power += GRID_SERIAL_NUMBER;

        /* Set the power level to itself multiplied by the rack ID. */
        power *= rack_id;

        /* Keep only the hundreds digit of the power level (so 12345 becomes 3; 
         * numbers with no hundreds digit become 0).
         */
        power = (power / 100) % 10;

        /* Subtract 5 from the power level. */

        power -= 5;

        return power;
    }
}
