package com.example.guild.controller;

import com.example.guild.controller.dto.GenreDto;
import com.example.guild.service.GenreService;
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
@RequestMapping(path = "api/v1/movieDb/genres")
@AllArgsConstructor
public class GenreController {

    GenreService genreService;

    @Operation(summary = "Get the most popular genre with its name and popularity for a specific year")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Genre successfully found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = GenreDto.class)) }),
            @ApiResponse(responseCode = "204", description = "No content available for the specified year",
                    content = @Content) })
    @GetMapping
    public GenreDto getMostPopularGenre(@RequestParam(name = "year") int year) {
        log.info("Requesting most popular genre for year {}", year);
        return genreService.getMostPopularGenre(year);
    }

}
