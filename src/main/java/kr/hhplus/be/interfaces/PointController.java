package kr.hhplus.be.interfaces;

import kr.hhplus.be.domain.point.PointService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/points")
public class PointController {
    private final PointService pointService;

    public PointController(PointService pointService) {
        this.pointService = pointService;
    }

    // 포인트 조회
    @GetMapping("/{userId}")
    public BigDecimal getPoint(@PathVariable Long userId) {
        return pointService.getPoint(userId);
    }

    // 포인트 충전
    @PostMapping("/charge/{userId}")
    public void chargePoint(@PathVariable Long userId, @RequestBody BigDecimal amount) {
        pointService.charge(userId, amount);
    }
    // 포인트 사용
    @PostMapping("/use/{userId}")
    public void usePoint(@PathVariable Long userId, @RequestBody BigDecimal amount) {
        pointService.use(userId, amount);
    }
}
