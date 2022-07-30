package Helpers;

import java.util.List;
import java.util.Arrays;

public class StringHelper {
  public final static String reverse(String text) {
    List<String> toList = Arrays.asList(text.split(""));
    
    toList = ArrayHelper.reverse(toList);

    return ArrayHelper.join(toList);
  }
}
