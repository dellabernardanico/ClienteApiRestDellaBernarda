package com.example.clientservice.service;


import com.example.clientservice.model.Client;
import com.example.clientservice.repository.ClientRepository;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class ClientService {
    @Autowired
    ClientRepository clientRepository;


    public Client create(String name, String lastName, String fechaNacimiento){
        Client c = new Client();
        c.setName(name);
        c.setLastName(lastName);
        c.setFechaNacimiento(LocalDate.parse(fechaNacimiento));
        return this.clientRepository.save(c);
    }

    public String finById(Long id){
        Optional<Client> cajaCliente = this.clientRepository.findById(id);
        JSONObject objetoJSON = null;
        if(cajaCliente.isPresent()){
            Client c = cajaCliente.get();

            String descripcionClient = "{ \n" +
                                    "\"id\": " + c.getId() + "," +
                                    "\"name\": " + "\"" + c.getName()+ "\"" + "," +
                                    "\"lastName\": " + "\"" + c.getLastName() + "\"" +" ," +
                                    "\"edad\":" +this.calcularEdad(c.getFechaNacimiento())+ " ," +
                                "}";

            // return descripcionClient;

            try{
              objetoJSON = new JSONObject(descripcionClient);
            }catch (JSONException e){
                e.printStackTrace();
            }

            return objetoJSON.toString();
        }

        return null;
    }

    public int calcularEdad(LocalDate fechaNacimiento){

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate fechaNac = LocalDate.parse(fechaNacimiento.toString(), fmt);
        LocalDate ahora = LocalDate.now();

        Period periodo = Period.between(fechaNac, ahora);

        return periodo.getYears();
    }
}
