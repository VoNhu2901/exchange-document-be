package com.sgu.exchage.model;

public class GenericEquals<T> {
    private final T value;

    public GenericEquals(T value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GenericEquals)) {
            return false;
        }
        return value != null && value.equals(((GenericEquals<T>) o).value);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

