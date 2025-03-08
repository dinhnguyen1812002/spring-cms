package com.interdata.cms.service;

import com.interdata.cms.exception.NotFoundException;
import com.interdata.cms.model.LandingPage;
import com.interdata.cms.repository.LandingPageRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class LandingPageService {
    @Autowired
    private LandingPageRepository landingPageRepository;

    public List<LandingPage> getAllPages() {
        return landingPageRepository.findAll();
    }

    public LandingPage getPageBySlug(String slug) {
        return landingPageRepository.findBySlug(slug)
                .orElseThrow(() -> new NotFoundException("Page not found"));
    }
    @Transactional
    public LandingPage createPage(LandingPage pageData) {
        LandingPage newPage = new LandingPage();
        newPage.setName(pageData.getName());
        newPage.setSlug(pageData.getSlug());
        newPage.setTemplate(pageData.getTemplate());
        newPage.setHtmlContent(pageData.getHtmlContent());
        newPage.setCssContent(pageData.getCssContent());
        newPage.setJsContent(pageData.getJsContent());
        newPage.setPublished(pageData.isPublished());

        return landingPageRepository.save(newPage);
    }


    @Transactional
    public LandingPage updatePage(Long id, LandingPage updatedPage) {
        LandingPage existingPage = landingPageRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Page not found"));

        existingPage.setHtmlContent(updatedPage.getHtmlContent());
        existingPage.setCssContent(updatedPage.getCssContent());
        existingPage.setJsContent(updatedPage.getJsContent());

        return landingPageRepository.save(existingPage);
    }

    public void deletePage(Long id) {
        landingPageRepository.deleteById(id);
    }

}
