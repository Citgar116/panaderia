package com.upiiz.panaderia.controllers;

import com.upiiz.panaderia.entities.UsuarioEntity;
import com.upiiz.panaderia.services.ProductoServicelmpl;
import com.upiiz.panaderia.services.UsuarioService;
import com.upiiz.panaderia.services.VentasServicelmpl;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("auth")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ProductoServicelmpl productoServicelmpl;

    @Autowired
    private VentasServicelmpl ventasServicelmpl;

    @GetMapping("/login")
    public String login() { return "login-v2"; }

    @GetMapping("/register")
    public String register() { return "register-v2"; }

    @GetMapping("/forgot")
    public String forgot() { return "forgot-password-v2"; }

    @GetMapping("/menu")
    public String menu(Model model, HttpSession session) {
        UsuarioEntity usuario = (UsuarioEntity) session.getAttribute("usuarioLogueado");
        if (usuario == null) {
            return "redirect:/auth/login";
        }
        model.addAttribute("usuario", usuario);

        int totalProductos = productoServicelmpl.listarProducto().size();
        int totalVentas = ventasServicelmpl.listarVentas().size();

        model.addAttribute("totalProductos", totalProductos);
        model.addAttribute("totalVentas", totalVentas);
        return "menu";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/auth/login";
    }

    @PostMapping("/login")
    public String loginPost(@RequestParam String correo,
                            @RequestParam String contrasena,
                            HttpSession session, Model model) {
        UsuarioEntity usuario = usuarioService.autenticar(correo, contrasena);
        if (usuario != null) {
            session.setAttribute("usuarioLogueado", usuario);
            return "redirect:/auth/menu";
        } else {
            model.addAttribute("error", "Correo o contraseña incorrectos");
            return "login-v2";
        }
    }

    @PostMapping("/register")
    public String registerPost(@ModelAttribute UsuarioEntity usuario,
                               @RequestParam String confirmar_contrasena,
                               Model model) {
        try {
            if (!usuario.getContrasena().equals(confirmar_contrasena)) {
                model.addAttribute("error", "Las contraseñas no coinciden");
                return "register-v2";
            }
            usuarioService.registrarUsuario(usuario);
            return "redirect:/auth/login?exito=true";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "register-v2";
        }
    }

    @PostMapping("/forgot")
    public String forgotPost(@RequestParam String correo, Model model) {
        try {
            // Llamamos al servicio para enviar el correo real
            usuarioService.enviarCorreoRecuperacion(correo);
            model.addAttribute("mensaje", "Te hemos enviado un correo con tu contraseña.");
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        return "forgot-password-v2";
    }
}