package com.Shiwans.portfolio.Controller.admin;

import com.Shiwans.portfolio.entity.Achievement;
import com.Shiwans.portfolio.service.interfaces.AchievementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/achievements")
public class AchievementAdminController {

    private final AchievementService achievementService;

    @Autowired
    public AchievementAdminController(AchievementService achievementService) {
        this.achievementService = achievementService;
    }

    @GetMapping({"", "/", "/list"})
    public String listAchievements(Model theModel) {
        List<Achievement> achievements = achievementService.getAllAchievements();
        theModel.addAttribute("achievements", achievements);
        return "admin/achievements/list-achievements";
    }

    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model theModel) {
        Achievement achievement = new Achievement();
        theModel.addAttribute("achievement", achievement);
        return "admin/achievements/achievement-form";
    }

    @PostMapping("/save")
    public String saveAchievement(@ModelAttribute("achievement") Achievement achievement) {
        achievementService.save(achievement);
        return "redirect:/admin/achievements";
    }

    @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("achievementId") int achievementId, Model theModel) {
        Achievement foundAchievement = achievementService.findById(achievementId);
        theModel.addAttribute("achievement", foundAchievement);
        return "admin/achievements/achievement-form";
    }

    @GetMapping("/delete")
    public String deleteAchievement(@RequestParam("achievementId") int achievementId) {
        achievementService.deleteById(achievementId);
        return "redirect:/admin/achievements";
    }
}
