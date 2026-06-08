package com.medipass.controller.mvc;

import com.medipass.service.LabService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dashboard/lab")
public class LabMvcController extends BaseController {

    private final LabService labService;

    public LabMvcController(LabService labService) {
        this.labService = labService;
    }

    @GetMapping({"", "/"})
    public String dashboard(Model model) {
        addCommonAttributes(model, "dashboard", "Laboratorio Clínico", "Órdenes y resultados pendientes");
        model.addAttribute("stats", labService.getStats());
        model.addAttribute("criticalAlerts", labService.getCriticalAlerts());
        model.addAttribute("pendingOrders", labService.getPendingOrders());
        model.addAttribute("recentPublished", labService.getPublishedResults());
        return "dashboard/lab/index";
    }
}
