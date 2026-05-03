package com.upiiz.panaderia.repositories;

import com.upiiz.panaderia.entities.VentasEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VentasRepository extends JpaRepository<VentasEntity,Long> {
    List<VentasEntity> findByNombreContainingIgnoreCase(String nombre);
}
