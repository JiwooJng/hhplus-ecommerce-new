package kr.hhplus.be.infrastructure.point;

import kr.hhplus.be.domain.point.entity.Point;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PointJpaRepository extends JpaRepository<Point, Long> {
}
