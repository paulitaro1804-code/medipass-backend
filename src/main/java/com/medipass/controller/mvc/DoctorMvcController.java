package com.medipass.controller.mvc;

import com.medipass.service.DoctorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.Collections;

@Controller
@RequestMapping("/dashboard/doctor")
public class DoctorMvcController extends BaseController {

    private final DoctorService doctorService;

    public DoctorMvcController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping({"", "/"})
    public String dashboard(Model model) {
        addCommonAttributes(model, "dashboard", "Panel del Médico", "Tu agenda y pacientes de hoy");
        model.addAttribute("stats", doctorService.getStats());
        model.addAttribute("todayPatients", doctorService.getTodaySchedule());
        model.addAttribute("recentRecords", Collections.emptyList());
        model.addAttribute("pendingLabs", Collections.emptyList());
        return "dashboard/doctor/index";
    }
}
