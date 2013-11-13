package com.tistory.lalap.spamFilter;


import java.io.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Vector;

/**
 * parsing
 *
 * @author <A HREF="mailto:lala7573@gmail.com">Yeonju, Hwang</A>
 * @version 1.0
 * @see [Class name#method name]
 * @since 2013. 11. 1.
 */
public class Parser {


    public static int[][] spam;
    public static int[][] ham;
    static char[] buffer = new char[1024];

    public static int[][] getSpam(String dir) throws IOException {
        return parsingText(getFileList(dir + "spam"));
    }

    public static int[][] getHam(String dir) throws IOException {
        return parsingText(getFileList(dir + "ham"));
    }

    /**
     * return every file path in vPath.
     * First, i want to use Files.walkFileTree, but i didn't find uses enough.
     * so i declare this method with recursive
     *
     * @param path
     * @param vPath
     * @return vPath
     * @throws IOException
     * @deprecated
     */
    public static void walkingFileTree(Path path, Vector<Path> vPath) throws IOException {
        if (Files.isDirectory(path)) {
            DirectoryStream<Path> dir = Files.newDirectoryStream(path);
            for (Path file : dir) {
                if (Files.isDirectory(file))
                    walkingFileTree(file, vPath);
                else
                    vPath.add(file);
            }
            dir.close();
        } else {
            vPath.add(path);
        }
    }

    /**
     * get directory file name and return files in that directory
     *
     * @param path
     * @return path.listFiles
     */
    public static File[] getFileList(String path) {
        File file = new File(path);
        return file.listFiles();
    }

    /**
     * find key and set map(set of vector from mail)
     *
     * @param vPath
     * @throws IOException
     */
    public static int[][] parsingText(File[] vPath) throws IOException {

        int keyLength = NaiveBayes.keyVector.length - 1;
        int[] value;
        int[][] map = new int[vPath.length][keyLength];


        for (int fileIndex = 0; fileIndex < vPath.length; ++fileIndex) {
            FileInputStream fis = new FileInputStream(vPath[fileIndex]);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));

            value = new int[keyLength];
            StringBuilder fileToString = new StringBuilder();
            while (-1 != reader.read(buffer)) {
                fileToString.append(buffer);
            }

            for (int index = 0; index < keyLength; ++index) {   //-2인 이유는 마지막에 isSpam을 빼주려고

                if (-1 != fileToString.indexOf(NaiveBayes.keyVector[index])) {
                    value[index] = 1;
                }
            }
            map[fileIndex] = value;

            reader.close();
            fis.close();
        }
        return map;
    }


}
