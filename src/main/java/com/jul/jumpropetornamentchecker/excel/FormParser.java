package com.jul.jumpropetornamentchecker.excel;

import com.jul.jumpropetornamentchecker.domain.department.DepartmentType;
import com.jul.jumpropetornamentchecker.dto.attend.CompetitionAttendRequestDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static com.jul.jumpropetornamentchecker.excel.FormCreator.PLAYER_DEFAULT_INFO_COUNT;


@Component
@Slf4j
@RequiredArgsConstructor
@Transactional
public class FormParser {
    private boolean isLastLow = false;

    public List<CompetitionAttendRequestDto> parseFormData(MultipartFile form) {
        return parse(form);
    }


    private List<CompetitionAttendRequestDto> parse(MultipartFile file) {
        try {
            HSSFWorkbook wb = new HSSFWorkbook(file.getInputStream());
            HSSFSheet sheet;
            HSSFRow currentRow; //현재 로우
            HSSFCell currentCell; //현재 셀

            sheet = wb.getSheetAt(0);
            int rowNum = sheet.getLastRowNum();

            // 대회ID 얻기
            currentRow = sheet.getRow(3);
            Long cmptId = Long.parseLong(currentRow.getCell(1).toString().replace(".0", ""));
            // 기관ID 얻기
            currentRow = sheet.getRow(4);
            Long orgId = Long.parseLong(currentRow.getCell(1).toString().replace(".0", ""));

            // 대회종목ID 얻기
            ArrayList<Long> cmptEventIds = new ArrayList<>();
            currentRow = sheet.getRow(6);
            for (int cellIdx = PLAYER_DEFAULT_INFO_COUNT; cellIdx < currentRow.getLastCellNum(); cellIdx++) {
                cmptEventIds.add(Long.parseLong(currentRow.getCell(cellIdx).toString()));
            }

            // 등록 유저 데이터 생성
            List<CompetitionAttendRequestDto> cmptAttendRequestDtos = new ArrayList<>();

            for (int rowIdx = 8; rowIdx <= rowNum; rowIdx++) {
                // 선수 기본 정보 저장
                currentRow = sheet.getRow(rowIdx);

                // row가 비어있으면 저장 stop
                if (currentRow == null) {
                    break;
                }
                // row의 기본정보 컬럼중 빈 정보가 존재하면 저장 stop, 소속 정보는 빈값이면 ""로 변경처리(아래코드에서)
                // 맨앞에 있는 선수명/참가부/성별 만 null 불가능이기 때문에 PLAYER_DEFAULT_INFO_COUNT - 3
                for (int cellnum = 0; cellnum < PLAYER_DEFAULT_INFO_COUNT - 3; cellnum++) {
                    // 빈 컬럼 존재시 예외처리
                    if (currentRow.getCell(cellnum) == null) {
                        isLastLow = true;
                        break;
                    }
                }

                if(isLastLow) break;

                String name = currentRow.getCell(0).toString();
                Long departId = DepartmentType.findDepartmentByName(currentRow.getCell(1).toString()).getDepartId();
                String gender = currentRow.getCell(2).toString();
                String birth = currentRow.getCell(3).toString();
                String tel = "";
                if(currentRow.getCell(4) != null) {
                    if(!currentRow.getCell(4).toString().isBlank()) { //공백으로 이뤄져있지 않으면
                        tel = currentRow.getCell(4).toString(); //소속값 사용
                    } else { //빈값이거나 공백으로 이루어져 있으면 참가기관명으로 입력되도록 소속값을 ""로 초기화
                        log.info(cmptId + "번 대회 / " + orgId + "번 기관 / " + name + " => 연락처가 공백으로 입력되어, 공백 처리됩니다.");
                    }
                } else { //빈값이거나 공백으로 이루어져 있으면 참가기관명으로 입력되도록 소속값을 ""로 초기화
                    log.info(cmptId + "번 대회 / " + orgId + "번 기관 / " + name + " => 연락처가 공백으로 입력되어, 공백 처리됩니다.");
                }

                String affiliation = "";
                //소속값 검증
                if (currentRow.getCell(5) != null) { //빈값이 아니고
                    if(!currentRow.getCell(5).toString().isBlank()) { //공백으로 이뤄져있지 않으면
                        affiliation = currentRow.getCell(5).toString(); //소속값 사용
                    } else { //빈값이거나 공백으로 이루어져 있으면 참가기관명으로 입력되도록 소속값을 ""로 초기화
                        log.info(cmptId + "번 대회 / " + orgId + "번 기관 / " + name + " => 소속이 공백으로 입력되어, 기관명이 소속명으로 입력됩니다.");
                    }
                } else { //빈값이거나 공백으로 이루어져 있으면 참가기관명으로 입력되도록 소속값을 ""로 초기화
                    log.info(cmptId + "번 대회 / " + orgId + "번 기관 / " + name + " => 소속이 공백으로 입력되어, 기관명이 소속명으로 입력됩니다.");
                }


                // 참가하는 대회 종목 ID 저장
                List<Long> attendCmptEventIds = new ArrayList<>();
                for (int cellIdx = PLAYER_DEFAULT_INFO_COUNT; cellIdx < currentRow.getLastCellNum(); cellIdx++) {
                    currentCell = currentRow.getCell(cellIdx);
                    // 미신청 종목 continue처리
                    if (currentCell == null) { //종목이 null이면 넘기기
                        continue;
                    }
                    if (currentCell.toString().isBlank()) { //종목이 null은 아니지만 공백으로 이뤄져있어도 넘기기
                        continue;
                    }
                    //신청 종목 추가
                    int cmptEventIdIdx = cellIdx - PLAYER_DEFAULT_INFO_COUNT;
                    attendCmptEventIds.add(cmptEventIds.get(cmptEventIdIdx));
                }

                CompetitionAttendRequestDto cmptAttendData = CompetitionAttendRequestDto.builder()
                        .cmptId(cmptId)
                        .orgId(orgId)
                        .departId(departId)
                        .cmptEventIds(attendCmptEventIds)
                        .playerName(name)
                        .playerGender(gender)
                        .playerTel(tel)
                        .playerBirth(birth)
                        .playerAffiliation(affiliation)
                        .build();
                System.out.println(cmptAttendData.toString());
                cmptAttendRequestDtos.add(cmptAttendData);
            }
            if (wb != null) wb.close();
            return cmptAttendRequestDtos;
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

}
