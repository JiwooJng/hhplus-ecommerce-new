package kr.hhplus.be.test.point;

import kr.hhplus.be.point.Point;
import kr.hhplus.be.point.repository.PointHistoryRepository;
import kr.hhplus.be.point.repository.PointRepository;
import kr.hhplus.be.point.PointService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class PointServiceUnitTest {

    @Mock
    private PointRepository pointRepository;
    @Mock
    private PointHistoryRepository pointHistoryRepository;
    @InjectMocks
    private PointService pointService;

    private final Long userId = 1L;
    private final BigDecimal initPoints = new BigDecimal("1000");


    //TODO: 포인트를 조회 할 수 있다.
    @Test
    void 포인트_조회_성공() {
        // given
        when(pointRepository.findById(userId))
                .thenReturn(new Point(userId, initPoints));
        // when
        BigDecimal points = pointService.getPoint(userId);
        // then
        Assertions.assertEquals(initPoints, points);

    }

    // TODO: 포인트를 충전할 수 있다.
    @Test
    void 포인트_충전_성공() {
        // given
        when(pointRepository.findById(userId))
                .thenReturn(new Point(userId, initPoints));
        BigDecimal chargeAmount = new BigDecimal("500");

        // when
        pointService.charge(userId, chargeAmount);

        // then
        BigDecimal expectedPoints = initPoints.add(chargeAmount);
        Assertions.assertEquals(expectedPoints, pointService.getPoint(userId));
    }

    //TODO: 포인트를 사용할 수 있다.
    @Test
    void 포인트_사용_성공() {
        // given
        when(pointRepository.findById(userId))
                .thenReturn(new Point(userId, initPoints));
        BigDecimal useAmount = new BigDecimal("500");

        // when
        pointService.use(userId, useAmount);

        // then
        BigDecimal expectedPoints = initPoints.subtract(useAmount);
        Assertions.assertEquals(expectedPoints, pointService.getPoint(userId));
    }

    //TODO: 포인트 충전 최대 한도가 넘으면 포인트를 충전할 수 없다.
    @Test
    void 포인트_충전_실패_최대한도초과() {
        // given
        when(pointRepository.findById(userId))
                .thenReturn(new Point(userId, new BigDecimal("1000000")));
        BigDecimal chargeAmount = new BigDecimal("1");

        // when & then
        Exception exception = Assertions.assertThrows(RuntimeException.class, () ->
                pointService.charge(userId, chargeAmount));
        Assertions.assertEquals("Charge failed", exception.getMessage());
    }

    //TODO: 포인트 사용 금액이 잔액을 초과하면 포인트를 사용할 수 없다.
    @Test
    void 포인트_사용_실패_잔액초과() {
        // given
        when(pointRepository.findById(userId))
                .thenReturn(new Point(userId, initPoints));
        BigDecimal useAmount = new BigDecimal("2000");

        // when & then
        Exception exception = Assertions.assertThrows(RuntimeException.class, () ->
                pointService.use(userId, useAmount));
        Assertions.assertEquals("Use failed", exception.getMessage());
    }
}
