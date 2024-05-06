package org.challenge.candidate.initializers;

import java.util.Map;

public interface Initializer<T> {
    Map<String, T> initialize();
}