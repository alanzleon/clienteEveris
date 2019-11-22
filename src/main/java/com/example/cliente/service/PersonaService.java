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

    String[] tiposLicencia = {"A","B","C"};

    @Override
    public String saveCliente(Cliente cliente) {
        try {
                if(cliente.getRut() != null) {
                    if(validarRut(cliente.getRut())){
                        //Se le da formato al rut xx.xxx.xxx-x
                        cliente.setRut(formatearRut(cliente.getRut()));
                        if(clienteExist(cliente.getRut())){
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
                                                                    cliente.setTipoLicencia(tipoLiencia);
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
                            return "rutExistente";
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
    public String updateCliente(Cliente cliente, String rut) {
        //Valido que exista el cliente
        if(this.repository.findOneByRut(rut) != null){
            Cliente clientebd = this.repository.findOneByRut(rut);
            if(cliente.getNombre() != null) {
                clientebd.setNombre(cliente.getNombre());
            }
            if(cliente.getApellidoPaterno() != null) {
                clientebd.setApellidoPaterno(cliente.getApellidoPaterno());
            }
            if(cliente.getApellidoMaterno() != null) {
                clientebd.setApellidoMaterno(cliente.getApellidoMaterno());
            }
            if(cliente.getEdad() >= 25) {
                clientebd.setEdad(cliente.getEdad());
            } else {
                return "invalidEdad";
            }
            if(cliente.getSexo() != null) {
                String sexo = cliente.getSexo().toLowerCase();
                if(sexo.equals("masculino") || sexo.equals("femenino")) {
                    clientebd.setSexo(sexo);
                } else {
                    return "invalidSexo";
                }
            }
            if(cliente.getDireccion() != null) {
                clientebd.setDireccion(cliente.getDireccion());
            }
            if(cliente.getTelefono() != null) {
                if(validarTelefono(cliente.getTelefono())){
                    clientebd.setTelefono(cliente.getTelefono());
                } else {
                    return "invalidTelefono";
                }
            }
            if(cliente.getTipoLicencia() != null) {
                String tipoLiencia = cliente.getTipoLicencia().toUpperCase();
                if(Arrays.asList(tiposLicencia).contains(tipoLiencia)){
                    clientebd.setTipoLicencia(tipoLiencia);
                } else {
                    return "invalidTipoLicencia";
                }
            }
            if(cliente.getFechaEmisionLicencia() != null) {
                clientebd.setFechaEmisionLicencia(cliente.getFechaEmisionLicencia());
            }
            if(cliente.getFechaVencimientoLicencia() != null) {
                clientebd.setFechaVencimientoLicencia(cliente.getFechaVencimientoLicencia());
            }
            this.repository.save(clientebd);
            return "update";
        } else {
            return "notFound";
        }
    }

    @Override
    public Cliente findClienteById(@PathVariable String idCliente) {
        return  this.repository.findById(idCliente).get();
    }

    @Override
    public Cliente findClienteByRut(String rut) {
        String rutFormateado = formatearRut(rut);
        return this.repository.findOneByRut(rutFormateado);
    }

    public boolean clienteExist(String rut) {
        if(this.repository.findOneByRut(rut) == null){
            return false;
        } else {
            return true;
        }
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

    public boolean validarTelefono(String telefono) {
        Pattern pattern = Pattern.compile("^(\\+?56)?(\\s?)(0?9)(\\s?)[9876543]\\d{7}$");
        return pattern.matcher(telefono).matches();
    }

    public String formatearRut(String rut){
        int cont=0;
        String format;
        if(rut.length() == 0){
            return "";
        }else{
            rut = rut.replace(".", "");
            rut = rut.replace("-", "");
            format = "-"+rut.substring(rut.length()-1);
            for(int i = rut.length()-2;i>=0;i--){
                format = rut.substring(i, i+1)+format;
                cont++;
                if(cont == 3 && i != 0){
                    format = "."+format;
                    cont = 0;
                }
            }
            return format;
        }
    }

}
