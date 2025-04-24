package kr.hhplus.be.point.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import kr.hhplus.be.user.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Point {
    @Id @GeneratedValue
    private Long id;

    @OneToOne
    private User user;
    private BigDecimal point;
    private final BigDecimal maxChargePoint = new BigDecimal(1000000);

    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    public Point(BigDecimal point) {
        this.point = point;
        this.createDate = LocalDateTime.now();
    }
    public Point() {
        // JPA에서 사용하기 위한 기본 생성자
    }

    public void setUser(User user) {
        this.user = user;
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
