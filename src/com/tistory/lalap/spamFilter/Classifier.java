package com.tistory.lalap.spamFilter;

/**
 * Determine whether spam or not.
 *
 * @author <A HREF="mailto:lala7573@gmail.com">Yeonju, Hwang</A>
 * @version 1.0
 * @since 2013. 11. 10.
 */
public class Classifier {
    static final boolean SPAM = true;
    static final boolean HAM = false;

    /**
     * determined ham or spam from vectors of mail's parsing information.
     *
     * @param vectors
     * @return result[0] : determined that # of spam mail , result[1] : determined that # of ham
     */
    public static double[] getTestResult(int[][] vectors) {
        double[] result = {0.0, 0.0};

        for (int[] vector : vectors) {

            classify(result, vector);
        }
        return result;
    }

    private static void classify(double[] result, int[] vector) {
        double Pspam = ((double) NaiveBayes.spamCount / (double) (NaiveBayes.spamCount + NaiveBayes.hamCount));
        double Pham = ((double) NaiveBayes.hamCount / (double) (NaiveBayes.spamCount + NaiveBayes.hamCount));


        for (int i = 0; i < vector.length; i++) {
            if (1 == vector[i]) {
                Pspam *= NaiveBayes.getLikelihood(i, SPAM);
                Pham *= NaiveBayes.getLikelihood(i, HAM);
            } else {
                Pspam *= (1 - NaiveBayes.getLikelihood(i, SPAM));
                Pham *= (1 - NaiveBayes.getLikelihood(i, HAM));
            }
        }

        if (Pspam > Pham) {
            result[0] += 1;
        } else {
            result[1] += 1;
        }
    }
}
