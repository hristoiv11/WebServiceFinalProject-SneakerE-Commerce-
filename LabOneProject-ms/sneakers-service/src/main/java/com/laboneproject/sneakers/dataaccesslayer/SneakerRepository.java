package com.laboneproject.sneakers.dataaccesslayer;


import org.springframework.data.jpa.repository.JpaRepository;


public interface SneakerRepository extends JpaRepository<Sneaker,Integer> {

    Sneaker findSneakerBySneakerIdentifier_SneakerId(String sneakerId);
}


