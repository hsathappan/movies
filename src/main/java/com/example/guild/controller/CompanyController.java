package com.example.guild.controller;

import com.example.guild.controller.dto.CompanyDto;
import com.example.guild.service.CompanyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(path = "api/v1/movieDb/companies")
@AllArgsConstructor
public class CompanyController {

    CompanyService companyService;

    @Operation(summary = "Get a company by its id with its name, budget and revenue for a specific year")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Company successfully found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CompanyDto.class)) }),
            @ApiResponse(responseCode = "204", description = "No content available for the company for the specified year",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Company not found",
                    content = @Content) })
    @GetMapping(path="{companyId}")
    public CompanyDto getCompany(@PathVariable("companyId") long companyId,
                                 @RequestParam(name = "year") int year
                              ) {
        log.info("Requesting company id {}", companyId);
        return companyService.getCompany(companyId, year);
    }

}
