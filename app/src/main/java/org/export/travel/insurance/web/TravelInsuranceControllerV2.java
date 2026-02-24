package org.export.travel.insurance.web;

import lombok.RequiredArgsConstructor;
import org.export.travel.insurance.core.api.command.TravelCalculatePremiumCoreCommand;
import org.export.travel.insurance.core.api.command.TravelCalculatePremiumCoreResult;
import org.export.travel.insurance.core.services.TravelCalculatePremiumService;
import org.export.travel.insurance.dto.v2.DtoV2Converter;
import org.export.travel.insurance.dto.v2.TravelCalculatePremiumRequestV2;
import org.export.travel.insurance.dto.v2.TravelCalculatePremiumResponseV2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class TravelInsuranceControllerV2 {

    private final TravelCalculatePremiumService service;
    private final DtoV2Converter dtoV2Converter;

    @GetMapping("/insurance/travel/web/v2")
    public String showForm(ModelMap modelMap) {
        if (!modelMap.containsAttribute("request")) {
            modelMap.addAttribute("request", new TravelCalculatePremiumRequestV2());
        }
        if (!modelMap.containsAttribute("response")) {
            modelMap.addAttribute("response", null);
        }
        return "travel-calculate-premium-v2";
    }

    @GetMapping("/insurance/travel/web/v2/clear")
    public String clearForm(RedirectAttributes redirectAttributes) {
        return "redirect:/insurance/travel/web/v2";
    }

    @PostMapping("/insurance/travel/web/v2")
    public String processForm(@ModelAttribute("request") TravelCalculatePremiumRequestV2 request,
                              RedirectAttributes redirectAttributes) {
        TravelCalculatePremiumCoreCommand coreCommand = dtoV2Converter.buildCoreCommand(request);
        TravelCalculatePremiumCoreResult coreResult = service.calculatePremium(coreCommand);
        TravelCalculatePremiumResponseV2 response = dtoV2Converter.buildResponse(coreResult);

        redirectAttributes.addFlashAttribute("request", request);
        redirectAttributes.addFlashAttribute("response", response);
        return "redirect:/insurance/travel/web/v2";
    }
}