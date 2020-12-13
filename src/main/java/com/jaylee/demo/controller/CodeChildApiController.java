package com.jaylee.demo.controller;

import com.jaylee.demo.model.CodeChild;
import com.jaylee.demo.model.User;
import com.jaylee.demo.repository.CodeChildRepository;
import com.jaylee.demo.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
class CodeChildApiController {

    private CodeChildRepository repository;

    CodeChildApiController(CodeChildRepository repository) {
        this.repository = repository;
    }

    // Aggregate root

    @GetMapping("/codeChilds")
    List<CodeChild> all() {
        return repository.findAll();
    }

    @PostMapping("/codeChilds")
    CodeChild newCodeChild(@RequestBody CodeChild newCodeChild) {
        return repository.save(newCodeChild);
    }

    // Single item

    @GetMapping("/codeChilds/{id}")
    CodeChild one(@PathVariable Long id) {

        return repository.findById(id).orElse(null);
    }

    @PutMapping("/codeChilds/{id}")
    CodeChild replaceCodeChild(@RequestBody CodeChild newCodeChild, @PathVariable Long id) {
        return repository.findById(id)
                .map(CodeChild -> {
                    return repository.save(CodeChild);
                })
                .orElseGet(() -> {
                    newCodeChild.setId(id);
                    return repository.save(newCodeChild);
                });
    }

    @DeleteMapping("/codeChilds/{id}")
    void deleteCodeChild(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
