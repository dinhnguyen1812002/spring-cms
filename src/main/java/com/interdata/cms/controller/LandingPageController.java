package com.interdata.cms.controller;

import com.interdata.cms.dto.LandingPageSummaryDTO;
import com.interdata.cms.model.LandingPage;
import com.interdata.cms.service.LandingPageService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/landing-pages")

public class LandingPageController {
    @Autowired
    private LandingPageService landingPageService;

    @GetMapping
    public List<LandingPageSummaryDTO> getAllPages() {
        return landingPageService.getAllPages();
    }

    @GetMapping("/{slug}")
    public ResponseEntity<LandingPage> getPageBySlug(@PathVariable String slug) {
        try {
            return ResponseEntity.ok(landingPageService.getPageBySlug(slug));
        }catch(Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
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

    @GetMapping("/url")
    public ResponseEntity<String> getUrl (){
        String url = landingPageService.getURl();
        return ResponseEntity.ok().body(url);
    }

    @GetMapping("/somePath")
    public String getBaseUrl(HttpServletRequest request) {
        String baseUrl = ServletUriComponentsBuilder.fromRequestUri(request)
                .replacePath(null)
                .build()
                .toUriString();
        return baseUrl; // returns scheme + host + port, without path
    }

    @GetMapping("/latest")
    public List<LandingPageSummaryDTO> getLatestPages() {
        return landingPageService.getLatestPages();
    }

}

