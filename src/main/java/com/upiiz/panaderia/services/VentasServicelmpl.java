package com.upiiz.panaderia.services;

import com.upiiz.panaderia.entities.VentasEntity;
import com.upiiz.panaderia.repositories.VentasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VentasServicelmpl implements VentasService {

    @Autowired
    private VentasRepository ventasRepository;

    @Override
    public VentasEntity actualizarVentas(VentasEntity ventas){
        return ventasRepository.save(ventas);
    }

    @Override
    public VentasEntity guardarVentas(VentasEntity ventas){
        return ventasRepository.save(ventas);
    }

    @Override
    public List<VentasEntity> listarVentas(){
        return ventasRepository.findAll();
    }

    @Override
    public void eliminarVentas(Long id_ventas){
        ventasRepository.deleteById(id_ventas);
    }

    @Override
    public List<VentasEntity> buscarPorNombre(String nombre){
        return ventasRepository.findByNombreContainingIgnoreCase(nombre);
    }

}
