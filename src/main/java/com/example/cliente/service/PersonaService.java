package com.example.cliente.service;

import com.example.cliente.entity.Cliente;
import com.example.cliente.repository.IClienteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Service
public class PersonaService implements IPersonaService{
    @Autowired
    private IClienteRepository repository;

    @Override
    public void saveCliente(Cliente cliente) {
        if (cliente.getEdad()>=25){
            this.repository.save(cliente);
        }
        else{}

    }

    @Override
    public List<Cliente> findClientes() {
        return this.repository.findAll();
    }

    @Override
    public void updateCliente(Cliente cliente, String id) {
        cliente.setId(id);
        this.repository.save(cliente);
    }

    @Override
    public Optional<Cliente> findClienteById(@PathVariable String idCliente) {
        return  this.repository.findById(idCliente);
    }


}
