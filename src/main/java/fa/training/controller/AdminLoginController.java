package fa.training.controller;

import fa.training.dto.AdminLoginDTO;
import fa.training.entity.Account;
import fa.training.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class AdminLoginController {
    private final AccountService accountService;
    private final HttpSession session;

    public AdminLoginController(AccountService accountService, HttpSession session) {
        this.accountService = accountService;
        this.session = session;
    }


    @GetMapping("alogin")
    public String login(ModelMap model) {
        model.addAttribute("account", new AdminLoginDTO());
        return "admin/accounts/login";
    }

    @PostMapping("alogin")
    public ModelAndView login(ModelMap model,
                              @Valid @ModelAttribute("account") AdminLoginDTO dto,
                              BindingResult result) {
        if (result.hasErrors()) {
            return new ModelAndView("admin/accounts/login",model);
        }

        Account account = accountService.login(dto.getUsername(), dto.getPassword());
        if( account == null) {
            model.addAttribute("message", "Invalid Username or Password");
            return new ModelAndView("admin/accounts/login",model);
        }
        session.setAttribute("username", account.getUsername());
        Object ruri = session.getAttribute("redirect-uri");

        if (ruri!=null) {
            session.removeAttribute("redirect-uri");
            return new ModelAndView("redirect:" +  ruri);
        }


        return new ModelAndView("forward:/admin/categories", model);

    }

}
