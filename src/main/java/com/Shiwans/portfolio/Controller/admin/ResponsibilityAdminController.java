package com.Shiwans.portfolio.Controller.admin;

import com.Shiwans.portfolio.entity.Responsibility;
import com.Shiwans.portfolio.service.interfaces.ResponsibilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/responsibilities")
public class ResponsibilityAdminController {

    private final ResponsibilityService responsibilityService;

    @Autowired
    public ResponsibilityAdminController(ResponsibilityService responsibilityService) {
        this.responsibilityService = responsibilityService;
    }

    @GetMapping({"", "/", "/list"})
    public String listResponsibilities(Model theModel) {
        List<Responsibility> responsibilities = responsibilityService.getAllResponsibilities();
        theModel.addAttribute("responsibilities", responsibilities);
        return "admin/responsibilities/list-responsibilities";
    }

    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model theModel) {
        Responsibility responsibility = new Responsibility();
        theModel.addAttribute("responsibility", responsibility);
        return "admin/responsibilities/responsibility-form";
    }

    @PostMapping("/save")
    public String saveResponsibility(@ModelAttribute("responsibility") Responsibility responsibility) {
        responsibilityService.save(responsibility);
        return "redirect:/admin/responsibilities";
    }

    @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("responsibilityId") int responsibilityId, Model theModel) {
        Responsibility foundResponsibility = responsibilityService.findById(responsibilityId);
        theModel.addAttribute("responsibility", foundResponsibility);
        return "admin/responsibilities/responsibility-form";
    }

    @GetMapping("/delete")
    public String deleteResponsibility(@RequestParam("responsibilityId") int responsibilityId) {
        responsibilityService.deleteById(responsibilityId);
        return "redirect:/admin/responsibilities";
    }
}
