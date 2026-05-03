package com.upiiz.panaderia.services;

import com.upiiz.panaderia.entities.VentasEntity;

import java.util.List;

public interface VentasService {
    VentasEntity guardarVentas(VentasEntity ventas);
    List<VentasEntity> listarVentas();
    VentasEntity actualizarVentas(VentasEntity ventas);
    void eliminarVentas(Long id_ventas);
    List<VentasEntity> buscarPorNombre(String nombre);
}
