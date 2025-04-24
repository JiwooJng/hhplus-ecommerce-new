package kr.hhplus.be.domain.point.repository;

import kr.hhplus.be.domain.point.entity.Point;
import kr.hhplus.be.domain.point.entity.PointHistory;

public interface PointRepository {
    Point findById(Long userId);
    Point save(Point point);
    PointHistory saveHistory(PointHistory pointHistory);

}
