package com.example.cliente.controller;

import com.example.cliente.entity.Cliente;
import com.example.cliente.repository.IClienteRepository;
import com.example.cliente.service.PersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/cliente")
@CrossOrigin(value={})
public class PersonaController {

    @Autowired
    private PersonaService service;

    @GetMapping("/getAll")
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(this.service.findClientes());

    }
    @GetMapping("/getById")
    public ResponseEntity<?> getClienteById(String id){
        return ResponseEntity.ok(this.service.findClienteById(id));

    }


    @PostMapping("/addCliente")
    public Cliente addCliente (@RequestBody Cliente cliente){
        this.service.saveCliente(cliente);
        return cliente;
    }
}
