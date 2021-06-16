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

        Assert.assertEquals(2, dataHolder.size());
        Assert.assertEquals("0", dataHolder.get(0).name);
        Assert.assertEquals("1", dataHolder.get(1).name);
    }
}
