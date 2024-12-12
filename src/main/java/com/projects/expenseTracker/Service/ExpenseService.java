package com.projects.expenseTracker.Service;

import com.projects.expenseTracker.Entity.Expense;
import com.projects.expenseTracker.Entity.User;
import com.projects.expenseTracker.Repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ExpenseService {
    @Autowired
    private ExpenseRepository expenseRepository;

    public Expense addExpense(Expense expense) {
        return expenseRepository.save(expense);
    }

    public List<Expense> getExpensesByUser(User user) {
        return expenseRepository.findByUser(user);
    }
    public Expense findById(String id) {
        return expenseRepository.findById(id).orElse(null);
    }
    public void delete(Expense expense) {
        expenseRepository.delete(expense);
    }
    public Expense save(Expense expense) {
        return expenseRepository.save(expense);
    }

}