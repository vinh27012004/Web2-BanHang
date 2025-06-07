package ntu.vinh.banhang.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ntu.vinh.banhang.entity.Invoice;
import ntu.vinh.banhang.entity.InvoiceItem;
import ntu.vinh.banhang.entity.Product;
import ntu.vinh.banhang.entity.Customer;
import ntu.vinh.banhang.model.CartItem;
import ntu.vinh.banhang.repository.InvoiceRepository;
import ntu.vinh.banhang.repository.ProductRepository;
import ntu.vinh.banhang.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    @Transactional
    public Invoice createOrder(List<CartItem> cartItems, Customer customer, Double customerPaid) {
        Invoice invoice = new Invoice();
        invoice.setCreatedAt(LocalDateTime.now());
        invoice.setCustomer(customer);
        
        double totalAmount = 0;
        List<InvoiceItem> invoiceItems = new ArrayList<>();
        
        for (CartItem cartItem : cartItems) {
            Product product = productRepository.findById(cartItem.getProduct().getId())
                .orElseThrow(() -> new RuntimeException("Product not found"));
            
            if (product.getQuantity() < cartItem.getQuantity()) {
                throw new RuntimeException("Insufficient stock for product: " + product.getName());
            }
            
            InvoiceItem invoiceItem = new InvoiceItem();
            invoiceItem.setProduct(product);
            invoiceItem.setQuantity(cartItem.getQuantity());
            invoiceItem.setUnitPrice(product.getPrice());
            invoiceItem.setTotalPrice(cartItem.getTotalPrice());
            invoiceItem.setInvoice(invoice);
            
            invoiceItems.add(invoiceItem);
            totalAmount += invoiceItem.getTotalPrice();
            
            // Update product quantity
            product.setQuantity(product.getQuantity() - cartItem.getQuantity());
            productRepository.save(product);
        }
        
        invoice.setTotalAmount(totalAmount);
        invoice.setCustomerPaid(customerPaid);
        invoice.setChangeAmount(customerPaid - totalAmount);
        invoice.setItems(invoiceItems);
        
        return invoiceRepository.save(invoice);
    }

    @Override
    public Invoice getOrder(Long id) {
        return invoiceRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    @Override
    public List<Invoice> getAllOrders() {
        return invoiceRepository.findAll();
    }
} 