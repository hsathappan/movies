package com.example.guild.repository.entity.compositePK;

import lombok.*;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GenreYearPK implements Serializable {
    long genreId;
    int year;
}
