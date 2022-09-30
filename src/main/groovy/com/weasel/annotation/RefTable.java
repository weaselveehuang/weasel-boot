package com.weasel.annotation;

public @interface RefTable {
    Class entityClass();

    String refColumn();

    String selfColumn();

    String erroMsg();
}
