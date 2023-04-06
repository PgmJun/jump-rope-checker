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


@Component
@Slf4j
@RequiredArgsConstructor
@Transactional
public class FormParser {

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
            Long cmptId = Long.parseLong(currentRow.getCell(1).toString().substring(0, 1));
            // 기관ID 얻기
            currentRow = sheet.getRow(4);
            Long orgId = Long.parseLong(currentRow.getCell(1).toString().substring(0, 1));

            // 대회종목ID 얻기
            ArrayList<Long> cmptEventIds = new ArrayList<>();
            currentRow = sheet.getRow(6);
            for (int cellIdx = 5; cellIdx < currentRow.getLastCellNum(); cellIdx++) {
                cmptEventIds.add(Long.parseLong(currentRow.getCell(cellIdx).toString().substring(0, 1)));
            }

            // 등록 유저 데이터 생성
            List<CompetitionAttendRequestDto> cmptAttendRequestDtos = new ArrayList<>();
            for (int rowIdx = 8; rowIdx < rowNum; rowIdx++) {
                // 선수 기본 정보 저장
                currentRow = sheet.getRow(rowIdx);

                // row가 비어있으면 저장 stop
                if (currentRow == null) {
                    break;
                }
                // row의 기본정보 컬럼중 빈 정보가 존재하면 저장 stop
                boolean isEmptyValue = false;
                for(int cellnum = 0; cellnum < 5; cellnum++) {
                    if (currentRow.getCell(cellnum).toString().equals("")) {
                        isEmptyValue = true;
                        break;
                    }
                }
                // 빈 컬럼이면 저장 stop
                if (isEmptyValue) {
                    break;
                }

                String name = currentRow.getCell(0).toString();
                Long departId = DepartmentType.findDepartmentByName(currentRow.getCell(1).toString()).getDepartId();
                String gender = currentRow.getCell(2).toString();
                String birth = currentRow.getCell(3).toString();
                String tel = currentRow.getCell(4).toString();

                // 참가하는 대회 종목 ID 저장
                List<Long> attendCmptEventIds = new ArrayList<>();
                for (int cellIdx = 5; cellIdx < currentRow.getLastCellNum(); cellIdx++) {
                    currentCell = currentRow.getCell(cellIdx);
                    // 미신청 종목 continue처리
                    if (currentCell.toString().isBlank()) {
                        continue;
                    }
                    //신청 종목 추가
                    int cmptEventIdIdx = cellIdx - 5;
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
                        .build();

                cmptAttendRequestDtos.add(cmptAttendData);
            }
            if (wb != null) wb.close();
            return cmptAttendRequestDtos;
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

}
