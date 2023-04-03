package com.jul.jumpropetornamentchecker.api.tools;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public abstract class ResponseEntityCreator {


    public ResponseEntity<?> getFindDatumResponseEntity(List<?> findDatum) {
        return (findDatum.isEmpty()) ?
                new ResponseEntity<>("데이터를 불러오지 못했습니다.", HttpStatus.NOT_FOUND) :
                new ResponseEntity<>(findDatum, HttpStatus.OK);
    }

    public abstract ResponseEntity<?> getFindDataResponseEntity(Optional<?> findData);

    public ResponseEntity<?> getSaveDataResponseEntity(boolean saveResult) {
        return (saveResult) ?
                new ResponseEntity<>("데이터가 등록되었습니다.", HttpStatus.OK) :
                new ResponseEntity<>("데이터 등록에 실패하였습니다.", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<?> createCompetitionAttendFormResponseEntity(boolean createResult) {
        return (createResult) ?
                new ResponseEntity<>("대회 신청서가 정상적으로 생성되었습니다.", HttpStatus.OK) :
                new ResponseEntity<>("대회 신청서 생성에 실패하였습니다.", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<?> getUpdateDataResponseEntity(boolean updateResult) {
        return (updateResult) ?
                new ResponseEntity<>("데이터가 갱신되었습니다.", HttpStatus.OK) :
                new ResponseEntity<>("데이터 갱신에 실패하였습니다.", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<?> getRemoveDataResponseEntity(boolean deleteResult) {
        return (deleteResult) ?
                new ResponseEntity<>(" 데이터가 삭제되었습니다.", HttpStatus.OK) :
                new ResponseEntity<>(" 데이터 삭제에 실패하였습니다.", HttpStatus.BAD_REQUEST);
    }
}
