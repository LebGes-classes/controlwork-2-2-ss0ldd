package org.examples;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.*;
import java.util.*;

public class Main  {
    public static final String EXCEL_DATA_FILE = "data.xlsx";
    public static final String TEXT_DATA_FILE = "C:\\Users\\cscsm\\Downloads\\data.txt";

    private static Workbook workbook;

    public static void main(String[] args) {
        List<Program> programs = readFromFile();

        programs.sort(Comparator.comparing(Program::getTime));
        System.out.println("Передачи по каналу и времени");
        programs.forEach(program -> System.out.println(program.toString()));
        System.out.println("Передачи идут сейчас");
        BroadcastsTime now = BroadcastsTime.now();
        for (int i = 0; i < (programs.size() - 1); i++) {
            if (now.between(programs.get(i).getTime(), programs.get(i + 1).getTime())) {
                System.out.println(programs.get(i).toString());
            }
        }
        String programName = "Время";
        System.out.println("Все передачи: " + programName);
        programs.stream()
                .filter(program -> program.getName().equals(programName))
                .forEach(System.out::println);
        String channelName = "Карусель";
        System.out.println("Все передачи канала: " + channelName + ", которые идут сейчас");
        for (int i = 0; i < (programs.size() - 1); i++) {
            if (programs.get(i).getChannel().equals(channelName)
                    && now.between(programs.get(i).getTime(), programs.get(i + 1).getTime())) {
                System.out.println(programs.get(i).toString());
            }
        }
        writeToFile(programs);
    }

    public static List<Program> readFromFile() {
        List<Program> programs = new ArrayList<>();
        try (FileInputStream inputStream = new FileInputStream(TEXT_DATA_FILE)) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            String channel = "";
            while (true) {
                line = reader.readLine();
                if (line == null) {
                    break;
                }
                if (line.startsWith("#")) {
                    channel = line.substring(1);
                    line = reader.readLine();
                    if (line == null) {
                        break;
                    }
                }
                BroadcastsTime time = BroadcastsTime.parse(line);
                line = reader.readLine();
                if (line == null) {
                    break;
                }
                String name = line;
                Program program = new Program(channel, time, name);
                programs.add(program);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return programs;
    }

    public static void writeToFile(List<Program> programs) {
        try (FileOutputStream outputStream = new FileOutputStream(EXCEL_DATA_FILE)) {
            Sheet sheet = workbook.getSheet("Sheet1");
            for (int i = 0; i <= programs.size(); i++) {
                if (sheet.getRow(i).getCell(0) == null) {
                    sheet.getRow(i).createCell(0);
                }
                sheet.getRow(i).getCell(0).setCellValue(programs.get(0).toString());
            }
            workbook.write(outputStream);
            workbook.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}