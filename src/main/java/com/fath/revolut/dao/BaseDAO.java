package com.fath.revolut.dao;

import java.util.Collection;

public interface BaseDAO<T> {

	T getBy(String id);

	T persist(T body);

	Collection<T> getAll();
}
