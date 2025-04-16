package kr.hhplus.be.point.repository;

import kr.hhplus.be.point.entity.PointHistory;

public interface PointHistoryRepository {
    PointHistory save(PointHistory pointHistory);
}
