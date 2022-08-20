package br.com.ProjetoPessoal.API.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.ProjetoPessoal.API.util.Callables.ArrayCallable;
import br.com.ProjetoPessoal.API.util.Callables.GroupableArrayCallable;

public interface ArrayUtils {
  static <T extends Object> List<T> reverse(List<T> list) {
    List<T> reversed = new ArrayList<>();

    list.forEach(item -> reversed.add(0, item));

    return reversed;
  }
  
  static <T extends Object> List<T> reverse(T[] list) {
	  return reverse(Arrays.asList(list));
  }
    
  static <T> String join(List<T> list) {
    String joinned = "";

    for(T item : list) {
      String stringValue = String.valueOf(item);
      joinned += stringValue;
    }

    return joinned;
  }
  
  static <T> String join(T[] list) {
	  return join(Arrays.asList(list));
  }

  static <T, U> List<U> map(List<T> list, ArrayCallable<U, T> callback) throws Exception {
    try {
      List<U> mappedList = new ArrayList<>();
      
      for(int index = 0; index < list.size(); index++) {
    	  mappedList.add(callback.run(list.get(index), index, list));
      }
      
      return mappedList;
      
    } catch(Exception e) {
      throw e;
    }
  }

  static <T> List<T> filter(List<T> original, ArrayCallable<Boolean, T> callback) {
    List<T> filtered = new ArrayList<>();
    
    for(int index = 0; index < original.size(); index++) {
    	T item = original.get(index);
    	
    	if(callback.run(item, index, original)) {
    		filtered.add(item);
    	}
    }

    return filtered;
  }

  static <T> T find(List<T> original, ArrayCallable<Boolean, T> callback) {
    for (int index = 0; index < original.size(); index++) {
    	T item = original.get(index);
    	
    	if(callback.run(item, index, original)) {
    		return item;
    	}
    }

    return null;
  }
  
  static <ReturningType, ItemsType> ReturningType 
  reduce(List<ItemsType> array, GroupableArrayCallable<ReturningType, ItemsType> callback, ReturningType beggingValue) {
	  ReturningType group = beggingValue;
	  
	  for(ItemsType item : array) {
		  group = callback.run(group, item);
	  }
	  
	  return group;
  }
}
