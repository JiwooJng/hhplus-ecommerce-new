package kr.hhplus.be.point.repository;

import kr.hhplus.be.point.Point;
import kr.hhplus.be.point.PointHistory;
import kr.hhplus.be.point.TransactionType;

public interface PointHistoryRepository {
    PointHistory save(PointHistory pointHistory);
}
