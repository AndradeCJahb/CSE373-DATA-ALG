package seamcarving;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Dynamic programming implementation of the {@link SeamFinder} interface.
 *
 * @see SeamFinder
 * @see SeamCarver
 */
public class DynamicProgrammingSeamFinder implements SeamFinder {
    @Override
    public List<Integer> findHorizontalSeam(double[][] energies) {
        double[][] temp = new double[energies[0].length][energies.length];
        for (int i = 0; i < energies.length; i++) {
            for (int j = 0; j < energies[0].length; j++) {
                temp[j][i] = energies[i][j];
            }
        }
        return findVerticalSeam(temp);
    }

    @Override
    public List<Integer> findVerticalSeam(double[][] energies) {

        Map<String, Integer> back = new HashMap<>();
        double[][] arr = new double[energies.length][energies[0].length];

        for (int i = 0; i < energies.length; i++) {
            System.arraycopy(energies[i], 0, arr[i], 0, energies[0].length);
        }

        for (int col = 1; col < arr[0].length; col++) {
            for (int row = 0; row < arr.length; row++) {
                double min = arr[row][col - 1];
                String curr = row + " " + col;
                back.put(curr, row);

                if (row - 1 > -1) {
                    if (arr[row - 1][col - 1] < min) {
                        min = arr[row - 1][col - 1];
                        back.put(curr, row - 1);
                    }
                }
                if (row + 1 < arr.length) {
                    if (arr[row + 1][col - 1] < min) {
                        min = arr[row + 1][col - 1];
                        back.put(curr, row + 1);
                    }
                }
                arr[row][col] = min + arr[row][col];
            }
        }

        double min = arr[0][arr[0].length - 1];
        int minY = 0;
        for (int i = 1; i < arr.length; i++) {
            if (arr[i][arr[0].length - 1] < min) {
                min = arr[i][arr[0].length - 1];
                minY = i;
            }
        }

        List<Integer> ret = new ArrayList<>();
        String curr = minY + " " + (arr[0].length - 1);
        ret.add(0, minY);
        for (int x = arr[0].length - 2; x > -1; x--) {
            int next = back.get(curr);
            ret.add(0, next);
            curr = next + " " + x;
        }

        return ret;
    }
}
