package cn.edu.jmu.cec.Test;

/**
 * Created by yangjb on 2015/4/2.
 */
public class Test {
    int N = 2;
    int[] rowf = new int[N + 1];
    static int n = 0;

    public boolean isOk(int col, int row) {
        if (rowf[row] != 0) {
            return false;
        }
        for (int i = 1; i < rowf.length; i++) {
            if (rowf[i] != 0) {
                if (rowf[i] == col) {
                    return false;
                } else if (Math.abs((i - row) / (rowf[i] - col)) == 1) {
                    return false;
                }
            }
        }
        return true;
    }

    public void dsp(int row) {
        if (row < N +1) {
            for (int i = 1; i < N + 1; i++) {
//                System.out.println(i+","+row);
                if (isOk(i, row)) {
//                    System.out.println(row);
                    if (row == N) {
                        n++;
                    } else {
                        rowf[row] = i;
                        dsp(row + 1);
                        rowf[row] = 0;
                    }

                }
            }
        }
    }

    public static void main(String[] args) {
        Test test = new Test();
        test.dsp(1);
        System.out.println(test.n);
    }
}
