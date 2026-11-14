package com.Shiwans.portfolio;

import com.Shiwans.portfolio.entity.*;
import com.Shiwans.portfolio.service.interfaces.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PortfolioApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EducationService educationService;

    @Autowired
    private ExperienceService experienceService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private SkillService skillService;

    @Autowired
    private AchievementService achievementService;

    @Autowired
    private ResponsibilityService responsibilityService;

    @Test
    void testHomepageRendering() throws Exception {
        MvcResult result = mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertTrue(content.contains("save-message"), "Page should contain contact form");
        assertTrue(content.contains("footer"), "Page should render all the way through footer");
        assertTrue(content.contains("Shiwans Pal"), "Page should contain owner name");
    }

    @Test
    void contextLoads() {
        assertNotNull(educationService);
        assertNotNull(experienceService);
        assertNotNull(projectService);
        assertNotNull(skillService);
        assertNotNull(achievementService);
        assertNotNull(responsibilityService);
    }

    @Test
    void testEducationLoaded() {
        List<Education> educationList = educationService.getAllEducationDetails();
        assertFalse(educationList.isEmpty(), "Education list should not be empty");
        assertTrue(educationList.stream().anyMatch(e -> e.getUniversityName().contains("Army Institute of Technology")));
    }

    @Test
    void testExperienceLoaded() {
        List<Experience> workEx = experienceService.getPortfolioWorkExperiences();
        assertFalse(workEx.isEmpty(), "Experience list should not be empty");
        assertTrue(workEx.stream().anyMatch(e -> e.getCompanyName().contains("Frugality Fintech")));
    }

    @Test
    void testProjectsLoaded() {
        List<Project> projects = projectService.getAllProjects();
        assertEquals(2, projects.size());
        assertTrue(projects.stream().anyMatch(p -> p.getName().equals("AI-Fitness-Tracker")));
        assertTrue(projects.stream().anyMatch(p -> p.getName().equals("Scoreit")));
    }

    @Test
    void testSkillsCategoriesLoaded() {
        List<Skill> languages = skillService.getSkillsByCategory("Languages");
        List<Skill> frameworks = skillService.getSkillsByCategory("Frameworks");
        List<Skill> cloudDatabases = skillService.getSkillsByCategory("Cloud & Databases");
        List<Skill> tools = skillService.getSkillsByCategory("Tools");

        assertEquals(3, languages.size(), "Languages should contain 3 skills");
        assertEquals(3, frameworks.size(), "Frameworks should contain 3 skills");
        assertEquals(5, cloudDatabases.size(), "Cloud & Databases should contain 5 skills");
        assertEquals(11, tools.size(), "Tools should contain 11 skills");
    }

    @Test
    void testAchievementsLoaded() {
        List<Achievement> achievements = achievementService.getAllAchievements();
        assertEquals(5, achievements.size());
        assertTrue(achievements.stream().anyMatch(a -> a.getTitle().contains("Tech-Akriti")));
    }

    @Test
    void testResponsibilitiesLoaded() {
        List<Responsibility> responsibilities = responsibilityService.getAllResponsibilities();
        assertEquals(2, responsibilities.size());
        assertTrue(responsibilities.stream().anyMatch(r -> r.getRoleTitle().equals("Vice Captain")));
        assertTrue(responsibilities.stream().anyMatch(r -> r.getRoleTitle().equals("Joint Secretary")));
    }
}
