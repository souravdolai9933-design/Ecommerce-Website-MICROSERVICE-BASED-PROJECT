package com.example.demo.Controller;
import java.util.List;
import org.springframework.web.bind.annotation.ModelAttribute;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;          // ✅ CORRECT Model
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.Dto.PagedResponse;
import com.example.demo.Dto.ProductCategoryDTO;
import com.example.demo.Dto.ProductDTO;
import com.example.demo.OrderDto.OrderDTO;
import com.example.demo.RootcustomerDTO.LoginUserRequestDTO;
import com.example.demo.RootcustomerDTO.RegistrationRequestDTO;
 
 
import com.example.demo.Service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService service;
	
    // ✅ Default entry
    @GetMapping({"", "/"})
    public String defaultUserPage() {
        return "redirect:/user/all";
    }

    @GetMapping("/all")
    public String getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "8") int size,
            Model model, HttpSession session) {
    	
    	System.out.println("*********I am Hitted Get All *****");

        PagedResponse<ProductDTO> response = service.getAllProduct(page, size);
        
        System.out.println("All product :->"+response);

        List<ProductCategoryDTO> categories = service.getAllCategory();
        
        System.out.println("Fins All Category :"+categories);

        System.out.println("Total Response :"+response);
        model.addAttribute("products", response.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", response.getTotalPages());
        model.addAttribute("url", "/user/all");

        model.addAttribute("allcategory", categories);
        session.setAttribute("allcategory", categories);

        return "user/userHome";
    }
    
    @GetMapping("/findProductName")
    public String GetProductByName(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "8") int size,
            Model model, HttpSession session) {

        PagedResponse<ProductDTO> response = service.getProductByName(keyword, page, size);

        List<ProductCategoryDTO> categories = service.getAllCategory();

        model.addAttribute("products", response.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", response.getTotalPages());
        model.addAttribute("url", "/user/findProductName");
        model.addAttribute("keyword", keyword);

        model.addAttribute("allcategory", categories);
        session.setAttribute("allcategory", categories);

        return "user/userHome";
    }
    @GetMapping("/getProduct")
    public String getProductBythroughTheCategry(
            @RequestParam Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "8") int size,
            Model model, HttpSession session) {

        PagedResponse<ProductDTO> response = service.getProductByCategory(id, page, size);

        model.addAttribute("products", response.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", response.getTotalPages());
        model.addAttribute("url", "/user/getProduct");
        model.addAttribute("categoryId", id);

        model.addAttribute("allcategory", session.getAttribute("allcategory"));

        return "user/userHome";
    }
    
    @GetMapping("/my-orders")
    public String getAllUserOrder(Model model,HttpServletRequest req) {
    	
    	
    	System.out.println("I am inside My order mth....");
    	
    	List<OrderDTO> orderList = null;
    	try {
    	   orderList = service.getAllOrderByRootUser(req);
    	}catch(Exception ex) {
    		ex.printStackTrace();
    		System.out.println("User order not avaliable");
    	}
    	if(orderList.isEmpty() || orderList ==null) {
    		
    	}else {
    		
    		model.addAttribute("orderlist",orderList);
    	}
    	
    	return "user/myorder";
    	
    }
    
    
    @GetMapping("/masterView")
    public String PeoductMasterView(@RequestParam Long id, Model model,HttpSession session) {
    	ProductDTO d2 = service.getProduct(id);
    	System.out.println("Product Object :---"+d2);
    	System.out.println("---i am hitted----");
    	model.addAttribute("masterView",d2);
    	
    	System.out.println("Session output "+session.getAttribute("allcategory"));
    	
    	model.addAttribute("allcategory", session.getAttribute("allcategory"));
    	
		return "user/masterView";
    	
    }
    @GetMapping("/getForm")
    public String getRegistrationForm( Model model) {
    	
    	model.addAttribute("registrationForm", new RegistrationRequestDTO());
    	
		return "user/registration";
    	
    }
    
    // Handle Form Submit
    @PostMapping("/save-Form")
    public String saveRegistrationForm(
            @ModelAttribute("registrationForm") RegistrationRequestDTO r3,
            Model model) {
    	
    	System.out.println("I am hitting save registration Form methode...");

        // Password length validation
        if (r3.getPassword() == null || r3.getPassword().length() < 8) {
            model.addAttribute("errorMsg",
                    "Password must be at least 8 characters long");
            return "user/registration";
        }

        // Password match validation (FIXED)
        if (!r3.getPassword().equals(r3.getConfirmpassword())) {
            model.addAttribute("errorMsg",
                    "Password and Confirm Password must match");
            return "user/registration";
        }

        // Call backend API
        String isRegistered = service.userRegistration(r3);
        
        System.out.println("isRegistered --->:"+isRegistered);

        if (isRegistered.equals("Registration Sucess")) {
            model.addAttribute("successMsg",
                    "User registered successfully!");
            model.addAttribute("registrationForm",
                    new RegistrationRequestDTO());
            
        } else if(isRegistered.equals("Email Alreadyexists")){
           model.addAttribute("errorMsg",
                    "Registration failed. Email already exist.");
         model.addAttribute("registrationForm",
                   r3);
            
        }else if(isRegistered.equals("Phone no Already exists")) {
        	 model.addAttribute("errorMsg",
                     "Registration failed. Phone no already exist.");
          model.addAttribute("registrationForm",
                    r3);
        	
        }

        return "user/registration";
    }
    
    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("loginForm", new LoginUserRequestDTO());
        return "user/login";
    }

    @PostMapping("/login")
    public String login(
            @ModelAttribute("loginForm") LoginUserRequestDTO dto,
            RedirectAttributes redirectAttributes, HttpServletRequest request) {

        System.out.println("I am hitting Login method.....");

        String response = service.userLogin(dto, request);
        System.out.println("Response " + response);

        if ("User Not Found".equals(response) || "Incorrect Password".equals(response)) {
            redirectAttributes.addFlashAttribute("error", response);
            return "redirect:/user/login";
        }

        // ✅ SUCCESS
        return "redirect:/user/all";
    }
}

     

