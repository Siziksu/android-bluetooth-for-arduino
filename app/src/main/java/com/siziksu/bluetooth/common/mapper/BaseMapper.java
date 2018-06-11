package com.siziksu.bluetooth.common.mapper;

import java.util.List;

/**
 * @param <O> object
 * @param <M> mapped object
 */
public interface BaseMapper<O, M> {

    List<M> map(List<O> objectList);

    List<O> unMap(List<M> mappedList);

    M map(O object);

    O unMap(M mapped);
}

