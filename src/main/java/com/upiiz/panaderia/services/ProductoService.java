package com.upiiz.panaderia.services;

import com.upiiz.panaderia.entities.ProductoEntity;

import java.util.List;

public interface ProductoService {
    ProductoEntity guardarProducto(ProductoEntity producto);
    List<ProductoEntity> listarProducto();
    ProductoEntity actualizarProducto(ProductoEntity producto);
    void eliminarProducto(Long id_producto);
}
