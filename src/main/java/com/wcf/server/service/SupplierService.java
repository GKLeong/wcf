package com.wcf.server.service;

import com.wcf.server.base.response.BizException;
import com.wcf.server.model.Supplier;
import com.wcf.server.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class SupplierService {
    private final SupplierRepository supplierRepository;

    @Autowired
    public SupplierService(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    public List<Supplier> findAll() {
        return supplierRepository.findAll();
    }

    public Supplier findById(Long id) {
        return supplierRepository.findById(id).orElseThrow(() -> new BizException("找不到供应商id: " + id));
    }

    public Supplier addSupplier(String name, String contactPerson, String phone, String address, BigDecimal paymentPercentage, String comments) {
        Supplier supplier = new Supplier();
        supplier.setName(name);
        supplier.setContactPerson(contactPerson);
        supplier.setPhone(phone);
        supplier.setAddress(address);
        supplier.setPaymentPercentage(paymentPercentage);
        supplier.setComments(comments);
        supplier.setEffective(true);
        return supplierRepository.save(supplier);
    }

    public Supplier updateSupplier(Long id, String name, String contactPerson, String phone, String address, BigDecimal paymentPercentage, String comments) {
        Supplier supplier = findById(id);
        supplier.setName(name);
        supplier.setContactPerson(contactPerson);
        supplier.setPhone(phone);
        supplier.setAddress(address);
        supplier.setPaymentPercentage(paymentPercentage);
        supplier.setComments(comments);
        return supplierRepository.save(supplier);
    }

    public void enable(Long id) {
        Supplier supplier = findById(id);
        supplier.setEffective(true);
        supplierRepository.save(supplier);
    }

    public void disable(Long id) {
        Supplier supplier = findById(id);
        supplier.setEffective(false);
        supplierRepository.save(supplier);
    }
}
