package com.siziksu.bluetooth.common.mapper;

import java.util.ArrayList;
import java.util.List;

/**
 * @param <O> object
 * @param <M> mapped object
 */
public abstract class Mapper<O, M> implements BaseMapper<O, M> {

    @Override
    public List<M> map(List<O> objectList) {
        List<M> mappedList = new ArrayList<>();
        for (O object : objectList) {
            mappedList.add(map(object));
        }
        return mappedList;
    }

    @Override
    public List<O> unMap(List<M> mapped) {
        List<O> objectList = new ArrayList<>();
        for (M object : mapped) {
            objectList.add(unMap(object));
        }
        return objectList;
    }
}
