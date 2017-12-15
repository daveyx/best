package com.example.elasticsearch.model;

import java.util.UUID;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public abstract class AbstractEntity {

    @Id
    private String id;
    
    protected UUID uuId;
}
