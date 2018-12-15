package com.coderpage.mine.app.tally.module.detail;

import com.coderpage.base.common.Callback;
import com.coderpage.base.common.IError;
import com.coderpage.base.common.NonThrowError;
import com.coderpage.base.common.SimpleCallback;
import com.coderpage.base.error.ErrorCode;
import com.coderpage.concurrency.MineExecutors;
import com.coderpage.mine.app.tally.persistence.model.Expense;
import com.coderpage.mine.app.tally.persistence.model.Income;
import com.coderpage.mine.app.tally.persistence.sql.TallyDatabase;
import com.coderpage.mine.app.tally.persistence.sql.entity.ExpenseEntity;
import com.coderpage.mine.app.tally.persistence.sql.entity.InComeEntity;

/**
 * @author lc. 2018-09-22 22:50
 * @since 0.6.0
 */

class RecordDetailRepository {

    void queryExpense(long expenseId, Callback<Expense, IError> callback) {
        MineExecutors.ioExecutor().execute(() -> {
            Expense expense = TallyDatabase.getInstance().expenseDao().queryById(expenseId);
            if (expense == null) {
                MineExecutors.executeOnUiThread(() -> callback.failure(new NonThrowError(ErrorCode.SQL_ERR, "EMPTY DATA")));
            } else {
                MineExecutors.executeOnUiThread(() -> callback.success(expense));
            }
        });
    }

    void queryIncome(long incomeId, Callback<Income, IError> callback) {
        MineExecutors.ioExecutor().execute(() -> {
            Income income = TallyDatabase.getInstance().incomeDao().queryById(incomeId);
            if (income == null) {
                MineExecutors.executeOnUiThread(() -> callback.failure(new NonThrowError(ErrorCode.SQL_ERR, "EMPTY DATA")));
            } else {
                MineExecutors.executeOnUiThread(() -> callback.success(income));
            }
        });
    }

    void deleteExpense(long expenseId, SimpleCallback<Boolean> callback) {
        MineExecutors.ioExecutor().execute(() -> {
            ExpenseEntity entity = new ExpenseEntity();
            entity.setId(expenseId);
            TallyDatabase.getInstance().expenseDao().delete(entity);
            MineExecutors.executeOnUiThread(() -> callback.success(true));
        });
    }

    void deleteIncome(long incomeId, SimpleCallback<Boolean> callback) {
        MineExecutors.ioExecutor().execute(() -> {
            InComeEntity entity = new InComeEntity();
            entity.setId(incomeId);
            TallyDatabase.getInstance().incomeDao().delete(entity);
            MineExecutors.executeOnUiThread(() -> callback.success(true));
        });
    }
}