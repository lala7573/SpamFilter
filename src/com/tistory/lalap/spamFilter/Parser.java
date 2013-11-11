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

    public Vector<int[]> spam;
    public Vector<int[]> ham;

    public Vector<int[]> getSpam(){
        return spam;
    }
    public Vector<int[]> getHam(){
        return ham;
    }

    public Parser(String[] args) {
        spam = new Vector<int[]>();
        ham = new Vector<int[]>();

        try{
            //parsing
            parsingText(getFileList(args[0]+"spam"), spam, true);
            parsingText(getFileList(args[0]+"ham"), ham, false);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * return every file path in vPath.
     * First, i want to use Files.walkFileTree, but i didn't find uses enough.
     * so i declare this method with recursive
     *
     * @param path
     * @param vPath
     * @throws IOException
     * @return vPath
     * @deprecated
     */
    public static void walkingFileTree(Path path, Vector<Path> vPath) throws IOException {
        if( Files.isDirectory(path)){
            DirectoryStream<Path> dir = Files.newDirectoryStream(path);
            for(Path file : dir){
                if( Files.isDirectory(file) )
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
     * @param path
     * @return path.listFiles
     */
    public static File[] getFileList(String path){
        File file = new File(path);
        return file.listFiles();
    }
    /**
     * find key and set map(set of vector from mail)
     * @param vPath
     * @param map
     * @throws IOException
     */
    public void parsingText(File[] vPath, Vector<int[]> map, boolean isSpam) throws IOException {
        String line;
        int[] value;

        //for(Path file : vPath){
        for(File file : vPath){
            int index = 0;

            FileInputStream fis = new FileInputStream(file);

            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            value = new int[NaiveBayes.keyVector.length - 1];
            for(String key : NaiveBayes.keyVector) {
                while( null != (line = reader.readLine())){
                    if(-1 != line.indexOf(key)) {
                        value[index] = 1;
                        break;
                    }
                }
                ++index;

                fis.getChannel().position(0);
            }

            reader.close();
            fis.close();

            map.add(value);
        }
    }


}
