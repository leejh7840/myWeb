package com.jaylee.demo.validator;

import com.jaylee.demo.model.Board;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.thymeleaf.util.StringUtils;
@Component
public class BoardValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return Board.class.equals(aClass);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Board board = (Board) obj;
        if(StringUtils.isEmpty(board.getContent())){
            errors.rejectValue("content", "key", "Input content.");
        }
    }
}
