package com.business.app.repository;

import com.business.app.repository.model.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BillRepository extends JpaRepository<Bill, Long> {

    public List<Bill> findByUserId(Long userId);
}
