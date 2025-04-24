package kr.hhplus.be.domain.point;

import kr.hhplus.be.domain.point.entity.Point;
import kr.hhplus.be.domain.point.entity.PointHistory;
import kr.hhplus.be.domain.point.repository.PointRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class PointService {
    private final PointRepository pointRepository;

    public PointService(PointRepository pointRepository) {
        this.pointRepository = pointRepository;
    }

    public BigDecimal getPoint(Long userId) {
        Point point = pointRepository.findById(userId);
        if (point == null) {
            throw new RuntimeException("User not fount");
        }
        return point.getPoint();
    }

    @Transactional
    public void charge(Long userId, BigDecimal chargeAmount) {
        Point point = pointRepository.findById(userId);
        if (point == null) {
            throw new IllegalArgumentException("User not fount");
        }

        Point updatePoint = point.charge(chargeAmount);

        pointRepository.save(updatePoint);
        pointRepository.saveHistory(new PointHistory(TransactionType.CHARGE, updatePoint));

    }

    @Transactional
    public void use(Long userId, BigDecimal useAmount) {
        Point point = pointRepository.findById(userId);
        if (point == null) {
            throw new IllegalArgumentException("User not fount");
        }

        Point updatePoint = point.use(useAmount);

        pointRepository.save(updatePoint);
        pointRepository.saveHistory(new PointHistory(TransactionType.USE, updatePoint));
    }

}
