package com.project.webcon.controller;

import com.project.webcon.entity.Webcon;
import com.project.webcon.service.WebconService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class WebconController {

    @Autowired
    private WebconService webconService;

    @GetMapping("/webcon/main")
    public String main(Model model) {

        model.addAttribute("list", webconService.webconList());

        return "main";
    }

    @GetMapping("/webcon/info")
    public String info(Model model, String num) {

        model.addAttribute("webtoon", webconService.webconView(num));
        return "webtoon_info";
    }

    @GetMapping("/webcon/search")
    public String search(Model model, String search) {
        List<Webcon> list = null;

        if(search != null) {
            list = webconService.search(search);
            model.addAttribute("list",list);
        }
        return "search";
    }

}
