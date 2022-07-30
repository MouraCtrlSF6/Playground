package br.com.library.Helpers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@FunctionalInterface
interface ArrayCallable<U, T> {
  U run(T argument);
}

public interface ArrayHelper {
  static <T extends Object> List<T> reverse(T[] list) {
    List<T> reversed = new ArrayList<>();
    
    Arrays
      .asList(list)
      .forEach(item -> reversed.add(0, item));

    return reversed;
  }

  static <T extends Object> List<T> reverse(List<T> list) {
    List<T> reversed = new ArrayList<>();

    list.forEach(item -> reversed.add(0, item));

    return reversed;
  }

  static <T> String join(List<T> list) {
    String joinned = "";

    for(T item : list) {
      String stringValue = String.valueOf(item);
      joinned += stringValue;
    }

    return joinned;
  }

  static <T extends Object, U extends Object> List<U> map(List<T> list, ArrayCallable<U, T> runner) throws Exception {
    try {
      List<U> mappedList = new ArrayList<>();

      list.forEach(item -> {
        @SuppressWarnings("unchecked")
        U callbackReturn = runner.run(item);
        mappedList.add(callbackReturn);
      });
      
      return mappedList;

    } catch(Exception e) {
      throw e;
    }
  }

  static <T> List<T> filter(List<T> original, ArrayCallable<Boolean, T> callback) {
    List<T> filtered = new ArrayList<>();

    original.forEach(item -> {
      if(callback.run(item)) {
        filtered.add(item);
      }
    });

    return filtered;
  }

  static <T> T find(List<T> original, ArrayCallable<Boolean, T> callback) {
    for (T item : original) {
      if(callback.run(item)) {
        return item;
      }
    }

    return null;
  }
}
