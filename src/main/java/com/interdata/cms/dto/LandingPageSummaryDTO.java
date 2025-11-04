package com.interdata.cms.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Locale;

@Data
public class LandingPageSummaryDTO {
    private Long id;
    private String name;
    private String slug;
    private String template;
    private boolean isPublished;
    private String url;
    private LocalDateTime createdAt;
    public LandingPageSummaryDTO(Long id, String name, String slug, String template, boolean isPublished, String url, LocalDateTime createAt) {
        this.id = id;
        this.name = name;
        this.slug = slug;
        this.template = template;
        this.isPublished = isPublished;
        this.url = url;
        this.createdAt = createAt;
    }

    // Getters and Setters
}
