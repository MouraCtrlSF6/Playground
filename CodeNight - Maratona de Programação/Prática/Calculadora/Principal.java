import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

public class Principal {
  public static void main(String[] args) {
    try {
      final Map<String, Callable<Double>> opcoes = new HashMap<>(){{
        put("somar", () -> Calculadora.soma());
        put("subtrair", () -> Calculadora.subtrai());
        put("dividir", () -> Calculadora.divide());
        put("multiplicar", () -> Calculadora.multiplica());
      }};

      exibeOpcoes(opcoes);
  
      final String opcao = Teclado.ler("\nOla, qual operacao deseja fazer? ");

      final double resultado = opcoes
        .get(opcao.toLowerCase())
        .call();
      
      exibeResultado(resultado);

      return;
    } catch(Exception e) {
      System.out.println("Error: " + e.getMessage());
    }
  }

  public static <K, V> void exibeOpcoes(Map<K, V> opcoes) {
    System.out.println("Opcoes disponiveis:");
    opcoes.forEach((key, value) -> {
      System.out.println("* " + key);
    });

    return;
  }

  public static void exibeResultado(double resultado) {
    System.out.println("\nResultado: " + resultado);
  }

}