package kr.hhplus.be.point;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

public class PointHistory {
    @Id @GeneratedValue
    private Long id;
    private TransactionType transactionType;
    private Point point;
    private LocalDateTime updateDate;

    public PointHistory(TransactionType transactionType, Point point) {
        this.transactionType = transactionType;
        this.point = point;

        this.updateDate = LocalDateTime.now();

    }
}
