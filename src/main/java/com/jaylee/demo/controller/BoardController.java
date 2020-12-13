package com.jaylee.demo.controller;

import com.jaylee.demo.model.Board;
import com.jaylee.demo.model.CodeChild;
import com.jaylee.demo.model.User;
import com.jaylee.demo.repository.BoardRepository;
import com.jaylee.demo.repository.CodeChildRepository;
import com.jaylee.demo.service.BoardService;
import com.jaylee.demo.service.CommonService;
import com.jaylee.demo.validator.BoardValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/board")
public class BoardController {
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private BoardService boardService;
    @Autowired
    private BoardValidator boardValidator;
    @Autowired
    private CommonService commonService;
    @Autowired
    private CodeChildRepository codeChildRepository;
    @GetMapping("/list")
    public String list(Model model, @PageableDefault(size = 5) Pageable pageable,
                       @RequestParam(required = false,defaultValue = "") String searchText){
        //Page<Board> boards = boardRepository.findAll(pageable);
        Page<Board> boards = boardRepository.findByTitleContainingOrContentContainingOrderByIdDesc(searchText, searchText, pageable);
        int stratPage = Math.max(1, boards.getPageable().getPageNumber() - 4);
        int endPage = Math.min(boards.getTotalPages(), boards.getPageable().getPageNumber() + 4);
        model.addAttribute("startPage",stratPage);
        model.addAttribute("endPage",endPage);
        model.addAttribute("boards",boards);
        return "board/list";
    }

    @GetMapping("/form")
    public String form(Model model, @RequestParam(required = false) Long id){
        System.out.println("------------form-------------------");
        if(id == null){
            model.addAttribute("board", new Board());
            model.addAttribute("cat_selected", "");
            model.addAttribute("edit", true);
        } else {
            Board board = boardRepository.findById(id).orElse(null);
            model.addAttribute("board", board);
            if(commonService.getLoginUserName().equals(board.getUsername())){
                model.addAttribute("edit", true);
            } else {
                model.addAttribute("edit", false);
            }
            model.addAttribute("cat_selected",board.getCategory().getId());
        }
        //category code
        List<CodeChild> categories = codeChildRepository.findByCodenameOrderBySeqAsc("CAT");
        model.addAttribute("categories", categories);
        return "board/form";
    }

    @PostMapping("/form")
    public String boardSubmit(@Valid Board board, BindingResult bindingResult, Authentication authentication) {
        System.out.println("=====boardSubmit==================");
        boardValidator.validate(board, bindingResult);
        if (bindingResult.hasErrors()) {
            return "board/form";
        }
        System.out.println("getCategory_id ------------------>" + board.getCategory_id());
        //board.setUsername(commonService.getLoginUserName());
        board.setUsername(authentication.getName());
        boardService.save(board); //6, 22:00
        return "redirect:/board/list";
    }
    @GetMapping("/delete")
    public String form(Board board){
        boardRepository.delete(board);
        return "redirect:/board/list";
    }

    @GetMapping("/docu")
    public String docu(Model model, @RequestParam(required = true) Long category_id){
        //Page<Board> boards = boardRepository.findAll(pageable);
        List<Board> boards = boardRepository.findByCategory_id(category_id);
        /*
        for(Board b: boards){
            System.out.println(b.getId() + ":" + b.getTitle());
        }
        */
        model.addAttribute("boards",boards);
        return "board/docu";
    }
}
