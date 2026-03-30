package com.seveneleven.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "quantity_measurement")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuantityMeasurementEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // First quantity input
    private double thisValue;
    private String thisUnit;
    private String thisMeasurementType;

    // Second quantity input
    private double thatValue;
    private String thatUnit;
    private String thatMeasurementType;

    // Operation
    private String operation;

    // Result
    private double resultValue;
    private String resultUnit;
    private String resultString;

    // Error
    private boolean isError;
    private String errorMessage;

    // Timestamp
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}

