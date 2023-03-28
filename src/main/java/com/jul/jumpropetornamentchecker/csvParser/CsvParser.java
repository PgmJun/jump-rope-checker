package com.jul.jumpropetornamentchecker.csvParser;

import com.jul.jumpropetornamentchecker.domain.Organization;
import com.jul.jumpropetornamentchecker.domain.player.Player;
import com.jul.jumpropetornamentchecker.dto.player.PlayerRequestDto;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;


@Component
@Slf4j
@RequiredArgsConstructor
@Transactional
public class CsvParser {
    private static final String SAVE_FILE_PATH = System.getProperty("user.dir") + "/src/main/java/com/jul/jumpropetornamentchecker/csvParser/csv/data.csv";
    private final EntityManager em;

    public void insertData(MultipartFile file, Organization organization) throws IOException, CsvValidationException {
        fileUploadDB(file, organization);
    }

    private void fileUploadDB(MultipartFile file, Organization organization) throws IllegalStateException, IOException, CsvValidationException {

        /* 서버에 저장할 파일 경로와 파일명 설정 */

        /* 빈 파일 생성 */
        File uploadFile = new File(SAVE_FILE_PATH);

        /* 사용자가 업르드한  파일 => 서버에 저장할 파일로 복사 */
        FileCopyUtils.copy(file.getBytes(), uploadFile);

        readPlayerData(organization);
        deleteFile(uploadFile);
    }

    private void readPlayerData(Organization organization) {

        String[] readData;
        try {
            //utf-8 형태로 csv 파일 파싱
            CSVReader csvReader = new CSVReader(new InputStreamReader(new FileInputStream(SAVE_FILE_PATH), "EUC-KR"));

            csvReader.readNext(); // 컬럼명은 저장되지 않도록 한 줄 읽기

            do {
                readData = csvReader.readNext();    //한 라인 읽기 (자동으로 콤마 분리해서 배열에 저장 됌)

                if (readData != null) {
                    Player player = Player.builder()
                            .organization(organization)
                            .playerRequestDto(PlayerRequestDto.builder()
                                    .playerName(readData[0])
                                    .playerGender(readData[1])
                                    .playerAge(Integer.parseInt(readData[2]))
                                    .playerTel(readData[3])
                                    .build())
                            .build();

                    em.persist(player);
                }
            } while (readData != null);



        } catch (IOException e) {
            log.error(e.getMessage());
        } catch (EntityExistsException | CsvValidationException e) {
            log.error(e.getMessage());
        }
    }

    private void deleteFile(File file) {
        file.delete();
    }
}
