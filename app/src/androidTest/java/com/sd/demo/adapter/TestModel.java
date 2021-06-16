package com.sd.demo.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TestModel {
    public String name;

    public TestModel(String name) {
        this.name = name;
    }

    public static List<TestModel> get(int count) {
        final List<TestModel> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            list.add(new TestModel(String.valueOf(i)));
        }
        return list;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final TestModel testModel = (TestModel) o;
        return Objects.equals(name, testModel.name);
    }

    @Override
    public String toString() {
        return name;
    }
}
