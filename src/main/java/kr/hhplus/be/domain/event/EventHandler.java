package kr.hhplus.be.domain.event;

public interface EventHandler<T> {
    void handle(T event);
}
