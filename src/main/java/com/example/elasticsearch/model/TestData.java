package com.example.elasticsearch.model;

import org.springframework.data.elasticsearch.annotations.Document;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
@Document(indexName = "test", type = "testdata")
public class TestData extends AbstractEntity {

	public static final String TEST_UUID = "1234-1234-1234-1234-1234";
     
    private String value;
     
//    @Field(type = FieldType.Nested)
//    private List<String> authors;
}