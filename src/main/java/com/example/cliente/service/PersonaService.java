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
    public String saveCliente(Cliente cliente) {
        cliente.setSexo(cliente.getSexo().toUpperCase());
        if(cliente.getRut() != null) {
            if(cliente.getEdad() >= 25 && cliente.getEdad() < 100){
                if(cliente.getNombre() != null) {
                    if (cliente.getApellidoPaterno() != null) {
                        if(cliente.getApellidoMaterno() != null) {
                            if(cliente.getSexo() != null) {
                                if(cliente.getSexo().equals("H") || cliente.getSexo().equals("M")) {
                                    if(cliente.getDireccion() != null) {
                                        //Faltan validaciones
                                    } else {
                                        return "NoDireccion";
                                    }
                                } else {
                                    return "InvalidSexo";
                                }
                            } else {
                                return "NoSexo";
                            }
                        } else {
                            return "noApellidoMaterno";
                        }
                    } else {
                        return "NoApellidoPaterno";
                    }
                } else {
                    return "NoNombre";
                }
            } else {
                return "NoEdad";
            }
        } else {
            return "NoRut";
        }
        if (cliente.getEdad()>=25){
            this.repository.save(cliente);
            return "ok";
        } else {
            return "menor";
        }
        //Falta validar tipo licencia
    }

    @Override
    public List<Cliente> findClientes() {
        return this.repository.findAll();
    }

    @Override
    public String updateCliente(Cliente cliente, String id) {
        if(this.repository.findOneById(id) != null){
            cliente.setId(id);
            this.repository.save(cliente);
            return "update";
        } else {
            return "updateError";
        }

    }

    @Override
    public Cliente findClienteById(@PathVariable String idCliente) {
        return  this.repository.findById(idCliente).get();
    }


}
