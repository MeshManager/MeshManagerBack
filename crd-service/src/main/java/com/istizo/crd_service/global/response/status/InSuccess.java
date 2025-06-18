package com.istizo.crd_service.global.response.status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum InSuccess {

    _OK(HttpStatus.OK, "COMMON200", "성공입니다."),

    GET_SERVICE_ENTITY_LIST_SUCCESS(HttpStatus.OK, "CRD2001", "service entity id list 조회 성공"),
    GET_SERVICE_ENTITY_SUCCESS(HttpStatus.OK, "CRD2002", "service entity 정보 조회 성공"),
    GET_DEPENDENCY_SUCCESS(HttpStatus.OK, "CRD2003", "dependency 정보 조회 성공"),
    GET_DARKNESSRELEASE_SUCCESS(HttpStatus.OK, "CRD2004", "darknessRelease 정보 조회 성공"),
    GET_DEPENDANTID_SUCCESS(HttpStatus.OK, "CRD2005", "dependency 정보 조회 성공"),
    
    POST_SERVICE_ENTITY_SUCCESS(HttpStatus.OK, "CRD2006", "service entity 생성 성공"),
    POST_DEPENDENCY_SUCCESS(HttpStatus.OK, "CRD2007", "dependency 생성 성공"),
    POST_DARKNESSRELEASE_SUCCESS(HttpStatus.OK, "CRD2008", "darknessRelease 생성 성공"),

    DELETE_SERVICE_ENTITY_SUCCESS(HttpStatus.OK, "CRD2009", "service entity 삭제 성공"),
    DELETE_DEPENDENCY_SUCCESS(HttpStatus.OK, "CRD2010", "dependency 삭제 성공"),
    DELETE_DARKNESSRELEASE_SUCCESS(HttpStatus.OK, "CRD2011", "darknessRelease 삭제 성공"),

    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}