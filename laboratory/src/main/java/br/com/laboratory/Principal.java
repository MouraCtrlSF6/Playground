package br.com.laboratory;

import java.util.Optional;

public class Principal {
  public static void main(String[] args) {
    System.out.println(test(Optional.of(1)));
  }

  public static Boolean test(Optional<Integer> opt) {
    return opt.isPresent();
  }
}