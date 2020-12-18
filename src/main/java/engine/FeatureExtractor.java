package engine;

import org.datavec.image.loader.ImageLoader;
import org.datavec.image.loader.NativeImageLoader;
import org.deeplearning4j.nn.graph.ComputationGraph;
import org.deeplearning4j.nn.modelimport.keras.KerasModelImport;
import org.deeplearning4j.nn.modelimport.keras.exceptions.UnsupportedKerasConfigurationException;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.FloatBuffer;

import org.nd4j.linalg.api.ops.impl.image.ResizeBilinear;

public class FeatureExtractor {
    private String jsonPath = "C:\\Users\\User\\IdeaProjects\\OverviewGallery\\src\\main\\java\\engine\\models\\model_config.json";
    private String weightsPath = "C:\\Users\\User\\IdeaProjects\\OverviewGallery\\src\\main\\java\\engine\\models\\model_weights.h5";
    private ComputationGraph graph;
    private static FeatureExtractor instance;

    private FeatureExtractor() {
        try {
            graph = KerasModelImport.importKerasModelAndWeights(jsonPath, weightsPath, false);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public static FeatureExtractor getInstance(){
        if (instance == null)
        {
            instance = new FeatureExtractor();
        }
        return instance;
    }
    private BufferedImage resizeImage(BufferedImage img, int imgWidth, int imgHeight) {
        if(img.getWidth() != imgWidth || img.getHeight() != imgHeight) {
            Image newImg = img.getScaledInstance(imgWidth, imgHeight, Image.SCALE_SMOOTH);
            BufferedImage newBufferedImg = new BufferedImage(newImg.getWidth(null),
                    newImg.getHeight(null),
                    BufferedImage.TYPE_INT_RGB);
            newBufferedImg.getGraphics().drawImage(newImg, 0, 0, null);
            return newBufferedImg;
        }
        return img;
    }

    private INDArray arrayImagePreprocessing(INDArray img, int imgHeight, int imgWidth)
    {
        img = Nd4j.expandDims(img, 0);
        img = img.permute(0, 3,2, 1); //BatchSize x H x W x Channels
        img = img.div(256); //Normalize
        img = img.sub(1);
        INDArray preprocessedImage = Nd4j.createUninitialized(1, imgHeight, imgWidth, 3);
        Nd4j.exec(new ResizeBilinear(img,preprocessedImage,imgHeight,imgWidth,false,false));
        return preprocessedImage;
    }


    public float[] extract(BufferedImage img, int imgHeight, int imgWidth) {
        // load the model
        try {
            ImageLoader loader = new ImageLoader(imgHeight, imgWidth, 3);
            INDArray img_array = loader.asMatrix(img);
            img_array = arrayImagePreprocessing(img_array, 224, 224);
            try {
                INDArray output = graph.output(img_array)[0];
                return output.toFloatVector();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
            catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
