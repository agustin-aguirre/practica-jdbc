package org.example.services.filters;

import java.util.Collection;
import java.util.function.Predicate;

public interface Filterer<T> {
    Collection<T> filter(Predicate<T> condition);
}
