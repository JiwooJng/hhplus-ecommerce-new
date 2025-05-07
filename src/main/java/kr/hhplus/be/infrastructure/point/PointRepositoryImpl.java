package kr.hhplus.be.infrastructure.point;

import jakarta.persistence.NoResultException;
import kr.hhplus.be.domain.point.entity.Point;
import kr.hhplus.be.domain.point.entity.PointHistory;
import kr.hhplus.be.domain.point.repository.PointRepository;
import org.springframework.stereotype.Repository;

@Repository
public class PointRepositoryImpl implements PointRepository {
    private final PointJpaRepository pointJpaRepository;
    private final PointHistoryJpaRepository pointHistoryJpaRepository;

    public PointRepositoryImpl(PointJpaRepository pointJpaRepository, PointHistoryJpaRepository pointHistoryJpaRepository) {
        this.pointJpaRepository = pointJpaRepository;
        this.pointHistoryJpaRepository = pointHistoryJpaRepository;
    }

    @Override
    public Point findById(Long pointId) {
        return pointJpaRepository.findById(pointId).orElseThrow(NoResultException::new);
    }
    @Override
    public Point save(Point point) {
        return pointJpaRepository.save(point);
    }

    @Override
    public PointHistory saveHistory(PointHistory pointHistory) {
        return pointHistoryJpaRepository.save(pointHistory);
    }
}
