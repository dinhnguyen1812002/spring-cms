package com.interdata.cms.controller;

import com.interdata.cms.model.LandingPage;
import com.interdata.cms.service.LandingPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/landing-pages")

public class LandingPageController {
    @Autowired
    private LandingPageService landingPageService;

    @GetMapping
    public List<LandingPage> getAllPages() {
        return landingPageService.getAllPages();
    }

    @GetMapping("/{slug}")
    public ResponseEntity<LandingPage> getPageBySlug(@PathVariable String slug) {
        return ResponseEntity.ok(landingPageService.getPageBySlug(slug));
    }

    @PostMapping("/create")
    public ResponseEntity<LandingPage> createPage(@RequestBody LandingPage page) {
        return ResponseEntity.ok(landingPageService.createPage(page));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LandingPage> updatePage(@PathVariable Long id, @RequestBody LandingPage page) {
        return ResponseEntity.ok(landingPageService.updatePage(id, page));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePage(@PathVariable Long id) {
        landingPageService.deletePage(id);
        return ResponseEntity.ok("Page with ID " + id + " has been deleted.");
    }
}

