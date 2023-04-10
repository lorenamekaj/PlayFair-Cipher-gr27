import java.util.Scanner;

public class PlayfairEncryption{
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

    // Encrypt the plaintext using the key matrix
    public String encrypt(String plaintext) {
        plaintext = plaintext.toUpperCase().replaceAll("[^A-Z]", ""); // Convert to uppercase and remove non-alphabetic characters
        plaintext = plaintext.replace("J", "I"); // Replace J with I
        StringBuilder sb = new StringBuilder(plaintext);
        System.out.println(sb);
        StringBuilder ciphertext = new StringBuilder();
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
                throw new RuntimeException("Invalid plaintext characters: " + c1 + c2);
            }

            if (row1 == row2) {
                col1 = (col1 + 1) % 5;
                col2 = (col2 + 1) % 5;
            } else if (col1 == col2) {
                row1 = (row1 + 1) % 5;
                row2 = (row2 + 1) % 5;
            } else {
                int tmp = col1;
                col1 = col2;
                col2 = tmp;
            }

            ciphertext.append(keyMatrix[row1][col1]);
            ciphertext.append(keyMatrix[row2][col2]);
        }

        return ciphertext.toString();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the key: ");
        String key = scanner.nextLine();
        PlayfairEncryption playfair = new PlayfairEncryption();
        playfair.generateKeyMatrix(key);

        System.out.print("Enter the plaintext: ");
        String plaintext = scanner.nextLine();
        System.out.print(playfair.encrypt(plaintext));
    }
}
