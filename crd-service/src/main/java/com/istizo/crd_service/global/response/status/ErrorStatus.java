package com.istizo.crd_service.global.response.status;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
@AllArgsConstructor
public enum ErrorStatus {
    _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 에러, 관리자에게 문의 바랍니다."),
    _BAD_REQUEST(HttpStatus.BAD_REQUEST, "COMMON400", "잘못된 요청입니다."),
    _UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "COMMON401", "인증이 필요합니다."),
    _FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403", "금지된 요청입니다."),
    _EMPTY_FIELD(HttpStatus.NO_CONTENT, "COMMON404", "입력 값이 누락되었습니다."),

    SERVICE_ENTITY_NOT_FOUND(HttpStatus.NOT_FOUND, "CRD4001", "ServiceEntity를 찾을 수 없습니다."),
    DEPENDENCY_NOT_FOUND(HttpStatus.NOT_FOUND, "CRD4002", "Dependency를 찾을 수 없습니다."),
    DARKNESS_RELEASE_NOT_FOUND(HttpStatus.NOT_FOUND, "CRD4003", "DarknessRelease를 찾을 수 없습니다.");


    ;
    //
    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
