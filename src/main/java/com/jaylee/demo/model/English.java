package com.jaylee.demo.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class English {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    private String englishtext;
    private String korean;
    private String username;
    private String remark;
}

