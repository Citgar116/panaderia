package com.upiiz.panaderia.controllers;

import com.upiiz.panaderia.entities.ProductoEntity;
import com.upiiz.panaderia.entities.UsuarioEntity;
import com.upiiz.panaderia.services.ProductoServicelmpl;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping ("/productos")
public class ProductoController {

    @Autowired
    private ProductoServicelmpl productoServicelmpl;

    @GetMapping
    public String listarproductos(Model model, HttpSession session) {
        UsuarioEntity usuario = (UsuarioEntity) session.getAttribute("usuarioLogueado");
        if (usuario == null) {
            return "redirect:/auth/login";
        }
        model.addAttribute("usuario", usuario);
        List<ProductoEntity> listadoProductos = productoServicelmpl.listarProducto();
        model.addAttribute("productos", listadoProductos);
        return "listado-productos";
    }

    @GetMapping("/nuevo")
    public String mostratFormularioRegiatro(Model model, HttpSession session) {
        UsuarioEntity usuario = (UsuarioEntity) session.getAttribute("usuarioLogueado");
        if (usuario == null) {
            return "redirect:/auth/login";
        }
        model.addAttribute("usuario", usuario);
        ProductoEntity producto = new ProductoEntity();
        model.addAttribute("productos", producto);
        return "agregar-productos";
    }

    @PostMapping("/guardar")
    public String guardarProducto(@ModelAttribute("productos") ProductoEntity producto) {
        productoServicelmpl.guardarProducto(producto);
        return "redirect:/productos";
    }

    @GetMapping("/actualizar/{id}")
    public String mostrarFormularioEditar(@PathVariable("id") Long id, Model model, HttpSession session) {
        UsuarioEntity usuario = (UsuarioEntity) session.getAttribute("usuarioLogueado");
        if (usuario == null) {
            return "redirect:/auth/login";
        }
        model.addAttribute("usuario", usuario);
        ProductoEntity producto = productoServicelmpl.listarProducto().stream().filter(c -> c.getId_productos().equals(id)).findFirst().orElse(null);
        model.addAttribute("productos", producto);
        return "actualizar-productos";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarProducto(@PathVariable Long id, RedirectAttributes flash){
       try{
           productoServicelmpl.eliminarProducto(id);
           flash.addFlashAttribute("succes", "El pan se elimino correctamente.");
       } catch (DataIntegrityViolationException e) {
           flash.addFlashAttribute("error", "No puedes borrar este pan porque tiene ventas registradas. " + "Primero debes anular las ventas asociadas. ");
       }
       return "redirect:/productos";
    }
}
