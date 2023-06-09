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

    //Decrypt the ciphertext using the key matrix

    public String decrypt(String ciphertext) {
        ciphertext = ciphertext.toUpperCase().replaceAll("[^A-Z]", ""); // Convert to uppercase and remove non-alphabetic characters
        StringBuilder sb = new StringBuilder(ciphertext);

        StringBuilder plaintext = new StringBuilder();
        for (int i = 0; i < sb.length(); i += 2) {

            char c1 = sb.charAt(i);
            char c2 = sb.charAt(i + 1);

            int row1 = -1, col1 = -1, row2 = -1, col2 = -1;
            for (int row = 0; row < 5; row++) {
                for (int col = 0; col < 5; col++) {
                    if (keyMatrix[row][col] == c1) {
                        row1 = row;
                        col1 = col;
                    } else if (keyMatrix[row][col] == c2) {
                        row2 = row;
                        col2 = col;
                    }
                }
            }
            if (row1 == -1 || col1 == -1 || row2 == -1 || col2 == -1) {
                throw new RuntimeException("Invalid ciphertext characters: " + c1 + c2);
            }

            if (row1 == row2) {
                col1 = (col1 - 1 + 5) % 5;
                col2 = (col2 - 1 + 5) % 5;
            } else if (col1 == col2) {
                row1 = (row1 - 1 + 5) % 5;
                row2 = (row2 - 1 + 5) % 5;
            } else {
                int tmp = col1;
                col1 = col2;
                col2 = tmp;
            }

            plaintext.append(keyMatrix[row1][col1]);
            plaintext.append(keyMatrix[row2][col2]);
        }

        int m = 0;
        while (m < plaintext.length() - 2) {
            char c1 = plaintext.charAt(m);
            char c2 = plaintext.charAt(m + 1);
            char c3 = plaintext.charAt(m + 2);

            if (c1 == c3 && c2 == 'X') {
                plaintext.deleteCharAt(m + 1);
                m++;
                continue;
            }

            m++;
        }
        return plaintext.toString();
    }



    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the key: ");
        String key = scanner.nextLine();
        PlayfairDecryption playfair = new PlayfairDecryption();
        playfair.generateKeyMatrix(key);

       
    }
}