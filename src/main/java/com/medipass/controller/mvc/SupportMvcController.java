package com.medipass.controller.mvc;

import com.medipass.service.SupportService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dashboard/support")
public class SupportMvcController extends BaseController {

    private final SupportService supportService;

    public SupportMvcController(SupportService supportService) {
        this.supportService = supportService;
    }

    @GetMapping({"", "/"})
    public String dashboard(Model model) {
        addCommonAttributes(model, "dashboard", "Centro de Soporte", "Gestión de tickets y PQRS");
        model.addAttribute("stats", supportService.getStats());
        model.addAttribute("openTickets", supportService.getOpenTickets());
        model.addAttribute("recentResolved", supportService.getResolvedTickets());
        model.addAttribute("ticketData", supportService.getMetrics());
        return "dashboard/support/index";
    }
}
