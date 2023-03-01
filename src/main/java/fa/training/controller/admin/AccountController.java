package fa.training.controller.admin;

import fa.training.dto.AccountDto;
import fa.training.entity.Account;
import fa.training.entity.Role;
import fa.training.entity.login.ERole;
import fa.training.repository.AccountRepository;
import fa.training.repository.RoleRepository;
import fa.training.service.AccountService;
import org.springframework.beans.BeanUtils;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


import javax.validation.Valid;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


@Controller
@RequestMapping("admin/accounts")
public class AccountController {
    private final AccountRepository accountRepository;
    private final AccountService accountService;
    private final RoleRepository roleRepository;

    public AccountController(AccountService accountService, AccountRepository accountRepository,
                             RoleRepository roleRepository) {
        this.accountService = accountService;
        this.accountRepository = accountRepository;
        this.roleRepository = roleRepository;
    }

    @GetMapping("add")
    public String add(Model model) {
        model.addAttribute("account", new AccountDto());
        return "admin/accounts/addOrEdit";
    }

    @PostMapping("saveOrUpdate")
    public ModelAndView saveOrUpdate(ModelMap model,
                                     @Valid @ModelAttribute("account") AccountDto dto,
                                     BindingResult result) {
        if (result.hasErrors()) {
            return new ModelAndView("admin/accounts/addOrEdit");
        }
        Account entity = new Account();
        BeanUtils.copyProperties(dto, entity);
        Set<Role> roles = new HashSet<>();

        Role role;
        if(dto.getRole().equals("ROLE_ADMIN")) {
            role= roleRepository.findByName(ERole.ROLE_ADMIN).get();
            roles.add(role);
        } else {
            role= roleRepository.findByName(ERole.ROLE_USER).get();
            roles.add(role);
        }
        entity.setCreateDate(LocalDate.now());
        entity.setIsActive(true);
        entity.setRoles(roles);
        accountService.save(entity);
        model.addAttribute("message", "Account is saved!");
        return new ModelAndView("forward:/admin/accounts", model);
    }

    @RequestMapping("admins")
    public String listAdmins(ModelMap model) {
        model.addAttribute("accounts", accountService.findAllAdmins());
        return "admin/accounts/list";
    }
    @RequestMapping("users")
    public String listUsers(ModelMap model) {
        model.addAttribute("accounts", accountService.findAllUsers());
        return "admin/accounts/list";
    }
    @RequestMapping("")
    public String list(ModelMap model) {
        model.addAttribute("accounts", accountService.findAll());
        return "admin/accounts/list";
    }

    @GetMapping("edit/{username}")
    public ModelAndView edit(ModelMap model, @PathVariable("username") String username) {
        Optional<Account> opt = accountService.findByUsername(username);
        AccountDto dto = new AccountDto();

        if (opt.isPresent()) {
            Account entity = opt.get();
            BeanUtils.copyProperties(entity, dto);
            dto.setIsEdit(true);
            dto.setPassword("");
            model.addAttribute("account", dto);
//            entity.setIsActive(false);
//            accountRepository.saveAndFlush(entity);
            return new ModelAndView("/admin/accounts/addOrEdit", model);
        }

        model.addAttribute("message", "account is not existed");
        return new ModelAndView("forward:/admin/accounts/");
    }

    @GetMapping("delete/{accountId}")
    public ModelAndView delete(ModelMap model,@PathVariable("accountId") String username) {
        Optional<Account> opt = accountService.findByUsername(username);
        if (opt.isPresent()) {
            Account entity = opt.get();
            accountService.deleteByUsername(entity);
        }
        model.addAttribute("message","Account is Delete!");
        return new ModelAndView("forward:/admin/accounts",model);
    }

}
//    @GetMapping("/accounts")
//    public ResponseEntity<List<AccountDto>> getAllaccount(){
//        return ResponseEntity.ok(accountService.findAll());
//    }







