package br.com.library.Helpers.Callables;

import java.util.Map;

@FunctionalInterface 
public interface ObjectCallable<keyType, valueType, sendValueType> {
  sendValueType run(keyType key, valueType value, Map<keyType, valueType> hashMap);
}