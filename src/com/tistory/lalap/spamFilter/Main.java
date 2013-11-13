package com.tistory.lalap.spamFilter;

import java.io.IOException;

/**
 * Main
 *
 * @author <A HREF="mailto:lala7573@gmail.com">Yeonju, Hwang</A>
 * @version 1.0
 * @since 2013. 11. 1.
 */
public class Main {
    static final String STUDENT_NUMBER = "12111699";

    public static void main(String[] args) throws IOException {
        if (1 != args.length) {
            System.out.println("Uses : FilteringSpam.jar <C:/dir/>");
            System.exit(-1);
        }

        NaiveBayes.setLikelihood();//args[0]);
        Parser parser = new Parser();

        // result[0][0] : True Positive     result[0][1] : False Negative
        // result[1][0] : False Positive    result[1][1] : True Negative

        double[][] result = new double[2][2];
        result[0] = Classifier.getTestResult(parser.getSpam(args[0]));
        result[1] = Classifier.getTestResult(parser.getHam(args[0]));

        printResult(result);
    }

    /**
     * print
     *
     * @param result
     */
    private static void printResult(double[][] result) {
        double precision = result[0][0] / (result[0][0] + result[1][0]);
        double recall = result[0][0] / (result[0][0] + result[0][1]);

        System.out.println(STUDENT_NUMBER);
        System.out.println("TP : " + result[0][0] + " TN : " + result[1][1] + " FP : " + result[1][0] + " FN : " + result[0][1]);
        System.out.println("Precision : " + precision + " Recall : " + recall);
    }
}
