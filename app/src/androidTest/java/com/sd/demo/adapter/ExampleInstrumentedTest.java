package com.sd.demo.adapter;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.sd.lib.adapter.data.DataHolder;
import com.sd.lib.adapter.data.ListDataHolder;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void testDataHolder() {
        final DataHolder<TestModel> dataHolder = new ListDataHolder<>();

        final List<TestModel> listData = TestModel.get(2);
        dataHolder.setData(listData);

        // test setData
        Assert.assertEquals(2, dataHolder.size());
        Assert.assertEquals("0", dataHolder.get(0).name);
        Assert.assertEquals("1", dataHolder.get(1).name);

        // test removeData
        Assert.assertEquals("1", dataHolder.removeData(1).name);
        Assert.assertEquals(true, dataHolder.removeData(new TestModel("0")));
        Assert.assertEquals(0, dataHolder.size());

        // test addData
        dataHolder.addData(new TestModel("a"));
        dataHolder.addData(new TestModel("b"));
        dataHolder.addData(1, new TestModel("c"));
        Assert.assertEquals(3, dataHolder.size());
        Assert.assertEquals("a", dataHolder.get(0).name);
        Assert.assertEquals("c", dataHolder.get(1).name);
        Assert.assertEquals("b", dataHolder.get(2).name);

        dataHolder.setData(null);
        Assert.assertEquals(0, dataHolder.size());

        // test addData
        dataHolder.setData(TestModel.get(1));
        dataHolder.addData(TestModel.get(2));
        dataHolder.addData(1, TestModel.get(3));
        Assert.assertEquals(6, dataHolder.size());
        Assert.assertEquals("0", dataHolder.get(0).name);
        Assert.assertEquals("0", dataHolder.get(1).name);
        Assert.assertEquals("1", dataHolder.get(2).name);
        Assert.assertEquals("2", dataHolder.get(3).name);
        Assert.assertEquals("0", dataHolder.get(4).name);
        Assert.assertEquals("1", dataHolder.get(5).name);

        // test updateData
        dataHolder.updateData(1, new TestModel("update"));
        Assert.assertEquals("update", dataHolder.get(1).name);
    }

    @Test
    public void testDataHolderIndexOutOfBounds() {
        final DataHolder<TestModel> dataHolder = new ListDataHolder<>();

        dataHolder.addData(new TestModel("a"));
        dataHolder.addData(100, new TestModel("b"));
        Assert.assertEquals(2, dataHolder.size());
        Assert.assertEquals("a", dataHolder.get(0).name);
        Assert.assertEquals("b", dataHolder.get(1).name);

        dataHolder.addData(1000, TestModel.get(3));
        Assert.assertEquals(5, dataHolder.size());
        Assert.assertEquals("0", dataHolder.get(2).name);
        Assert.assertEquals("1", dataHolder.get(3).name);
        Assert.assertEquals("2", dataHolder.get(4).name);
    }
}
