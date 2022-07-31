package br.com.library.Helpers.Callables;

@FunctionalInterface
public interface GroupableArrayCallable<ReturningType, ItemsType> {
	ReturningType run(ReturningType group, ItemsType item);
}
