package com.wechat.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * @author w29530
 * @date 2021/12/15
 * @desc 读取CSV数据成二维数组
 */
public class CSVParam {

    private static String[][] csvDate ;

    public static String[][] readCSVFileData(String path) throws Exception {
        String lineContent = "";
        ArrayList<String[]> contentList = new ArrayList<>();
        String[] partsOfLine;
        BufferedReader file =  new BufferedReader(new FileReader(path));
        // 读取csv文件并把内容存到列表中
        while((lineContent = file.readLine()) != null)
        {
            partsOfLine = lineContent.split(",");
            for ( int i = 0; i < partsOfLine.length; i++ ){
                partsOfLine[i] = partsOfLine[i].trim();
            }
            contentList.add(partsOfLine);
        }
        // 初始化二维数组
        int size = contentList.size();
        if (size>1){
            csvDate = new String[size-1][contentList.get(0).length];
            // 将读取到的内容赋给二维数组并返回
            for (int i = 0; i < size-1 ; i++) {
                for (int j = 0; j < contentList.get(0).length; j++) {
                    csvDate[i][j] = contentList.get(i+1)[j];
                }
            }
        }
        return csvDate;
    }

}
