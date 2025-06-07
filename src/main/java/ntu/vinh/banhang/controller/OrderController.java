package ntu.vinh.banhang.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;

import ntu.vinh.banhang.model.CartItem;
import ntu.vinh.banhang.service.CartService;
import ntu.vinh.banhang.service.OrderService;
import ntu.vinh.banhang.service.CustomerService;
import ntu.vinh.banhang.entity.Customer;
import ntu.vinh.banhang.entity.Invoice;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private CartService cartService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private CustomerService customerService;

    @GetMapping
    public String listOrders(Model model) {
        model.addAttribute("orders", orderService.getAllOrders());
        return "order/list";
    }

    @GetMapping("/{id}")
    public String viewOrder(@PathVariable Long id, Model model) {
        model.addAttribute("order", orderService.getOrder(id));
        return "order/detail";
    }

    @GetMapping("/checkout")
    public String checkout(Model model) {
        List<CartItem> cartItems = cartService.getCartItems();
        if (cartItems.isEmpty()) {
            return "redirect:/cart";
        }
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("totalAmount", cartService.getTotalAmount());
        model.addAttribute("customers", customerService.getAllCustomers());
        return "checkout";
    }

    @PostMapping("/create")
    public String createOrder(
            @RequestParam Double customerPaid,
            @RequestParam(required = false) Long customerId,
            Model model) {
        try {
            List<CartItem> cartItems = cartService.getCartItems();
            if (cartItems.isEmpty()) {
                return "redirect:/cart";
            }

            Double totalAmount = cartService.getTotalAmount();
            if (customerPaid < totalAmount) {
                model.addAttribute("error", "Số tiền thanh toán không đủ");
                model.addAttribute("cartItems", cartItems);
                model.addAttribute("totalAmount", totalAmount);
                model.addAttribute("customers", customerService.getAllCustomers());
                return "checkout";
            }

            Customer customer = null;
            if (customerId != null) {
                customer = customerService.getCustomerById(customerId);
            }

            Invoice order = orderService.createOrder(cartItems, customer, customerPaid);
            cartService.clearCart();
            return "redirect:/order/" + order.getId();
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("cartItems", cartService.getCartItems());
            model.addAttribute("totalAmount", cartService.getTotalAmount());
            model.addAttribute("customers", customerService.getAllCustomers());
            return "checkout";
        }
    }

    @GetMapping("/success")
    public String orderSuccess() {
        return "order-success";
    }
} 