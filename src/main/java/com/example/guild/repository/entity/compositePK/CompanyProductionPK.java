package com.example.guild.repository.entity.compositePK;

import lombok.*;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompanyProductionPK implements Serializable {
    long movieId;
    long companyId;
}
