package com.yzj.risingpath_zsb_backend.predictor;

import cn.hutool.json.JSONObject;
import com.google.gson.Gson;
import com.yzj.risingpath_zsb_backend.domain.dto.GenerateRequest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class ModelPredictor {
    /**
     返回AI预测的分数
     */
    public Integer getAiScore(GenerateRequest generate) {
        Integer Study_Hours = generate.getHours();
        ArrayList<Integer> hours = new ArrayList<>();
        hours.add(Study_Hours);
        hours.add(Study_Hours);
        hours.add(Study_Hours);
        Integer Study_Regularity = generate.getRegularity();
        ArrayList<Integer> regularity = new ArrayList<>();
        regularity.add(Study_Regularity);
        regularity.add(Study_Regularity);
        regularity.add(Study_Regularity);
        Integer Study_Method = generate.getMethod();
        ArrayList<Integer> method = new ArrayList<>();
        method.add(Study_Method);
        method.add(Study_Method);
        method.add(Study_Method);
        Integer Student_Background = generate.getBackground();
        ArrayList<Integer> background = new ArrayList<>();
        background.add(Student_Background);
        background.add(Student_Background);
        background.add(Student_Background);
        Integer Study_Start = generate.getStart();
        ArrayList<Integer> start = new ArrayList<>();
        start.add(Study_Start);
        start.add(Study_Start);
        start.add(Study_Start);
        Integer Exam_Score = generate.getScore();
        ArrayList<Integer> score = new ArrayList<>();
        score.add(Exam_Score);
        int otherScore = Exam_Score + 10;
        score.add(otherScore);
        score.add(236);
        Map<String, List<Integer>> dataMap = new HashMap<>();
        dataMap.put("Study_Hours", hours);
        dataMap.put("Study_Regularity", regularity);
        dataMap.put("Study_Method", method);
        dataMap.put("Student_Background", background);
        dataMap.put("Study_Start", start);
        dataMap.put("Exam_Score", score);
        Integer finalScore = getPredictScore(dataMap);
        return finalScore;
    }

    /**
     *调用神经网络接口
     */
    public static Integer getPredictScore(Map<String, List<Integer>> dataMap) {
        int averagePrediction = 0;
        try {
            // 定义目标URL
            String url = "http://localhost:5000/predict"; // 替换为你的API端点

            Gson gson = new Gson();
            String jsonData = gson.toJson(dataMap);

            // 创建URL对象
            URL apiUrl = new URL(url);

            // 打开连接
            HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();

            // 设置请求方法
            connection.setRequestMethod("POST");

            // 设置请求头
            connection.setRequestProperty("Content-Type", "application/json");

            // 允许输出
            connection.setDoOutput(true);

            // 获取输出流并写入JSON数据
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonData.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // 读取响应
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                // 解析JSON响应
                JSONObject jsonResponse = new JSONObject(response.toString());
                // 获取"average_prediction"的值
                averagePrediction = jsonResponse.getInt("average_prediction");
//                System.out.println("Average Prediction: " + averagePrediction);
            } else {
                System.out.println("HTTP Request failed with response code: " + responseCode);
            }
            // 关闭连接
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return averagePrediction;
    }

}
