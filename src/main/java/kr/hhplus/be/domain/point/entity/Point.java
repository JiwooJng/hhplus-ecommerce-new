package kr.hhplus.be.domain.point.entity;

import jakarta.persistence.*;
import kr.hhplus.be.domain.user.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Point {
    @Id @GeneratedValue
    @Column(name = "point_id")
    private Long id;
    @OneToOne @JoinColumn(name = "user_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private User user;
    @Column(nullable = false)
    private BigDecimal point;
    @Column(nullable = false)
    private final BigDecimal maxChargePoint = BigDecimal.valueOf(1000000);
    @Column(nullable = false)
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    public Point(BigDecimal point) {
        this.point = point;
        this.createDate = LocalDateTime.now();
    }
    public Point() {
        // JPA에서 사용하기 위한 기본 생성자
    }

    public BigDecimal getPoint() {
        return this.point;
    }

    public void canCharge(BigDecimal chargeAmount) {
        // 충전 금액은 0보다 커야 함
        if (chargeAmount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("충전 금액은 0보다 커야 합니다.");
        }

        // 최대 충전 가능 포인트 확인
        BigDecimal updatePoint = this.point.add(chargeAmount);
        if (updatePoint.compareTo(this.maxChargePoint) > 0) {
            throw new IllegalArgumentException("최대 충전 가능 포인트는 " + this.maxChargePoint + "입니다.");
        }
    }

    public Point charge(BigDecimal chargeAmount) {
        canCharge(chargeAmount);
        this.point = this.point.add(chargeAmount);
        this.updateDate = LocalDateTime.now();

        return this;
    }

    public void canUse(BigDecimal useAmount) {
        // 사용 금액은 0보다 커야 함
        if (useAmount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("사용 금액은 0보다 커야 합니다.");
        }
        // 현재 포인트보다 사용 금액이 크면 안됨
        if (this.point.compareTo(useAmount) < 0) {
            throw new IllegalArgumentException("포인트가 부족합니다.");
        }

    }

    public Point use(BigDecimal useAmount) {
        canUse(useAmount);
        this.point = this.point.subtract(useAmount);
        this.updateDate = LocalDateTime.now();

        return this;
    }
}
