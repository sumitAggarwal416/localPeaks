import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

/*
I, Sumit Aggarwal, student number - 000793607, certify that all work done is my own and that I have not copied it from
anywhere else. I also certify that I have not allowed anyone to copy my code.
 */

public class Main {

    public static void main(String[] args) {
        // write your code here
        final String filename = "src/ELEVATIONS.txt";
        int[][] matrix = {};
        int[] size = new int[3];

        File file = new File(filename);

        try {
            Scanner fileInput = new Scanner(file);

            for (int i = 0; i < size.length; i++) {
                size[i] = fileInput.nextInt();
            }

            fileInput.nextLine();
            matrix = new int[size[0]][size[1]];

            int rowCount = 0;
            int colCount = 0;
            while (fileInput.hasNext()) {
                if (colCount < size[1]) {
                    matrix[rowCount][colCount++] = fileInput.nextInt();
                } else {
                    colCount = 0;
                    matrix[++rowCount][colCount++] = fileInput.nextInt();
                }
            }
        } catch (IOException ex) {
            System.out.println("Error processing file " + ex.getMessage());
        }

        long startTime = System.nanoTime();
        int[] results = lowestValue(matrix);
        System.out.printf("The lowest elevation in the data is %d and it occurs %d times \n", results[0], results[1]);
        System.out.println();
        int[][] submatrix = localPeaks(matrix, size);
        closestPeaks(submatrix);
        mostCommon(matrix, size);

        long stopTime = System.nanoTime();
        System.out.printf("Total time taken: %d us", (stopTime - startTime) / 1000);
    }

    public static int[] lowestValue(int[][] data) {

        int minValue = data[0][0];
        int count = 0;
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                if (data[i][j] < minValue) {
                    minValue = data[i][j];
                } else if (data[i][j] == minValue) {
                    count++;
                } else {
                    continue;
                }
            }
        }

        if (minValue != data[0][0]) {
            count -= 1;
        }

        return new int[]{minValue, count};
    }

    public static int largestValue(int[][] data) {

        int maxValue = data[0][0];
        int count = 0;
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                if (data[i][j] > maxValue) {
                    maxValue = data[i][j];
                } else if (data[i][j] == maxValue) {
                    count++;
                } else {
                    continue;
                }
            }
        }

        if (maxValue != data[0][0]) {
            count -= 1;
        }

        return maxValue;
    }

    public static int[][] localPeaks(int[][] data, int[] size) {

        int maxPeaks = (size[0] * size[1]) / (size[2] * size[2]);
        int[][] submatrix = new int[maxPeaks][3];
        int value = 98480;
        int count = 0;

        for (int i = size[2]; i < size[0] - size[2]; i++) {
            for (int j = size[2]; j < size[1] - size[2]; j++) {
                if (data[i][j] >= value) {
                    boolean outOfLoop = false;

                    for (int row = i - size[2]; row < i + size[2] + 1 && !outOfLoop; row++) {
                        for (int col = j - size[2]; col < j + size[2] + 1; col++) {
                            if (row == i && col == j)
                                continue;
                            if (data[row][col] >= data[i][j]) {
                                outOfLoop = true;
                            }
                        }
                    }
                    if (outOfLoop == false) {
                        submatrix[count][0] = data[i][j];
                        submatrix[count][1] = i;
                        submatrix[count++][2] = j;
                    }
                }
            }
        }
        submatrix = Arrays.copyOf(submatrix, count);
        System.out.println("The total number of peaks are: " + count);
        return submatrix;
    }

    public static double closestPeaks(int[][] data) {

        int totalElements = data.length;
        int dataset = (totalElements * (totalElements - 1)) / 2;
        double[][] distance = new double[dataset][5];
        int count = 0;
        int[] rows = new int[data.length];
        int[] cols = new int[data.length];
        double[][] result = new double[5][5]; // I hard coded this because you gave us the result for the original data and the most we had was 3.

        try {
            for (int i = 0; i < data.length; i++) {
                rows[count] = data[i][1];
                cols[count++] = data[i][2];
            }
            count = 0;

            for (int x = 1; x < data.length; x++) {
                distance[count][1] = rows[x - 1];
                distance[count][2] = cols[x - 1];
                distance[count][3] = rows[x];
                distance[count][4] = cols[x];
                distance[count++][0] = Math.round(Math.sqrt((Math.pow(rows[x] - rows[x - 1], 2) + Math.pow(cols[x] - cols[x - 1], 2))) * 100.0) / 100.0;
            }
        } catch (Exception ex) {
            System.out.println("Program stopped unexpectedly: " + ex.getMessage());
        }

        distance = Arrays.copyOf(distance, count);

        double min = distance[0][0];
        count = 0;
        int row1 = 0;
        int col1 = 0;
        int row2 = 0;
        int col2 = 0;
        for (int i = 0; i < distance.length; i++) {
            if (distance[i][0] <= min) {
                min = distance[i][0];
                row1 = (int) distance[i][1];
                col1 = (int) distance[i][2];
                row2 = (int) distance[i][3];
                col2 = (int) distance[i][4];

            } else
                continue;
        }

        System.out.println("The minimum distance is " + min + " between numbers at " + row1 + "x" + col1 + " and " + row2 + "x" + col2);

        return 0;
    }

    public static int mostCommon(int[][] data, int[] size) {

        int[] flatData = new int[size[0] * size[1]];
        int count = 0;

        //to flatten the data
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                flatData[count++] = data[i][j];
            }
        }

        for (int i = 0; i < flatData.length; i++) {
            flatData[flatData[i] % flatData.length] += flatData.length;
        }

        count = 0;
        int index = 0;
        int max_element = largestValue(data);
        for (int i = 0; i < flatData.length; i++) {
            if (flatData[i] > max_element) {
                max_element = flatData[i];
                index = i;
            }
        }

        System.out.println("The most common height in the terrain is: " + index);


        return 0;
    }
}

