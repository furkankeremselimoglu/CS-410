import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class KEREM_SELIMOGLU_S018065 {

    enum results {accepted, rejected, looped}

    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(new File("C:\\Users\\furka\\Desktop\\CS Workspace\\CS 410\\Projects\\Project3\\input file.txt"));

            int n_input_alphabet, n_tape_alphabet;
            String[] tape_alphabet;
            String[] input_alphabet;
            String blank_symbol, start_state, accept_state, reject_state, to_detect;
            int n_states;
            String[] states;
            ArrayList<String> lines = new ArrayList<>();

            n_input_alphabet = Integer.parseInt(scanner.nextLine());
            input_alphabet = new String[n_input_alphabet];
            input_alphabet = scanner.nextLine().split(" ");
            n_tape_alphabet = Integer.parseInt(scanner.nextLine());
            tape_alphabet = new String[n_tape_alphabet];
            tape_alphabet = scanner.nextLine().split(" ");
            blank_symbol = scanner.nextLine();
            n_states = Integer.parseInt(scanner.nextLine());
            states = new String[n_states];
            states = scanner.nextLine().split(" ");
            start_state = scanner.nextLine();
            accept_state = scanner.nextLine();
            reject_state = scanner.nextLine();

            while(scanner.hasNextLine()) {
                lines.add(scanner.nextLine());
            }
            to_detect = lines.get(lines.size() - 1);
            lines.remove(lines.size() - 1);

            System.out.print("The input alphabet): " + input_alphabet[0]);
            for (String input:input_alphabet) {
                if (input != input_alphabet[0])
                    System.out.print(", " + input);
            }
            System.out.println();
            System.out.print("The tape alphabet): " + tape_alphabet[0]);
            for (String tape:tape_alphabet) {
                if (tape != tape_alphabet[0])
                    System.out.print(", " + tape);
            }
            System.out.println();
            System.out.println("Blank symbol: " + blank_symbol);
            System.out.print("States: " + states[0]);
            for (String state:states) {
                if (state != states[0])
                    System.out.print(", " + state);
            }
            System.out.println();
            System.out.println("Start state: " + start_state);
            System.out.println("Accept state: " + accept_state);
            System.out.println("Reject state: " + reject_state);
            System.out.println();
            System.out.println("These are the given lines for turing machine: ");
            for (String line:lines) {
                System.out.println(line);
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}