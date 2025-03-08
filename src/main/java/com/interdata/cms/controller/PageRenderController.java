package com.interdata.cms.controller;

import com.interdata.cms.model.LandingPage;
import com.interdata.cms.service.LandingPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/page")
public class PageRenderController {
    @Autowired
    private LandingPageService landingPageService;

    @GetMapping("/{slug}")
    public String renderPage(@PathVariable String slug, Model model) {
        LandingPage page = landingPageService.getPageBySlug(slug);
        model.addAttribute("title", page.getName());
        model.addAttribute("htmlContent", page.getHtmlContent());
        model.addAttribute("cssContent", page.getCssContent());
        model.addAttribute("jsContent", page.getJsContent());
        return "landing-template"; // File Thymeleaf
    }
}