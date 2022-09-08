package br.com.GerenciamentoDeUsuarios.API.util.Callables;

import java.util.List;

@FunctionalInterface
public interface ArrayCallable<U, T> {
  U run(T item, Integer index, List<T> array);
}
