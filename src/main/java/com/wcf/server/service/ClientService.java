package com.wcf.server.service;

import com.wcf.server.base.response.BizException;
import com.wcf.server.model.Client;
import com.wcf.server.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ClientService {
    private final ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    public Client findById(Long id) {
        return clientRepository.findById(id).orElseThrow(() -> new BizException("找不到客户id: " + id));
    }

    public Client add(String name, String contactPerson, String phone, String address, BigDecimal paymentPercentage, String comments) {
        Client client = new Client();
        client.setName(name);
        client.setContactPerson(contactPerson);
        client.setPhone(phone);
        client.setAddress(address);
        client.setPaymentPercentage(paymentPercentage);
        client.setComments(comments);
        client.setEffective(true);
        return clientRepository.save(client);
    }

    public Client update(Long id, String name, String contactPerson, String phone, String address, BigDecimal paymentPercentage, String comments) {
        Client client = findById(id);
        client.setName(name);
        client.setContactPerson(contactPerson);
        client.setPhone(phone);
        client.setAddress(address);
        client.setPaymentPercentage(paymentPercentage);
        client.setComments(comments);
        return clientRepository.save(client);
    }

    public void enable(Long id) {
        Client client = findById(id);
        client.setEffective(true);
        clientRepository.save(client);
    }

    public void disable(Long id) {
        Client client = findById(id);
        client.setEffective(false);
        clientRepository.save(client);
    }
}
