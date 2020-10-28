package com.konkuk.repository;

import com.konkuk.Utils;
import com.konkuk.asset.Langs;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Repository {

    public String debugTitle = "";
    HashSet<Integer> uniquePolicy = new HashSet<>();
    private final File file;

    protected Repository(String dataFilePath) {
        file = new File(dataFilePath);
    }

    protected boolean isDataFileExists() {
        return file.exists();
    }

    protected void createEmptyDataFile(String header) {
        Utils.debug("데이터 파일 생성: " + this.debugTitle);
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            // BOM
            bw.write(65279);
            bw.write(header);
            bw.close();
        } catch (IOException e) {
            Utils.exit(Langs.FAIL_TO_CREATE_DATA_FILE);
        }
    }

    protected List<String> parseDataLine(String data) throws ParseException {
        List<String> result = new ArrayList<>();
        StringBuilder s = new StringBuilder();
        boolean error = false;
        boolean open = false;
        // 이전이 " 인지
        boolean escapeBefore = false;
        for(char c: data.toCharArray()) {
            if(open) {
                if(c == '"') {
                    if(escapeBefore) {
                        s.append(c);
                        escapeBefore = false;
                    } else {
                        escapeBefore = true;
                    }
                } else if(c == ',') {
                    if(escapeBefore) {
                        result.add(s.toString());
                        s = new StringBuilder();
                        open = false;
                    } else {
                        s.append(c);
                    }
                    escapeBefore = false;
                } else {
                    // 이 경우에는 오류다. 이전이 " 인데 지금 " 나 , 외의 문
                    if(escapeBefore) {
                        error = true;
                        break;
                    } else {
                        s.append(c);
                        escapeBefore = false;
                    }
                }
            } else {
                // 닫혀있는경우 , 나 " 가 아니면 이상한것
                if(c == '"') {
                    open = true;
                } else if(c == ',') {
                } else {
                    // 첫줄 BOM의 경우 여기서 잡힌다.
                    error = true;
                    break;
                }
            }
        }
        // todo: 추후수정
        if(error) throw new ParseException("", 0);
        // 마지막 처리
        result.add(s.toString());
        return result;
    }

    private String serialize(List<String> fieldsData) {
        return "\n" + fieldsData
                .stream()
                .map(s -> "\"" + s.replaceAll("\"", "\"\"") + "\"")
                .collect(Collectors.joining(","));
    }

    protected interface Deserializer<T> {
        T deserialize(List<String> parsedData);
    }

    protected void addDataLine(List<String> fieldsData) throws IOException {
        FileWriter writer = new FileWriter(file, true);
        writer.write(serialize(fieldsData));
        writer.flush();
        writer.close();
    }

    protected <T> List<T> loadData(Deserializer<T> deserializer) {
        Utils.debug("데이터 파일 (" + debugTitle + ") 로드");
        List<T> result = new ArrayList<>();
        AtomicInteger ignoredData = new AtomicInteger();
        try {
            List<String> lines = Files.readAllLines(Paths.get(file.getPath()));
            AtomicBoolean isFirstLine = new AtomicBoolean(true);
            lines.forEach((line)-> {
                // todo: 어차피 여기서 헤더 무시하긴 할껀데 BOM 처리가 필요할까?. 대신 이렇게하면 BOM 없어도 처리 잘 된다.
                if(isFirstLine.get()) {
                    isFirstLine.set(false);
                    return;
                }
                try {
                    // todo: 일부 데이터가 없는경우 (사번은 무시, 기획서에서 사번만 고유하므로 나머지는 수정 필요를 명시하자)
                    List<String> parsed = parseDataLine(line);
                    result.add(deserializer.deserialize(parsed));
                } catch (ParseException | NumberFormatException e) {
                    // Line이 이상하거나, 정상 파싱은 되었으나 데이터가 이상한 경우
                    ignoredData.getAndIncrement();
                } // int에 해당하는 데이터가 숫자가 아닌 경우
            });
        } catch (IOException e) {
            Utils.debug("파일 읽는 중 오류");
        }
        Utils.debug(debugTitle + " [" + result.size() + " 로드, " + ignoredData + " 무시]");
        return result;
    }
}
