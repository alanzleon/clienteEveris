package com.example.cliente.service;

import com.example.cliente.entity.Cliente;
import com.example.cliente.repository.IClienteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class PersonaService implements IPersonaService{
    @Autowired
    private IClienteRepository repository;

    @Override
    public String saveCliente(Cliente cliente) {
        try {
                String[] tiposLicencia = {"A","B","C"};

                if(cliente.getRut() != null) {
                    if(validarRut(cliente.getRut())){
                        //Falta validar si existe el rut en la bd.
                        if(cliente.getEdad() >= 25 && cliente.getEdad() < 100){
                            if(cliente.getNombre() != null) {
                                if (cliente.getApellidoPaterno() != null) {
                                    if(cliente.getApellidoMaterno() != null) {
                                        if(cliente.getSexo() != null) {

                                            String sexo = cliente.getSexo().toLowerCase();

                                            if(sexo.equals("masculino") || sexo.equals("femenino")) {
                                                cliente.setSexo(sexo);
                                                if(cliente.getDireccion() != null) {
                                                    if(cliente.getTelefono() != null){
                                                        if(validarTelefono(cliente.getTelefono())){
                                                            if(cliente.getTipoLicencia() != null) {
                                                                String tipoLiencia = cliente.getTipoLicencia().toUpperCase();
                                                                if(Arrays.asList(tiposLicencia).contains(tipoLiencia)){
                                                                    if(cliente.getFechaEmisionLicencia() != null){
                                                                        if(cliente.getFechaVencimientoLicencia() != null){
                                                                            this.repository.save(cliente);
                                                                            return "ok";
                                                                        } else {
                                                                            return "noFechaVencimiento";
                                                                        }
                                                                    } else {
                                                                        return "noFechaEmision";
                                                                    }
                                                                } else {
                                                                    return "invalidTipoLicencia";
                                                                }
                                                            } else {
                                                                return "NoTipoLicencia";
                                                            }
                                                        } else {
                                                            return "invalidTelefono";
                                                        }
                                                    } else {
                                                        return "NoTelefono";
                                                    }
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
                        return "invalidRut";
                    }
                } else {
                    return "NoRut";
                }
        } catch (Exception ex) {
            return "" + ex;
        }

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

    public boolean validarRut(String rut) {

        boolean validacion = false;
        try {
            rut =  rut.toUpperCase();
            rut = rut.replace(".", "");
            rut = rut.replace("-", "");
            int rutAux = Integer.parseInt(rut.substring(0, rut.length() - 1));

            char dv = rut.charAt(rut.length() - 1);

            int m = 0, s = 1;
            for (; rutAux != 0; rutAux /= 10) {
                s = (s + rutAux % 10 * (9 - m++ % 6)) % 11;
            }
            if (dv == (char) (s != 0 ? s + 47 : 75)) {
                validacion = true;
            }

        } catch (java.lang.NumberFormatException e) {
        } catch (Exception e) {
        }
        return validacion;
    }

    private  boolean validarTelefono(String telefono) {
        Pattern pattern = Pattern.compile("^(\\+?56)?(\\s?)(0?9)(\\s?)[9876543]\\d{7}$");
        return pattern.matcher(telefono).matches();
    }

}
