//package com.yzj.risingpath_zsb_backend.predictor;
//
//import org.tensorflow.*;
//import org.tensorflow.framework.GraphDef;
//import org.tensorflow.framework.MetaGraphDef;
//import org.tensorflow.framework.SignatureDef;
//import org.tensorflow.types.UInt8;
//import java.nio.file.Paths;
//import java.util.List;
//import java.util.Map;
//
//public class ModelPredictor {
//    public static void main(String[] args) {
//        try (SavedModelBundle model = SavedModelBundle.load("path_to_your_model", "serve")) {
//            // 获取模型的输入和输出签名
//            Map<String, SignatureDef> signatureMap = model.metaGraphDef().getSignatureDefMap();
//            SignatureDef signature = signatureMap.get("serving_default");
//
//            // 获取输入和输出的张量名称
//            String inputTensorName = signature.getInputsMap().get("input_tensor_name").getName();
//            String outputTensorName = signature.getOutputsMap().get("output_tensor_name").getName();
//
//            // 创建一个TensorFlow会话
//            try (TensorFlow.Session session = model.session()) {
//                // 构建输入张量
//                float[] inputData = {1.0f, 2.0f, 3.0f}; // 替换为您的输入数据
//                Tensor<Float> inputTensor = Tensors.create(inputData);
//
//                // 运行模型进行预测
//                List<Tensor<?>> outputs = session.runner()
//                        .feed(inputTensorName, inputTensor)
//                        .fetch(outputTensorName)
//                        .run();
//
//                // 获取模型的输出
//                Tensor<?> outputTensor = outputs.get(0);
//
//                // 在Java中处理模型输出
//                float[] predictions = new float[(int) outputTensor.shape()[0]];
//                outputTensor.copyTo(predictions);
//
//                // 处理预测结果
//                for (float prediction : predictions) {
//                    System.out.println("Prediction: " + prediction);
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
