package com.siziksu.bluetooth.domain;

public interface BaseDomainContract<D> {

    void register(D domain);

    void unregister();
}
