package org.javaguru.travel.insurance.web;

import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.command.TravelCalculatePremiumCoreCommand;
import org.javaguru.travel.insurance.core.api.command.TravelCalculatePremiumCoreResult;
import org.javaguru.travel.insurance.core.services.TravelCalculatePremiumService;
import org.javaguru.travel.insurance.dto.v1.DtoV1Converter;
import org.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumRequestV1;
import org.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumResponseV1;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class TravelInsuranceControllerV1 {

    private final TravelCalculatePremiumService service;
    private final DtoV1Converter dtoV1Converter;

    @GetMapping("/insurance/travel/web/v1")
    public String showForm(ModelMap modelMap) {
        if (!modelMap.containsAttribute("request")) {
            modelMap.addAttribute("request", new TravelCalculatePremiumRequestV1());
        }
        if (!modelMap.containsAttribute("response")) {
            modelMap.addAttribute("response", null);
        }
        return "travel-calculate-premium-v1";
    }

    @GetMapping("/insurance/travel/web/v1/clear")
    public String clearForm(RedirectAttributes redirectAttributes) {
        return "redirect:/insurance/travel/web/v1";
    }

    @PostMapping("/insurance/travel/web/v1")
    public String processForm(@ModelAttribute(value = "request") TravelCalculatePremiumRequestV1 request,
                              RedirectAttributes redirectAttributes) {
        TravelCalculatePremiumCoreCommand coreCommand = dtoV1Converter.buildCoreCommand(request);
        TravelCalculatePremiumCoreResult coreResult = service.calculatePremium(coreCommand);
        TravelCalculatePremiumResponseV1 response = dtoV1Converter.buildResponse(coreResult);

        redirectAttributes.addFlashAttribute("request", request);
        redirectAttributes.addFlashAttribute("response", response);
        return "redirect:/insurance/travel/web/v1";
    }
}
