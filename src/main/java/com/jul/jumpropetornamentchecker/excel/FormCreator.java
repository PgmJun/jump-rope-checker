package com.jul.jumpropetornamentchecker.excel;

import com.jul.jumpropetornamentchecker.domain.event.Event;
import com.jul.jumpropetornamentchecker.dto.competition.CompetitionResponseDto;
import com.jul.jumpropetornamentchecker.dto.competitionEvent.CompetitionEventResponseDto;
import com.jul.jumpropetornamentchecker.dto.organization.OrganizationResponseDto;
import com.jul.jumpropetornamentchecker.service.CompetitionEventService;
import com.jul.jumpropetornamentchecker.service.CompetitionService;
import com.jul.jumpropetornamentchecker.service.EventService;
import com.jul.jumpropetornamentchecker.service.OrganizationService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.stereotype.Component;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class FormCreator {

    private final CompetitionService cmptService;
    private final OrganizationService orgService;
    private final CompetitionEventService cmptEventService;
    private final EventService eventService;

    public void createForm(HttpServletResponse response, Long cmptId, Long orgId) {
        boolean createResult = true;
        try {
            List<CompetitionEventResponseDto> competitionEventDatum = getCompetitionEventDatum(cmptId)
                    .stream()
                    .collect(Collectors.toList());

            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("대회신청서");

            Row row = null;
            Cell cell = null;
            int rowNo = 0; // 행 갯수
            int columnSize = 5 + competitionEventDatum.size();

            //타이틀용 폰트
            HSSFFont titleFont = workbook.createFont();
            titleFont.setFontName(HSSFFont.FONT_ARIAL); //폰트스타일
            titleFont.setFontHeightInPoints((short) 25); //폰트크기
            titleFont.setBold(true); //Bold 유무
            //서브 타이틀용 폰트
            HSSFFont subTitleFont = workbook.createFont();
            subTitleFont.setFontName(HSSFFont.FONT_ARIAL); //폰트스타일
            subTitleFont.setFontHeightInPoints((short) 15); //폰트크기
            subTitleFont.setBold(true); //Bold 유무

            //타이틀 셀 스타일
            CellStyle cellStyle_Title = workbook.createCellStyle();
            cellStyle_Title.setAlignment(HorizontalAlignment.CENTER);
            cellStyle_Title.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
            cellStyle_Title.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            //서브 타이틀 셀 스타일
            CellStyle cellStyle_subTitle = workbook.createCellStyle();
            cellStyle_subTitle.setAlignment(HorizontalAlignment.CENTER);
            cellStyle_subTitle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            cellStyle_subTitle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            //컬럼 사이즈 조절
            for(int i = 0; i < columnSize; i++) {
                sheet.setColumnWidth(i, (sheet.getColumnWidth(i)) + (short) 1024);
            }

            cellStyle_Title.setFont(titleFont); // cellStyle에 font를 적용
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, columnSize-1)); //첫행, 마지막행, 첫열, 마지막열( 0번째 행의 0~8번째 컬럼을 병합한다)

            cellStyle_subTitle.setFont(subTitleFont);
            sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, columnSize-1));

            // ===================== <타이틀 생성 =====================
            row = sheet.createRow(rowNo++); //행 객체 추가
            cell = row.createCell((short) 0); // 추가한 행에 셀 객체 추가
            cell.setCellStyle(cellStyle_Title); // 셀에 스타일 지정
            cell.setCellValue(getCompetitionAttendFormTitle(cmptId)); // 데이터 입력

            row = sheet.createRow(rowNo++); //행 객체 추가
            cell = row.createCell((short) 0); // 추가한 행에 셀 객체 추가
            cell.setCellStyle(cellStyle_subTitle); // 셀에 스타일 지정
            cell.setCellValue(getCompetitionAttendOrganizationName(orgId)); // 데이터 입력

            row = sheet.createRow(rowNo++);  // 빈행 추가
            // ===================== 타이틀 생성/> =====================

            // ===================== <헤더 생성 =====================
            CellStyle cellStyle_Body = workbook.createCellStyle();
            cellStyle_Body.setAlignment(HorizontalAlignment.CENTER);
            row = sheet.createRow(rowNo++); //대회번호 헤더
            cell = row.createCell((short) 0);
            cell.setCellStyle(cellStyle_Body);
            cell.setCellValue("대회번호");
            cell = row.createCell((short) 1);
            cell.setCellStyle(cellStyle_Body);
            cell.setCellValue(cmptId);

            row = sheet.createRow(rowNo++); //기관번호 헤더
            cell = row.createCell((short) 0);
            cell.setCellStyle(cellStyle_Body);
            cell.setCellValue("기관번호");
            cell = row.createCell((short) 1);
            cell.setCellStyle(cellStyle_Body);
            cell.setCellValue(orgId);
            // ===================== 헤더 생성/> =====================

            // ===================== <종목정보 입력 =====================

            // 단체전 / 개인전 구분 정보 입력
            ArrayList<String> eventIsGroupData = new ArrayList<>(List.of("구분", "-", "-", "-", "-"));
            competitionEventDatum.forEach(ce -> {
                Event event = eventService.findEventDataById(ce.eventId()).orElseThrow();
                if (event.getIsGroupEvent()) {
                    eventIsGroupData.add("단체전");
                } else if (!event.getIsGroupEvent()) {
                    eventIsGroupData.add("개인전");
                }
            });

            row = sheet.createRow(rowNo++);
            for (int i = 0; i < eventIsGroupData.size(); i++) {
                cell = row.createCell((short) i);
                cell.setCellStyle(cellStyle_Body);
                cell.setCellValue(eventIsGroupData.get(i));
            }

            //대회 종목 번호 입력
            ArrayList<String> eventIds = new ArrayList<>(List.of("대회종목번호", "-", "-", "-", "-"));
            competitionEventDatum.forEach(ce -> eventIds.add(ce.cmptEventId().toString()));

            row = sheet.createRow(rowNo++);
            for (int i = 0; i < eventIds.size(); i++) {
                cell = row.createCell((short) i);
                cell.setCellStyle(cellStyle_Body);
                cell.setCellValue(eventIds.get(i));
            }

            // ===================== 종목정보 입력/> =====================


            // ===================== <테이블 생성 =====================
            //테이블 스타일 설정
            CellStyle cellStyle_Table_Center = workbook.createCellStyle();
            cellStyle_Table_Center.setBorderTop(BorderStyle.THIN); //테두리 위쪽
            cellStyle_Table_Center.setBorderBottom(BorderStyle.THIN); //테두리 아래쪽
            cellStyle_Table_Center.setBorderLeft(BorderStyle.THIN); //테두리 왼쪽
            cellStyle_Table_Center.setBorderRight(BorderStyle.THIN); //테두리 오른쪽
            cellStyle_Table_Center.setAlignment(HorizontalAlignment.CENTER);

            ArrayList<String> tableData = new ArrayList<>(List.of("선수명", "참가부", "성별", "생년월일", "연락처"));
            competitionEventDatum.forEach(ce -> tableData.add(ce.eventName()));

            row = sheet.createRow(rowNo++);
            for (int i = 0; i < tableData.size(); i++) {
                cell = row.createCell((short) i);
                cell.setCellStyle(cellStyle_Table_Center);
                cell.setCellValue(tableData.get(i));
            }
            // ===================== 테이블 생성/> =====================

            // ===================== <예시 데이터 입력 =====================
            ArrayList<String> exampleData = new ArrayList<>(List.of("ex) 홍길동", "1", "남", "2000-01-01", "010-1234-1234"));
            for(int i = 0; i < columnSize-5;i++){
                if (i % 2 == 0) {
                    exampleData.add("o");
                }
            }

            row = sheet.createRow(rowNo++);
            for (int i = 0; i < exampleData.size(); i++) {
                cell = row.createCell((short) i);
                cell.setCellStyle(cellStyle_Body);
                cell.setCellValue(exampleData.get(i));
            }

            // ===================== 예시 데이터 입력/> =====================

            // ===================== <파일 생성 =====================
            // ===================== 파일 생성/> =====================

            //파일명은 URLEncoder로 감싸주는게 좋다.
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode(getCompetitionAttendFormTitle(cmptId)+" 양식", "UTF-8")+".xls");


            OutputStream tempFile = response.getOutputStream();
            workbook.write(tempFile);
            tempFile.close();

            response.getOutputStream().flush();
            response.getOutputStream().close();

            if (workbook != null) workbook.close();
        } catch (Exception e) {
            log.error(e.getMessage());
        }

    }

    private String getCompetitionAttendFormTitle(Long cmptId) {
        CompetitionResponseDto cmptDto = cmptService.findCompetitionInfoById(cmptId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않거나 잘못된 대회ID입니다."))
                .toDto();

        return cmptDto.competitionName() + " 참가 신청서";
    }

    private String getCompetitionAttendOrganizationName(Long orgId) {
        OrganizationResponseDto orgDto = orgService.findOrganizationById(orgId).orElseThrow();
        return orgDto.orgName();
    }

    private List<CompetitionEventResponseDto> getCompetitionEventDatum(Long cmptId) {
        return cmptEventService.findCompetitionEventDataByCompetitionId(cmptId);
    }

}
