package com.tistory.lalap.spamFilter;

import java.io.*;

/**
 * Decode resource/Spambase.txt to keyVector, hamVector and spamVector,
 * and get spamCount and hamCount
 *
 * @author <A HREF="mailto:lala7573@gmail.com">Yeonju, Hwang</A>
 * @version 1.0
 * @since 2013. 11. 1.
 */
public class NaiveBayes {

    static String[] keyVector;
    static int[] hamVector;
    static int[] spamVector;

    static int spamCount=0;
    static int hamCount=0;

    public static double getLikelihood(int index, boolean isSpam){
        if( isSpam ){
            return (double)spamVector[index]/(double)spamCount;
        } else {
            return (double)hamVector[index]/(double)hamCount;
        }
    }
    public static void setLikelihood() {
        try{

            InputStream fr = NaiveBayes.class.getResourceAsStream("Spambase.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(fr));

            String line;
            if((line = reader.readLine()) == null){
                System.out.println("Spambase.txt missing");
                return;
            } else {
                keyVector = line.split("\t");
            }
            hamVector = new int[keyVector.length-1];
            spamVector = new int[keyVector.length-1];

            setValue(reader);

            reader.close();
            fr.close();

        } catch(FileNotFoundException e){
            e.printStackTrace();
        } catch(IOException e){
            e.printStackTrace();
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    private static void setValue(BufferedReader reader) throws IOException {
        String line;
        String[] temp;
        while( null != (line = reader.readLine())) {

            temp = line.split("\t");

            if(isSpam(temp)) {
                ++spamCount;
                for(int i = 0 ; i < temp.length - 1; i++){
                    spamVector[i] = spamVector[i] +Integer.parseInt(temp[i]);
                }
            } else {
                ++hamCount;
                for(int i = 0 ; i < temp.length - 1; i++){
                    hamVector[i] = hamVector[i] +Integer.parseInt(temp[i]);
                }
            }
        }
    }


    private static boolean isSpam(String[] temp) {
        return 1 == Integer.parseInt(temp[temp.length-1]);
    }

}
