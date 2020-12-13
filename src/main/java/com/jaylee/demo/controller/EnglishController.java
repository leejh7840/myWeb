package com.jaylee.demo.controller;

import com.jaylee.demo.model.English;
import com.jaylee.demo.repository.EnglishRepository;
import com.jaylee.demo.service.ApiExamTranslateNmt;
import com.jaylee.demo.service.CommonService;
import com.jaylee.demo.validator.EnglishValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
@RequestMapping("/english")
public class EnglishController {
    @Autowired
    private EnglishRepository englishRepository;
    @Autowired
    private EnglishValidator englishValidator;
    @Autowired
    private CommonService commonService;
    @Autowired
    private ApiExamTranslateNmt apiExamTranslateNmt;

    @GetMapping("/list")
    public String list(Model model, @PageableDefault(size = 5) Pageable pageable,
                       @RequestParam(required = false,defaultValue = "") String searchText){
        //Page<English> englishs = englishRepository.findAll(pageable);
        Page<English> englishs = englishRepository.findByEnglishtextContainingOrderByIdDesc(searchText, pageable);
        int stratPage = Math.max(1, englishs.getPageable().getPageNumber() - 4);
        int endPage = Math.min(englishs.getTotalPages(), englishs.getPageable().getPageNumber() + 4);
        model.addAttribute("startPage",stratPage);
        model.addAttribute("endPage",endPage);
        model.addAttribute("englishs",englishs);
        return "english/list";
    }

    @GetMapping("/form")
    public String form(Model model, @RequestParam(required = false) Long id, Authentication authentication){
        System.out.println("=====english form==================");
        if(id == null){
            English eng = new English();
            //eng.setId(9999L);
            model.addAttribute("english", eng);
        } else {
            English english = englishRepository.findById(id).orElse(null);
            model.addAttribute("english", english);
        }
        return "english/form";
    }

    @PostMapping("/form")
    public String englishSubmit(@Valid English english, BindingResult bindingResult, Authentication authentication) {
        System.out.println("=====englishSubmit==================");
        englishValidator.validate(english, bindingResult);
        if (bindingResult.hasErrors()) {
           return "english/form";
        }
        english.setUsername(authentication.getName());
        if(english.getKorean().equals("")){
            english.setKorean(apiExamTranslateNmt.translateKor(english.getEnglishtext()));
        }
        englishRepository.save(english);
        return "redirect:/english/list";
    }
    @GetMapping("/delete")
    public String form(English english){
        englishRepository.delete(english);
        return "redirect:/english/list";
    }
}
