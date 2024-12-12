package com.projects.expenseTracker.Controller;

import com.projects.expenseTracker.Entity.Expense;
import com.projects.expenseTracker.Entity.User;
import com.projects.expenseTracker.Service.ExpenseService;
import com.projects.expenseTracker.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/expenses")
//@PreAuthorize("isAuthenticated()")
public class ExpenseController {
    @Autowired
    private ExpenseService expenseService;
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<Expense> addExpense(@RequestBody Expense expense, Authentication authentication) {
        String username = ((UserDetails) authentication.getPrincipal()).getUsername();
        User user = userService.findByUsername(username);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        expense.setUser(user);
        Expense addedExpense = expenseService.addExpense(expense);
        return ResponseEntity.ok(addedExpense);
    }

    @GetMapping
    public ResponseEntity<List<Expense>> getExpenses(Authentication authentication) {
        String username = ((UserDetails) authentication.getPrincipal()).getUsername();
        User user = userService.findByUsername(username);
        List<Expense> expenses = expenseService.getExpensesByUser(user);
        return ResponseEntity.ok(expenses);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Expense> updateExpense(@PathVariable String id, @RequestBody Expense updatedExpense) {
        Expense expense = expenseService.findById(id);
        if (expense == null) {
            return ResponseEntity.notFound().build();
        }
        expense.setCategory(updatedExpense.getCategory());
        expense.setAmount(updatedExpense.getAmount());
        expense.setDate(updatedExpense.getDate());

        Expense updatedExpenseEntity = expenseService.save(expense);
        return ResponseEntity.ok(updatedExpenseEntity);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable String id) {
        Expense expense = expenseService.findById(id);
        if (expense == null) {
            return ResponseEntity.notFound().build();
        }

        expenseService.delete(expense);
        return ResponseEntity.noContent().build();
    }


}