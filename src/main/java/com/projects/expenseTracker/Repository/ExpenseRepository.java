package com.projects.expenseTracker.Repository;

import com.projects.expenseTracker.Entity.Expense;
import com.projects.expenseTracker.Entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ExpenseRepository extends MongoRepository<Expense, String> {
    List<Expense> findByUser(User user);
}