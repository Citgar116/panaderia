package com.upiiz.panaderia.controllers;

import com.upiiz.panaderia.entities.UsuarioEntity;
import com.upiiz.panaderia.entities.VentasEntity;
import com.upiiz.panaderia.services.ProductoServicelmpl;
import com.upiiz.panaderia.services.VentasServicelmpl;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/ventas")
public class VentasController {

    @Autowired
    private VentasServicelmpl ventasServicelmpl;

    @Autowired
    private ProductoServicelmpl productoServicelmpl;



    @GetMapping
    public String listarventas(@RequestParam(name = "nombre", required = false) String nombre, Model model, HttpSession session){
        UsuarioEntity usuario = (UsuarioEntity) session.getAttribute("usuarioLogueado");
        if (usuario == null){
            return "redirect:/auth/login";
        }
        model.addAttribute("usuario", usuario);
        List<VentasEntity> listadoVentas;

        if (nombre != null && !nombre.isEmpty()){
            listadoVentas = ventasServicelmpl.buscarPorNombre(nombre);
        } else {
            listadoVentas = ventasServicelmpl.listarVentas();
        }

        model.addAttribute("ventas", listadoVentas);
        return "listado-ventas";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioRegistro(Model model, HttpSession session){
        UsuarioEntity usuario = (UsuarioEntity) session.getAttribute("usuarioLogueado");
        if (usuario == null) return "redirect:/auth/login";

        model.addAttribute("usuario", usuario);
        model.addAttribute("ventas", new VentasEntity());

        // ESTA LÍNEA ES NUEVA: Pasamos todos los panes para el menú desplegable
        model.addAttribute("listadoProductos", productoServicelmpl.listarProducto());

        return "agregar-ventas";
    }

    @PostMapping("/guardar")
    public String guardarVentas(@ModelAttribute("ventas") VentasEntity ventas){
        ventasServicelmpl.guardarVentas(ventas);
        return "redirect:/ventas";
    }

    @GetMapping("/actualizar/{id}")
    public String mostrarFormularioEditar(@PathVariable("id") Long id, Model model, HttpSession session){
        UsuarioEntity usuario = (UsuarioEntity) session.getAttribute("usuarioLogueado");
        if (usuario == null){
            return "redirect:/auth/login";
        }
        model.addAttribute("usuario", usuario);
        VentasEntity ventas = ventasServicelmpl.listarVentas().stream().filter(v-> v.getId_ventas().equals(id)).findFirst().orElse(null);
        model.addAttribute("ventas", ventas);

        model.addAttribute("listadoProductos",productoServicelmpl.listarProducto());
        return "actualizar-ventas";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarVentas(@PathVariable Long id){
        ventasServicelmpl.eliminarVentas(id);
        return "redirect:/ventas";
    }
    @GetMapping("/factura/{id}")
    public String verFactura(@PathVariable("id") Long id, Model model, HttpSession session) {
        UsuarioEntity usuario = (UsuarioEntity) session.getAttribute("usuarioLogueado");
        if (usuario == null) {
            return "redirect:/auth/login";
        }

        VentasEntity venta = ventasServicelmpl.listarVentas().stream()
                .filter(v -> v.getId_ventas().equals(id))
                .findFirst()
                .orElse(null);

        model.addAttribute("usuario", usuario);
        model.addAttribute("venta", venta);
        return "factura"; // Nombre de tu archivo HTML
    }
}
