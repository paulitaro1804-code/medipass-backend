package com.medipass.controller.mvc;

import com.medipass.service.PatientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.Collections;

@Controller
@RequestMapping("/dashboard/patient")
public class PatientMvcController extends BaseController {

    private final PatientService patientService;

    public PatientMvcController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping({"", "/"})
    public String dashboard(Model model) {
        addCommonAttributes(model, "dashboard", "Portal del Paciente", "Tu salud, siempre a la mano");
        model.addAttribute("stats", patientService.getStats());
        model.addAttribute("appointments", Collections.emptyList());
        model.addAttribute("labResults", Collections.emptyList());
        model.addAttribute("notifications", Collections.emptyList());
        return "dashboard/patient/index";
    }
}
