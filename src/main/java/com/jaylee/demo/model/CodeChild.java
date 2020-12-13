package com.jaylee.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "code_child")
@Data
public class CodeChild {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;
    @Column(name = "code_name")
    private String codename;
    @Column(name = "code_child_name")
    private String codechildname;
    @Column(name = "code_child_desc")
    private String codechilddesc;
    private int seq;
    @OneToMany(mappedBy = "category") //Board class's property name
    private List<Board> boards = new ArrayList<>();
}
