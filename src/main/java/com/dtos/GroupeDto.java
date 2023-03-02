package com.dtos;

import lombok.Data;

import java.util.List;

@Data
public class GroupeDto {

    private Long id;
    private String nom;
    private Boolean seul;
    private List<Long> artistes;

}
