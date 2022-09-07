import java.util.Scanner;

public interface Teclado {
  static final Scanner scanner = new Scanner(System.in);  

  static String ler(String message) {
    try {
      System.out.println(message);
      return scanner.nextLine();
    } catch(Exception e) {
      throw e;
    }
  }

  static String ler() {
    try {
      return scanner.nextLine();
    } catch(Exception e) {
      throw e;
    }
  }
}
