package com.upiiz.panaderia.services;

import com.upiiz.panaderia.entities.ProductoEntity;
import com.upiiz.panaderia.repositories.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoServicelmpl implements ProductoService{

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public ProductoEntity actualizarProducto(ProductoEntity producto){
        return productoRepository.save(producto);
    }

    @Override
    public ProductoEntity guardarProducto(ProductoEntity producto){
        return productoRepository.save(producto);
    }

    @Override
    public List<ProductoEntity> listarProducto(){
        return productoRepository.findAll();
    }

    @Override
    public void eliminarProducto(Long id_producto){
        productoRepository.deleteById(id_producto);
    }
}
