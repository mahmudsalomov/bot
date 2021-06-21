package com.example.mit.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
    private String name;
    private Long id;
    private Long temp_id;
    private Long parent_id;
}
