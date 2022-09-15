package br.com.library.Console;

import java.lang.reflect.Method;
import java.util.Scanner;
import java.util.List;
import java.util.Arrays;
import java.util.stream.Stream;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
public class Console {
  public static void WriteLine(String message) {
    System.out.println(message);
  }

  public static void WriteLine() {
    System.out.println("");
  }

  public static void Write(String message) {
    System.out.print(message);
  }

  public static void Write() {
    System.out.print("");
  }

  public static String ReadLine() 
  throws Exception {
    Scanner scanner = new Scanner(System.in);

    String message = scanner.nextLine();

    scanner.close();

    return message;
  }

  public static String ReadLine(String message) 
  throws Exception {
    Console.WriteLine(message);
    return Console.ReadLine();
  }

  public static <T extends Class, R> R ReadLine(String message, T type)
  throws Exception{
    Console.WriteLine(message);
    return Console.ReadLine(type);
  }

  public static <T extends Class, R> R ReadLine(T type)
  throws Exception {
    Scanner scanner = new Scanner(System.in);

    String payload = scanner.nextLine();

    scanner.close();
    
    Method methodCast = type.getMethod("valueOf", String.class);

    return payload.trim().split(" ").length > 1
     ? (R) formatValues(payload, methodCast)
     : (R) methodCast.invoke(null, payload);
  }

  private static <T> List<T> formatValues(String values, Method method)
  throws Exception {
    return Arrays.asList(values.trim().split(" "))
      .stream()
      .filter((value) -> value != " ")
      .map(value -> {
        try {
          return (T) method.invoke(null, value);
        } catch(Exception e) {
          return null;
        }
      })
      .collect(Collectors.toList());
  } 
}