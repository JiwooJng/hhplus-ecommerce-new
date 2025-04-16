package kr.hhplus.be.point.repository;

import kr.hhplus.be.point.Point;

public interface PointRepository {
    Point findById(Long userId);

    void save(Point point);

}
