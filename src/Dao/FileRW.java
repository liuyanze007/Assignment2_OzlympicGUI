package Dao;

import Model.*;
import Ozlympic.Game;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FileRW {
    private static Set<String> readLines;
    private static List<Athlete> athletes;
    private static List<Official> officials;

    public static void writeFile(Game game){

        try(BufferedWriter writer = new BufferedWriter(new FileWriter(new File("gameResults.txt")))){
            String line = null;
            writer.write("cc");
            writer.flush();
        }catch(Exception ex){
            ex.printStackTrace();
        }

    }


    public static void readFile(String fileName) {
        readLines = new HashSet<>();
        try(BufferedReader reader = new BufferedReader(new FileReader(new File("participants.txt")))){
            String line = null;
            while((line = reader.readLine())!=null){
                String[] args = line.split(",",-1);
                if(args.length!=5){
                    continue;
                }
                boolean flag = true;
                for (int i = 0; i < 5; i++) {
                    if(args[i].equals("") || args[i].trim().equals("")){
                        flag = false;
                        break;
                    }
                }
                if(!flag){
                    continue;
                }
                readLines.add(line);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        if(readLines.size()==0){
            athletes = null;
            officials = null;
            return;
        }
        athletes = new ArrayList<>();
        officials = new ArrayList<>();
        for(String line : readLines){
            String[] args = line.split(",");
            switch (args[1]){
                case "officer":
                    officials.add(new Official(args[0],args[2],args[3],args[4]));
                    break;
                case "sprinter":
                    athletes.add(new sprinter(args[0],args[2],args[3],args[4]));
                    break;
                case "swimmer":
                    athletes.add(new swimmer(args[0],args[2],args[3],args[4]));
                    break;
                case "cyclist":
                    athletes.add(new cyclist(args[0],args[2],args[3],args[4]));
                    break;
                case "super":
                    athletes.add(new superAthlete(args[0],args[2],args[3],args[4]));
                    break;
            }
        }
    }


    public static List<Athlete> getAthletes() {
        return athletes;
    }

    public static List<Official> getOfficials() {
        return officials;
    }
}
