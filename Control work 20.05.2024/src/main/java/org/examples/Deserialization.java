package org.examples;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
public class Deserialization {
    public static ArrayList<String> deserialize(String nameOfFile){
        ArrayList<String> programs = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(nameOfFile))) {
            String line;
            while ((line = br.readLine())!= null)
            {
                programs.add(line);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return programs;
    }
}
