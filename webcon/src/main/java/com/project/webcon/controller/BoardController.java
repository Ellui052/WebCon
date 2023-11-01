package com.project.webcon.controller;

import com.project.webcon.entity.Board;
import com.project.webcon.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class BoardController {
    @Autowired
    private BoardService boardService;

    @GetMapping("/webcon/noticeboard")
    public String list(Model model) {
        List<Board> list = null;

        list = boardService.list();
        model.addAttribute("list", list);

        return "noticeBoard";
    }


    @GetMapping("/webcon/noticeboard/detail/{id}")
    public String detail(Model model, @PathVariable("id") int id) {
        model.addAttribute("board", boardService.view(id));
        return "noticeDetail";
    }

    @GetMapping("/webcon/noticeboard/write")
    public String write() {
        return "write";
    }

    @PostMapping("webcon/noticeboard/writepro")
    public String writepro(Board board) {
        boardService.write(board);

        return "redirect:/webcon/noticeboard";
    }
}
