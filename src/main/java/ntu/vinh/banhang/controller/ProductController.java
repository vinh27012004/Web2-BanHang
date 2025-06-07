package ntu.vinh.banhang.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import ntu.vinh.banhang.entity.Product;
import ntu.vinh.banhang.service.ProductService;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public String listProducts(Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "products";
    }

    @GetMapping("/{id}")
    public String viewProduct(@PathVariable Long id, Model model) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "product-detail";
    }

    @PostMapping("/add")
    @ResponseBody
    public String addProduct(@RequestParam String code,
                           @RequestParam String name,
                           @RequestParam double price,
                           @RequestParam int quantity) {
        try {
            // Kiểm tra xem sản phẩm đã tồn tại chưa
            Product existingProduct = productService.getProductByCode(code);
            if (existingProduct != null) {
                // Nếu đã tồn tại, cập nhật thông tin
                existingProduct.setName(name);
                existingProduct.setPrice(price);
                existingProduct.setQuantity(quantity);
                productService.saveProduct(existingProduct);
                return "Cập nhật sản phẩm thành công";
            } else {
                // Nếu chưa tồn tại, thêm mới
                Product product = new Product();
                product.setCode(code);
                product.setName(name);
                product.setPrice(price);
                product.setQuantity(quantity);
                productService.saveProduct(product);
                return "Thêm sản phẩm thành công";
            }
        } catch (Exception e) {
            return "Lỗi: " + e.getMessage();
        }
    }

    @PostMapping("/{id}/update")
    @ResponseBody
    public String updateProduct(@PathVariable Long id,
                              @RequestParam String name,
                              @RequestParam double price,
                              @RequestParam int quantity) {
        try {
            Product product = productService.getProductById(id);
            if (product != null) {
                product.setName(name);
                product.setPrice(price);
                product.setQuantity(quantity);
                productService.saveProduct(product);
                return "Cập nhật sản phẩm thành công";
            }
            return "Không tìm thấy sản phẩm";
        } catch (Exception e) {
            return "Lỗi: " + e.getMessage();
        }
    }

    @PostMapping("/{id}/delete")
    @ResponseBody
    public String deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteProduct(id);
            return "Xóa sản phẩm thành công";
        } catch (Exception e) {
            return "Lỗi: " + e.getMessage();
        }
    }

    @PostMapping("/{id}/add-to-cart")
    @ResponseBody
    public String addToCart(@PathVariable Long id, @RequestParam Integer quantity) {
        try {
            productService.addToCart(id, quantity);
            return "success";
        } catch (Exception e) {
            return "error: " + e.getMessage();
        }
    }
} 