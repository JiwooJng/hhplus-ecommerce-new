package kr.hhplus.be.point.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Point {
    @Id @GeneratedValue
    private Long id;
    private Long userId;
    private BigDecimal point;
    private final BigDecimal maxChargePoint = new BigDecimal(1000000);

    private final LocalDateTime createDate;
    private LocalDateTime updateDate;

    public Point(Long userId, BigDecimal point) {
        this.userId = userId;
        this.point = point;
        this.createDate = LocalDateTime.now();
    }

    public BigDecimal getPoints() {
        return this.point;
    }

    public boolean canCharge(BigDecimal chargeAmount) {
        // 충전 금액은 0보다 커야 함
        if (chargeAmount.compareTo(BigDecimal.ZERO) < 0) {
            return false;
        }
        BigDecimal updatePoint = this.point.add(chargeAmount);

        // 최대 충전 가능 포인트 확인
        if (updatePoint.compareTo(this.maxChargePoint) > 0) {
            return false;
        }

        return true;
    }
    public Point charge(BigDecimal chargeAmount) {
        this.point = this.point.add(chargeAmount);
        this.updateDate = LocalDateTime.now();

        return this;
    }

    public boolean canUse(BigDecimal useAmount) {
        // 사용 금액은 0보다 커야 함
        if (useAmount.compareTo(BigDecimal.ZERO) < 0) {
            return false;
        }
        // 현재 포인트보다 사용 금액이 크면 안됨
        if (this.point.compareTo(useAmount) < 0) {
            return false;
        }

        return true;
    }

    public Point use(BigDecimal useAmount) {
        this.point = this.point.subtract(useAmount);
        this.updateDate = LocalDateTime.now();

        return this;
    }
}
