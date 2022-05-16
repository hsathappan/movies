package com.example.guild.repository;

import com.example.guild.repository.entity.relationship.CompanyProduction;
import com.example.guild.repository.entity.compositePK.CompanyProductionPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyProductionRepository extends JpaRepository<CompanyProduction, CompanyProductionPK> {
}
