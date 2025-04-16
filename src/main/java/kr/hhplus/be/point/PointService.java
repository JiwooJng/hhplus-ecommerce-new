package kr.hhplus.be.point;

import kr.hhplus.be.point.repository.PointHistoryRepository;
import kr.hhplus.be.point.repository.PointRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PointService {
    private final PointRepository pointRepository;
    private final PointHistoryRepository pointHistoryRepository;

    public PointService(PointRepository pointRepository, PointHistoryRepository pointHistoryRepository) {
        this.pointRepository = pointRepository;
        this.pointHistoryRepository = pointHistoryRepository;
    }

    public BigDecimal getPoint(Long userId) {
        Point point = pointRepository.findById(userId);
        if (point == null) {
            throw new RuntimeException("User not fount");
        }
        return point.getPoints();
    }

    public void charge(Long userId, BigDecimal chargeAmount) {
        Point point = pointRepository.findById(userId);
        if (point == null) {
            throw new RuntimeException("User not fount");
        }

        boolean result = point.canCharge(chargeAmount);
        if (!result) {
            throw new RuntimeException("Charge failed");
        }

        Point updatePoint = point.charge(chargeAmount);

        pointRepository.save(updatePoint);
        pointHistoryRepository.save(new PointHistory(TransactionType.CHARGE, updatePoint));

    }

    public void use(Long userId, BigDecimal useAmount) {
        Point point = pointRepository.findById(userId);
        if (point == null) {
            throw new RuntimeException("User not fount");
        }

        boolean result = point.canUse(useAmount);
        if (!result) {
            throw new RuntimeException("Use failed");
        }
        Point updatePoint = point.use(useAmount);

        pointRepository.save(updatePoint);
        pointHistoryRepository.save(new PointHistory(TransactionType.USE, updatePoint));
    }

}
