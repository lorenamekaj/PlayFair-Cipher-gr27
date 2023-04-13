import java.util.Scanner;

public class PlayfairDecryption {
    private char[][] keyMatrix;

    // Generate the key matrix for Playfair encryption
    public void generateKeyMatrix(String key) {

        key = key.toUpperCase().replaceAll("[^A-Z]", ""); // Convert to uppercase and remove non-alphabetic characters
       
        key = key.replace("J", "I"); // Replace J with I

        boolean[] visited = new boolean[26];
        keyMatrix = new char[5][5];
        
        int row = 0, col = 0;

        for (char c : key.toCharArray()) {
            if (!visited[c - 'A']) {
                visited[c - 'A'] = true;
                keyMatrix[row][col] = c;
                col++;
                if (col == 5) {
                    row++;
                    col = 0;
                }
            }
        }


        for (char c = 'A'; c <= 'Z'; c++) {
            if (c != 'J' && !visited[c - 'A']) {
                keyMatrix[row][col] = c;
                col++;
                if (col == 5) {
                    row++;
                    col = 0;
                }

            }

        }

    }





    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the key: ");
        String key = scanner.nextLine();
        PlayfairDecryption playfair = new PlayfairDecryption();
        playfair.generateKeyMatrix(key);

       
    }
}