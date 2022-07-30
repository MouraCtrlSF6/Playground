package br.com.library.Helpers;

import java.util.List;
import java.util.Arrays;

public interface StringHelper {
  static String reverse(String text) {
    List<String> toList = Arrays.asList(text.split(""));
    
    toList = ArrayHelper.reverse(toList);

    return ArrayHelper.join(toList);
  }
}
