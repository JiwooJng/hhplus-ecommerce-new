package kr.hhplus.be.domain.point.entity;

import jakarta.persistence.*;
import kr.hhplus.be.domain.point.enumtype.TransactionType;

import java.time.LocalDateTime;

@Entity
public class PointHistory {
    @Id @GeneratedValue
    private Long id;
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
    @ManyToOne
    @JoinColumn(name = "point_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Point point;
    @Column(nullable = false)
    private LocalDateTime updateDate;

    public PointHistory(TransactionType transactionType, Point point) {
        this.transactionType = transactionType;
        this.point = point;

        this.updateDate = LocalDateTime.now();

    }
    public PointHistory() {
    }
}
