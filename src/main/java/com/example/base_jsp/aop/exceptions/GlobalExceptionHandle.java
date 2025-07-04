package com.example.base_jsp.aop.exceptions;


import com.example.base_jsp.models.response.BaseResponse;
import com.example.base_jsp.utils.LocalTranslator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandle {
    private final LocalTranslator translator;

    @ExceptionHandler(ApiException.class)
    @ResponseBody
    public ResponseEntity<BaseResponse<?>> handleCustomizedException(ApiException e) {
        log.error("Exception {}", e.getMessage());
        return new ResponseEntity<>(new BaseResponse<>(e.getCode(), e.getMessage(), e.getData()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<BaseResponse<?>> handleInternalException(Exception ex) {
        try {
            log.error("Exception ", ex);
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);
        } catch (Exception e) {
            log.error("===== Handle exception {} =====", e.getMessage());
        }

        return new ResponseEntity<>(new BaseResponse<>(ERROR.SYSTEM_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseBody
    @ExceptionHandler(PropertyReferenceException.class)
    public ResponseEntity<BaseResponse<?>> handleInternalException(PropertyReferenceException e) {
        return new ResponseEntity<>(
                new BaseResponse<>(
                        ERROR.INVALID_REQUEST.getCode(),
                        translator.format("error.property.invalid", e.getPropertyName())
                ),
                HttpStatus.BAD_REQUEST
        );
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        log.error("Validation Exception: {}", e.getMessage());
        String errors = e.getBindingResult()
                         .getFieldErrors()
                         .stream()
                         .map(fieldError -> translator.format(fieldError.getDefaultMessage()))
                         .distinct()
                         .collect(Collectors.joining(System.lineSeparator()));
        BaseResponse<?> response = new BaseResponse<>(ERROR.INVALID_REQUEST.getCode(), errors);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Xử lý exception khi request body truyền vào null
     * @apiNote {@link HttpStatus#BAD_REQUEST}
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<Object> handleHttpMessageNotReadableException() {
        BaseResponse<?> response = new BaseResponse<>(ERROR.INVALID_REQUEST);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Xử lý exception khi request param truyền vào không đúng kiểu dữ liệu
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<BaseResponse<?>> handleIllegalArgumentException(MethodArgumentTypeMismatchException e) {
        BaseResponse<?> response = new BaseResponse<>(ERROR.INVALID_REQUEST);
        log.error("===== Handle method argument type mismatch {} =====", e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Xử lý exception khi thiếu request param
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<BaseResponse<String>> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        BaseResponse<String> response = new BaseResponse<>(ERROR.INVALID_REQUEST);

        response.setData("Missing parameter: " + e.getParameterName());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Xử lý exception khi request method không hợp lệ
     * @param e {@link HttpRequestMethodNotSupportedException}
     * @return {@link BaseResponse}
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ResponseBody
    public ResponseEntity<BaseResponse<?>> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        BaseResponse<?> response = new BaseResponse<>(ERROR.BAD_REQUEST);
        log.error("===== Handle http method not support {} =====", e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.METHOD_NOT_ALLOWED);
    }

//
//    /**
//     * Xử lý exception khi call grpc thất bại
//     * @param e {@link StatusRuntimeException}
//     * @return {@link BaseResponse}
//     */
//    @ExceptionHandler(StatusRuntimeException.class)
//    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
//    @ResponseBody
//    public ResponseEntity<BaseResponse<String>> handleExceptionFromGrpc(StatusRuntimeException e) {
//        BaseResponse<String> response = new BaseResponse<>(ERROR.BAD_REQUEST);
//        response.setData(e.getStatus().getDescription());
//        log.error("===== Handle call grpc fail {} =====", e.getMessage());
//        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//    }
}