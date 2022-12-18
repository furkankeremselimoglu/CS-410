import java.util.*;
import java.io.*;


public class KEREM_SELIMOGLU_S018065 {
    static char[] chars = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
    static String[] titles = { "NON-TERMINAL", "TERMINAL", "RULES", "START" };
    static Character charS;
    static ArrayList<String> listP = new ArrayList<>();
    static ArrayList<Character> listN = new ArrayList<>();
    static ArrayList<Character> listT = new ArrayList<>();


    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(new File("C:\\Users\\furka\\Desktop\\CS 410\\Projects\\Project 2\\G1.txt"));
            ArrayList<String> lines = new ArrayList<>();
            while(scanner.hasNextLine()) {
                lines.add(scanner.nextLine());
            }

            for(int i = 0; i < lines.size(); i++) {
                String currentLine = lines.get(i++);
                switch (currentLine) {
                    case "NON-TERMINAL" -> {
                        currentLine = lines.get(i);
                        while (!Arrays.asList(titles).contains(currentLine)) {
                            listN.add(currentLine.toCharArray()[0]);
                            i++;
                            currentLine = lines.get(i);
                        }
                        i--;
                    }
                    case "TERMINAL" -> {
                        currentLine = lines.get(i);
                        while (!Arrays.asList(titles).contains(currentLine)) {
                            listT.add(currentLine.toCharArray()[0]);
                            i++;
                            currentLine = lines.get(i);
                        }
                        i--;
                    }
                    case "RULES" -> {
                        currentLine = lines.get(i);
                        while (!Arrays.asList(titles).contains(currentLine)) {
                            listP.add(currentLine);
                            i++;
                            currentLine = lines.get(i);
                        }
                        i--;
                    }
                    case "START" -> {
                        currentLine = lines.get(i);
                        charS = currentLine.toCharArray()[0];
                        i--;
                    }
                }
            }

            boolean checkAnyStartRightHandSide = false;
            for(int i = 0; i < listP.size(); i++) {
                if(listP.get(i).substring(1).contains(charS + "")) {
                    checkAnyStartRightHandSide = true;
                    i = listP.size();
                }
            }

            char free_char = ' ';
            int index = 0;
            while (index < chars.length) {
                boolean free = true;
                for(int i = 0; i < listN.size(); i++) {
                    if(chars[index] == listN.get(i)) {
                        free = false;
                        i = listN.size();
                    }
                }
                if(free) {
                    free_char = chars[index];
                    index = chars.length;
                }
                index++;
            }

            if(checkAnyStartRightHandSide) {
                listN.add(free_char);
                listP.add(free_char + ":" + charS);
                charS = free_char;
            }

            int epsilon_index = Integer.MIN_VALUE;
            for(int i = 0; i < listP.size(); i++) {
                if ((listP.get(i).charAt(0) != charS)) {
                    if (listP.get(i).contains("e")) {
                        epsilon_index = i;
                        i = listP.size();
                    }
                }
            }
            while (epsilon_index >= 0) {
                String epsilonP = listP.get(epsilon_index);
                char epsilonT = epsilonP.charAt(0);
                listP.remove(epsilon_index);
                for (int i = 0; i < listP.size(); i++) {
                    String currentP = listP.get(i);
                    for (int j = 2; j < currentP.length(); j++) {
                        char currentT = currentP.charAt(j);
                        if (currentT == epsilonT) {
                            String newP;
                            if(currentP.length() == 3) {
                                newP = currentP.charAt(0) + ":e";
                            } else {
                                newP = currentP.charAt(0) + ":" + currentP.substring(2, j)
                                        + currentP.substring(j + 1);
                            }
                            listP.add(newP);
                        }
                    }
                }
                epsilon_index = Integer.MIN_VALUE;
                for(int i = 0; i < listP.size(); i++) {
                    if ((listP.get(i).charAt(0) != charS)) {
                        if (listP.get(i).contains("e")) {
                            epsilon_index = i;
                            i = listP.size();
                        }
                    }
                }
            }

            int unitProduction_index = Integer.MIN_VALUE;
            for (int i = 0; i < listP.size(); i++) {
                if (listP.get(i).length() == 3) {
                    if(Character.isUpperCase(listP.get(i).charAt(2))) {
                        unitProduction_index = i;
                        i = listP.size();
                    }
                }
            }
            while (unitProduction_index >= 0) {
                String unitP = listP.get(unitProduction_index);
                char unitT = unitP.charAt(0);
                char targetT = unitP.charAt(2);
                listP.remove(unitProduction_index);
                if (unitT != targetT) {
                    for (int i = 0; i < listP.size(); i++) {
                        String currentP = listP.get(i);
                        if (currentP.charAt(0) == targetT && !currentP.equals(targetT + ":" + targetT)) {
                            String newP = unitT + ":" + currentP.substring(2);
                            listP.add(newP);
                        }
                    }
                }
                unitProduction_index = Integer.MIN_VALUE;
                for (int i = 0; i < listP.size(); i++) {
                    if (listP.get(i).length() == 3) {
                        if(Character.isUpperCase(listP.get(i).charAt(2))) {
                            unitProduction_index = i;
                            i = listP.size();
                        }
                    }
                }
            }

            ArrayList<Character> terminal_productions = new ArrayList<>();

            int mixedRHS_index = Integer.MIN_VALUE;
            for (int i = 0; i < listP.size(); i++) {
                String currentP = listP.get(i);
                if (currentP.length() > 3) {
                    for (int j = 2; j < currentP.length(); j++) {
                        if (!Character.isUpperCase(currentP.charAt(j))) {
                            if (currentP.charAt(j) != ':') {
                                mixedRHS_index = i;
                                i = listP.size();
                                j = currentP.length();
                            }
                        }
                    }
                }
            }
            while (mixedRHS_index >= 0) {
                String currentP2 = listP.get(mixedRHS_index);
                listP.remove(mixedRHS_index);

                for (int i = 0; i < currentP2.length(); i++) {
                    char currentT = currentP2.charAt(i);
                    if (!Character.isUpperCase(currentT) && currentT != ':') {
                        for (int j = 0; j < listT.size(); j++) {
                            if (currentT == listT.get(j)) {
                                try {
                                    terminal_productions.get(j);
                                } catch (Exception e) {
                                    index = 0;
                                    while (index < chars.length) {
                                        boolean free = true;
                                        for (int k = 0; k < listN.size(); k++) {
                                            if (chars[index] == listN.get(k)) {
                                                free = false;
                                                k = listN.size();
                                            }
                                        }
                                        if (free) {
                                            free_char = chars[index];
                                            index = chars.length;
                                        }
                                        index++;
                                    }
                                    String newP = free_char + ":" + currentT;
                                    terminal_productions.add(j, free_char);
                                    listP.add(newP);
                                    listN.add(free_char);
                                }
                                String newP = currentP2.substring(0, i) + terminal_productions.get(j) + currentP2.substring(i + 1);
                                listP.add(newP);
                            }
                        }
                        i = currentP2.length();
                    }
                }
                mixedRHS_index = Integer.MIN_VALUE;
                for (int i = 0; i < listP.size(); i++) {
                    String currentP = listP.get(i);
                    if (currentP.length() > 3) {
                        for (int j = 2; j < currentP.length(); j++) {
                            if (!Character.isUpperCase(currentP.charAt(j))) {
                                if (currentP.charAt(j) != ':') {
                                    mixedRHS_index = i;
                                    i = listP.size();
                                    j = currentP.length();
                                }
                            }
                        }
                    }
                }
            }

            int moreThan2RHSN_index = Integer.MIN_VALUE;
            for (int i = 0; i < listP.size(); i++) {
                String tmp = listP.get(i);
                if (tmp.length() > 4) {
                    moreThan2RHSN_index = i;
                    i = listP.size();
                }
            }
            while (moreThan2RHSN_index >= 0) {
                String P = listP.get(moreThan2RHSN_index);
                listP.remove(moreThan2RHSN_index);
                index = 0;
                while(index < chars.length) {
                    boolean free = true;
                    for (int i = 0; i < listN.size(); i++) {
                        if (chars[index] == listN.get(i)) {
                            free = false;
                            i = listN.size();
                        }
                    }
                    if(free) {
                        free_char = chars[index];
                        index = chars.length;
                    }
                    index++;
                }
                listN.add(free_char);
                String newP1 = free_char + ":" + P.substring(2, 4);
                String newP2 = P.substring(0, 2) + free_char + P.substring(4);
                listP.add(newP2);
                listP.add(newP1);

                moreThan2RHSN_index = Integer.MIN_VALUE;
                for(int i = 0; i < listP.size(); i++) {
                    String tmp = listP.get(i);
                    if(tmp.length() > 4) {
                        moreThan2RHSN_index = i;
                        i = listP.size();
                    }
                }
            }

            System.out.println("NON-TERMINAL");
            for(Character character : listN) {
                System.out.println(character);
            }
            System.out.println("TERMINAL");
            for(Character character : listT) {
                System.out.println(character);
            }
            System.out.println("RULES");
            for(String s : listP) {
                System.out.println(s);
            }
            System.out.println("START");
            System.out.println(charS);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}

