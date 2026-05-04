package com.upiiz.panaderia.services;

import com.upiiz.panaderia.entities.UsuarioEntity;
import com.upiiz.panaderia.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

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

        if (usuarioOpt.isPresent()) {
            UsuarioEntity usuario = usuarioOpt.get();

            String mensajeTexto = "Hola " + usuario.getNombre() + ",\n\n" +
                    "Tu contraseña actual es: " + usuario.getContrasena() + "\n\n" +
                    "Te recomendamos cambiarla pronto por seguridad.";

            RestTemplate restTemplate = new RestTemplate();

            String url = "https://api.resend.com/emails";

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + System.getenv("RESEND_API_KEY"));
            headers.setContentType(MediaType.APPLICATION_JSON);

            String body = "{\n" +
                    "  \"from\": \"panaderiacokkieandbread@gmail.com\",\n" +
                    "  \"to\": [\"" + correo + "\"],\n" +
                    "  \"subject\": \"Recuperacion de contraseña - Panaderia\",\n" +
                    "  \"text\": \"" + mensajeTexto.replace("\n", "\\n") + "\"\n" +
                    "}";

            HttpEntity<String> request = new HttpEntity<>(body, headers);

            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

            System.out.println("Status: " + response.getStatusCode());

        } else {
            throw new Exception("No se encontró ningún usuario con este correo.");
        }
    }

}
