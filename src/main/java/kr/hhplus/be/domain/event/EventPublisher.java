package kr.hhplus.be.domain.event;

public interface EventPublisher {
    void publish(Object event);
}
