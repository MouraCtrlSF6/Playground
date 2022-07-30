package Helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@FunctionalInterface
interface Imp {
  Object run(Object argument);
}

public class ArrayHelper {
  public final static <T extends Object> List<T> reverse(T[] list) {
    List<T> reversed = new ArrayList<>();
    
    Arrays
      .asList(list)
      .forEach(item -> reversed.add(0, item));

    return reversed;
  }

  public final static <T extends Object> List<T> reverse(List<T> list) {
    List<T> reversed = new ArrayList<>();

    list.forEach(item -> reversed.add(0, item));

    return reversed;

  public final static String reverse(String text) {
    List<String> toList = Arrays.asList(text.split(""));
    
    toList = reverse(toList);

    return join(toList);
  }

  public final static <T> String join(List<T> list) {
    String joinned = "";

    for(T item : list) {
      String stringValue = String.valueOf(item);
      joinned += stringValue;
    }

    return joinned;
  }

  public final static <T extends Object, U extends Object> List<U> map(List<T> list, Imp runner) throws Exception {
    try {
      List<U> mappedList = new ArrayList<>();

      list.forEach(item -> {
        @SuppressWarnings("unchecked")
        U callbackReturn = (U) runner.run(item);
        mappedList.add(callbackReturn);
      });
      
      return mappedList;

    } catch(Exception e) {
      throw e;
    }
  }
}
