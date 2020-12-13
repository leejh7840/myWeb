package com.jaylee.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity
@Data
public class Board {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Size(min=2, max=40, message = "Title must be between 2 and 40.")
    private String title;
    private String content;
    @Column(name = "username", updatable = false)
    private String username;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", insertable = false, updatable = false)
    @JsonIgnore
    private CodeChild category;
    private Long category_id;
    /*
    @JoinColumns({
            @JoinColumn(name = "cat_code_name", referencedColumnName = "code_name"),
            @JoinColumn(name = "cat_code_child_name", referencedColumnName = "code_child_name")})

     */
}
