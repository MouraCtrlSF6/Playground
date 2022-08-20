package br.com.ProjetoPessoal.API.util.Callables;

import java.util.List;

@FunctionalInterface
public interface ArrayCallable<U, T> {
  U run(T item, Integer index, List<T> array);
}
