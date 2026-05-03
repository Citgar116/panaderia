package com.upiiz.panaderia.services;

import com.upiiz.panaderia.entities.UsuarioEntity;
import com.upiiz.panaderia.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JavaMailSender mailSender;



    public UsuarioEntity registrarUsuario(UsuarioEntity usuario) throws Exception {
        if (usuarioRepository.findByCorreo(usuario.getCorreo()).isPresent()){
            throw new Exception("Ya exisite un usuario con este correo electronico.");
        }
        return usuarioRepository.save(usuario);
    }

    public UsuarioEntity autenticar(String correo, String contrasena){
        Optional<UsuarioEntity> usuarioOpt = usuarioRepository.findByCorreo(correo);
        if (usuarioOpt.isPresent()){
            UsuarioEntity usuario = usuarioOpt.get();
            if (usuario.getContrasena().equals(contrasena)){
                return usuario;
            }
        }
        return null;
    }

    public String recuperarConstrasena(String correo) throws Exception{
        Optional<UsuarioEntity> usuarioOpt = usuarioRepository.findByCorreo(correo);
        if (usuarioOpt.isPresent()){
            return usuarioOpt.get().getContrasena();
        } else {
            throw new Exception("No se encontró ningun usuario con ese correo.");
        }
    }

    public void enviarCorreoRecuperacion(String correo) throws Exception {
        Optional<UsuarioEntity> usuarioOpt = usuarioRepository.findByCorreo(correo);

        if (usuarioOpt.isPresent()){
            UsuarioEntity usuario = usuarioOpt.get();

            SimpleMailMessage mensaje = new SimpleMailMessage();
            mensaje.setTo(correo);
            mensaje.setSubject("Recuperacion de contraseña - Panaderia - Cook and Bread");
            mensaje.setText("Hola " + usuario.getNombre() + ",\n\n" + "Tu contraseña actual es: " + usuario.getContrasena() + "\n\n" + "Te recomendamos cambiarla pronto por seguridad.");

            mailSender.send(mensaje);
        } else {
            throw new Exception("No se encontro ningun usuario con este correo.");
        }
    }
}
