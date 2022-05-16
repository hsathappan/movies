package com.example.guild.repository.entity.relationship;

import com.example.guild.repository.entity.compositePK.CompanyProductionPK;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@IdClass(CompanyProductionPK.class)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CompanyProduction {
    @Id
    long movieId;
    @Id
    long companyId;
    int productionYear;
}
