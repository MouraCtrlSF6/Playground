package Helpers;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

@FunctionalInterface 
interface Imp<keyType, valueType, sendValueType> {
  sendValueType run(keyType key, valueType value, Map<keyType, valueType> hashMap);
}

public class ObjectHelper {
  public final static <incKey, incValue, sendValue> Map<incKey, sendValue> 
  map(Map<incKey, incValue> hashMap, Imp<incKey, incValue, sendValue> callback)
  throws Exception {
    try {
      Map<incKey, sendValue> newHash = new HashMap<>();

      hashMap.forEach((key, value) -> {
        newHash.put(key, callback.run(key, value, hashMap));
      });

      return newHash;
    } catch(Exception e) {
      throw e;
    }
  }

  public final static <keyType, valueType> Map<keyType, valueType> 
  filter(Map<keyType, valueType> hashMap, Imp<keyType, valueType, Boolean> callback) {
    Map<keyType, valueType> filteredHash = new HashMap<>();

    for(Map.Entry<keyType, valueType> entry : hashMap.entrySet()) {
      if(callback.run(entry.getKey(), entry.getValue(), hashMap)) {
        filteredHash.put(entry.getKey(), entry.getValue());
      }
    }

    return filteredHash;
  }

  public final static <keyType, valueType> Map.Entry<keyType, valueType> 
  find(Map<keyType, valueType> hashMap, Imp<keyType, valueType, Boolean> callback) {
    for(Map.Entry<keyType, valueType> entry : hashMap.entrySet()) {
      if(callback.run(entry.getKey(), entry.getValue(), hashMap)) {
        return entry;
      }
    }

    return null;
  }
}