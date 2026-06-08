package com.medipass.controller.mvc;

import com.medipass.service.ValidatorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dashboard/validator")
public class ValidatorMvcController extends BaseController {

    private final ValidatorService validatorService;

    public ValidatorMvcController(ValidatorService validatorService) {
        this.validatorService = validatorService;
    }

    @GetMapping({"", "/"})
    public String dashboard(Model model) {
        addCommonAttributes(model, "dashboard", "Validación de Pólizas", "Cola de validación y alertas");
        model.addAttribute("stats", validatorService.getStats());
        model.addAttribute("pendingRequests", validatorService.getPendingRequests());
        model.addAttribute("expiringPolicies", validatorService.getExpiringPolicies());
        return "dashboard/validator/index";
    }

    @GetMapping("/policies")
    public String policies(Model model) {
        addCommonAttributes(model, "policies", "Gestión de Pólizas", "Revisión y validación de pólizas");
        model.addAttribute("pendingRequests", validatorService.getPendingRequests());
        model.addAttribute("approvedRequests", validatorService.getApprovedRequests());
        model.addAttribute("rejectedRequests", validatorService.getRejectedRequests());
        return "dashboard/validator/policies";
    }
}
