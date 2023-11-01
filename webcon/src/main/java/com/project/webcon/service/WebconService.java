package com.project.webcon.service;

import com.project.webcon.entity.Webcon;
import com.project.webcon.repository.WebconRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WebconService {

    @Autowired
    private WebconRepository webconRepository;

    public List<Webcon> webconList() {
       return webconRepository.findAllByOrderByTitleAsc();
    }

    public Webcon webconView(String num) {
        return webconRepository.findById(num).get();
    }


    public List<Webcon> search(String search) {

        return webconRepository.findByTitleContaining(search);
    }
}
