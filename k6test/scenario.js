import http from 'k6/http';
import { check, sleep } from 'k6';
import { uuidv4 } from 'https://jslib.k6.io/k6-utils/1.3.2/index.js';

export const options = {
    vus: 200, // 가상 유저 수
    duration: '30s', // 테스트 지속 시간
    thresholds: {
        http_req_failed: ['rate<0.01'], // 실패율이 1% 미만이어야 함
        http_req_duration: ['p(95)<500'] // 95%의 요청이 500ms 이하로 응답해야 함
    }
};

export default function () {
    const couponId = 1; // 테스트할 쿠폰 ID
    const userId = uuidv4(); // 유저 ID를 UUID로 생성

    const url= 'http://localhost:8080/api/coupons/redis/coupon/requestIssue';
    const payload = JSON.stringify({
        couponId: couponId,
        userId: userId,
    });

    const params = {
        headers: {
            'Content-Type': 'application/json',
        },
    };

    const response = http.post(url, payload, params);

    check(response, {
        'is status 200': (r) => r.status === 200,
        'is success true': (r) => r.json().success === true,
    });

    sleep(0.5);
}

