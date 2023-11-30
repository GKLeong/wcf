package com.wcf.server.controller;

import com.wcf.server.base.response.ResultBody;
import com.wcf.server.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/clients")
public class ClientController {
    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public ResultBody findAll() {
        return ResultBody.success(clientService.findAll());
    }

    @PostMapping
    public ResultBody add(@RequestParam(value = "name") String name,
                          @RequestParam(value = "contactPerson") String contactPerson,
                          @RequestParam(value = "phone", required = false) String phone,
                          @RequestParam(value = "address", required = false) String address,
                          @RequestParam(value = "paymentPercentage") BigDecimal paymentPercentage,
                          @RequestParam(value = "comments", required = false) String comments) {
        return ResultBody.success(clientService.add(
                name,
                contactPerson,
                phone,
                address,
                paymentPercentage,
                comments
        ));
    }

    @PutMapping("/{id}")
    public ResultBody update(@PathVariable Long id,
                             @RequestParam(value = "name") String name,
                             @RequestParam(value = "contactPerson") String contactPerson,
                             @RequestParam(value = "phone", required = false) String phone,
                             @RequestParam(value = "address", required = false) String address,
                             @RequestParam(value = "paymentPercentage") BigDecimal paymentPercentage,
                             @RequestParam(value = "comments", required = false) String comments) {
        return ResultBody.success(clientService.update(
                id,
                name,
                contactPerson,
                phone,
                address,
                paymentPercentage,
                comments
        ));

    }

    @PutMapping("/{id}/enable")
    public ResultBody enable(@PathVariable Long id) {
        clientService.enable(id);
        return ResultBody.success();
    }

    @PutMapping("/{id}/disable")
    public ResultBody disable(@PathVariable Long id) {
        clientService.disable(id);
        return ResultBody.success();
    }
}
