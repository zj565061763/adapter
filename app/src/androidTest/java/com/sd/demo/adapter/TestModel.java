package com.sd.demo.adapter;

import java.util.ArrayList;
import java.util.List;

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
    public String toString() {
        return name;
    }
}
