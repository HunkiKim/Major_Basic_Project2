package com.konkuk.repository;

import com.konkuk.asset.Settings;
import com.konkuk.dto.DayOff;
import com.konkuk.dto.Employee;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class DayOffHistoryRepository extends Repository implements IDayOffHistoryRepository {

    private static class Instance {
        private static final DayOffHistoryRepository instance = new DayOffHistoryRepository();
    }
    public static DayOffHistoryRepository getInstance() {
        return DayOffHistoryRepository.Instance.instance;
    }

    protected boolean isDataFileExists(String path) {
        File dayOffList = new File(path);
        return dayOffList.exists();
    }
    private List<DayOff> loadDataByRange(int employeeId, int start, int end){
        List<DayOff> results = new ArrayList<>();
        if (isDataFileExists(Settings.DATA_EMPLOYEE)){
            try{
                File file = new File("./dayoff.txt");

                Scanner scan = new Scanner(file);
                String[] line;
                scan.nextLine();
                while(scan.hasNextLine()){
                    line = scan.nextLine().split(",", 7);
                    for(int i=0;i<line.length;i++){
                        // \\\이렇게 안 하면 제대로 작동 안 해서 이렇게 했습니다.
                        line[i] = line[i].replaceAll("\\\"","");
                    }
//                    if(!line[4].substring(0,4).equals(thisYear)) continue;

                    int tempYear = Integer.parseInt(line[4].substring(0,4));
                    if(tempYear<start||tempYear>end) continue;

                    if(employeeId==Integer.parseInt(line[1])) {
                        SimpleDateFormat transFormat = new SimpleDateFormat("yyyyMMdd HH:mm");

                        DayOff dayOff = new DayOff(
                                Integer.parseInt(line[0]),
                                Integer.parseInt(line[1]),
                                Integer.parseInt(line[2]),
                                line[3],
                                transFormat.parse(line[4]),
                                transFormat.parse(line[5]),
                                transFormat.parse(line[6])
                        );
                        results.add(dayOff);
                    }
                }
            }catch (FileNotFoundException | ParseException e) {
                // TODO: handle ParseException
            }
        }else{
            // TODO: 데이터 파일이 존재하지 않음
        }
        return results;
    }
    @Override
    public List<DayOff> findByDate(int employeeId, Date start, Date end){
        // 해당 사번의 연차 시작 연도부터 끝나는 연도까지의 데이터 불러오기
        List<DayOff> results;
        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy");
        int startYear = Integer.parseInt(simpleDate.format(start));
        int endYear = Integer.parseInt(simpleDate.format(end));
        results = this.loadDataByRange(employeeId, startYear, endYear);
        return results;
    }
}
