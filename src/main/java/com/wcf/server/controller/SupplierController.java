package com.wcf.server.controller;

import com.wcf.server.base.response.ResultBody;
import com.wcf.server.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/suppliers")
public class SupplierController {
    private final SupplierService supplierService;

    @Autowired
    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @GetMapping
    public ResultBody findAll() {
        return ResultBody.success(supplierService.findAll());
    }

    @PostMapping
    public ResultBody add(@RequestParam(value = "name") String name,
                          @RequestParam(value = "contactPerson") String contactPerson,
                          @RequestParam(value = "phone", required = false) String phone,
                          @RequestParam(value = "address", required = false) String address,
                          @RequestParam(value = "paymentPercentage") BigDecimal paymentPercentage,
                          @RequestParam(value = "comments", required = false) String comments) {
        return ResultBody.success(supplierService.addSupplier(
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
        return ResultBody.success(supplierService.updateSupplier(
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
        supplierService.enable(id);
        return ResultBody.success();
    }

    @PutMapping("/{id}/disable")
    public ResultBody disable(@PathVariable Long id) {
        supplierService.disable(id);
        return ResultBody.success();
    }
}
