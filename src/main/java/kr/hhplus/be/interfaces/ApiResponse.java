package kr.hhplus.be.interfaces;

public class ApiResponse<T> {
    private final boolean success;
    private final String message;
    private final T data;

    public ApiResponse(boolean isSuccess, String msg, T data) {
        this.success = isSuccess;
        this.message = msg;
        this.data = data;
    }


    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, "요청에 성공하였습니다.", data);
    }

    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(true, message, data);
    }

}
