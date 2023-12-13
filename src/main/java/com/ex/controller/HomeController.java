package com.ex.controller;

import com.ex.model.AuthRequest;
import com.ex.model.CreateCustomer;
import com.ex.model.Customer;
import com.ex.service.ApiService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class HomeController {
    private final ApiService apiService;
    private static final String SESSION_TOKEN_KEY = "token";

    public HomeController(ApiService apiService) {
        this.apiService = apiService;
    }

    //    ...............login...............
    @RequestMapping("/*")
    public String protectAllPage() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String goToLoginPage(HttpSession session) {
        if (isLoggedIn(session))
            return "redirect:/customer";
        return "login-user";
    }


    //    pages(create, read, update page)
    @GetMapping("/addCustomer")
    public String addCustomerPage(HttpSession session, Model model) {
        if (!isLoggedIn(session))
            return "redirect:/login";
        session.removeAttribute("uuid");
        return "add-customer";
    }

    @GetMapping("/customer")
    public String getCustomerListPage(HttpSession session, Model model) {
        if (!isLoggedIn(session))
            return "redirect:/login";
        model.addAttribute("customerList", apiService.getCustomerList(session));
        return "customer-list";

    }

    @GetMapping("/updateCustomer")
    public String updatePage(@RequestParam String uuid,
                             @RequestParam String first_name,
                             @RequestParam String last_name,
                             @RequestParam String street,
                             @RequestParam String address,
                             @RequestParam String city,
                             @RequestParam String state,
                             @RequestParam String email,
                             @RequestParam String phone,
                             HttpSession session,
                             Model model) {
        System.out.println("first name " + first_name);

        if (!isLoggedIn(session))
            return "redirect:/login";

        session.setAttribute("uuid", uuid);
        Customer customer = new Customer(uuid.trim(), first_name.trim().toString(), last_name.trim(), street.trim(),
                address.trim(), city.trim(), state.trim(), email.trim(), phone.trim());
        System.out.println("customer for update " + customer);
        model.addAttribute("customer", customer);
        return "update-customer";
    }

    //..........................customer operation..............................................................................................
    @PostMapping("/login")
    public String submitForm(@RequestParam String login_id,
                             @RequestParam String password,
                             Model model,
                             RedirectAttributes redirectAttributes,
                             HttpSession session) {

//        boolean verify = apiService.verify(login_id, password);
        AuthRequest authRequest = new AuthRequest(login_id, password);
        String token = apiService.generateToken(session, authRequest);
//        System.out.println(token);
        if (token != null) {
            session.setAttribute("token", token);
            redirectAttributes.addFlashAttribute("msg", "successfully login ");
            redirectAttributes.addFlashAttribute("user", authRequest);
            return "redirect:/customer";
        } else {
            model.addAttribute("msg", "Invalid Credentials");
            return "login-user";
        }
    }

    @PostMapping("/customer")
    public String addCustomer(
            @RequestParam String first_name,
            @RequestParam String last_name,
            @RequestParam String street,
            @RequestParam String address,
            @RequestParam String city,
            @RequestParam String state,
            @RequestParam String email,
            @RequestParam String phone,
            HttpSession session,
            Model model) {
        CreateCustomer customer = new CreateCustomer(first_name, last_name, street, address, city, state, email, phone);

//        System.out.println("Received CreateCustomer: " + customer);
        boolean add = apiService.addCustomer(session, customer);
        if (add) {
            model.addAttribute("msg", "add success");
        } else {
            model.addAttribute("msg", "add fail");
        }
        return "add-customer";
    }

    @PostMapping("/updateCustomer")
    public String updateCustomer(
            @RequestParam String first_name,
            @RequestParam String last_name,
            @RequestParam String street,
            @RequestParam String address,
            @RequestParam String city,
            @RequestParam String state,
            @RequestParam String email,
            @RequestParam String phone,
            HttpSession session,
            RedirectAttributes redirectAttributes,
            Model model

    ) {
        String uuid = (String) session.getAttribute("uuid");
//        System.out.println("uuid" + uuid);
        CreateCustomer createCustomer = new CreateCustomer(first_name, last_name, street, address, city, state, email, phone);
        System.out.println("Received CreateCustomer: " + createCustomer);
        boolean updated = apiService.update(session, uuid, createCustomer);

        if (updated) {
            session.removeAttribute("uuid");
            redirectAttributes.addFlashAttribute("msg", "update success");

            return "redirect:/customer";
        }
        model.addAttribute("msg", "update fail");
        return "update-customer";
    }


    @GetMapping("/deleteCustomer")
    public String delete(HttpSession session, Model model, @RequestParam("uuid") String uuid) {
        boolean delete = apiService.deleteCustomer(session, uuid);
        System.out.println(delete);
        if (delete) {
            model.addAttribute("msg", "delete success");
        } else {
            model.addAttribute("msg", "delete fail");
        }
        return "customer-list";
    }

    @ResponseBody
    @GetMapping("/logout")
    public RedirectView logout(HttpSession session) {
        if (isLoggedIn(session)) {
            session.removeAttribute("token");
        }
        return new RedirectView("/");
    }


    private boolean isLoggedIn(HttpSession session) {
        String token = (String) session.getAttribute("token");
        return token != null && !token.isEmpty();
    }

}

