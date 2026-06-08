package com.medipass.controller.mvc;

import com.medipass.service.CoordinatorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dashboard/coordinator")
public class CoordinatorMvcController extends BaseController {

    private final CoordinatorService coordinatorService;

    public CoordinatorMvcController(CoordinatorService coordinatorService) {
        this.coordinatorService = coordinatorService;
    }

    @GetMapping({"", "/"})
    public String dashboard(Model model) {
        addCommonAttributes(model, "dashboard", "Coordinación Médica", "Agenda y recursos del día");
        model.addAttribute("stats", coordinatorService.getStats());
        model.addAttribute("todaySchedule", coordinatorService.getTodaySchedule());
        model.addAttribute("doctors", coordinatorService.getDoctors());
        model.addAttribute("waitlist", coordinatorService.getWaitlist());
        return "dashboard/coordinator/index";
    }
}
