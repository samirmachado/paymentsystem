package com.business.app.repository.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Bill extends Auditable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal originalValue;

    private BigDecimal correctedValue;

    private Long numberOfDaysLate;

    @Column(nullable = false)
    private LocalDate dueDate;

    private LocalDate paymentDate;

    private Float finePercentPerDay;

    private Float interestPercent;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

}
