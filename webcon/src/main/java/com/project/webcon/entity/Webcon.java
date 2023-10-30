package com.project.webcon.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "webtoon")
public class Webcon {
    @Id
    private String num;

    private String platform;

    private String title;

    private String img;

    private String info;

    private String authors;

    private String day;

    private String genre;
}
