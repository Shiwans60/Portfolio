package com.Shiwans.portfolio.Controller;

import com.Shiwans.portfolio.entity.*;
import com.Shiwans.portfolio.service.interfaces.*;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("")
public class BaseController {

    private static final Logger log = LoggerFactory.getLogger(BaseController.class);
    private final ExperienceService experienceService;
    private final SkillService skillService;
    private final EducationService educationService;
    private final MessageService messageService;
    private final ProjectService projectService;
    private final ResearchService researchService;
    private final AchievementService achievementService;
    private final ResponsibilityService responsibilityService;

    @Autowired
    public BaseController(ExperienceService experienceService, SkillService skillService,
                          EducationService educationService, MessageService messageService,
                          ProjectService projectService, ResearchService researchService,
                          AchievementService achievementService, ResponsibilityService responsibilityService) {
        this.experienceService = experienceService;
        this.skillService = skillService;
        this.educationService = educationService;
        this.messageService = messageService;
        this.projectService = projectService;
        this.researchService = researchService;
        this.achievementService = achievementService;
        this.responsibilityService = responsibilityService;
    }

    private void getEducationAttributes(Model theModel) {

        List<Education> educations = new ArrayList<>(
                educationService.getAllEducationDetails()
        );

        // Display latest education first
        educations.sort(
                Comparator.comparing(
                        Education::getStartDate,
                        Comparator.nullsLast(Comparator.reverseOrder())
                )
        );

        theModel.addAttribute("education", educations);
    }

    private void getExperienceAttributes(Model theModel){
        List<Experience> experiences = experienceService.getPortfolioWorkExperiences();
        theModel.addAttribute("workEx", experiences);
    }

    private void getSkillAttributes(Model model) {
        List<Skill> languages = skillService.getSkillsByCategory("Languages");
        List<Skill> frameworks = skillService.getSkillsByCategory("Frameworks");
        List<Skill> cloudDatabases = skillService.getSkillsByCategory("Cloud & Databases");
        List<Skill> tools = skillService.getSkillsByCategory("Tools");

        model.addAttribute("languages", languages);
        model.addAttribute("frameworks", frameworks);
        model.addAttribute("cloudDatabases", cloudDatabases);
        model.addAttribute("tools", tools);
    }

    private void getProjectAttributes(Model theModel){
        List<Project> projects = projectService.getAllProjects();
        theModel.addAttribute("projects", projects);
    }

    private void getResearchAttributes(Model theModel){
        List<Research> researchEntries = researchService.getAllResearch();
        theModel.addAttribute("researchEntries", researchEntries);
    }

    private void getAchievementAttributes(Model theModel) {
        List<Achievement> achievements = achievementService.getAllAchievements();
        theModel.addAttribute("achievements", achievements);
    }

    private void getResponsibilityAttributes(Model theModel) {
        List<Responsibility> responsibilities = responsibilityService.getAllResponsibilities();
        theModel.addAttribute("responsibilities", responsibilities);
    }

    private void getMessageAttributes(Model theModel){
        Message newMessage = new Message();
        theModel.addAttribute("newMessage", newMessage);
    }

    private void populateCommonAttributes(Model theModel) {
        getEducationAttributes(theModel);
        getExperienceAttributes(theModel);
        getSkillAttributes(theModel);
        getProjectAttributes(theModel);
        getResearchAttributes(theModel);
        getAchievementAttributes(theModel);
        getResponsibilityAttributes(theModel);
    }

    @GetMapping({"","/" ,"/portfolio"})
    public String homeView(Model theModel, @RequestParam(value = "rateLimitError", required = false) String rateLimitError){
        populateCommonAttributes(theModel);
        getMessageAttributes(theModel);

        if (rateLimitError != null) {
            log.info("Rate limit error: {}", rateLimitError);
            theModel.addAttribute("rateLimitError", rateLimitError);
        }

        return "portfolio";
    }

    @GetMapping("/login")
    public String showLoginForm(){
        return "login";
    }

    @PostMapping("/save-message")
    public String saveMessage(@Valid @ModelAttribute("newMessage") Message theMessage,
                              BindingResult bindingResult, Model theModel){
        if(bindingResult.hasErrors()) {
            populateCommonAttributes(theModel);
            theModel.addAttribute("newMessage", theMessage);
            return "portfolio";
        }

        messageService.save(theMessage);
        return "redirect:/";
    }

    @GetMapping("/rate-limit-error")
    public String errorPage(Model theModel, @RequestParam(value = "message", required = false) String message,
                            @RequestParam(value = "homepageError", required = false) String homepageError) {
        if (message != null) {
            theModel.addAttribute("errorMessage", message);
        }
        if (homepageError != null) {
            theModel.addAttribute("homepageError", homepageError);
        }

        return "rate-limit-error";
    }
}
