package com.jaylee.demo.validator;

import com.jaylee.demo.model.English;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.thymeleaf.util.StringUtils;

@Component
public class EnglishValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return English.class.equals(aClass);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        English english = (English) obj;
        if(StringUtils.isEmpty(english.getEnglishtext())){
            errors.rejectValue("english", "key", "Input english.");
        }
    }
}
