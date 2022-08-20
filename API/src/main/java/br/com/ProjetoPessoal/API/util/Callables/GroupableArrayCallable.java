package br.com.ProjetoPessoal.API.util.Callables;

@FunctionalInterface
public interface GroupableArrayCallable<ReturningType, ItemsType> {
	ReturningType run(ReturningType group, ItemsType item);
}
